package project1.components;

public class MethodFactory {

	public static enum Type {
		GenerateAndTest
	}

	private Brain brain;
	
	private MethodFactory() {}
	
	public MethodFactory(Brain brain) {
		this.brain = brain;
	}
	
	public Method create(Type type) {

		switch (type) {
		case GenerateAndTest:
			return new GenerateAndTestMethod(brain);
		default:
			return null;
		}
	}
}
