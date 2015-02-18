package glasgow.teamproject.teamB.mongodb.dao;

import glasgow.teamproject.teamB.Graphs.TopicWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

import com.mongodb.BasicDBObject;

import java.util.Queue;


import org.json.JSONArray;

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
	
	public BasicDBObject getNthEntry(String collectionName, int n);
	
	public ArrayList<Tweet> getResultList(int[] resultsDocids);
	
	public ArrayList<Tweet> getRankedResultList(int[] resultsDocids);
	
	public ArrayList<HashMap<String, Object>> getTerrierResults(ArrayList<Tweet> tweets);

	//For Terrier
	public Queue<String> getCollection(String string);
}
