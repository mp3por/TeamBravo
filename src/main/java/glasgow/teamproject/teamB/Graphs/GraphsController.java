package glasgow.teamproject.teamB.Graphs;


import glasgow.teamproject.teamB.Counter.Counter;
import glasgow.teamproject.teamB.Counter.Counter.DateCountPair;
import glasgow.teamproject.teamB.Counter.Counter.EntityCountPair;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GraphsController {
	
	private Counter c;
	
	@RequestMapping("/graphInit/{timeScale}")
	@ResponseBody
	public void graphInit(@PathVariable("timeScale") String timeScale){
		c = new Counter();
		/*c.dailyMapReduce(new Date());
		if(timeScale.equals("WEEK")){
			c.mergingMapReduce(Counter.TimePeriod.PASTWEEK);
		}else if(timeScale.equals("MONTH")){
			c.mergingMapReduce(Counter.TimePeriod.PASTMONTH);
		}else{
			System.err.print("Error: " + timeScale + " Not a valid time period.");
		}*/ //This only needs to be called daily or when the twitter stream is running
	}

	
	//RETURN LISTS FOR GRAPHS---------------------------------------------------------------------------------------->>
	
	//WORD CLOUD LIST
	public List<HashMap<String,String>> getTopicsForWordCloudList(){
		
		List<HashMap<String,String>> unhashedTopics = new ArrayList<HashMap<String,String>>();
		//Get top 10 topics for the past week
		List<EntityCountPair> topTopicsPastWeek = c.getTopEntities(Counter.Field.ALL, Counter.TimePeriod.PASTWEEK, 14);
		
		for(EntityCountPair e: topTopicsPastWeek){
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
	public List<HashMap<String,String>> getPieChartList(){

		List<HashMap<String,String>> tweetsForPie = new ArrayList<>();
		//Get top 3 topics for the past week
		List<EntityCountPair> top3TopicsPastWeek = c.getTopEntities(Counter.Field.ALL, Counter.TimePeriod.PASTWEEK, 10);
		System.out.println(top3TopicsPastWeek);
		//Clean up the strings removing "[" and "]" characters and disregard first 3 values
		String topic1 = top3TopicsPastWeek.get(2).getID().replace("[", "");
		String topic1Clean = topic1.replace("]", "");
		String topic2 = top3TopicsPastWeek.get(4).getID().replace("[", "");
		String topic2Clean = topic2.replace("]", "");
		String topic3 = top3TopicsPastWeek.get(6).getID().replace("[", "");
		String topic3Clean = topic3.replace("]", "");
		
		HashMap<String,String> top1 = new HashMap<String,String>();
		HashMap<String,String> top2 = new HashMap<String,String>();
		HashMap<String,String> top3 = new HashMap<String,String>();
		
		//Construct json objects for json array - {"Topic": value, "Tweets": value}
		top1.put("Topic", topic1Clean);
		top1.put("Tweets", Integer.toString(top3TopicsPastWeek.get(2).getCount().intValue()));
		top2.put("Topic", topic2Clean);
		top2.put("Tweets", Integer.toString(top3TopicsPastWeek.get(4).getCount().intValue()));
		top3.put("Topic", topic3Clean);
		top3.put("Tweets", Integer.toString(top3TopicsPastWeek.get(6).getCount().intValue()));
		
		//Add top 3 topic jsons to array
		tweetsForPie.add(top1);
		tweetsForPie.add(top2);
		tweetsForPie.add(top3);
		
		return tweetsForPie;
	}
	
	//Get Pie Chart - This may not be needed - used for testing c3 in new layout
	@RequestMapping("/graphPie")
	public ModelAndView getPieChart(){
		List<HashMap<String, String>> TweetsForPie = new ArrayList<HashMap<String, String>>();
		TweetsForPie = getPieChartList();
		ModelAndView mv = new ModelAndView("graphPie");
		mv.addObject("TweetsForPie", TweetsForPie);
		return mv;
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
			
			topics = c.getTopEntities(Counter.Field.ALL, Counter.TimePeriod.PASTWEEK, 10);
			noOfDays = 7;
			
			//Get top three values and clean up strings, removing []
			String topic1 = topics.get(1).getID().replace("[", "");
			topic1Clean = topic1.replace("]", "");
			String topic2 = topics.get(2).getID().replace("[", "");
			topic2Clean = topic2.replace("]", "");
			String topic3 = topics.get(3).getID().replace("[", "");
			topic3Clean = topic3.replace("]", "");

			topic1Dates = c.getEntitiyTrend(topic1Clean,7);
			topic2Dates = c.getEntitiyTrend(topic2Clean,7);
			topic3Dates = c.getEntitiyTrend(topic3Clean,7);
			
		}else if(timePeriod.equals("MONTH")){
			
			topics = c.getTopEntities(Counter.Field.ALL, Counter.TimePeriod.PASTMONTH, 10);
			noOfDays = 30;
			
			//Get top three values and clean up strings, removing []
			String topic1 = topics.get(2).getID().replace("[", ""); //Starts with get(2) as get(1) is "[]" empty.
			topic1Clean = topic1.replace("]", "");
			String topic2 = topics.get(3).getID().replace("[", "");
			topic2Clean = topic2.replace("]", "");
			String topic3 = topics.get(4).getID().replace("[", "");
			topic3Clean = topic3.replace("]", "");
			
			topic1Dates = c.getEntitiyTrend(topic1Clean,30);
			topic2Dates = c.getEntitiyTrend(topic2Clean,30);
			topic3Dates = c.getEntitiyTrend(topic3Clean,30);
			
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
			tweetsForPie = getPieChartList();
		}else if(timeScale.equals("MONTH")){
			tweetsForPie = getPieChartList();
			//TODO change this so that you can specify week or month with pie data
			//Add tweetsForPieMonthList
		}
		return tweetsForPie;
	}
	
	//Returns DATA for the cloud
	@RequestMapping("/cloud/{timeScale}")
	@ResponseBody
	public List<HashMap<String,String>> getCloudData(@PathVariable("timeScale") String timeScale){
		
		List<HashMap<String,String>> tweetsForCloud = new ArrayList<HashMap<String,String>>();
		if(timeScale.equals("WEEK")){
			tweetsForCloud = getTopicsForWordCloudList();
		}else if(timeScale.equals("MONTH")){
			tweetsForCloud = getTopicsForWordCloudList();
			//TODO change this so that you can specify week or month with pie data
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//OLD----------------------------------------------->>
	
	/*public JSONArray getTopicsForWordCloud(){
	
	JSONArray unhashedTopics = new JSONArray();
	//Get top 10 topics for the past week
	List<EntityCountPair> topTopicsPastWeek = c.getTopEntities(Counter.Field.ALL, Counter.TimePeriod.PASTWEEK, 14);
	
	for(EntityCountPair e: topTopicsPastWeek){
		JSONObject topic = new JSONObject();
		String topicDirty = e.getID().replace("[", "");
		String topicClean = topicDirty.replace("]", "");
		topic.put("Name", topicClean);
		topic.put("Tweets", e.getCount().intValue());
		unhashedTopics.put(topic);
	}
	
	 return WordCloudHash.gethashedFrequencies(unhashedTopics);
}*/

/*
//Get Word Cloud JSP
@RequestMapping("/wordCloud")
public ModelAndView getWordCloud(){
	JSONArray frequencyList = getTopicsForWordCloud();
	ModelAndView model = new ModelAndView("WordCloud");
	model.addObject("wordCloudList", frequencyList);
	return model;
}*/

/*
public JSONArray getGraphWeekData(){

	JSONArray tweetsForWeek = new JSONArray();
	//Get top 3 topics for the past week
	System.out.println("Getting top 3 entities from counter");
	List<EntityCountPair> top3TopicsPastWeek = c.getTopEntities(Counter.Field.ALL, Counter.TimePeriod.PASTWEEK, 10);
	System.out.println(top3TopicsPastWeek.toString());
	
	//Clean up the strings removing "[" and "]" characters and disregard first 3 values [get 3,4,7]
	String topic1 = top3TopicsPastWeek.get(2).getID().replace("[", "");
	String topic1Clean = topic1.replace("]", "");
	String topic2 = top3TopicsPastWeek.get(4).getID().replace("[", "");
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
}*/

/*
public JSONArray getGraphMonthData(){

	JSONArray tweetsForMonth = new JSONArray();
	//Get top 3 topics for the past week
	List<EntityCountPair> top3TopicsPastMonth = c.getTopEntities(Counter.Field.ALL, Counter.TimePeriod.PASTMONTH, 10);
	
	//Clean up the strings removing "[" and "]" characters and disregard first 3 values
	String topic1 = top3TopicsPastMonth.get(2).getID().replace("[", "");
	String topic1Clean = topic1.replace("]", "");
	String topic2 = top3TopicsPastMonth.get(4).getID().replace("[", "");
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
		tweetsForMonth.put(topic1List.get(i));
		tweetsForMonth.put(topic2List.get(i));
		tweetsForMonth.put(topic3List.get(i));
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
}*/

/*
public JSONArray getPieChartData(){

	JSONArray tweetsForPie = new JSONArray();
	//Get top 3 topics for the past week
	List<EntityCountPair> top3TopicsPastWeek = c.getTopEntities(Counter.Field.ALL, Counter.TimePeriod.PASTWEEK, 10);
	
	//Clean up the strings removing "[" and "]" characters and disregard first 3 values
	String topic1 = top3TopicsPastWeek.get(2).getID().replace("[", "");
	String topic1Clean = topic1.replace("]", "");
	String topic2 = top3TopicsPastWeek.get(4).getID().replace("[", "");
	String topic2Clean = topic2.replace("]", "");
	String topic3 = top3TopicsPastWeek.get(6).getID().replace("[", "");
	String topic3Clean = topic3.replace("]", "");
	
	JSONObject top1 = new JSONObject();
	JSONObject top2 = new JSONObject();
	JSONObject top3 = new JSONObject();
	
	//Construct json objects for json array - {"Topic": value, "Tweets": value}
	top1.put("Topic", topic1Clean);
	top1.put("Tweets", top3TopicsPastWeek.get(2).getCount().intValue());
	top2.put("Topic", topic2Clean);
	top2.put("Tweets", top3TopicsPastWeek.get(4).getCount().intValue());
	top3.put("Topic", topic3Clean);
	top3.put("Tweets", top3TopicsPastWeek.get(6).getCount().intValue());
	
	//Add top 3 topic jsons to array
	tweetsForPie.put(top1);
	tweetsForPie.put(top2);
	tweetsForPie.put(top3);
	
	return tweetsForPie;
}*/
	
	/*
	//For adding default graph
	@RequestMapping("/bar/{timeScale}")
	public ModelAndView getBarTime(@PathVariable String timeScale){
		if(timeScale.equals("WEEK")){
			JSONArray tweetsForWeek = getGraphWeekData();
			ModelAndView mv = new ModelAndView("graphWeek");
			mv.addObject("tweetsForWeek", tweetsForWeek);
			System.out.println(tweetsForWeek.toString());
			return mv;
		}else{
			System.out.println("Time scale not recognised");
			return null;
		}
	}*/
	
	/*
	//For adding default graph test
	@RequestMapping("/bar/{tileNumber}/{timeScale}")
	public ModelAndView getBarTimeWithIndex(@PathVariable("tileNumber") String tileNumber, @PathVariable("timeScale") String timeScale){
		if(timeScale.equals("WEEK")){
			JSONArray tweetsForWeek = getGraphWeekData();
			ModelAndView mv = new ModelAndView("graphWeek");
			mv.addObject("tweetsForWeek", tweetsForWeek);
			mv.addObject("TileNumber", tileNumber);
			System.out.println(tweetsForWeek.toString());
			return mv;
		}else{
			System.out.println("Time scale not recognised");
			return null;
		}
	}*/
	
	
	/*
	private Calendar today;
	private Calendar end;
	
	//Map reduce week
	today = Calendar.getInstance();
	today.add(Calendar.DATE, -1);
	end = Calendar.getInstance();
	end.add(Calendar.DATE, -2);
	c = new Counter();
	c.dailyMapReduce(today.getTime());
	c.dailyMapReduce(end.getTime());
	end.add(Calendar.DATE, -2);
	c.dailyMapReduce(end.getTime());
	c.mergingMapReduce(Counter.TimePeriod.PASTMONTH);
	*/
	
	
	
	
	
	
	
	
	

}