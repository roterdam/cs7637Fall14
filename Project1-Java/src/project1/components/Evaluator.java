package project1.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Evaluator {

	public static List<Answer> findBest(List<Answer> answers) {
		Answer[] array = answers.toArray(new Answer[answers.size()]);
		Arrays.sort(array, Collections.reverseOrder());

		List<Answer> best = new ArrayList<Answer>();
		best.add(array[0]);
		for (int ndx = 1; ndx < array.length; ndx++) {
			if (array[ndx].getPct() == array[0].getPct()) {
				best.add(array[ndx]);
			} else {
				break;
			}
		}

		return best;
	}

}
