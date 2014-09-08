package project1.components;

import java.util.HashMap;
import project1.RavensAttribute;
import project1.RavensFigure;
import project1.RavensObject;
import project1.RavensProblem;

public class Converter {

	public static HashMap<String, RDFDocument> convert(RavensProblem problem) {
		HashMap<String, RDFDocument> rdfProblem = new HashMap<String, RDFDocument>();
		
		for (String name : problem.getFigures().keySet()) {
			RavensFigure figure = problem.getFigures().get(name);
			RDFDocument document = new RDFDocument();
			rdfProblem.put(name, document);
			for (RavensObject object : figure.getObjects()) {
				for (RavensAttribute ravensAttribute : object.getAttributes()) {
					RDFFact fact = new RDFFact(object.getName(), ravensAttribute.getName(), ravensAttribute.getValue());
					document.addFact(fact);
				}
			}
		}
		
		return rdfProblem;
	}
}
