package project2.components;

import java.util.List;

public interface SolutionGenerator {

	public RDFDocument generate(RDFDocument docA, RDFDocument docB);
	
	public List<RDFDocument> generateSolution(RDFXDocument docX);

	public List<RDFDocument> generate(RDFDocument docA, RDFDocument docX, Memory memory);
	
	public List<RDFDocument> generate(RDFDocument docA, RDFXDocument docX);

}
