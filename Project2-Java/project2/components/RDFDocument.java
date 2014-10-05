package project2.components;

import java.util.ArrayList;
import java.util.List;

public class RDFDocument implements Cloneable {

	private String name;
	private List<RDFFact> facts = new ArrayList<RDFFact>();

	public RDFDocument() {
		// facts = new ArrayList<RDFFact>();
	}

	public RDFDocument(List<RDFFact> facts) {
		this.facts.addAll(facts);
	}

	public RDFDocument(List<RDFFact> factsA, List<RDFFact> factsB) {
		this.facts.addAll(factsA);
		for (RDFFact factB : factsB) {
			RDFFact factA = find(factB.getSubject(), factB.getPredicate());
			if (factA instanceof RDFXFact && factB instanceof RDFXFact) {
				RDFXFact factAX = (RDFXFact) factA;
				RDFXFact factBX = (RDFXFact) factB;
				switch (factBX.getState()) {
				case same:
					break;
				case different:
					facts.remove(factA);
					facts.add(factB);
					break;
				case delta:
					factAX.setDelta(factAX.getDelta() + factBX.getDelta());
					if (factAX.getPredicate().equals("angle")) {
						if (factAX.getDelta() >= 360) {
							factAX.setDelta(factAX.getDelta() - 360);
						} else if (factAX.getDelta() < 0) {
							factAX.setDelta(factAX.getDelta() + 360);
						}
					}
					break;
				case added:
					facts.add(factB);
					break;
				case missing:
					facts.add(factB);
				}
			}
		}
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
		for (RDFFact fact : facts) {
			if (fact.getSubject().equals(subject)
					&& fact.getPredicate().equals(predicate)) {
				return fact;
			}
		}
		return null;
	}

	public RDFFact find(String subject, String predicate, String object) {
		for (RDFFact fact : facts) {
			if (fact.getSubject().equals(subject)
					&& fact.getPredicate().equals(predicate)
					&& fact.getObject().equals(object)) {
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
			facts.add((RDFFact) fact.clone());
		}
		return new RDFDocument(facts);
	}

	public List<String> getSubjects() {
		List<String> subjects = new ArrayList<String>();
		for (RDFFact fact : facts) {
			if (!subjects.contains(fact.getSubject())) {
				subjects.add(fact.getSubject());
			}
		}
		return subjects;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
