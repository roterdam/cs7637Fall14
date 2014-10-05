package project2.attributes;

public class Other implements Attribute<Other> {

	private String name;
	private String value;

	@SuppressWarnings("unused")
	private Other() {
	}

	public Other(String name) {
		String[] values = name.split(":");
		if (values != null && values.length > 0) {
			this.name = values[0];
			if (values.length > 1) {
				this.value = values[1];
			}
		}
	}

	public String toString() {
		return "other:" + name + "," + value;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	@Override
	public int matchTo(Other o) {
		if (name.equals(o.getName()) && value.equals(o.getScore())) {
			return 100;
		} else {
			return 0;
		}
	}

	@Override
	public double getScore() {
		return 0;
	}

	@Override
	public int getOrder() {
		return AttributeOrder.other.ordinal();
	}

}
