package glasgow.teamproject.teamB.mongodb.dao;

import glasgow.teamproject.teamB.Counter.Counter;
import glasgow.teamproject.teamB.Counter.Counter.TimePeriod;
import glasgow.teamproject.teamB.Util.ProjectProperties;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
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
import com.mongodb.QueryBuilder;
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
		// System.out.println("OB:" + ob.toString());
		DBCollection dbCollection = mongoOps.getCollection(collectionName); // gets
																			// collection
		dbCollection.insert(ob);// stores the JSON

		// Simple store as String
		mongoOps.insert(tweet, collectionName + "STRING"); // stores the tweet
															// as string

		System.out.println("SAVE");
	}

	@Override
	public String readByTime(String time, String collectionName) {
		Query query = new Query(Criteria.where("timestamp_ms").is(time));
		String tweet = this.mongoOps.findOne(query, String.class,
				collectionName);
		return tweet;
	}

	@Override
	public List<String> getTweetsForMaps(String collectionName) {
		List<String> results = mongoOps.find(new Query(), String.class,
				collectionName);
		return results;
	}

	@Override
	public boolean addNamedEntitiesById(String id, String collectionName,
			Map<String, String> NamedEntities) {

		// just for referance what is going on
		/*
		 * // gets the collection in which is the entry you will modify
		 * DBCollection dbCollection = mongoOps.getCollection(collectionName);
		 * // used to get the specific tweet DBObject query = new
		 * BasicDBObject("id_str",id); // to hold the named Entities DBObject
		 * namedEntitiesList = new BasicDBObject(NamedEntities); // this will be
		 * added to the entry DBObject updateObject = new
		 * BasicDBObject("named_entities",namedEntitiesList); // the actual
		 * updates WriteResult result = dbCollection.update(query,new
		 * BasicDBObject("$push",updateObject)); // isUpdateOfExisting will
		 * return true if an existing entry was updated return
		 * result.isUpdateOfExisting();
		 */

		Query query = new Query(Criteria.where("id_str").is(id));
		Update update = new Update();
		update.push("named_entities", NamedEntities);

		WriteResult result = mongoOps
				.updateFirst(query, update, collectionName);

		return result.isUpdateOfExisting();
	}

	@Override
	public ArrayList<HashMap<String, Object>> getLastTweets(int count,
			String collectionName) {

		DBCollection dbCollection = mongoOps.getCollection(collectionName);

		DBCursor dbCursor = dbCollection.find().sort(
				new BasicDBObject("timestamp_ms", -1));
		dbCursor.next(); // this is needed as the first element is empty! Please
							// do not touch this again.
		ArrayList<HashMap<String, Object>> tweets = new ArrayList<>();
		int i = 0;
		// parsing gets complicated!
		while (dbCursor.hasNext() && i < count) {
			BasicDBObject currentObj = (BasicDBObject) dbCursor.next();
			HashMap<String, Object> tweet = new HashMap<>();
			for (String key : currentObj.keySet()) {
				if (ProjectProperties.defaultNE.contains(key)) {
					String s = currentObj.getString(key);
					// if s contains only "[]", return null - no need to parse
					// an empty array
					if (s.length() == 2) {
						tweet.put(key, null);
						continue;
					}

					s = s.replace("[", "");
					s = s.replace("]", "");

					HashSet<String> NEs = new HashSet<String>();

					for (String NE : s.split(",")) {
						NEs.add(NE);
					}
					tweet.put(key, NEs);
				} else {
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
		ArrayBlockingQueue<String> tweets = new ArrayBlockingQueue<String>(
				foo.size());
		foo.next();
		while (foo.hasNext()) {
			tweets.add(foo.next().toString());
		}
		return tweets;
	}

	// For Terrier Retriving
	@Override
	public BasicDBObject getNthEntry(String collectionName, int n) {
		DBCollection dbCollection = mongoOps.getCollection(collectionName);
		DBCursor foo = dbCollection.find();
		foo.skip(n + 1);
		return (BasicDBObject) foo.next();
	}

	@Override
	public ArrayList<Tweet> getResultList(int[] resultsDocids) {
		ArrayList<Tweet> list = new ArrayList<>();
		BasicDBObject currentObj;
		for (int i = 0; i < resultsDocids.length; i++) {
			currentObj = getNthEntry("tweets", resultsDocids[i]);
			list.add(new Tweet(currentObj));
		}
		return list;
	}

	@Override
	public ArrayList<Tweet> getRankedResultList(int[] resultsDocids) {
		ArrayList<Tweet> list = new ArrayList<>();
		BasicDBObject currentObj;
		for (int i = 0; i < resultsDocids.length; i++) {
			currentObj = getNthEntry("tweets", resultsDocids[i]);
			list.add(new Tweet(currentObj));
		}
		list.sort(Tweet.RetweetCountComparator);
		return list;
	}

	@Override
	public ArrayList<HashMap<String, Object>> getTerrierResults(
			ArrayList<Tweet> tweets) {
		ArrayList<HashMap<String, Object>> results = new ArrayList<>();
		BasicDBObject currentObj;
		for (int i = 0; i < tweets.size(); i++) {
			currentObj = tweets.get(i).getTweet();
			HashMap<String, Object> tweet = new HashMap<>();
			for (String key : currentObj.keySet()) {
				if (ProjectProperties.defaultNE.contains(key)) {
					String s = currentObj.getString(key);
					if (s.length() == 2) {
						tweet.put(key, null);
						continue;
					}

					s = s.replace("[", "");
					s = s.replace("]", "");

					HashSet<String> NEs = new HashSet<String>();

					for (String NE : s.split(",")) {
						NEs.add(NE);
					}
					tweet.put(key, NEs);
				} else {
					tweet.put(key, currentObj.get(key));
				}
			}
			results.add(tweet);
		}
		return results;
	}

	@Override
	public Queue<String> getCollection(String string) {
		List<String> o = this.mongoOps.find(new Query(), String.class);
		Queue<String> j = new PriorityQueue<String>();
		for (String p : o) {
			j.add(p);
		}
		return j;
	}

	// For statistics
	/**
	 * 
	 * @param stDate
	 *            : start date, if counting tweets from the beginning of the
	 *            day, set hours, minutes, seconds and milliseconds to be 0
	 *            before parsing the variable to this method
	 * @param edDate
	 *            : end date, if counting tweets until the end of the day
	 *            (cannot be time in the future), set hours, minutes, seconds
	 *            and milliseconds to be 23, 59, 59, 999 in order before parsing
	 *            the variable to this method
	 * @return
	 */
	@Override
	public long getTweetCount(Date stDate, Date edDate, boolean isRetweeted) {
		// TODO remove magic string
		DBCollection tweets = mongoOps.getCollection("tweets");

		DBObject query = QueryBuilder.start().put("timestamp_ms")
				.greaterThanEquals(Long.toString(stDate.getTime()))
				.lessThanEquals(Long.toString(edDate.getTime()))
				.put("retweeted").is(isRetweeted).get();

		return tweets.find(query).count();
	}

	@Override
	public HashMap<String, Object> getMostPopularTweet(Date stDate,
			Date edDate, String compareKey) {
		// TODO remove magic string
		DBCollection tweets = mongoOps.getCollection("tweets");

		DBObject query = QueryBuilder.start().put("timestamp_ms")
				.greaterThanEquals(Long.toString(stDate.getTime()))
				.lessThanEquals(Long.toString(edDate.getTime())).get();

		DBCursor cursor = tweets.find(query)
				.sort(new BasicDBObject(compareKey, -1)).limit(1);
		if (!cursor.hasNext())
			return null;
		DBObject obj = cursor.next();
		HashMap<String, Object> tweet = new HashMap<>();
		tweet.put("text", obj.get("text"));
		DBObject user = (DBObject) obj.get("user");
		tweet.put("user", user.get("screen_name"));
		
		return tweet;
	}

	//@Override						 
	public String getMostPopularEntity() {

		// TODO this is the same as Counter's getTopEntities method

		return null;
	}
}
