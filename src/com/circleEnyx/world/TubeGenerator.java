package com.circleEnyx.world;

import java.awt.image.BufferedImage;

import com.circleEnyx.entities.Entity;
import com.circleEnyx.entities.Tube;
import com.circleEnyx.main.Game;

public class TubeGenerator {
	
	public int time = 0;
	public int targetTime = 70;
	
	private static BufferedImage TOWER1 = Game.spritesheet.getSprite(16, 0, 16, 16);
	private static BufferedImage TOWER2 = Game.spritesheet.getSprite(32, 0, 16, 16);

	public void tick() {
		// TODO Auto-generated method stub
		time++;
		if(time == targetTime) {
			//new tube 
			//reset timer time
			int tubeHeight1 = Entity.rand.nextInt(65 - 30) + 30;
			Tube tube1 = new Tube(Game.WIDTH, 0, 20, tubeHeight1, 1, TOWER2);
			
			int tubeHeight2 = Entity.rand.nextInt(65 - 30) + 30;
			Tube tube2 = new Tube(Game.WIDTH,  Game.HEIGHT - tubeHeight2, 20, tubeHeight2, 1, TOWER1);
			
			Game.entities.add(tube1);
			Game.entities.add(tube2);
			time = 0;
		}
	}

	
	
}
