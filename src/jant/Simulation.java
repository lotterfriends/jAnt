package jant;

import java.util.*;
import jant.Collision.CollisionMode;

public class Simulation {

	private Spielmodi modi;
	public int punkte = 0;
	private int max_ants = 100;

	public Simulation(Spielmodi modi) {
		this.modi = modi;
		if (modi.getAmeisenrespawn())
			punkte += 5000;
		max_ants = modi.getMax_ants();
	}

	// Startwinkel der ersten Ameise (-7,2)
	double winkel = 300.2;
	int aktRound = 0;

	// Erstellte Zahl von Ameisen

	private final static int spawndelay = 10;
	private LinkedList<Ant> ants = new LinkedList<Ant>();
	// Kollisionen, Zusammenstöße und Sichtkollisionen
	private ArrayList<Collision> kollisionen = new ArrayList<Collision>();
	private ArrayList<Collision> viewKollisionen = new ArrayList<Collision>();
	private ArrayList<Zucker> zuckerberge = new ArrayList<Zucker>();
	private ArrayList<Apfel> aepfel = new ArrayList<Apfel>();
	private ArrayList<Kaefer> kaeferliste = new ArrayList<Kaefer>();
	private ArrayList<Apfel> selektierteApfelliste = new ArrayList<Apfel>();
	private ArrayList<Zucker> selektierteZuckerliste = new ArrayList<Zucker>();
	private ArrayList<Kaefer> selektierteKaeferliste = new ArrayList<Kaefer>();

	public ArrayList<Duftwolke> duftwolken = new ArrayList<Duftwolke>();
	private Bau bau = new Bau(0, 0);

	// instead of collision
	public int random = 0;
	public boolean zuckerGefunden = false;

	// Zielpunkte
	// public MyPosition left_bottom = new MyPosition(-400.0, -300.0);
	// public MyPosition right_top = new MyPosition(400.0, 300.0);
	public MyPosition left_bottom = new MyPosition(-495.0, -370.0);
	public MyPosition right_top = new MyPosition(495.0, 395.0);
	private AntMark marker;

	public Simulation() {

	}

	public int getAktRound() {
		return aktRound;
	}

	public int anzahlAmeisen(Ant ameise) {
		int anzahl = 0;

		for (int nCnt = 0; nCnt < getAnts().size(); nCnt++) {
			Collision coll = new Collision(ameise, (Ant) getAnts().get(nCnt));

			if (ameise != getAnts().get(nCnt)
					&& coll.collides(CollisionMode.View)) {
				anzahl++;
			}
		}

		return anzahl;
	}

	private boolean kollisionVorhanden(AllObject Obj1, AllObject Obj2,
			ArrayList<Collision> liste) {
		for (int nCnt = 0; nCnt < liste.size(); nCnt++) {
			AllObject CollObj1 = ((Collision) liste.get(nCnt)).getObj1();
			AllObject CollObj2 = ((Collision) liste.get(nCnt)).getObj2();

			if (CollObj1 == Obj1 && CollObj2 == Obj2) {
				return true;
			}
		}
		return false;
	}

	private void removeOldCollisions() {
		for (int nCnt = 0; nCnt < kollisionen.size(); nCnt++) {
			Collision coll = (Collision) kollisionen.get(nCnt);

			if (!coll.collides(CollisionMode.Body)) {
				kollisionen.remove(nCnt);
			}
		}

		for (int nCnt = 0; nCnt < viewKollisionen.size(); nCnt++) {
			Collision coll = (Collision) viewKollisionen.get(nCnt);

			if (!coll.collides(CollisionMode.View)) {
				viewKollisionen.remove(nCnt);
			}
		}
	}

	private void kollisionsverwaltung() {
		// Alte Kollisionen löschen
		removeOldCollisions();

		// Alle Ameisen mit Käfern
		for (int nAnt = 0; nAnt < getAnts().size(); nAnt++) {
			for (int nKaefer = 0; nKaefer < getKaeferliste().size(); nKaefer++) {
				Ant a = (Ant) getAnts().get(nAnt);
				Kaefer b = (Kaefer) getKaeferliste().get(nKaefer);
				Collision coll = new Collision((AllObject) a, (AllObject) b);

				if (coll.collides(CollisionMode.View)
						&& b.getTodesdatum() == 0
						&& !kollisionVorhanden((AllObject) a, (AllObject) b,
								viewKollisionen)) {
					viewKollisionen.add(coll);
					a.siehtKaefer(b);
				}
			}
		}

		// Alle Ameisen mit Bau
		for (int nAnt = 0; nAnt < getAnts().size(); nAnt++) {
			Ant a = (Ant) getAnts().get(nAnt);
			Bau b = getBau();
			Collision coll = new Collision((AllObject) a, (AllObject) b);

			if (coll.collides(CollisionMode.Body)
					&& !kollisionVorhanden((AllObject) a, (AllObject) b,
							kollisionen)) {
				kollisionen.add(coll);
				a.bleibStehen();
				a.erreichtBau();
				if ("Zucker".equals(a.getAktuelleLast())) {
					punkte += modi.getPunkteZucker();
					a.lasseNahrungFallen();
				}

			}
		}

		// Alle Ameisen mit Äpfeln
		for (int nAnt = 0; nAnt < getAnts().size(); nAnt++) {
			for (int nAepfel = 0; nAepfel < aepfel.size(); nAepfel++) {
				Ant a = (Ant) getAnts().get(nAnt);
				Apfel b = (Apfel) aepfel.get(nAepfel);
				Collision coll = new Collision((AllObject) a, (AllObject) b);

				if (coll.collides(CollisionMode.View)
						&& b.getTodesdatum() == 0
						&& !kollisionVorhanden((AllObject) a, (AllObject) b,
								viewKollisionen)) {
					viewKollisionen.add(coll);
					a.siehtApfel(b);
				}
			}
		}

		// Alle Ameisen mit Äpfeln
		for (int nAnt = 0; nAnt < getAnts().size(); nAnt++) {
			for (int nAepfel = 0; nAepfel < aepfel.size(); nAepfel++) {
				Ant a = (Ant) getAnts().get(nAnt);
				Apfel b = (Apfel) aepfel.get(nAepfel);
				Collision coll = new Collision((AllObject) a, (AllObject) b);

				if (coll.collides(CollisionMode.Body)
						&& b.getTodesdatum() == 0
						&& !kollisionVorhanden((AllObject) a, (AllObject) b,
								kollisionen)) {
					kollisionen.add(coll);
					a.erreichtApfel(b);
				}
			}
		}

		// Alle Ameisen mit Zucker
		for (int nAnt = 0; nAnt < getAnts().size(); nAnt++) {
			for (int nZucker = 0; nZucker < zuckerberge.size(); nZucker++) {
				Ant a = (Ant) getAnts().get(nAnt);
				Zucker b = (Zucker) zuckerberge.get(nZucker);
				Collision coll = new Collision((AllObject) a, (AllObject) b);

				if (coll.collides(CollisionMode.View)
						&& b.getTodesdatum() == 0
						&& !kollisionVorhanden((AllObject) a, (AllObject) b,
								viewKollisionen)) {
					viewKollisionen.add(coll);
					a.siehtZucker(b);
				}
			}
		}

		// Alle Ameisen mit Zucker
		for (int nAnt = 0; nAnt < getAnts().size(); nAnt++) {
			for (int nZucker = 0; nZucker < zuckerberge.size(); nZucker++) {
				Ant a = (Ant) getAnts().get(nAnt);
				Zucker b = (Zucker) zuckerberge.get(nZucker);
				Collision coll = new Collision((AllObject) a, (AllObject) b);

				if (coll.collides(CollisionMode.Body)
						&& b.getTodesdatum() == 0
						&& !kollisionVorhanden((AllObject) a, (AllObject) b,
								kollisionen)) {
					kollisionen.add(coll);
					a.erreichtZucker(b);
				}
			}
		}

		// Alle Kaefer mit Ameisen
		for (int nKaefer = 0; nKaefer < kaeferliste.size(); nKaefer++) {
			ArrayList<Ant> collidingAnts = new ArrayList<Ant>();
			Kaefer a = (Kaefer) kaeferliste.get(nKaefer);

			for (int nAnt = 0; nAnt < getAnts().size(); nAnt++) {
				Ant b = (Ant) getAnts().get(nAnt);
				Collision coll = new Collision((AllObject) a, (AllObject) b);

				if (coll.collides(CollisionMode.Body) && a.getTodesdatum() == 0) {
					collidingAnts.add(b);
				}
			}

			if (collidingAnts.size() > 0) {
				a.Kaempfen(collidingAnts.size());
				for (int nAnt = 0; nAnt < collidingAnts.size(); nAnt++) {
					collidingAnts.get(nAnt).kaempfen(collidingAnts.size());
				}
			}
		}

		// Ameisen mit Duftwolken
		for (int nAnt = 0; nAnt < getAnts().size(); nAnt++) {
			for (int nCloud = 0; nCloud < duftwolken.size(); nCloud++) {
				Ant a = (Ant) getAnts().get(nAnt);
				Duftwolke b = (Duftwolke) duftwolken.get(nCloud);
				Collision coll = new Collision((AllObject) a, (AllObject) b);

				if (coll.collides(CollisionMode.Body)
						&& !kollisionVorhanden((AllObject) a, (AllObject) b,
								kollisionen)) {
					kollisionen.add(coll);
					a.erreichtDuftwolke(b.getInformation());
				}
			}
		}

	}

	private void aepfelverwaltung() {

		if (modi.neuerApfel(aktRound))
			aepfel.add(new Apfel(zufallx('o'), zufally('r')));

		for (Iterator<Apfel> it = aepfel.iterator(); it.hasNext();) {
			Apfel apfel = it.next();
			if (apfel.getTodesdatum() != 0) {
				if (aktRound - apfel.getTodesdatum() >= modi.getRespawnApfel()) {
					apfel.setTodesdatum(0);
					apfel.Position.x = zufallx('o');
					apfel.Position.y = zufally('r');
				}
			}
		}
	}

	private void zuckerverwaltung() {

		if (modi.neuerZucker(aktRound))
			zuckerberge.add(new Zucker(zufallx('o'), zufally('r')));

		for (Iterator<Zucker> it = zuckerberge.iterator(); it.hasNext();) {
			Zucker zucker = it.next();
			if (zucker.getTodesdatum() != 0) {
				if (aktRound - zucker.getTodesdatum() >= modi
						.getRespawnZucker()) {
					zucker.setMenge(20);
					zucker.setTodesdatum(0);
					zucker.Position.x = zufallx('o');
					zucker.Position.y = zufally('r');
				}
			} else {
				if (zucker.getMenge() <= 0) {
					zucker.setTodesdatum(aktRound);
				}
			}
		}
	}

	ArrayList<Zucker> getAlleZuckerberge() {
		return zuckerberge;
	}

	void listenErstellen() {
		selektierteApfelliste = new ArrayList<Apfel>();
		selektierteZuckerliste = new ArrayList<Zucker>();

		for (Iterator<Zucker> it = zuckerberge.iterator(); it.hasNext();) {
			Zucker zucker = (Zucker) it.next();
			if (zucker.getTodesdatum() == 0)
				selektierteZuckerliste.add(zucker);
		}
		for (Iterator<Apfel> it = aepfel.iterator(); it.hasNext();) {
			Apfel apfel = (Apfel) it.next();
			if (apfel.getTodesdatum() == 0)
				selektierteApfelliste.add(apfel);
		}

		selektierteKaeferliste = new ArrayList<Kaefer>();
		for (Iterator<Kaefer> it = kaeferliste.iterator(); it.hasNext();) {
			Kaefer kaefer = (Kaefer) it.next();
			if (kaefer.getTodesdatum() == 0)
				selektierteKaeferliste.add(kaefer);
		}

	}

	ArrayList<Zucker> getZuckerberge() {
		return selektierteZuckerliste;
	}

	ArrayList<Apfel> getAepfel() {
		return selektierteApfelliste;
	}

	private void kaeferverwaltung(int runde) {
		Kaefer kaefer;

		if (modi.neuerKaefer(aktRound)) {
			char seite = zufallSeite();
			kaefer = new Kaefer(zufallx(seite), zufally(seite), 5,
					zufallwinkel(seite));
			kaeferliste.add(kaefer);
		}

		for (Iterator<Kaefer> it = kaeferliste.iterator(); it.hasNext();) {
			kaefer = it.next();
			if (kaefer.getTodesdatum() == 0) {
				if (kaefer.getAktuelleEnergie() <= 0) {
					kaefer.setTodesdatum(aktRound);
					punkte += modi.getPunkteKaefer();
					continue;
				}

				if (kaefer.Position.x <= left_bottom.x) {
					kaefer.wand(this, 'l');
				} else if (kaefer.Position.x >= right_top.x) {
					kaefer.wand(this, 'r');
				} else if (kaefer.Position.y >= right_top.y) {
					kaefer.wand(this, 'u');
				} else if (kaefer.Position.y <= left_bottom.y) {
					kaefer.wand(this, 'o');
				}

				kaefer.live();
			} else {
				if (aktRound - kaefer.getTodesdatum() >= modi
						.getRespawnKaefer()) {
					kaefer.setAktuelleEnergie(1500);
					kaefer.setTodesdatum(0);
				}
			}
		}
	}

	public ArrayList<Kaefer> getKaeferliste() {
		return selektierteKaeferliste;
	}

	ArrayList<Duftwolke> getDuftwolken() {
		ArrayList<Duftwolke> liste = new ArrayList<Duftwolke>();

		for (Iterator<Duftwolke> it = duftwolken.iterator(); it.hasNext();) {
			Duftwolke cloud = it.next();

			if (cloud.getLifetime() > 0) {
				liste.add(cloud);
			}
		}

		return liste;
	}

	public void runRound() {
		listenErstellen();
		// Es werden erstmal Ameisen geboren
		if (getAnts().size() < max_ants && aktRound % spawndelay == 0)
			if ((aktRound / spawndelay < max_ants && !modi.getAmeisenrespawn())
					|| (modi.getAmeisenrespawn() && punkte >= 50)) {
				if (modi.getAmeisenrespawn())
					punkte -= 50;
				// Gegen den Uhrzeigersinn
				winkel -= 7.2;

				if (winkel < 0) {
					winkel += 360;
				}

				getAnts().add(new MyAnt(0, 0, winkel, 1, 20, this));
			}

		// Tote entfernen

		for (int i = 0; i < duftwolken.size(); i++) {
			Duftwolke cloud = duftwolken.get(i);

			cloud.reduceLifetime();
			if (cloud.getLifetime() <= 0) {
				duftwolken.remove(i);
				i--;
			}
		}

		for (int i = 0; i < getAnts().size(); i++) {
			Ant ant = (Ant) getAnts().get(i);
			// Keine LP oder Reichweite mehr
			if (ant.getAktuelleEnergie() <= 0 || ant.getReichweite(this) <= 0) {
				if (marker != null && marker.getMarkedAnt() == ant) {
					marker = null;
				}
				ant.stirb();
				getAnts().remove(ant);
				punkte -= modi.getPunkteTodAbzug();
				i--;
			}
		}

		kollisionsverwaltung();
		kaeferverwaltung(aktRound);
		aepfelverwaltung();
		zuckerverwaltung();

		// Ameisen durchgehen
		// TODO Eventprioritäten
		for (int i = 0; i < getAnts().size(); i++) {
			Ant ant = (Ant) getAnts().get(i);
			if (ant.getIstMuede())
				ant.wirdMuede();

			// Wenn sonst nichts ist, letzte Option.
			if (ant.getZielErreicht())
				ant.wartet();

			// Achtung: Koordinaten system ist kaput - unten=plus, oben=minus
			if (ant.Position.x <= left_bottom.x) {
				ant.wand(this, 'l');
			} else if (ant.Position.x >= right_top.x) {
				ant.wand(this, 'r');
			} else if (ant.Position.y >= right_top.y) {
				ant.wand(this, 'u');
			} else if (ant.Position.y <= left_bottom.y) {
				ant.wand(this, 'o');
			}

			// Handlung beginnen.
			ant.live(aktRound);
		}

		for (Iterator<Apfel> it = getAepfel().iterator(); it.hasNext();) {
			Apfel apfel = it.next();
			if ((apfel.Position.x <= bau.Position.x + bau.getRadius() && apfel.Position.x >= bau.Position.x
					- bau.getRadius())
					&& (apfel.Position.y <= bau.Position.y + bau.getRadius() && apfel.Position.y >= bau.Position.y
							- bau.getRadius())) {
				apfel.setTodesdatum(aktRound);
				apfel.abgeliefert();
				punkte += modi.getPunkteApfel();
			}
		}

		// Spiel verloren weil keine Ameisen mehr da sind
		if (getAnts().size() <= 0) {
			System.out.println("Tot - Game Over");
		}
		aktRound++;
	}

	private char zufallSeite() {
		int random = Zufall.zahl(1, 4);
		char seite = 'o';
		switch (random) {
		case 1:
			seite = 'o';
			break;
		case 2:
			seite = 'u';
			break;
		case 3:
			seite = 'l';
			break;
		case 4:
			seite = 'r';
			break;
		}
		return seite;
	}

	private double zufallx(char seite) {
		double x = 100;
		switch (seite) {
		case 'o':
		case 'u':
			x = (double) Zufall.zahl((int) (left_bottom.x + 20),
					(int) (right_top.x - 20));
			break;
		case 'l':
			x = left_bottom.x + 5;
			break;
		case 'r':
			x = right_top.x - 5;
			break;
		}
		return x;
	}

	private double zufally(char seite) {
		double y = 100;
		switch (seite) {
		case 'o':
			y = left_bottom.y + 5;
			break;
		case 'u':
			y = right_top.y - +5;
			break;
		case 'l':
		case 'r':
			y = (double) Zufall.zahl(((int) (left_bottom.y + 20)),
					((int) (right_top.y - 20)));
			break;
		}
		return y;
	}

	private double zufallwinkel(char seite) {
		double winkel = 0;
		switch (seite) {
		case 'o':
			winkel = (double) Zufall.zahl(180);
			break;
		case 'u':
			winkel = (double) Zufall.zahl(180, 360);
			break;
		case 'l':
			winkel = (double) Zufall.zahl(90) + (double) Zufall.zahl(270, 360);
			if (winkel > 360)
				winkel -= 360;
			break;
		case 'r':
			winkel = (double) Zufall.zahl(90, 270);
			break;
		}

		return winkel;
	}

	protected final Bau getBau() {
		return bau;
	}

	public AntMark getMarker() {
		return marker;
	}

	public void setMarker(AntMark mark) {
		marker = mark;
	}

	public void setAnts(LinkedList<Ant> ants) {
		this.ants = ants;
	}

	public LinkedList<Ant> getAnts() {
		return ants;
	}

}
