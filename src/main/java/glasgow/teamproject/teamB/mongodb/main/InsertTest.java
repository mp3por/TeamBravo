package glasgow.teamproject.teamB.mongodb.main;

import glasgow.teamproject.teamB.TwitterStreaming.TwitterStreamBuilderUtil;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAO;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAOImpl;

import java.net.UnknownHostException;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

import twitter4j.FilterQuery;
import twitter4j.RawStreamListener;
import twitter4j.TwitterStream;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

public class InsertTest {

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

			RawStreamListener raw = new RawStreamListener() {
				int p = 0;

				@Override
				public void onException(Exception ex) {
					System.out.println("error");
				}

				@Override
				public void onMessage(String rawString) {
					tweetSaver.addTweet(rawString, TWEETS_COLLECTION);
					p++;
					if (p == 2) {
						stream.shutdown();
						mongo.close();
						System.out.println("CLOSED");
					}
				}
			};
			FilterQuery qry = new FilterQuery();
			double[][] locations = new double[][] { { 55.812753d, -4.508147d },
					{ 55.965241d, -4.037108d } };
			qry.locations(locations);
			stream.addListener(raw);
			String[] keywordsArray = { "Glasgow" };
			FilterQuery fq = new FilterQuery(0, null, keywordsArray, locations);
			stream.filter(fq);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
