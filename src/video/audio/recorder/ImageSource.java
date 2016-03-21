package video.audio.recorder;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class ImageSource extends JPanel {

	private Image image;

	public Image getImage() {
		// TODO Auto-generated method stub
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
		repaint();
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		g.drawImage(getImage(), 0, 0, null);
	}

}
