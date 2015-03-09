package glasgow.teamproject.teamB.mongodb.dao;

import glasgow.teamproject.teamB.Search.Tweet;
import glasgow.teamproject.teamB.Util.ProjectProperties;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Observable;
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
public class TweetDAOImpl extends TweetDAOAbstract {

	@Autowired
	private MongoOperations mongoOps;


	// /Users/velin/Documents/Workspaces/3_Year/TP3/TeamBravo
//	 public TweetDAOImpl(MongoOperations mongoOps2,StreamReaderService s) {
//	 super(s);
//	 mongoOps = mongoOps2;
//	 }

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("TweetDAOImpl");
		addTweet((String) arg, ProjectProperties.TWEET_COLLECTION);
	}

	@Override
	public void addTweet(String tweet, String collectionName) {
		tweet = tweet.replace("\"id\":", "\"tweet_id\":");

		// If we ever need to store it as JSON object
		// Done in order to save the JSON object efficiently
		DBObject ob = (DBObject) JSON.parse(tweet);
		// System.out.println("OB:" + ob.toString());
		DBCollection dbCollection = mongoOps.getCollection(collectionName); // gets
																			// collection
		dbCollection.insert(ob);// stores the JSON

		// Simple store as String
		//mongoOps.insert(tweet, "new_tweets"); // stores the
		// tweet as string

		System.out.println("SAVED in collection(" + collectionName+ "): " + tweet);
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
		//TODO:get only last 1000 tweets
		Query q = new Query();
		q.addCriteria(Criteria.where("coordinates").ne(null));
		List<String> results = mongoOps.find(q, String.class, collectionName);
		return results;
	}
	
	@Override
	public List<String> getTweetsForMapsWithLimit(String collectionName, int numberOfTweetsWanted) {
		//TODO:get only last 1000 tweets
		
		DBCollection collection = mongoOps.getCollection(collectionName);
		DBObject q = QueryBuilder.start().put("coordinates").notEquals(null).get();
		DBCursor c = collection.find(q).sort(new BasicDBObject("timestamp_ms", -1)).limit(numberOfTweetsWanted);
		List<String> results = getResults(c);
		return results;
	}
	
	private List<String> getResults(DBCursor c){
		c.next(); // first object is null
		List<String> results = new ArrayList<String>();
		
		while(c.hasNext()){
			results.add(c.next().toString());
		}
		return results;
	}
	
	public List<String> getTweetsForMapsWithLimitAndDates(Date stDate, Date endDate, String collectionName, int max){
		DBCollection collection = mongoOps.getCollection(collectionName);
		
		DBObject query = QueryBuilder.start().put("timestamp_ms")
				.greaterThanEquals(Long.toString(stDate.getTime()))
				.lessThanEquals(Long.toString(endDate.getTime())).get();
		
		DBCursor c = collection.find(query).sort(new BasicDBObject("timestamp_ms", -1)).limit(max);
		List<String> results = getResults(c);
		return results;
		
	}
	/* { "type" : "Point" , "coordinates" : [ -4.292994 , 55.874865]} */
	private double[] getCoordinate(DBObject tweet) {

		double[] coordinate = new double[2];
		// Map<String, Object> map = tweet.getTweet();
		// System.err.println(map);
		if (tweet.get("coordinates") != null) {
			String pairString = tweet.get("coordinates").toString();
			// System.err.println(pairString);

			int startOfCoordinate = pairString.lastIndexOf('[') + 2;
			int comma = pairString.lastIndexOf(',');
			int endOfCoordinate = pairString.lastIndexOf(']') - 1;

			String latitude = pairString
					.substring(startOfCoordinate, comma - 1);
			String longtitude = pairString.substring(comma + 2,
					endOfCoordinate + 1);

			coordinate[0] = Double.parseDouble(latitude);
			coordinate[1] = Double.parseDouble(longtitude);

			return coordinate;
		} else
			return null;
	}

	@Override
	public boolean addNamedEntitiesById(String id, String collectionName,
			HashMap<String, ArrayList<String>> NamedEntities) {

		Query query = new Query(Criteria.where("id_str").is(id));
		Update update = new Update();
		update.push("named_entities", NamedEntities);

		WriteResult result = mongoOps
				.updateFirst(query, update, collectionName);

		return result.isUpdateOfExisting();
	}


	private ArrayList<HashMap<String, Object>> traverseTheQuery (DBCursor dbCursor, int count) {
		if (!dbCursor.hasNext()) 
			return null;
		dbCursor.next(); // this is needed as the first element is empty! Please do not touch this again.
		System.out.println("DBCursor length:" + dbCursor);
		ArrayList<HashMap<String,Object>> tweets = new ArrayList<>(); 
		int i = 0;
		// parsing gets complicated!
		while(dbCursor.hasNext() && i<=count){
			BasicDBObject currentObj = (BasicDBObject) dbCursor.curr();
			HashMap<String, Object> tweet = parseDBObject(currentObj);
			tweets.add(tweet);
			i++;
			dbCursor.next();
		}
		return tweets;
	}
	
	@Override
	public ArrayList<HashMap<String, Object>> getTweetsForDate(int count,
			String dateFrom, String dateTo, String collectionName) throws ParseException {
		DBCollection dbCollection = mongoOps.getCollection(collectionName);

		SimpleDateFormat parserSDF = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy");
		Date dateFr = parserSDF.parse(dateFrom); 
		Date dateT  = parserSDF.parse(dateTo);
		
		dateFrom = ((Long) dateFr.getTime()).toString();
		dateTo = ((Long) dateT.getTime()).toString();
		
		//System.out.println((dateFrom.substring(dateFrom.length()-3).compareTo("000")));
		//if ((dateFrom.substring(dateFrom.length()-3).compareTo("000") == 0) && (dateTo.substring(dateFrom.length()-3).compareTo("000") == 0)) {
			//System.out.println("Replacing");
			//dateFrom = dateFrom.replace("000", "");
			//dateTo = dateTo.replace("000", "");		
		//}
		System.out.println(dateFrom + " to " +dateTo);
		//DBCursor dbCursor = dbCollection.find().sort(new BasicDBObject("timestamp_ms", -1)).limit(count);
	    BasicDBObject getQuery = new BasicDBObject();
	    getQuery.put("timestamp_ms", new BasicDBObject("$gt", dateFrom).append("$lt", dateTo));
	    DBCursor dbCursor = dbCollection.find(getQuery).limit(count+1);

		
		return traverseTheQuery(dbCursor, count);
	}
	
	@Override
	public ArrayList<HashMap<String,Object>> getLastTweets(int count, String collectionName) {
		DBCollection dbCollection = mongoOps.getCollection(collectionName);

		DBCursor dbCursor = dbCollection.find().sort(new BasicDBObject("timestamp_ms", -1)).limit(count+1);
		
		return traverseTheQuery(dbCursor, count);
	}

	private HashMap<String, Object> parseDBObject(BasicDBObject currentObj) {
		HashMap<String, Object> tweet = new HashMap<>();
		for (String key : currentObj.keySet()) {
			if (ProjectProperties.defaultNE.contains(key)) {
				String s = currentObj.getString(key);
				// if s contains only "[]", return null - no need to parse an
				// empty array
				if (s.length() == 2) {
					tweet.put(key, null);
					continue;
				}

				s = s.replace("[", "");
				s = s.replace("]", "");

				HashSet<String> NEs = new HashSet<String>();

				for (String NE : s.split(",")) {
					NEs.add(NE.trim());
				}
				tweet.put(key, NEs);
			} else {
				tweet.put(key, currentObj.get(key));
			}
		}
		return tweet;
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

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Tweet> getResultsList(String collectionName,
			int[] resultsDocids) {

		DBCollection dbCollection = mongoOps.getCollection(collectionName);
		DBCursor foo = dbCollection.find();
		foo.next();

		ArrayList<Tweet> collectionList = new ArrayList<>();
		while(foo.hasNext()){ 
			collectionList.add(new Tweet(foo.next().toString(), foo.curr().toMap()));
		}
		

		ArrayList<Tweet> resultsList = new ArrayList<>();
		Tweet tweet;
		for (int i = 0; i < resultsDocids.length; i++) {
			tweet = collectionList.get(resultsDocids[i]);
			resultsList.add(tweet);
		}

		return resultsList;
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

	@Override
	public ArrayList<HashMap<String, Object>> getTweetsForId(int[] ids) {
		// TODO Auto-generated method stub
		return null;
	}

	// ------------------------------------------------------------------------
	// Counting Section
	/** collection names */
	String DAILY_COLLECT_NAME = ProjectProperties.DAILY_COLLECT_NAME;
	String WEEKLY_COLLECT_NAME = ProjectProperties.WEEKLY_COLLECT_NAME;
	String MONTHLY_COLLECT_NAME = ProjectProperties.MONTHLY_COLLECT_NAME;

	String COUNTER_DATE_FORMAT = ProjectProperties.COUNTER_DATE_FORMAT;
	private DateFormat counterDateFormat = new SimpleDateFormat(
			COUNTER_DATE_FORMAT);

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
	public void dailyMapReduce(Date date,String collectionName) {

		DBCollection tweets = mongoOps.getCollection(collectionName);
		
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		
		System.out.println("Starting daily mapreduce...");
		System.out.println("Date: " + today.getTime().toString());
		long today_beginning_timestamp = today.getTimeInMillis();
		DBObject q = new BasicDBObject("timestamp_ms", new BasicDBObject("$gte",Long.toString(today_beginning_timestamp)));
		DBObject match = new BasicDBObject("$match",q);
		DBObject output = new BasicDBObject("$out","temp");

		// run aggregation
		List<DBObject> pipeline = Arrays.asList(match, output);
		tweets.aggregate(pipeline);
		
		DBCollection temp = mongoOps.getCollection("temp");
		System.out.println("Collection (temp) size: " + temp.count());
		
		// map function
		StringBuilder mapFunction = new StringBuilder("function() {");
		for (Field field : Field.values()) {
			mapFunction.append("var entity = this."+ field.toString() +";");
			mapFunction.append("if ( entity ) { entity = entity.toString().toLowerCase().split(\",\");");
			mapFunction.append("for ( var i = entity.length -1  ; i>=0 ;--i){");

			if (field.toString() == Field.PERSON.toString()) {
				mapFunction.append("entity[i]=entity[i].replace(/[^a-zA-Z]/g, ' ');");

			} else if (field.toString() == Field.HASHTAG.toString()) {
				mapFunction.append("entity[i]=entity[i].replace(/[`~!@$%^&*()_|+\\-=?;:\'\".<>\\{\\}\\[\\]\\\\/]/gi, '');");

			} else if (field.toString() != Field.URL.toString()) {
				mapFunction.append("entity[i]=entity[i].replace(/[`~!@#$%^&*()_|+\\-=?;:\'\".<>\\{\\}\\[\\]\\\\/]/gi, '');");
			}
			
			mapFunction.append("if ( entity[i] && entity[i].trim().length > 0 && entity[i] != '[]') { var values = { count: 1 };");
			mapFunction.append("emit( { id: entity[i].trim(), date: \"" + counterDateFormat.format(date)+ "\", type: \"" + field.toString() + "\"}, values);}}}");

		}
		mapFunction.append("};");
		String map = mapFunction.toString();

		// reduce function
		String reduce = "function(key, values) {"
				+ "var outs = { count: 0 };"
				+ "values.forEach( function(v) {"
				+ "outs.count += v.count;" + "});" + "return outs;" + "}";

		// run map-reduce on the temporary collection, the output is in the file
		// named outCollection
		MapReduceCommand cmd = new MapReduceCommand(temp, map, reduce,
				DAILY_COLLECT_NAME, MapReduceCommand.OutputType.MERGE, null);
		temp.mapReduce(cmd);

		// delete the temporary collection
		temp.drop();
		System.out.println("Finished daily mapreduce");
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
		
		System.out.println("Starting merging mapreduce...");
		
		DBCollection dailyCollection = mongoOps.getCollection(DAILY_COLLECT_NAME);

		Calendar stDate = Calendar.getInstance();
		if (timePeriod.equals(TimePeriod.PASTWEEK)) {
			stDate.add(Calendar.DATE, -7);
		} else if (timePeriod.equals(TimePeriod.PASTMONTH)) {
			stDate.add(Calendar.DATE, -30);
		} else {
			return;
		}
		
		System.out.println("Start date: " + stDate.getTime().toString());
		
		// create pipeline operations, first with the $match
		DBObject matchObj = new BasicDBObject( "_id.date",
				new BasicDBObject("$gte", counterDateFormat.format(stDate.getTime())));
		DBObject match = new BasicDBObject("$match", matchObj);
		
		// build the $projection operation
		DBObject fields = new BasicDBObject("_id.id", 1);
		fields.put("_id.type", 1);
		fields.put("value.count", 1);
		DBObject project = new BasicDBObject("$project", fields);
		
		// the $group operation
		Map<String, Object> dbObjIdMap = new HashMap<String, Object>();
		dbObjIdMap.put("id", "$_id.id");
		dbObjIdMap.put("type", "$_id.type");
		DBObject groupFields = new BasicDBObject("_id", new BasicDBObject(dbObjIdMap));
		groupFields.put("sum", new BasicDBObject("$sum", "$value.count"));
		DBObject group = new BasicDBObject("$group", groupFields);
		
		// $out operation
		DBObject out = new BasicDBObject("$out", (timePeriod.equals(TimePeriod.PASTWEEK) ? WEEKLY_COLLECT_NAME
				: MONTHLY_COLLECT_NAME));

		List<DBObject> pipeline = Arrays.asList(match, project, group, out);
		dailyCollection.aggregate(pipeline);
		
		System.out.println("Finished merging mapreduce");
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
		boolean findQuery = false;
		List<EntityCountPair> l = new ArrayList<EntityCountPair>(numEntities);
		
		QueryBuilder queryStr = QueryBuilder.start();
		DBCollection top;
		if (timePeriod.equals(TimePeriod.PASTWEEK)) {
			top = mongoOps.getCollection(WEEKLY_COLLECT_NAME);
		} else if (timePeriod.equals(TimePeriod.PASTMONTH)) {
			top = mongoOps.getCollection(MONTHLY_COLLECT_NAME);
		} else {
			top = mongoOps.getCollection(DAILY_COLLECT_NAME);
			if ( timePeriod.equals(TimePeriod.PASTDAY) ) {
				findQuery = true;
				queryStr = queryStr.put("_id.date").is(
						counterDateFormat.format(new Date()));
			}
		}
		
		if (!field.equals(Field.ALL)) {
			findQuery = true;
			queryStr.put("_id.type").is(field.toString());
		}
		
		DBCursor c;
		if( findQuery ) {
			c = top.find(queryStr.get()).sort( new BasicDBObject("sum", -1) );
		} else {
			c = top.find().sort( new BasicDBObject("sum", -1) );
		}
		
		int numAdded = 0;
		while( c.hasNext() ) {
			DBObject result = c.next();
			DBObject idObj = (DBObject) result.get("_id");
			String tri = (String) idObj.get("id");
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
		DBCollection dailyCollection = mongoOps
				.getCollection(DAILY_COLLECT_NAME);

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
		DBCollection tweets = mongoOps
				.getCollection(ProjectProperties.TWEET_COLLECTION);
		if (stDate != null && edDate != null) {
			DBObject query = QueryBuilder.start().put("timestamp_ms")
					.greaterThanEquals(Long.toString(stDate.getTime()))
					.lessThanEquals(Long.toString(edDate.getTime()))
					.put("retweeted").is(isRetweeted).get();
			return tweets.find(query).count();
		} else {
			DBObject query = QueryBuilder.start().put("retweeted")
					.is(isRetweeted).get();
			return tweets.find(query).count();
		}
	}

	@Override
	public HashMap<String, Object> getMostPopularTweet(Date stDate,
			Date edDate, String compareKey) {
		DBCollection tweets = mongoOps
				.getCollection(ProjectProperties.TWEET_COLLECTION);

		DBCursor cursor;
		if (stDate != null && edDate != null) {
			DBObject query = QueryBuilder.start().put("timestamp_ms")
					.greaterThanEquals(Long.toString(stDate.getTime()))
					.lessThanEquals(Long.toString(edDate.getTime())).get();
			cursor = tweets.find(query).sort(new BasicDBObject(compareKey, -1))
					.limit(1);
		} else {
			cursor = tweets.find().sort(new BasicDBObject(compareKey, -1))
					.limit(1);
		}

		HashMap<String, Object> tweet = new HashMap<>();
		if (cursor.hasNext()) {
			DBObject obj = cursor.next();
			if ((int) obj.get(compareKey) != 0) {
				tweet.put("text", obj.get("text"));
				DBObject user = (DBObject) obj.get("user");
				tweet.put("username", user.get("name"));
				tweet.put("screen_name", user.get("screen_name"));
			}
		}
		return tweet;
	}

	public String getMostActiveUser(Date stDate, Date edDate) {

		DBCollection tweets = mongoOps.getCollection("tweets");

		DBObject query = null;
		if (stDate != null && edDate != null) {
			query = QueryBuilder.start().put("timestamp_ms")
					.greaterThanEquals(Long.toString(stDate.getTime()))
					.lessThanEquals(Long.toString(edDate.getTime())).get();
		}

		// create pipeline operations, first with the $match
		DBObject match = null;
		if (query != null) {
			match = new BasicDBObject("$match", query);
		}

		// build the $projection operation
		DBObject fields = new BasicDBObject("user.screen_name", 1);
		DBObject project = new BasicDBObject("$project", fields);

		// the $group operation
		DBObject groupFields = new BasicDBObject("_id", "$user.screen_name");
		groupFields.put("count", new BasicDBObject("$sum", 1));
		DBObject group = new BasicDBObject("$group", groupFields);

		// Finally the $sort operation
		DBObject sort = new BasicDBObject("$sort", new BasicDBObject("count",
				-1));

		List<DBObject> pipeline;
		if (match != null) {
			pipeline = Arrays.asList(match, project, group, sort);
		} else {
			pipeline = Arrays.asList(project, group, sort);
		}
		AggregationOutput output = tweets.aggregate(pipeline);

		for (DBObject result : output.results()) {
			return (String) result.get("_id");
		}

		return null;
	}
}
