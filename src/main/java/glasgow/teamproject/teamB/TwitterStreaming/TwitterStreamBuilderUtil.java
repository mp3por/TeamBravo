package glasgow.teamproject.teamB.TwitterStreaming;

import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterStreamBuilderUtil {
	
	static String consumerKey = "ROYeDUXIF4P8THyH4751ZGBAB";
	static String consumerSecret = "mU8fHzTrWLJx2XLOt3G5xEReDvFah8WOREB7rNe9j75LtXPCY3";
	static String accessKey = "2837300387-KPJSsNiEV4XzuzVYeiX9eL1kTuIYouMNxqVLX9k";
	static String accessSecret = "RoyzS63Q1D1EaZst5cncLSfVaGBQqA5zPPBBc9Jfy9Fmi";
	
	
	public static TwitterStream getStream() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
		cb.setOAuthConsumerKey(consumerKey);
		cb.setOAuthConsumerSecret(consumerSecret);
		cb.setOAuthAccessToken(accessKey );
		cb.setOAuthAccessTokenSecret(accessSecret);

		return new TwitterStreamFactory(cb.build()).getInstance();
	}
}