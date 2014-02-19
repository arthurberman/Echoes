package echoes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Bullet {
	
	int x, y;
	boolean awesome = false;
	double angle;
	int power = 5;
	Rectangle rec;
	
	public Bullet( int x, int y, double angle ) {
		this.x = x;
		this.y = y;
		this.angle = angle + Math.toRadians(180);
		rec = new Rectangle(x, y, 50, 50);
		
	}
	public Bullet( int x, int y, double angle, boolean a ) {
		this.x = x;
		this.y = y;
		this.angle = angle+ Math.toRadians(180);
		rec = new Rectangle(x, y, 25, 25);
		
		awesome = a;
		if (awesome)
			power = 10;
	}
	public void move() {
		x+= Math.cos (angle) * 10;
		y+= Math.sin(angle) * 10;
		rec.x = x;
		rec.y = y;

	}
	public void draw(Graphics g) {
		g.setColor(Color.white);
		if (awesome)
			g.setColor(Color.red);
		
			
		g.fillOval(x, y, 25, 25);
		
	}
}
