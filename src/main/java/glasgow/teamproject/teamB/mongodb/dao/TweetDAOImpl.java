package glasgow.teamproject.teamB.mongodb.dao;

import org.springframework.data.mongodb.core.MongoOperations;

import com.mongodb.BasicDBList;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
public class TweetDAOImpl implements TweetDAO {
	
	private MongoOperations mongoOps;
	private int omg = 1;
    private static final String TWEET_COLLECTION = "tweets";
    
    public TweetDAOImpl(MongoOperations mongoOps) {
		this.mongoOps = mongoOps;
	}
    
	@Override
	public void addTweet(String tweet) {
		// Done in order to save the JSON object efficiently
		DBObject ob = (DBObject) JSON.parse(tweet);
		DBCollection collection = mongoOps.getCollection(TWEET_COLLECTION); // gets collection
		collection.insert(ob);
		
		//mongoOps.insert(tweet,TWEET_COLLECTION+"2");
		System.out.println("SAVE");
	}

}
