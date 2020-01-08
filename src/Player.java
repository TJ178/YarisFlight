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
	private boolean alive;		//aliveness of our reptilian  friend
	private int width, height; 	// size of froggy
	private Image img;			//image
	private int vx, vy;
	private boolean noSmash;
	private int logIndex;
	private int score;
	private int lives;
	private int highscore;
	
	public Player(String fileName) {
		width = 50;
		height = 50;
		x = (900/2) - (width/2);
		y = 800;
		vx = 0;
		vy = 0;
		img = getImage(fileName);
		init(x, y);
		noSmash = false;
		score = 0;
		highscore = 0;
		lives = 5;
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
	public void resetRound(int keyCode) {
		if(keyCode == 32 && !isAlive()) {
			reset();
			lives = 5;
			score = 0;
		}
	}
	public boolean isAlive() {
		return lives > 0;
	}
	public void score() {
		reset();
		score ++;
	}
	public void isDed() {
		reset();
		lives +=-1;
	}
	public int getLives() {
		return lives;
	}
	public void setLives(int newLives) {
		lives = newLives;
	}
	public void decrementLives() {
		lives+=-1; //:)
	}
	public int getHScore() {
		return highscore;
	}
	public void newHScore() {
		if(score > highscore) {
			highscore = score;
		}
	}
	public int getScore() {
		return score;
	}
	public void setIndex(int num) {
		logIndex = num;
	}
	public int getIndex() {
		return logIndex;
	}
	public boolean getSmash() {
		return noSmash;
	}
	public void setSmash(boolean bool) {
		noSmash = bool;
	}
	public void setVx(int newVx) {
		vy = 0;
		vx = newVx;
	}
	public void setVy(int newVy) {
		vx = 0;
		vy = newVy;
	}
	
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
	
	public boolean collided(int ox, int oy, int ow, int oh) {
		Rectangle obs = new Rectangle(ox, oy, ow, oh);
		Rectangle froggy = new Rectangle((int) tx.getTranslateX(), (int) tx.getTranslateY(), width, height);
		return obs.intersects(froggy);
	}
	public void move() {
		tx.translate(vx, vy);
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