package project1.attributes;

public class Shape implements Attribute<Shape> {

	private ShapeType type;
	private int sides;
	
	@SuppressWarnings("unused")
	private Shape() {}
	
	public Shape(String name) {
		type = ShapeType.find(name);
		sides = type.getSides();
	}
	
	public String toString() {
		return "shape:"+type.name()+"-"+sides;
	}
	public ShapeType getType() {
		return type;
	}

	public void setType(ShapeType type) {
		this.type = type;
	}

	// Allow setting sides in case shape type is unknown
	public int getSides() {
		return sides;
	}

	public void setSides(int sides) {
		this.sides = sides;
	}

	@Override
	public int matchTo(Shape o) {
		if (this == o) return 100;
		if (getSides() == o.getSides()) return 90;
		if (getSides() * 2 == o.getSides()) return 50;
		if (getSides()/2 == o.getSides()) return 50;
		
		return 0;
	}

	public static boolean canLoad(String name) {
		return name.equals("shape");
	}
}
