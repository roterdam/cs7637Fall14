package project1.components;

public class Answer implements Comparable<Answer> {

	private String solutionChosen;
	private int pct;

	@SuppressWarnings("unused")
	private Answer() {
	}

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

	@Override
	public int compareTo(Answer o) {
		if (this.getPct() < o.getPct()) {
			return -1;
		} else if (this.getPct() > o.getPct()) {
			return 1;
		} else {
			return 0;
		}
	}

}
