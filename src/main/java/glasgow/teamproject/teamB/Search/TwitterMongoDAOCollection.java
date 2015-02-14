package glasgow.teamproject.teamB.Search;

/**
 * An abstraction of all the tweets stored in the database.
 */

import glasgow.teamproject.teamB.mongodb.dao.TweetDAO;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAOImpl;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.ArrayBlockingQueue;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.terrier.indexing.Collection;
import org.terrier.indexing.Document;
import org.terrier.indexing.TwitterJSONDocument;

import com.mongodb.MongoClient;

public class TwitterMongoDAOCollection implements Collection{
	public static final String DB_NAME = "tweetsTest";
	public static final String MONGO_HOST = "localhost";
	public static final int MONGO_PORT = 27017;
	public static final String TWEETS_COLLECTION = "tweets";

	@Autowired
	protected TweetDAO tweetSaver;
	/** logger for this class */	
	protected static final Logger logger = Logger.getLogger(TwitterMongoDAOCollection.class);
	protected ArrayBlockingQueue<String> tweets;
	/** The current document */
	protected Document currentDocument;
	
	public TwitterMongoDAOCollection(){
		/* Initialize the cursor for the database
		   This is where it connects to the database, from this point on the cursor access the document stored in it.
		   */	
		try {
			MongoClient mc = new MongoClient(MONGO_HOST, MONGO_PORT);
			MongoOperations mongoOps = new MongoTemplate(mc, DB_NAME);
			TweetDAO tweetSaver = new TweetDAOImpl(mongoOps);
			this.tweets = tweetSaver.getTweetsQueue(TWEETS_COLLECTION);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		this.currentDocument = null;
//		this.tweets = tweetSaver.getTweetsQueue("tweets");
	}

	@Override
	public void close() throws IOException {
	}

	@Override
	public boolean nextDocument() {
		if(!tweets.isEmpty()){
			currentDocument = new TwitterJSONDocument(readTweet());
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	public Document getDocument() {
		return currentDocument;
	}
	
	public String readTweet(){
		String tweet = tweets.poll();
//		System.out.println(tweet);
		return tweet;
	}

	@Override
	public boolean endOfCollection() {
		return this.tweets.isEmpty();
	}

	@Override
	public void reset() {
		logger.error("WARN: TwitterMongoDBCollection.reset() was called but it has not been implemented.");		
	}
	
	public ArrayBlockingQueue<String> getAllTweets(){
		return this.tweets;
	}

}
