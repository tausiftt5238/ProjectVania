package projectvania.entity.mob;

import projectvania.entity.Entity;
import projectvania.entity.mob.envHazard.Spike;
import projectvania.graphics.Screen;
import projectvania.graphics.Sprite;

public abstract class Mob extends Entity {
	protected Sprite sprite;
	protected boolean moving = false;
	protected boolean falling = false;
	protected boolean walking = false;
	protected boolean climbing = false;
	protected boolean standing = false; //standing on the eeeeeeeeeeeeeeeeeeeeedge.
	protected int jumping = 0;
	protected Mob collidedMob = null;
	protected int timer = 0;
	
	
	protected enum Direction {
		UP,DOWN,LEFT,RIGHT;
	}
	
	protected Direction dir;
	
	protected enum Collision {
		ALLOW,STOP,DAMAGE,KILL;
	}
	
	protected Collision collisionAction;
	
	public void move(int xa,int ya) {
		if(xa!=0 && ya!=0) {
			move(0,ya);
			move(xa,0);
			return;
		}
		
		if(xa>0) dir = Direction.RIGHT;
		if(xa<0) dir = Direction.LEFT;
		
		falling = false;
		
		mobCollision(xa,ya);
		
		if(collisionAction == Collision.STOP)
			return;
		else if(!collision(xa,ya)) {
			x += xa;
			y += ya;
			if(ya!=0) {
				falling = true;
			}
		}
		
	}
	
	public abstract void update();
	
	
	public boolean collision(int xa,int ya) {
		return level.tileCollision(x+xa, y+ya, this);
	}
	
	public void mobCollision(int xa,int ya) {
		collidedMob = level.mobCollision(x+xa, y+ya, this);
		
		/*
		 * Add actions as follows: (Override as required)
		 * if(collidedMob instanceof [Specific Mob Class])
		 *     collisionAction = Collision.[Appropriate Action];
		 */
		
		if(collidedMob instanceof Spike)
			collisionAction = Collision.STOP;
		if(collidedMob instanceof Dummy)
			collisionAction = Collision.STOP;
		if(collidedMob instanceof Player)
			collisionAction = Collision.STOP;
		if(collidedMob == null)
			collisionAction = Collision.ALLOW;
	}
	
	public void invokeTimer(int n) { timer=n; }
	
	public abstract void render(Screen screen);
	
}
