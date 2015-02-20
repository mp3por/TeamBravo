package glasgow.teamproject.teamB.Search.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import glasgow.teamproject.teamB.Search.Tweet;
import glasgow.teamproject.teamB.Search.TweetsIndexer;
import glasgow.teamproject.teamB.Search.TweetsRetriver;
import glasgow.teamproject.teamB.Util.ProjectProperties;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAO;

public class SearchDAO{
	
	private TweetsIndexer indexer;
	private TweetsRetriver retriver;
	private TweetDAO tweetSaver;
	
	/* Constructor */
	public SearchDAO(TweetsIndexer indexer, String query){
		this.indexer = indexer;
		indexer.indexTweets();
		this.retriver = new TweetsRetriver(indexer.getIndex(), query);	
	}
		
	/* Get a returned tweets list to be parsed */
	public ArrayList<Tweet> getResultList() {
		
		int[] resultsDocids = this.retriver.getResultSet().getDocids();
		ArrayList<Tweet> list = new ArrayList<>();
		Map<String, Object> currentObj;
		for (int i = 0; i < resultsDocids.length; i++){
			currentObj = tweetSaver.getNthEntry("tweets", resultsDocids[i]);
			list.add(new Tweet(currentObj));
		}
		return list;
	}
	
	/* Get a returned tweets list to be parsed */
	public ArrayList<Tweet> getRankedResultList() {
		
		int[] resultsDocids = this.retriver.getResultSet().getDocids();
		ArrayList<Tweet> list = new ArrayList<>();
		Map<String, Object> currentObj;
		for (int i = 0; i < resultsDocids.length; i++){
			currentObj = tweetSaver.getNthEntry("tweets", resultsDocids[i]);
			list.add(new Tweet(currentObj));
		}
		list.sort(Tweet.RetweetCountComparator);
		return list;

	}

	/* Parse tweets list to ArrayList<HashMap<String, Object>> for display */
	public ArrayList<HashMap<String, Object>> getResultsForTweetWall(ArrayList<Tweet> tweets) {

		ArrayList<HashMap<String,Object>> results = new ArrayList<>(); 
		Map<String, Object> currentTweet;
		for (int i = 0; i < tweets.size(); i++){
			currentTweet = tweets.get(i).getTweet();
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
	
	/* Get latitudes list for maps */
	/* { "type" : "Point" , "coordinates" : [ -4.292994 , 55.874865]} */
	public double[] getCoordinate(Tweet tweet){	
		
		double[] coordinate = new double[2];
		String pairString = tweet.getTweet().get("coordinates").toString();
		
		if(pairString != null) {
			
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
		ArrayList<Tweet> tweets = this.getResultList();
		latitudes.add((double) 0);
		double[] coordinate;
		for (Tweet t: tweets){
			coordinate = this.getCoordinate(t);
			if (coordinate != null)
				latitudes.add(coordinate[0]);
		}	
		return latitudes;
	}
	
	public ArrayList<Double> longtitudesForMaps(){
		
		ArrayList<Double> longtitudes = new ArrayList<>();
		ArrayList<Tweet> tweets = this.getResultList();
		longtitudes.add((double) 0);
		double[] coordinate;
		for (Tweet t: tweets){
			coordinate = this.getCoordinate(t);
			if (coordinate != null)
				longtitudes.add(coordinate[1]);
		}	
		return longtitudes;
	}
	
	/* For graphs */
	
	/* Get query */
	public String getQuery(){
		return this.retriver.getQuery();
	}
}
