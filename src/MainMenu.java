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

public class MainMenu {


		public void paint(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			//g2.drawImage(img, btx, null);
			
			//g.setFont(font);
			
			//background
			g.setColor(Color.cyan); 				
			g.fillRect(0, 0, 1000, 1000);
			
			//title
			g.setColor(Color.white);
			g.drawString("Yaris Flight", 250, 50);
			
			//upgrades and play game options
			
		//	g.drawString("Upgrades", 100, 500);
			
			g.setColor(Color.RED);
			g.fillOval(450, 450, 200, 200);
			g.setColor(Color.white);
			g.drawString("Take Flight!", 450, 450);
			
			
		}
		
		
		
		
	}


