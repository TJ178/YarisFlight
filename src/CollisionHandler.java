
public class CollisionHandler {
	private Player player;
	private Ground ground;
	
	public CollisionHandler(Player p, Ground g){
		player = p;
		ground = g;
	}
	
	public boolean inGround(){
		return ground.collide(player.getBounds());
		
	}
	
}
