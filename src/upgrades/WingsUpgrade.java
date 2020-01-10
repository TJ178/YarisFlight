package upgrades;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class WingsUpgrade extends Upgrade {
	private static int weight;
	private static int drag;
	private static int lift;
	
	private int weightU1;
	private int dragU1;
	private int liftU1;
	
	private Image img;
	
	private AffineTransform tx;// = AffineTransform.getTranslateInstance(x, y);

	
	public WingsUpgrade() {
		super(weight, drag, lift);
		
		
	}
	
	public void uprade(int weight, int drag, int lift, String FileName) {
		this.weight = weight;
		this.drag = drag;
		this.lift = lift;
		this.img = getImage(FileName);
	}
	
	public void upgrade1() {
		uprade(weightU1, dragU1, liftU1, "yarisGlider.png");
	}
	
	//getters and setters time
	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		WingsUpgrade.weight = weight;
	}

	public int getDrag() {
		return drag;
	}

	public void setDrag(int drag) {
		WingsUpgrade.drag = drag;
	}

	public int getLift() {
		return lift;
	}

	public void setLift(int lift) {
		WingsUpgrade.lift = lift;
	}
	
	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}
	
	//paint stuff
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(img, tx, null);
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
