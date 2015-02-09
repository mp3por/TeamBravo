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
			
			/*
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
			System.out.println(frequencyList.toString());*/
			
			
			
			/////////////////////////////////////////////////////////////////////////
			//TESTING LINE GRAPH
			/*
			//mongoOps.createCollection("Topics_Year");
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
			JSONObject hotTopic11 = new JSONObject();
			JSONObject hotTopic12 = new JSONObject();
			JSONObject hotTopic13 = new JSONObject();
			JSONObject hotTopic14 = new JSONObject();
			JSONObject hotTopic15 = new JSONObject();
			JSONObject hotTopic16 = new JSONObject();
			JSONObject hotTopic17 = new JSONObject();
			JSONObject hotTopic18 = new JSONObject();
			JSONObject hotTopic19 = new JSONObject();
			JSONObject hotTopic20 = new JSONObject();
			JSONObject hotTopic21 = new JSONObject();
			JSONObject hotTopic22 = new JSONObject();
			JSONObject hotTopic23 = new JSONObject();
			JSONObject hotTopic24 = new JSONObject();
			JSONObject hotTopic25 = new JSONObject();
			JSONObject hotTopic26 = new JSONObject();
			JSONObject hotTopic27 = new JSONObject();
			JSONObject hotTopic28 = new JSONObject();
			JSONObject hotTopic29 = new JSONObject();
			JSONObject hotTopic30 = new JSONObject();
			JSONObject hotTopic31 = new JSONObject();
			JSONObject hotTopic32 = new JSONObject();
			JSONObject hotTopic33 = new JSONObject();
			JSONObject hotTopic34 = new JSONObject();
			JSONObject hotTopic35 = new JSONObject();
			JSONObject hotTopic36 = new JSONObject();
			
			hotTopic1.put("Date", "01/01/2011");
			hotTopic1.put("Month","Jan-11");
			hotTopic1.put("Tweets", new Integer(40000));
			hotTopic1.put("Topic", "IndyRef");
			hotTopic2.put("Date", "01/01/2011");
			hotTopic2.put("Month","Jan-11");
			hotTopic2.put("Tweets", new Integer(50000));
			hotTopic2.put("Topic", "SNP");
			hotTopic3.put("Date", "01/01/2011");
			hotTopic3.put("Month","Jan-11");
			hotTopic3.put("Tweets", new Integer(70000));
			hotTopic3.put("Topic", "Nicola Sturgeon");
			hotTopic4.put("Date", "01/02/2011");
			hotTopic4.put("Month","Feb-11");
			hotTopic4.put("Tweets", new Integer(80000));
			hotTopic4.put("Topic", "IndyRef");
			hotTopic5.put("Date", "01/02/2011");
			hotTopic5.put("Month","Feb-11");
			hotTopic5.put("Tweets", new Integer(60000));
			hotTopic5.put("Topic", "SNP");
			hotTopic6.put("Date", "01/02/2011");
			hotTopic6.put("Month","Feb-11");
			hotTopic6.put("Tweets", new Integer(65000));
			hotTopic6.put("Topic", "Nicola Sturgeon");
			hotTopic7.put("Date", "01/03/2011");
			hotTopic7.put("Month","Mar-11");
			hotTopic7.put("Tweets", new Integer(90000));
			hotTopic7.put("Topic", "IndyRef");
			hotTopic8.put("Date", "01/03/2011");
			hotTopic8.put("Month","Mar-11");
			hotTopic8.put("Tweets", new Integer(70000));
			hotTopic8.put("Topic", "SNP");
			hotTopic9.put("Date", "01/03/2011");
			hotTopic9.put("Month","Mar-11");
			hotTopic9.put("Tweets", new Integer(40000));
			hotTopic9.put("Topic", "Nicola Sturgeon");
			hotTopic10.put("Date", "01/04/2011");
			hotTopic10.put("Month","Apr-11");
			hotTopic10.put("Tweets", new Integer(40000));
			hotTopic10.put("Topic", "IndyRef");
			hotTopic11.put("Date", "01/04/2011");
			hotTopic11.put("Month","Apr-11");
			hotTopic11.put("Tweets", new Integer(70000));
			hotTopic11.put("Topic", "SNP");
			hotTopic12.put("Date", "01/04/2011");
			hotTopic12.put("Month","Apr-11");
			hotTopic12.put("Tweets", new Integer(20000));
			hotTopic12.put("Topic", "Nicola Sturgeon");
			hotTopic13.put("Date", "01/05/2011");
			hotTopic13.put("Month","May-11");
			hotTopic13.put("Tweets", new Integer(100000));
			hotTopic13.put("Topic", "IndyRef");
			hotTopic14.put("Date", "01/05/2011");
			hotTopic14.put("Month","May-11");
			hotTopic14.put("Tweets", new Integer(80000));
			hotTopic14.put("Topic", "SNP");
			hotTopic15.put("Date", "01/05/2011");
			hotTopic15.put("Month","May-11");
			hotTopic15.put("Tweets", new Integer(55000));
			hotTopic15.put("Topic", "Nicola Sturgeon");
			hotTopic16.put("Date", "01/06/2011");
			hotTopic16.put("Month","Jun-11");
			hotTopic16.put("Tweets", new Integer(40000));
			hotTopic16.put("Topic", "IndyRef");
			hotTopic17.put("Date", "01/06/2011");
			hotTopic17.put("Month","Jun-11");
			hotTopic17.put("Tweets", new Integer(20000));
			hotTopic17.put("Topic", "SNP");
			hotTopic18.put("Date", "01/06/2011");
			hotTopic18.put("Month","Jun-11");
			hotTopic18.put("Tweets", new Integer(85000));
			hotTopic18.put("Topic", "Nicola Sturgeon");
			hotTopic19.put("Date", "01/07/2011");
			hotTopic19.put("Month","Jul-11");
			hotTopic19.put("Tweets", new Integer(30000));
			hotTopic19.put("Topic", "IndyRef");
			hotTopic20.put("Date", "01/07/2011");
			hotTopic20.put("Month","Jul-11");
			hotTopic20.put("Tweets", new Integer(60000));
			hotTopic20.put("Topic", "SNP");
			hotTopic21.put("Date", "01/07/2011");
			hotTopic21.put("Month","Jul-11");
			hotTopic21.put("Tweets", new Integer(80000));
			hotTopic21.put("Topic", "Nicola Sturgeon");
			hotTopic22.put("Date", "01/08/2011");
			hotTopic22.put("Month","Aug-11");
			hotTopic22.put("Tweets", new Integer(60000));
			hotTopic22.put("Topic", "IndyRef");
			hotTopic23.put("Date", "01/08/2011");
			hotTopic23.put("Month","Aug-11");
			hotTopic23.put("Tweets", new Integer(90000));
			hotTopic23.put("Topic", "SNP");
			hotTopic24.put("Date", "01/08/2011");
			hotTopic24.put("Month","Aug-11");
			hotTopic24.put("Tweets", new Integer(100000));
			hotTopic24.put("Topic", "Nicola Sturgeon");
			hotTopic25.put("Date", "01/09/2011");
			hotTopic25.put("Month","Sep-11");
			hotTopic25.put("Tweets", new Integer(40000));
			hotTopic25.put("Topic", "IndyRef");
			hotTopic26.put("Date", "01/09/2011");
			hotTopic26.put("Month","Sep-11");
			hotTopic26.put("Tweets", new Integer(70000));
			hotTopic26.put("Topic", "SNP");
			hotTopic27.put("Date", "01/09/2011");
			hotTopic27.put("Month","Sep-11");
			hotTopic27.put("Tweets", new Integer(50000));
			hotTopic27.put("Topic", "Nicola Sturgeon");
			hotTopic28.put("Date", "01/10/2011");
			hotTopic28.put("Month","Oct-11");
			hotTopic28.put("Tweets", new Integer(80000));
			hotTopic28.put("Topic", "IndyRef");
			hotTopic29.put("Date", "01/10/2011");
			hotTopic29.put("Month","Oct-11");
			hotTopic29.put("Tweets", new Integer(90000));
			hotTopic29.put("Topic", "SNP");
			hotTopic30.put("Date", "01/10/2011");
			hotTopic30.put("Month","Oct-11");
			hotTopic30.put("Tweets", new Integer(55000));
			hotTopic30.put("Topic", "Nicola Sturgeon");
			hotTopic31.put("Date", "01/11/2011");
			hotTopic31.put("Month","Nov-11");
			hotTopic31.put("Tweets", new Integer(90000));
			hotTopic31.put("Topic", "IndyRef");
			hotTopic32.put("Date", "01/11/2011");
			hotTopic32.put("Month","Nov-11");
			hotTopic32.put("Tweets", new Integer(50000));
			hotTopic32.put("Topic", "SNP");
			hotTopic33.put("Date", "01/11/2011");
			hotTopic33.put("Month","Nov-11");
			hotTopic33.put("Tweets", new Integer(55000));
			hotTopic33.put("Topic", "Nicola Sturgeon");
			hotTopic34.put("Date", "01/12/2011");
			hotTopic34.put("Month","Dec-11");
			hotTopic34.put("Tweets", new Integer(70000));
			hotTopic34.put("Topic", "IndyRef");
			hotTopic35.put("Date", "01/12/2011");
			hotTopic35.put("Month","Dec-11");
			hotTopic35.put("Tweets", new Integer(80000));
			hotTopic35.put("Topic", "SNP");
			hotTopic36.put("Date", "01/12/2011");
			hotTopic36.put("Month","Dec-11");
			hotTopic36.put("Tweets", new Integer(40000));
			hotTopic36.put("Topic", "Nicola Sturgeon");
			
			
			mongoOps.insert(hotTopic1, "Topics_Year");
			mongoOps.insert(hotTopic2, "Topics_Year");
			mongoOps.insert(hotTopic3, "Topics_Year");
			mongoOps.insert(hotTopic4, "Topics_Year");
			mongoOps.insert(hotTopic5, "Topics_Year");
			mongoOps.insert(hotTopic6, "Topics_Year");
			mongoOps.insert(hotTopic7, "Topics_Year");
			mongoOps.insert(hotTopic8, "Topics_Year");
			mongoOps.insert(hotTopic9, "Topics_Year");
			mongoOps.insert(hotTopic10, "Topics_Year");
			mongoOps.insert(hotTopic11, "Topics_Year");
			mongoOps.insert(hotTopic12, "Topics_Year");
			mongoOps.insert(hotTopic13, "Topics_Year");
			mongoOps.insert(hotTopic14, "Topics_Year");
			mongoOps.insert(hotTopic15, "Topics_Year");
			mongoOps.insert(hotTopic16, "Topics_Year");
			mongoOps.insert(hotTopic17, "Topics_Year");
			mongoOps.insert(hotTopic18, "Topics_Year");
			mongoOps.insert(hotTopic19, "Topics_Year");
			mongoOps.insert(hotTopic20, "Topics_Year");
			mongoOps.insert(hotTopic21, "Topics_Year");
			mongoOps.insert(hotTopic22, "Topics_Year");
			mongoOps.insert(hotTopic23, "Topics_Year");
			mongoOps.insert(hotTopic24, "Topics_Year");
			mongoOps.insert(hotTopic25, "Topics_Year");
			mongoOps.insert(hotTopic26, "Topics_Year");
			mongoOps.insert(hotTopic27, "Topics_Year");
			mongoOps.insert(hotTopic28, "Topics_Year");
			mongoOps.insert(hotTopic29, "Topics_Year");
			mongoOps.insert(hotTopic30, "Topics_Year");
			mongoOps.insert(hotTopic31, "Topics_Year");
			mongoOps.insert(hotTopic32, "Topics_Year");
			mongoOps.insert(hotTopic33, "Topics_Year");
			mongoOps.insert(hotTopic34, "Topics_Year");
			mongoOps.insert(hotTopic35, "Topics_Year");
			mongoOps.insert(hotTopic36, "Topics_Year");*/
			//mongoOps.dropCollection("Topics_Year");
			
			/*
			//Get list of top hot topics from the "Topics_Year" table
			List<TopicWrapper> topics = tweetdao.getTweetsForGraphLine("Date", "Month", "Tweets", "Topic", "Topics_Year");
			
			//Build a data Json array
			JSONArray topicData = new JSONArray();
			//For every topic in topic list
			for(TopicWrapper topic : topics){
				//Create a new JSON Object
				JSONObject hotTopic = new JSONObject();
				try {
					//Put the topic's values into the JSON Object
					hotTopic.put("Date", topic.getDate());
					hotTopic.put("Month", topic.getMonth());
					hotTopic.put("Tweets", topic.getNoOfTweets());
					hotTopic.put("Topic", topic.getTopic());
					//Add the object to the frequency list
					topicData.put(hotTopic);
				} catch (JSONException e) {
					System.err.print("Exception: GraphsController.getTopicsForLineGraph - JSONObject.put()");
				}
			}
			
			System.out.println(topicData.toString());*/
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
}
