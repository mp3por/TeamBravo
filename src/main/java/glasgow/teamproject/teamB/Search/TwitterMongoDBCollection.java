package glasgow.teamproject.teamB.Search;

/**
 * An abstraction of all the tweets stored in the database.
 */

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.MongoOperations;
import org.terrier.indexing.Collection;
import org.terrier.indexing.Document;
import org.terrier.indexing.TwitterJSONDocument;
import com.mongodb.DBCursor;

public class TwitterMongoDBCollection implements Collection{
	
	/** logger for this class */	
	protected static final Logger logger = Logger.getLogger(TwitterMongoDBCollection.class);
	/** The iterator through the database */
	protected DBCursor cursor = null;
	protected MongoOperations mongoOps;
	/** The current document */
	protected Document currentDocument = null;
	
	public TwitterMongoDBCollection(MongoOperations mongoOps){
		/* Initialize the cursor for the database
		   This is where it connects to the database, from this point on the cursor access the document stored in it.
		   */
		this.mongoOps = mongoOps;
		this.cursor = this.mongoOps.getCollection("tweets").find();
		System.out.println("Found "+ cursor.count() + "tweets inside the database");
	}

	@Override
	public void close() throws IOException {
		if(cursor != null) cursor.close();
	}

	@Override
	public boolean nextDocument() {
		if(cursor.hasNext()){
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
		String tweet = cursor.next().toString();
		System.out.println(tweet);
		return tweet;
	}

	@Override
	public boolean endOfCollection() {
		return !this.cursor.hasNext();
	}

	@Override
	public void reset() {
		logger.error("WARN: TwitterMongoDBCollection.reset() was called but it has not been implemented.");		
	}

}
