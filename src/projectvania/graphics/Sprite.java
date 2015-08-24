package projectvania.graphics;

public class Sprite {
	
	public final int SIZE;
	private int x,y,width,height; //basically, co-ordinates of the sprite
	public int pixels[];
	public SpriteSheet sheet;
	
	//Sprites are static, since only one version of them will ever be required
	
	//Environment Sprites here
	public static Sprite voidSprite = new Sprite(16, 0x000000);
	public static Sprite stoneWall1Sprite = new Sprite(16,0,0,SpriteSheet.tiles);
	public static Sprite modernArtSprite = new Sprite(16,1,0,SpriteSheet.tiles);
	public static Sprite ground1Sprite = new Sprite(16,0,1,SpriteSheet.tiles);
	public static Sprite ground2Sprite = new Sprite(16,1,1,SpriteSheet.tiles);
	public static Sprite ground3Sprite = new Sprite(16,2,1,SpriteSheet.tiles);
	public static Sprite vines1Sprite = new Sprite(16,0,2,SpriteSheet.tiles);
	public static Sprite brickWall1Sprite = new Sprite(16,0,3,SpriteSheet.tiles);
	public static Sprite brickWall2Sprite = new Sprite(16,1,3,SpriteSheet.tiles);
	public static Sprite normalWallSprite = new Sprite(16,2,0,SpriteSheet.tiles);
	public static Sprite BloodStainWallSprite1 = new Sprite(16,3,0,SpriteSheet.tiles);
	public static Sprite BloodStainWallSprite2 = new Sprite(16,4,0,SpriteSheet.tiles);
	public static Sprite BloodStainWallSprite3 = new Sprite(16,5,0,SpriteSheet.tiles);
	public static Sprite BloodStainWallSprite4 = new Sprite(16,6,0,SpriteSheet.tiles);
	public static Sprite SpikeSprite = new Sprite(16,0,4,SpriteSheet.tiles);
	public static Sprite LadderSprite = new Sprite(16,0,5,SpriteSheet.tiles);
	
	//Player Sprites here
	//sayon's
	//public static Sprite Player_def = new Sprite(16,32,0,7,SpriteSheet.player);
	//public static Sprite Player_air = new Sprite(16,32,6,7,SpriteSheet.player);
	//mine
	public static Sprite Player_def = new Sprite(16,32,0,0,SpriteSheet.player);
	public static Sprite Player_air = new Sprite(16,32,2,1,SpriteSheet.player);
	
	//jump animation here
	public static Sprite Player_jump_up = new Sprite(16,32,1,1,SpriteSheet.player);
	
	//NPC Sprites here
	public static Sprite Cherno_def = new Sprite(32,1,0,SpriteSheet.Cherno_walking);
	
	
	//Projectile Sprites here
	
	
	public Sprite(int width,int height,int color) {
		SIZE=-1;
		this.width = width;
		this.height = height;
		pixels = new int[width*height];
		setColor(color);
	}
	
	
	public Sprite(int size,int color) {
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[SIZE*SIZE];
		setColor(color);
	}
	
	
	public Sprite(int size,int x,int y, SpriteSheet sheet) { //This version used for reading from SpriteSheet
		SIZE=size;
		this.width = SIZE;
		this.height = SIZE;
		pixels = new int [SIZE*SIZE];
		this.x = x*SIZE;
		this.y = y*SIZE;					//Position on SpriteSheet
		this.sheet = sheet;
		load();
	}
	
	//new
	public Sprite(int width,int height,int x,int y,SpriteSheet sheet) { //Used for reading non-square Sprites
		SIZE = (width==height)? width : -1;
		this.width = width;
		this.height = height;
		pixels = new int [width*height];
		this.x = x*width;
		this.y = y*height;					//Position on SpriteSheet
		this.sheet = sheet;
		load();
	}
	
	
	protected Sprite(SpriteSheet sheet,int width,int height) { //use to initialize these private variables for AnimatedSprite
		SIZE = (width==height)? width : -1;
		this.width = width;
		this.height = height;
		this.sheet = sheet;
	}
	
	
	public Sprite(int pixels[],int width,int height) {
		SIZE = (width==height)? width : -1;
		this.width = width;
		this.height = height;
		this.pixels = pixels;
	}
	
	
	private void setColor(int color) {
		for(int i=0; i<width*height; i++)
			pixels[i]=color;
	}
	
	
	private void load() {
		for(int j=0;j<height;j++)
			for(int i=0;i<width;i++)
				pixels[i + (j*width)] = sheet.pixels[(i+x) + ((j+y)*sheet.SIZE)];
	}
	
	
	public int getWidth() { return width; }
	
	
	public int getHeight() { return height; }
	
}
