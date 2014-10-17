package jant;

public class Classic extends Spielmodi {

	public boolean getAmeisenrespawn() {
		return false;
	}

    @Override
	public String toString() {
		return "Classic";
	}

	public int getMax_ants() {
		return 100;
	}

	public int getPunkteKaefer() {
		return 120;
	}

	public int getPunkteZucker() {
		return 5;
	}

	public int getPunkteApfel() {
		return 80;
	}

	public int getPunkteTodAbzug() {
		return 50;
	}

	public int getMaxRunden() {
		return 30000;
	}

	public int getRespawnKaefer() {
		return 500;
	}

	public int getRespawnApfel() {
		return 900;
	}

	public int getRespawnZucker() {
		return 700;
	}

	/*
	 * public int getMaxKaefer() { return 6; }
	 * 
	 * public int getMaxApfel(){ return 3 }
	 * 
	 * public int getMaxZucker() { return 2 }
	 */

	public boolean neuerKaefer(int aktRound) {

		if (aktRound == 1500)
			return true;
		if (aktRound == 5000)
			return true;
		if (aktRound == 8000)
			return true;
		if (aktRound == 10000)
			return true;
		if (aktRound == 15000)
			return true;
		if (aktRound == 20000)
			return true;

		return false;
	}

	public boolean neuerApfel(int aktRound) {

		if (aktRound == 200)
			return true;
		if (aktRound == 4000)
			return true;
		if (aktRound == 9000)
			return true;

		return false;
	}

	public boolean neuerZucker(int aktRound) {

		if (aktRound == 10)
			return true;
		if (aktRound == 1000)
			return true;

		return false;
	}

}
