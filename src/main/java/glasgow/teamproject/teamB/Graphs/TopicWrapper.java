package glasgow.teamproject.teamB.Graphs;

/**
 * Wrapper class for dealing with DBObjects
 * when used by the TopicComparator
 * */

import com.mongodb.DBObject;

public class TopicWrapper {
	
	private String topic;
	private int noOfTweets;
	
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

	public TopicWrapper(DBObject hotTopic, String topicField, String tweetField){
		
		try {
			setTopic((String) ((DBObject) hotTopic.get("map")).get(topicField));
			setNoOfTweets((int) ((DBObject) hotTopic.get("map")).get(tweetField));
		} catch (Exception e) {
			System.err.println("Column " + topicField + " or " + tweetField + " is not contained in the table");
		}
	}

	@Override
	public String toString() {
		return getTopic() + " " + getNoOfTweets();
	}
	
	
}
