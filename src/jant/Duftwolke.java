package jant;

public class Duftwolke extends AllObject {
	private int information;
	private int lifeTime;

	public Duftwolke(double x, double y, double r, int information) {
		super(x, y, r);
		this.information = information;
		this.lifeTime = (int) (-3.0 * r + 300);
	}

	public int getInformation() {
		return information;
	}

	public int getLifetime() {
		return lifeTime;
	}

	public void reduceLifetime() {
		lifeTime--;
	}
}
