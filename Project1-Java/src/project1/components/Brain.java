package project1.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import project1.RavensAttribute;
import project1.RavensFigure;
import project1.RavensObject;
import project1.RavensProblem;

import project1.attributes.*;

public class Brain {

	private HashMap<String, Object> memory = new HashMap<String, Object>();
	private MethodFactory methodFactory;
	private GeneratorFactory generatorFactory;
	private TesterFactory testerFactory;

	public Brain() {
		init();
	}

	private void init() {
		this.methodFactory = new MethodFactory(this);
		this.generatorFactory = new GeneratorFactory(this);
		this.testerFactory = new TesterFactory(this);
	}

	public String solveProblem(RavensProblem problem) {
		// Load data from figures into memory (including all possible answers)
		//loadProblemIntoMemory(problem);
		//dumpMemory("object:" + problem.getName() + ">");

		// Try solving problem using Generate And Test
		Method method = methodFactory.create(MethodFactory.Type.GenerateAndTest);
		Answer answer = method.solveProblem(problem);
		
		String correctAnswer = problem.checkAnswer(answer.getSolutionChosen());
		//System.out.println("Given answer = "+answer.getSolutionChosen()+"; Correct answer = "+correctAnswer);
		
		return answer.getSolutionChosen();
	}

	@SuppressWarnings("unused")
	private void loadProblemIntoMemory(RavensProblem problem) {
		// Remember problem
		// List<RavensProblem> problems = (List<RavensProblem>) memory
		// .get("problems:all");
		// if (problems == null) {
		// problems = new ArrayList<RavensProblem>();
		// problems.add(problem);
		// memory.put(problem.getProblemType(), "problems:all");
		// } else {
		// if (!problems.contains(problem)) {
		// problems.add(problem);
		// }
		// }

		// Remember that problem is of this type
		// List<RavensProblem> byType = (List<RavensProblem>) memory
		// .get("problems:" + problem.getProblemType());
		// if (byType == null) {
		// byType = new ArrayList<RavensProblem>();
		// byType.add(problem);
		// memory.put("problems:" + problem.getProblemType(), byType);
		// } else {
		// if (!byType.contains(problem)) {
		// byType.add(problem);
		// }
		// }

		for (String name : problem.getFigures().keySet()) {
			RavensFigure figure = problem.getFigures().get(name);
			for (RavensObject object : figure.getObjects()) {
				List<Attribute> attributes = new ArrayList<Attribute>();
				for (RavensAttribute ravensAttribute : object.getAttributes()) {
					attributes.add(AttributeFactory
							.buildAttribute(ravensAttribute));
				}
				memory.put("object:" + problem.getName() + ">" + name + ">"
						+ object.getName(), attributes);
			}
		}

	}

	@SuppressWarnings("unused")
	private void dumpMemory(String startsWith) {

		SortedSet<String> names = new TreeSet((Set<String>) memory.keySet());
		for (String name : names) {
			if (name.startsWith(startsWith)) {
				System.out.println(name);
				List<Attribute> attributes = (List<Attribute>) memory.get(name);
				for (Attribute attribute : attributes) {
					System.out.println(attribute.toString());
				}
			}
		}

	}

	public MethodFactory getMethodFactory() {
		return methodFactory;
	}
	
	public GeneratorFactory getGeneratorFactory() {
		return generatorFactory;
	}

	
	public TesterFactory getTesterFactory() {
		return testerFactory;
	}

	public void remember(String key, Object value) {
		memory.put(key, value);
	}
}
