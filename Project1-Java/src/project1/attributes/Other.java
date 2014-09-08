package project1.attributes;

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
		if (name.equals(o.getName()) && value.equals(o.getValue())) {
			return 100;
		} else {
			return 0;
		}
	}

}
