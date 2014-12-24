package glasgow.teamproject.teamB.TwitterStreaming;

import glasgow.teamproject.teamB.mongodb.dao.TweetDAO;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAOImpl;

import java.io.IOException;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.MongoClient;

import twitter4j.FilterQuery;
import twitter4j.RawStreamListener;
import twitter4j.TwitterStream;

public class StreamReaderService {
	
	public static final String DB_NAME = "tweetsTest";
	public static final String MONGO_HOST = "localhost";
	public static final int MONGO_PORT = 27017;
	private final TweetDAO tweetSaver;
	
	
	public StreamReaderService(TweetDAO tweetSaver) {
		this.tweetSaver = tweetSaver;
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
		TwitterStream stream = TwitterStreamBuilderUtil.getStream();
		
		/*MongoClient mongo = new MongoClient(MONGO_HOST, MONGO_PORT);
		MongoOperations mongoOps = new MongoTemplate(mongo, DB_NAME);
		TweetDAOImpl tweetSaver = new TweetDAOImpl(mongoOps);
		*/
		RawStreamListener raw = new RawStreamListener() {

			@Override
			public void onException(Exception ex) {
				// TODO Auto-generated method stub
				System.out.println("error");
			}

			@Override
			public void onMessage(String rawString) {
				// TODO Auto-generated method stub
				//System.out.println("RawTweet:" + rawString);
				System.out.println("SAVE");
				tweetSaver.addTweet(rawString);
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
