package jant;

public class Collision {
	private AllObject obj1;
	private AllObject obj2;

	public enum CollisionMode {
		// Body = Zusammenstoß, View = Sichtzusammenstoß
		Body, View;
	}

	public Collision(AllObject obj1, AllObject obj2) {
		this.obj1 = obj1;
		this.obj2 = obj2;
	}

	public AllObject getObj1() {
		return obj1;
	}

	public AllObject getObj2() {
		return obj2;
	}

	public boolean collides(CollisionMode cMode) {
		double r1, r2, x1, x2, y1, y2;

		if (cMode == CollisionMode.Body) {
			r1 = obj1.getRadius();
		} else {
			r1 = obj1.getViewRadius();
		}
		x1 = obj1.getPosition().x;
		y1 = obj1.getPosition().y;

		r2 = obj2.getRadius();
		x2 = obj2.getPosition().x;
		y2 = obj2.getPosition().y;

		return ((r1 + r2) * (r1 + r2) >= (x1 - x2) * (x1 - x2) + (y1 - y2)
				* (y1 - y2));
	}
}
