package glasgow.teamproject.teamB.Search;

/*
import gnu.trove.TLongHashSet;
*/

/**
 * An abstraction of the tweets collection stored in the database.
 * confused: 1. How to "locate" the specific collection?
 * 	         2. How to iterate through the database?
 * 			 3. How is each tweet stored in the database? (as JSON object?)
 */

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.MongoOperations;
import org.terrier.indexing.Collection;
import org.terrier.indexing.Document;
import org.terrier.indexing.TwitterJSONDocument;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class TwitterMongoDBCollection implements Collection{
	
	/** logger for this class */	
	protected static final Logger logger = Logger.getLogger(TwitterMongoDBCollection.class);
	/** The JSON stream containing the tweets */
	/** The iterator through the database */
	protected DBCursor cursor = null;
	/** The current database object to work with */
	protected DBObject db = null;
	protected MongoOperations mongoOps;
	/** The current document */
	protected Document currentDocument = null;
	protected int numOfDocument;
	
//  What is this for?	
//	TLongHashSet alldocnos = new TLongHashSet(); 
	
	public TwitterMongoDBCollection(MongoOperations mongoOps){
		/* Initialize the cursor for the database
		   This is where it connects to the database, from this point on the cursor access the document stored in it.
		   How to locate the database from here? 
		   */
		this.mongoOps = mongoOps;
		this.cursor = this.mongoOps.getCollection("tweets").find();
		System.out.println("CURSOR COUNT : "+cursor.count());
		/* How is it stored? */
		this.currentDocument = null;
		numOfDocument = 0;
	}

	@Override
	public void close() throws IOException {
		if(cursor != null) cursor.close();
	}

	@Override
	public boolean nextDocument() {
		if(cursor.hasNext()){
			/* Are tweets stored as JSON objects in the database? */
			/* If in DBObject, need to write TwitterMongoDBDocument.java */
			currentDocument = new TwitterJSONDocument(readTweet());
			numOfDocument++;   
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
	
	public int getNumberOfTweets(){
		return this.numOfDocument;
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
