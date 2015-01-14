package glasgow.teamproject.teamB.TwitterStreaming;

import glasgow.teamproject.teamB.mongodb.dao.TweetDAO;

import java.io.IOException;

import com.mongodb.util.JSON;

import twitter4j.FilterQuery;
import twitter4j.RawStreamListener;
import twitter4j.TwitterStream;

public class StreamReaderService {
	
	private final TweetDAO tweetSaver;
	private final TwitterStreamBuilderUtil streamBuilder;
	private final TwitterStream stream;
	private final String collectionName;
	
	
	public StreamReaderService(TweetDAO tweetSaver, TwitterStreamBuilderUtil streamBuilder) {
		this.tweetSaver = tweetSaver;
		this.streamBuilder = streamBuilder;
		stream = this.streamBuilder.getStream();
		this.collectionName = "tweets";
	}
	
	// @PostConstruct 	// same as init-method in .xml but with annotations
	public void run() throws IOException {
		System.out.println("\n\n\n");

		readTwitterFeed();

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

			@Override
			public void onMessage(String rawString) {
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

		FilterQuery qry = new FilterQuery();

		double[][] locations = new double[][] { { 55.812753d, -4.508147d }, { 55.965241d, -4.037108d } };

		qry.locations(locations);

		//stream.addListener(listener);
		
		stream.addListener(raw);
		String[] keywordsArray = { "Glasgow" };

		FilterQuery fq = new FilterQuery(0, null, keywordsArray, locations);

		stream.filter(fq);
	}
}
