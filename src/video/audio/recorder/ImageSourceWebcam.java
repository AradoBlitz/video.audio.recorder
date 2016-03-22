package video.audio.recorder;

import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamEvent;
import com.github.sarxos.webcam.WebcamListener;
import com.github.sarxos.webcam.WebcamResolution;

public class ImageSourceWebcam {

	private ImageCollector imageCollector;

	public ImageSourceWebcam(ImageCollector imageCollector) {
		this.imageCollector = imageCollector;
		// TODO Auto-generated constructor stub
	}

	public void collectImage() throws Exception {
		Webcam webcam = Webcam.getDefault();
		webcam.setViewSize(WebcamResolution.VGA.getSize());
		WebcamListener camListener = new WebcamListener() {
			
			@Override
			public void webcamOpen(WebcamEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println(arg0.toString() + "webcamOpen");
			}
			
			@Override
			public void webcamImageObtained(WebcamEvent arg0) {
				
				
			}
			
			@Override
			public void webcamDisposed(WebcamEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println(arg0.toString() + "webcamDisposed");
			}
			
			@Override
			public void webcamClosed(WebcamEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println(arg0.toString()  + "wbcamClosed");
			}
		};
		webcam.addWebcamListener(camListener );
		webcam.open();
		TimeUnit.SECONDS.sleep(1);
		BufferedImage image = webcam.getImage();
		System.out.println(image.toString() + "webcamImageObtained");
		imageCollector.setImage(image);
		
		
		webcam.close();
		
	}

}
