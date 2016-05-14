import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Image;
import java.awt.Label;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

public class LadowanieZdjecia extends JPanel implements ActionListener {

	public JFileChooser fc;
	public File filetoSave;
	public JPanel zaladuj;
	public String path;
	public JButton wczytaj;
	public BufferedImage Image2;
	public BufferedImage detectionImage;
	public File filetoSave1;
	int returnVal;
	public paint dziala;
	public JButton detection;
	public JButton kamera;
	public JPanel detekcjaa;
	public paint poDetekcji;

	public LadowanieZdjecia() {
		zaladuj = new JPanel();

		wczytaj = new JButton("Zaladuj");
		detection = new JButton("Detekcja");
		kamera = new JButton("Kamera");
		kamera.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//JFrame Vd = new JFrame("Detekcja");

				//Vd.setSize(800, 600);
				PrzechwytVideo videe = new PrzechwytVideo();
					
		
				//Vd.add(videe.panelDoWyswietlenia);

				//Vd.setVisible(true);
			}
		});
		detection.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JFrame DF = new JFrame("Detekcja");

				DF.setSize(400, 500);

				detekcjaa = new JPanel();
				DetectionFace face = new DetectionFace();

				String sciezka = "D:/Workspace_java/Obrazy/CZy.png";
				File plik = new File(sciezka);
				try {
					detectionImage = ImageIO.read(plik);
				} catch (IOException edd) {
					System.out.println("Nie pobrano po detekcji");
				}
				poDetekcji.setSize(400, 500);
				poDetekcji.picture = detectionImage;

				// detekcjaa.add(poDetekcji,BorderLayout.CENTER);
				poDetekcji.repaint();
				DF.add(poDetekcji);
				DF.setVisible(true);

			}
		});
		wczytaj.addActionListener(this);
		zaladuj.add(wczytaj, BorderLayout.NORTH);
		zaladuj.add(detection, BorderLayout.NORTH);
		zaladuj.add(kamera, BorderLayout.SOUTH);
		zaladuj.setSize(new Dimension(400, 500));

		dziala = new paint();
		poDetekcji = new paint();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == wczytaj) {
			fc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg", "gif", "png");
			fc.addChoosableFileFilter(filter);
			returnVal = fc.showOpenDialog(null);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				filetoSave = fc.getSelectedFile();
				path = filetoSave.getAbsolutePath();

				getPicture(path);
				try {
					File zapis = new File("zapisany.jpg");
					ImageIO.write(Image2, "png", zapis);
				} catch (IOException exxx) {
					System.out.println("Nie zapisano");
				}

			}

			dziala.setSize(400, 500);
			dziala.picture = Image2;
			zaladuj.add(dziala, BorderLayout.CENTER);
			dziala.repaint();
			System.out.println(path);

		}

	}

	public BufferedImage getPicture(String down) {
		File obraz = new File(down);
		try {
			Image2 = ImageIO.read(obraz);
		} catch (IOException w) {
			System.out.println("Nie pobrano");
		}

		return Image2;
	}

}
