package project1.attributes;

/**
 * Common shapes in order by their number of sides.
 * 
 * @author Scott Dickerson
 *
 */
public enum ShapeType {

	unknown(0),
	circle(1),
	line(2),
	triangle(3),
	trigon(3),
	quadrilateral(4),
	tetragon(4),
	square(4),
	diamond(4),
	rectangle(4),
	pentagon(5),
	halfarrow(5),
	hexagon(6),
	heptagon(7),
	septagon(7),
	arrow(7),
	octagon(8),
	nonagon(9),
	enneagon(9),
	decagon(10),
	hendecagon(11),
	undecagon(11),
	dodecagon(12),
	plus(12),
	triskaidecagon(13),
	tetrakaidecagon(14),
	pentadecagon(15),
	hexakaidecagon(16),
	heptadecagon(17),
	octakaidecagon(18),
	enneadecagon(19),
	icosagon(20);

	int sides;
	
	ShapeType(int sides) {
		this.sides = sides;
	}
	
	
	public int getSides() {
		return sides;
	}


	public static ShapeType find(String name) {
		name = name.replaceAll("[^a-zA-Z]", "");
		name = name.toLowerCase();
		for (ShapeType type : ShapeType.values()) {
			if (type.name().equals(name)) return type;
		}
		
		return ShapeType.unknown;
	}
}
