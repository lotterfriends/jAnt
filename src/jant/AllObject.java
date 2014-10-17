package jant;

public class AllObject {
	public MyPosition Position = new MyPosition(0, 0);
	private double radius;
	private double viewRadius;

	public AllObject(double x, double y) {
		Position.x = x;
		Position.y = y;
		radius = 0;
		viewRadius = 0;
	}

	public AllObject(double x, double y, double r) {
		Position.x = x;
		Position.y = y;
		radius = r;
		viewRadius = 0;
	}

	public AllObject(double x, double y, double r, double vr) {
		Position.x = x;
		Position.y = y;
		radius = r;
		viewRadius = vr;
	}

	public MyPosition getPosition() {
		return Position;
	}

	public double getRadius() {
		return radius;
	}

	public double getViewRadius() {
		return viewRadius;
	}

    @Override
	public String toString() {
		int pox = (int) (Position.x * 100);
		int poy = (int) (Position.y * 100);
		double po2x = pox / 100D;
		double po2y = poy / 100D;
		String string = getClass().getName().substring(5,
				getClass().getName().length());
		string += " (x = " + po2x + "; y = " + po2y + " r = " + radius
				+ " vr = " + viewRadius + ")";
		return string;
	}
}
