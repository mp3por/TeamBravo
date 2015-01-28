package glasgow.teamproject.teamB.mongodb.dao;

import glasgow.teamproject.teamB.Graphs.TopicWrapper;

import java.util.List;
import java.util.Map;

public interface TweetDAO {
	/**
	 * Adds a tweet to the DB
	 * @param tweet must be a JSON string
	 * */
	public void addTweet(String tweet, String collectionName);
	
	public String readByTime(String time, String collectionName);
	
	public boolean addNamedEntitiesById(String id, String collectionName, Map<String,String> NamedEntities);
	
	public List<String> getLastTweets(int count, String collectionName);
	
	public List<String> getTweetsForMaps(String collectionName);
	
	public List<String> getTweetsForGraphLine();
	
	public List<String> getTweetsForGraphPie();
	
	public List<TopicWrapper> getHotTopics(int noOfTopics, String topicColumnName, String tweetColumnName,String collectionName);
	
	public List<String> getTweetsForGraphBarchart();
}
