
public class FuelUpgrade {
	private int level = 0;
	
	private double[] fuelMultipliers = {1,1.25,1.75,2.5,3.5};
	
	private int[] prices = {0,200,500,1000,3000};
	
	public FuelUpgrade() {}
	
	public void upgrade() {
		level++;
	}
	
	public int getLevel() {
		return level;
	}
	
	public double getMultiplier() {
		return fuelMultipliers[level];
	}
	
	public int getUpgradePrice() {
		return prices[level+1];
	}
}
