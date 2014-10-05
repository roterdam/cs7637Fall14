package project2.components;

import java.util.HashMap;
import java.util.List;

import project2.attributes.RPMAttribute;
import project2.components.TesterFactory.Type;

public class GenerateBasicTransformation implements Generator {

	List<String> exactMatchRequired = RPMAttribute.exactRequired();

	@SuppressWarnings("unused")
	private Brain brain;

	@SuppressWarnings("unused")
	private GenerateBasicTransformation() {
	}

	public GenerateBasicTransformation(Brain brain) {
		this.brain = brain;
	}

	public RDFXDocument generate(RDFDocument docA, RDFDocument docB) {

		RDFXDocument transformation = new RDFXDocument();

		docB = normalizeSubjectsUsingMeansEnds(docA, docB);

		for (RDFFact factA : docA.getFacts()) {
			State state = State.same;
			int delta = 0;
			RDFFact factB = null;
			if (exactMatchRequired.contains(factA.getPredicate())) {
				factB = docB.find(factA.getSubject(), factA.getPredicate(),
						factA.getObject());
				if (factB != null) {
					state = State.same;
				} else {
					state = State.missing;
				}
			} else {
				factB = docB.find(factA.getSubject(), factA.getPredicate());
				if (factB != null) {
					state = factA.getObject().equals(factB.getObject()) ? State.same
							: State.different;
					if (state == State.different
							&& factA.getPredicate().equals("angle")) {
						int valA = Helper.toNumber(factA.getObject());
						int valB = Helper.toNumber(factB.getObject());

						state = State.delta;
						delta = valB - valA;
					}
				} else {
					state = State.missing;
				}
			}
			transformation.addFact(new RDFXFact(factA.getSubject(), factA
					.getPredicate(), factA.getObject(), factB != null ? factB
					.getObject() : null, state, delta));
		}

		for (RDFFact factB : docB.getFacts()) {
			if (exactMatchRequired.contains(factB.getPredicate())) {
				if (docA.find(factB.getSubject(), factB.getPredicate(),
						factB.getObject()) == null) {
					transformation.addFact(new RDFXFact(factB.getSubject(),
							factB.getPredicate(), null, factB.getObject(),
							State.added, 0));
				}
			} else {
				// Only looking for factB that doesn't have a corresponding
				// factA
				if (docA.find(factB.getSubject(), factB.getPredicate()) == null) {
					transformation.addFact(new RDFXFact(factB.getSubject(),
							factB.getPredicate(), null, factB.getObject(),
							State.added, 0));
				}
			}
		}

		return transformation;
	}

	public RDFDocument normalizeSubjectsUsingMeansEnds(RDFDocument docA,
			RDFDocument docB) {
		List<String> subjectsA = docA.getSubjects();
		List<String> subjectsB = docB.getSubjects();

		if (subjectsA.size() == 0 || subjectsB.size() == 0) {
			return docB;
		}

		// If only one subject in A and B no changes needed
		if (subjectsA.size() <= 2 && subjectsA.size() == subjectsB.size()) {
			return docB;
		}

		TesterMeansEnds tester = (TesterMeansEnds) brain.getTesterFactory()
				.create(Type.MeansEnds);
		for (String subjectB : subjectsB) {
			List<RDFFact> factsB = docB.find(subjectB);
			String closestSubjectA = null;
			int closestDistance = 0;
			for (String subjectA : subjectsA) {
				List<RDFFact> factsA = docA.find(subjectA);
				int distance = tester.distance(factsB, factsA);
				if (closestSubjectA == null || distance < closestDistance) {
					closestSubjectA = subjectA;
					closestDistance = distance;
					if (closestDistance == 0) {
						break; // no need to continue since found exact match
					}
				}
			}

			if (!subjectB.equals(closestSubjectA)) {
				// Move facts labeled as closestSubjectA to subjectB
				List<RDFFact> factsBM = docB.find(closestSubjectA);
				for (RDFFact fact : factsBM) {
					fact.setSubject(subjectB);
				}
				for (RDFFact fact : factsB) {
					fact.setSubject(closestSubjectA);
				}
			}

			// Remove closest subjectA from list to test
			subjectsA.remove(closestSubjectA);
			// If one or none subjectA(s) to test, no need to continue
			if (subjectsA.size() <= 1) {
				break;
			}

		}

		return docB;
	}

	public RDFDocument normalizeSubjectsUsingNearestNeighbor(RDFDocument docA,
			RDFDocument docB) {
		List<String> subjectsA = docA.getSubjects();
		List<String> subjectsB = docB.getSubjects();

		// If only one subject in A and B no changes needed
		if (subjectsA.size() == 1 && subjectsB.size() == 1) {
			return docB;
		}

		HashMap<String, Double[]> subjectScores = new HashMap<String, Double[]>();
		for (String subjectA : subjectsA) {
			List<RDFFact> facts = docA.find(subjectA);
			subjectScores.put(subjectA, new ObjectCase(facts).getScores());
		}

		HashMap<String, String> nearestNeighbors = new HashMap<String, String>();
		for (String subjectB : subjectsB) {
			List<RDFFact> facts = docB.find(subjectB);
			Double[] scores = new ObjectCase(facts).getScores();
			nearestNeighbors.put(subjectB,
					Compartor.findNearestNeighbor(subjectScores, scores));
		}

		// TODO - create new RDFDocument to contain new facts
		for (String subjectB : nearestNeighbors.keySet()) {
			String nearestNeighbor = nearestNeighbors.get(subjectB);
			if (!subjectB.equals(nearestNeighbor)) {
				List<RDFFact> facts = docB.find(subjectB);
				for (RDFFact fact : facts) {
					fact.setSubject(nearestNeighbor);
				}
			}
		}

		return docB;
	}
}
