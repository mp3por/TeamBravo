package glasgow.teamproject.teamB.Counter;

import glasgow.teamproject.teamB.mongodb.dao.TweetDAO;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAOImpl.EntityCountPair;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAOImpl.Field;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAOImpl.TimePeriod;

import java.util.Calendar;
import java.util.Date;
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

	@RequestMapping("/getStats/{timePeriod}")
	@ResponseBody
	public ModelAndView getStats(@PathVariable("timePeriod") String timePeriod) {
		ModelAndView model = new ModelAndView("only-stats");

		TimePeriod p;
		Date stTime = null, edTime = null;
		if (!timePeriod.equals("allTime")) {
			Calendar stCal = Calendar.getInstance();
			Calendar edCal = Calendar.getInstance();
			if (timePeriod.equals("pastMonth")) {
				p = TimePeriod.PASTMONTH;
				stCal.add(Calendar.DATE, -30);
			} else if (timePeriod.equals("pastWeek")) {
				p = TimePeriod.PASTWEEK;
				stCal.add(Calendar.DATE, -7);
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

		/*HashMap<String, Object> most_retweeted = DBHelper.getMostPopularTweet(
				stTime, edTime, "retweet_count");
		if (!most_retweeted.isEmpty()) {
			model.addObject("most_retweeted_tweet", most_retweeted.get("text"));
			String most_retweeted_link = most_retweeted.get("screen_name")
					+ " <a href=\"https://twitter.com/"
					+ most_retweeted.get("username") + "\">@"
					+ most_retweeted.get("username") + "</a>";
			model.addObject("most_retweeted_tweet_user", most_retweeted_link);
		}

		HashMap<String, Object> most_fav = DBHelper.getMostPopularTweet(stTime,
				edTime, "favorite_count");
		if (!most_fav.isEmpty()) {
			model.addObject("most_fav_tweet", most_fav.get("text"));
			String most_fav_link = most_fav.get("screen_name")
					+ " <a href=\"https://twitter.com/"
					+ most_fav.get("username") + "\">@"
					+ most_fav.get("username") + "</a>";
			model.addObject("most_fav_tweet_user", most_fav_link);
		}*/

		String most_active = DBHelper.getMostActiveUser(stTime, edTime);
		if (most_active != null) {
			String most_active_link = "<a href=\"https://twitter.com/"
					+ most_active + "\">@" + most_active + "</a>";
			model.addObject("most_active_user", most_active_link);
		} else {
			model.addObject("most_active_user", "-");
		}

		List<EntityCountPair> most_pop_user = DBHelper.getTopEntities(
				Field.USERID, p, 5);
		if (!most_pop_user.isEmpty()) {
			String username = most_pop_user.get(0).getID();
			String link = "<a href=\"https://twitter.com/" + username + "\">@"
					+ username + "</a>";
			model.addObject("most_mentioned_user", link);
		} else {
			model.addObject("most_mentioned_user", "-");
		}
		List<EntityCountPair> most_pop_hashtag = DBHelper.getTopEntities(
				Field.HASHTAG, p, 5);
		if (!most_pop_hashtag.isEmpty())
			model.addObject("most_pop_hashtag", 
					
					"<a href=\"/TeamBravo/search/terrier/"
							+ most_pop_hashtag.get(0).getID() + "\">"
							+ most_pop_hashtag.get(0).getID() + "</a>"); 
		else
			model.addObject("most_pop_hashtag", "-");

		// /TeamBravo/

		List<EntityCountPair> most_pop_location = DBHelper.getTopEntities(
				Field.LOCATION, p, 5);
		if (!most_pop_location.isEmpty())
			model.addObject("most_pop_location", 
					"<a href=\"/TeamBravo/search/terrier/"
							+ most_pop_location.get(0).getID() + "\">"
							+ most_pop_location.get(0).getID() + "</a>"); 
							else
			model.addObject("most_pop_location", "-");

		List<EntityCountPair> most_pop_person = DBHelper.getTopEntities(
				Field.PERSON, p, 5);
		if (!most_pop_person.isEmpty())
			model.addObject("most_pop_person",
					"<a href=\"/TeamBravo/search/terrier/"
							+ most_pop_person.get(0).getID() + "\">"
							+ most_pop_person.get(0).getID() + "</a>"); 
		else
			model.addObject("most_pop_person", "-");

		return model;
	}

	@RequestMapping("/stats/getSettings")
	@ResponseBody
	public ModelAndView getSettings() {
		ModelAndView mv = new ModelAndView("setting-stats");
		return mv;
	}

}
