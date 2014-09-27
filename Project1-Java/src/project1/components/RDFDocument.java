package project1.components;

import java.util.ArrayList;
import java.util.List;

public class RDFDocument implements Cloneable {

	private List<RDFFact> facts;

	public RDFDocument() {
		facts = new ArrayList<RDFFact>();
	}
	
	public RDFDocument(List<RDFFact> facts) {
		this.facts = facts;
	}

	public List<RDFFact> getFacts() {
		return new ArrayList<RDFFact>(facts);
	}

	public void addFact(RDFFact fact) {
		if (fact != null) {
			facts.add(fact);
		}
	}
	
	public List<RDFFact> find(String subject) {
		List<RDFFact> foundFacts = new ArrayList<RDFFact>();
		for (RDFFact fact : facts) {
			if (fact.getSubject().equals(subject)) {
				foundFacts.add(fact);
			}
		}
		return foundFacts;
	}
	
	public RDFFact find(String subject, String predicate) {
		List<RDFFact> foundFacts = new ArrayList<RDFFact>();
		for (RDFFact fact : facts) {
			if (fact.getSubject().equals(subject) && fact.getPredicate().equals(predicate)) {
				return fact;
			}
		}
		return null;
	}
		
	public String toString() {
		StringBuffer buf = new StringBuffer();
		for (RDFFact fact : facts) {
			if (buf.length() > 0) {
				buf.append("\n");
			}
			buf.append(fact.toString());
		}
		return buf.toString();
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		List<RDFFact> facts = new ArrayList<RDFFact>();
		for (RDFFact fact : getFacts()) {
			facts.add((RDFFact)fact.clone());
		}
		return new RDFDocument(facts);
	}
	
	
}
