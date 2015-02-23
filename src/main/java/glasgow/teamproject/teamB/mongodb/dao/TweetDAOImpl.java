package glasgow.teamproject.teamB.mongodb.dao;

import glasgow.teamproject.teamB.Util.ProjectProperties;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
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
	
	// ------------------------------------------------------------------------
	// Counting Section
	/** collection names */
	public static final String TWEETS_COLLECT = "tweets";
	public static final String DAILY_COLLECT_NAME = "DCNT";
	public static final String WEEKLY_COLLECT_NAME = "WCNT";
	public static final String MONTHLY_COLLECT_NAME = "MCNT";

	private static final String COUNTER_DATE_FORMAT = "yyyy-MM-dd";
	private static DateFormat counterDateFormat = new SimpleDateFormat(COUNTER_DATE_FORMAT);
	
	public enum Field {
		HASHTAG {
			public String toString() {
				return "Hashtag";
			}
		},
		ORG {
			public String toString() {
				return "Organization";
			}
		},
		PERSON {
			public String toString() {
				return "Person";
			}
		},
		USERID {
			public String toString() {
				return "UserID";
			}
		},
		URL {
			public String toString() {
				return "URL";
			}
		},
		LOCATION {
			public String toString() {
				return "Location";
			}
		},
		ALL {
			public String toString() {
				return "All";
			}
		}
	}

	public enum TimePeriod {
		PASTDAY, PASTWEEK, PASTMONTH, ALLTIME
	}

	/**
	 * This method is called timely during a day. outCollection is the same for
	 * a day, the output will be map-reduced value of tweets from the beginning
	 * of the day up to the current time (replace documents of the same date in
	 * the outCollection).
	 * 
	 * @param date
	 *            : tweet's created_at date
	 */
	public void dailyMapReduce(Date date) {

		DBCollection tweets = mongoOps.getCollection(TWEETS_COLLECT);

		// convert date to twitter created_at format
		String twitterFormatDateStr = new SimpleDateFormat("EEE MMM dd").format(date);
		// query tweets where created_at LIKE dateStr%
		BasicDBObject query = new BasicDBObject();
		query.put("created_at",
				java.util.regex.Pattern.compile(twitterFormatDateStr + ".*"));

		// create temporary collection for map-reduce
		DBCollection temp = mongoOps.getCollection("temp");
		DBCursor c = tweets.find(query);
		while (c.hasNext()) {
			temp.insert(c.next());
		}

		// map function
		String map = "function() {";
		int i = 0;
		for (Field field : Field.values()) {
			map += "var entity" + i + " = this." + field.toString() + ";"
					+ "if( entity" + i + " ) {" + "entity" + i + " = entity"
					+ i + ".toString().toLowerCase()" + ".split(\",\"); "
					+ "for ( var i = entity" + i
					+ ".length - 1; i >= 0; --i) {";

			if (field.toString() == Field.PERSON.toString()) {
				map += "entity" + i + "[i] = entity" + i + "[i]"
						+ ".replace(/[^a-zA-Z]/g, ' ');";
			} else if (field.toString() == Field.HASHTAG.toString()) {
				map += "entity" + i + "[i] = entity" + i + "[i]"
						+ ".replace(/[`~!@$%^&*()_|+\\-=?;:\'\".<>\\{\\}\\[\\]\\\\/]/gi, '');";
			} else if (field.toString() != Field.URL.toString()) {
				map += "entity" + i + "[i] = entity" + i + "[i]"
						+ ".replace(/[`~!@#$%^&*()_|+\\-=?;:\'\".<>\\{\\}\\[\\]\\\\/]/gi, '');";
			}

			map += "if ( entity" + i + "[i] && entity" + i
					+ "[i].trim().length > 0 && entity" + i + "[i] != '[]') { "
					+ "var values = { type: \"" + field.toString() + "\", "
					+ "count: 1 };" + "emit( { id: entity" + i
					+ "[i].trim(), date: \"" + counterDateFormat.format(date)
					+ "\"}, values);" + "}" + "}}";
		}
		map += "};";

		// reduce function
		String reduce = "function(key, values) {"
				+ "var outs = { type: \"type\", count: 0 };"
				+ "values.forEach( function(v) {" + "outs.type = v.type;"
				+ "outs.count += v.count;" + "});" + "return outs;" + "}";

		// run map-reduce on the temporary collection, the output is in the file
		// named outCollection
		MapReduceCommand cmd = new MapReduceCommand(temp, map, reduce,
				DAILY_COLLECT_NAME, MapReduceCommand.OutputType.MERGE, null);
		temp.mapReduce(cmd);

		// delete the temporary collection
		temp.drop();
	}
	
	/**
	 * This method is called once a day to calculate trends for the past
	 * week/month outCollection is always the same, one for weekly collection,
	 * one for monthly collection
	 * 
	 * @param timePeriod
	 *            : period specifying number of daily generated collection to be
	 *            merged PASTWEEK = documents with date within the past week
	 *            from today PASTMONTH = documents with date within the past
	 *            month collections from today
	 */
	public void mergingMapReduce(TimePeriod timePeriod) {

		DBCollection dailyCollection = mongoOps.getCollection(DAILY_COLLECT_NAME);

		Calendar today = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		if (timePeriod.equals(TimePeriod.PASTWEEK)) {
			end.add(Calendar.DATE, -7);
		} else if (timePeriod.equals(TimePeriod.PASTMONTH)) {
			end.add(Calendar.DATE, -30);
		} else {
			return;
		}

		DBObject query = QueryBuilder.start().put("_id.date")
				.lessThanEquals(counterDateFormat.format(today.getTime()))
				.greaterThan(counterDateFormat.format(end.getTime())).get();

		// create temporary collection for map-reduce
		DBCollection temp = mongoOps.getCollection("temp");
		DBCursor c = dailyCollection.find(query);
		while (c.hasNext()) {
			temp.insert(c.next());
		}

		// map function
		String map = "function() { "
				+ "if( this._id.id.trim().length > 0 ) { "
				+ "emit( { id: this._id.id.trim(), date: this._id.date }, { type: this.value.type, count: this.value.count } ); }}";

		// reduce function
		String reduce = "function(key, values) {"
				+ "var outs = { type: \"type\", count: 0 };"
				+ "values.forEach( function(v) {" + "outs.type = v.type;"
				+ "outs.count += v.count;" + "});" + "return outs;" + "}";

		// run map-reduce on the temporary collection, the output is in the file
		// named outCollection
		MapReduceCommand cmd = new MapReduceCommand(temp, map, reduce,
				(timePeriod.equals(TimePeriod.PASTWEEK) ? WEEKLY_COLLECT_NAME
						: MONTHLY_COLLECT_NAME),
				MapReduceCommand.OutputType.REPLACE, null);
		temp.mapReduce(cmd);

		// delete the temporary collection
		temp.drop();
	}
	
	/**
	 * @param field
	 *            : type of entity
	 * @param timePeriod
	 *            : period specifying collection to be retrieved
	 * @param numEntities
	 *            : number of top entities to be retrieved
	 * @return list of entities and the number of tweets associated with them
	 */
	public List<EntityCountPair> getTopEntities(Field field,
			TimePeriod timePeriod, int numEntities) {

		List<EntityCountPair> l = new ArrayList<EntityCountPair>(numEntities);

		Calendar end = Calendar.getInstance();
		QueryBuilder queryStr = QueryBuilder.start();
		DBCollection top;
		if (timePeriod.equals(TimePeriod.PASTDAY)) {
			top = mongoOps.getCollection(DAILY_COLLECT_NAME);
			queryStr = queryStr.put("_id.date").is(
					counterDateFormat.format(new Date()));
		} else if (timePeriod.equals(TimePeriod.PASTWEEK)) {
			top = mongoOps.getCollection(WEEKLY_COLLECT_NAME);
			end.add(Calendar.DATE, -7);
			queryStr = queryStr.put("_id.date")
					.lessThanEquals(counterDateFormat.format(new Date()))
					.greaterThan(counterDateFormat.format(end.getTime()));
		} else if (timePeriod.equals(TimePeriod.PASTMONTH)) {
			top = mongoOps.getCollection(MONTHLY_COLLECT_NAME);
			end.add(Calendar.DATE, -30);
			queryStr = queryStr.put("_id.date")
					.lessThanEquals(counterDateFormat.format(new Date()))
					.greaterThan(counterDateFormat.format(end.getTime()));
		} else {
			top = mongoOps.getCollection(DAILY_COLLECT_NAME);
		}
		if (!field.equals(Field.ALL)) {
			queryStr.put("value.type").is(field.toString());
		}

		// create pipeline operations, first with the $match
		DBObject match = new BasicDBObject("$match", queryStr.get());

		// build the $projection operation
		DBObject fields = new BasicDBObject("_id.id", 1);
		fields.put("value.count", 1);
		DBObject project = new BasicDBObject("$project", fields);

		// the $group operation
		DBObject groupFields = new BasicDBObject("_id", "$_id.id");
		groupFields.put("sum", new BasicDBObject("$sum", "$value.count"));
		DBObject group = new BasicDBObject("$group", groupFields);

		// Finally the $sort operation
		DBObject sort = new BasicDBObject("$sort", new BasicDBObject("sum", -1));

		List<DBObject> pipeline = Arrays.asList(match, project, group, sort);
		AggregationOutput output = top.aggregate(pipeline);

		int numAdded = 0;
		for (DBObject result : output.results()) {
			String tri = (String) result.get("_id");
			Double d = (Double) result.get("sum");
			if (tri.trim().length() <= 0)
				continue;
			l.add(new EntityCountPair(tri, d));
			if (++numAdded > numEntities)
				break;
		}

		return l;
	}

	/**
	 * @param entity
	 *            : the actual name of the entity
	 * @param numDays
	 *            : number of past days to retrieve the count
	 * @return list of (date string, number of tweets) sorted by date in
	 *         ascending order
	 */
	public ArrayList<DateCountPair> getEntitiyTrend(String entity, int numDays) {

		entity = entity.toLowerCase().trim();
		ArrayList<DateCountPair> l = new ArrayList<DateCountPair>(numDays);

		Calendar today = Calendar.getInstance();
		DBCollection dailyCollection = mongoOps.getCollection(DAILY_COLLECT_NAME);

		// iterate retrieving number of tweets of each day
		for (int i = 0; i < numDays; ++i, today.add(Calendar.DATE, -1)) {
			BasicDBObject query = new BasicDBObject();

			query.put("_id.id",
					java.util.regex.Pattern.compile(".*" + entity + ".*"));
			query.put("_id.date", counterDateFormat.format(today.getTime()));

			DBCursor cursor = dailyCollection.find(query);
			Double count = 0.0;
			while (cursor.hasNext()) {
				DBObject obj = cursor.next();
				BasicDBObject value = (BasicDBObject) obj.get("value");
				count += (Double) value.get("count");
			}
			l.add(i,
					new DateCountPair(
							counterDateFormat.format(today.getTime()), count
									.intValue()));
		}

		return l;
	}
	
	public class EntityCountPair {
		private String id;
		private Double count;

		public EntityCountPair(String id, Double count) {
			this.id = id;
			this.count = count;
		}

		public String getID() {
			return id;
		}

		public Double getCount() {
			return count;
		}
	}

	public class DateCountPair {
		private String date;
		private int count;

		public DateCountPair(String date, int count) {
			this.date = date;
			this.count = count;
		}

		public String getDate() {
			return date;
		}

		public int getCount() {
			return count;
		}
	}
	
	// ------------------------------------------------------------------------

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
		tweet.put("user", user.get("name") + " @" + user.get("screen_name"));
		
		return tweet;
	}
	
	

}
