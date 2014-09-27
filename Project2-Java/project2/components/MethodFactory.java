package project2.components;

public class MethodFactory {

	public static enum Type {
		GenerateAndTest, GenerateAndTest2x2
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
		case GenerateAndTest2x2:
			return new GenerateAndTestMethod2x2(brain);
		default:
			return null;
		}
	}
}
