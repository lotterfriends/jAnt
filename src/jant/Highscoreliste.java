package jant;

import java.util.*;

public class Highscoreliste {
	String punkte;
	String benutzer;
	String volk;
	ArrayList<String> liste;
	ArrayList<String> score = new ArrayList<String>();
	int index = 0;
	String modus = "#";

	public Highscoreliste(ArrayList<String> liste) {
		this.liste = liste;
	}

	public void setPunkte(String punkte) {
		if (punkte.indexOf("-") >= 0)
			punkte = "0";
		while (punkte.length() < 6) {
			punkte = " " + punkte;
		}
		this.punkte = punkte;
	}

	public void setBenutzer(String benutzer) {
		this.benutzer = benutzer;
	}

	public void setVolk(String volk) {
		this.volk = volk;
	}

	public ArrayList<String> erzeugeScore(Spielmodi modi) {

		if (modi instanceof Classic)
			modus += "Classic";
		if (modi instanceof Arcade)
			modus += "Arcade";
		if (modi instanceof Invasion)
			modus += "Invasion";

		String zeile;
		for (Iterator<String> it = liste.iterator(); it.hasNext(); index++) {
			zeile = ((String) it.next()).trim();
			if (zeile.startsWith(modus)) {
				break;
			}
		}
		String[] felder = { " ", " ", " ", " " };
		for (int i = 1; i < 6; i++) {
			zeile = (String) liste.get(index + i);
			felder = zeile.split("#");
			zeile = felder[1] + "#Benutzer:#" + felder[2] + "#Volk:#"
							  + felder[3];
			score.add(zeile);
		}
		zeile = punkte + "#Benutzer:#" + benutzer + "#Volk:#" + volk;
		score.add(zeile);
		Collections.sort(score);
		Collections.reverse(score);
		score.remove(5);
		return score;
	}

	public ArrayList<String> getNeueConfig() {
		liste.set(0, "modi" + modus + "#" + benutzer + "#" + volk);
		for (int i = 1; i < 6; i++) {
			String zeile = (String) score.get(i - 1);
			String[] felder = { " ", " ", " ", " ", " " };
			felder = zeile.split("#");
			if (felder.length < 5)
				continue;
			zeile = "score" + i + "#" + felder[0] + "#" + felder[2] + "#"
					+ felder[4];
			liste.set(index + i, zeile);
		}
		return liste;

	}

}
