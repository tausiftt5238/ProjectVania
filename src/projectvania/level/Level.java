package projectvania.level;

import java.util.ArrayList;
import java.util.List;

import projectvania.entity.Entity;
import projectvania.entity.mob.Mob;
import projectvania.graphics.Screen;

public class Level {
	protected int width,height;
	protected int tileInt[];	//specify an integer that will denote what type of tile it is
	protected int tiles[];
	
	private List<Entity> entities = new ArrayList<Entity>();
	private List<Mob> mobs = new ArrayList<Mob>();
	
	//public static Level test = new SpawnLevel("/levels/testLevel.png");
	public static Level test = new SpawnLevel("/levels/testLevel.png");
	/*public Level(int width,int height) {
		this.width = width;
		this.height = height;
		tileInt = new int[width*height];
		generateLevel();
	}*/
	
	public Level(String path) {
		loadLevel(path);
		generateLevel();
	}
	
	protected void generateLevel() {
		
	}
	
	protected void loadLevel(String path) {
		
	}
	
	public void add(Entity e) {
		e.init(this);
		
		if(e instanceof Mob)
			mobs.add((Mob)e);		//Yes, type-casting actually works this way :v
		else
			entities.add(e);
	}
	
	public void update() {
		for(int i=0; i<entities.size(); i++)
			entities.get(i).update();
		for(int i=0; i<mobs.size(); i++)
			mobs.get(i).update();
		remove();
	}
	
	public void remove() {
		for(int i=0; i<entities.size(); i++)
			if(entities.get(i).isRemoved())
				entities.remove(i);
		for(int i=0; i<mobs.size(); i++)
			if(mobs.get(i).isRemoved())
				mobs.remove(i);
		
	}
	
	public void time() {
		
	}
	
	public boolean tileCollision(int x,int y,int size,int xOffset,int yOffset) { //if collision, true. this version used for projectiles and particles
		//maybe using this version for all purposes would be a good idea. the other one seems redundant
		//x,y == next position of entity
		//change the offsets for better positioning of the collisions (experiment with values for better idea)
		boolean solid = false;
		for(int c=0;c<4;c++) {
			int xt = (x - (c%2)*size - xOffset)>>4;
			int yt = (y - (c/2)*size + yOffset)>>4;
			if(getTile(xt,yt).solid()) solid = true;
		}
		return solid;
	}
	
	public boolean tileCollision(int x,int y,Entity itself) { //if collision, true. this version is more general purpose, as it works with height and width
		//x,y == next position of entity
		//change the offsets for better positioning of the collisions (experiment with values for better idea)
		boolean solid = false;
		for(int c=0;c<4;c++) {
			int xt = (x - (c%2)*itself.width - itself.xOffset)>>4;
			int yt = (y - (c/2)*itself.height + itself.yOffset)>>4;
			if(getTile(xt,yt).solid()) solid = true;
		}
		return solid;
	}
	
	public int tileClimb(int x,int y,Entity itself) { //if collision, true. this version is more general purpose, as it works with height and width
		//x,y == next position of entity
		//change the offsets for better positioning of the collisions (experiment with values for better idea)
		
		//boolean climbable = false;
		for(int c=0;c<4;c++) {
			int xt = (x - (c%2)*itself.width - itself.xOffset)>>4;
			int yt = (y - (c/2)*itself.height + itself.yOffset)>>4;
			if(getTile(xt,yt).climb()) /*climbable = true;*/ return xt;
		}
		//if(getTile(x>>4,y>>4).climb()) climbable = true;
		return -1;
	}
	
	public Mob mobCollision(int x,int y, Entity itself) { //returns what type of Mob collision occurred with (for appropriate action afterwards) or null if none
		boolean noOverlap = true;				//it is easier to check the lack of overlap than to check if overlap occurred
		Mob current = null;
		for(int i=0; i<mobs.size(); i++) { //needs to check with all enemies on the level
			if(mobs.get(i)==itself)
				continue;
			current = mobs.get(i);
			int left1 = (x - itself.width - itself.xOffset);
			int right1 = (x - itself.xOffset);
			int top1 = (y - itself.height + itself.yOffset);
			int bottom1 = (y + itself.yOffset);

			int left2 = (current.x - current.width - current.xOffset);
			int right2 = (current.x - current.xOffset);
			int top2 = (current.y - current.height + current.yOffset);
			int bottom2 = (current.y + current.yOffset);
			
			noOverlap = (left1 >= right2) || (right1 <= left2) || (bottom1 <= top2) || (top1 >= bottom2);
			if(!noOverlap)
				return current;
		}
		
		return null;
	}
	
	public void render(int xScroll, int yScroll, Screen screen) {
		//Get Screen displacement from the upper-right corner of the Map, ie, the portion that contains the player
		screen.setOffset(xScroll, yScroll);
		//Corner Pins into tile precision
		int x0 = xScroll >> 4;
		int x1 = (xScroll + screen.width + 16) >> 4;
		int y0 = yScroll >> 4;
		int y1 = (yScroll + screen.height +16) >> 4;
		//16 is added so that it draws the tiles at the far margins of the screen as well, in case a tile is ever partially on-screen
		
		for(int y=y0; y<y1; y++) {
			for(int x=x0; x<x1;x++) {
				getTile(x,y).render(x,y,screen); //Just for this line we put a render method in Tile that just ends up calling screen.renderTile() anyways
			}
		}
		
		for(int i=0; i<entities.size(); i++)
			entities.get(i).render(screen);
		for(int i=0; i<mobs.size(); i++)
			mobs.get(i).render(screen);
		
	}
	
	public Tile getTile(int x,int y) {		//checks the integer array containing the level design and returns the tile corresponding to its color tag
		if(x<0 || x>=width || y<0 || y>=height) return Tile.voidTile;
		if(tiles[x+y*width]==Tile.col_StoneWall1) return Tile.StoneWall1;
		if(tiles[x+y*width]==Tile.col_BrickWall1) return Tile.BrickWall1;
		if(tiles[x+y*width]==Tile.col_BrickWall2) return Tile.BrickWall2;
		if(tiles[x+y*width]==Tile.col_Ground2) return Tile.Ground2;
		if(tiles[x+y*width]==Tile.col_ModernArt) return Tile.ModernArt;
		if(tiles[x+y*width]==Tile.col_NormalWall) return Tile.NormalWall;
		if(tiles[x+y*width]==Tile.col_BloodStainWall1) return Tile.BloodStainWall1;
		if(tiles[x+y*width]==Tile.col_BloodStainWall2) return Tile.BloodStainWall2;
		if(tiles[x+y*width]==Tile.col_BloodStainWall3) return Tile.BloodStainWall3;
		if(tiles[x+y*width]==Tile.col_BloodStainWall4) return Tile.BloodStainWall4;
		if(tiles[x + y*width] == Tile.col_Ladder) return Tile.Ladder;
		//if(tiles[x+y*width]==Tile.col_Spike) return Tile.Spike;
		return Tile.voidTile;
	}
	
	
}
