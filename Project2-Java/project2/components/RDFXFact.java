package project2.components;

/**
 * This is an RDF fact that holds state of the transformation.
 * 
 * @author e8pigke
 * 
 */
public class RDFXFact extends RDFFact implements Cloneable {

	private State state;
	private String objectB;
	private int delta;

	public RDFXFact(String subject, String predicate, String objectA,
			String objectB, State state, int delta) {
		super(subject, predicate, objectA);
		this.objectB = objectB;
		this.state = state;
		this.delta = delta;
	}

	public String getObjectA() {
		return getObject();
	}

	public String getObjectB() {
		return objectB;
	}

	public State getState() {
		return state;
	}

	
	public int getDelta() {
		return delta;
	}

	
	public void setState(State state) {
		this.state = state;
	}

	public void setDelta(int delta) {
		this.delta = delta;
	}

	public String toString() {
		return super.toString() + ":" + objectB + ":" + state + ":" + delta;
	}
	
	public Object clone() {
		return new RDFXFact(getSubject(), getPredicate(), getObjectA(), getObjectB(), getState(), getDelta());
	}
}
