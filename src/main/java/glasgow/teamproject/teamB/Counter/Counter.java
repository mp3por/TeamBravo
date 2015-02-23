package glasgow.teamproject.teamB.Counter;

import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MongoClient;
import com.mongodb.QueryBuilder;

public class Counter {

	private static final String MONGO_HOST = "localhost";
	private static final int MONGO_PORT = 27017;

	private static final String DB_NAME = "tweetsTest";
	private static final String TABLE_NAME = "tweets";

	private DB db;

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

	public Counter() {

		counterDateFormat = new SimpleDateFormat(COUNTER_DATE_FORMAT);

		try {
			MongoClient mongoClient = new MongoClient(MONGO_HOST, MONGO_PORT);
			db = mongoClient.getDB(DB_NAME);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

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

		DBCollection tweets = db.getCollection(TABLE_NAME);

		// convert date to twitter created_at format
		String twitterFormatDateStr = new SimpleDateFormat("EEE MMM dd")
				.format(date);
		// query tweets where created_at LIKE dateStr%
		BasicDBObject query = new BasicDBObject();
		query.put("created_at",
				java.util.regex.Pattern.compile(twitterFormatDateStr + ".*"));

		// create temporary collection for map-reduce
		DBCollection temp = db.getCollection("temp");
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
				map += "entity"
						+ i
						+ "[i] = entity"
						+ i
						+ "[i]"
						+ ".replace(/[`~!@$%^&*()_|+\\-=?;:\'\".<>\\{\\}\\[\\]\\\\/]/gi, '');";
			} else if (field.toString() != Field.URL.toString()) {
				map += "entity"
						+ i
						+ "[i] = entity"
						+ i
						+ "[i]"
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

		DBCollection dailyCollection = db.getCollection(DAILY_COLLECT_NAME);

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
		DBCollection temp = db.getCollection("temp");
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
			top = db.getCollection(DAILY_COLLECT_NAME);
			queryStr = queryStr.put("_id.date").is(
					counterDateFormat.format(new Date()));
		} else if (timePeriod.equals(TimePeriod.PASTWEEK)) {
			top = db.getCollection(WEEKLY_COLLECT_NAME);
			end.add(Calendar.DATE, -7);
			queryStr = queryStr.put("_id.date")
					.lessThanEquals(counterDateFormat.format(new Date()))
					.greaterThan(counterDateFormat.format(end.getTime()));
		} else if (timePeriod.equals(TimePeriod.PASTMONTH)) {
			top = db.getCollection(MONTHLY_COLLECT_NAME);
			end.add(Calendar.DATE, -30);
			queryStr = queryStr.put("_id.date")
					.lessThanEquals(counterDateFormat.format(new Date()))
					.greaterThan(counterDateFormat.format(end.getTime()));
		} else {
			top = db.getCollection(DAILY_COLLECT_NAME);
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
		DBCollection dailyCollection = db.getCollection(DAILY_COLLECT_NAME);

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

}
