package com.circleEnyx.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.circleEnyx.main.Game;

public class Tube extends Entity{
	

	private BufferedImage towerSprite;
	
	public Tube(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		// TODO Auto-generated constructor stub
		this.towerSprite = sprite;
	}

	public void tick() {
		x--;
		if(x+width <= 0) {
			Game.entities.remove(this);
			return;
		}if(x == Game.player.x && y <20) {
			Game.score++;
		}
	}
	
	public void render(Graphics g) {
//		g.setColor(new Color(105, 225, 105));
//		g.fillRect(this.getX(), this.getY(), width, height);
		g.drawImage(towerSprite, this.getX(), this.getY(), width, height, null);
	}

}
