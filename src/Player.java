import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

import javax.swing.ImageIcon;

public class Player {
	
	//attributes
	private int x, y; 			//position of frog
	private boolean alive;		//aliveness of our manual 2007 red yaris driving friend
	private int width, height; 	// size of yar'
	private Image img;			//image
	private int vx, vy;			//velocity 
	private double rv;	    		//rotation velocity  
	
	public Player(String fileName) {
		//width;
		//height;
		//x;
		//y;
		vx = 0;
		vy = 0;
		rv = 0;
		img = getImage(fileName);
		init(x, y);
		alive = true;
	}
	
	private AffineTransform tx = AffineTransform.getTranslateInstance(x, y);
	
	//vx setter 
	
	public void jump(int dist, int keyCode) {
		if(isAlive()) {
			switch(keyCode) {
				case 37:
					tx.setToTranslation(tx.getTranslateX() - dist, tx.getTranslateY());
					break;
				case 38:
					tx.setToTranslation(tx.getTranslateX(), tx.getTranslateY() - dist);
					break;
				case 39:
					tx.setToTranslation(tx.getTranslateX() + dist, tx.getTranslateY());			
					break;
				case 40:
					tx.setToTranslation(tx.getTranslateX(), tx.getTranslateY() + dist);			
					break;
			}
		}
	}

	//returns aliveness
	public boolean isAlive() {
		return alive;
	}
	
	//getters and setters for vx, vy, rv
	public void setVx(int newVx) {
		vy = 0;
		vx = newVx;
	}
	
	public void setVy(int newVy) {
		vx = 0;
		vy = newVy;
	}
	
	public double getRv() {
		return rv;
	}


	public void setRv(double rv) {
		this.rv = rv;
	}
	
	//getters and setters for x and y
	public int getX() {
		return (int) tx.getTranslateX();
	}
	
	public void setX(int newX) {
		tx.setToTranslation(newX, tx.getTranslateY());
	}
	
	public int getY() {
		return (int) tx.getTranslateY();
	}
	
	public void setY(int newY) {
		tx.setToTranslation(tx.getTranslateX(), newY);
	}
	
	/*
	 * colliding method, I'll leave it for reference for now
	public boolean collided(int ox, int oy, int ow, int oh) {
		Rectangle obs = new Rectangle(ox, oy, ow, oh);
		Rectangle froggy = new Rectangle((int) tx.getTranslateX(), (int) tx.getTranslateY(), width, height);
		return obs.intersects(froggy);
	}
	*/
	
	//prob wont use but lateral move method
	public void move() {
		tx.translate(vx, vy);
	}
	
	//rotate methods
	public void rotateCW() {
		tx.rotate(rv);
	}
	
	public void rotateCCW() {
		tx.rotate(-rv);
	}
	
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(img, tx, null);
	}

	private void init(double a, double b) {
		tx.setToTranslation(a, b);
		tx.scale(1, 1);
	}

	// converts image to make it drawable in paint
	private Image getImage(String path) {
		Image tempImage = null;
		try {
			//URL imageURL = Froggy.class.getResource(path);
			//tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}
	public void reset() {
		// TODO Auto-generated method stub
		tx.setToTranslation(x, y);
		vx = 0;
		vy = 0;
	}

}