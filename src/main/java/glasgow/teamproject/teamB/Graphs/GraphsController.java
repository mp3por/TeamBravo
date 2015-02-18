package glasgow.teamproject.teamB.Graphs;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import glasgow.teamproject.teamB.Counter.Counter;
import glasgow.teamproject.teamB.Counter.Counter.DateCountPair;
import glasgow.teamproject.teamB.Counter.Counter.EntityCountPair;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAO;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAOImpl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.data.convert.EntityConverter;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mongodb.MongoClient;


@Controller
public class GraphsController {

	/*
	 * Database info - This code may need moved to DAO
	 
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
	}*/
	
	/*
	 * Wordcloud for main/Home
	 * 
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
	}*/

	
	//Get Word Cloud JSP
	@RequestMapping("/wordCloud")
	public ModelAndView getWordCloud(){
		//setUpDBInfo();
		//JSONArray frequencyList = getTopicsForWordCloud();
		
		//Instantiate Counter and call getTopicsForWordPie(10);////////////////////////
		ModelAndView model = new ModelAndView("WordCloud");
		//model.addObject("frequencyList", frequencyList);
		return model;
	}
	
	/**
	 * Graph for main/SpecificTopic showing topic volume over
	 * past week
	 * */
	public JSONArray getGraphWeekData(){

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
		c.mergingMapReduce(Counter.TimePeriod.PASTWEEK);
		
		JSONArray tweetsForWeek = new JSONArray();
		
		//Get top 3 topics for the past week
		List<EntityCountPair> top3TopicsPastWeek = c.getTopEntities(Counter.Field.ALL, Counter.TimePeriod.PASTWEEK, 8);
		
		//Clean up the strings removing "[" and "]" characters and disregard first 3 values [get 3,4,7]
		String topic1 = top3TopicsPastWeek.get(4).getID().replace("[", "");
		String topic1Clean = topic1.replace("]", "");
		String topic2 = top3TopicsPastWeek.get(5).getID().replace("[", "");
		String topic2Clean = topic2.replace("]", "");
		String topic3 = top3TopicsPastWeek.get(6).getID().replace("[", "");
		String topic3Clean = topic3.replace("]", "");
		
		//Get lists of past week number of tweets for each topic)
		List<DateCountPair> topic1PastWeek = c.getEntitiyTrend(topic1Clean,7);
		List<DateCountPair> topic2PastWeek = c.getEntitiyTrend(topic2Clean,7);
		List<DateCountPair> topic3PastWeek = c.getEntitiyTrend(topic3Clean,7);
		
		//Add "topic" in each object and add to seperate lists
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
			tweetsForWeek.put(topic1List.get(i));
			tweetsForWeek.put(topic2List.get(i));
			tweetsForWeek.put(topic3List.get(i));
		}
		
		return tweetsForWeek;
	}
	
	//Get graphWeek graph
	@RequestMapping("/graphWeek")
	public ModelAndView getGraphWeek(){
		JSONArray tweetsForWeek = getGraphWeekData();
		ModelAndView mv = new ModelAndView("graphWeek");
		mv.addObject("tweetsForWeek", tweetsForWeek);
		return mv;
	}
	
	/**
	 * Graph for main/SpecificTopic showing topic volume over
	 * past month
	 * */
	public JSONArray getGraphMonthData(){
		
		
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
		
		
		JSONArray tweetsForMonth = new JSONArray();
		//Get top 3 topics for the past week
		List<EntityCountPair> top3TopicsPastMonth = c.getTopEntities(Counter.Field.ALL, Counter.TimePeriod.PASTMONTH, 8);
		
		//Clean up the strings removing "[" and "]" characters and disregard first 3 values
		String topic1 = top3TopicsPastMonth.get(4).getID().replace("[", "");
		String topic1Clean = topic1.replace("]", "");
		String topic2 = top3TopicsPastMonth.get(5).getID().replace("[", "");
		String topic2Clean = topic2.replace("]", "");
		String topic3 = top3TopicsPastMonth.get(6).getID().replace("[", "");
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
		
		return tweetsForMonth ;
	}
	
	//Get month graph
	@RequestMapping("/graphMonth")
	public ModelAndView getGraphMonth(){
		JSONArray tweetsForMonth = getGraphMonthData();
		ModelAndView mv = new ModelAndView("graphMonth");
		mv.addObject("tweetsForMonth", tweetsForMonth);
		return mv;
	}
	
	public JSONArray getPieChartData(){
		
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
		c.mergingMapReduce(Counter.TimePeriod.PASTWEEK);
		
		JSONArray tweetsForPie = new JSONArray();
		
		//Get top 3 topics for the past week
		List<EntityCountPair> top3TopicsPastWeek = c.getTopEntities(Counter.Field.ALL, Counter.TimePeriod.PASTWEEK, 10);
		
		//Clean up the strings removing "[" and "]" characters and disregard first 3 values
		String topic1 = top3TopicsPastWeek.get(4).getID().replace("[", "");
		String topic1Clean = topic1.replace("]", "");
		String topic2 = top3TopicsPastWeek.get(5).getID().replace("[", "");
		String topic2Clean = topic2.replace("]", "");
		String topic3 = top3TopicsPastWeek.get(6).getID().replace("[", "");
		String topic3Clean = topic3.replace("]", "");
		
		JSONObject top1 = new JSONObject();
		JSONObject top2 = new JSONObject();
		JSONObject top3 = new JSONObject();
		
		//Construct json objects for json array - {"Topic": value, "Tweets": value}
		top1.put("Topic", topic1Clean);
		top1.put("Tweets", top3TopicsPastWeek.get(4).getCount().intValue());
		top2.put("Topic", topic2Clean);
		top2.put("Tweets", top3TopicsPastWeek.get(5).getCount().intValue());
		top3.put("Topic", topic3Clean);
		top3.put("Tweets", top3TopicsPastWeek.get(6).getCount().intValue());
		
		//Add top 3 topic jsons to array
		tweetsForPie.put(top1);
		tweetsForPie.put(top2);
		tweetsForPie.put(top3);
		
		return tweetsForPie;
	}
	
	//Get Pie Chart
	@RequestMapping("/pieChart")
	public ModelAndView getPieChart(){
		JSONArray tweetsForPie = getPieChartData();
		ModelAndView mv = new ModelAndView("pieChart");
		mv.addObject("tweetsForPie", tweetsForPie);
		return mv;
	}
	
	
	public JSONArray getHashData(){
		
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
		c.mergingMapReduce(Counter.TimePeriod.PASTWEEK);
		
		JSONArray hashTags = new JSONArray();
		List<EntityCountPair> l = c.getTopEntities(Counter.Field.HASHTAG, Counter.TimePeriod.PASTWEEK, 10);
		
		for(EntityCountPair e : l){
			JSONObject hashtag = new JSONObject();
			String hash = e.getID().replace("[", "");
			String hashClean = hash.replace("]", "");
			hashtag.put("hashTag", hashClean);
			hashTags.put(hash);
		}

		return hashTags;
	}
	
	//Get Hash Tags
	@RequestMapping("/hashTags")
	public ModelAndView getHashTags(){
		JSONArray hashTags = getHashData();
		ModelAndView mv = new ModelAndView("hashTags");
		mv.addObject("hashTags", hashTags);
		return mv;
	}
	

	//Get All graphs
	@RequestMapping("/allGraphs")
	public ModelAndView getAllGraphs(){
		ModelAndView mv = new ModelAndView("graphs");
		return mv;
	}
	

}