package com.nishu.pong;

import org.lwjgl.opengl.Display;

import com.nishu.pong.world.Game;
import com.nishu.utils.Color4f;
import com.nishu.utils.GameLoop;
import com.nishu.utils.Screen;
import com.nishu.utils.ScreenTools;
import com.nishu.utils.Window;

import static org.lwjgl.opengl.GL11.*;

public class Main extends Screen{
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	
	private GameLoop loop;
	private Game game;
	
	public Main(){
		loop = new GameLoop();
		loop.setScreen(this);
		loop.start(60);
	}
	
	@Override
	public void init() {
		game = new Game();
	}

	@Override
	public void initGL() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		
		glMatrixMode(GL_MODELVIEW);
		glOrtho(0, WIDTH, 0, HEIGHT, 1, -1);
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
	}

	@Override
	public void update() {
		game.update();
	}
	
	@Override
	public void render() {
		ScreenTools.clearScreen(false, Color4f.BLACK);
		game.render();
	}
	
	@Override
	public void dispose() {
		game.dispose();
	}

	public static void main(String[] args){
		Window.createWindow(WIDTH, HEIGHT, "Nishu Basics Pong", false, false);
		new Main();
	}

}
