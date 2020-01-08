import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;


public abstract class Entity {
	private int x,y;
	private double angle, scale;
	private boolean gravity;
	private boolean collision;
	private Image img;
	
	private AffineTransform tx = AffineTransform.getTranslateInstance(x, y);
	
	public Entity(){
		
	}
	
	public Entity(int x, int y, double angle, boolean gravity, boolean collision, String filePath){
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.gravity = gravity;
		this.collision = collision;
		scale = 1;
		img = getImage(filePath);
	}
	
	public abstract void paint(Graphics g);
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public double getAngle(){
		return angle;
	}
	
	public boolean hasGravity(){
		return gravity;
	}
	
	public boolean hasCollision(){
		return collision;
	}
	
	public void setX(int i){
		x = i;
	}
	
	public void setY(int i){
		y = i;
	}
	
	public void setAngle(double i){
		angle = i;
	}
	
	public void setScale(double i){
		scale = i;
	}
	


	private void init(double a, double b) {
		tx.setToTranslation(a, b);
		tx.scale(1, 1);
	}

	// converts image to make it drawable in paint
	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = Entity.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}
	
}
