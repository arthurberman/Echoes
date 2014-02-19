package echoes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Player {
	int x, y;
	Music music;
	Game game;
	Polygon sword;
	public Rectangle rec;
	public int speed = 2;
	byte direction;
	public int hitCount;
	
	public Player (Music mus, Game game) {
		music = mus;
		this.game = game;
		x = 512;
		y = 384;
		rec = new Rectangle(x, y, 40, 10);
		hitCount = 0;
	}
	
	public void move(boolean up, boolean down, boolean left, boolean right) {
		hitCount++;
		if (up) {
			y -= speed;
		//	direction = 0;
		}
		if (left) {
			x -= speed;
			
		}
		if (right) {
			x+=speed;
		}
		if (down) {
			y+=speed;
					
		}
		if (x < 0)
			x=0;
		if (x > 1300)
			x=1300;
		if (y > 730)
			y=730;
		if (y < 0)
			y=0;
	}

	public void draw(Graphics g) {

		g.setColor(Color.blue);

		if (direction == 0) {
			//draw head
			
			g.setColor(Color.blue);
			g.fillRect(x + 3, y - 10, 14, 14);
			g.setColor(Color.white);
			g.drawRect(x + 3, y - 10, 14, 14);
//draw body
			g.setColor(Color.blue);
			g.fillRect(x, y, 20, 40);
			g.setColor(Color.white);
			g.drawRect(x, y, 20, 40);
			//draw gun
			g.setColor(Color.blue);
			if (music.beat())
				g.setColor(Color.white);
			g.fillRect(x+20, y-30, 10, 35);
		}
		
		if (direction == 2) {
			
			g.fillRect(x + 8, y - 14, 14, 14);
			g.setColor(Color.white);
			g.drawRect(x + 8, y - 14, 14, 14);

//draw body
			g.setColor(Color.blue);
			g.fillRect(x, y, 20, 40);
			g.setColor(Color.white);
			g.drawRect(x, y, 20, 40);
			//draw gun
			g.setColor(Color.blue);
			if (music.beat())
				g.setColor(Color.white);
			g.fillRect( x + 3, y + 3, 35, 10);
		}
		if (direction == 8) {
			g.fillRect(x - 4, y - 14, 14, 14);
			g.setColor(Color.white);
			g.drawRect(x - 4, y - 14, 14, 14);

			//draw gun

			g.setColor(Color.blue);
			if (music.beat())
				g.setColor(Color.white);
			g.fillRect( x - 18, y + 3, 35, 10);
//draw body
			g.setColor(Color.blue);
			g.fillRect(x, y, 20, 40);
			g.setColor(Color.white);
			g.drawRect(x, y, 20, 40);

		}
		if (direction == 4) {
//draw body
			g.setColor(Color.blue);

			g.fillRect(x, y, 20, 40);
			g.setColor(Color.white);
			g.drawRect(x, y, 20, 40);
			//draw gun
			g.setColor(Color.blue);
			g.fillRect(x + 3, y - 7, 14, 14);
			g.setColor(Color.white);
			g.drawRect(x+3, y-7, 14, 14);
			g.setColor(Color.blue);
			if (music.beat())
				g.setColor(Color.white);
			g.fillRect(x-10, y, 10, 35);
		}
		rec.x = x;
		rec.y = y;
		
	}
}
