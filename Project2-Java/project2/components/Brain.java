package project2.components;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import project2.RavensProblem;

public class Brain {

	public static final String MISSES2X1 = "MISSES2X1";
	public static final String MISSES2X2 = "MISSES2X2";
	private HashMap<String, List<Object>> memory = new HashMap<String, List<Object>>();
	private MethodFactory methodFactory;
	private GeneratorFactory generatorFactory;
	private TesterFactory testerFactory;
	private Date startTime;
	private Date endTime;

	public Brain() {
		init();
	}

	private void init() {
		startTime = new Date();
		this.methodFactory = new MethodFactory(this);
		this.generatorFactory = new GeneratorFactory(this);
		this.testerFactory = new TesterFactory(this);
	}

	public String solveProblem(RavensProblem problem) {
		String answer = null;
		if (problem.getProblemType().equals("2x1")) {
			answer = solve2x1Problem(problem);
		} else if (problem.getProblemType().equals("2x2")) {
			answer = solve2x2Problem(problem);
		} else {
			answer = null;
		}
		
		endTime = new Date();
		System.out.println("Elapsed time (ms): "+getElapsedTime());
		
		return answer;
	}

	public String solve2x1Problem(RavensProblem problem) {
		HashMap<String, RDFDocument> rdfProblem = null;
		Answer answer = null;

		rdfProblem = Converter.convert(problem);

		// Try solving problem using Generate And Test
		Method method = methodFactory
				.create(MethodFactory.Type.GenerateAndTest);
		answer = method.solveProblem(rdfProblem);

		String correctAnswer = problem.checkAnswer(answer.getSolutionChosen());
		if (!answer.getSolutionChosen().equals(correctAnswer)) {
			learn(MISSES2X1, problem);
		}
		return answer.getSolutionChosen();
	}

	public String solve2x2Problem(RavensProblem problem) {
		HashMap<String, RDFDocument> rdfProblem = null;
		Answer answer = null;

		//if (!problem.getName().endsWith("19")) return "0";
		
		rdfProblem = Converter.convert(problem);
		rdfProblem = Converter.normalizeSubjects(rdfProblem);

		// Try solving problem using Generate And Test
		Method method = methodFactory
				.create(MethodFactory.Type.GenerateAndTest2x2);
		answer = method.solveProblem(rdfProblem);

		String correctAnswer = problem.checkAnswer(answer.getSolutionChosen());
		if (!answer.getSolutionChosen().equals(correctAnswer)) {
			learn(MISSES2X2, problem);
		}
		return answer.getSolutionChosen();
	}

	// @SuppressWarnings("unused")
	// private void loadProblemIntoMemory(RavensProblem problem) {
	// // Remember problem
	// // List<RavensProblem> problems = (List<RavensProblem>) memory
	// // .get("problems:all");
	// // if (problems == null) {
	// // problems = new ArrayList<RavensProblem>();
	// // problems.add(problem);
	// // memory.put(problem.getProblemType(), "problems:all");
	// // } else {
	// // if (!problems.contains(problem)) {
	// // problems.add(problem);
	// // }
	// // }
	//
	// // Remember that problem is of this type
	// // List<RavensProblem> byType = (List<RavensProblem>) memory
	// // .get("problems:" + problem.getProblemType());
	// // if (byType == null) {
	// // byType = new ArrayList<RavensProblem>();
	// // byType.add(problem);
	// // memory.put("problems:" + problem.getProblemType(), byType);
	// // } else {
	// // if (!byType.contains(problem)) {
	// // byType.add(problem);
	// // }
	// // }
	//
	// for (String name : problem.getFigures().keySet()) {
	// RavensFigure figure = problem.getFigures().get(name);
	// for (RavensObject object : figure.getObjects()) {
	// List<Attribute> attributes = new ArrayList<Attribute>();
	// for (RavensAttribute ravensAttribute : object.getAttributes()) {
	// attributes.add(AttributeFactory
	// .buildAttribute(ravensAttribute));
	// }
	// memory.put("object:" + problem.getName() + ">" + name + ">"
	// + object.getName(), attributes);
	// }
	// }
	//
	// }

	// @SuppressWarnings("unused")
	// private void dumpMemory(String startsWith) {
	//
	// SortedSet<String> names = new TreeSet((Set<String>) memory.keySet());
	// for (String name : names) {
	// if (name.startsWith(startsWith)) {
	// System.out.println(name);
	// List<Attribute> attributes = (List<Attribute>) memory.get(name);
	// for (Attribute attribute : attributes) {
	// System.out.println(attribute.toString());
	// }
	// }
	// }
	//
	// }

	public MethodFactory getMethodFactory() {
		return methodFactory;
	}

	public GeneratorFactory getGeneratorFactory() {
		return generatorFactory;
	}

	public TesterFactory getTesterFactory() {
		return testerFactory;
	}

	public void learn(String key, Object value) {
		List<Object> values = (List<Object>) memory.get(key);
		if (values == null) {
			values = new ArrayList<Object>();
		}
		if (!values.contains(value)) {
			values.add(value);
		}
		memory.put(key, values);
	}

	public List<Object> recall(String key) {
		return memory.get(key);
	}
	
	public Date getStartTime() {
		return startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public long getElapsedTime() {
		return endTime.getTime() - startTime.getTime();
	}
}
