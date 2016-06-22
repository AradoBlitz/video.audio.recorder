package video.audio.recorder.concurrent;

import java.awt.Container;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
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
	private volatile List<BufferedImage> imageBuffer = new ArrayList<>();
	private List<Integer>  imageCount = new ArrayList<>();
	
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
				if(image !=null)
				System.out.println(image.toString() + "webcamImageObtained");
				screen.setImage(image);
				imageList.add(image);
			
	}
	
	
	volatile List<BufferedImage> playList = Collections.synchronizedList( new ArrayList<BufferedImage>());
	public void play(){
		
		for(BufferedImage image : imageList){
			screen.setImage(image);
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}					
		
	}
	
	public void storeImage() {
		List<BufferedImage> hold = imageBuffer;
		imageBuffer = new ArrayList<>();
		imageList.addAll(hold);
		imageCount.add(hold.size());
		
	}

	private int leftSide = 0;
	private int index = 0;
	public boolean playNext() {
		
		leftSide+=imageCount.get(index++);
		return leftSide < imageList.size();
	}

}
