package projectvania.entity.mob;

import projectvania.graphics.AnimatedSprite;
import projectvania.graphics.Screen;
import projectvania.graphics.Sprite;
import projectvania.graphics.SpriteSheet;
import projectvania.input.Keyboard;

public class Player extends Mob {
	
	private Keyboard input;
	private Sprite sprite;
	private int xa = 0, ya = 0;
	private int jumpingRange = 15;
	
	//animated sprites for player 
	private AnimatedSprite player_walking = new AnimatedSprite(SpriteSheet.player_walking,16,32,4,5);
	private AnimatedSprite player_jumping = new AnimatedSprite(SpriteSheet.player_jumping,16,32,1,5);
	private AnimatedSprite player_climbing = new AnimatedSprite(SpriteSheet.player_climbing,16,32,2,7);
	private AnimatedSprite player_default = new AnimatedSprite(SpriteSheet.player_default,16,32,2,100);
	private AnimatedSprite player_wall_jump = new AnimatedSprite(SpriteSheet.player_wall_jump,16,32,1,100);
	private AnimatedSprite animSprite = null;
	
	
	private int forceRight=0,forceLeft=0,forceSprite=0;
	
	public Player(int x,int y,Keyboard input) {
		this.x = x;
		this.y = y;
		this.input = input;
		height = 32;
		width = 16;
		xOffset = 0;
		yOffset = 15;
		//sprite = Sprite.Player_def;
		animSprite = player_default;
	}
	
	public void update() {
		if(timer>0) timer--;
		if(forceRight>0) forceRight--;
		if(forceLeft>0) forceLeft--;
		if(forceSprite>0) forceSprite--;
		if(jumping >0) jumping--;
		xa=0;
		ya=0;
		//climbing checking
		if(input.up){
			if(climbUp()){
				animSprite = player_climbing;
				animSprite.update();
				if(level.tileClimb(x, y-1 , this) == -1){
					y--;
				}
				return;
			}
		}
		else if(input.down){
			if(climbDown()){
				animSprite = player_climbing;
				animSprite.update();
				return;
			}
		}
		
		//checking walk
		if(level.tileClimb(x, y, this) != -1 && (input.left || input.right)){
			climbing = false;
			walk();
		}
		else walk(false); // i don't know why this part works.
		
		//checking jump
		jump();
		
		//WALL_JUMP
		wallJump();
		
		//regular movement
		movement();
		
		//checking gravity
		gravity();
		
		if(forceSprite>0)
			return;
		if(falling){
			walking = false;
			climbing = false;
		}
		else if(walking){
			animSprite = player_walking;
		}
		else if(climbing && level.tileClimb(x, y - ya, this) != -1){
			animSprite = player_climbing;
			return;
		}
		else {
			animSprite = player_default;
		}
		animSprite.update();
	}

	public void render(Screen screen) {
		int flip = 0;
		sprite = animSprite.getSprite();
		if(dir == Direction.RIGHT) {
			flip = 0;
		}
		if(dir == Direction.LEFT) {
			flip = 1;
		}
		if(forceSprite==0) {
			if(falling && !climbing) {
				sprite = Sprite.Player_air;
			}
			if(jumping>0 && !climbing) {
				sprite = Sprite.Player_jump_up;
			}
		}
		//System.out.printf("walking=%b falling=%b climbing=%b jumping=%d tileCol = %b\n",walking,falling,climbing,jumping,collision(xa,ya));
		screen.renderSprite(x-16,y-16,sprite,true,flip);
	}	
	
	private void movement() {
		if(xa!=0 || ya!=0) {
			move(xa,ya);
			move(xa,ya);
			walking = true;
		}
		else walking = false;
	}

	//Gravity check method
	private void gravity(){
		if(jumping==0 && !climbing) {
			move(0,1);
			move(0,1);
		}
		else if(jumping > 0 && !climbing) {
			move(0,-1);
			move(0,-1);
		}
		else if(level.tileClimb(x,y+1,this)==-1){
			move(0,1);
			move(0,1);
		}
	}
	
	//walking method
	private void walk(){
		if((input.left||forceLeft>0)&&forceRight==0) {
			if(!climbing){
				xa--;
			}
		}
		else if((input.right||forceRight>0)&&forceLeft==0) {
			if(!climbing){
				xa++;
			}
		}
	}
	private void walk(boolean climbing){
		if((input.left||forceLeft>0)&&forceRight==0) {
			if(!climbing){
				xa--;
			}
		}
		else if((input.right||forceRight>0)&&forceLeft==0) {
			if(!climbing){
				xa++;
			}
		}
	}
	
	//jumping method
	
	private void jump(){
		if(input.jump && jumping==0 && !falling && !collision(0,-1)){
			jumping = jumpingRange;
			invokeTimer(17);
			if(climbing){
				climbing = false;
				falling = true;
			}
		}
	}
	
	private void wallJump(){
		if(input.jump && falling && collision(xa, 0) && timer==0) { 
			jumping = jumpingRange;
			invokeTimer(17);
			forceLeft=0;
			forceRight=0;
			if(xa>0) forceLeft=16;
			else forceRight=16;
			animSprite = player_wall_jump;
			forceSprite=15;
		}
		
		
	}
	
	//climb methods
	private boolean climbDown() {
		if(level.tileClimb(x, y+1, this)!=-1){
			climbing = true;
			walking = false;
			falling = false;
			ya++;
			xa = (collision(1,0))? -1:0;
			startClimbing(xa,ya);
			if(!collision(xa,ya))
				return true;
			else{
				//System.out.println((x+xa));
				climbing = false;
				walking = true;
				animSprite = player_walking;
				return false;
			}
		}
		return false;
		
	}
	
	private boolean climbUp(){
		if(level.tileClimb(x, y-1, this)!=-1){
			climbing = true;
			walking = false;
			falling = false;
			ya--;
			startClimbing(xa,ya);
			return true;
		}
		return false;
	}
	
	
	private void startClimbing(int xa, int ya){
		if(ya > 0 ) dir = Direction.DOWN;
		if(ya < 0 ) dir = Direction.UP;
		int temp = climb(xa,ya);
		if(temp != -1){
			if(!collision(-1,0)&&(collision(1,-1)||collision(1,1))) {
				x = (temp<<4)+15;
				xa = -1;
			}
			else {
				x = (temp<<4)+16;
				xa = 0;
			}
			if(!collision(xa,ya)) y += ya ;
		}
	}
	
	private int climb(int xa,int ya){
		return level.tileClimb(x+xa, y+ya, this);
	}
	
}