import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.opencv.core.Core;

public class main {

	protected void createAndShowGUI() {
		JFrame f = new JFrame("Detection Face");
		f.setSize(400, 500);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//LadowanieZdjecia wczytywanie=new LadowanieZdjecia();
		MenuGlowne menuGlowne =new MenuGlowne();
		f.setSize(500, 600);
		f.add(menuGlowne.menu);
		Toolkit zestaw=Toolkit.getDefaultToolkit();
		Dimension wymiary=zestaw.getScreenSize();
		int wysokosc=wymiary.height;
		int szerokosc=wymiary.width;
	
		f.setLocation(szerokosc/2, wysokosc/2);
		f.setResizable(false);

		f.setVisible(true);
		
	}

	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME); 
		System.out.println(Thread.currentThread().getName());
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				System.out.println(Thread.currentThread().getName());
				main app = new main();
				app.createAndShowGUI();
			}
		});
	}
}