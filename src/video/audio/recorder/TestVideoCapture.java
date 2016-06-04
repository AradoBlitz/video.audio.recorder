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

	public static final List<BufferedImage> IMAGE_LIST = new ArrayList<>();
	ImageCollector imageCollector = new ImageCollector();
	ImageSource screen = new ImageSource();
	ImageSourceWebcam imageSource = new ImageSourceWebcam(imageCollector,screen);
	AudioRecorder audioRecorder = new AudioRecorder();
	
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
	public void audio() throws Exception {		
		
		boolean nextBytes = true;
		while (nextBytes) {
			nextBytes = audioRecorder.capture();			
			
		}
		
	}
	
	@Test
	public void audioVideo() throws Exception {
		
		
		
		boolean nextBytes = true;
		int index = 0;
		while (index < 10 && nextBytes) {
			imageSource.collectImage();
			for(int i = 0;i<20;i++)
				nextBytes = audioRecorder.capture();			
			index++;
		}
		
		while(imageSource.play() && audioRecorder.play()){
			TimeUnit.SECONDS.sleep(1);
		}
	}
	
	@Test
	public void videoCaptureTwice() throws Exception {

		TimeUnit.SECONDS.sleep(1);
		for(int x=0;x<50;x++)	
		imageSource.collectImage();
			//audioRecorder.capture();
			assertThat(imageCollector.videoFile.exists(), Is.is(true));
			assertThat(imageCollector.videoFile.length()>1,Is.is(true));
			
			//imageSource.collectImage();
		//	audioRecorder.capture();
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
		
		
		Image image = ImageIO.read(new File("/home/dmitriy/Projects/Java/video.audio.capture/video.audio.recorder/image_0.png"));
		System.out.println(image.getWidth(null) + " " + image.getHeight(null));
		screen.setImage(image);
		Thread.currentThread().sleep(1000);
		screen.setImage(ImageIO.read(new File("/home/dmitriy/Projects/Java/video.audio.capture/video.audio.recorder/image_1.png")));
		
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
