package glasgow.teamproject.teamB.Search;

import java.io.IOException;

import org.terrier.indexing.Document;
import org.terrier.indexing.TwitterJSONDocument;
import org.terrier.realtime.memory.MemoryIndex;
/**
 * A TweetsIndexer object will take a Terrier collection object, then produce index for the given collection.
 * The index it returns is then fed to a TweetsRetriver object for querying.
 * @author vincentfung13
 *
 */

public class TweetsIndexer {
	/* Shall be changed to TwitterMongoDBCollection shortly */
//	private TwitterJSONCollection tweets;
	private TwitterMongoDAOCollection tweets;
	/* Shall be changed to incremental index shortly */
	private MemoryIndex index;
	
	public TweetsIndexer(){
		this.index = new MemoryIndex();
		this.tweets = new TwitterMongoDAOCollection();
	}
	
	public TwitterMongoDAOCollection getTweets(){
		return this.tweets;
	}
	
	public MemoryIndex getIndex(){
		return this.index;
	}
	
	public void indexTweet(String tweet){
		try {
			index.indexDocument(new TwitterJSONDocument(tweet));
		} catch (Exception e) {
			System.err.println("Failed to index tweet:" + tweet);
		}
	}
		
	public void indexTweets(){
		System.out.println("Start indexing the tweets");
		while(tweets.nextDocument()){
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
