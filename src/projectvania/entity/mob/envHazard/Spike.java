package projectvania.entity.mob.envHazard;

import projectvania.entity.mob.Mob;
import projectvania.graphics.Screen;
import projectvania.graphics.Sprite;

public class Spike extends Mob{
	public Spike(int x,int y){
		this.x = x<<4;
		this.y = y<<4;
		width = 16;
		height = 0;
		xOffset = -16;
		yOffset = 3;
		this.sprite = Sprite.SpikeSprite;
	}
	
	@Override
	public void update() {
		
	}

	@Override
	public void render(Screen screen) {
		screen.renderSprite(x,y,sprite,true);
	}
	
}
