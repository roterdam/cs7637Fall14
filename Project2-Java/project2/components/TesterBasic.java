package project2.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TesterBasic implements Tester {

	@SuppressWarnings("unused")
	private Brain brain;

	@SuppressWarnings("unused")
	private TesterBasic() {
	}

	public TesterBasic(Brain brain) {
		this.brain = brain;
	}

	public int score(RDFDocument docA, RDFDocument docB) {
		// In case no facts to compare
		if (docA.getFacts().size() == 0) {
			return docB.getFacts().size() == 0 ? 100 : 0;
		}

		List<Integer> scores = new ArrayList<Integer>();
		for (RDFFact factA : docA.getFacts()) {
			RDFFact factB = docB.find(factA.getSubject(), factA.getPredicate(),
					factA.getObject());
			if (factB != null) {
				scores.add(100);
			} else {
				scores.add(0);
			}
		}

		for (RDFFact factB : docB.getFacts()) {
			RDFFact factA = docA.find(factB.getSubject(), factB.getPredicate(),
					factB.getObject());
			if (factA != null) {
				scores.add(100);
			} else {
				scores.add(0);
			}
		}

		return scores.size() > 0 ? Helper.average(scores) : 0;
	}

	public int score2(RDFDocument docA, RDFDocument docB) {
		// In case no facts to compare
		if (docA.getFacts().size() == 0) {
			return docB.getFacts().size() == 0 ? 100 : 0;
		}

		List<Integer> scores = new ArrayList<Integer>();
		for (RDFFact factA : docA.getFacts()) {
			RDFFact factB = docB.find(factA.getSubject(), factA.getPredicate());
			if (factB != null) {
				if (factA.getObject() != null
						&& factA.getObject().equals(factB.getObject())) {
					scores.add(100);
				} else {
					scores.add(50);
				}
			} else {
				scores.add(0);
			}
		}

		for (RDFFact factB : docB.getFacts()) {
			RDFFact factA = docA.find(factB.getSubject(), factB.getPredicate());
			if (factA != null) {
				if (factB.getObject().equals(factA.getObject())) {
					scores.add(100);
				} else {
					scores.add(50);
				}
			} else {
				scores.add(0);
			}
		}

		return scores.size() > 0 ? Helper.average(scores) : 0;
	}

	public int score(RDFDocument docA, RDFXDocument docX, RDFDocument docB) {

		List<Integer> scores = new ArrayList<Integer>();

		for (RDFXFact factX : docX.getFacts()) {

			RDFFact factA = docA.find(factX.getSubject(), factX.getPredicate());
			RDFFact factB = docB.find(factX.getSubject(), factX.getPredicate());

			scores.add(score(factA, factX, factB));
		}

		return Helper.average(scores);
	}

	private int score(RDFFact factA, RDFXFact factX, RDFFact factB) {
		if (factA != null && factB != null) {

			try {
				switch (factX.getState()) {
				case missing:
					return !factA.getObject().equals(factB.getObject()) ? 50
							: 0;
				case same:
					return scoreSame(factA.getObject(), factB.getObject());
				case different:
					return !factA.getObject().equals(factB.getObject()) ? 100
							: 50;
				case delta:
					// TODO - give points for being equal or less
					int valA = Helper.toNumber(factA.getObject());
					int valB = Helper.toNumber(factB.getObject());
					return (valA + factX.getDelta() == valB) ? 100 : 0;
				case added:
					return factX.getObjectB().equals(factB.getObject()) ? 100
							: 50;
				case unknown:
				default:
					return 0;
				}
			} catch (Exception e) {
				return 0;
			}

		} else if (factA != null && factB == null) {
			try {
				switch (factX.getState()) {
				case missing:
					return 100;
				case same:
				case different:
				case delta:
				case added:
				case unknown:
				default:
					return 0;
				}
			} catch (Exception e) {
				return 0;
			}

		} else if (factA == null && factB != null) {
			try {
				switch (factX.getState()) {
				case missing:
				case same:
				case different:
				case delta:
					return 0;
				case added:
					return factB.getObject().equals(factX.getObjectB()) ? 100
							: 50;
				case unknown:
				default:
					return 0;
				}
			} catch (Exception e) {
				return 0;
			}
		} else {
			return 0;
		}
	}

	private int scoreSame(String objectA, String objectB) {
		List<String> partsA = Arrays.asList(objectA.split(","));
		List<String> partsB = Arrays.asList(objectB.split(","));

		List<Integer> scores = new ArrayList<Integer>();
		for (String partA : partsA) {
			if (partsB.contains(partA)) {
				scores.add(100);
			} else {
				scores.add(0);
			}
		}
		for (String partB : partsB) {
			if (partsA.contains(partB)) {
				scores.add(100);
			} else {
				scores.add(0);
			}
		}

		return Helper.average(scores);
	}
}
