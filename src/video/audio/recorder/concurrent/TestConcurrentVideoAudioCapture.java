package video.audio.recorder.concurrent;

import static org.junit.Assert.assertThat;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.hamcrest.core.Is;
import org.hamcrest.core.IsEqual;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;

import video.audio.recorder.concurrent.AudioRecorder;
import video.audio.recorder.concurrent.ImageCollector;
import video.audio.recorder.concurrent.ImageSource;
import video.audio.recorder.concurrent.ImageSourceWebcam;


public class TestConcurrentVideoAudioCapture {
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
		int index = 0;
		while (index < 50 && nextBytes) {
			nextBytes = audioRecorder.capture();			
			index++;
		}
		System.out.println("Playing...");
		TimeUnit.SECONDS.sleep(3);		
		while(audioRecorder.play()){
			TimeUnit.MICROSECONDS.sleep(1);
		}
	}
	
	
	@Test
	public void audioVideoInParallel() throws Exception {
		boolean nextBytes = true;
		int index = 0;
		final Thread vc = new Thread(){

				@Override
				public void run() {
					try {
						for(int i = 0; i<100;i++)
							imageSource.collectImage();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
			vc.start();
			vc.join();
			
		/*new Thread(){

			@Override
			public void run() {
				boolean nextBytes = true;
				int index = 0;
				while (index < 10 && nextBytes) {
					
					imageSource.storeImage();
					
					for(int i = 0;i<20;i++)
						nextBytes = audioRecorder.capture();			
					index++;
				}
				vc.interrupt();
			}
			
			
		}.start();*/
		
		System.out.println("Playing...");
		TimeUnit.SECONDS.sleep(3);
		long start = System.currentTimeMillis();
		final Thread vp = new Thread(){

			@Override
			public void run() {			
					
						imageSource.play();
					
				}
			};
			vp.start();
			vp.join();
		/*new Thread(){

			@Override
			public void run() {
				
				while(imageSource.playNext()){
					for(int i = 0;i<20;i++)
						audioRecorder.play();
				}
				
			}
			
			
		}.start();*/
		
		Date date = new Date(System.currentTimeMillis() - start);
		DateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSS");
		String dateFormatted = formatter.format(date);
		System.out.println("Movie long: " + dateFormatted);
		TimeUnit.MINUTES.sleep(1);
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
}
