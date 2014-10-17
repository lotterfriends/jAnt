package jant;

public abstract class Spielmodi {

	public abstract boolean getAmeisenrespawn();

	public abstract int getMax_ants();

	public abstract int getMaxRunden();

	public abstract int getPunkteKaefer();

	public abstract int getPunkteApfel();

	public abstract int getPunkteZucker();

	public abstract int getPunkteTodAbzug();

	public abstract int getRespawnKaefer();

	public abstract int getRespawnApfel();

	public abstract int getRespawnZucker();

	/*
	 * public abstract int getMaxKaefer(); public abstract int getMaxApfel();
	 * public abstract int getMaxZucker();
	 */

	public abstract boolean neuerKaefer(int aktRound);

	public abstract boolean neuerApfel(int aktRound);

	public abstract boolean neuerZucker(int aktRound);

}
