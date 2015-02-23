package glasgow.teamproject.teamB.mongodb.dao;

import glasgow.teamproject.teamB.Search.Tweet;
import glasgow.teamproject.teamB.Util.ProjectProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

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

	public TweetDAOImpl() {
		
	}
	
	public TweetDAOImpl(MongoOperations mongoOps) {
		this.mongoOps = mongoOps;
	}

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
	
	/* { "type" : "Point" , "coordinates" : [ -4.292994 , 55.874865]} */
	private double[] getCoordinate(DBObject tweet){	
		
		double[] coordinate = new double[2];
//		Map<String, Object> map = tweet.getTweet();
//		System.err.println(map);
		if (tweet.get("coordinates") != null){
			String pairString = tweet.get("coordinates").toString();
//			System.err.println(pairString);
		
			int startOfCoordinate = pairString.lastIndexOf('[') + 2;
			int comma = pairString.lastIndexOf(',');
			int endOfCoordinate = pairString.lastIndexOf(']') - 1;
		
			String latitude = pairString.substring(startOfCoordinate, comma - 1);
			String longtitude = pairString.substring(comma + 2, endOfCoordinate + 1);
		
		
			coordinate[0] = Double.parseDouble(latitude);
			coordinate[1] = Double.parseDouble(longtitude);
		
		
			return coordinate;
		}
		else return null;
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
	public ArrayList<HashMap<String,Object>> getLastTweets(int count, String collectionName) {

		DBCollection dbCollection = mongoOps.getCollection(collectionName);

		DBCursor dbCursor = dbCollection.find().sort(new BasicDBObject("timestamp_ms", -1));
		dbCursor.next(); // this is needed as the first element is empty! Please do not touch this again. 
		ArrayList<HashMap<String,Object>> tweets = new ArrayList<>(); 
		int i = 0;
		// parsing gets complicated!
		while(dbCursor.hasNext() && i<count){
			BasicDBObject currentObj = (BasicDBObject) dbCursor.next();
			HashMap<String, Object> tweet = new HashMap<>();
			for (String key: currentObj.keySet()) {
				if (ProjectProperties.defaultNE.contains(key)) {
					String s = currentObj.getString(key);
					// if s contains only "[]", return null - no need to parse an empty array
					if (s.length() == 2) {
						tweet.put(key, null);
						continue;
					}

					s = s.replace("[", "");
					s = s.replace("]", "");

					HashSet<String> NEs = new HashSet<String>();

					for (String NE: s.split(",")) {
						NEs.add(NE);
					}
					tweet.put(key, NEs);
				}
				else {
					tweet.put(key, currentObj.get(key));
				}
			}
			tweets.add(tweet);
			i++;
			dbCursor.next();
		}
		return tweets;
	}

	// For Terrier indexing
	@Override
	public ArrayBlockingQueue<String> getTweetsQueue(String collectionName) {
		DBCollection dbCollection = mongoOps.getCollection(collectionName);
		DBCursor foo = dbCollection.find();
		ArrayBlockingQueue<String> tweets = new ArrayBlockingQueue<String>(foo.size());
		foo.next();
		while(foo.hasNext()){
			tweets.add(foo.next().toString());
		}
		return tweets;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Tweet> getResultsList(String collectionName, int[] resultsDocids) {
		
		DBCollection dbCollection = mongoOps.getCollection(collectionName);
		DBCursor foo = dbCollection.find();
		foo.next();
		
		ArrayList<Tweet> collectionList = new ArrayList<>();
		while(foo.hasNext())
			collectionList.add(new Tweet(foo.next().toMap()));			
		
		ArrayList<Tweet> resultsList = new ArrayList<>();
		Tweet tweet;
		for (int i = 0; i < resultsDocids.length; i++){
			tweet = collectionList.get(resultsDocids[i]);
			resultsList.add(tweet);
		}
		
		return resultsList;
	}

	@Override
	public Queue<String> getCollection(String string) {
		List<String> o = this.mongoOps.find(new Query(), String.class);
		Queue<String> j = new PriorityQueue<String>();
		for (String p : o){
			j.add(p);
		}
		return j;
	}

	@Override
	public Set<String> getTweetsForId(int[] ids) {
		// TODO Auto-generated method stub
		return null;
	}
}
