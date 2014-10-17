package jant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Fenster implements ActionListener {

	boolean schalter = true;

	Spielmodi modi;
	String benutzer;
	String volk;

	Font tahoma = new Font("Tahoma", 0, 13);
	JTextField meldungen;
	JPanel panel;
	JLabel labelVolk;
	JLabel labelBenutzer;
	JTextField feldVolk;
	JTextField feldBenutzer;
	JButton buttonClassic;
	JButton buttonArcade;
	JButton buttonInvasion;
	JButton buttonLetzerModi;

	JFrame frame = new JFrame();

	Fenster(Spielmodi modi, String benutzer, String volk) {
		this.modi = modi;
		this.benutzer = benutzer;
		this.volk = volk;
	}

	@SuppressWarnings("deprecation")
	public void start() {

		frame.setTitle("jAnt - Auswahlmenue");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = new JPanel();

		labelVolk = new JLabel("Volk:");
		labelBenutzer = new JLabel("Benutzer:");
		labelVolk.setFont(tahoma);
		labelBenutzer.setFont(tahoma);

		if (benutzer == null || benutzer.trim().equals("")) benutzer = "Benutzername";
		if (volk == null || volk.trim().equals(""))			volk = "Volkname";

		feldVolk = new JTextField(volk, 20);
		feldBenutzer = new JTextField(benutzer, 20);

		buttonLetzerModi = new JButton("letzten Spielmodus");
		buttonLetzerModi.addActionListener(this);

		buttonArcade = new JButton("Arcade");
		buttonArcade.addActionListener(this);

		buttonInvasion = new JButton("Invasion");
		buttonInvasion.addActionListener(this);

		buttonClassic = new JButton("Classic");
		buttonClassic.addActionListener(this);

		meldungen = new JTextField(" ", 20);

		meldungen.disable();
		panel.add(labelBenutzer);
		panel.add(feldBenutzer);
		panel.add(labelVolk);
		panel.add(feldVolk);
		panel.add(buttonClassic);
		panel.add(buttonArcade);
		panel.add(buttonInvasion);
		if (modi != null)
			panel.add(buttonLetzerModi);
		panel.add(meldungen);

		frame.setContentPane(panel);

		Dimension frameSize = new Dimension(300, 200);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int top = (screenSize.height - frameSize.height) / 2;
		int left = (screenSize.width - frameSize.width) / 2;
		frame.setLocation(left, top);
		frame.setSize(frameSize);
		frame.setResizable(false);
		frame.setVisible(true);

		while (schalter) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public Spielmodi getSpielmodi() {
		return modi;
	}

	public String getBenutzer() {
		return benutzer;
	}

	public String getVolk() {
		return volk;
	}

	public void actionPerformed(ActionEvent ae) {
		if (feldBenutzer.getText().trim().equals("") || feldBenutzer.getText().trim().equals("Benutzername")) {
			meldungen.setText("Benutzername fehlt");
			return;
		}
		if (feldVolk.getText().trim().equals("") || feldVolk.getText().trim().equals("Volkname")) {
			meldungen.setText("Volkname fehlt");
			return;
		}
		JButton item = (JButton) ae.getSource();

		if (item.getText().equals("Arcade"))
			modi = new Arcade();
		if (item.getText().equals("Classic"))
			modi = new Classic();
		if (item.getText().equals("Invasion"))
			modi = new Invasion();

		benutzer = feldBenutzer.getText().trim();
		volk = feldVolk.getText().trim();

		schalter = false;
		frame.dispose();
	}

}
