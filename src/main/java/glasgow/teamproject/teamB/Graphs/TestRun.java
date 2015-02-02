package glasgow.teamproject.teamB.Graphs;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import twitter4j.JSONArray;
import twitter4j.JSONException;
import twitter4j.JSONObject;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

import glasgow.teamproject.teamB.mongodb.dao.TweetDAO;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAOImpl;

public class TestRun {

	public static final String DB_NAME = "tweetsTest";
	public static final String TWEETS_COLLECTION = "tweets";
	public static final String MONGO_HOST = "localhost";
	public static final int MONGO_PORT = 27017;
	
	
	public static void main(String[] args){
		
		try{
			MongoClient mongo = new MongoClient(MONGO_HOST, MONGO_PORT);
			MongoOperations mongoOps = new MongoTemplate(mongo, DB_NAME);
			TweetDAO tweetdao = new TweetDAOImpl(mongoOps);
			
			/*
			//Creating new table for weekly hot topic count
			//mongoOps.createCollection("Topics_Week");
			JSONObject hotTopic1 = new JSONObject();
			JSONObject hotTopic2 = new JSONObject();
			JSONObject hotTopic3 = new JSONObject();
			JSONObject hotTopic4 = new JSONObject();
			JSONObject hotTopic5 = new JSONObject();
			JSONObject hotTopic6 = new JSONObject();
			JSONObject hotTopic7 = new JSONObject();
			JSONObject hotTopic8 = new JSONObject();
			JSONObject hotTopic9 = new JSONObject();
			JSONObject hotTopic10 = new JSONObject();
			
			
			hotTopic1.put("Name", "SNP");
			hotTopic1.put("Tweets",new Integer(30000));
			hotTopic2.put("Name", "IndyRef");
			hotTopic2.put("Tweets",new Integer(40000));
			hotTopic3.put("Name", "Celtic");
			hotTopic3.put("Tweets",new Integer(20000));
			hotTopic4.put("Name", "Nicola Sturgeon");
			hotTopic4.put("Tweets",new Integer(60000));
			hotTopic5.put("Name", "Rangers");
			hotTopic5.put("Tweets",new Integer(30000));
			hotTopic6.put("Name", "Labour");
			hotTopic6.put("Tweets",new Integer(70000));
			hotTopic7.put("Name", "Tories");
			hotTopic7.put("Tweets",new Integer(25000));
			hotTopic8.put("Name", "Austerity");
			hotTopic8.put("Tweets",new Integer(15000));
			hotTopic9.put("Name", "Westminster");
			hotTopic9.put("Tweets",new Integer(80000));
			hotTopic10.put("Name", "Hydro");
			hotTopic10.put("Tweets",new Integer(10));


			mongoOps.insert(hotTopic1, "Topics_Week");
			mongoOps.insert(hotTopic2, "Topics_Week");
			mongoOps.insert(hotTopic3, "Topics_Week");
			mongoOps.insert(hotTopic4, "Topics_Week");
			mongoOps.insert(hotTopic5, "Topics_Week");
			mongoOps.insert(hotTopic6, "Topics_Week");
			mongoOps.insert(hotTopic7, "Topics_Week");
			mongoOps.insert(hotTopic8, "Topics_Week");
			mongoOps.insert(hotTopic9, "Topics_Week");
			mongoOps.insert(hotTopic10, "Topics_Week");*/

			//mongoOps.dropCollection("Topics_Week");
			
			//Get list of top 8 topics from the "Topics_Week" table
			List<TopicWrapper> topics = tweetdao.getHotTopics(3, "Name", "Tweets", "Topics_Week");
			
			//Build a frequency list of the top eight topics
			//Frequency list - JSON array:  [{"text":"Ibrox","size":50,"URL":"http://www.rangers.co.uk/"},...]
			JSONArray frequencyList = new JSONArray();
			//For every topic in topic list
			for(TopicWrapper topic : topics){
				//Create a new JSON Object
				JSONObject hotTopic = new JSONObject();
				try {
					//Put the topic's values into the JSON Object
					hotTopic.put("Name", topic.getTopic());
					hotTopic.put("Tweets", topic.getNoOfTweets());
					//Add the object to the frequency list
					frequencyList.put(hotTopic);
				} catch (JSONException e) {
					System.err.print("Exception: GraphsController.getTopicsForWordCloud - JSONObject.put()");
				}
			}
			
			//JSONArray arr = WordCloudHash.gethashedFrequencies(frequencyList);
			//System.out.println(arr.toString());
			System.out.println(frequencyList.toString());
			

			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
}
