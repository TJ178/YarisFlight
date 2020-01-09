import java.awt.Image;


public class Upgrade {
	private String imgPath;
	private Image img;
	protected int weight;
	protected int drag;
	protected int lift;
	protected int speed;
	
	public Upgrade() {
		
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
}
