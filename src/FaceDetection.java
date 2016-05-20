import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteOrder;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.objdetect.CascadeClassifier;

public class FaceDetection extends JFrame implements ActionListener {
	private DaemonThread myThread = null;
	int count = 0;
	VideoCapture webSource = null;
	Mat frame = new Mat();
	CascadeClassifier faceDetector = new CascadeClassifier(
			"D:/opencv/sources/data/haarcascades/haarcascade_frontalface_alt.xml");
	MatOfRect faceDetections = new MatOfRect();
	MatOfByte mem = new MatOfByte();
	public JPanel wys;
	private JButton start;
	private JButton pause;

	public FaceDetection() {
		wys = new JPanel();
		JFrame ramka = new JFrame("Online Detection");
		start = new JButton("Start");
		pause = new JButton("Pause");
		wys.setSize(800, 600);

		wys.setLayout(null);
		ramka.setSize(800, 600);
		start.setBounds(350, 500, 70, 50);
		pause.setBounds(450, 500, 70, 50);
		start.addActionListener(this);
		pause.addActionListener(this);
		wys.add(start);
		wys.add(pause);
		ramka.getContentPane().add(wys);
		ramka.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == start) {
			webSource = new VideoCapture(0); // video capture from default cam
			myThread = new DaemonThread(); // create object of threat class
			Thread t = new Thread(myThread);
			t.setDaemon(true);
			myThread.runnable = true;
			t.start(); // start thrad
			start.setEnabled(false); // deactivate start button
			pause.setEnabled(true); // activate stop button

		}
		if (e.getSource() == pause) {
			myThread.runnable = false; // stop thread
			start.setEnabled(true); // activate start button
			pause.setEnabled(false); // deactivate stop button

			webSource.release(); // stop caturing fron cam
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
							Graphics g = wys.getGraphics();
							faceDetector.detectMultiScale(frame, faceDetections);
							for (Rect rect : faceDetections.toArray()) {
								System.out.println("wykryto ryj");
								Core.rectangle(frame, new Point(rect.x, rect.y),
										new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
							}
							Highgui.imencode(".bmp", frame, mem);
							Image im = ImageIO.read(new ByteArrayInputStream(mem.toArray()));
							BufferedImage buff = (BufferedImage) im;
							if (g.drawImage(buff, 0, 0, wys.getWidth(), wys.getHeight() - 150, 0, 0, buff.getWidth(),
									buff.getHeight(), null)) {
								if (runnable == false) {
									System.out.println("Paused ..... ");
									this.wait();
								}
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