package project1.components;

import java.util.ArrayList;
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

	@Override
	public int score(RDFDocument docA, RDFDocument docX, RDFDocument docB) {
		List<Integer> scores = new ArrayList<Integer>();

		for (RDFFact factA : docA.getFacts()) {
			try {
				RDFFact factX = docX.find(factA.getSubject(),
						factA.getPredicate());
				try {
					RDFFact factB = docB.find(factA.getSubject(),
							factA.getPredicate());
					if (factX.getObject().equals("missing")) {
						scores.add(0);
					} else if (factX.getObject().equals("same")
							&& factA.getObject().equals(factB.getObject())) {
						scores.add(100);
					} else if (factX.getObject().equals("different")
							&& !factA.getObject().equals(factB.getObject())) {
						scores.add(100);
					} else if (factX.getObject().equals("increase") && Helper.toNumber(factA.getObject()) < Helper.toNumber(factB.getObject())) {
							scores.add(100);
					} else if (factX.getObject().equals("decrease") && Helper.toNumber(factA.getObject()) > Helper.toNumber(factB.getObject())) {
						scores.add(100);
					} else {
						scores.add(0);
					}
				} catch (NotFoundException nfe) {
					if (factX.getObject().equals("missing")) {
						scores.add(100);
					} else {
						scores.add(0);
					}
				}
			} catch (NotFoundException nfe) {
				scores.add(0);
				break;
			}
		}

		int score = 0;
		for (int value : scores) {
			score += value;
		}
		return score / scores.size();

	}

}
