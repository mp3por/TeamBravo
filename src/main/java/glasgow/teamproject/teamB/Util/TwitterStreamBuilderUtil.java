package glasgow.teamproject.teamB.Util;

import org.springframework.stereotype.Component;

import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;


@Component
public class TwitterStreamBuilderUtil {
	
// SpringMVCTP3
//	static String consumerKey = "ROYeDUXIF4P8THyH4751ZGBAB";
//	static String consumerSecret = "mU8fHzTrWLJx2XLOt3G5xEReDvFah8WOREB7rNe9j75LtXPCY3";
//	static String accessKey = "2837300387-KPJSsNiEV4XzuzVYeiX9eL1kTuIYouMNxqVLX9k";
//	static String accessSecret = "RoyzS63Q1D1EaZst5cncLSfVaGBQqA5zPPBBc9Jfy9Fmi";
//	
	
	// TeamProject
	static String consumerKey = "jssgEMabXna5MMtjEkGCpZNHP";
	static String consumerSecret = "u9tNTHuXYy4LU5MonpyxQrX9chCIbmFvgO8b0s5FfUNgrrGEDV";
	static String accessKey = "2837300387-4sddnex8etZHgkjvz1sauzm6XU3XTGJWP0ylOPw";
	static String accessSecret = "RB5OlAUiW4C2WXM5cilNVfW6UKwMzJMXkDvCjdcIYeVkT";
	
	
	
	
	
	public TwitterStream getStream() {
		System.out.println("getStream");
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
		cb.setOAuthConsumerKey(consumerKey);
		cb.setOAuthConsumerSecret(consumerSecret);
		cb.setOAuthAccessToken(accessKey );
		cb.setOAuthAccessTokenSecret(accessSecret);

		TwitterStream stream = new TwitterStreamFactory(cb.build()).getInstance();
		System.out.println("stream OK");
		return stream;
	}
}