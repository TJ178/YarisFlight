import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.net.URL;

import javax.swing.ImageIcon;

public class Player {
	
	//instance variables
	private int x, y;				//position in map
	private double vx, vy;			//velocity 
	private double ax, ay;			//acceleration
	private double angle;			//current flight angle
	private double rv;	    		//rotation velocity
	private double appliedThrust;	//forward thrust from yaris
	
	//display variables
	private double scale = .25;					//size of yaris on screen
	private int displayX = 500, displayY = 400;	//display location for yaris (the center)
	private int offsetx = (int)(540*scale);		//offset of yaris image (to center yaris)
	private int offsety = (int)(390*scale);
	private int width = (int)(1024*scale);		//size of yaris image
	private int height= (int)(768*scale); 		
	private Image img;							//image
	
	//physics variables
	private int mass = 100;						//thiccness of yaris,, actually controls the strength of the physics
	private double liftdragratio = 4;			//amount of drag compared to lift
	private int gravity = -15;					//make her fall
	
	//gameplay variables
	private boolean onGround = false;			//keep track if yaris dies
	private boolean alive;						//aliveness of our manual 2007 red yaris driving friend
	private Shape bounds;						//collision boundaries for yaris (for obstacles and ground)
	public boolean onRampTop;
	public boolean onRampMid;
	public boolean onRampBot;
	private RampUpgrade ramp;
	
	public Player(String fileName, RampUpgrade ramp) {
		//width;
		//height;
		x = 0;
		y = 500;
		angle = 0;
		vx = 5;
		vy = 5;
		rv = 0;
		img = getImage(fileName);
		alive = true;
		tx = AffineTransform.getTranslateInstance(displayX-offsetx, displayY-offsety);
		tx.scale(scale, scale);
		bounds = new Rectangle();
		this.ramp = ramp;
	}
	
	private AffineTransform tx;
	
	//returns aliveness
	public boolean isAlive() {
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
	
	public double getVy(){
		return vy;
	}
	
	public double getVx(){
		return vx;
	}
	
	public void setAx(int newAx){
		ax = newAx;
	}
	public double getAy(){
		return ay;
	}
	public double getAx(){
		return ax;
	}
	
	public void setThrust(int newThrust){
		appliedThrust = newThrust;
	}
	public double getThrust(){
		return appliedThrust;
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
	
	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	/*
	 * colliding method, I'll leave it for reference for now
	public boolean collided(int ox, int oy, int ow, int oh) {
		Rectangle obs = new Rectangle(ox, oy, ow, oh);
		Rectangle froggy = new Rectangle((int) tx.getTranslateX(), (int) tx.getTranslateY(), width, height);
		return obs.intersects(froggy);
	}
	*/
	
	
	public void move() {
		//update physics and apply new velocity
		if(!(onRampTop || onRampMid || onRampBot)){
			updateAccelerations();
			
			vx += ax;
			vy += ay;
			
			/// maximum velocity - hopefully won't reach this
			if(vx > 100){
				vx = 100;
			}else if(vx < -100){
				vx = -100;
			}
			if(vy > 100){
				vy = 100;
			}else if(vy < -100){
				vy = -100;
			}
			
			x += vx;
			y += vy;
			angle += rv;
			
			//keep angle within bounds from 0 to 2pi
			if(angle > Math.PI*2){
				angle = 0;
			}else if(angle < 0){
				angle = Math.PI*2;
			}
			
			//stop yaris if it collides with ground
			//if(onGround && ay <= 0) {
				//y = (int) (40);
			//}
			
			//rotate the yaris display variables to match physics
			
			//tx.rotate(rv, displayX/*-offsetx*scale*/, displayY/*-offsety*scale*/);
			//tx.setToRotation(-angle, displayX, displayY);
			tx.setToTranslation(displayX, displayY);
			tx.rotate(-angle);
	
			tx.translate(-offsetx, -offsety);
			tx.scale(scale, scale);
			
			//create bounds for yaris & rotate them to match physics
			bounds = new Rectangle(92,206,900,359);
			bounds = tx.createTransformedShape(bounds);
		}else{
			ax = 4;
			ay = -4;
			vx = 3;
			vy = -5.5;
			
			if(onRampTop){
				if(y > 1050){
					angle -= .02;
					x += 10;
					y -= 4;
				}else if(y > 800){
					angle -= 0.01;
					x += 3;
					y -= 8;
				}else if (y <= 800 && y > 250){
					angle = 5.4;
					vx += ax;
					vy += ay;
					x += vx;
					y += vy;
				}else if (y <= 250 && x < 670){
					angle += 0.08;
					x += 10;
					y -= 4;
				}else{
					onRampTop = false;
					vx = mass*0.2;
					vy = mass*0.2;
				}
				
				/*if(y < ramp.getRampTopY()){
					onRampTop = false;
					onRampMid = true;
				}*/
			}
			
			
			if(angle > Math.PI*2){
				angle = 0;
			}else if(angle < 0){
				angle = Math.PI*2;
			}
			
			tx.setToTranslation(displayX, displayY);
			tx.rotate(-angle);
	
			tx.translate(-offsetx, -offsety);
			tx.scale(scale, scale);
			
			
		}
	}
	
	public void updateAccelerations(){
		double windAngle = Math.atan2(vy, vx);
		//System.out.println(windAngle);
		
		double correctedAngle = 0;
		/*if(angle < -Math.PI/2){
			correctedAngle = -Math.PI - angle;
		}else if(angle > Math.PI/2){
			correctedAngle = Math.PI-angle;
		}else{
			correctedAngle = angle;
		}*/
		
		if(windAngle < 0){
			correctedAngle = windAngle + 2*Math.PI;
		}else{
			correctedAngle = windAngle;
		}
		//System.out.println(correctedAngle);
		
		double angleOfAttack = angle - correctedAngle;
		if(Math.abs(angle - correctedAngle) > Math.PI){
			angleOfAttack = correctedAngle - angle;
		}
		
		
		double lift = .5 * (Math.pow(vx, 2)+Math.pow(vy, 2)) * angleOfAttack;
		double drag = Math.abs( lift * Math.pow(liftdragratio, -1));
		//System.out.println("angle: "+ angle);
		//System.out.println("angleOfAttack: " + angleOfAttack);
		//System.out.println("lift: " + lift);
		//System.out.println("drag: " + drag);
		
		
		
		//get netforce, then offset angle to be same plane as screen
		/*if(angle < 0){
			correctedAngle = angle + 2*Math.PI;
		}else{
			correctedAngle = angle;
		}*/
		//System.out.println("cAngle: "+ correctedAngle);
		double costheta = vx / Math.sqrt(Math.pow(vx, 2) + Math.pow(vy, 2));
		double sintheta = vy / Math.sqrt(Math.pow(vx, 2) + Math.pow(vy, 2));
		
		double fx = (appliedThrust - drag)*costheta - lift*sintheta;
		double fy = (appliedThrust - drag)*sintheta + lift*costheta + gravity;
		
		
		////////////old system that exists just in case
		
		/*double tempNet = Math.sqrt(Math.pow(tempX, 2)+Math.pow(tempY,2));
		double tempAngle = Math.atan(tempY/tempX);
		System.out.println("temp atan : "+ tempAngle);
		if(tempX < 0){
			tempAngle += Math.PI;
			System.out.println("tempX is negative");
		}
		tempAngle += correctedAngle;
		System.out.println("tempAngle: "+ tempAngle);
		//System.out.println(tempNet);
		
		
		//apply gravity
		double fx = Math.cos(tempAngle)*tempNet;
		System.out.println("fx: "+fx);
		double fy = Math.sin(tempAngle)*tempNet + gravity;
		
		double dragAngle = Math.atan(vy/vx);
		if(vx < 0){
			dragAngle += Math.PI;
		}
		fx -= Math.cos(dragAngle)*drag;
		fy -= Math.sin(dragAngle)*drag;
		
		
		System.out.print("fx: "+fx);
		System.out.println(" fy: "+fy);*/
		
		
		//double netforce = Math.sqrt(Math.pow(fx, 2)+Math.pow(fy,2));
		//double netvelocity = Math.sqrt(Math.pow(vx, 2)+Math.pow(vy, 2));

		//System.out.println(tempNet);
		
		
		//fy = Math.sin(netForceAngle)*netforce;
		ax = fx / mass;
		ay = fy / mass;
	}	
	
	//paint and transform mumbo jumbo 
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(img, tx, null);
		g2.setColor(Color.BLACK);
		//g2.draw(bounds);
	}
	
	// converts image to make it drawable in paint
	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = Player.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
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
