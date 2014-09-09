package project1.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
		Generator transformer = brain.getGeneratorFactory().create(
				GeneratorFactory.Type.BasicTransformation);
		Tester tester = brain.getTesterFactory().create(TesterFactory.Type.Basic);
		RDFDocument docABX = transformer.generate(rdfProblem.get("A"),
				rdfProblem.get("B"));
		//System.out.println("A...\n" + rdfProblem.get("A").toString());
		//System.out.println("B...\n" + rdfProblem.get("B").toString());
		System.out.println("ABX...\n" + docABX.toString());
		
		RDFDocument docC = rdfProblem.get("C");
		List<Answer> answers = new ArrayList<Answer>();
		for (int cnt=1; cnt <=6; cnt++) {
			RDFDocument docS = rdfProblem.get(cnt+"");
			int score = tester.score(docC, docABX, docS);
			//System.out.println("Solution "+cnt+"; score="+score);
			answers.add(new Answer(cnt+"", score));
		}
		
		// Get best answer
		List<Answer> bestAnswers = Evaluator.findBest(answers);
		
		if (bestAnswers.size() == 1) {
			return bestAnswers.get(0);
		} else {
			return new Answer("0",0);
		}

	}

	
}
