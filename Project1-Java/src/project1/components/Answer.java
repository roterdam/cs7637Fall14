package project1.components;

public class Answer {

	private String solutionChosen;
	private int pct;
	
	private Answer() {}
	
	public Answer(String solutionChosen, int pct) {
		this.solutionChosen = solutionChosen;
		this.pct = pct;
	}

	public String getSolutionChosen() {
		return solutionChosen;
	}

	public int getPct() {
		return pct;
	}
	
}
