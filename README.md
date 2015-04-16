jAnt
====

[![Join the chat at https://gitter.im/lotterfriends/jAnt](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/lotterfriends/jAnt?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

learn to program with a little ant simulator 

Authors:
- Daniel Horn
- André Fenske
- M.D. 
- André Tarnowsky

#Ameisenbefehlsliste und Methoden

##Drehen</h2>

Eine Ameise kann sich entweder drehen oder geradeaus laufen, wobei ein Drehbefehl immer Vorrang hat. Angegeben wird der Winkel für eine Drehung in Grad entsprechend dem Uhrzeigersinn. So ist 0 Grad rechts, 90 Grad unten usw. bis 360 Grad wieder rechts ist. Größere Winkel werden automatisch umgerechnet und die Ameise wird immer die kürzere Drehrichtung wählen, also maximal 180 Grad.

<table>
	<tr><td>dreheInRichtung(double)</td><td>Die Ameise dreht sich in die angegebene Richtung.</td></tr>
	<tr><td>dreheUm()</td><td>Die Ameise dreht sich um 180° in die entgegen gesetzte Richtung. Hat die selbe Wirkung wie dreheUmWinkel(180).</td></tr>
	<tr><td>dreheUmWinkel(double)</td><td>Gibt der Ameise eine neue Ausrichtung. Sie wird stehen bleiben und sich drehen. Negativ links und Positiv rechts herum.</td></tr>
	<tr><td>dreheZuZiel(AllObject)</td><td>Die Ameise dreht sich in Richtung des angegebenen Ziels. Z.B. dreheZuZiel(zucker).</td></tr>
</table>

##Gehen

Die Längenangabe entspricht Schritten. Zum Vergleich: Eine Ameise misst 3 Schritt, ein Apfel

<table>
	<tr><td>bleibStehen()</td><td>Die Ameise bleibt stehen und vergisst ihr aktuelles Ziel. In der nächsten Runde wird das Ereignis wartet() aufgerufen werden.</td></tr>
	<tr><td>geheGeradeaus()<br />geheGeradeaus(double)</td><td>Die Ameise geht geradeaus. Das Ziel bleibt dabei unverändert. Die Ameise wird ihr Ziel wieder anvisieren, nach dem sie die angegebene Strecke zurück gelegt hat.</td></tr>
	<tr><td>geheWegVonZiel(AllObject, double)</td><td>Die Ameise dreht sich in die Richtung die vom angegebenen Ziel weg zeigt und geht dann die angegebene Entfernung geradeaus. Das Ziel der Ameise bleibt unangetastet.</td></tr>
	<tr><td>geheZuZiel(AllObject)</td><td>Die Ameise speichert das angegebene Ziel und geht dort hin</td></tr>
	<tr><td>geheZuBau()</td><td>Die Ameise speichert den Bau als Ziel und geht dort hin. Hat die selbe Wirkung wie geheZuZiel(bau).</td></tr>
</table>

##Nahrung

Gesammelte Nahrung erhöht den Punktestand. Nahrung muss zum Bau befördert werden. Ein Apfel wird erst bei 5 Trägern mit maximaler Geschwindigkeit transportiert.

<table>
	<tr><td>nimm(NoneLivingObject)</td><td>Die Ameise nimmt die angegebene Nahrung auf.</td></tr>
	<tr><td>lasseNahrungFallen()</td><td>Die Ameise lässt alle gerade getragene Nahrung fallen. Zucker geht dabei verloren, Äpfel werden liegen gelassen und können wieder aufgenommen werden. Der Befehl muss nicht ausgeführt werden um Nahrung im Bau abzuliefern. Das passiert dort automatisch.</td></tr>
</table>

##Volkeigenschaften

<table>
	<tr><td>getAngriff()</td><td>Gibt den Angriffswert der Ameise an. Er bestimmt wie viele Lebenspunkte die Ameise einem Gegner in jeder Runde abzieht. Ein Kampf wird automatisch abgewickelt.</td></tr>
	<tr><td>getMaximaleGeschwindigkeit()</td><td>Gibt die maximale Geschwindigkeit der Ameise an.</td></tr>
	<tr><td>getReichweite()</td><td>Gibt die Reichweite in Schritten an die die Ameise zurücklegen kann, bevor sie vor Hunger stirbt. Nachdem die Ameise ein Drittel dieser Strecke zurückgelegt hat, wird das Ereignis wirdMuede() aufgerufen und der Wert von istMuede auf wahr gesetzt.</td></tr>
	<tr><td>getDrehgeschwindigkeit()</td><td>Gibt an wie viel Grad pro Runde sich die Ameise drehen kann.</td></tr>
</table>

##Ameisen-Eigenschaften

<table>
	<tr><td>getAktuelleEnergie()</td><td>Gibt an über wie viel Lebenspunkte die Ameise noch verfügt. Hat die Ameise 0 Lebenspunkte stirbt sie.</td></tr>
	<tr><td>getAktuelleLast()</td><td>Gibt die aktuelle Last der Ameise als Zeichenkette an. Diese ist entweder "Zucker", "Apfel" oder null.</td></tr>
	<tr><td>getAktuelleGeschwindigkeit()</td><td>Gibt die aktuell mögliche Geschwindigkeit der Ameise an. Der Wert wird von der aktuellen Last beeinflusst. Ameisen die Zucker tragen bewegen sich nur mit halber Geschwindigkeit, beim alleine tragen eines Apfels sogar nur mit 1/5 ihrer maximalen Geschwindigkeit</td></tr>
	<tr><td>getSichtweite()</td><td>Gibt die Anzahl der befreundeten Ameisen im Wahrnehmungsradius der Ameise zurück.</td></tr>
	<tr><td>getEntfernungZuBau()</td><td>Die Entfernung zum Bau in Schritt.</td></tr>
	<tr><td>getZiel()</td><td>Aktuelles Ziel der Ameise. Hat die Ameise gerade kein Ziel zeigt der Verweis ins Leere.</td></tr>
	<tr><td>getIstMuede()</td><td>Gibt an ob die Ameise müde ist. Das ist der Fall, wenn sie bereits über ein Drittel ihrer maximalen Strecke zurück gelegt hat. Wird gleichzeitig mit dem Ereignis wirdMuede() gesetzt.</td></tr>
	<tr><td>getReststrecke()</td><td>Gibt an wie viele Schritte die Ameise noch geradeaus geht bevor sie entweder ihr Ziel erreicht hat oder es erneut anvisiert.</td></tr>
	<tr><td>getRestwinkel()</td><td>Gibt an wie viel Grad die Ameise sich noch drehen wird, bevor sie wieder geradeaus läuft. Verringert sich in jeder Runde um die Drehgeschwindigkeit.</td></tr>
	<tr><td>getRichtung()</td><td>Gibt den aktuellen Blickwinkel der Ameise an.</td></tr>
	<tr><td>getAngekommen()</td><td>Gibt an ob die Ameise an ihrem Ziel angekommen ist. In der nächsten Runde wird das Ereignis wartet() aufgerufen werden.</td></tr>
	<tr><td>getZurueckgelegteStrecke()</td><td>Diese Funktion gibt die Gesamtzahl der Schritte an, die die Ameise seit ihrem letzten Besuch im Bau zurück gelegt hat.</td></tr>
</table>

##Hilfsbefehle für Nahrungsaufnahme

<table>
	<tr><td>brauchtNochTraeger(apfel)</td><td>Ermittelt ob der angegebene Apfel noch Träger braucht, um mit maximaler Geschwindigkeit transportiert werden zu können. Die maximale Anzahl an Trägern beträgt 5.</td></tr>
</table>

##Hilfsbefehle für Koordination

Die Klasse Koordinate.java ist ein Hilfsobjekt und wird mit einem Konstrukter erstellt, der zwei Objekte erfordert. Anschließend können die beide Hilfsmethoden, bezogen auf diese Objekte, genutzt werden.

<table>	
	<tr><td>bestimmteEntfernung()</td><td>Bestimmt die Entfernung in Schritten zwischen den angegebenen Spielelementen.</td></tr>
	<tr><td>bestimmeRichtung()</td><td>Bestimmt die Richtung ausgehend vom ersten Element, hin zum zweiten Element.</td></tr>
</table>

<table>
	<tr><td>Zufall.zahl(int)<br />Zufall.zahl(int,int)</td><td>Erzeugt eine zufällige Zahl zwischen den angegebenen Grenzen. Wenn nur ein Parameter mitgegeben wird ist die untere Grenze 0. Das Ergebnis ist einschließlich des Intervalls.</td></tr>
</table>

##Befehle zur Koordination

<table>
	<tr><td>erzeugeDuftwolke (int,int)</td><td>Erwartet einen Radius und eine Information, um eine Duftmarkierung zu erzeugen, die von anderen Ameisen wahrgenommen werden kann. Die Information wird dabei an die andere Ameise übermittelt und kann für vielfältige Zwecke verwendet werden.</td></tr>
</table>

#Spielmodi

Die Simulation unterstützt drei verschiedene Spielmodi, die unterschiedliche Strategien in der KI-Programmierung erfordern. Das Spiel endet in jedem Fall, wenn keine Ameisen mehr vorhanden sind. Die besten fünf Ergebnisse werden in die jeweilige Highscoreliste eingetragen.

##Classic

Ziel ist es eine möglichst hohe Punktzahl in 30 000 Runden zu erreichen. Zum Bau gebrachte Nahrung und getötete Käfer erhöhen den Punktestand, eine gestorbene Ameise senkt ihn.

##Arcade

Wie auch im Classicmodus ist eine möglichst hohe Punktzahl das Ziel, dafür ist das Spiel erst dann zuende, wenn keine Ameisen mehr auf dem Feld sind. Je weiter der Spielfortschritt desto mehr Käfer werden die Ameisen bedrängen. Der Tod einer Ameise reduziert die Punkte nicht.

##Invasion

Wie auch im Arcade wird die KI im späteren Verlauf mit immer mehr Käfern konfrontiert, allerdings kommen hier für je 50 erwirtschaftete Punkte neue Ameisen nach, bis zu ihrer Maximalzahl von 100. Die Punkte werden dabei entsprechend reduziert. Ziel ist es hier eine möglichst hohe Anzahl an Runden noch Ameisen auf dem Feld zu haben.

#Koordinatensystem


```
             
        -400, 300                            400, 300
                 ----------------------------
                 |                          |
                 |                          |
                 |                          |
                 |                          |
                 |                          |
                 |                          |
                 |                          |
                 |                          |
                 |                          |
                 |                          |
                 ----------------------------
       -400, -300                            400, -300                         
       
       
       

             
             0, 0                            800, 0
                 ----------------------------
                 |                          |
                 |                          |
                 |                          |
                 |                          |
                 |                          |
                 |                          |
                 |                          |
                 |                          |
                 |                          |
                 |                          |
                 ----------------------------
           0, 600                            800, 600                         
           
           
 ```          

