package jant;

import java.util.*;

//TODO: Methoden als final deklarieren
public class Ant extends LivingObject {

	protected Simulation sim;
	private double winkel;
	private double richtung;
	private int runde = 0;
	// Max. Strecke, bevor tot
	private int reichweite = 5000;
	private int maxReichweite = 5000;
	private int drehgeschwindigkeit = 5;
	// Restweg bis Ziel
	private double reststrecke = 0.0;
	private int Wolke = 0;
	private Apfel apfel = null;
	private int maximaleEnergie = 500;
	private int aktuelleEnergie = maximaleEnergie;
	private AllObject ziel = null;
	// Angriff
	private static int angriffswert = 100;

	// Wird nur auf != null geprÃ¼ft
	// Evtl. spï¿½ter Klartestabfrage aktuelleLast == "Zucker" oder "Apfel" ?
	private String aktuelleLast = null;

	// TODO bei 2/3 von max. Reichweite + Event aufrufen
	private boolean istMuede = false;

	// Besprechungsbedarf, wird bisher nicht verwendet
	private double geschwindigkeit = 1;
	private double maximaleGeschwindigkeit = 1;
	// Keine Reststrecke und Winkelausrichtung erreicht. In live() gesetzt.
	private boolean zielErreicht = true;

	public Ant(double x, double y, double winkel, Simulation sim) {
		super(x, y);
		this.winkel = winkel;
		this.richtung = winkel;
		this.sim = sim;
	}

	public Ant(double x, double y, double winkel, double r, Simulation sim) {
		super(x, y, r);
		this.winkel = winkel;
		this.richtung = winkel;
		this.sim = sim;
	}

	public Ant(double x, double y, double winkel, double r, double rv,
			Simulation sim) {
		super(x, y, r, rv);
		this.winkel = winkel;
		this.richtung = winkel;
		this.sim = sim;
	}

	public final int getAnzahlInSichtweite() {
		return sim.anzahlAmeisen(this);
	}

	// Nur Mainprogramm soll Reichweite erfragen.
	protected final boolean getZielErreicht() {
		return zielErreicht;
	}

	protected AllObject getZiel() {
		return ziel;
	}

	protected int getReichweite(Object o) {
		if (new Simulation().getClass() == o.getClass()) {
			return reichweite;
		}
		return 0;
	}

	protected final void geheZuBau() {
		geheZuZiel(sim.getBau());
	}

	protected final String getAktuelleLast() {
		return aktuelleLast;
	}

	protected final int getAktuelleEnergie() {
		return aktuelleEnergie;
	}

	protected final double getEntfernungZuBau() {

		/*
		 * AllObject zielMerker = ziel; double richtung = this.winkel; double
		 * reststrecke = this.reststrecke; geheZuZiel(Simulation.getBau()); ziel
		 * = zielMerker; double entfernung = this.reststrecke; this.reststrecke
		 * = reststrecke; this.winkel = richtung;
		 */

		Koordinate k = new Koordinate(this, sim.getBau());
		return k.bestimmeEntfernung();
	}

	protected final int getDrehgeschwindigkeit() {
		return drehgeschwindigkeit;
	}

	protected final double getMaximaleGeschwindigkeit() {
		return maximaleGeschwindigkeit;
	}

	protected final boolean getIstMuede() {
		return istMuede;
	}

	public final boolean istMuede() {
		return istMuede;
	}

	protected final double getRechweite() {
		return reichweite;
	}

	protected final boolean getAngekommen() {
		return zielErreicht;
	}

	protected final double getZurueckgelegteStrecke() {
		return maxReichweite - reichweite;
	}

	protected static final int getAngriff() {
		return angriffswert;
	}

	protected final double getAktuelleGeschwindigkeit() {
		return geschwindigkeitErmitteln();
	}

	protected final double getRichtung() {
		return winkel;
	}

	protected final double getRestwinkel() {
		return getDiffWinkel(richtung, winkel);
	}

	protected final double getReststrecke() {
		return reststrecke;
	}

	private double getDiffWinkel(double von, double bis) {
		double restWinkel = bis - von;
		if (restWinkel < 0)
			restWinkel *= (-1);
		if (restWinkel > 180)
			restWinkel = 360 - restWinkel;
		return restWinkel;
	}

	protected final void dreheZuZiel(AllObject o) {
		double reststrecke = this.reststrecke;
		AllObject zielMerker = ziel;
		geheZuZiel(o);
		ziel = zielMerker;
		this.reststrecke = reststrecke;
	}

	protected final boolean brauchtNochTraeger(Apfel apfel) {
		if (apfel.getAnzahlTraeger() < 5)
			return true;
		return false;
	}

	protected final void geheWegVonZiel(AllObject o, double reststrecke) {
		AllObject zielMerker = ziel;
		geheZuZiel(o);
		ziel = zielMerker;
		richtung += 180;
		winkelKorrigieren(richtung);
		this.reststrecke = reststrecke;
	}

	private final void ausgeruht() {
		this.reichweite = this.maxReichweite;
	}

	// Geht bisher 1 Schritt pro Aufruf. Evtl. "geschwindigkeit" einbeziehen.
	private final void gehe() {
		double bogWinkel = Math.PI * winkel / 180.0;
		Position.x = (Math.cos(bogWinkel) * geschwindigkeit) + Position.x;
		Position.y = (Math.sin(bogWinkel) * geschwindigkeit) + Position.y;
		reststrecke -= geschwindigkeit;
		reichweite -= 1;

		// Ungenauigkeit vermeiden. Bau muss mindestens geschwindigkeit * 3
		// Kollision haben!!
		if (reststrecke < 0)
			reststrecke = 0;
	}

	// TODO: Koordinaten durch Reflection auf das ï¿½bergebene Objekt
	// umstellen.
	// Das ist eine sehr komplizierte Funktion.
	protected final void geheZuZiel(AllObject o) {

		double a = ((MyPosition) o.getPosition()).x;
		double b = ((MyPosition) o.getPosition()).y;

		double x = Position.x - a;
		if (x <= 0)
			x = x * (-1);

		double y = Position.y - b;
		if (y <= 0)
			y = y * (-1);

		// Super spezial Sonderfall
		if (y == 0 && b == 0 && winkel == 0) {
			richtung = 180;
			reststrecke = x;
			return;
		}

		double strecke = Math.sqrt(x * x + y * y);
		double bruch = y / strecke;
		richtung = Math.toDegrees(Math.asin(bruch));

		if (Position.x < a && Position.y > b)
			richtung = 360 - richtung;

		if (Position.x > a && Position.y > b)
			richtung += 180;

		if (Position.x > a && Position.y < b)
			richtung = 180 - richtung;

		reststrecke = strecke;
		ziel = o;
	}

	protected final void dreheInRichtung(double richtung) {
		richtung = winkelKorrigieren(richtung);
		if (richtung < 0)
			richtung += 360;
		this.richtung = richtung;
	}

	protected final void dreheUm() {
		dreheUmWinkel(180);
	}

	protected final void bleibStehen() {
		ziel = null;
		reststrecke = 0;
		richtung = winkel;
	}

	protected final void geheGeradeaus() {
		geheGeradeaus(1);
	}

	protected final void geheGeradeaus(double reststrecke) {
		this.reststrecke = reststrecke;
	}

	protected final void dreheUmWinkel(double richtung) {
		richtung = winkelKorrigieren(richtung);
		this.richtung += richtung;
		if (this.richtung > 360)
			this.richtung -= 360;
		if (this.richtung < 0)
			this.richtung += 360;
	}

	protected final void stirb() {
		aktuelleEnergie = 0;
		lasseNahrungFallen();
	}

	// TODO: Einfallswinkel = Ausfallswinkel
	protected final void wand(Object o, char seite) {
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
		richtung = winkel;

	}

	private final void drehe() {
		if (winkel > richtung) {
			double links = winkel - richtung;
			if (links > 180) {
				winkel += drehgeschwindigkeit;
				if (winkel > 360)
					winkel -= 360;
			} else {
				winkel -= drehgeschwindigkeit;
			}
		} else {
			double rechts = richtung - winkel;
			if (rechts > 180) {
				winkel -= drehgeschwindigkeit;
				if (winkel < 0)
					winkel += 360;
			} else {
				winkel += drehgeschwindigkeit;
			}
		}
		double winkeldiff = winkel - richtung;
		if (winkeldiff < 0)
			winkeldiff *= (-1);
		if (winkeldiff < drehgeschwindigkeit)
			winkel = richtung;
	}

	// Wenn Zucker, dann aufnehmen
	// TODO: Ãpfel
	protected void nimm(AllObject o) {
		if (o == apfel)
			return;
		lasseNahrungFallen();
		if (new Zucker(10, 10).getClass() == o.getClass()) {
			Zucker zucker = (Zucker) o;

			ArrayList<Zucker> zuckerberge = sim.getAlleZuckerberge();
			if (!zuckerberge.contains(zucker)) {
				System.out
						.println("Eigene Objekte erzeugen ist nicht erlaubt!");
				System.exit(0);
			}
			zuckerberge = sim.getZuckerberge();
			if (zuckerberge.contains(zucker)) {
				if (hatZielErreicht(o)) {
					zucker.weniger();
					this.aktuelleLast = "Zucker";
				}
			}
			return;
		}
		if (new Apfel(12, 12).getClass() == o.getClass()) {
			apfel = (Apfel) o;
			ArrayList<Apfel> aepfel = sim.getAepfel();
			if (!aepfel.contains(apfel)) {
				System.out
						.println("Eigene Objekte erzeugen ist nicht erlaubt!");
				System.exit(1);
			} else {
				if (hatZielErreicht(o)) {
					apfel.traegerMehr(this);
					this.aktuelleLast = "Apfel";
				}
			}
		}
	}

	protected final void lasseNahrungFallen() {
		// Konsistenzfehler beim Abgeben im Bau vermeiden..
		if (apfel != null)
			// && apfel.getTodesdatum() == 0)
			apfel.traegerWeniger(this);
		this.aktuelleLast = null;
		apfel = null;
	}

	// Immer aufgerufen, verfolgt das aktuelle Ziel (Winkel und Strecke)
	// @Override
	protected final void live(int runde) {
		geschwindigkeit = geschwindigkeitErmitteln();

		if (aktuelleEnergie < maximaleEnergie)
			aktuelleEnergie++;

		if (Wolke > 0)
			Wolke--;

		if (aktuelleEnergie <= 0)
			stirb();

		if (this.runde == 0)
			this.runde = runde;
		if (this.runde != runde) {
			System.out.println("Eigener Aufruf von live() ist nicht erlaubt!");
			System.exit(1);
		} else {
			this.runde++;
			if ((Position.x <= 3.0 && Position.x >= -3.0)
					&& (Position.y <= 3.0 && Position.y >= -3.0)) {
				ausgeruht();
			}

			// Muede Indikator
			if (reichweite <= maxReichweite / 3 * 2)
				istMuede = true;
			else
				istMuede = false;

			// Handlung pro Runde
			if (winkel != richtung) {
				drehe();
			} else {
				if (reststrecke > 0) {
					gehe();
				}
			}

			// Indikator für warten()
			if (winkel == richtung && reststrecke == 0) {
				if (hatZielErreicht()) {
					zielErreicht = true;
					ziel = null;
				} else {
					geheZuZiel(ziel);
					zielErreicht = false;
				}
			} else
				zielErreicht = false;
		}
	}

	private boolean hatZielErreicht(AllObject o) {
		if (o == null)
			return true;
		double x1 = this.Position.x;
		double y1 = this.Position.y;
		double x2 = o.Position.x;
		double y2 = o.Position.y;
		double r1 = this.getRadius();
		double r2 = o.getRadius();
		return ((r1 + r2) * (r1 + r2) >= (x1 - x2) * (x1 - x2) + (y1 - y2)
				* (y1 - y2));
	}

	private boolean hatZielErreicht() {
		return hatZielErreicht(ziel);
	}

	private final double winkelKorrigieren(double winkel) {
		return winkel % 360;
	}

	private final double geschwindigkeitErmitteln() {

		if (aktuelleLast == null)
			return maximaleGeschwindigkeit;

		if (aktuelleLast.equalsIgnoreCase("Zucker"))
			return 0.5;
		if (aktuelleLast.equalsIgnoreCase("Apfel")) {
			if (apfel.getAnzahlTraegerInReichweite() >= 5)
				return maximaleGeschwindigkeit;
			else {
				if (apfel.getTraegerInReichweite().contains(this)) {
					return maximaleGeschwindigkeit
							/ (5D / apfel.getAnzahlTraegerInReichweite());
				}
			}
		}
		return maximaleGeschwindigkeit;

	}

	// Events
	// public final void erreichtKaefer(Kaefer kaefer){
	// aktuelleEnergie -= 5;
	// }

	public final void erzeugeDuftwolke(int Radius, int Information) {
		if (Wolke == 0) {
			Wolke = 300;
			sim.duftwolken.add(new Duftwolke(this.getPosition().x, this
					.getPosition().y, Radius, Information));
		}
	}

	public void wirdMuede() {
	}

	public void siehtApfel(Apfel apfel) {
	}

	public void erreichtApfel(Apfel apfel) {
	}

	public void siehtZucker(Zucker zucker) {
	}

	public void erreichtZucker(Zucker zucker) {
	}

	public void siehtKaefer(Kaefer kaefer) {
	}

	public void wartet() {
	}

	public void hatNahrung() {
	}

	public void erreichtBau() {
	}

	public void erreichtDuftwolke(int Information) {

	}

	public final void kaempfen(int anzahlAmeisen) {
		aktuelleEnergie -= (int) (80 / anzahlAmeisen);
	}

}
