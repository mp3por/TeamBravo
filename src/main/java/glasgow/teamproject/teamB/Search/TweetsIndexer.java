package glasgow.teamproject.teamB.Search;

import java.io.IOException;

import org.springframework.data.mongodb.core.MongoOperations;
import org.terrier.indexing.Document;
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
	private TwitterMongoDBCollection tweets;
	/* Shall be changed to incremental index shortly */
	private MemoryIndex index;
	
	public TweetsIndexer(MongoOperations mongoOps){
		index = new MemoryIndex();
		tweets = new TwitterMongoDBCollection(mongoOps);
	}
	
	public TwitterMongoDBCollection getTweets(){
		return this.tweets;
	}
	
	public MemoryIndex getIndex(){
		return this.index;
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
//		System.out.println(tweets.getNumberOfTweets());
		try {
			tweets.close();
		} catch (IOException e) {
			System.err.println("Failed to close collection");
		}
	}

}
