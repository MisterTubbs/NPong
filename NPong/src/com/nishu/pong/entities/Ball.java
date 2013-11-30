package com.nishu.pong.entities;

import static org.lwjgl.opengl.GL11.*;

import com.nishu.pong.Main;
import com.nishu.pong.world.Game;
import com.nishu.utils.Color4f;

public class Ball extends Entity implements Renderable{
	
	private float velX, velY;
	
	public Ball(float x, float y) {
		super(x, y, 32, Color4f.BLUE);
	}

	@Override
	public void update() {
		if(getX() < 0) {
			Game.score -= 1;
			Game.reloadBall = true;
		}
		if(getX() > Main.WIDTH - 16) {
			Game.score += 1;
			Game.reloadBall = true;
		}
		if(getY() < 0) setVelY(-velY);
		if(getY() > Main.HEIGHT - 16) setVelY(-velY);
		setX(getX() + velX);
		setY(getY() + velY);
	}

	@Override
	public void render() {
		glBegin(GL_QUADS);
		glColor4f(getColor().r, getColor().g, getColor().b, getColor().a);
		glVertex2f(getX(), getY());
		glVertex2f(getX() + getSize(), getY());
		glVertex2f(getX() + getSize(), getY() + getSize());
		glVertex2f(getX(), getY() + getSize());
		glEnd();
	}

	public float getVelX() {
		return velX;
	}

	public void setVelX(float velX) {
		this.velX = velX;
	}

	public float getVelY() {
		return velY;
	}

	public void setVelY(float velY) {
		this.velY = velY;
	}

}
