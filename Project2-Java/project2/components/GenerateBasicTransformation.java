package project2.components;

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

	public RDFXDocument generate(RDFDocument docA, RDFDocument docB) {

		RDFXDocument transformation = new RDFXDocument();

		for (RDFFact factA : docA.getFacts()) {
			RDFFact factB = docB.find(factA.getSubject(), factA.getPredicate());
			State state;
			int delta = 0;
			if (factB != null) {
				factB = docB.find(factA.getSubject(), factA.getPredicate());
				state = factA.getObject().equals(factB.getObject()) ? State.same
						: State.different;
				if (state == State.different
						&& Helper.isNumeric(factA.getObject())) {
					int valA = Helper.toNumber(factA.getObject());
					int valB = Helper.toNumber(factB.getObject());

					state = State.delta;
					delta = valB - valA;
				}
			} else {
				state = State.missing;
			}
			transformation.addFact(new RDFXFact(factA.getSubject(), factA
					.getPredicate(), factA.getObject(), factB != null ? factB
					.getObject() : null, state, delta));
		}

		for (RDFFact factB : docB.getFacts()) {
			// Only looking for factB that doesn't have a corresponding factA
			if (docA.find(factB.getSubject(), factB.getPredicate()) == null) {
				transformation.addFact(new RDFXFact(factB.getSubject(), factB
						.getPredicate(), null, factB.getObject(), State.added,
						0));
			}
		}

		return transformation;
	}

}
