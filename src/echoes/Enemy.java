package echoes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;

public class Enemy {
	int x, y, w, h;
	public int health;
	public Shape rec;
	private int speed = 50;
	Music music;
	Game game;
	public int points = 100;
	private int lastBeat;
	protected boolean explode = false;

	public Enemy(Music mu, Game ga) {
		game = ga;
		music = mu;
		x = (int)(Math.random() * 1024);
		y = (int)(Math.random() * 768);
		w = 50;
		h = 50;
		if (Math.random() * 2.0f > 1) {
			if (x > 512)
				x = 1366;
			else
				x = 0;
		} else {
			if (y > 384) 
				y = 768;
			else
				y = 0;
		}
		rec = new Rectangle(x, y, w, h);
		health = 30;
	}
	public void draw(Graphics g) {
		g.setColor(Color.black);
		g.drawRect(x, y, w, h);
		g.setColor(Color.red);

		if (music.beat()) {
			g.setColor(Color.green);

		}
		g.fillRect(x, y, w, h);
	}
	public void move(Player hero) {
		// TODO Auto-generated method stub
		if (!explode ) {
			if ((music.getBeat() == 0 || music.getBeat() == 2) && lastBeat != music.getBeat()) {
				if ( x < hero.x)
					x += speed;

				else if ( x > hero.x) 
					x -= speed;
				if (y < hero.y)
					y+= speed;
				else if (y > hero.y)
					y-=speed;

			}
		} else {
			int xplo = 1;
			x-=xplo;
			y-=xplo;
			w+=xplo*2;
			h+=xplo*2;
			if (w>500)
				health = 0;
		}
		((Rectangle)rec).x = x;
		((Rectangle)rec).y = y;
		((Rectangle)rec).width = w;
		((Rectangle)rec).height = h;
		lastBeat = music.getBeat();
	}
	
	public void explode() {
		points = 0;
		explode = true;
	}
	public void hit(int amt) {
		// TODO Auto-generated method stub
		health -= amt;

	}
}
