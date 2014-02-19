package echoes;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Visualizer {
	private BufferedImage image, image2;
	Music music;
	Game game;
	private int rad;
	private boolean ready;
	int w, h;
	Graphics graph; 
	Graphics graph2;
	float r, g, b, a;
	private int currentEffect;
	public Visualizer(Music mu, Game g) {
		w= 78;
		h = 38;
		
		image = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
		image2 = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
		graph = image.getGraphics();
		graph2 = image2.getGraphics();
		music = mu;
		game =g ;
		//manipulate();

	}

	public void manipulate() {
		new Thread() {
			private int currentEffect = 0;

			public void run() {
				Graphics graph = image.getGraphics();
				Graphics graph2 = image2.getGraphics();
				float r, g, b,a ;
				r = 0;
				g = 0;
				b = 0;
				if (music.beat())
					a=1;
				while (true) {

					r+=Math.random()/10000;
					b+=Math.random()/1000;
					g+=Math.random()/100000;
					a = 0.8f;
					if (r > 1)
						r=0;
					if (g > 1)
						g=0;
					if (b > 1)
						b=0;

					if (a < 0)
						a=0;


					graph.setColor(new Color(r,g,b, a));
					switch(currentEffect ) {
					case 0:
						if (Math.random() > 0.4)
							bars(graph2);

						break;
					case 1:
						if (Math.random() > 0.4) {
							buzz(graph2);
						}
						break;
					case 2:
						currentEffect = 0;
						break;
					default:
						currentEffect = 0;
						break;

					}
					graph.fillRect(0, 0, 1024, 768);
					graph.drawImage(image2, 0, 0, game);
					if (Math.random() > 0.995) {
						image2 = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
						graph2 = image2.getGraphics();
						currentEffect++;
					}

					ready = true;
					try {
						sleep(2);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}


		}.start();
	}
	private void bars(Graphics graph) {
		// TODO Auto-generated method stub
		graph.setColor(new Color((float)Math.random(), (float)Math.random(), (float)Math.random(), (float)Math.random()/4));
		graph.fillRect((int)(Math.random() * w), (int)(Math.random() * h), (int)(Math.random() * 100), (int)(Math.random() * 100));
	}
	protected void buzz(Graphics graph) {
		// TODO Auto-generated method stub
		graph.setColor(new Color((float)Math.random(), (float)Math.random(), (float)Math.random(), (float)Math.random()/4));

		graph.fillOval(w/2, h/2, rad, rad);
		graph.setColor(new Color((float)Math.random(), (float)Math.random(), (float)Math.random(), (float)Math.random()/4));

		graph.fillOval((w/2)-rad, (h/2)-rad, rad, rad);
		graph.setColor(new Color((float)Math.random(), (float)Math.random(), (float)Math.random(), (float)Math.random()/4));

		graph.fillOval((w/2)-rad, (h/2), rad, rad);
		graph.setColor(new Color((float)Math.random(), (float)Math.random(), (float)Math.random(), (float)Math.random()/4));

		graph.fillOval((w/2), (h/2)-rad, rad, rad);

		rad+=10;
		if (rad > (w/2))
			rad = 0;
	}
	public Color getColor() {
		r+=Math.random()/10000;
		b+=Math.random()/1000;
		g+=Math.random()/100000;
		a-=0.3f;
		if (music.beat())
			a = 0.8f;
		if (r > 1)
			r=0;
		if (g > 1)
			g=0;
		if (b > 1)
			b=0;
		if (a < 0)
			a=0;

		return new Color(r, g, b);
	}
	public BufferedImage getImg() {


		r+=Math.random()/10000;
		b+=Math.random()/1000;
		g+=Math.random()/100000;
		a-=0.3f;
		if (music.beat())
			a = 0.8f;
		if (r > 1)
			r=0;
		if (g > 1)
			g=0;
		if (b > 1)
			b=0;
		if (a < 0)
			a=0;


		graph.setColor(new Color(r,g,b, a));
		switch(currentEffect ) {
		case 0:
			if (Math.random() > 0.4)
				bars(graph2);

			break;
		case 1:
			if (Math.random() > 0.4) {
				buzz(graph2);
			}
			break;
		case 2:
			graph2.setColor(Color.black);
			((Graphics2D)graph2).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC).derive(0.55f));

			graph2.drawImage(game.screen, 0, 0, w, h, game);
			
			break;
		default:
			currentEffect = 0;
			break;

		}
		graph.fillRect(0, 0, 1024, 768);
		graph.drawImage(image2, 0, 0, game);
		if (Math.random() > 0.995) {
			image2 = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
			graph2 = image2.getGraphics();
			currentEffect++;
		}
		
		return image;
	}
}
