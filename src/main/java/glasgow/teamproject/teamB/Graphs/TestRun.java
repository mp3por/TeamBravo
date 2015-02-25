package glasgow.teamproject.teamB.Graphs;

import glasgow.teamproject.teamB.Counter.Counter;
import glasgow.teamproject.teamB.Counter.Counter.DateCountPair;
import glasgow.teamproject.teamB.Counter.Counter.EntityCountPair;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TestRun {

	private static Calendar today;
	private static Calendar end;
	private static Counter c;
	
	public static void main(String[] args) {
		
		/*today = Calendar.getInstance();
		//today.add(Calendar.DATE, -1);
		end = Calendar.getInstance();
		end.add(Calendar.DATE, -7);
		c = new Counter();
		c.dailyMapReduce(today.getTime());
		c.dailyMapReduce(end.getTime());
		//end.add(Calendar.DATE, -2);
		//c.dailyMapReduce(end.getTime());
		c.mergingMapReduce(Counter.TimePeriod.PASTMONTH);*/
		
		c = new Counter();
		//c.dailyMapReduce(new Date());
		//c.mergingMapReduce(Counter.TimePeriod.PASTMONTH);
		
		List<EntityCountPair> top3TopicsPastWeek = c.getTopEntities(Counter.Field.ALL, Counter.TimePeriod.PASTWEEK, 10);
		for(EntityCountPair e : top3TopicsPastWeek){
			System.out.println("Topic: " + e.getID() + "\nTweets: " + e.getCount().intValue());
		}
		
		List<DateCountPair> topic1PastWeek = c.getEntitiyTrend("tattoo",4);
		System.out.println("\nResults for 'Tattoo' in past 4 days:\n");
		for(DateCountPair d : topic1PastWeek){
			System.out.println("Date: " + d.getDate() + "\nTweets :" + d.getCount());
		}
	}

}
