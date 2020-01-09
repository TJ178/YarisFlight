import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

public class WingsUpgrade extends Upgrade {
	private int weightWings;
	private int dragWings;
	private int liftWings;
	
	private int weightU1;
	private int dragU1;
	private int liftU1;
	
	private Image img;
	
	public WingsUpgrade() {
		weightWings = 0;
		dragWings = 0;
		liftWings = 0;
		
	}
	
	public void uprade1(String FileName) {
		weightWings = weightU1;
		dragWings = dragU1;
		liftWings = liftU1;
		img = getImage(FileName);
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
