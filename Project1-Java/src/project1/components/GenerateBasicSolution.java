package project1.components;

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

	@Override
	public RDFDocument generate(RDFDocument docA, RDFDocument docB) {

		RDFDocument transformation = new RDFDocument();

		for (RDFFact factA : docA.getFacts()) {
			RDFFact factB = docB.find(factA.getSubject(), factA.getPredicate());
			State state;
			if (factB != null) {
				factB = docB.find(factA.getSubject(), factA.getPredicate());
				state = factA.getObject().equals(factB.getObject()) ? State.same
						: State.different;
				if (state == State.different
						&& Helper.isNumeric(factA.getObject())) {
					if (Helper.toNumber(factA.getObject()) < Helper
							.toNumber(factB.getObject())) {
						state = State.increase;
					} else {
						state = State.decrease;
					}
				}
			} else {
				state = State.missing;
			}
			transformation.addFact(new RDFXFact(factA.getSubject(), factA
					.getPredicate(), factA.getObject(), factB != null ? factB
					.getObject() : null, state));
		}

		for (RDFFact factB : docB.getFacts()) {
			// Only looking for factB that doesn't have a corresponding factA
			if (docA.find(factB.getSubject(), factB.getPredicate()) == null) {
				transformation.addFact(new RDFXFact(factB.getSubject(), factB
						.getPredicate(), null, factB.getObject(), State.added));
			}
		}

		return transformation;
	}

	@Override
	public List<RDFDocument> generate(RDFDocument docA, RDFDocument docX,
			Memory memory) {

		List<RDFDocument> solutions = new ArrayList<RDFDocument>();
		solutions.add(new RDFDocument()); // seed solutions

		for (RDFFact fact : docX.getFacts()) {
			RDFXFact factX = (RDFXFact) fact;
			RDFFact factA = docA.find(factX.getSubject(), factX.getPredicate());
			RDFFact factB;
			List<String> values;
			List<RDFFact> facts;
			switch (factX.getState()) {
			case same:
				add(solutions, factA);
				break;
			case different:
				facts = new ArrayList<RDFFact>();
				values = memory.recall(factX.getPredicate());
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
			case increase:
			case decrease:
				if (factA != null) {
					try {
						facts = new ArrayList<RDFFact>();
						int valA = Integer.parseInt(factA.getObject());
						values = memory.recall(factX.getPredicate());
						for (String value : values) {
							int val = Integer.parseInt(value);
							if ((factX.getState() == State.increase && val > valA)
									|| (factX.getState() == State.decrease && val < valA)) {
								factB = new RDFFact(factX.getSubject(),
										factX.getPredicate(), value);
								facts.add(factB);
							}
						}
						solutions = add(solutions, facts);
					} catch (NumberFormatException nfe) {
						//
					}
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
