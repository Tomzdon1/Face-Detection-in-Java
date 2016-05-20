import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public class paint extends JComponent {

	public BufferedImage picture;
	public BufferedImage picture2;

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(picture, 0, 0, 400, 500, null);

		repaint();
	}
}
