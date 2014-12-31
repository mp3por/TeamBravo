package glasgow.teamproject.teamB.mongodb.main;

import glasgow.teamproject.teamB.TwitterStreaming.TwitterStreamBuilderUtil;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAO;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAOImpl;

import java.net.UnknownHostException;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

import twitter4j.TwitterStream;

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
			TwitterStream stream = TwitterStreamBuilderUtil.getStream();
			TweetDAO tweetSaver = new TweetDAOImpl(mongoOps);
			
			// to work uncomment code in TweetDAOImpl
			String omg = tweetSaver.readByTime("1420037773402", TWEETS_COLLECTION); // extract tweet which was stored as JSON
			String omg2 = tweetSaver.readByTime("1420037773402", TWEETS_COLLECTION+"2"); // extract tweet which was stored as String 
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
