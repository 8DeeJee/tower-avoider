package com.circleEnyx.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.circleEnyx.main.Game;
import com.circleEnyx.world.Camera;

public class Particle extends Entity{
	


	private int lifeTime = 5;
	private int curLife = 0;
	private int speed = 3;
	private double dx = 0, dy = 0; 
	private Color color;

	public Particle(double x, double y, int width, int height, double speed, BufferedImage sprite, Color color) {
		super(x, y, width, height, speed, sprite);
		// TODO Auto-generated constructor stub
		this.color = color;
		dx = new Random().nextGaussian();
		dy = new Random().nextGaussian();
	}
	
	public void render(Graphics g) {
		x+=dx*speed;
		y+=dy*speed;
		curLife++;
		if(curLife >= lifeTime) {
			Game.entities.remove(this);
		}
		g.setColor(this.color);
		g.fillRect(this.getX(), this.getY(), width, height);
	}
		


}
