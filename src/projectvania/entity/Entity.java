package projectvania.entity;

import java.util.Random;

import projectvania.graphics.Screen;
import projectvania.level.Level;

public class Entity {
	public int x,y;
	private boolean removed = false;
	protected Level level;
	protected final Random random = new Random();
	
	public int height,width,xOffset,yOffset;
	
	public void update() {
	}
	
	public void render(Screen screen) {
	}
	
	public void remove() {
		//Removed from level
		removed = true;
	}
	
	public boolean isRemoved() {
		return removed;
	}
	
	public void init(Level level) {
		this.level = level;
	}
	
}
