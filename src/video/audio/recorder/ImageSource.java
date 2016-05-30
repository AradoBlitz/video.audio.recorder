package video.audio.recorder;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class ImageSource extends JPanel {

	private Image image;

	public ImageSource(){
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);		
		frame.add(this);	
		frame.setSize(640, 480);
		frame.setVisible(true);
	}
	
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
