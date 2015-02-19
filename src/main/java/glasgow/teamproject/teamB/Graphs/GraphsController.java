package glasgow.teamproject.teamB.Graphs;

import java.net.UnknownHostException;
import java.util.List;

import glasgow.teamproject.teamB.mongodb.dao.TweetDAO;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAOImpl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
		List<TopicWrapper> topics = tweetdao.getHotTopics(10, "Name", "Tweets", "Topics_Week");
		System.out.println("Topics in graph controller:" + topics);
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
		System.out.println("JSON ARRAY of hashed frequency list in graphs controller: " + hashedFrequencyList.toString());
		return hashedFrequencyList;
	}
	
	
	
	@RequestMapping("/getAll")
	public ModelAndView getGraphs(){
		
		ModelAndView model = new ModelAndView("graphs-all");
		
		return model;
	}
	
	@RequestMapping("/ajax")
	public ModelAndView ajax(){
		ModelAndView mv = new ModelAndView("ajax-graphs");
		return mv;
	}
	
	//Get Word Cloud JSP
	@RequestMapping("/wordCloud")
	public ModelAndView getWordCloud(){
		setUpDBInfo();
		JSONArray frequencyList = getTopicsForWordCloud();
		ModelAndView model = new ModelAndView("WordCloud");
		model.addObject("frequencyList", frequencyList);
		return model;
	}
	
}