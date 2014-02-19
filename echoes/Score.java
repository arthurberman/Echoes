package echoes;

import java.awt.Graphics;

public class Score {
	int x, y;
	int points;
	
	public Score(int x, int y, int p) {
		this.x=x;
		this.y=y;
		points = p;
	}
	
	public void draw(Graphics g) {
		
		g.drawString(""+points, x, y);
		x+=10;
		y-=30;
	}
}
