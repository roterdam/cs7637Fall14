package project1.components;

import java.util.List;

public interface Generator {

	public RDFDocument generate(RDFDocument docA, RDFDocument docB);
	
	public List<RDFDocument> generate(RDFDocument docA, RDFDocument docX, Memory memory);
	
}
