package project2.components;

import java.util.HashMap;

public class Compartor {

	public static String findNearestNeighbor(
			HashMap<String, Double[]> subjectScores, Double[] scores) {
		String nearestSubject = null;
		double nearestDistance = 0;
		for (String subject : subjectScores.keySet()) {
			double distance = calculateDistance(subjectScores.get(subject),
					scores);
			if (nearestSubject == null || distance < nearestDistance) {
				nearestSubject = subject;
				nearestDistance = distance;
			}
		}

		return nearestSubject;
	}

	private static double calculateDistance(Double[] scoresA, Double[] scoresB) {
		double total = 0;
		for (int ndx = 0; ndx < scoresA.length; ndx++) {
			if (scoresA[ndx] != null) {
				total += Math.pow(scoresA[ndx]
						+ (scoresB[ndx] != null ? scoresB[ndx] : 0), 2);
			}
		}
		return Math.sqrt(total);
	}
}
