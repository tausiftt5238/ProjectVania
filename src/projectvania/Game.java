package projectvania;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import projectvania.entity.mob.Player;
import projectvania.graphics.Screen;
import projectvania.graphics.Sprite;
import projectvania.graphics.SpriteSheet;
import projectvania.input.Keyboard;
import projectvania.input.Mouse;
import projectvania.level.Level;
import projectvania.level.TileCoordinate;

public class Game extends Canvas implements Runnable {
	
	private static final long serialVersionUID = 1L;
	
	private static final int WIDTH = 300;
	private static final int HEIGHT = WIDTH/16*9;
	private static final int SCALE = 3;
	public static final String NAME = "Game";
	
	private Thread thread;
	private JFrame frame;
	private Screen screen;
	private Keyboard key;
	private Mouse mouse;
	private Level level;
	private TileCoordinate spawn_coords = new TileCoordinate(4,28);
	private Player knight;
	
	public boolean running = false;
	public int tickCount = 0;
	
	private BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	
	public Game() {
		frame = new JFrame(NAME);
		screen = new Screen(WIDTH,HEIGHT);
		key = new Keyboard();
		mouse = new Mouse();
		level = Level.test;
		knight = new Player(spawn_coords.x(),spawn_coords.y(),key);
		level.add(knight);
		
		setMinimumSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE)); //to be finalized
		//setMaximumSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE)); //t.b.f
		setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE)); //t.b.f
		
		super.addKeyListener(key);
		super.addMouseListener(mouse);
		super.addMouseMotionListener(mouse);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(this, BorderLayout.CENTER);
		frame.pack();
		
		//frame.setResizable(false);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public synchronized void start() {
		running = true;
		thread = new Thread(this, "MainGameThread");
		thread.start();
	}
	
	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0/60.0;
		double delta = 0;
		int frames = 0;
		int ticks = 0;
		super.requestFocus(); //puts the canvas into focus (avoids problem with listeners not working at times)
		
		while(running) {
			long now = System.nanoTime();
			delta += (now-lastTime) / ns; //means the value of delta will be 1 or higher 60times a second
			lastTime = now;
			while(delta>=1) { //only call update when the difference of time is 1/60th of a second
				update();
				ticks++;
				delta--;
			}
			render(); //renders on every tick however
			frames++;
			if(System.currentTimeMillis()-timer>1000) { //adds a second to the timer so that the checking difference doesn't get bigger each time
				timer += 1000;
				frame.setTitle(NAME + " | " + ticks + "ticks, " + frames + "frames");
				ticks=0;
				frames=0;
			}
		}
	}

	
	public void update() {
		key.update();
		//knight.update();
		level.update();
	}
	
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		screen.clear(); //Clearing screen before starting to render
		int xScroll = knight.x - screen.width/2;
		int yScroll = knight.y - 100;
		level.render(xScroll, yScroll, screen);
		//knight.render(screen);
		//screen.renderSheet(40, 40, SpriteSheet.player_jumping, false);
		//screen.renderSprite(50, 50, Sprite.Player_def, false);
		//screen.renderSheet(40, 40, SpriteSheet.player_default, false);
		for(int i=0; i<pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		
		g.dispose();
		bs.show();
	}

	public static void main(String args[]) {
		new Game().start();
	}
	
}
