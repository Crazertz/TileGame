package dev.codenmore.tilegame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.ScrollPaneAdjustable;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import dev.codenmore.tilegame.display.Display;
import dev.codenmore.tilegame.gfx.ImageLoader;
import dev.codenmore.tilegame.gfx.SpriteSheet;

public class Game implements Runnable 
{
	private Display display;
	public int width, height;
	boolean raised;
	public String title;
	
	private boolean running = false;
	private Thread thread;
	
	private BufferStrategy bs;
	private Graphics g;

	private BufferedImage test;
	private SpriteSheet sheet;
	
	public Game(String title, int width, int height)
	{
		this.width = width;
		this.height = height;
		this.title = title;
	}
	
	private void init()
	{
		display = new Display(title, width, height);
		test = ImageLoader.loadImage("/textures/sprite_sheet.png");
		sheet = new SpriteSheet(test);
	}
	
	private void tick()
	{
		
	}
	
	private void render()
	{
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null)
		{
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		//Clear Screen
		g.clearRect(0, 0, width, height);
		//Draw Here

		g.drawImage(sheet.crop(0, 72, 72, 72), 5, 5, null);
		
		//End Drawing		
		bs.show();
		g.dispose();
	}
	
	public void run()
	{
		init();
		
		while(running)
		{
			tick();
			render();
		}
		
		stop();
		
	}
	
	public synchronized void start()
	{
		if(running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop()
	{
		if(!running)
			return;
		running = false;
		try 
		{
			thread.join();
		} 
		
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

}
