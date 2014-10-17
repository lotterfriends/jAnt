package jant;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.Toolkit;
import java.util.*;
import javax.swing.JFrame;

public class View2D extends JFrame implements WindowListener, KeyListener,
		MouseListener {
	private static final long serialVersionUID = -5545356498091954477L;
	private Simulation sim;
	private long CurrentFrameRate = 0;
	private boolean bShowViewRadius = false;
	private boolean bPaused = false;
	private boolean bShowHighscore = false;
	private boolean bGirlieMode = false;
	private Spielmodi modi;
	private int runden = 300000;

	// Farben der verschiedenen Objekte
	private Color COLOR_AMEISE = Color.black;
	private Color COLOR_KAEFER = Color.red;
	private Color COLOR_HINTERGRUND = new Color(150, 100, 40);
	private Color COLOR_BAU = Color.blue;
	private Color COLOR_DUFTWOLKE = new Color(255, 128, 0);
	private Color COLOR_ZUCKER = Color.white;
	private Color COLOR_APFEL = Color.green;

	public void mouseExited(MouseEvent m) {
	}

	public void mouseEntered(MouseEvent m) {
	}

	public void mouseReleased(MouseEvent m) {
	}

	public void mousePressed(MouseEvent m) {
	}

	public void mouseClicked(MouseEvent m) {
		Collision coll;
		int x, y;

		if (m.getButton() == MouseEvent.BUTTON3) {
			sim.setMarker(null);
			return;
		}

		x = m.getX() - 500;
		y = m.getY() - 400; // + 11;

		for (int nAnts = 0; nAnts < sim.getAnts().size(); nAnts++) {
			AllObject a = (AllObject) sim.getAnts().get(nAnts);
			AllObject b = new AllObject(x, y, 7);

			coll = new Collision(a, b);

			if (coll.collides(Collision.CollisionMode.Body)) {
				sim.setMarker(new AntMark(sim.getAnts().get(nAnts)));
			}
		}
	}

	public void keyTyped(KeyEvent e) {

	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == ' ')
			bPaused = !bPaused;
		if (e.getKeyChar() == '#')
			bShowViewRadius = !bShowViewRadius;
		/*
		if (e.getKeyChar() == 'g') {
			bGirlieMode = !bGirlieMode;
			if (bGirlieMode) {
				COLOR_AMEISE = Color.black;
				COLOR_KAEFER = new Color(255, 0, 255);
				COLOR_HINTERGRUND = Color.pink;
				COLOR_BAU = Color.blue;
				COLOR_DUFTWOLKE = new Color(255, 255, 0);
				COLOR_ZUCKER = Color.white;
				COLOR_APFEL = Color.orange;
			} else {
				COLOR_AMEISE = Color.black;
				COLOR_KAEFER = Color.red;
				COLOR_HINTERGRUND = new Color(150, 100, 40);
				COLOR_BAU = Color.blue;
				COLOR_DUFTWOLKE = new Color(255, 128, 0);
				COLOR_ZUCKER = Color.white;
				COLOR_APFEL = Color.green;
			}
		}
		*/
		if (e.getKeyChar() == '+') {
			System.out.println("Duftwolken: " + sim.getDuftwolken().size());
			System.out.println("Ameisen:   " + sim.getAnts().size());
		}
	}

	public void keyReleased(KeyEvent e) {

	}

	public Graphics FSBF(Graphics g) {

		/*
		 * for (int x = 1; x < g. - 1; x++) { for (int y = 1; y <
		 * g.getClipRect().height - 1; y++) {
		 * 
		 * } }
		 */

		// g.setColor(Color.red);
		// g.drawOval(10, 10, 20, 20);
		return g.create();

	}

	public String run() {
		long nOldTime = 0, nNewTime = 0, nFrames = 0, nSleepTimer = 1000, nFramesWished = 90;

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setUndecorated(false);
		this.setVisible(true);
		this.addWindowListener(this);
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.createBufferStrategy(2);

		Dimension frameSize = new Dimension(1000, 800);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		int top = (screenSize.height - frameSize.height) / 2;
		int left = (screenSize.width - frameSize.width) / 2;
		this.setLocation(left, top);
		this.setSize(frameSize);

		nOldTime = System.currentTimeMillis();

		for (int i = 0; i < runden; i++) {
			nNewTime = System.currentTimeMillis();

			if (nOldTime + 1000 <= nNewTime) {
				nSleepTimer = nSleepTimer * nFrames / nFramesWished;
				CurrentFrameRate = nFrames;
				nFrames = 0;
				nOldTime = nNewTime;
			}

			if (!bPaused) sim.runRound();
			if (sim.getAnts().size() <= 0) break;

			repaint();

			nFrames += 1;

			try {
				Thread.sleep(nSleepTimer);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		if (modi instanceof Invasion) {
			System.out.println("Überlebte Runden: " + sim.getAktRound());
		} else {
			System.out.println("Ameisen: " + sim.getAnts().size());
			System.out.println("Erreichte Punkte: " + sim.punkte);
		}

		bPaused = false;
		while (!bPaused) {
			bShowHighscore = true;
			repaint();
		}

		if (modi instanceof Invasion) {
			return "" + sim.getAktRound();
		} else {
			return "" + sim.punkte;
		}
	}

	// Zeichen Methode des JFrames die von Mainprogramm ueberschireben wird
    @Override
	public void paint(Graphics g) {
		// Graphics g;
		AntMark mark;
		BufferStrategy strategy = this.getBufferStrategy();
        g = strategy.getDrawGraphics();

		// "Waldboden" malen
		g.setColor(COLOR_HINTERGRUND);
		g.fillRect(0, 0, 1000, 800);

		// Duftwolken
		ArrayList<Duftwolke> duftwolken = sim.getDuftwolken();
		for (Iterator<Duftwolke> it = duftwolken.iterator(); it.hasNext();) {
			Duftwolke cloud = (Duftwolke) it.next();

			g.setColor(COLOR_DUFTWOLKE);
			int x = (int) cloud.Position.x + (this.getWidth() / 2) - (int) (cloud.getRadius() * 0.5);
			int y = (int) cloud.Position.y + (this.getHeight() / 2)	- (int) (cloud.getRadius() * 0.5);
			g.fillOval(x, y, (int) (cloud.getRadius() * 2), (int) (cloud.getRadius() * 2));
		}

		// Bau
		g.setColor(COLOR_BAU);
		g.drawOval(getWidth() / 2 - 5, getHeight() / 2 - 5, 10, 10);

		// Ameisen
		for (int i = 0; i < sim.getAnts().size(); i++) {
			Ant ant = sim.getAnts().get(i);
			int x = (int) (ant.Position.x + (this.getWidth() / 2) - 1.5);
			int y = (int) (ant.Position.y + (this.getHeight() / 2) - 1.5);

			g.setColor(COLOR_AMEISE);
			g.drawOval(x, y, 3, 3);

			if (bShowViewRadius) {
				g.drawOval(x - 20, y - 20, 20 * 2, 20 * 2);
			}

			if ("Zucker".equals(ant.getAktuelleLast())) {
				g.setColor(COLOR_ZUCKER);
				g.fillOval(x + 1, y + 1, 5, 5);
			}
		}

		// Kaefer
		g.setColor(COLOR_KAEFER);
		ArrayList<Kaefer> kaeferliste = sim.getKaeferliste();
		for (Iterator<Kaefer> it = kaeferliste.iterator(); it.hasNext();) {
			Kaefer kaefer = (Kaefer) it.next();
			int x = (int) kaefer.Position.x + (this.getWidth() / 2) - 5;
			int y = (int) kaefer.Position.y + (this.getHeight() / 2) - 5;
			g.fillOval(x, y, 10, 10);

		}

		// Apefel
		g.setColor(COLOR_APFEL);
		ArrayList<Apfel> aepfel = sim.getAepfel();
		for (Iterator<Apfel> it = aepfel.iterator(); it.hasNext();) {
			Apfel apfel = (Apfel) it.next();
			int x = (int) apfel.getPositionX() + (this.getWidth() / 2) - 5;
			int y = (int) apfel.getPositionY() + (this.getHeight() / 2) - 5;
			g.fillOval(x, y, 10, 10);
		}

		// Zucker
		g.setColor(COLOR_ZUCKER);
		ArrayList<Zucker> zuckerberge = sim.getZuckerberge();
		for (Iterator<Zucker> it = zuckerberge.iterator(); it.hasNext();) {
			Zucker zucker = (Zucker) it.next();
			int menge = zucker.getMenge();
			int durchmesserZucker = 5+menge/4;
			int x = (int) zucker.Position.x + (this.getWidth() / 2) -durchmesserZucker/2;
			int y = (int) zucker.Position.y + (this.getHeight() / 2) -durchmesserZucker/2;
			g.fillOval(x, y, durchmesserZucker, durchmesserZucker);
		}

		g.setColor(new Color(255, 0, 0, 128));
		mark = sim.getMarker();

		if (mark != null) {
			int x = (int) mark.getMarkedAnt().Position.x
					+ (this.getWidth() / 2) - 5;
			int y = (int) mark.getMarkedAnt().Position.y
					+ (this.getHeight() / 2) - 5;

			g.drawRect(x, y, 10, 10);
			g.drawRect(x - 1, y - 1, 12, 12);
			g.drawRect(x - 2, y - 2, 14, 14);

			// draw ant-information
			g.setColor(new Color(0, 0, 0, 128));
			g.setFont(new Font("Tahoma", Font.BOLD, 10));
			g.drawString("Lebenspunkte: "
                    + mark.getMarkedAnt().getAktuelleEnergie()
                    + "/500", 10, 70);
		}

		g.setColor(new Color(0, 0, 0, 30));
		g.setFont(new Font("Tahoma", Font.BOLD, 16));
		g.drawString("Punkte: " + sim.punkte + " FPS: " + CurrentFrameRate, 10,
				50);

		if (bShowHighscore) {
			g.setColor(new Color(0, 255, 0, 128));
			g.setFont(new Font("Tahoma", Font.BOLD, 24));

			if (modi instanceof Invasion) {
				g.drawString("Game Over!\n\nÜberlebte Runden: "
						+ sim.getAktRound(), 10, 100);
			} else {
				g.drawString("Game Over!\n\nPunkte: " + sim.punkte, 10, 100);
			}
		}
		g = FSBF(g);
		strategy.show();
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	public View2D(Spielmodi modi) {
		setTitle("jAnt");
		sim = new Simulation(modi);
		if (modi.getMaxRunden() != 0)
			runden = modi.getMaxRunden();
		this.modi = modi;
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// try {
			if (bShowHighscore) {
				bPaused = true;
		//		arg0.wait(1000);
			}
		// } catch (InterruptedException e) {
		// 	e.printStackTrace();
		// }
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

}
