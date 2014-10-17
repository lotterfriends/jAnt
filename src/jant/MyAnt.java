package jant;

public class MyAnt extends Ant {
	
  public MyAnt (double x, double y, double winkel, Simulation sim) {
    super(x,y,winkel,sim);
  }
  
  public MyAnt (double x, double y, double winkel, double r, Simulation sim) {
		super(x,y,winkel,r,sim);
  }
  
  public MyAnt (double x, double y, double winkel, double r, double rv, Simulation sim) {
    super(x,y,winkel,r,rv, sim);
  }
  
  // Diese Klasse bestimmt das Verhalten deiner Ameisen.
  // Hier kannst du bestimmen wie dein Volk auf unterschiedliche Ereignisse reagieren soll.

  
  // Wenn die Ameise nichts weiter zu tun hat wird wartet() aufgerufen.
  // Meist empfiehlt es sich f�r den ersten Schritt geheGeradeaus(int) aufzurufen
  @Override
  public void wartet() {
  }
  
  // Wird aufgerufen, wenn die Ameise einen Berg Zucker sieht
  @Override
  public void siehtZucker(Zucker zucker) {
  }
  
  // Wird aufgerufen, wenn die Ameise einen Berg Zucker erreicht. SIe k�nnte jetzt z.B. etwas davon nehmen
  @Override
  public void erreichtZucker(Zucker zucker) {
  }
  

  // Die Ameise sieht einen K�fer.
  @Override
  public void siehtKaefer(Kaefer kaefer) {
  }
  
  // Ein Apfel ist in Sicht
  @Override
  public void siehtApfel(Apfel apfel) {
  }
  
  // Die Ameise hat einen Apfel erreicht. Je mehr Ameisen einen Apfel tragen, desto schneller kommen sie voran
  @Override
  public void erreichtApfel(Apfel apfel) {
  }

  // Die Ameise ist zuhause
  @Override
  public void erreichtBau() {
  }
	
  // Die Ameise findet eine Duftwolke, die eine andere Ameise f�r sie hinterlassen hat.
  // Sie enth�lt eine Information
  @Override
  public void erreichtDuftwolke(int information)	{
  }

  // Wird aufgerufen wenn die Ameise ein drittel ihrer maximalen Reichweite zur�ck gelegt hat.
  // Hat die Ameise ihre maximale Entfernung zur�ck gelegt ohne zum Bau zur�ck zu kehren stirbt sie.
  @Override
  public void wirdMuede() {
  }

}
