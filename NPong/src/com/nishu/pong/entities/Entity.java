package com.nishu.pong.entities;

import com.nishu.utils.Color4f;
import com.nishu.utils.Vector2f;

public class Entity {
	
	private Vector2f pos;
	private Color4f color;
	private float size, length, width;
	
	public Entity(float x, float y, float size, Color4f color){
		this(new Vector2f(x, y), size, color);
	}
	
	public Entity(float x, float y, float length, float width, Color4f color){
		this.pos = new Vector2f(x, y);
		this.length = length;
		this.width = width;
		this.color = color;
	}
	
	public Entity(Vector2f pos, float size, Color4f color){
		this.pos = pos;
		this.size = size;
		this.color = color;
	}
	
	public float getX(){
		return pos.getX();
	}
	
	public float getY(){
		return pos.getY();
	}
	
	public void setX(float x){
		pos.setX(x);
	}
	
	public void setY(float y){
		pos.setY(y);
	}
	
	public Vector2f getPosition(){
		return pos;
	}
	
	public float getSize(){
		return size;
	}
	
	public void setSize(float size){
		this.size = size;
	}

	public Color4f getColor(){
		return color;
	}
	
	public void setColor(Color4f color){
		this.color = color;
	}

	public float getLength() {
		return length;
	}

	public void setLength(float length) {
		this.length = length;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public void update(Ball ball) {
	}
	
}
