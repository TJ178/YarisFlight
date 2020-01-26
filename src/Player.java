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
	private double x, y;				//position in map
	private double vx, vy;			//velocity 
	private double ax, ay;			//acceleration
	private double angle;			//current flight angle
	private double rv;	    		//rotation velocity
	private double appliedThrust;	//current forward thrust from yaris
	
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
	private int lift = 1;
	private int drag = 5;
	private double liftdragratio = (double)(lift) / drag;
	private int gravity = -15;					//make her fall
	
	private double thrustAmount = 0; 	// amount of thrust that is output
	
	//gameplay variables
	private boolean onGround = false;			//keep track if yaris dies
	private boolean alive;						//aliveness of our manual 2007 red yaris driving friend
	private Shape bounds;						//collision boundaries for yaris (for obstacles and ground)
	public boolean onRamp;
	
	public Player(String fileName) {
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
	
	public void setThrust(double newThrust){
		appliedThrust = newThrust;
	}
	public double getThrust(){
		return appliedThrust;
	}
	
	public double getPossibleThrust() {
		return thrustAmount;
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
		return (int)x;
	}
	
	public void setX(int newX) {
		x = newX;
	}
	
	public int getY() {
		return (int)y;
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
	
	public void addUpgrade(Upgrade d) {
		drag += d.getDrag();
		mass += d.getWeight();
		lift += d.getLift();
		if(d instanceof EngineUpgrade) {
			thrustAmount = d.getThrust();
			System.out.println(thrustAmount);
		}
		liftdragratio = lift / drag; 
		
	}
	
	public void removeUpgrade(Upgrade d) {
		drag -= d.getDrag();
		mass -= d.getWeight();
		lift -= d.getLift();
		if(d instanceof EngineUpgrade) {
			thrustAmount = 0;
		}
		liftdragratio = lift / drag;
	}

	
	public void move() {
		//update physics and apply new velocity
		if(!onRamp && !onGround){
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
			if(onGround && ay <= 0) {
				y = (int) (40);
			}
			
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
		}else if(onRamp){
			
			startAnimation();	
		}else {
			ax = 0;
			ay = 0;
			vx = 0;
			vy = 0;
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
		double lift;
		double drag;
		
		if(this.lift > 1){
			lift = .5 * (Math.pow(vx, 2)+Math.pow(vy, 2)) * angleOfAttack;
			drag = Math.abs( lift * Math.pow(liftdragratio, -1));
		}else{
			lift = 0;
			drag = 2;
		}
		
		if(Math.sqrt(Math.pow(vx, 2)+Math.pow(vy, 2)) < 20 && angle > Math.PI/2-0.4 && angle < Math.PI/2+0.4) {
			lift = 0;
			drag = 0;
		}
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
		//System.out.println("fy:" + (appliedThrust - drag)*sintheta);
		
		
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
	
	
	public void startAnimation(){
		ax = 4;
		ay = -4;
		vx = 2.4;
		vy = -7;
		
		if(onRamp){
			if(y > 1050){
				angle -= .011;
				x += 5;
				y -= 2.5;
			}else if(y > 970){
				angle -= 0.008;
				x += 4;
				y -= 3.3;
			}else if(y <= 970 && y > 800){
				angle -= 0.01;
				x += 3;
				y -= 8;
			}else if (y <= 920 && y > 270){
				angle = 5.3;
				vx += ax;
				vy += ay;
				x += vx;
				y += vy;
			}else if (y <= 270 && x < 650){
				angle += 0.05;
				x += 9;
				y -= 2.5;
			}else{
				onRamp = false;
				vx = mass*0.2;
				vy = mass*0.2;
			}
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
		x = -400;
		y = 1200;
		angle = 0;
		onRamp = true;
	}

}
