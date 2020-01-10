import java.awt.Color;
import java.awt.Graphics;


public class Ground {
	private int y = 0;
	private int displayY;
	
	public Ground(){
		
	}
	
	public void paint(Graphics g, int playerY){
		if(playerY +400 > 0){
			displayY = 400 + playerY;
			g.setColor(Color.black);
			g.fillRect(0, displayY, 1000, 900-displayY);
		}
	}
}
