package project2.attributes;

public class Location implements Attribute<Location> {

	private boolean inQuadrant1;
	private boolean inQuadrant2;
	private boolean inQuadrant3;
	private boolean inQuadrant4;

	public Location() {
		//
	}

	public String toString() {
		return "location:" + (isInQuadrant1() ? "x" : ".")
				+ (isInQuadrant2() ? "x" : ".") + (isInQuadrant3() ? "x" : ".")
				+ (isInQuadrant4() ? "x" : ".");
	}

	public boolean isInQuadrant1() {
		return inQuadrant1;
	}

	public void setInQuadrant1(boolean quadrant1) {
		this.inQuadrant1 = quadrant1;
	}

	public boolean isInQuadrant2() {
		return inQuadrant2;
	}

	public void setInQuadrant2(boolean quadrant2) {
		this.inQuadrant2 = quadrant2;
	}

	public boolean isInQuadrant3() {
		return inQuadrant3;
	}

	public void setInQuadrant3(boolean quadrant3) {
		this.inQuadrant3 = quadrant3;
	}

	public boolean isInQuadrant4() {
		return inQuadrant4;
	}

	public void setInQuadrant4(boolean quadrant4) {
		this.inQuadrant4 = quadrant4;
	}

	public static boolean canLoad(String name) {
		return false;
	}

	@Override
	public int matchTo(Location o) {
		if (isInQuadrant1() == o.isInQuadrant1()
				&& isInQuadrant2() == o.isInQuadrant2()
				&& isInQuadrant3() == o.isInQuadrant3()
				&& isInQuadrant4() == o.isInQuadrant4()) {
			return 100;
		}

		// TODO - partial match

		return 0;
	}

	@Override
	public double getScore() {
		return ((inQuadrant1 ? 1 : 0) + (inQuadrant2 ? 1 : 0)
				+ (inQuadrant3 ? 1 : 0) + (inQuadrant4 ? 1 : 0)) / 4;
	}

	@Override
	public int getOrder() {
		return AttributeOrder.location.ordinal();
	}

}
