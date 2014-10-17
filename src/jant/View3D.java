package jant;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.*;
import javax.media.opengl.*;
import javax.media.opengl.glu.*;

import com.sun.opengl.util.GLUT;

public class View3D extends JFrame implements MouseListener,
		MouseWheelListener, GLEventListener, KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4782064094808522394L;
	GLCanvas canvas;
	DreiDeEsFile ant3D;
	DreiDeEsFile bug3D;
	DreiDeEsFile apfel3D;
	Simulation sim;
	Spielmodi modi;
	GLU glu;
	float field_width = 1000.0f; // 1000
	float field_height = 800.0f;// 800
	float zoom = 1.0f;

	final int MAX_ZOOM = 126;
	final int MIN_ZOOM = 1000;

	float eye[][] = { { 0, 0, 1000 }, { 0, 0, 0 } };

	public View3D(Spielmodi modi) {
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(10);
					} catch (Exception e) {
						e.printStackTrace();
					}
					// resize(getWidth()+1, getHeight());
					// resize(getWidth()-1, getHeight());

					paintAll(getGraphics());

				}
			}
		}).start();

		this.addMouseListener(this);
		this.addMouseWheelListener(this);
		this.addKeyListener(this);
		try {
			ant3D = new DreiDeEsFile("../res/ant.3ds");
			ant3D.scale(1.875f);
			bug3D = new DreiDeEsFile("../res/bug.3ds");
			bug3D.scale(0.0375f);
			apfel3D = new DreiDeEsFile("../res/apfel.3ds");
			apfel3D.scale(0.3f);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return;
		}

		sim = new Simulation(modi);
		this.modi = modi;

		GLCapabilities cap = new GLCapabilities();

		canvas = new GLCanvas(cap);
		canvas.addGLEventListener(this);

		getContentPane().add(canvas);

		setTitle("jAnt 3D");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 400);
		setVisible(true);
	}

	// class SceneView implements GLEventListener
	// {
	public void init(GLAutoDrawable arg0) {
		GL gl = arg0.getGL();
		glu = new GLU();
		// float l_position[] = {0f, 0f, 100f, 1.0f};

		gl.glBlendFunc(GL.GL_ONE, GL.GL_DST_ALPHA);// Blending Funktion f�r
													// Durchsichtigkeit basiered
													// auf dem Quell Alpha Wert
													// ( NEU )

		gl.glDisable(GL.GL_DEPTH_TEST);

		gl.glEnable(GL.GL_LIGHTING);
		gl.glEnable(GL.GL_LIGHT0);
		gl.glEnable(GL.GL_COLOR_MATERIAL);
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glEnable(GL.GL_NORMALIZE);
		gl.glEnable(GL.GL_POLYGON_SMOOTH);
		// gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, l_position, 0);

		float light_pos[] = { 0, 0, 100, 0 };
		float light_color_am[] = { 0.5f, 0.5f, 0.5f, 0f };
		float light_color_diff[] = { 0.1f, 0.1f, 0.1f, 0 };
		float light_color_spec[] = { 0.1f, 0.1f, 0.1f, 1 };

		gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, light_pos, 0);
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, light_color_am, 0);
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, light_color_diff, 0);
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, light_color_spec, 0);
		gl.glEnable(GL.GL_LIGHTING);
		gl.glEnable(GL.GL_LIGHT0);
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glEnable(GL.GL_SMOOTH);

		gl.glClearColor(0.7f, 0.7f, 0.7f, 0.0f);

		final float h = (float) field_width / (float) field_height;
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(50.0f, h, 0.0, -1000.0);
		glu.gluLookAt(eye[0][0], eye[0][1], eye[0][2], eye[1][0], eye[1][1],
				eye[1][2], 0, 1, 0);
		// gl.glOrtho(-50-(field_width/2.0f),
		// 50+field_width/2.0f,-50-(field_height/2.0f), 50+field_height/2.0f,
		// -field_z, field_z);

		gl.glMatrixMode(GL.GL_MODELVIEW);
		// gl.glRotatef(35.0f, 1.0f, 0.0f, 0.0f); // Rotation um die x-Achse
		// gl.glRotatef(-25.0f, 0.0f, 1.0f, 0.0f); // Rotation um die y-Achse
		// gl.glEnableClientState(gl.GL_VERTEX_ARRAY);

	}

	public void display(GLAutoDrawable arg0) {
		GL gl = arg0.getGL();
		GLUT glut = new GLUT();
		sim.runRound();

		// KAMERA
		final float h = (float) field_width / (float) field_height;
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(50.0f, h, 100, -1000.0);
		glu.gluLookAt(eye[0][0], eye[0][1], eye[0][2], eye[1][0], eye[1][1],
				eye[1][2], 0, 1, 0);
		gl.glMatrixMode(GL.GL_MODELVIEW);

		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

		gl.glLoadIdentity();
		gl.glColor3f(0.5f, 0.25f, 0.0f);
		glut.glutSolidTeapot(10.0f);

		gl.glLoadIdentity();
		gl.glColor3f(0.914f, 0.914f, 0.0f);
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex3f(-(field_width / 2), (field_height / 2.0f), 0.0f);
		gl.glVertex3f(-(field_width / 2), -(field_height / 2.0f), 0.0f);
		gl.glVertex3f((field_width / 2), -(field_height / 2.0f), 0.0f);
		gl.glVertex3f((field_width / 2), (field_height / 2.0f), 0.0f);
		gl.glEnd();

		for (Ant ant : sim.getAnts()) {
			// ant.erzeugeDuftwolke(20, 1);
			gl.glColor3f(0.5f, 0.25f, 0.0f);
			for (DreiDeEsObject obj : ant3D.objects) {
				gl.glLoadIdentity();
				gl.glTranslated(ant.Position.x, ant.Position.y, 0);

				gl.glRotatef((float) ant.getRichtung() + 180, 0.0f, 0, 1);
				gl.glRotatef(270, 1.0f, 0, 0);
				drawDreiDeEsObject(arg0, obj);
			}
			gl.glColor3f(1.0f, 1.0f, 1.0f);
			if ("Zucker".equals(ant.getAktuelleLast())) {
				gl.glLoadIdentity();
				gl.glTranslated(ant.Position.x, ant.Position.y, 5);
				gl.glRotatef((float) ant.getRichtung(), 0.0f, 0, 1);
				glut.glutSolidCube(2.5f);
			}
		}

		gl.glColor3f(1.0f, 0.0f, 0.0f);
		for (Kaefer k : sim.getKaeferliste()) {
			for (DreiDeEsObject obj : bug3D.objects) {
				gl.glLoadIdentity();
				gl.glTranslated(k.getPosition().x, k.getPosition().y, 0);// ??
				gl.glRotatef((float) k.winkel + 90.0f, 0.0f, 0, 1);// ??
				gl.glRotatef(270, 1.0f, 0, 0);
				drawDreiDeEsObject(arg0, obj);
			}
		}

		for (Apfel ap : sim.getAepfel()) {
			for (DreiDeEsObject obj : apfel3D.objects) {
				if (obj.name.equals("subdivis00")) {
					gl.glColor3f(0.5f, 0.25f, 0.0f);
				} else {
					gl.glColor3f(0.0f, 1.0f, 0.3f);
				}
				gl.glLoadIdentity();
				gl.glTranslated(ap.getPositionX(), ap.getPositionY(), 0);
				gl.glRotatef(270, 1.0f, 0, 0);
				drawDreiDeEsObject(arg0, obj);
			}
		}

		gl.glColor3f(1.0f, 1.0f, 1.0f);
		for (Zucker z : sim.getZuckerberge()) {
			gl.glLoadIdentity();
			gl.glTranslated(z.getPosition().x, z.getPosition().y, 0);
			double faktor = (z.getMenge() / 9.0);
			glut.glutSolidCone(faktor * 9.0, faktor * 12.0, 20, 4);
		}

		gl.glEnable(GL.GL_BLEND); // Turn Blending On
		gl.glColor4f(1.0f, 1.0f, 0.0f, 0.5f);
		for (Duftwolke d : sim.getDuftwolken()) {
			gl.glLoadIdentity();
			gl.glTranslated(d.getPosition().x, d.getPosition().y, 0);
			double faktor = d.getRadius();
			glut.glutSolidSphere(faktor, 10, 10);
		}
		gl.glDisable(GL.GL_BLEND);
		// TODO
		gl.glFinish();
		gl.glFlush();
	}

	public void drawDreiDeEsObject(GLAutoDrawable arg0, DreiDeEsObject obj) {
		GL gl = arg0.getGL();
		gl.glRotatef(90, 1.0f, 0, 0);
		/*
		 * gl.glVertexPointer(3, gl.GL_FLOAT, 0,
		 * obj.vertexBufferObject.vertexe); gl.glDrawArrays (gl.GL_TRIANGLES, 0,
		 * obj.faceCount); gl.glFlush();
		 */
		gl.glBegin(GL.GL_TRIANGLES);
		for (int i = 0; i < obj.faceCount; i++) {
			for (int f = 0; f < 3; f++) {
				float x = obj.vertexe[obj.faces[i][f]][0];
				float y = obj.vertexe[obj.faces[i][f]][1];
				float z = obj.vertexe[obj.faces[i][f]][2];
				gl.glVertex3f(x, y, z);
			}
		}
		gl.glEnd();
		/*
		 * 
		 * for(int i=0; i<obj.faceCount9;) { gl.glBegin(gl.GL_TRIANGLES);
		 * for(int f=0; f<3; f++) {
		 * gl.glVertex3f(obj.vertexBufferObject.fVertexe[i++],
		 * obj.vertexBufferObject.fVertexe[i++],
		 * obj.vertexBufferObject.fVertexe[i++]); } gl.glEnd(); }
		 */

	}

	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3,
			int arg4) {
		GL gl = arg0.getGL();
		final float h = (float) field_width / (float) field_height;
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(50.0f, h, 200.0, -1000.0);
		glu.gluLookAt(eye[0][0], eye[0][1], eye[0][2], eye[1][0], eye[1][1],
				eye[1][2], 0, 1, 0);
		gl.glMatrixMode(GL.GL_MODELVIEW);
	}

	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
	}

	// }

	@Override
	public void mouseClicked(MouseEvent arg0) {

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		if (arg0.getWheelRotation() < 0) { // UP
			if (eye[0][2] > MAX_ZOOM) {
				eye[0][2] -= 25.0f;
			}
		} else {
			if (eye[0][2] < MIN_ZOOM) {
				eye[0][2] += 25.0f;
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getKeyCode() == KeyEvent.VK_A) {
			eye[1][1] += 10;
		} else if (arg0.getKeyCode() == KeyEvent.VK_Y) {
			eye[1][1] -= 10;
		}

		if (arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
			eye[0][0] += 10;
			eye[1][0] += 10;
		} else if (arg0.getKeyCode() == KeyEvent.VK_LEFT) {
			eye[0][0] -= 10;
			eye[1][0] -= 10;
		}
		if (arg0.getKeyCode() == KeyEvent.VK_UP) {
			eye[0][1] += 10;
			eye[1][1] += 10;
		} else if (arg0.getKeyCode() == KeyEvent.VK_DOWN) {
			eye[0][1] -= 10;
			eye[1][1] -= 10;
		}
		// System.out.println("adssda"+arg0.getButton());
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public String run() {
		return Integer.toString(sim.punkte);
	}

}
