package glasgow.teamproject.teamB.Util;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.stereotype.Component;


@Component
public class ProjectProperties {
	
	public static final String DB_NAME = "tweetsTest";
	
	public static final String TWEET_COLLECTION = "tweets";
	
	public final static HashSet<String> defaultNE = new HashSet<String>(Arrays.asList("Location", "Organization", "Person", "Hashtag", "URL", "UserID"));
	
	
	public static final String DAILY_COLLECT_NAME = "DCNT";
	public static final String WEEKLY_COLLECT_NAME = "WCNT";
	public static final String MONTHLY_COLLECT_NAME = "MCNT";

	public static final String COUNTER_DATE_FORMAT = "yyyy-MM-dd";

}
