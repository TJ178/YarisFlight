
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
	private double scale = 1;
	private double rv;
	private double anchorX = 750;
	private double anchorY = 650;
	private int level = 0;
	
	private int weightU1 = 10;
	private int dragU1 = 5;
	private int liftU1 = 40;
	private int widthU1 = (int)(1287 * scale);
	private int heightU1 = (int)(494 * scale);
	private double scaleU1 = 0.2;
	private double anchorxU1 = 750;
	private double anchoryU1 = 655;

	private int weightU2 = 20;
	private int dragU2 = 2;
	private int liftU2 = 60;
	private int widthU2 = (int)(186 * scale);	//186 × 119 pixels
	private int heightU2 = (int)(119 * scale);
	private double scaleU2 = 0.75;
	
	private String imgU1 = "yarisGlider_v2.png"; //1287 × 494 pixels
	private String imgU2 = "air-canada-flight_wing.png";

	private double[] scales = {scaleU1, scaleU2};
	private double[] transX = {-170, -40};
	private double[] transY = {-140, 0};
	
	private Image img;
	
	private AffineTransform tx;

	
	public WingsUpgrade() {
		super(0, 0, 0, 0);
		tx = AffineTransform.getTranslateInstance(500, 400); //392 && 322
	}
	
	public void upgrade(int weight, int drag, int lift, String FileName, double anchorX, double anchorY) {
		setWeight(weight);
		setDrag(drag);
		setLift(lift);
		setImg(getImage(FileName));
	}
	
	public void upgrade1() {
		upgrade(weightU1, dragU1, liftU1, imgU1, anchorxU1, anchoryU1);
		
		level = 0;
		//tx = AffineTransform.getTranslateInstance(335, 270); //392 && 322
		//tx.scale(scaleU1, scaleU1);
	}
	
	public void upgrade2() {
		upgrade(weightU2, dragU2, liftU2, imgU2, anchorxU2, anchoryU2);
		
		level = 1;
		//tx = AffineTransform.getTranslateInstance(displayX-offsetx + 495, displayY-offsety + 385); //392 && 322
		//tx.scale(scaleU2, scaleU2);
	}
	
	//rotation mechanics
	public void moveTo(double angle) {
		tx.setToTranslation(displayX, displayY);
		tx.rotate(-angle);
		
		if(level > -1) {
			tx.translate( transX[getLevel()], transY[getLevel()]);
			tx.scale(scales[getLevel()], scales[getLevel()]);
		}
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
