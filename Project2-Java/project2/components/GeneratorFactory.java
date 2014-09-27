package project2.components;

public class GeneratorFactory {

	@SuppressWarnings("unused")
	private Brain brain;

	public static enum Type {
		BasicSolution, BasicTransformation, DeltaSolution
	}

	@SuppressWarnings("unused")
	private GeneratorFactory() {
	}

	public GeneratorFactory(Brain brain) {
		this.brain = brain;
	}

	public Generator create(Type type) {

		switch (type) {
		case BasicTransformation:
			return new GenerateBasicTransformation(brain);
		case BasicSolution:
			return new GenerateBasicSolution(brain);
		case DeltaSolution:
			return new GenerateDeltaSolution(brain);
		default:
			return null;
		}

	}

}
