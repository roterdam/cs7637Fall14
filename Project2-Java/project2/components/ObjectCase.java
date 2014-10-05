package project2.components;

import java.util.List;

import project2.attributes.Attribute;
import project2.attributes.AttributeFactory;
import project2.attributes.AttributeOrder;

public class ObjectCase implements Case {

	private Double[] scores = new Double[AttributeOrder.values().length];

	public ObjectCase(List<RDFFact> facts) {

		for (RDFFact fact : facts) {
			Attribute<?> attribute = AttributeFactory.buildAttribute(
					fact.getPredicate(), fact.getObject());
			scores[attribute.getOrder()] = attribute.getScore();
		}
	}

	@Override
	public Double[] getScores() {
		return scores;
	}

}
