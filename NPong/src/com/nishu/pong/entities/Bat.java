package com.nishu.pong.entities;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

import org.lwjgl.input.Keyboard;

import com.nishu.pong.Main;
import com.nishu.utils.Color4f;

public class Bat extends Entity implements Renderable{
	
	private boolean player;
	private final float MOVESPEED = 10;
	private float force;

	public Bat(float x, float y, boolean player) {
		super(x, y, 200, 16, Color4f.WHITE);
		this.player = player;
	}

	@Override
	public void update(Ball ball) {
		if(player){
			if(Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP)){
				setY(getY() + MOVESPEED);
				force = 2;
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
				setY(getY() + -MOVESPEED);
				force = -2;
			}
		}else if(!player){
			setY(ball.getY() - ball.getSize() / 2);
			if(getY() > Main.HEIGHT / 2) force = 2;
			if(getY() < Main.HEIGHT / 2) force = -2;
		}
		if(getY() + (getLength() / 2) > Main.HEIGHT - (getLength() / 2)) setY(Main.HEIGHT - getLength());
		if(getY() < 0) setY(0);
	}

	@Override
	public void render() {
		glBegin(GL_QUADS);
		glColor4f(getColor().r, getColor().g, getColor().b, getColor().a);
		glVertex2f(getX(), getY());
		glVertex2f(getX() + getWidth(), getY());
		glVertex2f(getX() + getWidth(), getY() + getLength());
		glVertex2f(getX(), getY() + getLength());
		glEnd();
	}

	@Override
	public void update() {
	}

	public float getForce() {
		return force;
	}

	public void setForce(float force) {
		this.force = force;
	}

}
