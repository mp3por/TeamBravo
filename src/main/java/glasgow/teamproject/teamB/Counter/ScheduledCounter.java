package glasgow.teamproject.teamB.Counter;

import glasgow.teamproject.teamB.Util.ProjectProperties;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAO;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAOImpl.TimePeriod;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledCounter {

	@Autowired
	private TweetDAO tweetSaver;
	
	
	@Scheduled(cron="0 0/30 * * * ?")
    public void dailyMapReduce()
    {
		tweetSaver.dailyMapReduce(new Date(),ProjectProperties.TWEET_COLLECTION);
    }
	
	@Scheduled(cron="0 55 23 * * ?")
	public void mergingTables(){
		tweetSaver.mergingMapReduce(TimePeriod.PASTWEEK);
		tweetSaver.mergingMapReduce(TimePeriod.PASTMONTH);
	}
}
