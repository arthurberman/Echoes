package echoes;

import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.swing.JFrame;
import javax.swing.Timer;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import com.echonest.api.v4.*;

public class Echoes extends JFrame implements KeyListener, MouseListener, MouseMotionListener, ActionListener{

	/**
	 * @param args
	 */
	static String API_KEY = "O53NO38SC3RFJZ752";
	Game game;
	Timer theTimer[] = new Timer[256];

	public static void main(String[] args) throws EchoNestException {
		Echoes e = new Echoes();

		e.setSize(1366, 768);
		e.setExtendedState(Frame.MAXIMIZED_BOTH);
		e.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		e.setUndecorated(true);
		GraphicsDevice gd = GraphicsEnvironment.
				   getLocalGraphicsEnvironment().getScreenDevices()[0];
		gd.setFullScreenWindow(e.getWindows()[0]);
		e.setVisible(true);

	}
	
	public Echoes () {
		for (int i = 0; i < 256; i++) {
			theTimer[i] = new Timer(15,  this);
			theTimer[i].setRepeats(false);
		}
		game = new Game(getGraphicsConfiguration());
		game.addKeyListener(this);
		game.addMouseListener(this);
		game.addMouseMotionListener(this);
		add(game);
		init();
	}
	
	public void init() {
		new Thread () {
			public void run() {
				
				while(true) {
					game.doStuff();
					
					try {
						sleep(5);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
	
	public static void mainstreamballs(String[] args) throws EchoNestException {
		// TODO Auto-generated method stub
		EchoNestAPI echoNest = new EchoNestAPI(API_KEY);
		File file = new File("music/05 Saris.mp3");
		Player player = null;
		try {
			player = new Player(new FileInputStream(file));
		} catch (FileNotFoundException | JavaLayerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//     File file = new File("/media/arthur/OS/Users/ABerman/Music/iTunes/iTunes Media/Music/");
		try {
			Track track = echoNest.uploadTrack(file);
			System.out.println("Starting analysis");
			track.waitForAnalysis(30000);
			System.out.println("Done with analysis");
			if (track.getStatus() == Track.AnalysisStatus.COMPLETE && false) {
				System.out.println("Tempo: " + track.getTempo());
				System.out.println("Danceability: " + track.getDanceability());
				System.out.println("Speechiness: " + track.getSpeechiness());
				System.out.println("Liveness: " + track.getLiveness());
				System.out.println("Energy: " + track.getEnergy());
				System.out.println("Loudness: " + track.getLoudness());
				System.out.println();
				System.out.println("Beat start times:");

				TrackAnalysis analysis = track.getAnalysis();
				System.out.println("starting the times");
				for (TimedEvent beat : analysis.getBeats()) {
					System.out.println("beat " + beat.getStart());
				}
			} else {
				System.err.println("Trouble analysing track " + track.getStatus());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			player.play();
			System.out.println("Got here");
		} catch (JavaLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		game.key[arg0.getKeyCode()] = true;
		theTimer[arg0.getKeyCode()].stop();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		theTimer[arg0.getKeyCode()].restart();


		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Timer t = (Timer) (arg0.getSource());
		for (int i = 0; i < 256; i++) {
			if (theTimer[i] == t) {
				game.key[i] = false;
			}
		}
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		game.click(arg0.getX(), arg0.getY());
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		game.moveMouse(arg0.getX(), arg0.getY());
		
	}

}
