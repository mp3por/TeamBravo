package glasgow.teamproject.teamB.mongodb.dao;

import glasgow.teamproject.teamB.Graphs.TopicComparator;
import glasgow.teamproject.teamB.Graphs.TopicWrapper;
import glasgow.teamproject.teamB.Util.ProjectProperties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
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

	/*
	//For word cloud
	@Override
	public List<TopicWrapper> getHotTopics(int noOfTopics,
			String topicColumnName, String tweetColumnName,
			String collectionName) {
		
		List<TopicWrapper> hotTopics = null;
		List<DBObject> topicDBObjects;
		try {
			//Set up list of db objects
			topicDBObjects = mongoOps.find(new Query(), DBObject.class, collectionName);
			//Set up list for db objects wrappers
			hotTopics = new ArrayList<TopicWrapper>();
			//For every db object, wrap it in topic wrapper and add to list
			for (DBObject dbobj: topicDBObjects){
				TopicWrapper topic = new TopicWrapper(dbobj, topicColumnName, tweetColumnName);
				hotTopics.add(topic);
			}
			System.out.println("Topics before sort: " + hotTopics);
			//Sort the list of topics in descending order
			Collections.sort(hotTopics, new TopicComparator());
			System.out.println("Topics after sort: " + hotTopics);
			
			//Trim the list to the number of topics specified in noOfTopics
			int topicListSize = hotTopics.size();
			if ( topicListSize > noOfTopics )
			    hotTopics.subList(noOfTopics, topicListSize).clear();
			System.out.println("Topics after trim: " + hotTopics);
		} catch (Exception e) {
			System.err.println(collectionName + " does not exist");
		}
		
		return hotTopics;
	}*/
	
	/*
	//For graphWeek on main/specificTopic
	//Return a Json array for the amount of tweets made about a specific topic
	//By each day - Mon, Tues, Wed etc
	public JSONArray getSpecificTopicWeek (String collectionName){
		
		JSONArray tweetsForWeek = new JSONArray();

		return tweetsForWeek;
	}
	
	public JSONArray getSpecificTopicYear(String collectionName){
		
		JSONArray tweetsForYear = new JSONArray();
		return tweetsForYear;
	}*/
	
	
}
