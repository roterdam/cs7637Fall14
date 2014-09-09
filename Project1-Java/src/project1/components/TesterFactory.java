package project1.components;

public class TesterFactory {

	private Brain brain;

	public static enum Type {
		Basic
	}

	@SuppressWarnings("unused")
	private TesterFactory() {
	}

	public TesterFactory(Brain brain) {
		this.brain = brain;
	}

	public Tester create(Type type) {

		switch (type) {
		case Basic: return new TesterBasic(brain);
		default: return null;
		}

	}
	
	
}
