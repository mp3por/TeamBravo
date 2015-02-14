package glasgow.teamproject.teamB.Counter;

import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MongoClient;

public class Counter {
	
	private static final String MONGO_HOST = "localhost";
	private static final int MONGO_PORT = 27017;
	
	private static final String DB_NAME = "tweetsTest";
	private static final String TABLE_NAME = "tweets";
	
	private DB db;
	
	/** collection names */
	public static final String DAILY_COLLECT_NAME = "DCNT_";
	public static final String WEEKLY_COLLECT_NAME = "WCNT";
	public static final String MONTHLY_COLLECT_NAME = "MCNT";
	
	public enum Field {
		HASHTAG { public String toString() { return "Hashtag"; } },
		ORG { public String toString() { return "Organization"; } },
		PERSON { public String toString() { return "Person"; } },
		USERID { public String toString() { return "UserID"; } },
		URL { public String toString() { return "URL"; } },
		LOCATION { public String toString() { return "Location"; } },
	}

	public enum TimePeriod { PASTDAY, PASTWEEK, PASTMONTH }
	
	public Counter() {
		
		try {
			MongoClient mongoClient = new MongoClient(MONGO_HOST, MONGO_PORT);
			db = mongoClient.getDB( DB_NAME );
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * This method is called timely during a day.
	 * outCollection is the same for a day, the output will be map-reduced value
	 * of tweets from the beginning of the day up to the current time
	 * (replace documents of the same date in the outCollection).
	 * 
	 * @param field: the type of entity
	 * @param date: tweet's created_at date
	 * @param outCollection: name of output file
	 */
	public void dailyMapReduce(Date date) {
		
		DBCollection tweets = db.getCollection(TABLE_NAME);
		
		// convert date to counter's format
		DateFormat counterFormatDateStr = new SimpleDateFormat("yyyyMMdd");
		
		// convert date to twitter created_at format
		String twitterFormatDateStr = new SimpleDateFormat("EEE MMM dd").format(date);
		// query tweets where created_at LIKE dateStr%
		BasicDBObject query = new BasicDBObject();
		query.put("created_at", java.util.regex.Pattern.compile(twitterFormatDateStr + ".*"));
		
		// create temporary collection for map-reduce
		DBCollection temp = db.getCollection("temp");
		DBCursor c = tweets.find(query);
		while( c.hasNext() ) { temp.insert(c.next()); }
		
		// map function
		String map = "function() {";
		int i = 0;
		for( Field field : Field.values() ) {
			map += "var entity" + i + " = this." + field.toString() + ";"
					+ "if( entity" + i + " ) {"
						+ "entity" + i + " = entity" + i + ".toString().toLowerCase()"
						+ (field.toString() == Field.PERSON.toString() ? ".replace(/[^a-zA-Z]/g, ' ')" : "")
						+ ".split(\",\");"
						+ "for ( var i = entity" + i + ".length - 1; i >= 0; --i) {"
								+ "if ( entity" + i + "[i]) { "
										+ "var key   = { id: entity" + i + "[i], "
														+ "date: " + counterFormatDateStr + "};"
										+ "var values = { type: \"" + field.toString() + "\", "
														+ "count: 1, "
														+ "date: " + counterFormatDateStr + " };"
										+ "emit(key, values);"
									+ "}"
						+ "}}";
		}
		map += "};";
		
		// reduce function
		String reduce = "function(key, values) {"
				+ "var outs = { type: \"type\", count: 0 };"
				+ "values.forEach( function(v) {"
					+ "outs.type = v.type;"
					+ "outs.count += v.count;"
					+ "outs.date = v.date;"
					+ "});"
					+ "return outs;"
				+ "}";
		
		// run map-reduce on the temporary collection, the output is in the file named outCollection
		MapReduceCommand cmd = new MapReduceCommand(temp, map, reduce, 
				DAILY_COLLECT_NAME, MapReduceCommand.OutputType.MERGE, null);
		// TODO check if this works, if it does then don't sort when select
		//cmd.setSort(new BasicDBObject("value.count", -1));
		temp.mapReduce(cmd);
		
		// delete the temporary collection
		temp.drop();
	}
	
	/**
	 * This method is called once a day to calculate trends for the past week/month
	 * outCollection is always the same, one for weekly collection, one for monthly collection
	 * 
	 * @param field: the type of entity
	 * @param timePeriod: period specifying number of daily generated collection to be merged
	 * 					  PASTWEEK = 7 collections from today
	 * 					  PASTMONTH = 30 collections from today
	 * @param outCollection: name of output file
	 */
	public void mergingMapReduce(TimePeriod timePeriod) {
		// TODO merge daily generated collections to a week collection and a month collection
		// there should be only one week collection and one month collection kept in the system
		
		
	}

	/**
	 * @param field: type of entity
	 * @param timePeriod: period specifying collection to be retrieved
	 * @param numEntities: number of top entities to be retrieved
	 * @return list of entities and the number of tweets associated with them
	 */
	public List<EntityCountPair> getTopEntities(Field field, TimePeriod timePeriod, int numEntities) {

		List<EntityCountPair> l = new ArrayList<EntityCountPair>(numEntities);
		
		BasicDBObject query = new BasicDBObject();
		query.put("value.type", field.toString());
		
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
				
		// TODO re-implement this part
		if ( timePeriod.equals(TimePeriod.PASTDAY) ) {
			DBCollection top = db.getCollection(DAILY_COLLECT_NAME);
			query.put("key.date", dateFormat.format(new Date()));
			//DBCursor cursor = top.find(query).sort(new BasicDBObject("value.count", -1));
			DBCursor cursor = top.find(query).limit(numEntities);
			int i = 0;
			while( cursor.hasNext() && i < numEntities ) {
				BasicDBObject obj = (BasicDBObject) cursor.next();
				DBObject id = (BasicDBObject) obj.get("key");
				DBObject value = (BasicDBObject) obj.get("value");
				l.add(new EntityCountPair((String) id.get("id"), (Double) value.get("count")));
			}
		} else if ( timePeriod.equals(TimePeriod.PASTWEEK) ) {
			
		} else if ( timePeriod.equals(TimePeriod.PASTMONTH) ) {
			
		}
		
		return l;
	}
	
	
	
	/**
	 * @param field: type of entity
	 * @param entity: the id for the entity (the actual name ex. "Taylor Swift"
	 * @return list of number of tweets per day in a week period
	 * 			last index = today
	 */
	public ArrayList<Long> getEntitiyTrend(Field field, String entity) {
		//TODO
		return null;
	}
	
	public void dropExpiredDocument() {
		//TODO
	}
	
	public class EntityCountPair {
		private String id;
		private Double count;
		
		public EntityCountPair(String id, Double count) {
			this.id = id;
			this.count = count;
		}
		
		public String getID() { return id; }
		public Double getCount() { return count; }
	}

}
