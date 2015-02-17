package glasgow.teamproject.teamB.Search;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.terrier.indexing.Document;
import org.terrier.indexing.TwitterJSONDocument;
import org.terrier.realtime.memory.MemoryIndex;
import org.terrier.utility.ApplicationSetup;

/**
 * A TweetsIndexer object will take a Terrier collection object, then produce
 * index for the given collection. The index it returns is then fed to a
 * TweetsRetriver object for querying.
 * 
 * @author vincentfung13
 *
 */

@Component
public class TweetsIndexer {
	/* Shall be changed to TwitterMongoDBCollection shortly */
	//	private TwitterJSONCollection tweets;

	@Autowired
	private TwitterMongoDAOCollection tweets;

	@Autowired
	private TerrierInitializer terrier;
	
	private SearchMemoryIndex index;

	public TwitterMongoDAOCollection getTweets() {
		return this.tweets;
	}

	public SearchMemoryIndex getIndex() {
		return this.index;
	}

	public void indexTweet(String tweet) {
		try {
			index.indexDocument(new TwitterJSONDocument(tweet));
		} catch (Exception e) {
			System.err.println("Failed to index tweet:" + tweet);
		}
	}

	@PostConstruct
	private void init() {
		this.index = terrier.getMemoryIndex();
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

}
