package project2.components;

import java.util.ArrayList;
import java.util.List;

public class RDFXDocument implements Cloneable {

	private List<RDFXFact> facts = new ArrayList<RDFXFact>();

	public RDFXDocument() {
		// facts = new ArrayList<RDFXFact>();
	}

	public RDFXDocument(List<RDFXFact> facts) {
		this.facts.addAll(facts);
	}

	public RDFXDocument(List<RDFXFact> factsAX, List<RDFXFact> factsBX) {
		this.facts.addAll(factsAX);
		for (RDFXFact factBX : factsBX) {
			RDFXFact factAX = find(factBX.getSubject(), factBX.getPredicate());
			switch (factBX.getState()) {
			case same:
				break;
			case different:
				facts.remove(factAX);
				facts.add(factBX);
				break;
			case delta:
				// factAX.setDelta(factAX.getDelta() + factBX.getDelta());
				// if (factAX.getPredicate().equals("angle")) {
				// if (factAX.getDelta() >= 360) {
				// factAX.setDelta(factAX.getDelta() - 360);
				// } else if (factAX.getDelta() < 0) {
				// factAX.setDelta(factAX.getDelta() + 360);
				// }
				// }

				if (factAX.getPredicate().equals("angle")) {
					factAX.setState(State.delta);
					int angleA = Helper.toNumber(factAX.getObject());
					int angleB = angleA + factAX.getDelta();
					int angleC = angleA + factBX.getDelta();

					if (Math.abs(angleB - angleC) == 180) {
						// if (angleA + angleB + angleC == 270) {
						// center fold
						factAX.setDelta(180);
					} else if (factAX.getDelta() + factBX.getDelta() == 0) {
						factAX.setDelta(0);
					} else if (factAX.getDelta() < factBX.getDelta()) {
						// rotate clockwise
						factAX.setDelta(factBX.getDelta() - factAX.getDelta());
					} else {
						// rotate counter-clockwise
						factAX.setDelta(factAX.getDelta() - factBX.getDelta());
					}
				}
				break;
			case added:
				facts.add(factBX);
				break;
			case missing:
				facts.add(factBX);
			}
		}
	}

	public List<RDFXFact> getFacts() {
		return new ArrayList<RDFXFact>(facts);
	}

	public void addFact(RDFXFact fact) {
		if (fact != null) {
			facts.add(fact);
		}
	}

	public List<RDFXFact> find(String subject) {
		List<RDFXFact> foundFacts = new ArrayList<RDFXFact>();
		for (RDFXFact fact : facts) {
			if (fact.getSubject().equals(subject)) {
				foundFacts.add(fact);
			}
		}
		return foundFacts;
	}

	public RDFXFact find(String subject, String predicate) {
		List<RDFXFact> foundFacts = new ArrayList<RDFXFact>();
		for (RDFXFact fact : facts) {
			if (fact.getSubject().equals(subject)
					&& fact.getPredicate().equals(predicate)) {
				return fact;
			}
		}
		return null;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer();
		for (RDFXFact fact : facts) {
			if (buf.length() > 0) {
				buf.append("\n");
			}
			buf.append(fact.toString());
		}
		return buf.toString();
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		List<RDFXFact> facts = new ArrayList<RDFXFact>();
		for (RDFXFact fact : getFacts()) {
			facts.add((RDFXFact) fact.clone());
		}
		return new RDFXDocument(facts);
	}

	public List<String> getSubjects() {
		List<String> subjects = new ArrayList<String>();
		for (RDFXFact fact : facts) {
			if (!subjects.contains(fact.getSubject())) {
				subjects.add(fact.getSubject());
			}
		}
		return subjects;
	}
}
