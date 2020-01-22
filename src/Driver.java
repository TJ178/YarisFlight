import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Driver  extends JPanel implements ActionListener, KeyListener, MouseListener, MouseMotionListener{
	private int screen_height = 800;
	private int screen_width = 1000;
	private Timer t;
	
	public Cloud[] cloudRow1 = new Cloud[5];
	
	Player player;
	Ground ground;
	Cloud cloud;
	
	WingsUpgrade wings;
	EngineUpgrade engine;
	
	CollisionHandler collision;
	
	//put variables // things to update in here
	public void update(){
		player.move();
		wings.moveTo(player.getAngle());
		engine.moveTo(player.getAngle());
		
		/*if(collision.inGround()) {
			//player.setVx(0);
			//player.setVy(0);
			player.setGround(true);
		}else {
			player.setGround(false);
		}*/
		
	}
	
	public void paint(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, screen_width, screen_height);
		g.setColor(Color.blue);
		
		//paint clouds
		for(int i = 0; i< cloudRow1.length; i++) {
			cloudRow1[i].paint(g, player.getX(), player.getY());
			//System.out.println("CLOUD" + cloudRow1[i].getX());	
		}
		
		//wing paint
		engine.paint(g);
		if(wings.getLevel() < 1) {
			wings.paint(g);
		}
		player.paint(g);

		if(wings.getLevel() > 0) {
			wings.paint(g);
		}
		
		g.setColor(Color.black);
		g.drawString("x: " + player.getX(), 0, 10);
		g.drawString("y: " + player.getY(), 0, 20);
		g.drawString("ground: " + player.onGround(), 0, 30);
		g.drawString("Thrust: " + player.getThrust(), 0, 40);
		g.drawString("accelX: " + player.getAx(),0, 50);
		g.drawString("accelY: " + player.getAy(),0, 60);
		g.drawString("vx: " + player.getVx(),0, 70);
		g.drawString("vy: " + player.getVy(),0, 80);

		ground.paint(g, player.getY());
		
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
		player = new Player("yarisright.png");
		//cloud = new Cloud("cloud.png");
		
		for(int i = 0; i < cloudRow1.length; i++) {
			cloudRow1[i] = new Cloud("cloud.png");
		}

		//player.setX(screen_width/2);
		//player.setY(screen_height/2);
		wings = new WingsUpgrade();
		wings.upgrade1();
		//wings.upgrade2();
		
		cloud = new Cloud("cloud.png");
		
		engine = new EngineUpgrade();
		engine.upgrade1();
		engine.upgrade2();
		
		ground = new Ground();
		
		collision = new CollisionHandler(player, ground);
		
	
		
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
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		switch(key){
		case 37: //left arrow key
			player.setRv(.05);
			break;
		case 39:
			player.setRv(-.05);
			break;
		case 40:
			player.setThrust(20);
			break;
		case 38:
			player.setThrust(10);
			break;
		default:
			System.out.println(key);
			//update();
			//repaint();
		}
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		switch(key){
		case 37: //left arrow key
			player.setRv(0);
			break;
		case 39:
			player.setRv(0);
			break;
		case 40:
			player.setThrust(0);
			break;
		case 38:
			player.setThrust(0);
			break;
		default:
			System.out.println(key);
		}
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
