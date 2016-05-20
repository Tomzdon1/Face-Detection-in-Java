import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MenuGlowne extends JPanel {

	public JPanel menu;
	private JButton detekcjaZdjecie;
	private JButton detekcjaScreen;
	private JButton detekcjaLive;
	private JLabel napisGlowny;

	private String napis = "DETECTION FACE";

	public MenuGlowne() {

		menu = new JPanel();
		detekcjaLive = new JButton("WebCam detekcja");
		detekcjaScreen = new JButton("Take a picture");
		detekcjaZdjecie = new JButton("Load a image");
		napisGlowny = new JLabel(napis);

		detekcjaLive.setBounds(100, 150, 300, 50);
		detekcjaScreen.setBounds(100, 250, 300, 50);
		detekcjaZdjecie.setBounds(100, 350, 300, 50);

		napisGlowny.setFont(new Font("Arial Bold", Font.BOLD, 18));
		napisGlowny.setBounds(170, 10, 300, 50);

		menu.setSize(500, 600);

		menu.setLayout(null);
		menu.add(napisGlowny);
		menu.add(detekcjaScreen);
		menu.add(detekcjaZdjecie);
		menu.add(detekcjaLive);
		menu.setBackground(Color.GRAY);
		detekcjaLive.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new FaceDetection();
			}
		});
		detekcjaScreen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new PrzechwytVideo();

			}
		});
		detekcjaZdjecie.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new LadowanieZdjecia();

			}
		});

	}

}
