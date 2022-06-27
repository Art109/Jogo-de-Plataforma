package com.arthur.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.arthur.main.Game;
import com.arthur.world.Camera;
import com.arthur.world.World;

public class Enemy extends Entity {

	private BufferedImage rightEnemy[], leftEnemy[];
	private int frames = 0, maxFrames= 10 , index = 0 , maxIndex = 1;
	
	
	public Enemy(double x, double y, int width, int height, double speed) {
		super(x, y, width, height, speed);
		
		rightEnemy = new BufferedImage[2];
		leftEnemy = new BufferedImage[2];
		
		for(int i = 0 ; i < 2; i++) {
			rightEnemy[i] = Game.spritesheet.getSprite(32, 0 + (i * 16), 16,16);
		}
		for(int i = 0 ; i < 2; i++) {
			leftEnemy[i] = Game.spritesheet.getSprite(48, 0 + (i * 16), 16,16);
		}
		
		right = true;
		
	}
	
	public void tick() {
		
		if(World.isFree((int)x,(int)(y+1)) ){
			y+=1;
		}
		if(right && World.isFree((int)(x + speed), (int)y)){
			x+=speed;
			if(World.isFree((int)(x+16),(int)y+1)) {
				right = false;
				left = true;
			}
		}
		else if(right && !World.isFree((int)(x + speed),(int)y)) {
			right = false;
			left = true;
		}
		
		else if(left && World.isFree((int)(x - speed), (int)y)){
			x-=speed;
			if(World.isFree((int)(x-16),(int)y+1)) {
				right = true;
				left = false;
			}
		}
		else if(left && !World.isFree((int)(x-speed),(int)y)) {
			right = true;
			left = false;
		}
		
		frames++;
		if(frames == maxFrames) {
			frames = 0;
			index++;
			if(index > maxIndex) {
				index = 0;
			}
		}
	}
	
	public void render(Graphics g) {
		if(right) {
			g.drawImage(rightEnemy[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
		}
		else {
			g.drawImage(leftEnemy[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
		}
	}
	

}
