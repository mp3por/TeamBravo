package glasgow.teamproject.teamB.mongodb.dao;

import glasgow.teamproject.teamB.Search.Tweet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

public interface TweetDAO {
	/**
	 * Adds a tweet to the DB
	 * @param tweet must be a JSON string
	 * */
	public void addTweet(String tweet, String collectionName);
	
	public String readByTime(String time, String collectionName);
	
	public boolean addNamedEntitiesById(String id, String collectionName, Map<String,String> NamedEntities);
	
	public ArrayList<HashMap<String, Object>> getLastTweets(int count, String collectionName);
	
	public List<String> getTweetsForMaps(String collectionName);
	
	
	// For Terrier
	public ArrayBlockingQueue<String> getTweetsQueue(String collectionName);
		
	public ArrayList<Tweet> getResultsList(String collectionName, int[] resultsDocids);
	
	//For Terrier
	public Queue<String> getCollection(String string);

	public ArrayList<HashMap<String,Object>> getTweetsForId(int[] ids);

}
