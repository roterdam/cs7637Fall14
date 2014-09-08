package project1.attributes;

public class Angle implements Attribute<Angle> {

	private int angle;
	
	@SuppressWarnings("unused")
	private Angle() {}
	
	public Angle(String value) {
		value = value.replaceAll("[^\\d.]", "");
		try {
			angle = Integer.parseInt(value);
		} catch (NumberFormatException nfe) {
			//
		}
	}
	
	public String toString() {
		return "angle:"+angle;
	}
	
	public int getAngle() {
		return angle;
	}

	public static boolean canLoad(String name) {
		return name.equals("angle");
	}

	@Override
	public int matchTo(Angle o) {
		if (getAngle() == o.getAngle()) {
			return 100;
		} else {
			return 0;
			// TODO - return some percentage based on difference
		}
	}

}
