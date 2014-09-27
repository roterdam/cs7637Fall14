package project1.components;

/**
 * RDF (Resource Description Framework) can be defined in three simple rules:
 *
 * A fact is expressed as a triple of the form (Subject, Predicate, Object). It's like a little English sentence.
 * Subjects, predicates, and objects are names for entities, whether concrete or abstract, in the real world. 
 * 
 * Names are either:
 *  
 * 1) global and refer to the same entity in any RDF document in which they appear, or 
 * 2) local, and the entity it refers to cannot be directly referred to outside of the RDF document.
 * 
 * Objects can also be text values, called literal values.
 * 
 * Each line below is a fact:
 *
 * :john    a           :Person .
 * :john    :hasMother  :susan .
 *  
 * @author e8pigke
 *
 */
public class RDFFact implements Cloneable {

	private String subject;
	private String predicate;
	private String object;
	
	@SuppressWarnings("unused")
	private RDFFact() {}
	
	public RDFFact(String subject, String predicate, String object) {
		this.subject = subject;
		this.predicate = predicate;
		this.object = object;
	}

	public String getSubject() {
		return subject;
	}

	public String getPredicate() {
		return predicate;
	}

	public String getObject() {
		return object;
	}
	
	public String toString() {
		return ":"+subject+"  "+":"+predicate+"  "+":"+object;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new RDFFact(getSubject(), getPredicate(), getObject());
	}
	
	
}
