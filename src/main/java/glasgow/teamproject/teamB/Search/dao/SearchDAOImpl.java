package glasgow.teamproject.teamB.Search.dao;

import glasgow.teamproject.teamB.Search.SearchMemoryIndex;
import glasgow.teamproject.teamB.Search.TerrierInitializer;
//import glasgow.teamproject.teamB.Search.SearchMemoryIndex;
import glasgow.teamproject.teamB.Search.Tweet;
import glasgow.teamproject.teamB.Util.ProjectProperties;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAO;

import java.io.FileNotFoundException;
import java.io.IOException;
//import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
//import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
//import java.util.Set;








import javax.annotation.PostConstruct;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.terrier.applications.secondary.CollectionEnrichment;
import org.terrier.matching.ResultSet;
import org.terrier.querying.Manager;
import org.terrier.querying.SearchRequest;
import org.terrier.structures.MetaIndex;
import org.terrier.utility.ApplicationSetup;

//import org.terrier.realtime.memory.MemoryIndex;

/**
 * A TweetsRetriver object takes an index object produced by a TweetsIndexer.
 * Then query the collection based on the given query as a String.
 * 
 * @author vincentfung13
 *
 */

@Component
public class SearchDAOImpl {

	@Autowired
	private TweetDAO tweetSaver;

	//@Autowired
	SearchMemoryIndex index;

	@Autowired
	TerrierInitializer ti;
	
	private List<Tweet> resultsList;
	private boolean alreadyRunQuery = false;
	private Manager queryManager;
	private String query;
	private int resultCount = 0;

	// public SearchDAO(TweetDAO tweetSaver){
	// this.tweetSaver = tweetSaver;
	// alreadyRunQuery = false;
	// }

	@PostConstruct
	public void setUp() {
		index = ti.getMemoryIndex();
//		String currentDir = getClass().getProtectionDomain().getCodeSource().getLocation().toString();
//		currentDir = currentDir.replace("file:", "").split("\\.")[0] + "TeamBravo/stopword-list.txt";
//		System.out.println("Search:" + currentDir);
//		ApplicationSetup.setProperty("stopwords.filename", currentDir);	
//		ApplicationSetup.setProperty("indexer.meta.forward.keys", "docno,text");
//		ApplicationSetup.setProperty("indexer.meta.forward.keylens", "20,200");
		queryManager = new Manager(index);
	}
	
	public int foo(){
		return this.index.getProperties().size();
	}

	public List<Tweet> getResultsList() {

		if (this.alreadyRunQuery == false) {
			System.out.println("You have not run any query");
			return null;
		} else
		return this.resultsList;
	}
	
	private List<String> getResultsListString() throws FileNotFoundException, UnsupportedEncodingException{
		
		List<String> results = new ArrayList<String>();
		for (Tweet tweet: this.resultsList){
			results.add(tweet.toString());
		}
		return results;
	}

	// public void sortResults(Comparator<Tweet> cmp) {
	// this.resultsList.sort(cmp);
	// }

	public void runQuery(String query) {

		System.err.println("Running search for " + query);
		StringBuffer sb = new StringBuffer();
		sb.append(CollectionEnrichment.normaliseString(query));
		SearchRequest srq = queryManager.newSearchRequest("query",
				sb.toString());
		/* What matching model should I set? */
		/* Study searchRequest class and matching model. */
		srq.addMatchingModel("Matching", "DirichletLM");
		srq.setOriginalQuery(sb.toString());
		srq.setControl("decorate", "on");
		queryManager.runPreProcessing(srq);
		queryManager.runMatching(srq);
		queryManager.runPostProcessing(srq);
		queryManager.runPostFilters(srq);

		List<String> tweetidList = this.populateTweetidList(srq.getResultSet().getDocids());
		
		System.err.println("Got " + tweetidList.size() + " ids");
		
		this.resultsList = this.tweetSaver.getResultsList(
				ProjectProperties.TWEET_COLLECTION, tweetidList);

		this.alreadyRunQuery = true;
		this.resultCount = srq.getResultSet().getDocids().length;
	}
	
	private List<String> populateTweetidList(int[] resultsDocids){
		System.out.println("Populating list!!!");
		List<String> tweetidList = new ArrayList<String>();
		tweetidList = new ArrayList<String>();
		
		MetaIndex mt = this.index.getMetaIndex();
		String[] fields = mt.getKeys();
		
		for (String s: fields)
			System.out.println("I am fields: " + s);
		
//		String tmp;
		for(int i = 0; i < resultsDocids.length; i++)
			try {
//				System.out.println(index);
//				System.err.println("yayayaya" + tmp);
//				System.out.println(mt.getItem("text", resultsDocids[i]));
				tweetidList.add(mt.getItem("text", resultsDocids[i]));
			} catch (IOException e) {
				System.out.println("Counldn't add docno for tweets " + resultsDocids[i]);
			}
		return tweetidList;
	}
	
	public List<Tweet> rankedByRetweeted(){
//		System.err.println("Sorting by retweeted times");
		List<Tweet> list = new ArrayList<>();
		for(Tweet t: this.resultsList)
			list.add(t);
		Collections.sort(list, Tweet.RetweetCountComparator);
		return list;
	}
	
	public List<Tweet> rankedByPostedTime(){
//		System.err.println("Sorting by posted time");
		List<Tweet> list = new ArrayList<>();
		for(Tweet t: this.resultsList)
			list.add(t);
		try{
		Collections.sort(list, Tweet.PostedTimeComparator);}
		catch(IllegalArgumentException e){
			System.err.println("Exception to be handled.");
		}
		
		return list;
	}
	
	public List<Tweet> rankByFavourited(){
//		System.err.println("Sorting by retweeted times");
		List<Tweet> list = new ArrayList<>();
		for(Tweet t: this.resultsList)
			list.add(t);
		Collections.sort(list, Tweet.MostFavouritedComparator);
		return list;
	}

	/*
	 * Parse the results to ArrayList<HashMap<String, Object>> to display tweet
	 * wall with NEs
	 */
	public ArrayList<HashMap<String, Object>> getTweetsForTweetWall(List<Tweet> list) {
		ArrayList<HashMap<String, Object>> results = new ArrayList<>();
		
		for (Tweet tweet: list)
			results.add(tweet.getTweetMap());
		
		return results;
	}

	/*
	 * The following three functions retrieve coordinates of geo tagged results
	 * for mapping
	 */	
	public Map<String, ArrayList<String>> getDataForMaps() throws FileNotFoundException, UnsupportedEncodingException {
		
		HashSet<String> tweetsForMaps = new HashSet<String>(this.getResultsListString());
		ArrayList<String> tweet_text = new ArrayList<>();
		ArrayList<String> latitudes = new ArrayList<>();
		ArrayList<String> longitudes = new ArrayList<>();
		ArrayList<String> tweet_time = new ArrayList<>();
		ArrayList<String> tweet_user = new ArrayList<>();
		Map<String, ArrayList<String>> data = new HashMap<String, ArrayList<String>>();

		String a = null;
		String time = null;
		JSONObject b = null;
		JSONArray c = null;

		String user = null;
		
		int non_geotagged_count = 0;

		for (String tweet : tweetsForMaps) {
			JSONObject js = new JSONObject(tweet);
			a = (String) js.get("text");
			time = js.getString("created_at");
			try{
				b = (JSONObject) js.get("coordinates");
				c = (JSONArray) b.get("coordinates");
				b = js.getJSONObject("user");
				user = b.getString("name");
				tweet_text.add(a);
				longitudes.add(Double.toString(c.getDouble(0)));
				latitudes.add(Double.toString(c.getDouble(1)));
				tweet_time.add(time);
				tweet_user.add(user);
			}
			catch(ClassCastException e){
//				System.err.println("Tweet not geotagged, couldn't pin on map.");
				non_geotagged_count++;
			}
		}
		
		System.err.println(non_geotagged_count + " out of " + 
							this.resultsList.size() + " tweets are not geotagged and couldn't be pinned on the map");
		
		String needed = "<div id='added_map_container' class='map-container'><div id='added_map_div' class='map'></div></div>";
		ArrayList<String> need = new ArrayList<String>();
		need.add(needed);
		data.put("needed", need);
		data.put("longitudes", longitudes);
		data.put("latitudes", latitudes);
		data.put("text", tweet_text);
		data.put("time", tweet_time);
		data.put("user", tweet_user);

		return data;
	}

	/* { "type" : "Point" , "coordinates" : [ -4.292994 , 55.874865]} */
//	private double[] getCoordinate(Tweet tweet) {
//
//		double[] coordinate = new double[2];
//		// Map<String, Object> map = tweet.getTweet();
//		// System.err.println(map);
//		if (tweet.getTweet().get("coordinates") != null) {
//			String pairString = tweet.getTweet().get("coordinates").toString();
//			// System.err.println(pairString);
//
//			int startOfCoordinate = pairString.lastIndexOf('[') + 2;
//			int comma = pairString.lastIndexOf(',');
//			int endOfCoordinate = pairString.lastIndexOf(']') - 1;
//
//			String latitude = pairString
//					.substring(startOfCoordinate, comma - 1);
//			String longtitude = pairString.substring(comma + 2,
//					endOfCoordinate + 1);
//
//			coordinate[0] = Double.parseDouble(latitude);
//			coordinate[1] = Double.parseDouble(longtitude);
//
//			return coordinate;
//		} else
//			return null;
//	}
//
//	public ArrayList<Double> latitudesForMaps() {
//
//		ArrayList<Double> latitudes = new ArrayList<>();
//		double[] coordinate;
//		for (int i = 0; i < this.resultsList.size(); i++) {
//			coordinate = this.getCoordinate(this.resultsList.get(i));
//			if (coordinate != null)
//				latitudes.add(coordinate[0]);
//		}
//		// System.err.println("latitudes list created");
//		return latitudes;
//	}
//
//	public ArrayList<Double> longtitudesForMaps() {
//
//		ArrayList<Double> longtitudes = new ArrayList<>();
//		double[] coordinate;
//		for (int i = 0; i < this.resultsList.size(); i++) {
//			coordinate = this.getCoordinate(this.resultsList.get(i));
//			if (coordinate != null)
//				longtitudes.add(coordinate[1]);
//		}
//		// System.err.println(longtitudes);
//		return longtitudes;
//	}
	
	public String getQuery(){
		return this.query;
	}
	
	public int getResultsCount(){
		return this.resultCount;
	}

	/* To be added for graphs */

}
