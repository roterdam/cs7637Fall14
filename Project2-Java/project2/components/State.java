package project2.components;

public enum State {

	unknown, same, different, delta, added, missing;
	
	public static State find(String name) {
		try {
		   return State.valueOf(name);
		} catch (IllegalArgumentException iae) {
			return unknown;
		}
	}
	
}
