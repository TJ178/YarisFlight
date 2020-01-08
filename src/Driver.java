import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javafx.scene.layout.Background;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Driver  extends JPanel implements ActionListener, KeyListener, MouseListener, MouseMotionListener{
	private int screen_height = 800;
	private int screen_width = 1000;
	private Timer t;
	
	
	//put variables // things to update in here
	public void update(){
		
	}
	
	public void paint(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, screen_width, screen_height);
		
		g.drawRect(10, 10, 50, 50);
	}
	
	
	
	
	
	
	
	
	
	
/////////////////////////////////////////////// DRIVER ///////////////////////////////////////////
	@Override
	public void actionPerformed(ActionEvent e) {
		update();
		repaint();
	}
	
	
	public static void main(String[] args) {
		Driver d = new Driver();
		
	}
	
	public Driver() {
		JFrame f = new JFrame();
		f.setTitle("Yaris Flight");
		f.setSize(screen_width, screen_height);
		f.setResizable(false);
		f.addKeyListener(this);
		f.addMouseListener(this);
		
		//sprite instantiation
		
		f.add(this);
		t = new Timer(17, this);
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	
	
	

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
