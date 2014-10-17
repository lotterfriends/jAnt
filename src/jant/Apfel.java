package jant;

import java.util.*;

public class Apfel extends NoneLivingObject {

	private int todesdatum = 0;

	public Apfel(double x, double y) {
		super(x, y);
	}

	public Apfel(double x, double y, double r) {
		super(x, y, r);
	}

	private ArrayList<Ant> traeger = new ArrayList<Ant>();

	public int getTodesdatum() {
		return todesdatum;
	}

	public void setTodesdatum(int todesdatum) {
		this.todesdatum = todesdatum;
	}

	public double getPositionX() {
		if (traeger.size() > 0)
			Position.x = ((Ant) traeger.get(0)).Position.x;
		return Position.x;
	}

	public double getPositionY() {
		if (traeger.size() > 0)
			Position.y = ((Ant) traeger.get(0)).Position.y;
		return Position.y;
	}

	public void traegerMehr(Ant ant) {
		if (!traeger.contains(ant))
			traeger.add(ant);
	}

	public void traegerWeniger(Ant ant) {

		if (todesdatum != 0)
			return;
		if (traeger.contains(ant))
			traeger.remove(traeger.indexOf(ant));
		else
			System.out.println("Konsistensfehler in " + this.toString()
					+ ".traegerWeniger(" + ant.toString() + ")");
	}

	public int getAnzahlTraegerInReichweite() {
		return getTraegerInReichweite().size();
	}

	public List<Ant> getTraegerInReichweite() {
		List<Ant> liste = new ArrayList<Ant>();
		for (Iterator<Ant> it = traeger.iterator(); it.hasNext();) {
			Ant ant = it.next();
			if ((ant.Position.x <= Position.x + 5.0 && ant.Position.x >= Position.x - 5.0)
					&& (ant.Position.y <= Position.y + 5.0 && ant.Position.y >= Position.y - 5.0)) {
				liste.add(ant);
			}
		}
		return liste;
	}

	public int getAnzahlTraeger() {
		return traeger.size();
	}

	public Ant getErstenTraeger() {
		if (traeger.size() > 0)
			return (Ant) traeger.get(0);
		return null;
	}

	public void abgeliefert() {
		for (Iterator<Ant> it = traeger.iterator(); it.hasNext();) {
			Ant ant = it.next();
			ant.lasseNahrungFallen();
		}
		traeger = new ArrayList<Ant>();
	}

}
