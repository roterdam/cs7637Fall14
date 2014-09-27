package project1.components;

import java.util.HashMap;
import java.util.List;

import project1.RavensProblem;

public interface Method {

	public Answer solveProblem(HashMap<String, RDFDocument> rdfProblem);
	
}
