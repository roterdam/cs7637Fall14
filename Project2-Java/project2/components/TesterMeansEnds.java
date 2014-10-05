package project2.components;

import java.util.ArrayList;
import java.util.List;

public class TesterMeansEnds implements Tester {

	@SuppressWarnings("unused")
	private Brain brain;

	@SuppressWarnings("unused")
	private TesterMeansEnds() {
	}

	public TesterMeansEnds(Brain brain) {
		this.brain = brain;
	}

	public int distance(List<RDFFact> factsA, List<RDFFact> factsB) {
		List<String> includeInTest = new ArrayList<String>();
		includeInTest.add("shape");
		includeInTest.add("size");
		includeInTest.add("fill");
		includeInTest.add("angle");
		int distance = 0;
		for (RDFFact factA : factsA) {
			if (includeInTest.contains(factA.getPredicate())) {
				RDFFact factB = RDFFact.find(factsB, factA.getPredicate());
				if (factB != null) {
					if (factA.getObject() != null && !factA.getObject().equals(factB.getObject())) {
						distance++;
					}
				} else {
					distance++;
				}
			}
		}

		return distance;
	}
}
