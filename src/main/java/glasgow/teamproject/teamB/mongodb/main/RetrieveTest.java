package glasgow.teamproject.teamB.mongodb.main;

import glasgow.teamproject.teamB.mongodb.dao.TweetDAO;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAOImpl;

import java.net.UnknownHostException;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.MongoClient;

public class RetrieveTest {
	public static final String DB_NAME = "tweetsTest";
	public static final String TWEETS_COLLECTION = "tweetsfrommainclass";
	public static final String MONGO_HOST = "localhost";
	public static final int MONGO_PORT = 27017;

	public static void main(String[] args) {
		try {
			MongoClient mongo = new MongoClient(MONGO_HOST, MONGO_PORT);
			MongoOperations mongoOps = new MongoTemplate(mongo, DB_NAME);
			TweetDAO tweetSaver = new TweetDAOImpl(mongoOps);
			
			// to work uncomment code in TweetDAOImpl
			String omg = tweetSaver.readByTime("1421509145513", TWEETS_COLLECTION); // extract tweet which was stored as JSON
			String omg2 = tweetSaver.readByTime("1421509145513", TWEETS_COLLECTION+"STRING"); // extract tweet which was stored as String 
			
			
			System.out.println("END");
			System.out.println(omg + "\n\n\n\n" + omg2);
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
