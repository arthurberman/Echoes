package echoes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

public class TriEnemy extends Enemy {
	double angle, goalangle;
	public TriEnemy(Music mu, Game ga) {
		super(mu, ga);
		// TODO Auto-generated constructor stub


	}
	public void move(Player hero) {
		if (!explode) {
			if (music.getBar() %2 == 0) {
				goalangle = Math.atan2(y - hero.y, x - hero.x) + Math.toRadians(180);
				angle += (goalangle - angle) / 100;
			} else {
				x+=Math.cos(angle) * 2;
				y+=Math.sin(angle) * 2;
			}

		} else {
			angle = -Math.atan2(y - 348, x - 512) + Math.toRadians(180);
			x+=Math.cos(angle) * 2;
			y+=Math.sin(angle) * 2;
			if (x > 1024 || x < 0 || y > 768 || y < 0)
				health = 0;
		}
	}
	public void draw(Graphics g) {
		Polygon p = new Polygon();
		p.addPoint(x, y);
		p.addPoint((int)(x+Math.cos(angle-1) * 80), (int)(y+Math.sin(angle-1) * 80));
		p.addPoint((int)(x+Math.cos(angle+1) * 80), (int)(y+Math.sin(angle+1) * 80));
		g.setColor(Color.black);
		g.drawPolygon(p);
		g.setColor(Color.blue);
		if(music.beat()){
			g.setColor(Color.cyan);
		}
		rec = p;
		
		g.fillPolygon(p);
	}

}
