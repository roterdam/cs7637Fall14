package project1.transform;

public enum State {

	deleted(0), changed(1), scaled(3), rotated(4), mirrored(5), unchanged(6);
	
	int value;
	
	State(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
}
