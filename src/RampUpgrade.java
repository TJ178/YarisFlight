import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;


public class RampUpgrade extends Upgrade {
	private int extraLength;
	
	private Image top;
	private Image middle;
	private Image bottom;
	private Image filler;
	
	public RampUpgrade(String rampTop, String rampMiddle, String rampBottom, String rampFiller) {
		extraLength = 1;
		
		top = getImage(rampTop);
		middle = getImage(rampMiddle);
		bottom = getImage(rampBottom);
		filler = getImage(rampFiller);
	}
	
	public void upgrade(){
		extraLength++;
	}
	
	//cx-playerx, -cy+playery
	public void paint(Graphics g, int playerx, int playery){
		if(Math.sqrt(Math.pow(playerx, 2)+Math.pow(playery, 2)) < 1600){
			
			g.drawImage(top, 0-playerx, playery-750, null);
			
			g.drawImage(filler, 0-playerx, playery - 500, null);
			g.drawImage(filler, 250-playerx, playery - 500, null);
			g.drawImage(middle, 500-playerx, playery - 500, null);
			
			
			//TODO: logic to make ramp taller, probably not going to happen
			/*for(int i = 1; i < extraLength; i++){
				for(int j = i; j > 0; j++){
					g.drawImage()
				}
			}*/
			
			g.drawImage(filler, 0-playerx, playery, null);
			g.drawImage(filler, 250-playerx, playery, null);
			g.drawImage(filler, 500-playerx, playery, null);
			g.drawImage(bottom, 750-playerx, playery, null);
		}
		
	}
	
	public int getRampTopY(){
		return 750;
	}
	public int getRampMidY(){
		return 500;
	}
	public int getRampBotY(){
		return 0;
	}
	
	
	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = Player.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}
}
