package jant;

import java.util.*;

public class jAnt {

	public static void main(String[] args) {
		Dateieinleser dateieinleser = new Dateieinleser("../lib/profile.conf");
		Fenster fenster = new Fenster(dateieinleser.getLetzterSpielmodi(),
				dateieinleser.getLetzterBenutzer(), dateieinleser.getLetztesVolk());
		fenster.start();
		Spielmodi modi = fenster.getSpielmodi();
		Highscoreliste highscoreliste = dateieinleser.getHighscoreliste();
		if (args.length >= 1 && args[0].equals("3D")) {
			View3D view = new View3D(modi);
			highscoreliste.setPunkte(view.run());
		} else {
			View2D view = new View2D(modi);
			highscoreliste.setPunkte(view.run());
		}

		highscoreliste.setBenutzer(fenster.getBenutzer());
		highscoreliste.setVolk(fenster.getVolk());
		ArrayList<String> liste = highscoreliste.erzeugeScore(modi);
		System.out.println("Highscoreliste von " + modi.toString());
		for (Iterator<String> it = liste.iterator(); it.hasNext();)
			System.out.println("Punkte: " + it.next().replaceAll("#", " "));
		dateieinleser.erstelleConfigdatei(highscoreliste.getNeueConfig());
		System.exit(0);
	}

}
