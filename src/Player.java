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
	
	//attributes
	private int x, y;
	private int displayX = 500, displayY = 400;
	private double angle;
	
	private double scale = .25;
	private int offsetx = (int)(540*scale);
	private int offsety = (int)(390*scale);
	private boolean alive;		//aliveness of our manual 2007 red yaris driving friend
	private int width = (int)(1024*scale); //768x 652
	private int height= (int)(768*scale); 	// size of yar'
	private Image img;			//image
	
	private double vx, vy;			//velocity 
	private double ax, ay;			//acceleration
	private int appliedForceX = 5, appliedForceY;
	private double appliedThrust;
	private int netArea = 10; ////TODO: make netArea a function of the angle
	private int mass = 100;
	private double liftdragratio = 10
			;
	
	private boolean onGround = false;
	
	private double rv;	    	//rotation velocity  
	private double pi = Math.PI;
	
	private int airDrag = 0;
	private int gravity = -10;
	private Shape bounds;
	
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
	
	public int getAppliedForceY() {
		return appliedForceY;
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
		//tx.translate(vx, vy);
		updateAccelerations();
		vx += ax;
		vy += ay;
		
		
		x += vx;
		y += vy;
		
		//if(onGround && ay <= 0) {
			//y = (int) (40);
		//}
		
		System.out.println("Accel x: "+ax+" y: "+ay);
		tx.rotate(rv, displayX/*-offsetx*scale*/, displayY/*-offsety*scale*/);
		//tx.setToRotation(angle, displayX, displayY);
		angle = -extractAngle(tx);
		//System.out.println(angle);
		
		bounds = new Rectangle(92,206,900,359);
		//AffineTransform trans = (AffineTransform) tx.clone();
		//trans.setToScale(1, 1);
		bounds = tx.createTransformedShape(bounds);
		
	}
	
	public void updateAccelerations(){
		double windAngle = Math.atan(vy/vx);
		if(vx < 0){
			windAngle += Math.PI;
		}
		
		
		System.out.println(windAngle);
		
		double correctedAngle = 0;
		if(angle < -Math.PI/2){
			correctedAngle = -Math.PI - angle;
		}else if(angle > Math.PI/2){
			correctedAngle = Math.PI-angle;
		}else{
			correctedAngle = angle;
		}
		double angleOfAttack = correctedAngle - windAngle;
		
		double lift = .5 * (Math.pow(vx, 2)+Math.pow(vy, 2)) * angleOfAttack;
		double drag = Math.abs( lift * Math.pow(liftdragratio, -1));
		System.out.println("cAngle: "+ correctedAngle);
		System.out.println("angleOfAttack: " + angleOfAttack);
		System.out.println("lift: " + lift);
		System.out.println("drag: " + drag);
		//x and y components, but currently offset in relation to the player's angle
		double tempX = appliedThrust;
		double tempY = lift;
		//System.out.println(tempX);
		//System.out.println(tempY);
		
		
		
		//get netforce, then offset angle to be same plane as screen
		if(angle < 0){
			correctedAngle = angle + Math.PI;
		}else{
			correctedAngle = angle;
		}
		
		double tempNet = Math.sqrt(Math.pow(tempX, 2)+Math.pow(tempY,2));
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
		System.out.println(" fy: "+fy);
		
		
		//double netforce = Math.sqrt(Math.pow(fx, 2)+Math.pow(fy,2));
		//double netvelocity = Math.sqrt(Math.pow(vx, 2)+Math.pow(vy, 2));

		//System.out.println(tempNet);
		
		
		//fy = Math.sin(netForceAngle)*netforce;
		ax = fx / mass;
		ay = fy / mass;
		
		
		
		////////////////////////works but only on right quadrants
		
		
		/*double windAngle = Math.atan(vy/vx);
		if(vx < 0){
			windAngle *= -1;
		}
		double angleOfAttack = angle - windAngle;
		
		double lift = .5 * (Math.pow(vx, 2)+Math.pow(vy, 2)) * angleOfAttack;
		
		double drag = Math.abs( lift * Math.pow(liftdragratio, -1));
		System.out.println("angle: "+ angle);
		System.out.println("angleOfAttack: " + angleOfAttack);
		System.out.println("lift: " + lift);
		System.out.println("drag: " + drag);
		//x and y components, but currently offset in relation to the player's angle
		double tempX = appliedThrust;
		double tempY = lift;
		//System.out.println(tempX);
		//System.out.println(tempY);
		
		
		
		//get netforce, then offset angle to be same plane as screen
		double tempNet = Math.sqrt(Math.pow(tempX, 2)+Math.pow(tempY,2));
		double tempAngle = Math.atan(tempY/tempX) + angle; /// need to fix angle offset -> make sure that angle is based on 0 -> 2pi rather than 0 -> pi/2
		//System.out.println(tempAngle);
		//System.out.println(tempNet);
		
		
		//apply gravity
		double fx = Math.cos(tempAngle)*tempNet;
		double fy = Math.sin(tempAngle)*tempNet + gravity;
		
		double dragAngle = Math.atan(vy/vx);
		if(vx < 0){
			dragAngle *= -1;
		}
		fx -= Math.cos(dragAngle)*drag;
		fy -= Math.sin(dragAngle)*drag;
		
		
		System.out.print("fx: "+fx);
		System.out.println(" fy: "+fy);
		
		
		//double netforce = Math.sqrt(Math.pow(fx, 2)+Math.pow(fy,2));
		//double netvelocity = Math.sqrt(Math.pow(vx, 2)+Math.pow(vy, 2));

		//System.out.println(tempNet);
		
		
		//fy = Math.sin(netForceAngle)*netforce;
		ax = fx / mass;
		ay = fy / mass;*/
		
		
		///////////// doesn't work
		
		/*double fx = appliedForceX;
		double fy = appliedForceY;
		if(!onGround) {
			fy += gravity;
		}
		
		
		
		//double netForceAngle = Math.atan(fy/fx);
		
		
		
		double lift = .5 * (Math.pow(vx, 2)+Math.pow(vy, 2)) * angle; //* angle; //* lift coefficient changes with angle
		double drag = Math.pow(lift,2) * Math.pow(liftdragratio, -1);
		System.out.println("angle: "+ angle);
		System.out.println("lift: " + lift);
		System.out.println("drag: " + drag);
		//x and y components, but currently offset in relation to the player's angle
		double tempX = appliedThrust +0.05;
		double tempY = lift + 0.05;
		System.out.println(tempX);
		System.out.println(tempY);
		
		
		
		//get netforce, then offset angle to be same plane as screen
		double tempNet = Math.sqrt(Math.pow(tempX, 2)+Math.pow(tempY,2));
		double tempAngle = Math.atan(tempY/tempX) + angle; /// need to fix angle offset -> make sure that angle is based on 0 -> 2pi rather than 0 -> pi/2
		//System.out.println(tempAngle);
		//System.out.println(tempNet);
		
		
		//apply gravity
		double fx = Math.cos(tempAngle)*tempNet;
		double fy = Math.sin(tempAngle)*tempNet + gravity;
		
		double dragAngle = Math.atan(vy/vx);
		fx -= Math.cos(dragAngle)*drag;
		fy -= Math.sin(dragAngle)*drag;
		
		
		System.out.print("fx: "+fx);
		System.out.println(" fy: "+fy);
		
		
		//double netforce = Math.sqrt(Math.pow(fx, 2)+Math.pow(fy,2));
		//double netvelocity = Math.sqrt(Math.pow(vx, 2)+Math.pow(vy, 2));

		//System.out.println(tempNet);
		
		
		//fy = Math.sin(netForceAngle)*netforce;
		ax = fx / mass;
		ay = fy / mass;
		*/
	}
	
	private static double extractAngle(AffineTransform at)
    {
        Point2D p0 = new Point();
        Point2D p1 = new Point(1,0);
        Point2D pp0 = at.transform(p0, null);
        Point2D pp1 = at.transform(p1, null);
        double dx = pp1.getX() - pp0.getX();
        double dy = pp1.getY() - pp0.getY();
        double angle = Math.atan2(dy, dx);
        return angle;
    }
	
	
	
	//rotate methods
	public void rotateCW() {
		angle += rv;
	}
	
	public void rotateCCW() {
		angle -= rv;
	}
	
	//paint and transform mumbo jumbo 
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(img, tx, null);
		g2.setColor(Color.BLACK);
		g2.draw(bounds);
	}

	private void init(double a, double b) {
		tx.setToTranslation(a, b);
		tx.scale(scale, scale);
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