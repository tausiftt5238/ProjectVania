package projectvania.graphics;

import projectvania.graphics.SpriteSheet;
import projectvania.level.Tile;

public class Screen {
	public int width,height;
	public final int MAP_SIZE = 64;
	public int pixels[]; //public so that it can copied in the Game class
	public int xOffset,yOffset;
	
	
	public Screen(int width,int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width*height];
	}
	
	
	public void clear() {
		for (int i=0; i<pixels.length; i++)
			pixels[i] = 0;
	}
	
	/*
	 * FLIP VALUES:
	 * 0 = normal
	 * 1 = x-invert
	 * 2 = y-invert
	 * 3 = dual invert
	 */
	public void renderSprite(int xp,int yp,Sprite sprite,boolean fixed,int flip) {
		if(fixed) {		//its position on the map is fixed and will not depend on the position of the screen
			xp -= xOffset;
			yp -= yOffset;
		}
		for(int y=0; y<sprite.getHeight();y++) {
				int ya = y + yp;
				int ys = y;
				if(flip==2||flip==3)
					ys = (sprite.getHeight()-y);
				for(int x=0; x<sprite.getWidth();x++) {
					int xa = x + xp;
					int xs = x;
					if(flip==1||flip==3)
						xs= (sprite.getWidth()-1)-x;
					if(xa < (-sprite.getWidth()) ||xa>=width||ya<0||ya>=height) break; //check later
					if(xa < 0) xa = 0;
					int col = sprite.pixels[xs + ys * sprite.getWidth()];
					if(col != 0xffff00ff)
						pixels[xa + ya*width] = col;
				}
		}
	}
	
	public void renderSprite(int xp,int yp,Sprite sprite,boolean fixed) { //Simplified version where flip doesn't matter
		renderSprite(xp,yp,sprite,fixed,0);
	}
	
	public void renderTile(int xp, int yp, Tile tile) {
		xp -= xOffset;
		yp -= yOffset;
		for(int y=0; y<tile.sprite.SIZE; y++) {
			int ya = y+yp;
			for(int x=0; x<tile.sprite.SIZE; x++) {
				int xa = x + xp;
				if(xa<(-tile.sprite.SIZE)||xa>=width||ya<0||ya>=height) break;
				if(xa<0) xa=0;
				int col = tile.sprite.pixels[x + y * tile.sprite.getWidth()];
				if(col != 0xffff00ff)
					pixels[xa + ya*width] = col;
				//pixels[xa + ya*width] = tile.sprite.pixels[x + y*tile.sprite.SIZE];
			}
		}
	}
	
	public void setOffset(int xOffset,int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	public void renderSheet(int xp,int yp, SpriteSheet sheet,boolean fixed){
		if(fixed){
			xp -= xOffset;
			yp -= yOffset;
		}
		for(int y = 0; y < sheet.HEIGHT; y++){
			int ya = y + yp;
			for(int x = 0 ; x < sheet.WIDTH; x++){
				int xa = x + xp;
				if(xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
				pixels[xa + ya * width] = sheet.pixels[x + y * sheet.WIDTH];
			}
		}
	}

}
