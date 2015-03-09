package glasgow.teamproject.teamB.mongodb.main;

import glasgow.teamproject.teamB.TwitIE.NamedEntityParser;
import glasgow.teamproject.teamB.TwitIE.TwitIE;
import glasgow.teamproject.teamB.Util.ProjectProperties;
import glasgow.teamproject.teamB.Util.StreamReaderService;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAOAbstract;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAOImpl.Field;

import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MongoClient;

public class FixDB {

	public static final String DB_NAME = "tweetsTest";
	public static final String TWEETS_COLLECTION = "tweets";
	public static final String MONGO_HOST = "localhost";
	public static final int MONGO_PORT = 27017;

	static TweetDAOAbstract tweetSaver;
	static NamedEntityParser NE;
	static StreamReaderService serv;
	static MongoClient mongo;
	static TwitIE t;

	static String COUNTER_DATE_FORMAT = ProjectProperties.COUNTER_DATE_FORMAT;
	private static DateFormat counterDateFormat = new SimpleDateFormat(
			COUNTER_DATE_FORMAT);
	static String DAILY_COLLECT_NAME = ProjectProperties.DAILY_COLLECT_NAME;
	static String WEEKLY_COLLECT_NAME = ProjectProperties.WEEKLY_COLLECT_NAME;
	static String MONTHLY_COLLECT_NAME = ProjectProperties.MONTHLY_COLLECT_NAME;
	static MongoOperations mongoOps;

	public enum TimePeriod {
		PASTDAY, PASTWEEK, PASTMONTH, ALLTIME
	}

	public static void main(String[] args) throws UnknownHostException {
		System.out.println("start");
		// startStreamerDBAndNE();

		MongoClient mongo = new MongoClient(MONGO_HOST, MONGO_PORT);
		mongoOps = new MongoTemplate(mongo, DB_NAME);

		Calendar today = Calendar.getInstance();
//		today.add(Calendar.DATE, -7);
		int q = -7;

		Scanner i = new Scanner(System.in);
		boolean work = true;
		while (work) {
			String j = i.next();
			int p = Integer.parseInt(j);
			switch (p) {
			case 2:
				if (q <= 0) {
					today = Calendar.getInstance();
					today.add(Calendar.DATE, q++);
					System.out.println("daily for day:" + today.getTime());
					dailyMapReduce(today.getTime(), TWEETS_COLLECTION);
				}else {
					System.out.println("NO");
				}
				break;
			case 3:
				System.out.println("week");
				mergingMapReduce(TimePeriod.PASTWEEK);
				break;
			case 4:
				System.out.println("month");
				mergingMapReduce(TimePeriod.PASTMONTH);
				break;
			default:
				break;
			}
		}

	}

	public static void dailyMapReduce(Date date, String collectionName) {

		DBCollection tweets = mongoOps.getCollection(collectionName);

		Calendar today = Calendar.getInstance();
		today.setTime(date);

		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);

		long today_beginning_timestamp = today.getTimeInMillis();
		System.out.println("Today beginning:" + today.getTime());

		today.set(Calendar.HOUR_OF_DAY, 23);
		today.set(Calendar.MINUTE, 59);
		today.set(Calendar.SECOND, 59);
		long today_ending_timestamp = today.getTimeInMillis();
		System.out.println("Today ending:" + today.getTime());

		DBObject condition = new BasicDBObject(2);
		condition.put("$gte", Long.toString(today_beginning_timestamp));
		condition.put("$lt", Long.toString(today_ending_timestamp));
		DBObject q = new BasicDBObject("timestamp_ms", condition);
		DBObject match = new BasicDBObject("$match", q);
		DBObject output = new BasicDBObject("$out", "temp");

		// run aggregation
		List<DBObject> pipeline = Arrays.asList(match, output);
		tweets.aggregate(pipeline);

		DBCollection temp = mongoOps.getCollection("temp");

		// map function
		StringBuilder mapFunction = new StringBuilder("function() {");
		for (Field field : Field.values()) {
			if (field == Field.ALL) {
				continue;
			}
			mapFunction.append("var entity = this." + field.toString() + ";");
			mapFunction
					.append("if ( entity ) { entity = entity.toString().toLowerCase().split(',');");
			mapFunction.append("for ( var i = entity.length -1  ; i>=0 ;--i){");

			if (field.toString() == Field.PERSON.toString()) {
				mapFunction
						.append("entity[i]=entity[i].replace(/[^a-zA-Z]/g, ' ');");

			} else if (field.toString() == Field.HASHTAG.toString()) {
				mapFunction
						.append("entity[i]=entity[i].replace(/[`~!@$%^&*()_|+\\-=?;:\\'\".<>\\{\\}\\[\\]\\\\/]/gi, '');");

			} else if (field.toString() != Field.URL.toString()) {
				mapFunction
						.append("entity[i]=entity[i].replace(/[`~!@#$%^&*()_|+\\-=?;:\\'\".<>\\{\\}\\[\\]\\\\/]/gi, '');");
			}

			mapFunction
					.append("if ( entity[i] && entity[i].trim().length > 0 && entity[i] !== '[]') {");
			mapFunction.append("emit( { id: entity[i].trim(), date: \""
					+ counterDateFormat.format(date) + "\", type: \""
					+ field.toString() + "\"}, 1);}}}");

		}
		mapFunction.append("};");
		String map = mapFunction.toString();
		System.out.println(map);

		// reduce function
		String reduce = "function(key, values) {" + "var sum = 0;"
				+ "values.forEach( function(v) {" + "sum += v;" + "});"
				+ "return sum;" + "}";

		System.out.println(reduce);

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
	public static void mergingMapReduce(TimePeriod timePeriod) {

		System.out.println("Starting merging mapreduce...");

		DBCollection dailyCollection = mongoOps
				.getCollection(DAILY_COLLECT_NAME);

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
		DBObject matchObj = new BasicDBObject("_id.date", new BasicDBObject(
				"$gte", counterDateFormat.format(stDate.getTime())));
		DBObject match = new BasicDBObject("$match", matchObj);

		// build the $projection operation
		DBObject fields = new BasicDBObject("_id.id", 1);
		fields.put("_id.type", 1);
		fields.put("value", 1);
		DBObject project = new BasicDBObject("$project", fields);

		// the $group operation
		Map<String, Object> dbObjIdMap = new HashMap<String, Object>();
		dbObjIdMap.put("id", "$_id.id");
		dbObjIdMap.put("type", "$_id.type");
		DBObject groupFields = new BasicDBObject("_id", new BasicDBObject(
				dbObjIdMap));
		groupFields.put("value", new BasicDBObject("$sum", "$value"));
		DBObject group = new BasicDBObject("$group", groupFields);

		// $out operation
		DBObject out = new BasicDBObject("$out",
				(timePeriod.equals(TimePeriod.PASTWEEK) ? WEEKLY_COLLECT_NAME
						: MONTHLY_COLLECT_NAME));

		List<DBObject> pipeline = Arrays.asList(match, project, group, out);
		dailyCollection.aggregate(pipeline);

		System.out.println("Finished merging mapreduce");
	}
}
