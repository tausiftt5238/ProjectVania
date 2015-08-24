package projectvania.graphics;

public class AnimatedSprite extends Sprite {
	private int frame = 0;
	private Sprite sprite;
	private int rate= 5, time=0,length=-1; //length == how many frames the animation takes
	
	//Load animations here
	
	
	
	public AnimatedSprite(SpriteSheet sheet,int width,int height,int length,int rate) {
		super(sheet,width,height);
		this.length = length;
		sprite = sheet.getSprites()[0];
		this.rate = rate;
		if(length > sheet.getSprites().length){
			
			System.err.println("Error: Invalid Animation Length");
		}
			
	}
	
	public void update() {
		time++;
		if(time%rate == 0) {
			if(frame >= length-1) frame=0;
			else frame++;
			sprite = sheet.getSprites()[frame];
		}
	}
	
	public Sprite getSprite() { return sprite; }
	
	public void setFrameRate(int frames) {
		rate = frames;
	}

	public void setFrame(int i) {
		if(i > sheet.getSprites().length - 1) {
			System.err.println("Error: Invalid Frame");
			return;
		}
		frame = i;
		sprite = sheet.getSprites()[frame];
	}
	
}
