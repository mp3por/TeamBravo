package glasgow.teamproject.teamB.Graphs;

import glasgow.teamproject.teamB.mongodb.dao.TweetDAO;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAOImpl;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAOImpl.DateCountPair;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAOImpl.EntityCountPair;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GraphsController {
	
	@Autowired
	private TweetDAO c;
	
	//RETURN LISTS FOR GRAPH DATA------------------------------------------------------------------------------------>>
	
	//WORD CLOUD LIST
	public List<HashMap<String,String>> getCloudList(String timePeriod){
		
		List<HashMap<String,String>> unhashedTopics = new ArrayList<HashMap<String,String>>();
		//Get top 10 topics for the past week
		List<EntityCountPair> topics = new ArrayList<EntityCountPair>();
		
		if(timePeriod.equals("WEEK")){
			topics = c.getTopEntities(TweetDAOImpl.Field.ALL, TweetDAOImpl.TimePeriod.PASTWEEK, 14);
		}else if(timePeriod.equals("MONTH")){
			topics = c.getTopEntities(TweetDAOImpl.Field.ALL, TweetDAOImpl.TimePeriod.PASTMONTH, 14);
		}else{
			System.err.print("Error: " + timePeriod + " Not a valid time period.");
		}
		
		for(EntityCountPair e: topics){
			HashMap<String,String> topic = new HashMap<String,String>();
			String topicDirty = e.getID().replace("[", "");
			String topicClean = topicDirty.replace("]", "");
			topic.put("Name", topicClean);
			topic.put("Tweets", Integer.toString(e.getCount().intValue()));
			unhashedTopics.add(topic);
		}
		
		return WordCloudHash.gethashedFrequencies(unhashedTopics);
	}
	
	//PIE CHART LIST
	public List<HashMap<String,String>> getPieChartList(String timePeriod){
		
		List<HashMap<String,String>> tweets = new ArrayList<>();
		List<EntityCountPair> topics = null;

		//For removing "[]"
		String topic1Clean = "";
		String topic2Clean = "";
		String topic3Clean = "";
		String topic4Clean = "";
		String topic5Clean = "";
		
		if(timePeriod.equals("WEEK")){
			topics = c.getTopEntities(TweetDAOImpl.Field.ALL, TweetDAOImpl.TimePeriod.PASTWEEK, 10);
			//Get top three values and clean up strings, removing []
			String topic1 = topics.get(0).getID().replace("[", "");
			topic1Clean = topic1.replace("]", "");
			String topic2 = topics.get(1).getID().replace("[", "");
			topic2Clean = topic2.replace("]", "");
			String topic3 = topics.get(2).getID().replace("[", "");
			topic3Clean = topic3.replace("]", "");
			String topic4 = topics.get(3).getID().replace("[", "");
			topic4Clean = topic4.replace("]", "");
			String topic5 = topics.get(4).getID().replace("[", "");
			topic5Clean = topic5.replace("]", "");
			
		}else if(timePeriod.equals("MONTH")){
			topics = c.getTopEntities(TweetDAOImpl.Field.ALL, TweetDAOImpl.TimePeriod.PASTMONTH, 10);
			//Get top three values and clean up strings, removing []
			String topic1 = topics.get(0).getID().replace("[", ""); //Starts with get(2) as get(1) is "[]" empty.
			topic1Clean = topic1.replace("]", "");
			String topic2 = topics.get(1).getID().replace("[", "");
			topic2Clean = topic2.replace("]", "");
			String topic3 = topics.get(2).getID().replace("[", "");
			topic3Clean = topic3.replace("]", "");
			String topic4 = topics.get(3).getID().replace("[", ""); //Starts with get(2) as get(1) is "[]" empty.
			topic4Clean = topic4.replace("]", "");
			String topic5 = topics.get(4).getID().replace("[", "");
			topic5Clean = topic5.replace("]", "");

		}else{
			System.err.print("Error: " + timePeriod + " Not a valid time period.");
		}
		
		HashMap<String,String> top1 = new HashMap<String,String>();
		HashMap<String,String> top2 = new HashMap<String,String>();
		HashMap<String,String> top3 = new HashMap<String,String>();
		HashMap<String,String> top4 = new HashMap<String,String>();
		HashMap<String,String> top5 = new HashMap<String,String>();

		//Construct map objects for list - {"Topic": value, "Tweets": value}
		top1.put("Topic", topic1Clean);
		top1.put("Tweets", Integer.toString(topics.get(0).getCount().intValue()));
		top2.put("Topic", topic2Clean);
		top2.put("Tweets", Integer.toString(topics.get(1).getCount().intValue()));
		top3.put("Topic", topic3Clean);
		top3.put("Tweets", Integer.toString(topics.get(2).getCount().intValue()));
		top4.put("Topic", topic4Clean);
		top4.put("Tweets", Integer.toString(topics.get(3).getCount().intValue()));
		top5.put("Topic", topic5Clean);
		top5.put("Tweets", Integer.toString(topics.get(4).getCount().intValue()));
		
		//Add top 3 topics to list
		tweets.add(top1);
		tweets.add(top2);
		tweets.add(top3);
		tweets.add(top4);
		tweets.add(top5);
		
		return tweets;
	}
	
	//GET WEEK OR MONTH LIST FOR DIMPLE GRAPHS
	public List<HashMap<String,String>> getGraphList(String timePeriod){
		

		List<HashMap<String,String>> tweets = new ArrayList<>();
		List<EntityCountPair> topics = null;
		List<DateCountPair> topic1Dates = null;
		List<DateCountPair> topic2Dates = null;
		List<DateCountPair> topic3Dates = null;
		int noOfDays = 0;
		
		//For removing "[]"
		String topic1Clean = "";
		String topic2Clean = "";
		String topic3Clean = "";
		
		
		//Get top 3 topics for the past time period
		if(timePeriod.equals("WEEK")){
			
			topics = c.getTopEntities(TweetDAOImpl.Field.ALL, TweetDAOImpl.TimePeriod.PASTWEEK, 10);
			noOfDays = 7;
			
			//Get top three values and clean up strings, removing []
			String topic1 = topics.get(0).getID().replace("[", "");
			topic1Clean = topic1.replace("]", "");
			String topic2 = topics.get(1).getID().replace("[", "");
			topic2Clean = topic2.replace("]", "");
			String topic3 = topics.get(2).getID().replace("[", "");
			topic3Clean = topic3.replace("]", "");

			topic1Dates = c.getEntitiyTrend(topic1Clean,noOfDays);
			topic2Dates = c.getEntitiyTrend(topic2Clean,noOfDays);
			topic3Dates = c.getEntitiyTrend(topic3Clean,noOfDays);
			
		}else if(timePeriod.equals("MONTH")){
			
			topics = c.getTopEntities(TweetDAOImpl.Field.ALL, TweetDAOImpl.TimePeriod.PASTMONTH, 10);
			
			noOfDays = 30;
			
			//Get top three values and clean up strings, removing []
			String topic1 = topics.get(1).getID().replace("[", ""); //Starts with get(1) as get(0) is "[]" empty.
			topic1Clean = topic1.replace("]", "");
			String topic2 = topics.get(2).getID().replace("[", "");
			topic2Clean = topic2.replace("]", "");
			String topic3 = topics.get(3).getID().replace("[", "");
			topic3Clean = topic3.replace("]", "");
			
			topic1Dates = c.getEntitiyTrend(topic1Clean,noOfDays);
			topic2Dates = c.getEntitiyTrend(topic2Clean,noOfDays);
			topic3Dates = c.getEntitiyTrend(topic3Clean,noOfDays);
			
		}else{
			System.err.print("Error: " + timePeriod + " Not a valid time period.");

		}
		
		//Add "topic" in each object and add to separate lists
		List<HashMap<String,String>> topic1List = new ArrayList<>();
		List<HashMap<String,String>> topic2List = new ArrayList<>();
		List<HashMap<String,String>> topic3List = new ArrayList<>();
		
		for(DateCountPair t1 : topic1Dates){
			HashMap<String,String> topicValue = new HashMap<String,String>();
			topicValue.put("Day", t1.getDate());
			topicValue.put("Tweets",Integer.toString(t1.getCount()));
			topicValue.put("Topic", topic1Clean);
			topic1List.add(topicValue);
		}
		
		for(DateCountPair t2 : topic2Dates){
			HashMap<String,String> topicValue = new HashMap<String,String>();
			topicValue.put("Day", t2.getDate());
			topicValue.put("Tweets",Integer.toString(t2.getCount()));
			topicValue.put("Topic", topic2Clean);
			topic2List.add(topicValue);
		}
		
		for(DateCountPair t3 : topic3Dates){
			HashMap<String,String> topicValue = new HashMap<String,String>();
			topicValue.put("Day", t3.getDate());
			topicValue.put("Tweets",Integer.toString(t3.getCount()));
			topicValue.put("Topic", topic3Clean);
			topic3List.add(topicValue);
		}
		
		//Construct list in correct format for graph
		for(int i = 0; i < noOfDays; i++){
			tweets.add(topic1List.get(i));
			tweets.add(topic2List.get(i));
			tweets.add(topic3List.get(i));
		}
		
		return tweets;
	}
	
	//Return Data --------------------------------------------------------------------------------------------->>
	
	//Returns DATA for the dimple bar and line graphs, either week or month
	@RequestMapping("/dimple/{timeScale}")
	@ResponseBody
	public List<HashMap<String, String>> getDimpleData(@PathVariable("timeScale") String timeScale){
		
		List<HashMap<String,String>> tweetsForDimple = new ArrayList<HashMap<String,String>>();
		if(timeScale.equals("WEEK")){
			tweetsForDimple = getGraphList("WEEK");
		}else if(timeScale.equals("MONTH")){
			tweetsForDimple = getGraphList("MONTH");
		}
		return tweetsForDimple;
	}
	
	
	//Returns DATA for the pie chart
	@RequestMapping("/pie/{timeScale}")
	@ResponseBody
	public List<HashMap<String,String>> getPieData(@PathVariable("timeScale") String timeScale){
		
		List<HashMap<String,String>> tweetsForPie = new ArrayList<HashMap<String,String>>();
		if(timeScale.equals("WEEK")){
			tweetsForPie = getPieChartList("WEEK");
		}else if(timeScale.equals("MONTH")){
			tweetsForPie = getPieChartList("MONTH");
		}
		return tweetsForPie;
	}
	
	//Returns DATA for the cloud
	@RequestMapping("/cloud/{timeScale}")
	@ResponseBody
	public List<HashMap<String,String>> getCloudData(@PathVariable("timeScale") String timeScale){
		
		List<HashMap<String,String>> tweetsForCloud = new ArrayList<HashMap<String,String>>();
		if(timeScale.equals("WEEK")){
			tweetsForCloud = getCloudList("WEEK");
		}else if(timeScale.equals("MONTH")){
			tweetsForCloud = getCloudList("MONTH");
		}
		return tweetsForCloud;
	}
	
	
	//RETURN VIEW-------------------------------------------------------------------------------------------------->>
	@RequestMapping("/graphView/{tileNo}")
	public ModelAndView getDimpleView(@PathVariable("tileNo") String tileNo){
		ModelAndView view = new ModelAndView("graphView");
		view.addObject("TileNumber", tileNo);
		return view;
	}
	
	//RETURN SETTINGS-------------------------------------------------------------------------------------------------->>
	@RequestMapping("/getSettings")
	public ModelAndView getGraphSettings(){
		ModelAndView view = new ModelAndView("graphSettings");
		return view;
	}
}