import java.awt.Image;
import java.awt.Shape;

public class PhysicsTesting {
	
	//variables
	static int x, y;
	static double angle = -Math.PI*.75;
	static double vx = 0;
	static double vy = 10;			//velocity 
	static double ax = 0;
	static double ay = 0;			//acceleration
	
	//applied force
	static int appliedForceX, appliedForceY;
	static double appliedThrust = 0;
	
	//constants for player
	static int netArea = 10; ////TODO: make netArea a function of the angle
	static int mass = 100;
	static double liftdragratio = 4;
	static int airDrag = 0;
	
	//testing if car is on ground
	static boolean onGround = false;
	
	//constants
	static double pi = Math.PI;
	static int gravity = -10;
	
	
	
	public PhysicsTesting() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		/*for(int i = 0; i < 30; i++){
			updateAccelerations();
			System.out.println("ax:" + ax);
			System.out.println("ay:" + ay);
			System.out.println();
			vx += ax;
			vy += ay;
		}*/
		
		/*for(int i = 0; i < 6; i++){
			double d = (Math.PI/6)*i;
			angle = d;
			double windAngle = 0;
			if(vx < 0){
				windAngle += Math.PI;
			}
			System.out.println(windAngle);
			
			double tempAngle;
			if(angle < 0){
				tempAngle = angle + Math.PI;
			}else{
				tempAngle = angle;
			}
			double angleOfAttack = tempAngle - windAngle;
			System.out.print("angle: " + angle);
			System.out.println("   aa: "+ angleOfAttack);
		}
		
		for(int i = 5; i >= 0; i--){
			double d = -(Math.PI/6)*i;
			angle = d;
			double windAngle = 0;
			if(vx < 0){
				windAngle += Math.PI;
			}
			System.out.println(windAngle);
			
			double tempAngle;
			if(angle < 0){
				tempAngle = angle + Math.PI;
			}else{
				tempAngle = angle;
			}
			double angleOfAttack = tempAngle - windAngle;
			System.out.print("angle: " + angle);
			System.out.println("   aa: "+ angleOfAttack);
		}*/
		System.out.println(Math.cos(Math.PI*6));
	

	}
	
	public static void updateAccelerations(){
		
		double windAngle = Math.atan(vy/vx);
		if(vx < 0){
			windAngle += Math.PI;
		}
		System.out.println(windAngle);
		
		double correctedAngle;
		if(angle < 0){
			correctedAngle = angle + 2*Math.PI;
		}else{
			correctedAngle = angle;
		}
		double angleOfAttack = correctedAngle - windAngle;
		
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
		double tempAngle = Math.atan(tempY/tempX);
		if(tempX < 0){
			tempAngle += Math.PI;
		}
		tempAngle += correctedAngle;
		//System.out.println(tempAngle);
		//System.out.println(tempNet);
		
		
		//apply gravity
		double fx = Math.cos(tempAngle)*tempNet;
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
	}

}
