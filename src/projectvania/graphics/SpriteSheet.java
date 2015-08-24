package projectvania.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	private String path;
	public final int SIZE,WIDTH,HEIGHT;
	public int pixels[];
	private Sprite sprites[];
	
	//Individual SpriteSheets load here
	public static SpriteSheet tiles = new SpriteSheet("/textures/texture.png",128);
	//public static SpriteSheet player = new SpriteSheet("/textures/KnightSprites.png",256);
	public static SpriteSheet player = new SpriteSheet("/textures/KnightSprites2.png",256);
	public static SpriteSheet KingCherno = new SpriteSheet("/textures/KingCherno.png",96);
	
	//Sub-sheets
	//public static SpriteSheet player_walking = new SpriteSheet(SpriteSheet.player,0,7,4,1,16,32);
	public static SpriteSheet player_walking = new SpriteSheet(SpriteSheet.player,0,0,4,1,16,32);
	public static SpriteSheet player_jumping = new SpriteSheet(SpriteSheet.player,1,1,1,1,16,32);
	public static SpriteSheet player_climbing = new SpriteSheet(SpriteSheet.player,4,0,2,1,16,32);
	public static SpriteSheet Cherno_walking = new SpriteSheet(SpriteSheet.KingCherno,1,0,1,3,32);
	public static SpriteSheet player_attack = new SpriteSheet(SpriteSheet.player,0,2,2,1,32);
	public static SpriteSheet player_default = new SpriteSheet(SpriteSheet.player,0,3,2,1,16,32);
	public static SpriteSheet player_wall_jump = new SpriteSheet(SpriteSheet.player,3,1,1,1,16,32);
	
	public SpriteSheet(String path,int size) {
		this.path = path;
		SIZE = size;
		WIDTH = size;
		HEIGHT = size;
		pixels = new int[SIZE*SIZE];
		load();
	}
	
	//for creating Sub-sheets
	//x,y == starting postion
	//width,height -> number of Sprites horizontally/vertically to be put into the Sub-sheet
	public SpriteSheet(SpriteSheet sheet,int x,int y,int width,int height,int spriteSize) {
		this(sheet, x,y,width,height,spriteSize,spriteSize);
	}
	
	
	public SpriteSheet(SpriteSheet sheet,int x,int y,int width,int height,int spriteWidth,int spriteHeight) {
		int xx = x*spriteWidth;
		int yy = y*spriteHeight;
		//let's take a moment to realize w and h are pixel precision while width and height are tile precision.
		int w = width*spriteWidth;
		int h = height*spriteHeight;
		SIZE = -1; //not important in this case as we are not gonna use sprite.load() from a subsheet
		WIDTH = w;
		HEIGHT = h;
		pixels = new int[w*h];
		for(int y0=0;y0<h;y0++) {
			int yp = yy+y0;
			for(int x0=0;x0<w;x0++) {
				int xp = xx+x0;
				pixels[x0 + y0*w] = sheet.pixels[xp + yp*sheet.WIDTH];
			}
		}
		int frame = 0;
		sprites = new Sprite[width*height];
		for(int ya=0;ya<height;ya++) {
			for(int xa=0;xa<width;xa++) {
				int spritePixels[] = new int[spriteWidth*spriteHeight];
				for(int y0=0;y0<spriteHeight;y0++) {
					for(int x0=0;x0<spriteWidth;x0++) {
						spritePixels[x0+y0*spriteWidth] = pixels[(x0 + xa*spriteWidth) + (y0 + ya*spriteWidth)*WIDTH];
					}
				}
				Sprite sprite = new Sprite(spritePixels,spriteWidth,spriteHeight);
				sprites[frame++] = sprite;
			}
		}
	}

	public Sprite[] getSprites() { return sprites; }
	
	private void load() {
		try {
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
