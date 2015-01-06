package glasgow.teamproject.teamB.mongodb.dao;

import java.util.List;

import org.json.JSONObject;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
public class TweetDAOImpl implements TweetDAO {
	
	private MongoOperations mongoOps;
	
    public TweetDAOImpl(MongoOperations mongoOps) {
		this.mongoOps = mongoOps;
	}
    
	@Override
	public void addTweet(String tweet, String collectionName) {
		System.out.println(tweet);
		//If we ever need to store it as JSON object
		/*// Done in order to save the JSON object efficiently
		DBObject ob = (DBObject) JSON.parse(tweet);
		//System.out.println("OB:" + ob.toString());
		DBCollection dbCollection = mongoOps.getCollection(collectionName); // gets collection
		dbCollection.insert(ob);*/
		
		mongoOps.insert(tweet,collectionName); // stores the tweet as string  
		System.out.println("SAVE");
	}

	@Override
	public String readByTime(String time, String collectionName) {
		Query querry = new Query(Criteria.where("timestamp_ms").is(time));
		String tweet = this.mongoOps.findOne(querry,String.class,collectionName);
		return tweet;
	}
	
	public List<String> getTweetsForMaps(String collectionName){
		List<String> resutls = mongoOps.find(new Query(), String.class, collectionName);
		return resutls;
	}

}
