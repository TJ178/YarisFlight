
public class ScoreKeeper {
	private int money;
	
	//instance variables (for each individual flight)
	private int altMaxCur;
	private int distMaxCur;
	private int speedMaxCur;
	private long startTime;
	private int moneyEarned;
	
	private boolean newAltRecord;
	private boolean newDistRecord;
	private boolean newSpeedRecord;
	
	
	//records - best out of every flight
	private int altitudeRecord;
	private int distanceRecord;
	private int speedRecord;
	
	public ScoreKeeper(){
		money = 0;
	}
	
	
	public void calculateScores(){
		moneyEarned += altMaxCur*.1;
		moneyEarned += distMaxCur*.1;
		moneyEarned += calcMaxSpeedKmh(speedMaxCur);
		moneyEarned += (System.currentTimeMillis() - startTime)*.0001;
		money += moneyEarned;
		
		if(altMaxCur > altitudeRecord) {
			altitudeRecord = altMaxCur;
			newAltRecord = true;
		}
		
		if(speedMaxCur > speedRecord) {
			speedRecord = speedMaxCur;
			newSpeedRecord = true;
		}
		
		if(distMaxCur > distanceRecord) {
			distanceRecord = distMaxCur;
			newDistRecord = true;
		}
	}
	
	
	public void trackScores(Player p) {
		if(p.getY() > altMaxCur) {
			altMaxCur = p.getY();
		}
		
		double speed = Math.sqrt(Math.pow(p.getVx(), 2)+Math.pow(p.getVy(), 2));
		if(speed > speedMaxCur){
			speedMaxCur = (int)(speed);
		}
		
		if(p.getX() > distMaxCur) {
			distMaxCur = p.getX();
		}
		
	}
	
	public int getMoney() {
		return money;
	}
	public int getMoneyEarned() {
		return moneyEarned;
	}
	public void spendMoney(int m) {
		money -= m;
	}


	//booleans for checking if current record is new record
	public boolean getNewAltRecord() {
		return newAltRecord;
	}
	public boolean getNewDistRecord() {
		return newDistRecord;
	}
	public boolean getNewSpeedRecord() {
		return newSpeedRecord;
	}
	
	//get max values for this run
	
	public int getMaxAlt() {
		return altMaxCur;
	}
	public int getMaxSpeed() {
		return speedMaxCur;
	}
	public int getMaxDist() {
		return distMaxCur;
	}
	
	public double calcMaxSpeedKmh(double speed) {
		double maxspeed = speed*0.055*60*1000/3600;
		int temp = (int) (maxspeed * 100);
		maxspeed = maxspeed - (maxspeed - temp*0.01);
		return maxspeed;
	}


	

	public void start() {
		newAltRecord = false;
		newDistRecord = false;
		newSpeedRecord = false;
		startTime = System.currentTimeMillis();
		
		moneyEarned = 0;
		altMaxCur = 0;
		distMaxCur = 0;
		speedMaxCur = 0;
		
	}
}
