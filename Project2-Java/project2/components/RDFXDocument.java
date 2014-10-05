package project2.components;

import java.util.ArrayList;
import java.util.List;

import project2.attributes.Attribute;
import project2.attributes.AttributeFactory;
import project2.attributes.Shape;
import project2.attributes.ShapeType;

public class RDFXDocument implements Cloneable {

	private String name;
	private List<RDFXFact> facts = new ArrayList<RDFXFact>();

	public RDFXDocument() {
		// facts = new ArrayList<RDFXFact>();
	}

	public RDFXDocument(List<RDFXFact> facts) {
		this.facts.addAll(facts);
	}

	public RDFXDocument(RDFXDocument docAX, RDFXDocument docBX) {
		this.name = docAX.getName() + "X"+docBX.getName();
		
		List<String> subjectsAX = docAX.getSubjects();
		List<String> subjectsBX = docBX.getSubjects();

		for (String subjectBX : subjectsBX) {
			List<RDFXFact> factsBX = docBX.find(subjectBX);
			if (factsBX.size() > 0) {
				RDFXFact factBX = factsBX.get(0);
				if (factBX.getState() == State.added) {
					if (subjectsAX.contains(subjectBX)) {
						int nxt = Helper.toNumber(subjectsAX.get(subjectsAX
								.size() - 1));
						String toSubject = ++nxt + "";
						changeSubject(toSubject, factsBX);
						subjectsAX.add(toSubject);
					}
				}
			}
		}

		loadFacts(docAX.getFacts(), docBX.getFacts());
		transformAddedFacts();
	}

	public RDFXDocument(List<RDFXFact> factsAX, List<RDFXFact> factsBX) {
		loadFacts(factsAX, factsBX);
	}

	private void changeSubject(String toSubject, List<RDFXFact> facts) {
		for (RDFXFact fact : facts) {
			fact.setSubject(toSubject);
		}
	}

	private void loadFacts(List<RDFXFact> factsAX, List<RDFXFact> factsBX) {
		this.facts.addAll(factsAX);
		for (RDFXFact factBX : factsBX) {
			RDFXFact factAX = find(factBX.getSubject(), factBX.getPredicate());
			switch (factBX.getState()) {
			case same:
				break;
			case different:
				if (factAX.getState() == State.same
						|| factAX.getState() == State.missing) {
					facts.remove(factAX);
					facts.add(factBX);
				} else {
					Attribute<?> attributeA = AttributeFactory.buildAttribute(
							factAX.getPredicate(), factAX.getObject());
					if (attributeA instanceof Shape) {
						Shape shapeA = (Shape) attributeA;
						Shape shapeB = (Shape) AttributeFactory.buildAttribute(
								factAX.getPredicate(), factAX.getObjectB());
						Shape shapeC = (Shape) AttributeFactory.buildAttribute(
								factAX.getPredicate(), factBX.getObjectB());
						int diffAB = shapeB.getSides() - shapeA.getSides();
						int diffAC = shapeC.getSides() - shapeA.getSides();
						int sides = shapeA.getSides() + (diffAB + diffAC);
						String shapeDName = ShapeType.find(sides).name();
						factAX.setObjectB(shapeDName);
					} else {
						factAX.setState(State.same);
					}
				}
				break;
			case missing:
				if (!(factAX.getState() == State.different)) {
					facts.remove(factAX);
					facts.add(factBX);
				}
				break;
			case added:
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
					} else if (factAX.getDelta() >= 0 && factBX.getDelta() >= 0) {
						factAX.setDelta(factAX.getDelta() + factBX.getDelta());
					} else if (factAX.getDelta() <= 0 && factBX.getDelta() <= 0) {
						factAX.setDelta(factAX.getDelta() + factBX.getDelta());
					} else if (factAX.getDelta() < factBX.getDelta()) {
						// rotate clockwise
						factAX.setDelta(factBX.getDelta() - factAX.getDelta());
					} else {
						// rotate counter-clockwise
						factAX.setDelta(factAX.getDelta() - factBX.getDelta());
					}
				}
				break;
			}
		}
	}

	private void transformAddedFacts() {
		for (RDFXFact fact : getFacts()) {
			if (fact.getState() == State.added) {
				if (fact.getPredicate().equals("angle")) {
					RDFXFact xfact = findWithState(fact.getPredicate(),
							State.delta);
					if (xfact != null) {
						fact.setObjectB(xfact.getDelta()+"");
					}
				} else {
					RDFXFact xfact = findWithState(fact.getPredicate(),
							State.different);
					if (xfact != null) {
						fact.setObjectB(xfact.getObjectB());
					}
				}
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

	public RDFXFact findWithState(String predicate, State state) {
		for (RDFXFact fact : facts) {
			if (fact.getPredicate().equals(predicate)
					&& fact.getState() == state) {
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
