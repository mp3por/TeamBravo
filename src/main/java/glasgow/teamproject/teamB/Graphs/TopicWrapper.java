package glasgow.teamproject.teamB.Graphs;

/**
 * Wrapper class for dealing with DBObjects
 * when used by the TopicComparator
 * */

import com.mongodb.DBObject;

public class TopicWrapper {
	
	private String topic;
	private int noOfTweets;
	private String month;
	private String date;
	
	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public int getNoOfTweets() {
		return noOfTweets;
	}

	public void setNoOfTweets(int noOftweets) {
		this.noOfTweets = noOftweets;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	//For use with the word cloud and pie chart
	public TopicWrapper(DBObject hotTopic, String topicField, String tweetField){
		
		//topicField is the column name in the table for topics
		//tweetField is the column name in the table for the number of tweets
		try {
			setTopic((String) ((DBObject) hotTopic.get("map")).get(topicField));
			setNoOfTweets((int) ((DBObject) hotTopic.get("map")).get(tweetField));
		} catch (Exception e) {
			System.err.println("Error: no such column - TopicWrapper Constructor - topic/tweet");
		}
	}
	
	//For use with line graph
	public TopicWrapper(DBObject hotTopic, String topicField, String tweetField, String dateField, String monthField){
		
		try {
			setTopic((String) ((DBObject) hotTopic.get("map")).get(topicField));
			setNoOfTweets((int) ((DBObject) hotTopic.get("map")).get(tweetField));
			setDate((String) ((DBObject) hotTopic.get("map")).get(dateField));
			setMonth((String) ((DBObject) hotTopic.get("map")).get(monthField));
		} catch (Exception e) {
			System.err.println("Error: no such column - TopicWrapper Constructor - topic/tweet/date/month");
		}
	}

	@Override
	public String toString() {
		
		if(getDate() != null){
			return getDate() + " " + getMonth() + " " + getTopic() + " " + getNoOfTweets();
		}else{
			return getTopic() + " " + getNoOfTweets();
		}
	}
	
	
}
