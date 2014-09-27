package project2.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import project2.RavensAttribute;
import project2.RavensFigure;
import project2.RavensObject;
import project2.RavensProblem;

public class Converter {

	public static HashMap<String, RDFDocument> convert(RavensProblem problem) {
		HashMap<String, RDFDocument> rdfProblem = new HashMap<String, RDFDocument>();

		for (String name : problem.getFigures().keySet()) {
			RavensFigure figure = problem.getFigures().get(name);
			RDFDocument document = new RDFDocument();
			rdfProblem.put(name, document);
			for (RavensObject object : figure.getObjects()) {
				for (RavensAttribute ravensAttribute : object.getAttributes()) {
					String value = ravensAttribute.getValue();
					// if (value.contains(",")) {
					// List<String> values = Arrays.asList(value.split(","));
					// for (String val : values) {
					// RDFFact fact = new RDFFact(object.getName(),
					// ravensAttribute.getName(), val);
					// document.addFact(fact);
					// }
					// } else {
					RDFFact fact = new RDFFact(object.getName(),
							ravensAttribute.getName(),
							ravensAttribute.getValue());
					document.addFact(fact);
					// }
				}
			}
		}

		return rdfProblem;
	}

	public static HashMap<String, RDFDocument> normalizeSubjects(
			HashMap<String, RDFDocument> srcProblem) {
		HashMap<String, RDFDocument> cnvProblem = new HashMap<String, RDFDocument>();

		for (String key : srcProblem.keySet()) {
			RDFDocument srcDocument = srcProblem.get(key);
			RDFDocument cnvDocument = new RDFDocument();
			List<String> subjects = srcDocument.getSubjects();
			for (RDFFact srcFact : srcDocument.getFacts()) {
				String cnvSubject = mapSubject(srcFact.getSubject(), subjects);
				String cnvObject = mapObject(srcFact.getObject(), subjects);

				RDFFact cnvFact = new RDFFact(cnvSubject,
						srcFact.getPredicate(), cnvObject);
				cnvDocument.addFact(cnvFact);
			}
			cnvProblem.put(key, cnvDocument);
		}
		return cnvProblem;
	}

	private static String mapSubject(String srcSubject, List<String> subjects) {
		int ndx = subjects.indexOf(srcSubject);
		if (ndx >= 0) {
			return ndx + "";
		} else {
			return srcSubject;
		}
	}

	private static String mapObject(String srcObject, List<String> subjects) {
		String newObject = null;
		for (String obj : srcObject.split(",")) {
			int ndx = subjects.indexOf(obj);
			if (ndx >= 0) {
				obj = ndx + "";
			}
			if (newObject == null) {
				newObject = obj;
			} else {
				newObject += "," + obj;
			}
		}
		return newObject;
	}

}
