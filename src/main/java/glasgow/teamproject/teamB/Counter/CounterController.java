package glasgow.teamproject.teamB.Counter;

import glasgow.teamproject.teamB.mongodb.dao.TweetDAO;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAOImpl.EntityCountPair;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAOImpl.Field;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAOImpl.TimePeriod;

import java.util.Calendar;
import java.util.Date;
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

	@RequestMapping("/stat")
	public ModelAndView getStat() {
		ModelAndView model = new ModelAndView("statistics");

		DBHelper.dailyMapReduce(new Date());

		// TODO
		Calendar stCal = Calendar.getInstance();
		Calendar edCal = Calendar.getInstance();
		stCal.set(Calendar.HOUR_OF_DAY, 0);
		stCal.set(Calendar.MINUTE, 0);
		stCal.set(Calendar.SECOND, 0);
		stCal.set(Calendar.MILLISECOND, 0);
		edCal.set(Calendar.HOUR_OF_DAY, 17);
		edCal.set(Calendar.MINUTE, 50);
		edCal.set(Calendar.SECOND, 0);
		edCal.set(Calendar.MILLISECOND, 0);

		model.addObject("total_tweets", DBHelper.getTweetCount(stCal.getTime(), edCal.getTime(), false));
		model.addObject("total_retweets", DBHelper.getTweetCount(stCal.getTime(), edCal.getTime(), true));

		HashMap<String, Object> most_retweeted = DBHelper.getMostPopularTweet(stCal.getTime(), edCal.getTime(), "retweet_count");
		model.addObject("most_retweeted_tweet", most_retweeted.get("text"));
		String most_retweeted_link = most_retweeted.get("screen_name") + " <a href=\"https://twitter.com/" + most_retweeted.get("username") + "\">@" + most_retweeted.get("username") + "</a>";
		model.addObject("most_retweeted_tweet_user", most_retweeted_link);

		HashMap<String, Object> most_fav = DBHelper.getMostPopularTweet(stCal.getTime(), edCal.getTime(), "favorite_count");
		model.addObject("most_fav_tweet", most_fav.get("text"));
		String most_fav_link = most_fav.get("screen_name") + " <a href=\"https://twitter.com/" + most_fav.get("username") + "\">@" + most_fav.get("username") + "</a>";
		model.addObject("most_fav_tweet_user", most_fav_link);

		String most_active = DBHelper.getMostActiveUser(stCal.getTime(), edCal.getTime());
		String most_active_link = "<a href=\"https://twitter.com/" + most_active + "\">@" + most_active + "</a>";
		model.addObject("most_active_user", most_active_link);

		List<EntityCountPair> most_pop_user = DBHelper.getTopEntities(Field.USERID, TimePeriod.PASTDAY, 1);
		if (!most_pop_user.isEmpty()) {
			String username = most_pop_user.get(0).getID();
			String link = "<a href=\"https://twitter.com/" + username + "\">@" + username + "</a>";
			model.addObject("most_mentioned_user", link);
		}

		List<EntityCountPair> most_pop_hashtag = DBHelper.getTopEntities(Field.HASHTAG, TimePeriod.PASTDAY, 1);
		if (!most_pop_hashtag.isEmpty())
			model.addObject("most_pop_hashtag", most_pop_hashtag.get(0).getID());

		List<EntityCountPair> most_pop_location = DBHelper.getTopEntities(Field.LOCATION, TimePeriod.PASTDAY, 1);
		if (!most_pop_location.isEmpty())
			model.addObject("most_pop_location", most_pop_location.get(0).getID());

		List<EntityCountPair> most_pop_person = DBHelper.getTopEntities(Field.PERSON, TimePeriod.PASTDAY, 1);
		if (!most_pop_person.isEmpty())
			model.addObject("most_pop_person", most_pop_person.get(0).getID());

		return model;
	}

	@RequestMapping("/test")
	public ModelAndView getStat2() {
		ModelAndView model = new ModelAndView("only-stats");

		DBHelper.dailyMapReduce(new Date());

		// TODO
		Calendar stCal = Calendar.getInstance();
		Calendar edCal = Calendar.getInstance();
		stCal.set(Calendar.HOUR_OF_DAY, 0);
		stCal.set(Calendar.MINUTE, 0);
		stCal.set(Calendar.SECOND, 0);
		stCal.set(Calendar.MILLISECOND, 0);
		edCal.set(Calendar.HOUR_OF_DAY, 17);
		edCal.set(Calendar.MINUTE, 50);
		edCal.set(Calendar.SECOND, 0);
		edCal.set(Calendar.MILLISECOND, 0);

		model.addObject("total_tweets", DBHelper.getTweetCount(stCal.getTime(), edCal.getTime(), false));
		model.addObject("total_retweets", DBHelper.getTweetCount(stCal.getTime(), edCal.getTime(), true));

		HashMap<String, Object> most_retweeted = DBHelper.getMostPopularTweet(stCal.getTime(), edCal.getTime(), "retweet_count");
		model.addObject("most_retweeted_tweet", most_retweeted.get("text"));
		String most_retweeted_link = most_retweeted.get("screen_name") + " <a href=\"https://twitter.com/" + most_retweeted.get("username") + "\">@" + most_retweeted.get("username") + "</a>";
		model.addObject("most_retweeted_tweet_user", most_retweeted_link);

		HashMap<String, Object> most_fav = DBHelper.getMostPopularTweet(stCal.getTime(), edCal.getTime(), "favorite_count");
		model.addObject("most_fav_tweet", most_fav.get("text"));
		String most_fav_link = most_fav.get("screen_name") + " <a href=\"https://twitter.com/" + most_fav.get("username") + "\">@" + most_fav.get("username") + "</a>";
		model.addObject("most_fav_tweet_user", most_fav_link);

		String most_active = DBHelper.getMostActiveUser(stCal.getTime(), edCal.getTime());
		String most_active_link = "<a href=\"https://twitter.com/" + most_active + "\">@" + most_active + "</a>";
		model.addObject("most_active_user", most_active_link);

		List<EntityCountPair> most_pop_user = DBHelper.getTopEntities(Field.USERID, TimePeriod.PASTDAY, 1);
		if (!most_pop_user.isEmpty()) {
			String username = most_pop_user.get(0).getID();
			String link = "<a href=\"https://twitter.com/" + username + "\">@" + username + "</a>";
			model.addObject("most_mentioned_user", link);
		}

		List<EntityCountPair> most_pop_hashtag = DBHelper.getTopEntities(Field.HASHTAG, TimePeriod.PASTDAY, 1);
		if (!most_pop_hashtag.isEmpty())
			model.addObject("most_pop_hashtag", most_pop_hashtag.get(0).getID());

		List<EntityCountPair> most_pop_location = DBHelper.getTopEntities(Field.LOCATION, TimePeriod.PASTDAY, 1);
		if (!most_pop_location.isEmpty())
			model.addObject("most_pop_location", most_pop_location.get(0).getID());

		List<EntityCountPair> most_pop_person = DBHelper.getTopEntities(Field.PERSON, TimePeriod.PASTDAY, 1);
		if (!most_pop_person.isEmpty())
			model.addObject("most_pop_person", most_pop_person.get(0).getID());

		return model;
	}

	//	@RequestMapping("/test")
	//	public ModelAndView getCount() {
	//
	//		if (c == null)
	//			c = new Counter();
	//
	//		Calendar today = Calendar.getInstance();
	//		c.dailyMapReduce(today.getTime());
	// today.add(Calendar.DATE, -1);
	// c.dailyMapReduce(today.getTime());
	// today.add(Calendar.DATE, -4);
	// c.dailyMapReduce(today.getTime());
	// today.add(Calendar.DATE, -2);
	// c.dailyMapReduce(today.getTime());

	//		c.mergingMapReduce(Counter.TimePeriod.PASTWEEK);
	//
	//		StringBuilder sb = new StringBuilder();
	//		sb.append("Top Entities<br>");
	//		List<EntityCountPair> l = c.getTopEntities(Counter.Field.PERSON,
	//				Counter.TimePeriod.PASTWEEK, 20);
	//		for (EntityCountPair e : l) {
	//			sb.append(e.getID() + " : " + e.getCount().intValue() + "<br>");
	//		}
	//		sb.append("<br><br>");
	//
	//		ModelAndView model = new ModelAndView("counter");
	//		model.addObject("output1", sb.toString());
	//
	//		StringBuilder sb2 = new StringBuilder();
	//		sb2.append("Entity Search \"rain\"<br>");
	//		List<DateCountPair> l2 = c.getEntitiyTrend("rain", 7);
	//		for (DateCountPair e : l2) {
	//			sb2.append(e.getDate() + " : " + e.getCount() + "<br>");
	//		}
	//		model.addObject("output2", sb2.toString());

	//		return model;
	//	}

}
