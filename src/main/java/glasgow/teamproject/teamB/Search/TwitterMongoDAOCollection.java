package glasgow.teamproject.teamB.Search;

/**
 * An abstraction of all the tweets stored in the database.
 */

import glasgow.teamproject.teamB.Util.ProjectProperties;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAO;
//import glasgow.teamproject.teamB.mongodb.dao.TweetDAOImpl;

import java.io.IOException;
//import java.net.UnknownHostException;
import java.util.concurrent.ArrayBlockingQueue;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.MongoOperations;
//import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.terrier.indexing.Collection;
import org.terrier.indexing.Document;
import org.terrier.indexing.TwitterJSONDocument;

//import com.mongodb.MongoClient;

@Component
public class TwitterMongoDAOCollection implements Collection{

	@Autowired
	private TweetDAO tweetSaver;
	
	/** logger for this class */	
	protected static final Logger logger = Logger.getLogger(TwitterMongoDAOCollection.class);
	
	protected ArrayBlockingQueue<String> tweets;
	
	/** The current document */
	protected Document currentDocument;
	
	@PostConstruct
	private void init(){
		this.tweets = tweetSaver.getTweetsQueue(ProjectProperties.TWEET_COLLECTION);
		this.currentDocument = null;
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
