package jant;

public class Kaefer extends LivingObject {

	public double winkel;
	private int todesdatum = 0;
	private double geschwindigkeit = 0.60;
	private int aktuelleEnergie = 1500;

	// Konstruktor
	public Kaefer(double x, double y, double winkel) {
		super(x, y);
		this.winkel = winkel;
	}

	public Kaefer(double x, double y, double r, double winkel) {
		super(x, y, r);
		this.winkel = winkel;
	}

	// Setter und Getter
	public int getTodesdatum() {
		return todesdatum;
	}

	public void setTodesdatum(int todesdatum) {
		this.todesdatum = todesdatum;
	}

	public int getAktuelleEnergie() {
		return aktuelleEnergie;
	}

	public void setAktuelleEnergie(int aktuelleEnergie) {
		this.aktuelleEnergie = aktuelleEnergie;
	}

	public void wand(Object o, char seite) {
		if (new Simulation().getClass() != o.getClass())
			return;

		switch (seite) {
		case 'o':
		case 'u':
			winkel = 360 - winkel;
			break;
		case 'l':
			if (winkel > 180)
				winkel = 540 - winkel;
			else
				winkel = 180 - winkel;
			break;
		case 'r':
			if (winkel >= 270)
				winkel = 540 - winkel;
			else
				winkel = 180 - winkel;
			break;
		}
	}

	public void gehe() {
		double bogWinkel = Math.PI * winkel / 180.0;
		Position.x = (Math.cos(bogWinkel) * geschwindigkeit) + Position.x;
		Position.y = (Math.sin(bogWinkel) * geschwindigkeit) + Position.y;
	}

	public void drehe() {
		int random = (int) (Math.random() * 2);
		if (random == 1)
			winkel *= 5;
		else
			winkel -= 5;

		if (winkel > 360)
			winkel -= 360;
		if (winkel < 0)
			winkel += 360;

	}

	//
    @Override
	public void live() {
		// bewegt sich
		if (aktuelleEnergie < 150)
			aktuelleEnergie += 2;

		int random = (int) (Math.random() * 100);
		if (random <= 98) {
			gehe();
		} else {
			drehe();
		}
	}

	// event fuer kampf
	public final void Kaempfen(int antCount) {
		aktuelleEnergie -= (int) (antCount * 100);
	}
}
