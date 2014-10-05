package project2.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import project2.RavensProblem;
import project2.components.GeneratorFactory.Type;

public class GenerateAndTestMethod2x2 implements Method {

	private static int ACCEPT_GOAL = 100;

	private Brain brain;

	@SuppressWarnings("unused")
	private GenerateAndTestMethod2x2() {
	}

	public GenerateAndTestMethod2x2(Brain brain) {
		this.brain = brain;
	}

	@Override
	public Answer solveProblem(HashMap<String, RDFDocument> rdfProblem) {
		GenerateBasicTransformation transformer = (GenerateBasicTransformation) brain
				.getGeneratorFactory().create(
						GeneratorFactory.Type.BasicTransformation);
		TesterBasic basicTester = (TesterBasic) brain.getTesterFactory()
				.create(TesterFactory.Type.Basic);
		TesterSubjectShifter subjectShifterTester = (TesterSubjectShifter) brain
				.getTesterFactory().create(TesterFactory.Type.SubjectShifter);
		RDFXDocument docABX = transformer.generate(rdfProblem.get("A"),
				rdfProblem.get("B"));
		RDFXDocument docACX = transformer.generate(rdfProblem.get("A"),
				rdfProblem.get("C"));

//		System.out.println("A...\n" + rdfProblem.get("A").toString());
//		System.out.println("B...\n" + rdfProblem.get("B").toString());
//		System.out.println("C...\n" + rdfProblem.get("C").toString());
//		System.out.println("ABX...\n" + docABX.toString());
//		System.out.println("ACX...\n" + docACX.toString());

		List<RDFDocument> solutions = new ArrayList<RDFDocument>();
		List<Answer> answers = new ArrayList<Answer>();
		List<Answer> bestAnswers;
		Answer bestAnswer;

		// RDFDocument docB = rdfProblem.get("B");
		// RDFDocument docC = rdfProblem.get("C");
		//
		// Memory memory = new Memory();
		// memory.load(rdfProblem.values());
		//
		// GenerateBasicSolution generator = (GenerateBasicSolution) brain
		// .getGeneratorFactory().create(
		// GeneratorFactory.Type.BasicSolution);
		//
		// List<RDFDocument> solutionsB = generator.generate(docB, docABX,
		// memory);
		// List<RDFDocument> solutionsC = generator.generate(docC, docACX,
		// memory);
		//
		// solutions.addAll(solutionsB);
		// solutions.addAll(solutionsC);
		//
		// for (int cnt = 1; cnt <= 6; cnt++) {
		// RDFDocument docS = rdfProblem.get(cnt + "");
		// for (RDFDocument solution : solutions) {
		// int score = tester.score(solution, docS);
		// // System.out.println("Solution " + cnt + "; score=" + score);
		// answers.add(new Answer(cnt + "", score));
		// }
		// }
		//
		// // Get best answer
		// for (Answer answer : bestAnswers) {
		// System.out.println(answer.toString());
		// }
		// if (bestAnswers.size() == 1 && bestAnswers.get(0).getPct() >=
		// ACCEPT_GOAL) {
		// return bestAnswers.get(0);
		// }

		// Didn't find an adequate answer, so now try consolidating
		// transformations to generate solutions from docA
		RDFDocument docA = rdfProblem.get("A");

		// RDFXDocument docABCX = new RDFXDocument(docABX.getFacts(),
		// docACX.getFacts());
		RDFXDocument docABCX = new RDFXDocument(docABX, docACX);
//		System.out.println("ABCX...\n" + docABCX.toString());

		GenerateDeltaSolution deltaGenerator = (GenerateDeltaSolution) brain
				.getGeneratorFactory().create(Type.DeltaSolution);
		solutions = deltaGenerator.generate(docA, docABCX);

		// Test with Basic tester looking for an exact match
		answers = new ArrayList<Answer>();
		for (int cnt = 1; cnt <= 6; cnt++) {
			RDFDocument docS = rdfProblem.get(cnt + "");
			for (RDFDocument solution : solutions) {
				int score = basicTester.score(solution, docS);
				// System.out.println("Solution " + cnt + "; score=" + score);
				answers.add(new Answer(cnt + "", score));
			}
		}

		// Get best answer
		bestAnswers = Evaluator.findBest(answers);
		bestAnswer = bestAnswers.get(0);
		if (bestAnswer.getPct() == 100) {
			return bestAnswer;
		}

		// Test with SubjectShifter since didn't get an exact match
		answers = new ArrayList<Answer>();
		for (int cnt = 1; cnt <= 6; cnt++) {
			RDFDocument docS = rdfProblem.get(cnt + "");
			for (RDFDocument solution : solutions) {
				int score = subjectShifterTester.score(solution, docS);
				// System.out.println("Solution " + cnt + "; score=" + score);
				answers.add(new Answer(cnt + "", score));
			}
		}

		// Get best answer
		bestAnswers = Evaluator.findBest(answers);
		bestAnswer = bestAnswers.get(0);

		return bestAnswer;
	}
}
