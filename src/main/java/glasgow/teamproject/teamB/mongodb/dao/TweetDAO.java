package glasgow.teamproject.teamB.mongodb.dao;


import glasgow.teamproject.teamB.Search.Tweet;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAOImpl.DateCountPair;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAOImpl.EntityCountPair;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAOImpl.Field;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAOImpl.TimePeriod;

import java.util.ArrayList;
import java.util.Date;
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

	// For Statistics
	public long getTweetCount(Date stDate, Date edDate, boolean isRetweeted);
	public String getMostActiveUser(Date stDate, Date edDate);
	public HashMap<String, Object> getMostPopularTweet(Date stDate, Date edDate, String compareKey);
	public List<EntityCountPair> getTopEntities(Field field,
			TimePeriod timePeriod, int numEntities);
	public ArrayList<DateCountPair> getEntitiyTrend(String entity, int numDays);
	
	// For Counting
	public void dailyMapReduce(Date date);
	public void mergingMapReduce(TimePeriod timePeriod);

	


}
