package project2.components;

import java.util.List;

import project2.components.TesterFactory.Type;

public class TesterSubjectShifter implements Tester {

	TesterBasic basicTester;

	@SuppressWarnings("unused")
	private Brain brain;

	@SuppressWarnings("unused")
	private TesterSubjectShifter() {
	}

	public TesterSubjectShifter(Brain brain) {
		this.brain = brain;
		this.basicTester = (TesterBasic) brain.getTesterFactory().create(
				Type.Basic);
	}

	public int score(RDFDocument docA, RDFDocument docB) {
		// In case no facts to compare
		if (docA.getFacts().size() == 0) {
			return docB.getFacts().size() == 0 ? 100 : 0;
		}

		int highScore = basicTester.score(docA, docB);
		if (highScore == 100) {
			return highScore;
		}

		List<String> subjectsA = docA.getSubjects();

		List<List<String>> permsA = Helper.generatePerm(subjectsA);
		for (List<String> permA : permsA) {
			RDFDocument doc = new RDFDocument();
			int cnt = 0;
			for (String subject : permA) {
				List<RDFFact> facts = docA.find(subject);
				String cntSubject = cnt + "";
				for (RDFFact fact : facts) {
					doc.addFact(new RDFFact(cntSubject, fact.getPredicate(),
							fact.getObject()));
				}
				cnt++;
			}
			int score = basicTester.score(doc, docB);
			if (score == 100) {
				highScore = score;
				break;
			} else if (score > highScore) {
				highScore = score;
			}
		}

		return highScore;
	}

}
