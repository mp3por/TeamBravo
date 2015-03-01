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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CounterController {

	@Autowired
	private TweetDAO DBHelper;

	@RequestMapping("/stat")
	public ModelAndView forTest() {
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

		model.addObject("total_tweets",
				DBHelper.getTweetCount(stCal.getTime(), edCal.getTime(), false));
		model.addObject("total_retweets",
				DBHelper.getTweetCount(stCal.getTime(), edCal.getTime(), true));

		HashMap<String, Object> most_retweeted = DBHelper.getMostPopularTweet(
				stCal.getTime(), edCal.getTime(), "retweet_count");
		if (!most_retweeted.isEmpty()) {
			model.addObject("most_retweeted_tweet", most_retweeted.get("text"));
			String most_retweeted_link = most_retweeted.get("screen_name")
					+ " <a href=\"https://twitter.com/"
					+ most_retweeted.get("username") + "\">@"
					+ most_retweeted.get("username") + "</a>";
			model.addObject("most_retweeted_tweet_user", most_retweeted_link);
		}

		HashMap<String, Object> most_fav = DBHelper.getMostPopularTweet(
				stCal.getTime(), edCal.getTime(), "favorite_count");
		if (!most_fav.isEmpty()) {
			model.addObject("most_fav_tweet", most_fav.get("text"));
			String most_fav_link = most_fav.get("screen_name")
					+ " <a href=\"https://twitter.com/"
					+ most_fav.get("username") + "\">@"
					+ most_fav.get("username") + "</a>";
			model.addObject("most_fav_tweet_user", most_fav_link);
		}

		String most_active = DBHelper.getMostActiveUser(stCal.getTime(),
				edCal.getTime());
		String most_active_link = "<a href=\"https://twitter.com/"
				+ most_active + "\">@" + most_active + "</a>";
		model.addObject("most_active_user", most_active_link);

		List<EntityCountPair> most_pop_user = DBHelper.getTopEntities(
				Field.USERID, TimePeriod.PASTDAY, 1);
		if (!most_pop_user.isEmpty()) {
			String username = most_pop_user.get(0).getID();
			String link = "<a href=\"https://twitter.com/" + username + "\">@"
					+ username + "</a>";
			model.addObject("most_mentioned_user", link);
		}

		List<EntityCountPair> most_pop_hashtag = DBHelper.getTopEntities(
				Field.HASHTAG, TimePeriod.PASTDAY, 1);
		if (!most_pop_hashtag.isEmpty())
			model.addObject("most_pop_hashtag", most_pop_hashtag.get(0).getID());

		List<EntityCountPair> most_pop_location = DBHelper.getTopEntities(
				Field.LOCATION, TimePeriod.PASTDAY, 1);
		if (!most_pop_location.isEmpty())
			model.addObject("most_pop_location", most_pop_location.get(0)
					.getID());

		List<EntityCountPair> most_pop_person = DBHelper.getTopEntities(
				Field.PERSON, TimePeriod.PASTDAY, 1);
		if (!most_pop_person.isEmpty())
			model.addObject("most_pop_person", most_pop_person.get(0).getID());

		return model;
	}

	@RequestMapping("/getStats/{timePeriod}")
	@ResponseBody
	public ModelAndView getStats(@PathVariable("timePeriod") String timePeriod) {
		ModelAndView model = new ModelAndView("only-stats");

		TimePeriod p;
		Date stTime = null, edTime = null;
		// TODO
		if( !timePeriod.equals("allTime") ) {
			Calendar stCal = Calendar.getInstance();
			Calendar edCal = Calendar.getInstance();
			if( timePeriod.equals("pastMonth") ) {
				p = TimePeriod.PASTMONTH;
				edCal.add(Calendar.DATE, -30);
			}
			else if ( timePeriod.equals("pastWeek") ) {
				p = TimePeriod.PASTWEEK;
				edCal.add(Calendar.DATE, -7);
			} else {
				p = TimePeriod.PASTDAY;
			}
			stCal.set(Calendar.HOUR_OF_DAY, 0);
			stCal.set(Calendar.MINUTE, 0);
			stCal.set(Calendar.SECOND, 0);
			stCal.set(Calendar.MILLISECOND, 0);
			edCal.set(Calendar.HOUR_OF_DAY, 23);
			edCal.set(Calendar.MINUTE, 59);
			edCal.set(Calendar.SECOND, 59);
			edCal.set(Calendar.MILLISECOND, 999);
			stTime = stCal.getTime();
			edTime = edCal.getTime();
		} else {
			p = TimePeriod.ALLTIME;
		}

		model.addObject("total_tweets",
				DBHelper.getTweetCount(stTime, edTime, false));
		model.addObject("total_retweets",
				DBHelper.getTweetCount(stTime, edTime, true));

		HashMap<String, Object> most_retweeted = DBHelper.getMostPopularTweet(
				stTime, edTime, "retweet_count");
		if (!most_retweeted.isEmpty()) {
			model.addObject("most_retweeted_tweet", most_retweeted.get("text"));
			String most_retweeted_link = most_retweeted.get("screen_name")
					+ " <a href=\"https://twitter.com/"
					+ most_retweeted.get("username") + "\">@"
					+ most_retweeted.get("username") + "</a>";
			model.addObject("most_retweeted_tweet_user", most_retweeted_link);
		}

		HashMap<String, Object> most_fav = DBHelper.getMostPopularTweet(
				stTime, edTime, "favorite_count");
		if (!most_fav.isEmpty()) {
			model.addObject("most_fav_tweet", most_fav.get("text"));
			String most_fav_link = most_fav.get("screen_name")
					+ " <a href=\"https://twitter.com/"
					+ most_fav.get("username") + "\">@"
					+ most_fav.get("username") + "</a>";
			model.addObject("most_fav_tweet_user", most_fav_link);
		}

		String most_active = DBHelper.getMostActiveUser(stTime,
				edTime);
		String most_active_link = "<a href=\"https://twitter.com/"
				+ most_active + "\">@" + most_active + "</a>";
		model.addObject("most_active_user", most_active_link);

		List<EntityCountPair> most_pop_user = DBHelper.getTopEntities(
				Field.USERID, p, 1);
		if (!most_pop_user.isEmpty()) {
			String username = most_pop_user.get(0).getID();
			String link = "<a href=\"https://twitter.com/" + username + "\">@"
					+ username + "</a>";
			model.addObject("most_mentioned_user", link);
		}

		List<EntityCountPair> most_pop_hashtag = DBHelper.getTopEntities(
				Field.HASHTAG, p, 1);
		if (!most_pop_hashtag.isEmpty())
			model.addObject("most_pop_hashtag", most_pop_hashtag.get(0).getID());

		List<EntityCountPair> most_pop_location = DBHelper.getTopEntities(
				Field.LOCATION, p, 1);
		if (!most_pop_location.isEmpty())
			model.addObject("most_pop_location", most_pop_location.get(0)
					.getID());

		List<EntityCountPair> most_pop_person = DBHelper.getTopEntities(
				Field.PERSON, p, 1);
		if (!most_pop_person.isEmpty())
			model.addObject("most_pop_person", most_pop_person.get(0).getID());

		return model;
	}

	@RequestMapping("/stats/getSettings")
	@ResponseBody
	public ModelAndView getSettings() {
		ModelAndView mv = new ModelAndView("setting-stats");
		return mv;
	}

}
