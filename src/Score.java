
public class Score {
	private int altitudeMax;
	private int distanceMax;
	private int speedMax;
	private long startTime;
	
	public Score() {
		altitudeMax = 0;
		distanceMax = 0;
		speedMax = 0;
		startTime = System.currentTimeMillis();
	}
	
	public void updateScores(int alt, int dist, int speed) {
		if(alt > altitudeMax) {
			altitudeMax = alt;
		}
		
		if(dist > distanceMax) {
			distanceMax = dist;
		}
		
		if(speed > speedMax) {
			speedMax = speed;
		}
	}
	
	public int getMaxAlt() {
		return altitudeMax;
	}
	
	public int getMaxDist() {
		return distanceMax;
	}
	
	public int getMaxSpeed() {
		return speedMax;
	}
	
	public int getTimeElapsed() {
		return (int)(startTime - System.currentTimeMillis());
	}
	
}
