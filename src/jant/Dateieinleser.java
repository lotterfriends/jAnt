package jant;

import java.io.*;
import java.util.*;

public class Dateieinleser {
	String pfad = "";
	File file;
	ArrayList<String> zeilen = new ArrayList<String>();
	String benutzer;
	String volk;

	public Dateieinleser(String pfad) {
		this.pfad = pfad;
		file = new File(pfad);
		if (file.exists()) {
			try {
				BufferedReader bufferedReader = new BufferedReader(
						new FileReader(pfad));
				String zeile = bufferedReader.readLine();
				while (zeile != null) {
					zeilen.add(zeile);
					zeile = bufferedReader.readLine();
				}
				bufferedReader.close();
			} catch (Exception e) {
				System.err.println("DateiEinleser meldet: Einlesen nicht m�glich!");
			}
			zeilen.remove("");
		}

		// if (!file.exists())
		// file.createNewFile();
	}

	public Highscoreliste getHighscoreliste() {
		Highscoreliste highscoreliste = new Highscoreliste(zeilen);
		return highscoreliste;
	}

	public Spielmodi getLetzterSpielmodi() {

		if (zeilen.size() == 0)
			return null;
		Spielmodi spielmodi = null;
		String zeile = (String) zeilen.get(0);
		if (zeile.indexOf("#") >= 0) {
			String[] array = zeile.split("#");
			zeile = array[1];
			if ("Arcade".equals(zeile))
				spielmodi = new Arcade();
			if ("Classic".equals(zeile))
				spielmodi = new Classic();
			if ("Invasion".equals(zeile))
				spielmodi = new Invasion();
			if (array.length == 4) {
				benutzer = array[2];
				volk = array[3];
			}
		}
		return spielmodi;
	}

	public String getLetzterBenutzer() {
		return benutzer;
	}

	public String getLetztesVolk() {
		return volk;
	}

	public void erstelleConfigdatei(ArrayList<String> liste) {
		try {
			file.delete();
			file.createNewFile();
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(
					pfad));
			for (Iterator<String> it = liste.iterator(); it.hasNext();)
				bufferedWriter.write((String) it.next() + "\n");
			bufferedWriter.close();
		} catch (IOException e) {
			System.err.println("Kann Datei nicht erzeugen! " + e);
		}
	}
}
