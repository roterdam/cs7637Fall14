package project1.components;

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
			State state;
			try {
				RDFFact factB = docB.find(factA.getSubject(),
						factA.getPredicate());
				state = factA.getObject().equals(factB.getObject()) ? State.same
						: State.different;
				if (state == State.different && Helper.isNumeric(factA.getObject())) {
					if (Helper.toNumber(factA.getObject()) < Helper.toNumber(factB.getObject())) {
						state = State.increase;
					} else {
						state = State.decrease;
					}
				}
			} catch (NotFoundException nfe) {
				state = State.missing;
			}
			transformation.addFact(new RDFFact(factA.getSubject(), factA
					.getPredicate(), state.name()));
		}
		return transformation;
	}

}
