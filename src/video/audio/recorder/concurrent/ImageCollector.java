package video.audio.recorder.concurrent;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageCollector {

	private int fCount = 0;
	File videoFile;
	

	public void setImage(BufferedImage image) {
		try {
			videoFile = new File("./image_" + fCount++ + ".png");
			ImageIO.write(image, "PNG", videoFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
