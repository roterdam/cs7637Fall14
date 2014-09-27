package project2.components;

import java.util.HashMap;
import java.util.List;

import project2.RavensProblem;

public interface Method {

	public Answer solveProblem(HashMap<String, RDFDocument> rdfProblem);
	
}
