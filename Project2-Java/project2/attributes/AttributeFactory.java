package project2.attributes;

public class AttributeFactory {

	public static Attribute<?> buildAttribute(String name, String value) {
		
		if (Shape.canLoad(name)) {
			return new Shape(value);
		}
		
		if (Fill.canLoad(name)) {
			return new Fill(value);
		}
		
		if (Size.canLoad(name)) {
			return new Size(value);
		}
		
		if (RelativeLocation.canLoad(name)) {
			return new RelativeLocation(name+":"+value);
		}
		
		if (Angle.canLoad(name)) {
			return new Angle(value);
		}
		
		return new Other(name+":"+value);
	}
}
