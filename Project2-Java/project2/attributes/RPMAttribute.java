package project2.attributes;

import java.util.ArrayList;
import java.util.List;

public enum RPMAttribute {

	shape("shape",false), 
	fill("fill",false),
	angle("angle",false),
	size("size",false),
	inside("inside",true),
	above("above",true),
	leftof("left-of",true),
	rightof("right-of",true),
	overlays("overlays",true);
	
	private boolean exact;
	private String name;
	
	RPMAttribute(String name, boolean exact) {
		this.name = name;
		this.exact = exact;
	}

	public boolean isExact() {
		return exact;
	}

	public String getName() {
		return name;
	}
	
	public static List<String> exactRequired() {
		List<String> exacts = new ArrayList<String>();
		for (RPMAttribute attribute : RPMAttribute.values()) {
			if (attribute.isExact()) {
				exacts.add(attribute.getName());
			}
		}
		return exacts;
	}
}
