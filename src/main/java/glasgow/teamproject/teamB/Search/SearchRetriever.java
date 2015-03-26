package glasgow.teamproject.teamB.Search;

import glasgow.teamproject.teamB.Util.ProjectProperties;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.terrier.applications.secondary.CollectionEnrichment;
import org.terrier.querying.Manager;
import org.terrier.querying.SearchRequest;
import org.terrier.realtime.memory.MemoryIndex;
import org.terrier.structures.MetaIndex;

/**
 * A TweetsRetriver object takes an index object produced by a TweetsIndexer.
 * Then query the collection based on the given query as a String.
 * 
 * @author vincentfung13
 *
 */

@Component
public class SearchRetriever {

	@Autowired
	private TweetDAO tweetSaver;

	MemoryIndex index;

	@Autowired
	TerrierInitializer ti;
	
	private List<Tweet> resultsList;
	private boolean alreadyRunQuery = false;
	private Manager queryManager;
	private String query;
	private int resultCount = 0;

	@PostConstruct
	public void setUp() {
		index = ti.getMemoryIndex();
		queryManager = new Manager(index);
	}
	
	public int getIndexSize(){
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


	public void runQuery(String query) {

		if (query.startsWith("#"))
			query = query.substring(1);
		
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
		
		this.resultsList = this.tweetSaver.getResultsList(
				ProjectProperties.TWEET_COLLECTION, tweetidList);

		this.alreadyRunQuery = true;
		this.resultCount = srq.getResultSet().getDocids().length;
	}
	
	private List<String> populateTweetidList(int[] resultsDocids){

		List<String> tweetidList = new ArrayList<String>();
		tweetidList = new ArrayList<String>();
		
		MetaIndex mt = this.index.getMetaIndex();
		
		for(int i = 0; i < resultsDocids.length; i++)
			try {
				tweetidList.add(mt.getItem("text", resultsDocids[i]));
			} catch (IOException e) {
				System.out.println("Counldn't add docno for tweets " + resultsDocids[i]);
			}
		return tweetidList;
	}
	
	public List<Tweet> rankedByRetweeted(){
		List<Tweet> list = new ArrayList<>();
		for(Tweet t: this.resultsList)
			list.add(t);
		Collections.sort(list, Tweet.RetweetCountComparator);
		return list;
	}
	
	public List<Tweet> rankedByPostedTime(){
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
	
	public String getQuery(){
		return this.query;
	}
	
	public int getResultsCount(){
		return this.resultCount;
	}

}
