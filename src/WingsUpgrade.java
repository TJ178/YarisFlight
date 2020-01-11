
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class WingsUpgrade extends Upgrade {
	private static int weight;
	private static int drag;
	private static int lift;
	
	private int x = 0, y = 200;
	private int displayX = 500, displayY = 400;
	private double scale = 0.2;
	private int offsetx = (int)(540*scale);
	private int offsety = (int)(390*scale);
	private double rv;
	
	private int weightU1 = 10;
	private int dragU1 = 10;
	private int liftU1 = 10;
	private int widthU1 = (int)(1287 * scale);
	private int heightU1 = (int)(494 * scale);
	private double anchorX = 750;
	private double anchorY = 650;

	private String imgU1 = "yarisGlider_v2.png"; //1287 × 494 pixels

	
	private Image img;
	
	private AffineTransform tx;

	
	public WingsUpgrade() {
		super(weight, drag, lift);
		
		//System.out.println((displayX-offsetx) + " " +  (displayY-offsety));
		//AffineTransform trans = (AffineTransform) tx.clone();
		//trans.setToScale(1, 1);
	}
	
	public void uprade(int weight, int drag, int lift, String FileName) {
		setWeight(weight);
		setDrag(drag);
		setLift(lift);
		setImg(getImage(FileName));
	}
	
	public void upgrade1() {
		uprade(weightU1, dragU1, liftU1, imgU1);
		
		tx = AffineTransform.getTranslateInstance(displayX-offsetx - 50, displayY-offsety - 50); //392 && 322
		tx.scale(scale, scale);
	}
	
	//rotation mechanics
	public void move(double rv) {
		tx.rotate(rv, anchorX, anchorY);
		this.rv = rv;
	}
	
	//rotate methods
	public void rotateCW() {
		tx.rotate(rv);
	}
	
	public void rotateCCW() {
		tx.rotate(-rv);
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
	
	public double getAnchorX() {
		return anchorX;
	}

	public void setAnchorX(double anchorX) {
		this.anchorX = anchorX;
	}

	public double getAnchorY() {
		return anchorY;
	}

	public void setAnchorY(double anchorY) {
		this.anchorY = anchorY;
	}
	
	//paint stuff
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(img, tx, null);
	}
	
	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = WingsUpgrade.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}
}
