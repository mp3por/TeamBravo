package glasgow.teamproject.teamB.Search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import glasgow.teamproject.teamB.Util.ProjectProperties;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAO;

import org.terrier.applications.secondary.CollectionEnrichment;
import org.terrier.querying.Manager;
import org.terrier.querying.SearchRequest;
import org.terrier.realtime.memory.MemoryIndex;

/**
 * A TweetsRetriver object takes an index object produced by a TweetsIndexer.
 * Then query the collection based on the given query as a String.
 * @author vincentfung13
 *
 */

public class TweetsRetriever {
		
	private TweetDAO tweetSaver;
	private ArrayList<Tweet> resultsList;
	private boolean alreadyRunQuery = false;
	
	public TweetsRetriever(TweetDAO tweetSaver){		
		this.tweetSaver = tweetSaver;
		alreadyRunQuery = false;
	}
	
	public ArrayList<Tweet> getResultsList(){
		
		if (this.alreadyRunQuery == false){
			System.out.println("You have not run any query");
			return null;
		}
		else return this.resultsList;
	}
		
	public void runQuery(MemoryIndex index, String query){
		
		this.alreadyRunQuery = true;
		Manager queryManager = new Manager(index);
		
		System.err.println("Running search for " + query);
        StringBuffer sb = new StringBuffer();
        sb.append(CollectionEnrichment.normaliseString(query));
        SearchRequest srq = queryManager.newSearchRequest("query", sb.toString());
        /* What matching model should I set? */
        /* Study searchRequest class and matching model. */
        srq.addMatchingModel("Matching","DirichletLM");
        srq.setOriginalQuery(sb.toString());
        srq.setControl("decorate", "on");
        queryManager.runPreProcessing(srq);
        queryManager.runMatching(srq);
        queryManager.runPostProcessing(srq);
        queryManager.runPostFilters(srq);
        
        int[] resultsDocids = srq.getResultSet().getDocids();
        this.resultsList = this.tweetSaver.getResultsList(ProjectProperties.TWEET_COLLECTION, resultsDocids);
	}
	
	/* Parse the results to ArrayList<HashMap<String, Object>> to display tweet wall with NEs */
	public ArrayList<HashMap<String, Object>> getTweetsForTweetWall() {
		ArrayList<HashMap<String,Object>> results = new ArrayList<>(); 
		Map<String, Object> currentTweet;
		for (int i = 0; i < this.resultsList.size(); i++){
			currentTweet = this.resultsList.get(i).getTweet();
			HashMap<String, Object> tweet = new HashMap<>();
			for (String key: currentTweet.keySet()) {
				if (ProjectProperties.defaultNE.contains(key)) {
					String s = currentTweet.get(key).toString();
					if (s.length() == 2) {
						tweet.put(key, null);
						continue;
					}

					s = s.replace("[", "");
					s = s.replace("]", "");

					HashSet<String> NEs = new HashSet<String>();

					for (String NE: s.split(",")) {
						NEs.add(NE);
					}
					tweet.put(key, NEs);
				}
				else {
					tweet.put(key, currentTweet.get(key));
				}
			}
			results.add(tweet);
		}
		return results;
	}
	
	/* The following three functions retrieve coordinates of geo tagged results for mapping */
	
	/* { "type" : "Point" , "coordinates" : [ -4.292994 , 55.874865]} */
	private double[] getCoordinate(Tweet tweet){	
		
		double[] coordinate = new double[2];
//		Map<String, Object> map = tweet.getTweet();
//		System.err.println(map);
		if (tweet.getTweet().get("coordinates") != null){
			String pairString = tweet.getTweet().get("coordinates").toString();
//			System.err.println(pairString);
		
			int startOfCoordinate = pairString.lastIndexOf('[') + 2;
			int comma = pairString.lastIndexOf(',');
			int endOfCoordinate = pairString.lastIndexOf(']') - 1;
		
			String latitude = pairString.substring(startOfCoordinate, comma - 1);
			String longtitude = pairString.substring(comma + 2, endOfCoordinate + 1);
		
		
			coordinate[0] = Double.parseDouble(latitude);
			coordinate[1] = Double.parseDouble(longtitude);
		
		
			return coordinate;
		}
		else return null;
	}
	
	public ArrayList<Double> latitudesForMaps(){
		
		ArrayList<Double> latitudes = new ArrayList<>();
		double[] coordinate;
		for (int i = 0; i < this.resultsList.size(); i++){
			coordinate = this.getCoordinate(this.resultsList.get(i));
			if (coordinate != null)
				latitudes.add(coordinate[0]);
		}
//		System.err.println("latitudes list created");
		return latitudes;
	}
	
	public ArrayList<Double> longtitudesForMaps(){
		
		ArrayList<Double> longtitudes = new ArrayList<>();
		double[] coordinate;
		for (int i = 0; i < this.resultsList.size(); i++){
			coordinate = this.getCoordinate(this.resultsList.get(i));
			if (coordinate != null)
				longtitudes.add(coordinate[1]);
		}
//		System.err.println(longtitudes);
		return longtitudes;
	}
	
	/* To be added for graphs */
 
}
