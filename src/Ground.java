import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Toolkit;
import java.net.URL;


public class Ground {
	private int y = 0;
	private int displayY;
	private Image image;
	
	private Rectangle bounds = new Rectangle();
	
	public Ground(String img){
		bounds = new Rectangle();
		
		image = getImage(img);
	}
	
	public void paint(Graphics g, int playerX, int playerY){
		if(playerY +400 > 0){
			displayY = 400 + playerY;
			//g.setColor(Color.black);
			//g.fillRect(0, displayY, 1000, 900-displayY);
			bounds = new Rectangle(0,displayY,1000,10);
			int dispx1 = 1000 - (playerX % 1000);
			int dispx2 = dispx1 - 1000;
			g.drawImage(image, dispx1, displayY, null);
			g.drawImage(image, dispx2, displayY, null);
			
			//g.setColor(Color.yellow);
			//Graphics2D g2d = (Graphics2D) g;
			//g2d.draw(bounds);
		}
	}
	
	public boolean collide(Shape b){
		return b.intersects(bounds);
	}
	
	
	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = Entity.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}
}
