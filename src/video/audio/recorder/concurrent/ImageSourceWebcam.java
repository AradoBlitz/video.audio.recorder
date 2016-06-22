package video.audio.recorder.concurrent;

import java.awt.Container;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamEvent;
import com.github.sarxos.webcam.WebcamListener;
import com.github.sarxos.webcam.WebcamResolution;



public class ImageSourceWebcam {

	private ImageCollector imageCollector;
	private ImageSource screen;
	
	Webcam webcam = Webcam.getDefault();
	private List<BufferedImage> imageList = new ArrayList<>();
	
	public ImageSourceWebcam(ImageCollector imageCollector,ImageSource screen) {
		this.imageCollector = imageCollector;
		this.screen = screen;
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
	}

	public void open(){
		webcam.open();
	}
	
	public void close(){
		webcam.close();
	}
	
	public void collectImage() throws Exception {		
		
		BufferedImage image = webcam.getImage();
		System.out.println(image.toString() + "webcamImageObtained");
		imageCollector.setImage(image);
		screen.setImage(image);
		imageList .add(image);
	}
	
	int index=0;
	
	public boolean play(){
		screen.setImage(imageList.get(index));
		index++;
		return index<imageList.size();
	}

}
