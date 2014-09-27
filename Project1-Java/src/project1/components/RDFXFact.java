package project1.components;

/**
 * This is an RDF fact that holds state of the transformation.
 * 
 * @author e8pigke
 * 
 */
public class RDFXFact extends RDFFact implements Cloneable {

	private State state;

	public RDFXFact(String subject, String predicate, String objectA,
			String objectB, State state) {
		super(subject, predicate, objectA != null ? objectA : "" + "\t"
				+ objectB != null ? objectB : "");
		this.state = state;
	}

	public String getObjectA() {
		String[] objects = getObject().split("\t");
		return objects[0].length() > 0 ? objects[0] : null;
	}

	public String getObjectB() {
		if (getObject().contains("\t")) {
			String[] objects = getObject().split("\t");
			if (objects.length > 1) {
				return objects[1].length() > 0 ? objects[1] : null;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public State getState() {
		return state;
	}

	public String toString() {
		return super.toString() + ":" + state;
	}
	
	public Object clone() {
		return new RDFXFact(getSubject(), getPredicate(), getObjectA(), getObjectB(), getState());
	}
}
