package com.nishu.pong.world;

import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glViewport;

import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import com.nishu.pong.Main;
import com.nishu.pong.entities.Ball;
import com.nishu.pong.entities.Bat;
import com.nishu.utils.Color4f;
import com.nishu.utils.Font;
import com.nishu.utils.GameLoop;
import com.nishu.utils.ScreenTools;
import com.nishu.utils.Text;

public class Game {

	private Font font;
	private Random rand;

	private Bat playerBat;
	private Bat enemyBat;
	private Ball ball;

	private boolean speedBoost, sizeBoost;

	public static int score, boostTimer, time;
	public static boolean reloadBall, isBoostEnabled, debug;

	public Game() {
		initGL();
		init();
	}

	public void initGL() {
	}

	public void init() {
		rand = new Random();
		font = new Font();
		font.loadFont("Default", "calibri.png");
		playerBat = new Bat(10, Main.HEIGHT / 2 - 100, true);
		enemyBat = new Bat(Main.WIDTH - 26, Main.HEIGHT / 2 - 100, false);
		ball = new Ball(playerBat.getX() + 16, playerBat.getY() + playerBat.getLength() / 2);
		ball.setVelX(-8);
	}

	public void update() {
		time++;
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) && Keyboard.isKeyDown(Keyboard.KEY_C)) {
					debug = !debug;
				}
			}
		}

		if(time % (100 * 4) == 0){
			if (ball.getVelX() < 0) {
				ball.setVelX(ball.getVelX() - 0.75f);
			} else if (ball.getVelX() > 0) {
				ball.setVelX(ball.getVelX() + 0.75f);
			}
		}
		
		if(ball.getVelX() < -16){
			ball.setVelX(-16);
		}
		if(ball.getVelX() > 16){
			ball.setVelX(16);
		}
		
		if (!isBoostEnabled) {
			//size reduction
			if(rand.nextInt(500) >= 499){
				isBoostEnabled = true;
				sizeBoost = true;
				boostTimer = 1000;
				
				ball.setColor(Color4f.RED);
				ball.setSize(8);
			}
			
			//speed boost
			if (rand.nextInt(700) >= 699) {
				isBoostEnabled = true;
				speedBoost = true;
				boostTimer = 1000;

				ball.setColor(Color4f.RED);

				if (ball.getVelX() < 0) {
					ball.setVelX(ball.getVelX() - 8);
				} else if (ball.getVelX() > 0) {
					ball.setVelX(ball.getVelX() + 8);
				}
			}
		}

		if (isBoostEnabled && boostTimer >= 1) {
			boostTimer--;
		}
		if (isBoostEnabled && boostTimer <= 0) {
			isBoostEnabled = false;
			boostTimer = 0;

			ball.setColor(Color4f.BLUE);

			if(sizeBoost){
				ball.setSize(32);
			}
			
			if (speedBoost) {
				if (ball.getVelX() < 0) {
					ball.setVelX(-8);
				} else if (ball.getVelY() > 0) {
					ball.setVelX(8);
				}
			}
			speedBoost = false;
			sizeBoost = false;
		}

		if (reloadBall) {
			ball = new Ball(playerBat.getX() + 16, playerBat.getY() + playerBat.getLength() / 2);
			ball.setVelX(-8);
			reloadBall = false;
		}

		playerBat.update(ball);
		enemyBat.update(ball);

		if (ball.getX() >= playerBat.getX() && ball.getX() <= playerBat.getX() + playerBat.getWidth() && ball.getY() >= playerBat.getY() && ball.getY() <= playerBat.getY() + playerBat.getLength()) {
			ball.setVelX(-ball.getVelX());
			ball.setVelY(playerBat.getForce());
			playerBat.setForce(0);
		}
		if (ball.getX() + ball.getSize() >= enemyBat.getX() && ball.getX() + ball.getSize() <= enemyBat.getX() + enemyBat.getWidth() && ball.getY() + ball.getSize() >= enemyBat.getY() && ball.getY() + ball.getSize() <= enemyBat.getY() + enemyBat.getLength()) {
			ball.setVelX(-ball.getVelX());
		}

		ball.update();
	}

	public void render() {
		playerBat.render();
		enemyBat.render();
		ball.render();
		
		ScreenTools.render2D();
		if (debug) {
			Text.renderString(font, "Debug Mode", -1.25f, 0.57f, 0.75f, Color4f.WHITE);
			Text.renderString(font, "FPS: " + GameLoop.getFPS(), -1.25f, 0.53f, 0.75f, Color4f.WHITE);
			Text.renderString(font, "Time: " + time, -1.25f, 0.49f, 0.75f, Color4f.WHITE);
			Text.renderString(font, "Time / Four Seconds: " + time % (100 * 4), -1.25f, 0.45f, 0.75f, Color4f.WHITE);
			Text.renderString(font, "Ball Velocity: " + ball.getVelX() + " , " + ball.getVelY(), -1.25f, 0.41f, 0.75f, Color4f.WHITE);
		}
		if (!debug) {
			if (isBoostEnabled && speedBoost) {
				Text.renderString(font, "Speed Boost Activated!: " + boostTimer / 100, -1.25f, 0.57f, 0.75f, Color4f.WHITE);
			}
			if (isBoostEnabled && sizeBoost) {
				Text.renderString(font, "Size Boost Activated!: " + boostTimer / 100, -1.25f, 0.57f, 0.75f, Color4f.WHITE);
			}
			Text.renderString(font, "Score: " + score, -1.25f, 0.61f, 0.75f, Color4f.WHITE);
		}
		renderGame();
	}

	private void renderGame() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();

		glMatrixMode(GL_MODELVIEW);
		glOrtho(0, Main.WIDTH, 0, Main.HEIGHT, 1, -1);
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
	}

	public void dispose() {
	}

}
