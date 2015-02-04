package glasgow.teamproject.teamB.Util;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.stereotype.Component;


@Component
public class ProjectProperties {
	
	public final String DB_NAME = "tweetsTest";
	
	public final String TWEET_COLLECTION = "tweets";
	
	public final static HashSet<String> defaultNE = new HashSet<String>(Arrays.asList("Location", "Organization", "Person", "Hashtag", "URL", "UserID", "Emoticon"));
	

}
