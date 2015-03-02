package glasgow.teamproject.teamB.Search;

import java.util.Comparator;
import java.util.Map;

public class Tweet implements Comparable<Tweet> {
	
	private String tweet;
	private Map<String, Object> tweetMap;
	
	public Tweet (String tweet, Map<String, Object> tweetMap){
		this.tweet = tweet;
		this.tweetMap = tweetMap;
	}
	
	public String getTweet(){
		return this.tweet;
	}
	
	public Map<String, Object> getTweetMap(){
		return this.tweetMap;
	}
	
	@Override
	public String toString(){
		return this.tweet;
	}

	@Override
	public int compareTo(Tweet o) {
		return Integer.parseInt(o.getTweetMap().get("retweet_count").toString()) 
				- Integer.parseInt(this.tweetMap.get("retweet_count").toString()); 
	}
	
	public static Comparator<Tweet> RetweetCountComparator = new Comparator<Tweet>(){
		
		public int compare(Tweet tweet1, Tweet tweet2){
			
			int retweet_count1 = Integer.parseInt(tweet1.getTweetMap().get("retweet_count").toString());
			int retweet_count2 = Integer.parseInt(tweet2.getTweetMap().get("retweet_count").toString());
			
			return retweet_count2 - retweet_count1;
		}
	};
	
	public static Comparator<Tweet> PostedTimeComparator = new Comparator<Tweet>(){
			
			public int compare(Tweet tweet1, Tweet tweet2){
			
			int posted_time1 = Integer.parseInt(tweet1.getTweetMap().get("timestamp").toString());
			int posted_time2 = Integer.parseInt(tweet2.getTweetMap().get("timestamp").toString());
			
			return posted_time2 - posted_time1;
		}
	};

}
