package glasgow.teamproject.teamB.Search;

import glasgow.teamproject.teamB.Util.StreamReaderService;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.terrier.indexing.Document;
import org.terrier.indexing.TwitterJSONDocument;

/**
 * A TweetsIndexer object will take a Terrier collection object, then produce
 * index for the given collection. The index it returns is then fed to a
 * TweetsRetriver object for querying.
 * 
 * @author vincentfung13
 *
 */

@Component
public class TweetsIndexer implements Observer {
	/* Shall be changed to TwitterMongoDBCollection shortly */
	//	private TwitterJSONCollection tweets;

	@Autowired
	private TwitterMongoDAOCollection tweets;

//	@Autowired
//	private TerrierInitializer terrier;
	
	@Autowired
	private SearchMemoryIndex index;
	
	@Autowired
	private StreamReaderService serv;

	public TwitterMongoDAOCollection getTweets() {
		return this.tweets;
	}

	public SearchMemoryIndex getIndex() {
		return this.index;
	}

	
	/**
	 * To be called from TweeterStreaming
	 * */
	public void indexTweet(String tweet) {
		try {
			index.indexDocument(new TwitterJSONDocument(tweet));
		} catch (Exception e) {
			System.err.println("Failed to index tweet:" + tweet);
		}
	}

	@PostConstruct
	private void init() {
//		this.index = terrier.getMemoryIndex();
		serv.addObserver(this);
		indexTweets();
	}

	public void indexTweets() {
		System.out.println("Start indexing the tweets");
		while (tweets.nextDocument()) {
			Document tweet = tweets.getDocument();
			try {
				index.indexDocument(tweet);
			} catch (Exception e) {
				System.err.println("Failed to index tweets");
			}
		}
		try {
			tweets.close();
		} catch (IOException e) {
			System.err.println("Failed to close collection");
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		indexTweet((String) arg);
	}

}
