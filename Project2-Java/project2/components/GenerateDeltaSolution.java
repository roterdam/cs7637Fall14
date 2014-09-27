package project2.components;

import java.util.ArrayList;
import java.util.List;

public class GenerateDeltaSolution implements Generator {

	@SuppressWarnings("unused")
	private Brain brain;

	@SuppressWarnings("unused")
	private GenerateDeltaSolution() {
	}

	public GenerateDeltaSolution(Brain brain) {
		this.brain = brain;
	}

	public List<RDFDocument> generate(RDFDocument docA, RDFXDocument docX) {

		List<RDFDocument> solutions = new ArrayList<RDFDocument>();
		solutions.add(new RDFDocument()); // seed solutions

		for (RDFXFact factX : docX.getFacts()) {
			RDFFact factA = docA.find(factX.getSubject(), factX.getPredicate());
			RDFFact factB;
			int valA;
			int val;
			List<String> values;
			List<RDFFact> facts;
			switch (factX.getState()) {
			case same:
				add(solutions, factA);
				break;
			case different:
				factB = new RDFFact(factX.getSubject(), factX.getPredicate(),
						factX.getObjectB());
				add(solutions, factB);
				break;
			case added:
				factB = new RDFFact(factX.getSubject(), factX.getPredicate(),
						factX.getObjectB());
				add(solutions, factB);
				break;
			case delta:

				int angle = Helper.toNumber(factA.getObject())
						+ factX.getDelta();
				if (angle >= 360) {
					angle -= 360;
				}
				factB = new RDFFact(factX.getSubject(), factX.getPredicate(),
						angle + "");
				add(solutions, factB);

				break;
			}
		}

		return solutions;
	}

	private void add(List<RDFDocument> solutions, RDFFact fact) {
		for (RDFDocument solution : solutions) {
			solution.addFact(fact);
		}
	}

	private List<RDFDocument> add(List<RDFDocument> solutions,
			List<RDFFact> facts) {
		if (facts.size() == 0)
			return solutions;

		if (facts.size() == 1) {
			add(solutions, facts.get(0));
			return solutions;
		} else {
			List<RDFDocument> master = new ArrayList<RDFDocument>();
			for (RDFFact fact : facts) {
				for (RDFDocument solution : solutions) {
					RDFDocument clone;
					try {
						clone = (RDFDocument) solution.clone();
						clone.addFact(fact);
						master.add(clone);
					} catch (CloneNotSupportedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			return master;
		}
	}

}
