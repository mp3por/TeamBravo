package glasgow.teamproject.teamB.Counter;

import glasgow.teamproject.teamB.Counter.Counter.DateCountPair;
import glasgow.teamproject.teamB.Counter.Counter.EntityCountPair;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAO;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CounterController {

	@Autowired
	private TweetDAO DBHelper;

	Counter c = null;

	@RequestMapping("/stat")
	public ModelAndView getStat() {
		ModelAndView model = new ModelAndView("statistics");
	
		// TODO
		Calendar stCal = Calendar.getInstance();
		Calendar edCal = Calendar.getInstance();
		stCal.set(Calendar.HOUR_OF_DAY, 0);
		stCal.set(Calendar.MINUTE, 0);
		stCal.set(Calendar.SECOND, 0);
		stCal.set(Calendar.MILLISECOND, 0);
		edCal.set(Calendar.HOUR_OF_DAY, 12);
		edCal.set(Calendar.MINUTE, 0);
		edCal.set(Calendar.SECOND, 0);
		edCal.set(Calendar.MILLISECOND, 0);
		
		model.addObject("total_tweets", DBHelper.getTweetCount(stCal.getTime(), edCal.getTime(), false));
		model.addObject("total_retweets", DBHelper.getTweetCount(stCal.getTime(), edCal.getTime(), true));
		
		HashMap<String,Object> most_retweeted = DBHelper.getMostPopularTweet(stCal.getTime(), edCal.getTime(), "retweet_count");
		model.addObject("most_retweeted_tweet", most_retweeted.get("text"));
		model.addObject("most_retweeted_tweet_user", most_retweeted.get("user"));
		
		HashMap<String,Object> most_fav = DBHelper.getMostPopularTweet(stCal.getTime(), edCal.getTime(), "favorite_count");
		model.addObject("most_fav_tweet", most_fav.get("text"));
		model.addObject("most_fav_tweet_user", most_fav.get("user"));
		
		return model;
	}

	@RequestMapping("/test")
	public ModelAndView getCount() {

		if (c == null)
			c = new Counter();

		Calendar today = Calendar.getInstance();
		c.dailyMapReduce(today.getTime());
		// today.add(Calendar.DATE, -1);
		// c.dailyMapReduce(today.getTime());
		// today.add(Calendar.DATE, -4);
		// c.dailyMapReduce(today.getTime());
		// today.add(Calendar.DATE, -2);
		// c.dailyMapReduce(today.getTime());

		c.mergingMapReduce(Counter.TimePeriod.PASTWEEK);

		StringBuilder sb = new StringBuilder();
		sb.append("Top Entities<br>");
		List<EntityCountPair> l = c.getTopEntities(Counter.Field.PERSON,
				Counter.TimePeriod.PASTWEEK, 20);
		for (EntityCountPair e : l) {
			sb.append(e.getID() + " : " + e.getCount().intValue() + "<br>");
		}
		sb.append("<br><br>");

		ModelAndView model = new ModelAndView("counter");
		model.addObject("output1", sb.toString());

		StringBuilder sb2 = new StringBuilder();
		sb2.append("Entity Search \"rain\"<br>");
		List<DateCountPair> l2 = c.getEntitiyTrend("rain", 7);
		for (DateCountPair e : l2) {
			sb2.append(e.getDate() + " : " + e.getCount() + "<br>");
		}
		model.addObject("output2", sb2.toString());

		return model;
	}

}
