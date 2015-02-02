package glasgow.teamproject.teamB.mongodb.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;

@Component
public class TweetDAOImpl implements TweetDAO {

	@Autowired
	private MongoOperations mongoOps;

	//	public TweetDAOImpl(MongoOperations mongoOps) {
	//		this.mongoOps = mongoOps;
	//	}

	@Override
	public void addTweet(String tweet, String collectionName) {
		tweet = tweet.replace("\"id\":", "\"tweet_id\":");
		System.out.println(tweet);

		// If we ever need to store it as JSON object
		// Done in order to save the JSON object efficiently
		DBObject ob = (DBObject) JSON.parse(tweet);
		//System.out.println("OB:" + ob.toString());
		DBCollection dbCollection = mongoOps.getCollection(collectionName); // gets collection
		dbCollection.insert(ob);// stores the JSON

		// Simple store as String
		mongoOps.insert(tweet, collectionName + "STRING"); // stores the tweet as string

		System.out.println("SAVE");
	}

	@Override
	public String readByTime(String time, String collectionName) {
		Query query = new Query(Criteria.where("timestamp_ms").is(time));
		String tweet = this.mongoOps.findOne(query, String.class, collectionName);
		return tweet;
	}

	@Override
	public List<String> getTweetsForMaps(String collectionName) {
		List<String> results = mongoOps.find(new Query(), String.class, collectionName);
		return results;
	}

	@Override
	public boolean addNamedEntitiesById(String id, String collectionName, Map<String, String> NamedEntities) {

		// just for referance what is going on
		/*// gets the collection in which is the entry you will modify
		DBCollection dbCollection = mongoOps.getCollection(collectionName);
		// used to get the specific tweet
		DBObject query = new BasicDBObject("id_str",id);
		// to hold the named Entities
		DBObject namedEntitiesList = new BasicDBObject(NamedEntities);
		// this will be added to the entry
		DBObject updateObject = new BasicDBObject("named_entities",namedEntitiesList);
		// the actual updates
		WriteResult result = dbCollection.update(query,new BasicDBObject("$push",updateObject));
		// isUpdateOfExisting will return true if an existing entry was updated
		return result.isUpdateOfExisting();*/

		Query query = new Query(Criteria.where("id_str").is(id));
		Update update = new Update();
		update.push("named_entities", NamedEntities);

		WriteResult result = mongoOps.updateFirst(query, update, collectionName);

		return result.isUpdateOfExisting();
	}

	@Override
	public ArrayList<JSONObject> getLastTweets(int count, String collectionName) {

		DBCollection dbCollection = mongoOps.getCollection(collectionName);

		DBCursor dbCursor = dbCollection.find().sort(new BasicDBObject("id", -1));
		//dbCursor.next(); ? why do you do this 
		ArrayList<JSONObject> tweets = new ArrayList<JSONObject>(); 
		int i = 0;
		while(dbCursor.hasNext() && i<count){
			tweets.add(new JSONObject(dbCursor.next()));
		}
		
		// WTF:D:D:D
//		for (int i = 0; i < count; i++) {
//			if (dbCursor.curr() == null)
//				continue; ??? i think it should be break ? why would you continue 
//			tweets.add(new JSONObject(source).curr());
//			if (dbCursor.hasNext())
//				dbCursor.next();
//		}
		return tweets;
	}

}
