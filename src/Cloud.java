import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

import javax.swing.ImageIcon;

public class Cloud {
	
	//attributes
	private int x =1000, y;
	private int displayX = 1000, displayY = 200;
	private double scale = .25;
	private int offsetx = (int)(540*scale);
	private int offsety = (int)(390*scale);
	//private boolean alive;		//aliveness of our manual 2007 red yaris driving friend
	//private int width = (int)(1024*scale); //768x 652
//	private int height= (int)(768*scale); 	// size of yar'
	private Image img;			//image
	
	/*private double vx, vy;			//velocity 
	private double ax, ay;			//acceleration
	private int appliedForceX = 5, appliedForceY;
	private int netArea = 10; ////TODO: make netArea a function of the angle
	private int mass = 100;
	private boolean onGround = false;
	
	private double rv;	    	//rotation velocity  
	private double pi = Math.PI;
	
	private int airDrag = 0;
	private int gravity = -3;
	private Shape bounds;
	*/
	public Cloud(String fileName) {
		//width;
		//height;
		x = 8000;
		y = 100;
	/*	vx = 0;
		vy = 0;
		rv = 0;*/
		img = getImage(fileName);
		//alive = true;
		tx = AffineTransform.getTranslateInstance(displayX-offsetx, displayY-offsety);
		tx.scale(scale, scale);
	}
	
	private AffineTransform tx;// = AffineTransform.getTranslateInstance(x, y);
	
	
	/*
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
	*/
	
	//returns aliveness
/*	public boolean isAlive() {
		return alive;
	}
	
	public Shape getBounds(){
		return bounds;
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
	
	public void setAx(int newAx){
		ax = newAx;
	}
	
	public void setAy(int newAy){
		appliedForceY = newAy;
		//appliedForceY += gravity;
	}
	
	public void setGround(boolean g) {
		onGround = g;
	}
	
	public boolean onGround() {
		return onGround;
	}
	
	public double getRv() {
		return rv;
	}


	public void setRv(double rv) {
		this.rv = rv;
	}
	*/
	//getters and setters for x and y
	public int getX() {
		return x;
	}
	
	public void setX(int newX) {
		x = newX;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int newY) {
		y = newY;
	}
	
	/*public int getAppliedForceY() {
		return appliedForceY;
	}
	*/
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
		//tx.translate(vx, vy);
	/*	updateAccelerations();
		vx += ax;
		vy += ay;
		
		
		x += vx;
		y += vy;
		
		if(onGround && ay <= 0) {
			y = (int) (40);
		}
	*/	
		//System.out.println("Accel x: "+ax+" y: "+ay);
	//	tx.rotate(rv, displayX/*-offsetx*scale*/, displayY/*-offsety*scale*/);
	/*
		bounds = new Rectangle(92,206,900,359);
		//AffineTransform trans = (AffineTransform) tx.clone();
		//trans.setToScale(1, 1);
		bounds = tx.createTransformedShape(bounds);
	*/
	}
	
	/*public void updateAccelerations(){
		double fx = appliedForceX;
		double fy = appliedForceY;
		if(!onGround) {
			fy += gravity;
		}
		double netForceAngle = Math.atan(fy/fx);
		
		
		double netforce = Math.sqrt(Math.pow(fx, 2)+Math.pow(fy,2));
		double netvelocity = Math.sqrt(Math.pow(vx, 2)+Math.pow(vy, 2));
		netforce -= .5 * Math.pow(netvelocity, 2) * airDrag * netArea;
		System.out.println(netforce);
		
		fx = Math.cos(netForceAngle)*netforce;
		fy = Math.sin(netForceAngle)*netforce;
		ax = (int) fx;
		ay = (int) fy;
	}
	*/
	
	//rotate methods
/*	public void rotateCW() {
		tx.rotate(rv);
	}
	
	public void rotateCCW() {
		tx.rotate(-rv);
	}
*/	
	//paint and transform mumbo jumbo 
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(img, tx, null);
		g2.setColor(Color.BLACK);
	//	g2.draw(bounds);
	}

	private void init(double a, double b) {
		tx.setToTranslation(a, b);
		tx.scale(scale, scale);
	}

	// converts image to make it drawable in paint
	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = Cloud.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}
	
	
	public void reset() {
		// TODO Auto-generated method stub
		tx.setToTranslation(x, y);
	//	vx = 0;
	//	vy = 0;
	}

}