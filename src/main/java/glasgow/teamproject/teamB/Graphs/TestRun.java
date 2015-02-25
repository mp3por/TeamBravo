package glasgow.teamproject.teamB.Graphs;

import glasgow.teamproject.teamB.Counter.Counter;
import glasgow.teamproject.teamB.Counter.Counter.EntityCountPair;

import java.util.Calendar;
import java.util.List;

public class TestRun {

	private static Calendar today;
	private static Calendar end;
	private static Counter c;
	
	public static void main(String[] args) {
		
		today = Calendar.getInstance();
		//today.add(Calendar.DATE, -1);
		end = Calendar.getInstance();
		end.add(Calendar.DATE, -11);
		c = new Counter();
		c.dailyMapReduce(today.getTime());
		c.dailyMapReduce(end.getTime());
		//end.add(Calendar.DATE, -2);
		//c.dailyMapReduce(end.getTime());
		c.mergingMapReduce(Counter.TimePeriod.PASTMONTH);
		
		List<EntityCountPair> top3TopicsPastWeek = c.getTopEntities(Counter.Field.ALL, Counter.TimePeriod.PASTMONTH, 10);
		System.out.println("topics: " + top3TopicsPastWeek);

	}

}
