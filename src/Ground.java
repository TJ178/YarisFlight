import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;


public class Ground {
	private int y = 0;
	private int displayY;
	
	private Rectangle bounds = new Rectangle();
	
	public Ground(){
		bounds = new Rectangle();
	}
	
	public void paint(Graphics g, int playerY){
		if(playerY +400 > 0){
			displayY = 400 + playerY;
			g.setColor(Color.black);
			g.fillRect(0, displayY, 1000, 900-displayY);
			bounds = new Rectangle(0,displayY,1000,10);
			g.setColor(Color.yellow);
			Graphics2D g2d = (Graphics2D) g;
			g2d.draw(bounds);
		}
	}
	
	public boolean collide(Shape b){
		return b.intersects(bounds);
	}
}
