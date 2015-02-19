package glasgow.teamproject.teamB.mongodb.dao;

import java.util.Comparator;

import com.mongodb.BasicDBObject;

public class Tweet implements Comparable<Tweet>{
	
	private BasicDBObject tweet;
	
	public Tweet (BasicDBObject tweet){
		this.tweet = tweet;
	}
	
	public BasicDBObject getTweet(){
		return this.tweet;
	}

	@Override
	public int compareTo(Tweet o) {
		return Integer.parseInt(o.getTweet().get("retweet_count").toString()) 
				- Integer.parseInt(this.tweet.get("retweet_count").toString()); 
	}
	
	public static Comparator<Tweet> RetweetCountComparator = new Comparator<Tweet>(){
		
		public int compare(Tweet tweet1, Tweet tweet2){
			
			int retweet_count1 = Integer.parseInt(tweet1.getTweet().get("retweet_count").toString());
			int retweet_count2 = Integer.parseInt(tweet2.getTweet().get("retweet_count").toString());
			
			return retweet_count2 - retweet_count1;
		}
	};

}
