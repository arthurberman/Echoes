package echoes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import com.echonest.api.v4.*;

public class Music {
	static String API_KEY = "O53NO38SC3RFJZ752";
	boolean ready = false;
	EchoNestAPI echoNest = new EchoNestAPI(API_KEY);
	long lastBeat;
	TrackAnalysis analysis;
	mplayer player;
	private double loudness;
	private int beatPosition = 0;
	private int segment;
	private int section;
	protected int bar;
	private List<Segment> segbox;
	private Segment seg;
	int curSegment;
	/**
	 * @param args
	 */
	public Music(File file) {

		//     File file = new File("/media/arthur/OS/Users/ABerman/Music/iTunes/iTunes Media/Music/");
		player = new mplayer(file);
		try {
			System.out.println("Starting analysis");

			Track track = echoNest.uploadTrack(file);
			track.waitForAnalysis(30000);
			System.out.println("Done with analysis");
			if (track.getStatus() == Track.AnalysisStatus.COMPLETE) {
				System.out.println("Tempo: " + track.getTempo());
				System.out.println("Danceability: " + track.getDanceability());
				System.out.println("Speechiness: " + track.getSpeechiness());
				System.out.println("Liveness: " + track.getLiveness());
				System.out.println("Energy: " + track.getEnergy());
				System.out.println("Loudness: " + track.getLoudness());
				System.out.println();
				System.out.println("Beat start times:");

				analysis = track.getAnalysis();
				segbox = analysis.getSegments();
				System.out.println("starting the times");

			} else {
				System.err.println("Trouble analysing track " + track.getStatus());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EchoNestException e) {
			
		}

		player.start();
		updateSection();
		System.out.println("Got here");
		ready = true;
	}

	public int getBeat() {
		int beat = beatPosition % 4;

		return beat;
	}

	public double getBeatConfidence() {
		return analysis.getBeats().get(beatPosition).getConfidence();
	}

	private void updateSection() {
		// TODO Auto-generated method stub
		new Thread() {


			public void run() {
				while (true) {
					try {
						while (player.time() > (long)(analysis.getSections().get(section).getStart() * 1000))  {
							if (section + 1 < analysis.getSections().size())
								section++;
							bar = 0;
							//	loudness = computeLoudness(analysis.getSections().get(section).getStart(), analysis.getSections().get(section).getDuration());
						}
					} catch(Exception e) {
					}

					if (player.time() > (long)(analysis.getBars().get(bar).getStart() * 1000)) {
						bar ++;
					}
					if (player.player.isComplete())
						System.exit(0);
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
	public Segment getSegment() {
		while  (player.time() > (long)(analysis.getSegments().get(curSegment).getStart() * 1000)) {
			curSegment++;
		}
		Segment seg = analysis.getSegments().get(curSegment);

		return seg;
	}

	public int getBar() {
		return bar;
	}

	public int getSectionNumber() {
		return section;
	}
	public TimedEvent getSection() {
		return analysis.getSegments().get(section);
	}
	public boolean beat() {
		int bottom = 150;
		int top = 150;
		if (!ready)
			return false;
		int time = (int) player.time();
		//int time = player.player.getPosition();
		int curBeat = 0;
		try {
			TimedEvent e = analysis.getBeats().get(beatPosition);
			curBeat = (int) (analysis.getBeats().get(beatPosition).getStart() * 1000);

		} catch (IndexOutOfBoundsException e) {
			//System.exit(0);
		}
		boolean val = (time > curBeat - bottom && time < curBeat + top);
		if (analysis.getBeats().get(beatPosition).getConfidence() < 0.1f)
			val = false;
		//		System.out.println(time + ", " + curBeat + ", " + val);
		if (time > curBeat + top)
			beatPosition++;
		return val;
	}




}
