package projectvania.level;

import projectvania.graphics.Screen;
import projectvania.graphics.Sprite;

public class Tile {
	
	public int x,y;
	public Sprite sprite;
	private boolean solid;
	private boolean climbable;
	
	//Put in Tiles here
	public static Tile voidTile = new Tile(Sprite.voidSprite,true);
	public static Tile StoneWall1 = new Tile(Sprite.stoneWall1Sprite,true);
	public static Tile BrickWall1 = new Tile(Sprite.brickWall1Sprite,true);
	public static Tile BrickWall2 = new Tile(Sprite.brickWall2Sprite,true);
	public static Tile Ground2 = new Tile(Sprite.ground2Sprite,true);
	public static Tile ModernArt = new Tile(Sprite.modernArtSprite,true);
	public static Tile NormalWall = new Tile(Sprite.normalWallSprite,false);
	public static Tile BloodStainWall1 = new Tile(Sprite.BloodStainWallSprite1,false);
	public static Tile BloodStainWall2 = new Tile(Sprite.BloodStainWallSprite2,false);
	public static Tile BloodStainWall3 = new Tile(Sprite.BloodStainWallSprite3,false);
	public static Tile BloodStainWall4 = new Tile(Sprite.BloodStainWallSprite4,false);
	public static Tile Spike = new Tile(Sprite.SpikeSprite,true);
	public static Tile Ladder = new Tile(Sprite.LadderSprite,false,true);
	
	
	//Put in color tags here
	public static int col_StoneWall1 = 0xffb9b9b9;
	public static int col_BrickWall1 = 0xff390007;
	public static int col_BrickWall2 = 0xff7f3300;
	public static int col_ModernArt = 0xff636363;
	public static int col_Ground2 = 0xff593300;
	public static int col_NormalWall = 0xffd5d5d5;
	public static int col_BloodStainWall1 = 0xffc10d0d;
	public static int col_BloodStainWall2 = 0xffe70c0c;
	public static int col_BloodStainWall3 = 0xff890d0d;
	public static int col_BloodStainWall4 = 0xffab0909;
	public static int col_Ladder = 0xff710707;
	//public static int col_Spike = 0xff474447;
	
	
	public Tile(Sprite sprite,boolean solid) {
		this.sprite = sprite;
		this.solid = solid;
		climbable = false;
	}
	public Tile(Sprite sprite,boolean solid, boolean climbable){
		this.sprite = sprite;
		this.solid = solid;
		this.climbable = climbable;
	}
	
	public void render(int x,int y, Screen screen) {
		screen.renderTile(x<<4,y<<4,this);
	}
	
	public boolean solid() {
		return solid;
	}
	public boolean climb(){
		return climbable;
	}
}
