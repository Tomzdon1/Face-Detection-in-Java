import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.nio.ByteOrder;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

public class PrzechwytVideo extends JFrame implements ActionListener {

	public JPanel panelDoWyswietlenia;
	public JButton kameraON;
	public JButton kameraOFF;
	private DaemonThread myThread = null;
	int count = 0;
	VideoCapture webSource = null;

	Mat frame = new Mat();
	MatOfByte mem = new MatOfByte();

	public PrzechwytVideo() {
		JFrame tom=new JFrame("Zdjecie");
		panelDoWyswietlenia = new JPanel();
		kameraON = new JButton("ON");
		kameraOFF = new JButton("OFF");
		panelDoWyswietlenia.setSize(800, 600);
		kameraON.addActionListener(this);
		kameraOFF.addActionListener(this);
		kameraON.setBounds(300, 450, 75, 75);
		kameraOFF.setBounds(400, 450, 75, 75);
	
		panelDoWyswietlenia.setLayout(null);
		panelDoWyswietlenia.add(kameraON);
		panelDoWyswietlenia.add(kameraOFF );
		tom.setSize(800, 600);
		tom.getContentPane().add(panelDoWyswietlenia);
		tom.setLocation(500, 400);
		tom.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == kameraON) {
			webSource = new VideoCapture(0);
			myThread = new DaemonThread();
			Thread t = new Thread(myThread);
			t.setDaemon(true);
			myThread.runnable = true;
			t.start();
			kameraON.setEnabled(false); // start button
			kameraOFF.setEnabled(true); // stop button
		}
		if (e.getSource() == kameraOFF) {
			myThread.runnable = false;
			kameraON.setEnabled(true);
			kameraOFF.setEnabled(false);

			webSource.release();
		}

	}

	class DaemonThread implements Runnable {
		protected volatile boolean runnable = false;

		@Override
		public void run() {
			synchronized (this) {
				while (runnable) {
					if (webSource.grab()) {
						try {
							webSource.retrieve(frame);
							Highgui.imencode(".bmp", frame, mem);
							Image im = ImageIO.read(new ByteArrayInputStream(mem.toArray()));

							BufferedImage buff = (BufferedImage) im;
							Graphics g = panelDoWyswietlenia.getGraphics();

							if (g.drawImage(buff, 0, 0, panelDoWyswietlenia.getWidth(),
									panelDoWyswietlenia.getHeight() - 150, 0, 0, buff.getWidth(), buff.getHeight(),
									null))

								if (runnable == false) {
									System.out.println("Going to wait()");
									this.wait();
								}
						} catch (Exception ex) {
							System.out.println("Error");
						}
					}
				}
			}
		}

	}

}
