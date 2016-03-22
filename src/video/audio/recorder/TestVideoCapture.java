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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamEvent;
import com.github.sarxos.webcam.WebcamListener;
import com.github.sarxos.webcam.WebcamResolution;

public class TestVideoCapture {

	ImageCollector imageCollector = new ImageCollector();
	ImageSourceWebcam imageSource = new ImageSourceWebcam(imageCollector);
	
	@Before
	public void startCamera(){
		imageSource.open();
	}
	
	@After
	public void stopCamera(){
		imageSource.close();
	}
	
	@Test
	public void videoCapture() throws Exception {

		imageSource.collectImage();
				
		assertThat(imageCollector.videoFile.exists(), Is.is(true));
		assertThat(imageCollector.videoFile.length()>1,Is.is(true));
	}
	
	@Test
	public void videoCaptureTwice() throws Exception {

			imageSource.collectImage();
			assertThat(imageCollector.videoFile.exists(), Is.is(true));
			assertThat(imageCollector.videoFile.length()>1,Is.is(true));
			
			imageSource.collectImage();	
			assertThat(imageCollector.videoFile.exists(), Is.is(true));
			assertThat(imageCollector.videoFile.length()>1,Is.is(true));
	}
	
	@Test
	public void sizeConfigurations() throws Exception {
		assertThat(WebcamResolution.VGA.getSize(),IsEqual.equalTo(new Dimension(640,480)));
		assertThat(Webcam.getDefault().getViewSize(), IsEqual.equalTo(new Dimension(640,480)));
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
