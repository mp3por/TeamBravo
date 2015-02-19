package glasgow.teamproject.teamB.mongodb.main;

import glasgow.teamproject.teamB.mongodb.dao.TweetDAO;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAOImpl;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.fasterxml.jackson.core.sym.Name;
import com.mongodb.MongoClient;

public class UpdateTest {

	public static final String DB_NAME = "tweetsTest";
	public static final String TWEETS_COLLECTION = "tweetsfrommainclass";
	public static final String MONGO_HOST = "localhost";
	public static final int MONGO_PORT = 27017;

	public static void main(String[] args) {
		try {
			MongoClient mongo = new MongoClient(MONGO_HOST, MONGO_PORT);
			MongoOperations mongoOps = new MongoTemplate(mongo, DB_NAME);
			TweetDAO tweetSaver = new TweetDAOImpl(mongoOps);
			
			String id = "557566105297100800";
			Map<String, String> NamedEntities = new HashMap<String, String>();
			NamedEntities.put("OMG", "Sofia");
			
			
			boolean omg = tweetSaver.addNamedEntitiesById(id, TWEETS_COLLECTION, NamedEntities);
			
			System.out.println("OK?: " + omg);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
