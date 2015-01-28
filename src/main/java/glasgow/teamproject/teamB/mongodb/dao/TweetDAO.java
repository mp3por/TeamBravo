package glasgow.teamproject.teamB.mongodb.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mongodb.DBObject;

public interface TweetDAO {
	/**
	 * Adds a tweet to the DB
	 * @param tweet must be a JSON string
	 * */
	public void addTweet(String tweet, String collectionName);
	
	public String readByTime(String time, String collectionName);
	
	public boolean addNamedEntitiesById(String id, String collectionName, Map<String,String> NamedEntities);
	
	public ArrayList<DBObject> getLastTweets(int count, String collectionName);
	
	public List<String> getTweetsForMaps(String collectionName);
}
