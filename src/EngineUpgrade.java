import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class EngineUpgrade extends Upgrade {
	/*private static int weight;
	private static int lift;
	private static int thrust;*/
	
	private int level = -1;
	private boolean isThrusting = false;
	private double fuelPerc =1.0;
	
	private double fuelMultiplier = 1;
	
	private int weightU1 = 5;
	private int dragU1 = 0;
	private int thrustU1 = 10;
	private double scaleU1 = 0.2;
	private int fuelU1 = 10;
	private int priceU1 = 420;

	private int weightU2 = 50;
	private int thrustU2 = 20;
	private int dragU2 = 5;
	private double scaleU2 = 0.2;
	private int fuelU2 = 20;
	private int priceU2 = 6969;
	
	private String imgU1 = "rocketU1.png"; //1287 × 494 pixels
	private String imgU1Lit = "rocketU1_lit.png";
	private String imgU2 = "thicc_rocket.png"; //384 × 799 pixels
	private String imgU2Lit = "thicc_rocket_lit.png";

	private double[] scales = {scaleU1, scaleU2};
	private double[] transX = {-30, -90};
	private double[] transY = {75, 90};
  	private String[] imgsStrings = {imgU1, imgU2};
  	private String[] imgsStringsLit = {imgU1Lit, imgU2Lit};
  	private int[] fuels = {fuelU1, fuelU2}; 
  	private double[] fuelUsage = {0.05, 0.1};
  	private int[] prices = {priceU1, priceU2};

	private Image img;
	private Image[] imgs = new Image[2];
	private Image[] imgsLit = new Image[2];
	
	private AffineTransform tx;
	
	private FuelUpgrade fuelManager;

	
	public EngineUpgrade() {
		super(0, 0, 0, 0);
		
		for(int i = 0; i < 2; i ++) {
			imgs[i] = getImage(imgsStrings[i]);
			imgsLit[i] = getImage(imgsStringsLit[i]);
		}
		
		tx = AffineTransform.getTranslateInstance(500, 400);
		fuelManager = new FuelUpgrade();
	}
	
	public void upgrade(int weight, int thrust, int drag, String FileName) {
		setWeight(weight);
		setThrust(thrust);
		setDrag(drag);
		setImg(getImage(FileName));
	}
	
	public void upgrade1() {
		upgrade(weightU1, thrustU1, dragU1, imgU1);
		
		level = 0;
		
	}
	
	public void upgrade2() {
		upgrade(weightU2, thrustU2, dragU2, imgU2);
		
		level = 1;
		
	}
	
	//rotation mechanics
	public void moveTo(double angle) {
		tx.setToTranslation(500, 400);
		tx.rotate(-angle + (Math.PI / 2));
		if(getLevel() > -1) {
			tx.translate(transX[getLevel()], transY[getLevel()]);
			tx.scale(scales[getLevel()], scales[getLevel()]);
		}
		

		if(isThrusting) {
			fuelPerc -= fuelUsage[level]*fuelManager.getMultiplier() / fuels[level];
			if(fuelPerc < 0) {
				isThrusting = false;
			}
		}
		
	}
	
	//getters and setters time
	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getDrag() {
		return drag;
	}

	public int getLift() {
		return lift;
	}

	public void setLift(int lift) {
		this.lift = lift;
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
	
	public int getThrust() {
		return thrust;
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
		isThrusting = true;
	}
	public void notLit() {
		if(getLevel() > -1) {
			img = imgs[getLevel()];
		}
		isThrusting = false;
	}
	
	public double getFuelUsage() {
		if(getLevel() > -1) {
			return fuelUsage[getLevel()];
		}
		return 0;
	}
	
	public double getFuelPerc() {
		if(level > -1) {
			return fuelPerc;
		}
		return 0;
	}
	
	public FuelUpgrade getFuelManager() {
		return fuelManager;
	}
	
	public void refuel() {
		fuelPerc = 1;
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
