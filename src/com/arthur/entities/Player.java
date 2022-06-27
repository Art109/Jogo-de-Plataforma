package com.arthur.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.arthur.graficos.Spritesheet;
import com.arthur.main.Game;
import com.arthur.world.Camera;
import com.arthur.world.World;




public class Player extends Entity{
	
	
	private int frames = 0, maxFrames= 10 , index = 0 , maxIndex = 2;
	
		
	public BufferedImage[] rightPlayer,leftPlayer,upPlayer;

	public boolean jump = false;
	public boolean isJumping = false;
	public int jumpheight = 42;
	public int jumpFrames = 0;
	private static Spritesheet spritesheet;
	private int dir = 1, right_dir = 1,left_dir = -1;
	private boolean moved = false;
	
	public static double vida = 100;
		

	public Player(int x, int y, int width, int height,double speed) {
		super(x, y, width, height,speed);
		spritesheet = new Spritesheet("/player_spritesheet.png");
		rightPlayer = new BufferedImage[3];
		leftPlayer = new BufferedImage[3];
		upPlayer = new BufferedImage[1];
		
		for(int i = 0 ; i < 3 ; i++) {
			rightPlayer[i] = spritesheet.getSprite(0,0 + (i * 16), 16, 16);
		}
		for(int i = 0 ; i < 3 ; i++) {
			leftPlayer[i] = spritesheet.getSprite(16,0 + (i * 16), 16, 16);
		}
	}
	
	public void tick(){
		
		moved = false;
		if(World.isFree((int)x,(int)(y+gravidade)) && isJumping == false){
			y+=2;
			
			for(int i = 0 ; i < Game.entities.size(); i++) {
				Entity e = Game.entities.get(i);
				if(e instanceof Enemy) {
					if(Entity.isColidding(this,e)) {
						isJumping = true;
						jumpheight = 32;
						
						Game.entities.remove(i);
						break;
					}
				}
				else {
					jumpheight = 42;
				}
			}
			
		}
		if(right && World.isFree((int)(x + speed),(int)y)){
			x+=2;
			dir = right_dir;
			left = false;
			moved = true;
			
		}
		if(left && World.isFree((int)(x - speed),(int)y)){
			x-=2;
			dir = left_dir;
			right = false;
			moved = true;
		}
		
		if(jump) {
			if(!World.isFree(this.getX(),this.getY() + 1)) {
				isJumping = true;
			}
			else {
				jump = false;
			}
		}
		
		if(isJumping) {
			if(World.isFree(this.getX(),this.getY() - 2)) {
				y-=2;
				jumpFrames+=2;
				if(jumpFrames == jumpheight) {
					isJumping = false;
					jump = false;
					jumpFrames = 0;
					
				}
			}
			else {
				isJumping = false;
				jump = false;
				jumpFrames = 0;
			}
		}
		//Verifica Danos
		for(int i = 0 ; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Enemy) {
				if(Entity.isColidding(this,e)) {
					if(Entity.rand.nextInt(100) < 10){
						vida--;
					}
				}
			}
		}

		frames++;
		if(frames == maxFrames) {
			frames = 0;
			index++;
			if(index > maxIndex) {
				index = 0;
			}
		}
		

		Camera.x = Camera.clamp((int)x - Game.WIDTH/2, 0, World.WIDTH * 16 - Game.WIDTH);
		Camera.y = Camera.clamp((int)y - Game.HEIGHT/2, 0, World.HEIGHT * 16 - Game.HEIGHT);
	}
	
	
	
	
	
	public void render(Graphics g) {
		if(moved) {
			if(dir == right_dir) {
				g.drawImage(rightPlayer[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
			}
			else if(dir == left_dir){
				g.drawImage(leftPlayer[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
			}
		}
		else  {
			if(dir == right_dir) {
				g.drawImage(rightPlayer[0],this.getX() - Camera.x,this.getY() - Camera.y,null);
			}
			else if(dir == left_dir){
				g.drawImage(leftPlayer[0],this.getX() - Camera.x,this.getY() - Camera.y,null);
			}
		}

	}

	


}
