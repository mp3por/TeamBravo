package glasgow.teamproject.teamB.Twitter;

import twitter4j.RateLimitStatus;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


public class TwitterCreator implements Comparable<TwitterCreator>{
	private Twitter twitter;

	public TwitterCreator (String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret){
	ConfigurationBuilder cb = new ConfigurationBuilder();
	cb.setDebugEnabled(true)
	  .setOAuthConsumerKey(consumerKey)
	  .setOAuthConsumerSecret(consumerSecret)
	  .setOAuthAccessToken(accessToken)
	  .setOAuthAccessTokenSecret(accessTokenSecret);
	 this.twitter = new TwitterFactory(cb.build()).getInstance();
	}
	 public Twitter getTwitter() {
		 return this.twitter;
	 }
	 
	
	// returns how many searc queries you can do
	public int getRemainingQueries() throws TwitterException {
		RateLimitStatus status = this.twitter.getRateLimitStatus().get("/search/tweets");
		return status.getRemaining();
	}
	//
	public int getResetTimer () throws TwitterException{
	RateLimitStatus status = twitter.getRateLimitStatus().get("/search/tweets");
		return status.getSecondsUntilReset();
	}
	@Override
	public int compareTo(TwitterCreator o) {
		try {
			return this.getResetTimer()-o.getResetTimer();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}

