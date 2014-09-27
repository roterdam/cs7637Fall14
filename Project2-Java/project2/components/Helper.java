package project2.components;

import java.util.List;

public class Helper {

	public static boolean isNumeric(String str)  
	{  
	  try  
	  {  
	    int d = Integer.parseInt(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
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
	
}
