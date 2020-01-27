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
	
	private final boolean debug = false;
	
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
	private Image fuelgauge;
	
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
		fuelbar.setValue(engine.getFuelPerc()*100);
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
		
		if(debug) {
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
		}

		Graphics2D g2d = (Graphics2D)(g);
		g.drawImage(fuelgauge, 890, 570, null);
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
		
		fuelbar = new StatusBar(913, 593, 132, 58, 0, Color.yellow, true, 1, "", false, 100, 0, 0, false);
		fuelgauge = getImage("fuelgauge.png");
		
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
		Font font1 = new Font("MV Boli", Font.PLAIN, 25);
		Font font2 = new Font("MV Boli", Font.PLAIN, 45);
		Font font3 = new Font("Courier New", Font.PLAIN, 50);


		Graphics2D g2 = (Graphics2D) g;
		tx = AffineTransform.getTranslateInstance(0, 0);
		tx.scale(1,1);		
		
		g2.drawImage(getImage("newUpgradeBackground.png"), tx, null);
		//tx.scale(0.6, 0.6);
		//g.setFont(font1);

		//background
		//g.setColor(Color.GREEN);
		//g.fillRect(0, 0, screen_width, screen_height);
		
		//rectangles
		g.setColor(Color.GREEN);
		//g.fillRect(300, 50, 400, 100);
		
		//wings
		g2.drawImage(getImage("upgradeButton.png"), 270, 410, null);
		//engine
		//g.fillRect(400, 450, 200, 100);
		g2.drawImage(getImage("upgradeButton.png"), 270, 548, null);
		//fuel
		//g.fillRect(400, 600, 200, 100);
		g2.drawImage(getImage("upgradeButton.png"), 270, 688, null);
		
		g.setFont(font3);
		g.setColor(Color.black);
		g.drawString("$"+ scorekeep.getMoney(), 30, 140);
		g.setFont(font2);
		g.drawString("Upgrades", 40, 325);
		
		g.drawString("Wings:", 40, 400); 
		g.setFont(font1);
		switch(wings.getLevel()) {
			case -1:
				g.drawString("Level 1 $"+wings.getUpgradePrice(), 270, 400);	
				
				tx = AffineTransform.getTranslateInstance(40, 420);
				tx.scale(0.1, 0.1);
				g2.drawImage(getImage(wings.getWingsUpgrade(0)), tx, null);
				
				/*tx = AffineTransform.getTranslateInstance(610, 315);
				tx.scale(0.1, 0.1);
				g2.drawImage(getImage(wings.getWingsUpgrade(0)), tx, null);*/
				break;
			case 0:
				g.drawString("Level 2 $"+wings.getUpgradePrice(), 270, 400);
				
				tx = AffineTransform.getTranslateInstance(60, 420);
				tx.scale(0.5, 0.5);
				g2.drawImage(getImage(wings.getWingsUpgrade(1)), tx, null);
				
				/*tx = AffineTransform.getTranslateInstance(630, 315);
				tx.scale(0.5, 0.5);
				g2.drawImage(getImage(wings.getWingsUpgrade(1)), tx, null);*/
				break;
			case 1:
				g.drawString("MAXED OUT!", 270, 400);
				break;
		}
		
		
		
		g.setFont(font2);
		g.drawString("Engine:", 40, 540);
		g.setFont(font1);
		switch(engine.getLevel()) {
			case -1:
				g.drawString("Level 1 $"+engine.getUpgradePrice(), 270, 540);
				
				tx = AffineTransform.getTranslateInstance(60, 535);
				tx.scale(0.2, 0.2);
				g2.drawImage(getImage(engine.getEngineUpgrade(0)), tx, null);
				
				/*tx = AffineTransform.getTranslateInstance(620, 450);
				tx.scale(0.2, 0.2);
				g2.drawImage(getImage(engine.getEngineUpgrade(0)), tx, null);*/
				
				break;
			case 0:
				g.drawString("Level 2 $"+engine.getUpgradePrice(), 270, 540);
				
				tx = AffineTransform.getTranslateInstance(140, 555);
				tx.rotate(Math.PI/2);
				tx.scale(0.15, 0.15);
				g2.drawImage(getImage(engine.getEngineUpgrade(1)), tx, null);
				
				/*x = AffineTransform.getTranslateInstance(640, 450);
				tx.scale(0.15, 0.15);
				g2.drawImage(getImage(engine.getEngineUpgrade(1)), tx, null);*/
				break;
			case 1:
				g.drawString("Level 3 $"+engine.getUpgradePrice(), 270, 540);
				tx = AffineTransform.getTranslateInstance(200, 510);
				tx.rotate(Math.PI/2);
				tx.scale(0.15, 0.15);
				g2.drawImage(getImage(engine.getEngineUpgrade(2)), tx, null);
				
				/*tx = AffineTransform.getTranslateInstance(610, 450);
				tx.scale(0.15, 0.15);
				g2.drawImage(getImage(engine.getEngineUpgrade(2)), tx, null);*/
				break;
			case 2:
				g.drawString("MAXED OUT!", 270, 540);
				break;
		}
		
		g.setFont(font2);
		g.drawString("Fuel Tank:", 40, 680);
		g.setFont(font1);
		switch(engine.getFuelManager().getLevel()) {
			case 0:
				g.drawString("Level 1 $"+engine.getFuelManager().getUpgradePrice(), 270, 680);		
				break;
			case 1:
				g.drawString("Level 2 $"+engine.getFuelManager().getUpgradePrice(), 270, 680);
				break;
			case 2:
				g.drawString("MAXED OUT!", 270, 680);
				break;
		}
		//g.setFont(font2);
		//g.drawString("Press Space to Start", 290, 750);
	}
	
	private String getEngineUpgrade(Object imgsStrings, int i) {
		// TODO Auto-generated method stub
		return null;
	}

	private Object getImgsStrings() {
		// TODO Auto-generated method stub
		return null;
	}

	public void mainMenu(Graphics g) {
		Font font1 = new Font("Book Antiqua", Font.PLAIN, 100);
		Font font2 = new Font("MV Boli", Font.PLAIN, 35);
	
		Graphics2D g2 = (Graphics2D) g;
		tx = AffineTransform.getTranslateInstance(0, 0);
		tx.scale(1,1);
		g2.drawImage(getImage("tableback.jpg"), tx, null);
		
		g.setColor(Color.black);
		g.setFont(font1);
		
		//g.drawString("Yaris Flight", 230, 200);
		g2.drawImage(getImage("logo.png"), 110, 150, null);
		
		g.setFont(font2);
		
		tx = AffineTransform.getTranslateInstance(150, 520);
		tx.scale(1.3,1);
		g2.drawImage(getImage("notebook.png"), tx, null);
		
		g.drawString("Press the spacebar to start", 250, 600);
		
		Font font3 = new Font("MV Boli", Font.PLAIN, 28);
		g.setFont(font3);
		g.drawString("Use L/R to turn, space to thrust", 250, 700);
		
	}
	
	
	
	public void endScreen(Graphics g) {
		Font font1 = new Font("Book Antiqua", Font.PLAIN, 25);
		Font font2 = new Font("Book Antiqua", Font.PLAIN, 20);
		Font font3 = new Font("MV Boli", Font.PLAIN, 30);

		Graphics2D g2 = (Graphics2D) g;
		
		g2.drawImage(getImage("notebook.png"), 220, 50, null);
		
		g.setFont(font3);
		g.setColor(Color.black);
		g.drawString("You crashed the Yaris!", 300, 150);
		
		
		if(scorekeep.getNewDistRecord()) {
			g.setColor(Color.GREEN);
			g.drawString("New Record!!", 290, 250);
			g.setColor(Color.BLACK);
		}
		g.drawString("Distance: " + scorekeep.getMaxDist()*0.055 + "m", 290, 213);
		
		
		if(scorekeep.getNewAltRecord()) {
			g.setColor(Color.GREEN);
			g.drawString("New Record!!", 290, 330);
			g.setColor(Color.BLACK);
		}
		g.drawString("Max Altitude: " + scorekeep.getMaxAlt()*0.055 + "m", 290, 300);
			
		
		if(scorekeep.getNewSpeedRecord()) {
			g.setColor(Color.GREEN);
			g.drawString("New Record!!", 290, 420);
			g.setColor(Color.BLACK);
		}
		double maxspeed = scorekeep.getMaxSpeed()*0.055*60*1000/3600;
		int temp = (int) (maxspeed * 1000);
		maxspeed = temp / 1000.0;
		g.drawString("Top Speed: " + maxspeed + " kmh", 290, 388);
		g.drawString("Money Earned: $" +scorekeep.getMoneyEarned(), 290, 470);
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
			if(isInside(e.getX(), e.getY(), 270, 443, 430, 500) && wings.getUpgradePrice() < scorekeep.getMoney()) {
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
			
			if(isInside(e.getX(), e.getY(), 270, 581, 430, 636) && engine.getUpgradePrice() < scorekeep.getMoney()) {
				switch(engine.getLevel()){
					case -1:
						scorekeep.spendMoney(engine.getUpgradePrice());
						engine.upgrade1();
						player.addUpgrade(engine);
						if(debug) {System.out.println("upgrade engine");}
						break;
					case 0:
						scorekeep.spendMoney(engine.getUpgradePrice());
						engine.upgrade2();
						player.addUpgrade(engine);
						if(debug) {System.out.println("upgrade engine");}
						break;
					case 1:
						scorekeep.spendMoney(engine.getUpgradePrice());
						engine.upgrade4();
						player.addUpgrade(engine);
						if(debug) {System.out.println("upgrade engine");}
						break;
				}
				//System.out.println("upgrade engine");

			}
				
			if(isInside(e.getX(), e.getY(), 270, 721, 430, 780) && engine.getFuelManager().getUpgradePrice() < scorekeep.getMoney()){
				
				if(engine.getFuelManager().getLevel() < 2) {
					scorekeep.spendMoney(engine.getFuelManager().getUpgradePrice());
					engine.getFuelManager().upgrade();
				}
			}
		}
		if(debug) {System.out.println(e.getX() + ":" + e.getY());}
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
			
			break;	
		case 32:
			switch(stage) { 
			case 0:
				stage++;
				break;
			case 1:
				stage++;
				break;
			case 3:
				if(engine.getFuelPerc() > 0) {
					player.setThrust(player.getPossibleThrust());
					engine.getLit();
				}else {
					player.setThrust(0);
					engine.notLit();
				}
				break;
			case 4:
				stage = 1;
				break;
			}
			
			break;
		case 38:
			break;
		default:
			if(debug) {System.out.println(key);}
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
			if(debug) {System.out.println(key);}
		}
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
