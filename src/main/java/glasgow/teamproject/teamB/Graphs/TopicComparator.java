package glasgow.teamproject.teamB.Graphs;

import java.util.Comparator;
import com.mongodb.DBObject;

public class TopicComparator implements Comparator<TopicWrapper> {

	@Override
	public int compare(TopicWrapper topic1, TopicWrapper topic2) {
		
		return  topic1.getNoOfTweets() > topic2.getNoOfTweets() ? -1 :
				topic1.getNoOfTweets() == topic2.getNoOfTweets() ? 0 : 1;
	}

}
