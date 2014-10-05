package project2.attributes;

public enum RelativeDirection {
	
	unknown,
	inside, 
	leftOf, 
	rightOf,
	overlays,
	above, 
	below;

	public static RelativeDirection find(String name) {
		
		for (RelativeDirection type : RelativeDirection.values()) {
			if (name.contains(type.name())) return type;
		}
		
		return RelativeDirection.unknown;
	}

}
