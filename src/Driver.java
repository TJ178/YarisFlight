import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Driver  extends JPanel implements ActionListener, KeyListener, MouseListener, MouseMotionListener{
	private int screen_height = 800;
	private int screen_width = 1000;
	private Timer t;
	private int stage = 0;
	
	public Cloud[] cloudRow1 = new Cloud[5];
	
	Player player;
	Ground ground;
	Cloud cloud;
	
	WingsUpgrade wings;		//wings upgrade
	EngineUpgrade engine;	//engine upgrade
	
	CollisionHandler collision;
	
	RampUpgrade ramp;
	
	ScoreKeeper scorekeep;
  
	//UpgradeScreen upgradeScreen;

	StatusBar fuelbar;
	
	private AffineTransform tx;

	
	//put variables // things to update in here
	public void update(){
		player.move();
		wings.moveTo(player.getAngle());
		engine.moveTo(player.getAngle());
		if(!engine.getIsThrusting() && player.getThrust() > 0) {
			player.setThrust(0);
		}
		
		if(collision.inGround()) {
			player.setVx(0);
			player.setVy(0);
			player.setGround(true);
		}else {
			player.setGround(false);
		}
		
		scorekeep.trackScores(player);
		
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
		
		ground.paint(g, player.getX(), player.getY());
		ramp.paint(g,player.getX(), player.getY());

		g.setColor(Color.black);
		g.drawString("x: " + player.getX(), 0, 10);
		g.drawString("y: " + player.getY(), 0, 20);
		g.drawString("ground: " + player.onGround(), 0, 30);
		g.drawString("Thrust: " + player.getThrust(), 0, 40);
		g.drawString("accelX: " + player.getAx(),0, 50);
		g.drawString("accelY: " + player.getAy(),0, 60);
		g.drawString("vx: " + player.getVx(),0, 70);
		g.drawString("vy: " + player.getVy(),0, 80);
		g.drawString("fuel: "+ engine.getFuelPerc(), 0, 90);

		fuelbar.paint(g);
		
		
		switch(stage) {
		case 0:
			mainMenu(g);
			break;
		case 1:
			upgradeScreen(g);
			break;
		case 2:
			startGame();
			stage ++;
			break;
		case 3:
			if(collision.inGround() /*&& player.onGround() && player.getAx() > 670*/) {
				stage = 4;
				scorekeep.calculateScores();
			}
			break;
		case 4:
			endScreen(g);
			break;
		}
		
		
	}
	
	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = WingsUpgrade.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}
	
	public void startGame(){	
		scorekeep.start();	

		player.reset();
		engine.refuel();
	}
	
	
	
	
	
	
/////////////////////////////////////////////// DRIVER ///////////////////////////////////////////
	@Override
	public void actionPerformed(ActionEvent e) {
		if(stage == 3) {
			update();
		}
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
		
		//upgradeScreen = new UpgradeScreen();
		
		//cloud = new Cloud("cloud.png");
		
		for(int i = 0; i < cloudRow1.length; i++) {
			cloudRow1[i] = new Cloud("cloud.png");
		}

		player = new Player("yarisright.png");
		
		wings = new WingsUpgrade();
		//wings.upgrade1();
		//player.addUpgrade(wings);
		
		cloud = new Cloud("cloud.png");
		
		engine = new EngineUpgrade();
		//engine.upgrade1();
		//player.addUpgrade(engine);
		
		ground = new Ground("ground.png");
		
		collision = new CollisionHandler(player, ground);
		
		ramp = new RampUpgrade("Ramp_Top_realistic.png","Ramp_middle_realistic.png", "Ramp_Bottom_realisitic.png", "Ramp_ExtraPart_realisitic.png");
		
		scorekeep = new ScoreKeeper();
		
		fuelbar = new StatusBar(100, 100, 100, 30, 0, Color.yellow, false, 1, "Fuel", false, 0, 100, 50, false);
		
		/*
		if(stage == 3) {
			startGame();
		}
		*/
		
		f.add(this);
		t = new Timer(17, this);
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	
	public void upgradeScreen(Graphics g) {		
		Font font1 = new Font("Book Antiqua", Font.PLAIN, 13);
		Font font2 = new Font("Book Antiqua", Font.PLAIN, 50);
		Font font3 = new Font("Book Antiqua", Font.BOLD, 20);


		Graphics2D g2 = (Graphics2D) g;
		tx = AffineTransform.getTranslateInstance(0, 0);
		tx.scale(1,1);		
		
		g2.drawImage(getImage("upgradeBackground.png"), tx, null);
		//tx.scale(0.6, 0.6);
		//g.setFont(font1);

		//background
		//g.setColor(Color.GREEN);
		//g.fillRect(0, 0, screen_width, screen_height);
		
		//rectangles
		g.setColor(Color.CYAN);
		g.fillRect(300, 50, 400, 100);
		
		//wings
		g.fillRect(400, 300, 200, 100);
		//engine
		g.fillRect(400, 450, 200, 100);
		//fuel
		g.fillRect(400, 600, 200, 100);
		
		g.setFont(font3);
		g.setColor(Color.black);
		g.drawString("Upgrades", 450, 75);
		g.drawString("You have "+ scorekeep.getMoney()+" Dollars!", 410, 100);
		
		g.drawString("Wings:", 470, 275); 
		g.setFont(font1);
		switch(wings.getLevel()) {
			case -1:
				g.drawString("Level 1 $"+wings.getUpgradePrice(), 460, 348);		
				break;
			case 0:
				g.drawString("Level 2 $"+wings.getUpgradePrice(), 450, 348);
				break;
			case 1:
				g.drawString("Oops! Wings are Fully Upgraded!", 405, 348);
				break;
		}
		
		g.setFont(font3);
		g.drawString("Engine:", 465, 425);
		g.setFont(font1);
		switch(engine.getLevel()) {
			case -1:
				g.drawString("Level 1 $"+engine.getUpgradePrice(), 460, 498);		
				break;
			case 0:
				g.drawString("Level 2 $"+engine.getUpgradePrice(), 450, 498);
				break;
			case 1:
				g.drawString("Oops! Engine is Fully Upgraded!", 410, 498);
				break;
		}
		
		g.setFont(font3);
		g.drawString("Fuel Tank:", 460, 575);
		g.setFont(font1);
		switch(engine.getFuelManager().getLevel()) {
			case 0:
				g.drawString("Level 1 $"+engine.getFuelManager().getUpgradePrice(), 460, 648);		
				break;
			case 1:
				g.drawString("Level 2 $"+engine.getFuelManager().getUpgradePrice(), 450, 648);
				break;
			case 2:
				g.drawString("Oops! Engine is Fully Upgraded!", 410, 648);
				break;
		}
		g.setFont(font2);
		g.drawString("Press Space to Start", 250, 750);
	}
	
	public void mainMenu(Graphics g) {
		Font font1 = new Font("Book Antiqua", Font.PLAIN, 100);
		Font font2 = new Font("Book Antiqua", Font.PLAIN, 50);

		Graphics2D g2 = (Graphics2D) g;
		tx = AffineTransform.getTranslateInstance(0, 0);
		tx.scale(1,1);
		g2.drawImage(getImage("upgradeBackground.png"), tx, null);
		
		g.setColor(Color.cyan);
		g.setFont(font1);
		
		g.drawString("Yaris Flight", 250, 200);
		
		g.setFont(font2);
		
		g.drawString("Press Space to Start", 250, 400);
		
	}
	
	
	
	public void endScreen(Graphics g) {
		Font font1 = new Font("Book Antiqua", Font.PLAIN, 25);
		Font font2 = new Font("Book Antiqua", Font.PLAIN, 20);

		Graphics2D g2 = (Graphics2D) g;
		tx = AffineTransform.getTranslateInstance(200, 160);
		tx.scale(0.6, 0.6);
		g2.drawImage(getImage("upgradeBackground.png"), tx, null);
		
		g.setFont(font1);
		g.setColor(Color.cyan);
		g.drawString("Darnit you crashed the 2007 Toyota Yaris!", 250, 200);
		g.setFont(font2);
		
		if(scorekeep.getNewDistRecord()) {
			g.setColor(Color.green);
		}else {
			g.setColor(Color.cyan);
		}
		g.drawString("You flew " + scorekeep.getMaxDist()*0.055 + " meters!", 250, 250);
		
		
		if(scorekeep.getNewAltRecord()) {
			g.setColor(Color.green);
		}else {
			g.setColor(Color.cyan);
		}
		g.drawString("You reached a maximum altitude of " + scorekeep.getMaxAlt()*0.055 + " meters!", 250, 300);
		
		if(scorekeep.getNewSpeedRecord()) {
			g.setColor(Color.green);
		}else {
			g.setColor(Color.cyan);
		}
		double maxspeed = scorekeep.getMaxSpeed()*0.055*60*1000/3600;
		int temp = (int) (maxspeed * 100);
		maxspeed = maxspeed - (maxspeed - temp*0.01);
		g.drawString("You reached a top speed of " + maxspeed + " km per hour!", 250, 350);
		g.drawString("Money Earned: $" +scorekeep.getMoneyEarned(), 250, 400);
		
	}
	
	public boolean isInside(int x, int y, int xBound1, int yBound1, int xBound2, int yBound2) {
		if(x >= xBound1 && x <= xBound2 && y >= yBound1 && y <= yBound2) {
			return true;
		}
		return false;
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
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(stage == 1) {
			if(isInside(e.getXOnScreen(), e.getYOnScreen(), 400, 300, 600, 400) && wings.getUpgradePrice() < scorekeep.getMoney()) {
				switch(wings.getLevel()){
					case -1:
						scorekeep.spendMoney(wings.getUpgradePrice());
						wings.upgrade1();
						player.addUpgrade(wings);
						break;
					case 0:
						scorekeep.spendMoney(wings.getUpgradePrice());
						wings.upgrade2();
						player.addUpgrade(wings);
						break;
				}
				//System.out.println("upgrade wings");

			}
			
			if(isInside(e.getXOnScreen(), e.getYOnScreen(), 400, 450, 600, 550) && engine.getUpgradePrice() < scorekeep.getMoney()) {
				switch(engine.getLevel()){
					case -1:
						scorekeep.spendMoney(engine.getUpgradePrice());
						engine.upgrade1();
						player.addUpgrade(engine);
						System.out.println("upgrade engine");
	
						break;
					case 0:
						scorekeep.spendMoney(engine.getUpgradePrice());
						engine.upgrade2();
						player.addUpgrade(engine);
						break;
				}
				//System.out.println("upgrade engine");

			}
				
			if(isInside(e.getXOnScreen(), e.getYOnScreen(), 400, 600, 600, 700) && engine.getFuelManager().getUpgradePrice() < scorekeep.getMoney()){
				
				if(engine.getFuelManager().getLevel() < 2) {
					scorekeep.spendMoney(engine.getFuelManager().getUpgradePrice());
					engine.getFuelManager().upgrade();
				}
			}
		}
		System.out.println(e.getXOnScreen() + ":" + e.getYOnScreen());
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
			if(engine.getFuelPerc() > 0) {
				player.setThrust(player.getPossibleThrust());
				engine.getLit();
			}else {
				player.setThrust(0);
				engine.notLit();
			}
			if(stage == 2) {
				stage++;
			}
			break;	
		case 32:
			if(stage == 0 || stage == 1) {
				stage++;
			}else if(stage == 4) {
				stage = 1;
			}
			break;
		case 38:
			if(engine.getFuelPerc() > 0 && !player.onRamp && !player.onGround()) {
				player.setThrust(player.getPossibleThrust());
				engine.getLit();
			}else {
				player.setThrust(0);
				engine.notLit();
			}
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
			engine.notLit();
			break;
		case 32:
		case 38:
			player.setThrust(0);
			engine.notLit();

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
