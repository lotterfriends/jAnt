package jant;

public class Zufall {
	public static int zahl(int bis) {
		return zahl(0, bis);
	}

	public static int zahl(int von, int bis) {
		if (von > bis) {
			int merker = bis;
			bis = von;
			von = merker;
		}
		int bereich = (bis - von) + 1;
		int zufallszahl = ((int) (Math.random() * bereich)) + von;
		return zufallszahl;
	}
}
