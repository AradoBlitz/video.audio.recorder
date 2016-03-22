package video.audio.recorder;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageCollector {

	private int fCount = 1;
	File videoFile = new File("./image_" + fCount++ + ".png");
	public File imageFile = videoFile;

	public void setImage(BufferedImage image) {
		try {
			ImageIO.write(image, "PNG", imageFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
