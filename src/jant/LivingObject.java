package jant;

//TODO: Variablen als privat, wo möglich/nötig
public class LivingObject extends AllObject {

	// private int Lifepoints = 0;

	public LivingObject(double x, double y) {
		super(x, y);
	}

	public LivingObject(double x, double y, double r) {
		super(x, y, r);
	}

	public LivingObject(double x, double y, double r, double rv) {
		super(x, y, r, rv);
	}

	/*
	 * public int getLifepoints() { return Lifepoints; }
	 * 
	 * public void setLifepoints(int Lifepoints) { this.Lifepoints = Lifepoints;
	 * }
	 */

	// Haupt Methode die der Benutzer ueberschreiben kann
	protected void live() {

	}

}
