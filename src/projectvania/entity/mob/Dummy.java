package projectvania.entity.mob;

import java.util.Random;

import projectvania.graphics.AnimatedSprite;
import projectvania.graphics.Screen;
import projectvania.graphics.Sprite;
import projectvania.graphics.SpriteSheet;

public class Dummy extends Mob {
	
	
	private AnimatedSprite cherno_walking = new AnimatedSprite(SpriteSheet.Cherno_walking,32,32,3,5);
	
	private AnimatedSprite animSprite = null;
	
	private int t=0;
	
	public Dummy (int x, int y) {
		this.x = x<<4;
		this.y = y<<4;
		xOffset = -6;
		yOffset = 15;
		height = 32;
		width = 16;
		sprite = SpriteSheet.Cherno_walking.getSprites()[0];
		animSprite = cherno_walking;
	}
	
	public void update() {
		
		int xa=0,ya=0;
		t++;
		t%=180;
		if(t<90)
			xa++;
		else xa--;
		if(walking) animSprite.update();
		else animSprite.setFrame(0);
		
		if(xa < 0) {
			animSprite = cherno_walking;
			xa--;
		}
		else if(xa>0) {
			animSprite = cherno_walking;
			xa++;
		}
		
		
		if(xa!=0 || ya!=0) {
			move(xa,ya);
			walking = true;
		}
		else walking = false;

		move(0,1);
	}

	public void render(Screen screen) {
		int flip = 0;
		sprite = Sprite.Cherno_def;
		if(dir == Direction.RIGHT) {
			flip = 0;
		}
		if(dir == Direction.LEFT) {
			flip = 1;
		}
		sprite = animSprite.getSprite();
		screen.renderSprite(x-16,y-16,sprite,true,flip);
	}
	
	public boolean collision(int xa,int ya) {
		return level.tileCollision(x+xa, y+ya,this);
	}
	
}
