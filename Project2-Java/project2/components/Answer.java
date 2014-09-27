package project2.components;

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

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 7 * hash + this.solutionChosen.hashCode();
		hash = 7 * hash + this.pct;
		return hash;
	}

	@Override
	public boolean equals(Object o) {
		Answer obj = (Answer) o;
		return (this.solutionChosen.equals(obj.getSolutionChosen()) && this.pct == obj
				.getPct());
	}

	public String toString() {
		return "Solution: " + solutionChosen + " @ " + pct;
	}
}
