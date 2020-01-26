
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
	
	private int displayX = 500, displayY = 400;
	private int level = -1;
	
	private int weightU1 = 10;
	private int dragU1 = 5;
	private int liftU1 = 20;
	private double scaleU1 = 0.2;
	private int priceU1 = 500;

	private int weightU2 = 20;
	private int dragU2 = 10;
	private int liftU2 = 40;
	private double scaleU2 = 0.75;
	private int priceU2 = 2000;
	
	private String imgU1 = "yarisGlider_v2.png"; //1287 × 494 pixels
	private String imgU2 = "air-canada-flight_wing.png";
	private String[] wingsImgs = {"yarisGlider_v2.png", "air-canada-flight_wing.png"};

	private double[] scales = {scaleU1, scaleU2};
	private double[] transX = {-170, -40};
	private double[] transY = {-140, 0};
	private int[] prices = {priceU1, priceU2};
	
	private Image img;
	
	private AffineTransform tx;

	
	public WingsUpgrade() {
		super(0, 0, 0, 0);
		tx = AffineTransform.getTranslateInstance(500, 400); //392 && 322
	}
	
	public void upgrade(int weight, int drag, int lift, String FileName) {
		setWeight(weight);
		setDrag(drag);
		setLift(lift);
		setImg(getImage(FileName));
	}
	
	public void upgrade1() {
		upgrade(weightU1, dragU1, liftU1, imgU1);
		
		level = 0;
		//tx = AffineTransform.getTranslateInstance(335, 270); //392 && 322
		//tx.scale(scaleU1, scaleU1);
	}
	
	public void upgrade2() {
		upgrade(weightU2, dragU2, liftU2, imgU2);
		
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
	
	public String getWingsUpgrade(int level){
		return wingsImgs[level];
	}		

	public void setWingsImgs(String[] wingsImgs) {
		//this.imgsStrings = imgsStrings;
		for(int i = 0; i < wingsImgs.length; i ++) {
			this.wingsImgs[i] = wingsImgs[i]; 
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
	
	public int getUpgradePrice() {
		return prices[level+1];
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
