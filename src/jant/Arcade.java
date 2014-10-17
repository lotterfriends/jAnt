package jant;

public class Arcade extends Spielmodi {

	private int kaeferBisher = 0;

    @Override
	public String toString() {
		return "Arcade";
	}

	public boolean getAmeisenrespawn() {
		return false;
	}

	public int getMax_ants() {

		return 100;
	}

	public int getPunkteKaefer() {
		return 120;
	}


	public int getPunkteZucker(){
		return 5;
	}


	public int getPunkteApfel(){
		return 80;
	}


	public int getPunkteTodAbzug(){
		return 0;
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

	public boolean neuerKaefer(int aktRound) {

		double formel =  (aktRound/15000) * (aktRound/10000) + aktRound/1500 -1;
		if(formel >= (double) kaeferBisher) {
			kaeferBisher++;
			return true;
		}
		return false;
	}

	public boolean neuerApfel(int aktRound){

		if(aktRound == 200)
			return true;
		if(aktRound == 4000)
			return true;
		if(aktRound == 9000)
			return true;

		return false;
	}

	public boolean neuerZucker(int aktRound){

		if(aktRound == 10)
			return true;
		if(aktRound == 1000)
			return true;

		return false;
	}


}
