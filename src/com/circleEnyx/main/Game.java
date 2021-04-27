package com.circleEnyx.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.circleEnyx.entities.Entity;
import com.circleEnyx.entities.Player;
import com.circleEnyx.graficos.Spritesheet;
import com.circleEnyx.graficos.UI;
import com.circleEnyx.world.TubeGenerator;
import com.circleEnyx.world.World;

public class Game extends Canvas implements Runnable,KeyListener,MouseListener,MouseMotionListener{

	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	public static final int WIDTH = 240;
	public static final int HEIGHT = 160;
	public static final int SCALE = 4;
	
	private BufferedImage image;
	

	public static List<Entity> entities;
	public static Spritesheet spritesheet;
	public static Player player;
	
	public static TubeGenerator tubeGenerator;
	
	public UI ui;
	
	public static boolean gameOver = false;
	
	public static int score = 0;
	
	public Game(){
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		initFrame();
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		
		//Inicializando objetos.
		spritesheet = new Spritesheet("/spritesheet.png");
		entities = new ArrayList<Entity>();
		player = new Player(WIDTH/2 - 30, HEIGHT/2, 16, 16, 2, spritesheet.getSprite(0,0,16,16));
		tubeGenerator = new TubeGenerator();
		ui = new UI();
		
		entities.add(player);
		
	}
	
	public void initFrame() {
		
		frame = new JFrame("Tower avoider");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		//Icone da janela
		Image frameIcon = null;
		try {
			frameIcon = ImageIO.read(getClass().getResource("/frameIcon.png"));
		}catch(IOException e) {e.printStackTrace();}
		frame.setIconImage(frameIcon);
		
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		//Keybindings
		System.out.println("Keybindings : ");
		System.out.println("");
		System.out.println("Up - UP ARROW or W");
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) {
		Game game = new Game();
		game.start();
	}
	
	public void tick() {
		if(!gameOver) {
			tubeGenerator.tick();
			for(int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);
				e.tick();
			}
		}else {
			
		}
	}
	
	
	public void render() {

		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		Graphics2D g2 = (Graphics2D) g;
		g.setColor(new Color(122,102,255));
		g.fillRect(0, 0,WIDTH,HEIGHT);
	
		/*Renderiza��o do jogo*/
		//Graphics2D g2 = (Graphics2D) g;
		Collections.sort(entities,Entity.nodeSorter);
		for(int i = 0; i < entities.size(); i++) {
		Entity e = entities.get(i);
		e.render(g);
		}
		if(Game.gameOver) {
			g2.setColor(new Color(0, 0, 0, 140));
			g2.fillRect(0, 0, Game.WIDTH*SCALE, Game.HEIGHT*SCALE);
			g2.setColor(Color.WHITE);
			g2.drawString("Press Enter to restart", (Game.WIDTH/2)-55, (Game.HEIGHT/2));
		}
		/***/
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0,WIDTH*SCALE,HEIGHT*SCALE,null);
		ui.render(g);
		bs.show();
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		while(isRunning){
			long now = System.nanoTime();
			delta+= (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer >= 1000){
				frames = 0;
				timer+=1000;
			}
			
		}
		
		stop();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			player.isPressed = true;
		}else if(e.getKeyCode() == KeyEvent.VK_ENTER && Game.gameOver == true) {
			World.restartGame();
			Game.gameOver = false;
		}
	
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			player.isPressed = false;
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {	
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	
	}

	
}