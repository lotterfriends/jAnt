package jant;

public class Koordinate {

	double richtung;
	double entfernung;

	public Koordinate(AllObject o1, AllObject o2) {

		double a = ((MyPosition) o2.getPosition()).x;
		double b = ((MyPosition) o2.getPosition()).y;

		double x = o1.Position.x - a;
		if (x <= 0)
			x = x * (-1);

		double y = o1.Position.y - b;
		if (y <= 0)
			y = y * (-1);

		// Super spezial Sonderfall
		if (o1 instanceof Ant)
			if (y == 0 && b == 0 && ((Ant) o1).getRichtung() == 0) {
				richtung = 180;
				entfernung = x;
				return;
			}

		double strecke = Math.sqrt(x * x + y * y);
		double bruch = y / strecke;
		richtung = Math.toDegrees(Math.asin(bruch));

		if (o1.Position.x < a && o1.Position.y > b)
			richtung = 360 - richtung;

		if (o1.Position.x > a && o1.Position.y > b)
			richtung += 180;

		if (o1.Position.x > a && o1.Position.y < b)
			richtung = 180 - richtung;

		entfernung = strecke;

	}

	public double bestimmeRichtung() {
		return richtung;
	}

	public double bestimmeEntfernung() {
		return entfernung;
	}

}
