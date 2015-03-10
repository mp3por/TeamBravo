package glasgow.teamproject.teamB.mongodb.dao;


import glasgow.teamproject.teamB.Search.Tweet;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAOImpl.EntityCountPair;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAOImpl.Field;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAOImpl.TimePeriod;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public interface TweetDAO {
	/**
	 * Adds a tweet to the DB
	 * @param tweet must be a JSON string
	 * */
	public void addTweet(String tweet, String collectionName);
	
	public String readByTime(String time, String collectionName);
	
	public boolean addNamedEntitiesById(String id, String collectionName, HashMap<String, ArrayList<String>> NamedEntities);
	
	public ArrayList<HashMap<String, Object>> getLastTweets(int count, String collectionName);
	
	public List<String> getTweetsForMaps(String collectionName);
	public List<String> getTweetsForMapsWithLimit(String collectionName, int numberOfTweetsWanted); 
	
	
	// For Terrier
	public LinkedList<String> getTweetsQueue(String collectionName);
		
	public List<Tweet> getResultsList(String collectionName, List<String> idList);
	
	//For Terrier
	public Queue<String> getCollection(String string);

	// For Statistics
	public long getTweetCount(Date stDate, Date edDate, boolean isRetweeted);
	public String getMostActiveUser(Date stDate, Date edDate);
	// public HashMap<String, Object> getMostPopularTweet(Date stDate, Date edDate, String compareKey);
	public List<EntityCountPair> getTopEntities(Field field,
			TimePeriod timePeriod, int numEntities);
	public List<HashMap<String, String>> getEntitiyTrend(String entity, int numDays);
	
	// For Counting
	public void dailyMapReduce(Date date,String collectionName);
	public void mergingMapReduce(TimePeriod timePeriod);

	public ArrayList<HashMap<String, Object>> getTweetsForDate(int count,
			String dateFrom, String dateTo, String collectionName) throws ParseException;

	


}
