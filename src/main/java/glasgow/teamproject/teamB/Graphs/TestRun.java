package glasgow.teamproject.teamB.Graphs;

import glasgow.teamproject.teamB.Counter.Counter;
import glasgow.teamproject.teamB.Counter.Counter.DateCountPair;
import glasgow.teamproject.teamB.Counter.Counter.EntityCountPair;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAO;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAOImpl;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.MongoClient;

public class TestRun {
	
	private static final String DB_NAME = "tweetsTest";
	private static final String MONGO_HOST = "localhost";
	private static final int MONGO_PORT = 27017;
	private static TweetDAO tweetdao;
	private static MongoOperations mongoOps;
	private static final int NUMBEROFTOPICS = 6;
	
	public static void main(String[] args){
		
		/*setUpDBInfo();
		mongoOps.createCollection("DCNT_");
		mongoOps.createCollection("WCNT");
		mongoOps.createCollection("MCNT");*/
		
		/*Date date = new Date();
		Calendar end = Calendar.getInstance();
		end.add(Calendar.DATE, -1);
		Counter c = new Counter();
		c.dailyMapReduce(date);
		c.dailyMapReduce(end.getTime());
		end.add(Calendar.DATE, -2);
		c.dailyMapReduce(end.getTime());
		c.mergingMapReduce(Counter.TimePeriod.PASTWEEK);*/
		
		//Map reduce week
		//Date date = new Date();
		Calendar today = Calendar.getInstance();
		today.add(Calendar.DATE, -1);
		Calendar end = Calendar.getInstance();
		end.add(Calendar.DATE, -2);
		Counter c = new Counter();
		c.dailyMapReduce(today.getTime());
		c.dailyMapReduce(end.getTime());
		end.add(Calendar.DATE, -2);
		c.dailyMapReduce(end.getTime());
		c.mergingMapReduce(Counter.TimePeriod.PASTMONTH);
		
		List<EntityCountPair> l = c.getTopEntities(Counter.Field.ALL, Counter.TimePeriod.PASTWEEK, 10);
		
		for( EntityCountPair e : l ) {
			System.out.println(e.getID() + " : " +  e.getCount().intValue());
		}
		
		
		/*//Map reduce week
		//Date date = new Date();
		Calendar today = Calendar.getInstance();
		today.add(Calendar.DATE, -1);
		Calendar end = Calendar.getInstance();
		end.add(Calendar.DATE, -2);
		Counter c = new Counter();
		c.dailyMapReduce(today.getTime());
		c.dailyMapReduce(end.getTime());
		end.add(Calendar.DATE, -2);
		c.dailyMapReduce(end.getTime());
		c.mergingMapReduce(Counter.TimePeriod.PASTMONTH);*/
		
		
		/*JSONArray tweetsForMonth = new JSONArray();
		//Get top 3 topics for the past week
		List<EntityCountPair> top3TopicsPastMonth = c.getTopEntities(Counter.Field.ALL, Counter.TimePeriod.PASTWEEK, 20);
		
		for( EntityCountPair e : top3TopicsPastMonth ) {
			System.out.println(e.getID() + " : " +  e.getCount().intValue());
		}*/
		
		
		/*List<DateCountPair> l2 = c.getEntitiyTrend("glasgow", 30);
		for( DateCountPair e : l2 ) {
			System.out.println(e.getDate() + " : " + e.getCount());
		}*/
		
		/*List<EntityCountPair> top3TopicsPastWeek = c.getTopEntities(Counter.Field.ALL, Counter.TimePeriod.PASTWEEK, NUMBEROFTOPICS);
		
		String topic1 = top3TopicsPastWeek.get(3).getID().replace("[", "");
		String topic1Clean = topic1.replace("]", "");
		String topic2 = top3TopicsPastWeek.get(4).getID().replace("[", "");
		String topic2Clean = topic2.replace("]", "");
		String topic3 = top3TopicsPastWeek.get(5).getID().replace("[", "");
		String topic3Clean = topic3.replace("]", "");

		System.out.println("t1: " + topic1Clean + "\nt2: " + topic2Clean + "\nt3: " + topic3Clean);
		List<DateCountPair> topic1PastWeek = c.getEntitiyTrend(topic1Clean,7);
		List<DateCountPair> topic2PastWeek = c.getEntitiyTrend(topic2Clean,7);
		List<DateCountPair> topic3PastWeek = c.getEntitiyTrend(topic3Clean,7);
		
		
		for( EntityCountPair e :top3TopicsPastWeek ) {
			System.out.println(e.getID() + " : " +  e.getCount().intValue());
		}
		
		for(DateCountPair d : topic1PastWeek){
			System.out.println("Topic 1 past week\n " + d.getDate() + " : " +  d.getCount());
		}
		
		
		for(DateCountPair d : topic2PastWeek){
			System.out.println("Topic 2 past week\n " + d.getDate() + " : " +  d.getCount());
		}
		
		for(DateCountPair d : topic3PastWeek){
			System.out.println("Topic 3 past week\n " + d.getDate() + " : " +  d.getCount());
		}*/
		
		
		/*JSONArray tweetsForweek = new JSONArray();
		
		//Get top 3 topics for the past week
		List<EntityCountPair> top3TopicsPastWeek = c.getTopEntities(Counter.Field.ALL, Counter.TimePeriod.PASTWEEK, 8);
		
		//Clean up the strings removing "[" and "]" characters and disregard first 3 values
		String topic1 = top3TopicsPastWeek.get(3).getID().replace("[", "");
		String topic1Clean = topic1.replace("]", "");
		String topic2 = top3TopicsPastWeek.get(4).getID().replace("[", "");
		String topic2Clean = topic2.replace("]", "");
		String topic3 = top3TopicsPastWeek.get(7).getID().replace("[", "");
		String topic3Clean = topic3.replace("]", "");
		
		//Get lists of past week number of tweets for each topic)
		List<DateCountPair> topic1PastWeek = c.getEntitiyTrend(topic1Clean,7);
		List<DateCountPair> topic2PastWeek = c.getEntitiyTrend(topic2Clean,7);
		List<DateCountPair> topic3PastWeek = c.getEntitiyTrend(topic3Clean,7);
		
		//Add "topic" in each object in list and build JSON object
		List<JSONObject> topic1List = new ArrayList<JSONObject>();
		List<JSONObject> topic2List = new ArrayList<JSONObject>();
		List<JSONObject> topic3List = new ArrayList<JSONObject>();
		
		for(DateCountPair t1 : topic1PastWeek){
			JSONObject topicValue = new JSONObject();
			topicValue.put("Day", t1.getDate());
			topicValue.put("Tweets",t1.getCount());
			topicValue.put("Topic", topic1Clean);
			topic1List.add(topicValue);
		}
		
		for(DateCountPair t2 : topic2PastWeek){
			JSONObject topicValue = new JSONObject();
			topicValue.put("Day", t2.getDate());
			topicValue.put("Tweets",t2.getCount());
			topicValue.put("Topic", topic2Clean);
			topic2List.add(topicValue);
		}
		
		for(DateCountPair t3 : topic3PastWeek){
			JSONObject topicValue = new JSONObject();
			topicValue.put("Day", t3.getDate());
			topicValue.put("Tweets",t3.getCount());
			topicValue.put("Topic", topic3Clean);
			topic3List.add(topicValue);
		}
		
		//Construct JSON Array in correct format for graph
		for(int i = 0; i < 7; i++){
			tweetsForweek.put(topic1List.get(i));
			tweetsForweek.put(topic2List.get(i));
			tweetsForweek.put(topic3List.get(i));
		}*/
		

		/*
		//Clean up the strings removing "[" and "]" characters and disregard first 3 values
		String topic1 = top3TopicsPastMonth.get(3).getID().replace("[", "");
		String topic1Clean = topic1.replace("]", "");
		String topic2 = top3TopicsPastMonth.get(4).getID().replace("[", "");
		String topic2Clean = topic2.replace("]", "");
		String topic3 = top3TopicsPastMonth.get(5).getID().replace("[", "");
		String topic3Clean = topic3.replace("]", "");
		
		//Get lists of past month number of tweets for each topic)
		List<DateCountPair> topic1PastMonth = c.getEntitiyTrend(topic1Clean,30);
		List<DateCountPair> topic2PastMonth = c.getEntitiyTrend(topic2Clean,30);
		List<DateCountPair> topic3PastMonth = c.getEntitiyTrend(topic3Clean,30);
		
		//Add "topic" in each object and add to seperate lists
		List<JSONObject> topic1List = new ArrayList<JSONObject>();
		List<JSONObject> topic2List = new ArrayList<JSONObject>();
		List<JSONObject> topic3List = new ArrayList<JSONObject>();
		
		for(DateCountPair t1 : topic1PastMonth){
			JSONObject topicValue = new JSONObject();
			topicValue.put("Day", t1.getDate());
			topicValue.put("Tweets",t1.getCount());
			topicValue.put("Topic", topic1Clean);
			topic1List.add(topicValue);
		}
		
		for(DateCountPair t2 : topic2PastMonth){
			JSONObject topicValue = new JSONObject();
			topicValue.put("Day", t2.getDate());
			topicValue.put("Tweets",t2.getCount());
			topicValue.put("Topic", topic2Clean);
			topic2List.add(topicValue);
		}
		
		for(DateCountPair t3 : topic3PastMonth){
			JSONObject topicValue = new JSONObject();
			topicValue.put("Day", t3.getDate());
			topicValue.put("Tweets",t3.getCount());
			topicValue.put("Topic", topic3Clean);
			topic3List.add(topicValue);
		}
		
		//Construct JSON Array in correct format for graph
		for(int i = 0; i < 30; i++){
			tweetsForMonth .put(topic1List.get(i));
			tweetsForMonth .put(topic2List.get(i));
			tweetsForMonth .put(topic3List.get(i));
		}
		
		
		
		System.out.println(tweetsForMonth.toString());*/

		/*
		JSONArray tweetsForPie = new JSONArray();
		
		//Get top 3 topics for the past week
		List<EntityCountPair> top3TopicsPastWeek = c.getTopEntities(Counter.Field.ALL, Counter.TimePeriod.PASTWEEK, 6);
		
		//Clean up the strings removing "[" and "]" characters and disregard first 3 values
		String topic1 = top3TopicsPastWeek.get(3).getID().replace("[", "");
		String topic1Clean = topic1.replace("]", "");
		String topic2 = top3TopicsPastWeek.get(4).getID().replace("[", "");
		String topic2Clean = topic2.replace("]", "");
		String topic3 = top3TopicsPastWeek.get(5).getID().replace("[", "");
		String topic3Clean = topic3.replace("]", "");
		
		JSONObject top1 = new JSONObject();
		JSONObject top2 = new JSONObject();
		JSONObject top3 = new JSONObject();
		
		//Construct json objects for json array - {"Topic": value, "Tweets": value}
		top1.put("Topic", topic1Clean);
		top1.put("Tweets", top3TopicsPastWeek.get(3).getCount().intValue());
		top2.put("Topic", topic2Clean);
		top2.put("Tweets", top3TopicsPastWeek.get(4).getCount().intValue());
		top3.put("Topic", topic3Clean);
		top3.put("Tweets", top3TopicsPastWeek.get(7).getCount().intValue());
		
		//Add top 3 topic jsons to array
		tweetsForPie.put(top1);
		tweetsForPie.put(top2);
		tweetsForPie.put(top3);
		
		System.out.println(tweetsForPie.toString());
		*/
	
		
	}
	
	
	
	
	
	
	
	
	
	
	
}
