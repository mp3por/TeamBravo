package glasgow.teamproject.teamB.Search;

import java.util.Comparator;
import java.util.HashMap;

import org.json.JSONObject;

public class Tweet{
	
	private String tweet;
	private HashMap<String, Object> tweetMap;
	
	public Tweet (String tweet, HashMap<String, Object> tweetMap){
		this.tweet = tweet;
		this.tweetMap = tweetMap;
	}
	
	public String getTweet(){
		return this.tweet;
	}
	
	public HashMap<String, Object> getTweetMap(){
		return this.tweetMap;
	}
	
	@Override
	public String toString(){
		return this.tweet;
	}

	
	public static Comparator<Tweet> RetweetCountComparator = new Comparator<Tweet>(){
		@Override
		public int compare(Tweet tweet1, Tweet tweet2){
			
			JSONObject js1 = new JSONObject(tweet1.getTweet());
			JSONObject js2 = new JSONObject(tweet2.getTweet());
			
			int retweet_count1 = Integer.parseInt(js1.get("retweet_count").toString());
			int retweet_count2 = Integer.parseInt(js2.get("retweet_count").toString());
			
			return retweet_count2 - retweet_count1;
		}
	};
	
	public static Comparator<Tweet> PostedTimeComparator = new Comparator<Tweet>(){
			@Override
			public int compare(Tweet tweet1, Tweet tweet2){
				
			JSONObject js1 = new JSONObject(tweet1.getTweet());
			JSONObject js2 = new JSONObject(tweet2.getTweet());
			
			long posted_time1 = Long.parseLong(js1.get("timestamp_ms").toString());
			long posted_time2 = Long.parseLong(js2.get("timestamp_ms").toString());
			
			return (int) (posted_time2 - posted_time1);
		}
	};
	
	public static Comparator<Tweet> MostFavouritedComparator = new Comparator<Tweet>(){
			@Override
			public int compare(Tweet tweet1, Tweet tweet2){
			
				JSONObject js1 = new JSONObject(tweet1.getTweet());
				JSONObject js2 = new JSONObject(tweet2.getTweet());
				
				int favourite_count1 = Integer.parseInt(js1.get("favorite_count").toString());
				int favourite_count2 = Integer.parseInt(js2.get("favorite_count").toString());
				
				return (favourite_count1 - favourite_count2);			
			}
	};

}
