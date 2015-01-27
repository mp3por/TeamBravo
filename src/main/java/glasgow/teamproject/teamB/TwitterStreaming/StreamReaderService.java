package glasgow.teamproject.teamB.TwitterStreaming;

import glasgow.teamproject.teamB.TwitIE.TwitIE;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import twitter4j.FilterQuery;
import twitter4j.RawStreamListener;
import twitter4j.TwitterStream;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;

public class StreamReaderService {

	private final TweetDAO tweetSaver;
	private final TwitterStreamBuilderUtil streamBuilder;
	private final TwitterStream stream;
	private final String collectionName;
	private TwitIE twitIE;
	
	public StreamReaderService(TweetDAO tweetSaver, TwitterStreamBuilderUtil streamBuilder, TwitIE twitie) {
		this.tweetSaver = tweetSaver;
		this.streamBuilder = streamBuilder;
		stream = this.streamBuilder.getStream();
		this.collectionName = "tweets";
		this.twitIE = twitie;
	}

	// @PostConstruct 	// same as init-method in .xml but with annotations
	public void run() throws IOException {
		//twitIE.init();

		System.out.println("\n\n\n");
		//readTwitterFeed();

		System.out.println("\n\n\n");
	}

	/**
	 * Just for testing
	 * */
	public static void main(String[] args) throws IOException {
		//readTwitterFeed();
	}

	public void readTwitterFeed() throws IOException {

		RawStreamListener raw = new RawStreamListener() {

			@Override
			public void onException(Exception ex) {
				System.out.println("error");
			}

			@SuppressWarnings("static-access")
			@Override
			public void onMessage(String rawString) {

				//TODO: extract this 
				DBObject ob = (DBObject) JSON.parse(rawString);
				HashMap<String, ArrayList<String>> NEs = null;
				try {

					NEs = twitIE.processString((String)ob.get("text"));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (!NEs.isEmpty()) {
				for (String s: NEs.keySet()) {
					ob.put(s, NEs.get(s));
					rawString = ob.toString();
				}
				}
				tweetSaver.addTweet(rawString,collectionName);

			}
		};

		/*
		 * Unneeded. Kept just for reference
		 * */
		/*		StatusListener listener = new StatusListener() {

			@Override
			public void onException(Exception ex) {
				// TODO Auto-generated method stub
				System.out.println("Exception occured:" + ex.getMessage());
				ex.printStackTrace();
			}

			@Override
			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
				// TODO Auto-generated method stub
				System.out.println("Track limitation notice for " + numberOfLimitedStatuses);

			}

			@Override
			public void onStatus(Status status) {
				// TODO Auto-generated method stub
				String message = "Tweet:" + status.getText() + "\tLoc:" + status.getGeoLocation();
				//System.out.println(message);

			}

			@Override
			public void onStallWarning(StallWarning warning) {
				// TODO Auto-generated method stub
				System.out.println("Stall worning");

			}

			@Override
			public void onScrubGeo(long userId, long upToStatusId) {
				// TODO Auto-generated method stub
				System.out.println("Scrub geo with " + userId + ":" + upToStatusId);
			}

			@Override
			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
				// TODO Auto-generated method stub
				System.out.println("Status deletion notice");
			}
		};*/

		double[][] locations = new double[][] { {  -4.508147d, 55.812753d }, {  -4.037108d,55.965241d} };

		stream.addListener(raw);

		FilterQuery fq = new FilterQuery();
		fq.locations(locations);

		stream.filter(fq);
	}
}
