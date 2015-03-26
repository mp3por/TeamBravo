package glasgow.teamproject.teamB.Search;

import glasgow.teamproject.teamB.Util.ProjectProperties;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAO;

import java.io.IOException;
import java.util.LinkedList;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.terrier.indexing.Collection;
import org.terrier.indexing.Document;

/**
 * A TwitterMongoDAOCollection object is an abstraction of all the tweets stored in the master collection of the database.
 */

@Component
public class TwitterMongoDAOCollection implements Collection{

	/*
	 * The database layer.
	 */
	@Autowired
	private TweetDAO tweetSaver;
	
	/** logger for this class */	
	protected static final Logger logger = Logger.getLogger(TwitterMongoDAOCollection.class);
	
	/*
	 * A linked list is used to store all the tweets just in case there are too many of them.
	 */
	protected LinkedList<String> tweets;
	
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
	
	public LinkedList<String> getAllTweets(){
		return this.tweets;
	}

}
