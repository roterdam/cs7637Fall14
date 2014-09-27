package project1.components;

import java.util.List;

public class GenerateBasicTransformation implements Generator {

	@SuppressWarnings("unused")
	private Brain brain;

	@SuppressWarnings("unused")
	private GenerateBasicTransformation() {
	}

	public GenerateBasicTransformation(Brain brain) {
		this.brain = brain;
	}

	@Override
	public RDFDocument generate(RDFDocument docA, RDFDocument docB) {

		RDFDocument transformation = new RDFDocument();

		for (RDFFact factA : docA.getFacts()) {
			RDFFact factB = docB.find(factA.getSubject(), factA.getPredicate());
			State state;
			if (factB != null) {
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
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
