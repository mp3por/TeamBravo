package glasgow.teamproject.teamB.Graphs;

import glasgow.teamproject.teamB.mongodb.dao.TweetDAO;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAOImpl;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAOImpl.EntityCountPair;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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
		
		//Get top topics for the past week
		List<EntityCountPair> topics = new ArrayList<EntityCountPair>();
		
		if(timePeriod.equals("WEEK")){
			topics = c.getTopEntities(TweetDAOImpl.Field.ALL, TweetDAOImpl.TimePeriod.PASTWEEK, 20);
		}else if(timePeriod.equals("MONTH")){
			topics = c.getTopEntities(TweetDAOImpl.Field.ALL, TweetDAOImpl.TimePeriod.PASTMONTH, 20);
		}else{
			System.err.print("Error: " + timePeriod + " Not a valid time period.");
		}
		
		//Remove empty strings
		for (Iterator<EntityCountPair> iterator = topics.iterator(); iterator.hasNext();) {
			EntityCountPair topic = iterator.next();
		    if (topic.getID().isEmpty() || topic.getID().equals("[]")) {
		        // Remove the current element from the iterator and the list.
		        iterator.remove();
		    }
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
		
		if(timePeriod.equals("WEEK")){
			topics = c.getTopEntities(TweetDAOImpl.Field.ALL, TweetDAOImpl.TimePeriod.PASTWEEK, 20);
		}else if(timePeriod.equals("MONTH")){
			topics = c.getTopEntities(TweetDAOImpl.Field.ALL, TweetDAOImpl.TimePeriod.PASTMONTH, 20);
		}else{
			System.err.print("Error: " + timePeriod + " Not a valid time period.");
		}
		
		//Remove square brackets
		for (Iterator<EntityCountPair> iterator = topics.iterator(); iterator.hasNext();) {
			EntityCountPair topic = iterator.next();
		    if (topic.getID().equals("[]")) {
		        // Remove the current element from the iterator and the list.
		        iterator.remove();
		    }
		}
		
		//Remove empty strings
		for (Iterator<EntityCountPair> iterator = topics.iterator(); iterator.hasNext();) {
			EntityCountPair topic = iterator.next();
		    if (topic.getID().isEmpty()) {
		        // Remove the current element from the iterator and the list.
		        iterator.remove();
		    }
		}
		
		//Construct list
		for(EntityCountPair e: topics){
			HashMap<String,String> topic = new HashMap<String,String>();
			topic.put("Topic", e.getID());
			topic.put("Tweets", Integer.toString(e.getCount().intValue()));
			tweets.add(topic);
		}
		
		return tweets;
	}
	
	//GET WEEK OR MONTH LIST FOR DIMPLE GRAPHS
	public List<HashMap<String,String>> getGraphList(String timePeriod){
		
		//c.mergingMapReduce(TimePeriod.PASTWEEK);
	
		List<HashMap<String,String>> tweets = new ArrayList<>();
		List<EntityCountPair> topics = null;
		int noOfDays = 0;
		
		List<HashMap<String, String>> topic1Dates = null;
		List<HashMap<String, String>> topic2Dates = null;
		List<HashMap<String, String>> topic3Dates = null;
		List<HashMap<String, String>> topic4Dates = null;
		List<HashMap<String, String>> topic5Dates = null;
		List<HashMap<String, String>> topic6Dates = null;
		List<HashMap<String, String>> topic7Dates = null;
		List<HashMap<String, String>> topic8Dates = null;
		List<HashMap<String, String>> topic9Dates = null;
		List<HashMap<String, String>> topic10Dates = null;
		List<HashMap<String, String>> topic11Dates = null;
		List<HashMap<String, String>> topic12Dates = null;
		List<HashMap<String, String>> topic13Dates = null;
		List<HashMap<String, String>> topic14Dates = null;
		List<HashMap<String, String>> topic15Dates = null;
		List<HashMap<String, String>> topic16Dates = null;
		List<HashMap<String, String>> topic17Dates = null;
		List<HashMap<String, String>> topic18Dates = null;
		List<HashMap<String, String>> topic19Dates = null;
		List<HashMap<String, String>> topic20Dates = null;
		
		
		//Get top 3 topics for the past time period
		if(timePeriod.equals("WEEK")){
			topics = c.getTopEntities(TweetDAOImpl.Field.ALL, TweetDAOImpl.TimePeriod.PASTWEEK, 20);
			noOfDays = 7;
			
			//Remove square brackets and empty strings
			for (Iterator<EntityCountPair> iterator = topics.iterator(); iterator.hasNext();) {
				EntityCountPair topic = iterator.next();

			    if (topic.getID().equals("[]") || topic.getID().isEmpty()) {
			        // Remove the current element from the iterator and the list.
			        iterator.remove();
			    }
			}
			
			topic1Dates = c.getEntitiyTrend(topics.get(0).getID(), 7);
			topic2Dates = c.getEntitiyTrend(topics.get(1).getID(), 7);
			topic3Dates = c.getEntitiyTrend(topics.get(2).getID(), 7);
			topic4Dates = c.getEntitiyTrend(topics.get(3).getID(), 7);
			topic5Dates = c.getEntitiyTrend(topics.get(4).getID(), 7);
			topic6Dates = c.getEntitiyTrend(topics.get(5).getID(), 7);
			topic7Dates = c.getEntitiyTrend(topics.get(6).getID(), 7);
			topic8Dates = c.getEntitiyTrend(topics.get(7).getID(), 7);
			topic9Dates = c.getEntitiyTrend(topics.get(8).getID(), 7);
			topic10Dates = c.getEntitiyTrend(topics.get(9).getID(), 7);
			topic11Dates = c.getEntitiyTrend(topics.get(10).getID(), 7);
			topic12Dates = c.getEntitiyTrend(topics.get(11).getID(), 7);
			topic13Dates = c.getEntitiyTrend(topics.get(12).getID(), 7);
			topic14Dates = c.getEntitiyTrend(topics.get(13).getID(), 7);
			topic15Dates = c.getEntitiyTrend(topics.get(14).getID(), 7);
			topic16Dates = c.getEntitiyTrend(topics.get(15).getID(), 7);
			topic17Dates = c.getEntitiyTrend(topics.get(16).getID(), 7);
			topic18Dates = c.getEntitiyTrend(topics.get(17).getID(), 7);
			topic19Dates = c.getEntitiyTrend(topics.get(18).getID(), 7);
			topic20Dates = c.getEntitiyTrend(topics.get(19).getID(), 7);
			
			
		}else if(timePeriod.equals("MONTH")){
			topics = c.getTopEntities(TweetDAOImpl.Field.ALL, TweetDAOImpl.TimePeriod.PASTMONTH, 20);
			noOfDays = 30;
			
			//Remove square brackets and empty strings
			for (Iterator<EntityCountPair> iterator = topics.iterator(); iterator.hasNext();) {
				EntityCountPair topic = iterator.next();
			    if (topic.getID().equals("[]") || topic.getID().isEmpty()) {
			        // Remove the current element from the iterator and the list.
			        iterator.remove();
			    }
			}
			
			topic1Dates = c.getEntitiyTrend(topics.get(0).getID(), 7);
			topic2Dates = c.getEntitiyTrend(topics.get(1).getID(), 7);
			topic3Dates = c.getEntitiyTrend(topics.get(2).getID(), 7);
			topic4Dates = c.getEntitiyTrend(topics.get(3).getID(), 7);
			topic5Dates = c.getEntitiyTrend(topics.get(4).getID(), 7);
			topic6Dates = c.getEntitiyTrend(topics.get(5).getID(), 7);
			topic7Dates = c.getEntitiyTrend(topics.get(6).getID(), 7);
			topic8Dates = c.getEntitiyTrend(topics.get(7).getID(), 7);
			topic9Dates = c.getEntitiyTrend(topics.get(8).getID(), 7);
			topic10Dates = c.getEntitiyTrend(topics.get(9).getID(), 7);
			topic11Dates = c.getEntitiyTrend(topics.get(10).getID(), 7);
			topic12Dates = c.getEntitiyTrend(topics.get(11).getID(), 7);
			topic13Dates = c.getEntitiyTrend(topics.get(12).getID(), 7);
			topic14Dates = c.getEntitiyTrend(topics.get(13).getID(), 7);
			topic15Dates = c.getEntitiyTrend(topics.get(14).getID(), 7);
			topic16Dates = c.getEntitiyTrend(topics.get(15).getID(), 7);
			topic17Dates = c.getEntitiyTrend(topics.get(16).getID(), 7);
			topic18Dates = c.getEntitiyTrend(topics.get(17).getID(), 7);
			topic19Dates = c.getEntitiyTrend(topics.get(18).getID(), 7);
			topic20Dates = c.getEntitiyTrend(topics.get(19).getID(), 7);
			
		}else{
			System.err.print("Error: " + timePeriod + " Not a valid time period.");
		}

		//Construct list in correct format for graph
		for(int i = 0; i < noOfDays; i++){
			tweets.add(topic1Dates.get(i));
			tweets.add(topic2Dates.get(i));
			tweets.add(topic3Dates.get(i));
			tweets.add(topic4Dates.get(i));
			tweets.add(topic5Dates.get(i));
			tweets.add(topic6Dates.get(i));
			tweets.add(topic7Dates.get(i));
			tweets.add(topic8Dates.get(i));
			tweets.add(topic9Dates.get(i));
			tweets.add(topic10Dates.get(i));
			tweets.add(topic11Dates.get(i));
			tweets.add(topic12Dates.get(i));
			tweets.add(topic13Dates.get(i));
			tweets.add(topic14Dates.get(i));
			tweets.add(topic15Dates.get(i));
			tweets.add(topic16Dates.get(i));
			tweets.add(topic17Dates.get(i));
			tweets.add(topic18Dates.get(i));
			tweets.add(topic19Dates.get(i));
			tweets.add(topic20Dates.get(i));
		}
		
		return tweets;
	}
	
	public List<HashMap<String,String>> getGraphSearchList(String searchTerm, String timePeriod){
		List<HashMap<String,String>> trend = new ArrayList<>();
		List<HashMap<String, String>> topicDates = new ArrayList<HashMap<String, String>>();
		int daysForNull = 0;
		
		if(timePeriod.equals("WEEK")){
			topicDates = c.getEntitiyTrend(searchTerm, 7);
			daysForNull = 7;
		}else{
			topicDates = c.getEntitiyTrend(searchTerm, 30);
			daysForNull = 30;
		}
		
		//Deal with 0 results returned
		if(topicDates.size() < 1){
			for(int i = 0; i < daysForNull; i++){
				HashMap<String,String> topic = new HashMap<String,String>();
				topic.put("Day","Day" + i);
				topic.put("Tweets","0");
				topic.put("Topic", searchTerm);
				trend.add(topic);
			}
		}else{
			for(HashMap<String, String> d : topicDates){
				HashMap<String,String> topic = new HashMap<String,String>();
				topic.put("Day",d.get("Day"));
				topic.put("Tweets",d.get("Tweets"));
				topic.put("Topic", searchTerm);
				trend.add(topic);
			}
		}

		return trend;
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
	
	//RETURN SETTINGS------------------------------------------------------------------------------------------------>>
	@RequestMapping("/getSettings")
	public ModelAndView getGraphSettings(){
		ModelAndView view = new ModelAndView("graphSettings");
		return view;
	}
	
	//Testing Search Page-------------------------------------------------------------------------------------------->>
	@RequestMapping("/searchTest")
	public ModelAndView getSearchTest(){
		ModelAndView view = new ModelAndView("specificTopic");
		return view;
	}
	
	//Data for search page
	@RequestMapping("/search/{timePeriod}/{searchTerm}")
	@ResponseBody
	public List<HashMap<String,String>> getDataforSearchGraph(@PathVariable("searchTerm") String searchTerm, @PathVariable("timePeriod") String timePeriod){
		List<HashMap<String,String>> tweetsForSearchGraphs = getGraphSearchList(searchTerm,timePeriod);
		return tweetsForSearchGraphs;
	}
	
}