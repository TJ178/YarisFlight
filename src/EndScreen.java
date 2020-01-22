

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.image.*;
import java.awt.geom.AffineTransform;

public class EndScreen {

//	public Score currentScore;

		public void paint(Graphics g, ScoreKeeper ScoreKeeper, Score currentScore) {
			Graphics2D g2 = (Graphics2D) g;
			
		//	ScoreKeeper currentScore;
			
			//g2.drawImage(img, btx, null);
			
			//g.setFont(font);
			
			//background
			g.setColor(Color.red); 				
			g.fillRect(0, 0, 1000, 1000);
			
			//title
			g.setColor(Color.white);
			g.drawString("Nice work! You now have "+ScoreKeeper.getMoney()+" dollars", 300, 100);
			
			g.drawString("Distance flown: "+currentScore.getMaxDist()*18+" meters", 300, 200);
			g.drawString("Max Altitude: "+currentScore.getMaxAlt()*18+" meters", 300, 300);
			g.drawString("Max Speed: "+currentScore.getMaxSpeed()*18+" meters/sec", 300, 400);
			
			g.drawString("High Scores:", 400, 700);
			g.drawString("Distance: "+currentScore.getMaxDist(), 300, 500);
			g.drawString("Altitude: "+currentScore.getMaxAlt(), 300, 600);
			g.drawString("Speed: "+currentScore.getMaxSpeed(), 300, 700);
			
		
			
		}
		
		
		
		
	}

