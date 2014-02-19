package echoes;


import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.event.KeyEvent;
import java.awt.image.VolatileImage;
import java.io.File;
import java.util.LinkedList;
import java.util.ListIterator;
import java.awt.Graphics2D;

import javax.swing.JFileChooser;


import com.echonest.api.v4.*;

public class Game extends Canvas {



	/**
	 * @param args
	 */
	Music mus;
	VolatileImage screen, filterscreen;
	Visualizer viz;
	public boolean[] key = new boolean[256]; //key pressed booleans
	Player hero;
	Graphics offScreen;
	private int oldSection;
	private LinkedList<Enemy> enemies;
	private LinkedList<Bullet> bullets;
	private LinkedList<Score> scores;
	int score = 0;
	private boolean canBarSpawn;
	private int multiplier = 1;
	private int multiplierCount = 0;
	private double oldLoudness;
	private int timeForTris;
	private int crescendo;
	GraphicsConfiguration gc;

	public Game(GraphicsConfiguration g) {
		
		//File f = new File("music/Of Far Away Lands.mp3");
		JFileChooser fc = new JFileChooser();
		fc.showOpenDialog(this);
		File f = fc.getSelectedFile();
		gc = g;
		System.out.println(gc);
		//File f = new File("music/Daft Punk/Discovery/07 Superheroes.mp3");
		//File f = new File("music/00 - Duncan Hills Jingle.mp3");
		//File f = new File("music/Daft Punk/Discovery/01 One More Time.mp3");
		mus = new Music(f);
		screen = gc.createCompatibleVolatileImage(1366, 768);
		filterscreen = gc.createCompatibleVolatileImage(1366, 768);
		offScreen = screen.getGraphics();
		hero = new Player(mus, this);
		enemies = new LinkedList<Enemy> ();
		bullets = new LinkedList<Bullet> ();
		scores = new LinkedList<Score>();
		viz = new Visualizer(mus, this);
		((Graphics2D) offScreen).setStroke(new BasicStroke(5));
	}
	public void doStuff() {
		doInput();
		spawn();

		ai();
		bullets();
		repaint();
	}
	private void bullets() {
		// TODO Auto-generated method stub
		ListIterator<Bullet> bullIt = bullets.listIterator();
		Bullet b;
		try {
			while (bullIt.hasNext()) {
				b = bullIt.next();
				b.move();
				if (b.x > 1366 || b.x < 0) {
					if (b.y > 768 || b.y < 0) {
						bullIt.remove();
					}
				}
				ListIterator<Enemy> enemyIt = enemies.listIterator();
				Enemy d;
				try {
					while (enemyIt.hasNext()) {
						d = enemyIt.next();
						if (d.rec.intersects(b.rec)) {
							d.hit(b.power);
							boolean v = b.awesome;
							if (v) {
								multiplierCount++;
							} else {
								multiplierCount = 0;
								multiplier = 1;
							}
							if (multiplierCount > 7) {
								multiplier *= 2;
								multiplierCount = 0;
							}
							bullIt.remove();
						}
					}
				} catch( Exception e) {

				}


			}
		} catch( Exception e) {

		}
	}
	
	private void ai() {
		ListIterator<Enemy> enemyIt = enemies.listIterator();
		Enemy d;
		try {
			while (enemyIt.hasNext()) {
				d = enemyIt.next();

				if (d.health <= 0) {
					score+= d.points * multiplier;
					scores.add(new Score(d.x, d.y, d.points * multiplier));
					enemyIt.remove();

				}
				d.move(hero);
				if (d.rec.intersects(hero.rec) && hero.hitCount > 100) {
					multiplier = 1;
					multiplierCount = 0;
					score -=50;
					scores.add(new Score(hero.x, hero.y, -50));
					hero.hitCount = 0;


				}
			}
		} catch( Exception e) {

		}
		// TODO Auto-generated method stub

	}
	
	private void spawn() {

		// TODO Auto-generated method stub
		if (mus.getSectionNumber() != oldSection) {
			//System.out.println(mus.getSegment().getLoudnessMax());
			if(mus.getSegment().getLoudnessMax() > oldLoudness) {
				spawnBoids(10);
				crescendo++;
			} else {
				spawnTris(10);
			}
			if (crescendo > 4) {
				crescendo = 0;
				spawnTris(5);
			}
			timeForTris++;
			oldLoudness = mus.getSegment().getLoudnessMax();
		}
		oldSection = mus.getSectionNumber();

		if (mus.getBar() % 4 == 0 && enemies.size() == 0) {
			spawnBoids(5);
		}

	}

	private void spawnTris(int num) {
		// TODO Auto-generated method stub
		ListIterator<Enemy> enemyIt = enemies.listIterator();
		Enemy d;
		try {
			while (enemyIt.hasNext()) {
				d = enemyIt.next();
				d.explode();
			}
		} catch( Exception e) {

		}
		for (int i = 0; i < num; i++) {
			enemies.add(new TriEnemy(mus, this));
		}
	}
	private void spawnBoids (int num) {
		ListIterator<Enemy> enemyIt = enemies.listIterator();
		Enemy d;
		try {
			while (enemyIt.hasNext()) {
				d = enemyIt.next();
				d.explode();
			}
		} catch( Exception e) {

		}
		for (int i = 0; i < num; i++) {
			enemies.add(new Enemy(mus, this));
		}
	}
	public void doInput() {
		hero.move(key[KeyEvent.VK_W], key[KeyEvent.VK_S], key[KeyEvent.VK_A], key[KeyEvent.VK_D]);

	}

	public void update(Graphics g) {
		paint(g);
	}
	public void paint( Graphics g) {

		offScreen.setColor(Color.black);
		//	offScreen.setColor(viz.getColor());
	//	offScreen.fillRect(0, 0, 1366, 768);
		//offScreen.setColor(Color.red);
		offScreen.drawImage(viz.getImg(), 0, 0, 1366, 768, this);
		//viz.buzz(offScreen);
		ListIterator<Enemy> enemyIt = enemies.listIterator();
		Enemy d;
		try {
			while (enemyIt.hasNext()) {
				d = enemyIt.next();
				d.draw(offScreen);
			}
		} catch( Exception e) {

		}

		ListIterator<Bullet> bullIt = bullets.listIterator();
		Bullet b;
		try {
			while (bullIt.hasNext()) {
				b = bullIt.next();
				b.draw(offScreen);
			}
		} catch( Exception e) {

		}

		ListIterator<Score> scoreIt = scores.listIterator();
		Score s;
		try {
			while (scoreIt.hasNext()) {
				s = scoreIt.next();
				s.draw(offScreen);
				if (s.x > 1366)
					scoreIt.remove();
			}
		} catch( Exception e) {

		}
		hero.draw(offScreen);

		offScreen.setColor(Color.blue);
		offScreen.setFont(new Font(Font.DIALOG, Font.PLAIN, 40));
		offScreen.setColor(new Color(0, 0, 0, 0.8f));
		offScreen.fillRect(1000, 80, 300, 100);
		offScreen.setColor(Color.gray);
		offScreen.drawString(""+score, 1050, 110);
		offScreen.drawString("x"+multiplier , 1090, 150);
		//offScreen.fillRect(10, 10, 100, 100);
		//filter.filter(screen, filterscreen);
		g.drawImage(screen, 0, 0, 1366, 768, this);
	}
	public void click(int x, int y) {
		// TODO Auto-generated method stub
		double angle = Math.atan2(hero.y - y, hero.x - x);
		makeBullet(hero.x, hero.y, angle);
	}
	private void makeBullet(int x, int y, double angle) {
		// TODO Auto-generated method stub
		boolean v = mus.beat();
		if (!v) {
			multiplier = 1;
			multiplierCount = 0;
		}

		bullets.add(new Bullet(x, y, angle, v));

	}
	public void moveMouse(int x, int y) {
		// TODO Auto-generated method stub
		double angle = Math.toDegrees(Math.atan2(x-hero.x, y - hero.y));
		if (angle < -135  || angle > 135)
			hero.direction = 0;
		if (angle < 135 && angle > 45)
			hero.direction = 2;
		if (angle < 45 && angle > -45)
			hero.direction = 4;
		if (angle < -45 && angle > -135)
			hero.direction = 8;
		//System.out.println(angle);

	}

}
