package project2.components;

import java.util.ArrayList;
import java.util.List;

public class GenerateBasicSolution implements Generator {

	@SuppressWarnings("unused")
	private Brain brain;

	@SuppressWarnings("unused")
	private GenerateBasicSolution() {
	}

	public GenerateBasicSolution(Brain brain) {
		this.brain = brain;
	}

	public List<RDFDocument> generate(RDFDocument docA, RDFXDocument docX,
			Memory memory) {

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
				facts = new ArrayList<RDFFact>();
				values = memory.recall(factX.getSubject() + ":"
						+ factX.getPredicate());
				for (String value : values) {
					factB = new RDFFact(factX.getSubject(),
							factX.getPredicate(), value);
					facts.add(factB);
				}
				solutions = add(solutions, facts);
				break;
			case added:
				factB = new RDFFact(factX.getSubject(), factX.getPredicate(),
						factX.getObjectB());
				add(solutions, factB);
				break;
			case delta:
				if (factA != null) {
					valA = Helper.toNumber(factA.getObject());
					val = valA + factX.getDelta();
					if (factX.getPredicate().equals("angle")) {
						if (val > 360) {
							val -= 360;
						} else if (val < 0) {
							val += 360;
						}
					}
					factB = new RDFFact(factX.getSubject(),
							factX.getPredicate(), val + "");
					add(solutions, factB);
				}
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
