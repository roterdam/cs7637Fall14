package project2.attributes;

public enum SizeType {

	unknown(0), small(25), medium(50), large(100);
	
	int pct;
	
	SizeType(int pct) {
		this.pct = pct;
	}

	public int getPct() {
		return pct;
	}
	
	public static SizeType find(String name) {
		
		for (SizeType type : SizeType.values()) {
			if (type.name().equals(name)) return type;
		}
		
		return SizeType.unknown;
	}

}
