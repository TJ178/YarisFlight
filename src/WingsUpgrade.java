
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
	/*
	private static int weight;
	private static int drag;
	private static int lift;
	
	private int x = 0, y = 200;
	private int displayX = 500, displayY = 400;
	private double scale = 1;
	private int offsetx = (int)(540*scale);
	private int offsety = (int)(390*scale);
	private double rv;
	private double anchorX;
	private double anchorY;
	
	private int weightU1 = 10;
	private int dragU1 = 10;
	private int liftU1 = 10;
	private int widthU1 = (int)(1287 * scale);
	private int heightU1 = (int)(494 * scale);
	private double anchorXU1 = 750;
	private double anchorYU1 = 650;
	private double displayXU1 = displayX-offsetx - 50;
	private double displayYU1 = displayY-offsety - 50;
	private double scaleU1 = 0.2;
	
	private int weightU2 = 20;
	private int dragU2 = 5;
	private int liftU2 = 20;
	private int widthU2 = (int)(186 * scale);	//186 × 119 pixels
	private int heightU2 = (int)(119 * scale);
	private double anchorXU2 = 750;
	private double anchorYU2 = 650;
	private double displayXU2 = displayX-offsetx - 50;
	private double displayYU2 = displayY-offsety - 50;

	private String imgU1 = "yarisGlider_v2.png"; //1287 × 494 pixels

	
	private Image img;
	
	private AffineTransform tx;

	/*
	public WingsUpgrade() {
		super(weight, drag, lift);
		
		//System.out.println((displayX-offsetx) + " " +  (displayY-offsety));
		//AffineTransform trans = (AffineTransform) tx.clone();
		//trans.setToScale(1, 1);
	}
	
	public void uprade(int weight, int drag, int lift, String FileName, double x, double y, double anchorx, double anchory, double scale) {
		setWeight(weight);
		setDrag(drag);
		setLift(lift);
		setImg(getImage(FileName));
		//tx = AffineTransform.getTranslateInstance(this.x, this.y); //392 && 322
		//tx.scale(scale, scale);
		anchorX = anchorx;
		anchorY = anchory;
		this.scale = scale;
	}
	
	public void upgrade1() {
		uprade(weightU1, dragU1, liftU1, imgU1, displayXU1, displayYU1, anchorXU1, anchorYU1, scaleU1);
		
		//tx = AffineTransform.getTranslateInstance(displayXU1, displayYU1); //392 && 322
		//tx.scale(scale, scale);
		//anchorX = anchorXU1;
		//anchorY = anchorYU1;
		tx = AffineTransform.getTranslateInstance(displayXU1, displayYU1); //392 && 322
		tx.scale(scaleU1, scaleU1);
		
	}
	
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
		tx.scale(scaleU1, scaleU1);
	}
	*/
	
	private static int weight;
	private static int drag;
	private static int lift;
	
	private int x = 0, y = 200;
	private int displayX = 500, displayY = 400;
	private double scale = 1;
	private int offsetx = (int)(540*scale);
	private int offsety = (int)(390*scale);
	private double rv;
	private double anchorX = 750;
	private double anchorY = 650;
	private int level = 0;
	
	private int weightU1 = 10;
	private int dragU1 = 10;
	private int liftU1 = 10;
	private int widthU1 = (int)(1287 * scale);
	private int heightU1 = (int)(494 * scale);
	private double scaleU1 = 0.2;
	private double anchorxU1 = 750;
	private double anchoryU1 = 655;

	private int weightU2 = 20;
	private int dragU2 = 5;
	private int liftU2 = 20;
	private int widthU2 = (int)(186 * scale);	//186 × 119 pixels
	private int heightU2 = (int)(119 * scale);
	private double scaleU2 = 0.75;
	private double anchorxU2 = 50;
	private double anchoryU2 = 13;
	
	private String imgU1 = "yarisGlider_v2.png"; //1287 × 494 pixels
	private String imgU2 = "air-canada-flight_wing.png";

	
	private Image img;
	
	private AffineTransform tx;

	
	public WingsUpgrade() {
		super(weight, drag, lift);
		
		//System.out.println((displayX-offsetx) + " " +  (displayY-offsety));
		//AffineTransform trans = (AffineTransform) tx.clone();
		//trans.setToScale(1, 1);
	}
	
	public void uprade(int weight, int drag, int lift, String FileName, double anchorX, double anchorY) {
		setWeight(weight);
		setDrag(drag);
		setLift(lift);
		setImg(getImage(FileName));
		this.anchorX = anchorX;
		this.anchorY = anchorY;
	}
	
	public void upgrade1() {
		uprade(weightU1, dragU1, liftU1, imgU1, anchorxU1, anchoryU1);
		
		level = 1;
		tx = AffineTransform.getTranslateInstance(335, 270); //392 && 322
		tx.scale(scaleU1, scaleU1);
	}
	
	public void upgrade2() {
		uprade(weightU2, dragU2, liftU2, imgU2, anchorxU2, anchoryU2);
		
		level = 2;
		tx = AffineTransform.getTranslateInstance(displayX-offsetx + 495, displayY-offsety + 385); //392 && 322
		tx.scale(scaleU2, scaleU2);
	}
	
	//rotation mechanics
	public void move(double rv) {
		tx.rotate(-rv, anchorX, anchorY);
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
	
	public int getLevel() {
		return level;
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
