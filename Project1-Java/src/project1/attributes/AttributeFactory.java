package project1.attributes;

import project1.RavensAttribute;

public class AttributeFactory {

	public static Attribute buildAttribute(RavensAttribute ravensAttribute) {
		String name = ravensAttribute.getName();
		String value = ravensAttribute.getValue();
		
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
