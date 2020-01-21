

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


		public void paint(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			//g2.drawImage(img, btx, null);
			
			//g.setFont(font);
			
			//background
			g.setColor(Color.red); 				
			g.fillRect(0, 0, 900, 900);
			
			//title
			g.setColor(Color.white);
			g.drawString("Nice work! You flew:", 300, 100);
			
			//g.drawString(distanceMax+" meters", x, y);
		
		
			
		}
		
		
		
		
	}

