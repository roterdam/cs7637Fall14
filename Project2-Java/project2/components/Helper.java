package project2.components;

import java.util.ArrayList;
import java.util.List;

public class Helper {

	public static boolean isNumeric(String str) {
		try {
			int d = Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	public static int toNumber(String str) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			return 0;
		}
	}

	public static int average(List<Integer> scores) {
		int score = 0;
		for (int value : scores) {
			score += value;
		}
		return score / scores.size();
	}

	public static List<List<String>> generatePerm(List<String> original) {
		if (original.size() == 0) {
			List<List<String>> result = new ArrayList<List<String>>();
			result.add(new ArrayList<String>());
			return result;
		}
		String firstElement = original.remove(0);
		List<List<String>> returnValue = new ArrayList<List<String>>();
		List<List<String>> permutations = generatePerm(original);
		for (List<String> smallerPermutated : permutations) {
			for (int index = 0; index <= smallerPermutated.size(); index++) {
				List<String> temp = new ArrayList<String>(smallerPermutated);
				temp.add(index, firstElement);
				returnValue.add(temp);
			}
		}
		return returnValue;
	}
}
