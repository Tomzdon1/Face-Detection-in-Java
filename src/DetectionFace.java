import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.objdetect.CascadeClassifier;

public class DetectionFace extends LadowanieZdjecia {

	private CascadeClassifier Detekcja;
	public String filename;

	public DetectionFace() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		System.out.println("\nRunning FaceDetector");

		Detekcja = new CascadeClassifier("D:/opencv/sources/data/lbpcascades/lbpcascade_frontalface.xml");
		Mat image = Highgui.imread("D:/Workspace_java/Obrazy/zapisany.jpg");

		MatOfRect DetekcjaTwarzy = new MatOfRect();
		Detekcja.detectMultiScale(image, DetekcjaTwarzy);

		for (Rect rect : DetekcjaTwarzy.toArray()) {
			Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
					new Scalar(0, 255, 0));

		}
		filename = "CZy.png";
		Highgui.imwrite(filename, image);
	}

}
