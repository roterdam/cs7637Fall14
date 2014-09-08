package project1.components;

import java.util.HashMap;

import project1.RavensProblem;

public class GenerateAndTestMethod implements Method {

	private Brain brain;

	@SuppressWarnings("unused")
	private GenerateAndTestMethod() {
	}

	public GenerateAndTestMethod(Brain brain) {
		this.brain = brain;
	}

	@Override
	public Answer solveProblem(RavensProblem problem) {
		HashMap<String, RDFDocument> rdfProblem = Converter.convert(problem);
		Generator transformer = brain.getGeneratorFactory().create(GeneratorFactory.Type.BasicTransformation);
		RDFDocument docABX = transformer.generate(rdfProblem.get("A"), rdfProblem.get("B"));
		System.out.println("A...\n"+rdfProblem.get("A").toString());
		System.out.println("B...\n"+rdfProblem.get("B").toString());
		System.out.println("ABX...\n"+docABX.toString());
		Answer answer = new Answer("1", 100);
		return answer;
	}

}
