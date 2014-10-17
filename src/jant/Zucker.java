package jant;

public class Zucker extends NoneLivingObject {

	public Zucker(double x, double y) {
		super(x, y);
	}

	private int todesdatum = 0;
	private int menge = 20;

	public void setTodesdatum(int todesdatum) {
		this.todesdatum = todesdatum;
	}

	public int getTodesdatum() {
		return todesdatum;
	}

	public void setMenge(int menge) {
		this.menge = menge;
	}

	// Ladung entfernt
	public void weniger() {
		menge--;
	}

	// Auskunft
	public int getMenge() {
		return menge;
	}

}
