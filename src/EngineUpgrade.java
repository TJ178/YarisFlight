import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class EngineUpgrade extends Upgrade {
	private static int weight;
	private static int drag;
	private static int lift;
	
	private double scale = 1;
	private double anchorX = 750;
	private double anchorY = 650;
	private int level = -1;
	private boolean isThrusting = false;
	
	private int weightU1 = 10;
	private int dragU1 = 10;
	private int liftU1 = 10;
	private double scaleU1 = 0.2;
	private double anchorxU1 = 893;;
	private double anchoryU1 = 155;

	private int weightU2 = 20;
	private int dragU2 = 5;
	private int liftU2 = 20;
	private double scaleU2 = 0.2;
	private double anchorxU2 = 172;
	private double anchoryU2 = -343;
	
	private String imgU1 = "rocketU1.png"; //1287 × 494 pixels
	private String imgU1Lit = "rocketU1_lit.png";
	private String imgU2 = "thicc_rocket.png"; //384 × 799 pixels
	private String imgU2Lit = "thicc_rocket_lit.png";

	private double[] scales = {scaleU1, scaleU2};
	private double[] transX = {-30, -90};
	private double[] transY = {75, 90};
  	private String[] imgsStrings = {imgU1, imgU2};
  	private String[] imgsStringsLit = {imgU1Lit, imgU2Lit};
  	//private 

	private Image img;
	private Image[] imgs = new Image[2];
	private Image[] imgsLit = new Image[2];
	
	private AffineTransform tx;

	
	public EngineUpgrade() {
		super(weight, drag, lift);
		
		for(int i = 0; i < 2; i ++) {
			imgs[i] = getImage(imgsStrings[i]);
			imgsLit[i] = getImage(imgsStringsLit[i]);
		}
		
		tx = AffineTransform.getTranslateInstance(500, 400);
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
		
	}
	
	public void upgrade2() {
		upgrade(weightU2, dragU2, liftU2, imgU2, anchorxU2, anchoryU2);
		
		level = 1;
		
	}
	
	//rotation mechanics
	public void moveTo(double angle) {
		tx.setToTranslation(500, 400);
		tx.rotate(-angle + (Math.PI / 2));

		tx.translate(transX[getLevel()], transY[getLevel()]);
		tx.scale(scales[getLevel()], scales[getLevel()]);
	}
	
	//getters and setters time
	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		EngineUpgrade.weight = weight;
	}

	public int getDrag() {
		return drag;
	}

	public void setDrag(int drag) {
		EngineUpgrade.drag = drag;
	}

	public int getLift() {
		return lift;
	}

	public void setLift(int lift) {
		EngineUpgrade.lift = lift;
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
	
	public void setIsThrusting(boolean val) {
		isThrusting = true;
	}
	public boolean getIsThrusting() {
		return isThrusting;
	}
	public void getLit() {
		if(getLevel() > -1) {
			img = imgsLit[getLevel()];
		}
	}
	public void notLit() {
		if(getLevel() > -1) {
			img = imgs[getLevel()];
		}
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
