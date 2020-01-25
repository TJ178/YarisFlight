

//public class Cloud extends Entity{
	

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

import javax.swing.ImageIcon;

public class Cloud extends Entity {

	private int cx , cy; 						//position of car;
	private int cwidth = 85, cheight = 40; 		//width and height of cars
	private Image cimg;					//car image
	private double scale = 0.5;
	
	private int cvx, cvy;	
	
	
 
//car constructor 
public Cloud(String fileName) {
	cx = (int)(Math.random()*1000-500);
	cy = (int)(Math.random()*550+250);
	cvx = 0;
	cvy = 0;
	cwidth = (int)(386*scale);
	cheight = (int)(167*scale);
	
	
	
	//ok to be blackboxed and not explain
	cimg = getImage(fileName);
	init(cx, cy);
}

public Cloud(String fileName, int startCX, int startCY) {
	cx = startCX;
	cy = startCY;
	cvx = 0;
	cvy = 0;
	cwidth = 386;
	cheight = 167;
	
	
	//ok to be blackboxed and not explain
	cimg = getImage(fileName);
	init(cx, cy);
}

//cars can't move diagonally
public void setCvx(int cxVelocity) {
	cvx = cxVelocity;
	
	if(cvy!=0) {
		cvx = 0;
	}
}


public void setCvy(int cyVelocity) {
	cvy = cyVelocity;
	if(cvx!=0) {
		cvy = 0;
	}
}

//the cars reset loop back to where they started when they go off the screen
public void move() {
	ctx.translate(cvx,  cvy);
	
	if (ctx.getTranslateX() > 1500 && cvx > 0) {
		ctx.setToTranslation(-470, cy);
	}
	
	if (ctx.getTranslateX() < -2000 && cvx < 0) {
		ctx.setToTranslation(1700, cy);
	}
}



private AffineTransform ctx = AffineTransform.getTranslateInstance(cx, cy);


//two paint functions because need to implement the superclass paint(Graphics)

public void paint(Graphics g, int playerx, int playery) {
	Graphics2D g2 = (Graphics2D) g;
	
	
	if (ctx.getTranslateX() < -100-cwidth) {
		cx = playerx + 1000;
		
	}else if (ctx.getTranslateX() > 1100) {
		cx = playerx;
	}
	if (ctx.getTranslateY() < -100) {
		cy = playery -800;
		//System.out.println(cy);
		
	}else if (ctx.getTranslateY() > 900) {
		cy = playery + cheight;
		//System.out.println(cy);
	}
	ctx.setToTranslation(cx-playerx, -cy+playery);
	ctx.scale(scale,scale);
	
	
	g2.drawImage(cimg, ctx, null);
}

public void paint(Graphics g) {
	Graphics2D g2 = (Graphics2D) g;
	g2.drawImage(cimg, ctx, null);
	
	//g2.draw(new Rectangle((int)(ctx.getTranslateX()), (int)(ctx.getTranslateY()), cwidth, cheight)); 	
}


private void init(double a, double b) {
	ctx.setToTranslation(a, b);
	ctx.scale(scale,scale);
}

	//getters and setters for car variables
	public int getCx() {
	return (int) ctx.getTranslateX();
}

public void setCx(int cx) {
	this.cx = cx;
}

public int getCy() {
	return (int) ctx.getTranslateY();
}

public void setCy(int cy) {
	this.cy = cy;
}

public int getCwidth() {
	return cwidth;

}

public void setCwidth(int cwidth) {
	this.cwidth = cwidth;
}

public int getCheight() {
	return cheight;
}

public void setCheight(int cheight) {
	this.cheight = cheight;
}

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
	
}