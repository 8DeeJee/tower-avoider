package com.circleEnyx.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.circleEnyx.main.Game;
import com.circleEnyx.main.Sound;
import com.circleEnyx.world.Camera;
import com.circleEnyx.world.World;


public class Player extends Entity {
	
	public boolean isPressed = false;
	
	public Player(int x, int y, int width, int height,double speed,BufferedImage sprite) {
		super(x, y, width, height,speed,sprite);
	}
	
	public void tick(){
		depth = 2;
		if(!isPressed) {
			y+=1;
		}else {
			if(y>0)
				y-=1;
		}
		
		if(y > Game.HEIGHT) {
			World.restartGame();
			System.out.println("Fell out of the screen !");
		}
		
		checkCollision();
	}

	private void checkCollision() {
		// TODO Auto-generated method stub
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Tube) {
				if(Entity.isColidding(Game.player, e)) {
					Game.gameOver = true;
//					World.restartGame();
					System.out.println("*Booom*");	
					World.generateParticles(90, this.getX()+16, this.getY()+8, Color.YELLOW, 50);
					Sound.gameOverSound.play();
					return;
				}
			}
		}
	}
	
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		if(!isPressed) {
			g2.rotate(Math.toRadians(20), this.getX() + width/2, this.getY() + height/2);
			g2.drawImage(sprite, this.getX(), this.getY(), null);
			g2.rotate(Math.toRadians(-20), this.getX() + width/2, this.getY() + height/2);
		}else {
			g2.drawImage(sprite, this.getX(), this.getY(), null);
		}
	}

	

	


}
