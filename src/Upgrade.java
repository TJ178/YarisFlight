
import java.awt.Image;
import java.awt.geom.AffineTransform;


public class Upgrade {
	private String imgPath;
	private Image img;
	protected int weight;
	protected int drag;
	protected int lift;
	protected int thrust;
	
	public Upgrade(int weight, int drag, int lift, int thrust) {
		this.weight = weight;
		//this.speed = speed;
		this.lift = lift;
		this.drag = drag;
		this.thrust = thrust;
	}
	
	public Upgrade(){
		
	}
	
	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getDrag() {
		return drag;
	}

	public void setDrag(int drag) {
		this.drag = drag;
	}
	public int getLift() {
		return lift;
	}

	public void setLift(int lift) {
		this.lift = lift;
	}

	public int getThrust() {
		return thrust;
	}

	public void setThrust(int thrust) {
		this.thrust = thrust;
	}
}
