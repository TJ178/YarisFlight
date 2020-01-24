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


public class UpgradeScreen {
	
	public void paint(Graphics g, WingsUpgrade wings, EngineUpgrade engine, ScoreKeeper ScoreKeeper, Score currentScore) {
		Graphics2D g2 = (Graphics2D) g;
		
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, 1000, 1000);
		
		g.setColor(Color.WHITE);
		g.drawString("Upgrades", 400, 50);
		g.drawString("You have "+ScoreKeeper.getMoney()+" dollars.", 350, 100);
		
		g.drawString("Wings:", 400, 150);
		if(wings.getLevel() < 1) {
			g.drawString("Level 1 ($420)", 300, 250);		
			g.drawString("Level 2 ($6969)", 700, 250);
		}
		
		if(wings.getLevel()==1) {
			g.drawString("Level 2 ($6969)", 700, 250);
		}
		
		if(wings.getLevel()>=2) {
			g.drawString("Wings are Fully Upgraded.", 400, 250);
		}
		
		g.drawString("Engine:", 400, 400);
		
		if(engine.getLevel() < 1) {
			g.drawString("Level 1 ($420)", 300, 550);
			
			g.drawString("Level 2 ($6969)", 700, 550);
			}
			
			if(engine.getLevel()==1) {
				g.drawString("Level 2 ($6969)", 700, 550);
			}
			
			if(engine.getLevel()>=2) {
				g.drawString("Engine is Fully Upgraded.", 400, 550);
			}
		}
	
}