package project1.attributes;

import java.util.Arrays;
import java.util.List;

public class RelativeLocation implements Attribute<RelativeLocation> {

	private List<String> target;
	private RelativeDirection relativeDirection;

	@SuppressWarnings("unused")
	private RelativeLocation() {
	}

	public RelativeLocation(String value) {
		String[] values = value.split(":");
		this.relativeDirection = RelativeDirection.find(values[0]);
		this.target = Arrays.asList(values[1].split(","));
	}

	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("<relativeLocation>" + relativeDirection.name() + ":");
		boolean first = true;
		for (String name : target) {
			if (!first) {
				buf.append(",");
			}
			buf.append(name);
		}
		return buf.toString();
	}

	public static boolean canLoad(String name) {
		return (RelativeDirection.find(name) != RelativeDirection.unknown);
	}

	@Override
	public int matchTo(RelativeLocation o) {
		if (relativeDirection == o.getRelativeDirection()) {
			return 100;
		} else {
			return 0;
		}
	}

	public List<String> getTarget() {
		return target;
	}

	public RelativeDirection getRelativeDirection() {
		return relativeDirection;
	}

}
