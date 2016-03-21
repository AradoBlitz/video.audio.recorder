package video.audio.recorder;

import static org.junit.Assert.*;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.hamcrest.core.Is;
import org.hamcrest.core.IsEqual;
import org.junit.Test;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamEvent;
import com.github.sarxos.webcam.WebcamListener;
import com.github.sarxos.webcam.WebcamResolution;

public class TestVideoCapture {

	private int fCount = 1;
	
	@Test
	public void videoCapture() throws Exception {

		File videoFile = new File("./image_" + fCount++ + ".png");
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
		try {
			ImageIO.write(image, "PNG", videoFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		webcam.close();
		assertThat(videoFile.exists(), Is.is(true));
		assertThat(videoFile.length()>1,Is.is(true));
	}
	
	@Test
	public void sizeConfigurations() throws Exception {
		assertThat(WebcamResolution.VGA.getSize(),IsEqual.equalTo(new Dimension(640,480)));
		assertThat(Webcam.getDefault().getViewSize(), IsEqual.equalTo(new Dimension(176,144)));
	}
	
	@Test
	public void drawToPanel() throws Exception {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		

		Image image = ImageIO.read(new File("/home/dmitriy/Projects/Java/video.audio.capture/video.audio.recorder/image_0.png"));
		ImageSource imageSource = new ImageSource();
		
		frame.add(imageSource);
		frame.setSize(image.getWidth(null), image.getHeight(null));
		frame.setVisible(true);
		
		imageSource.setImage(image);
		Thread.currentThread().sleep(1000);
		imageSource.setImage(ImageIO.read(new File("/home/dmitriy/Projects/Java/video.audio.capture/video.audio.recorder/image_1.png")));
		
	}
	
	public static class Run{
		
		public static void main(String[] args) {
			try {
				new TestVideoCapture().drawToPanel();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
