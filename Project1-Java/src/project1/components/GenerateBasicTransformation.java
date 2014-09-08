package project1.components;

public class GenerateBasicTransformation implements Generator {

	public static enum State {
		equal, notEqual, missing
	}

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
				state = factA.getObject().equals(factB.getObject()) ? State.equal
						: State.notEqual;
			} catch (NotFoundException nfe) {
				state = State.missing;
			}
			transformation.addFact(new RDFFact(factA.getSubject(), factA
					.getPredicate(), state.name()));
		}
		return transformation;
	}

}
