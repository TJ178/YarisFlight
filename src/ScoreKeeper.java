
public class ScoreKeeper {
	private int money;
	
	//instance variables (for each individual flight)
	private boolean newAltRecord;
	private boolean newDistRecord;
	private boolean newSpeedRecord;
	
	
	//records - best out of every flight
	private int altitudeRecord;
	private int distanceRecord;
	private int speedRecord;
	
	public Score currentScore;
	
	public ScoreKeeper(){

	}
	
	
	public void calculateScores(){
		money += currentScore.getMaxAlt()*.1;
		money += currentScore.getMaxDist()*.1;
		money += currentScore.getMaxSpeed();
		money += currentScore.getTimeElapsed()*.001;
		
		if(currentScore.getMaxAlt() > altitudeRecord) {
			altitudeRecord = currentScore.getMaxAlt();
		}
		
		if(currentScore.getMaxSpeed() > speedRecord) {
			altitudeRecord = currentScore.getMaxSpeed();
		}
		
		if(currentScore.getMaxDist() > distanceRecord) {
			altitudeRecord = currentScore.getMaxDist();
		}
		
	}
	
	public void start() {
		currentScore = new Score();
		newAltRecord = false;
		newDistRecord = false;
		newSpeedRecord = false;
	}
}
