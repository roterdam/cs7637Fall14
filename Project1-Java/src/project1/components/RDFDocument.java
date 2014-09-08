package project1.components;

import java.util.ArrayList;
import java.util.List;

public class RDFDocument {

	private List<RDFFact> facts;

	public RDFDocument() {
		facts = new ArrayList<RDFFact>();
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
	
	public RDFFact find(String subject, String predicate) throws NotFoundException {
		List<RDFFact> foundFacts = new ArrayList<RDFFact>();
		for (RDFFact fact : facts) {
			if (fact.getSubject().equals(subject) && fact.getPredicate().equals(predicate)) {
				return fact;
			}
		}
		throw new NotFoundException();
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
}
