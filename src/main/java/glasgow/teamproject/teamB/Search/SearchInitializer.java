package glasgow.teamproject.teamB.Search;

import java.net.UnknownHostException;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.terrier.matching.ResultSet;
import org.terrier.utility.ApplicationSetup;

import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

/**
 * This class provides a main method to configure Terrier and initialize the
 * search.
 * 
 * @author vincentfung13
 *
 */

public class SearchInitializer {

	public static final String DB_NAME = "tweetsTest";
	public static final String TWEETS_COLLECTION = "tweets";
	public static final String MONGO_HOST = "localhost";
	public static final int MONGO_PORT = 27017;

	public static void main(String[] args) {
		String query = "Taylor Swift";
		// To be configured
//		ApplicationSetup.setProperty("stopwords.filename", "/Users/vincentfung13/Development/TP3/terrier-4.0-win/share/stopword-list.txt");
		
		MongoClient mongo;
		try {
			mongo = new MongoClient(MONGO_HOST, MONGO_PORT);
			MongoOperations mongoOps = new MongoTemplate(mongo, DB_NAME);
			
			TweetsIndexer indexer = new TweetsIndexer(mongoOps);
			indexer.indexTweets();
			
			TweetsRetriver retriver = new TweetsRetriver(indexer.getIndex(), query);
			retriver.runQuery();
			ResultSet result = retriver.getResult();
			result.sort();
			int[] resultDocnos = result.getDocids();
			System.out.println("Returned " + resultDocnos.length + " tweets");
			
			/* Print out the result */
			DBCursor cursor = mongoOps.getCollection("tweets").find();
			cursor.next();
			int j = 0;
			for(int i = 0; i < resultDocnos.length; i++){
				while(j != resultDocnos[i]){
					cursor.next();
					j++;
				}
				System.out.print(cursor.curr().get("text").toString());
			}
			
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

	}
}
