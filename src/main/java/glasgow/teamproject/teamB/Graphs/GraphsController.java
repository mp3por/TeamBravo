package glasgow.teamproject.teamB.Graphs;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import glasgow.teamproject.teamB.mongodb.dao.TweetDAO;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAOImpl;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import twitter4j.JSONArray;
import twitter4j.JSONException;
import twitter4j.JSONObject;

import com.mongodb.MongoClient;


@Controller
public class GraphsController {
	
	/*
	 * Database info
	 */
	private final String DB_NAME = "tweetsTest";
	private final String TWEETS_COLLECTION = "tweets"; //table
	private final String MONGO_HOST = "localhost";
	private final int MONGO_PORT = 27017;
	private TweetDAO tweetdao;
	private MongoOperations mongoOps;
	
	//Sets up client and mongo operations objects
	private void setUpDBInfo(){
		try{
			MongoClient mongo = new MongoClient(MONGO_HOST, MONGO_PORT);
			mongoOps = new MongoTemplate(mongo, DB_NAME);
			tweetdao = new TweetDAOImpl(mongoOps);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	private JSONArray getTopicsForWordCloud(){
		//Get list of top 8 topics from the "Topics_Week" table
		List<TopicWrapper> topics = tweetdao.getHotTopics(8, "Name", "Tweets", "Topics_Week");
		
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
		JSONArray hashedFrequencyList = WordCloudHash.gethashedFrequencies(frequencyList);
		return hashedFrequencyList;
	}
	
	private JSONArray getTopicsForPieChart(){
		//This also works by calling getTopicsForWordCloud()
		//As the javascript in PieChart.jsp selects first 3 positions of frequency array
		
		//Get list of top 3 topics from the "Topics_Week" table
		List<TopicWrapper> topics = tweetdao.getHotTopics(3, "Name", "Tweets", "Topics_Week");
		
		//Build a frequency list of the top 3 topics
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
				System.err.print("Exception: GraphsController.getTopicsForPieChart - JSONObject.put()");
			}
		}
		return frequencyList;
	}
	
	private JSONArray getTopicsForLineGraph(){
		
		//Get list of top hot topics from the "Topics_Year" table
		List<TopicWrapper> topics = tweetdao.getTweetsForGraphLine("Date", "Month", "Tweets", "Topic", "Topics_Year");
		
		//Create a data Json array
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
		return topicData;
	}
	
	
	@RequestMapping("/getAll")
	public ModelAndView getGraphs(){
		setUpDBInfo();
		ModelAndView model = new ModelAndView("graphs-all");
		return model;
	}
	
	@RequestMapping("/lineGraph")
	public ModelAndView getLineGraph(){
		setUpDBInfo();
		JSONArray topicData = getTopicsForLineGraph();
		ModelAndView model = new ModelAndView("LineGraph");
		model.addObject("topicData", topicData);
		return model;
	}
	
	@RequestMapping("/pieChart")
	public ModelAndView getPieChart(){
		setUpDBInfo();
		JSONArray frequencyList = getTopicsForPieChart();
		ModelAndView model = new ModelAndView("PieChart");
		model.addObject("frequencyList", frequencyList);
		return model;
	}
	
	@RequestMapping("/wordCloud")
	public ModelAndView getWordCloud(){
		setUpDBInfo();
		JSONArray frequencyList = getTopicsForWordCloud();
		ModelAndView model = new ModelAndView("WordCloud");
		model.addObject("frequencyList", frequencyList);
		return model;
	}
	
	@RequestMapping("/barChart")
	public ModelAndView getBarChart(){
		setUpDBInfo();
		ModelAndView model = new ModelAndView("BarChart");
		return model;
	}
	
}