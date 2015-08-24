package projectvania.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import projectvania.entity.mob.Dummy;
import projectvania.entity.mob.envHazard.Spike;

public class Level1 extends Level{
	public Level1(String path) {
		super(path);
	}
	
	protected void loadLevel(String path) {
		try {
			BufferedImage image = ImageIO.read(SpawnLevel.class.getResource(path));
			int w = width = image.getWidth();
			int h = height = image.getHeight();
			tiles = new int[w*h];
			image.getRGB(0, 0, w, h, tiles, 0, w);
		} catch(IOException e) {
			System.out.println("Exception! Could not load level file!");
		}
	}
	
	protected void generateLevel() {
		add(new Spike(5,28));
		//add(new Dummy(16,14));
		//add(new Dummy(6,14));
		//add(new Dummy(12,14));
	}
	
}
