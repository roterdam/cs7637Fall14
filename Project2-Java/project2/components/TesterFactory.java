package project2.components;

public class TesterFactory {

	private Brain brain;

	public static enum Type {
		Basic, MeansEnds, SubjectShifter
	}

	@SuppressWarnings("unused")
	private TesterFactory() {
	}

	public TesterFactory(Brain brain) {
		this.brain = brain;
	}

	public Tester create(Type type) {

		switch (type) {
		case Basic:
			return new TesterBasic(brain);
		case MeansEnds:
			return new TesterMeansEnds(brain);
		case SubjectShifter:
			return new TesterSubjectShifter(brain);
		default:
			return null;
		}

	}

}
