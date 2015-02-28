package glasgow.teamproject.teamB.TwitterStreaming;

import glasgow.teamproject.teamB.mongodb.dao.TweetDAO;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class TweetController {
	
	@Autowired
	private TweetDAO tweetSaver;
	
	//private final int TWEETS_TO_RECEIVE = 20;
	protected final Log logger = LogFactory.getLog(getClass());
	
	@RequestMapping("/tweetWall/{amount}/{dateFrom}/{dateTo}")
	public ModelAndView allTweetsTest(@PathVariable("amount") String amount, @PathVariable("dateFrom") String dateFrom, @PathVariable("dateTo") String dateTo) throws UnknownHostException{
		ModelAndView mv = new ModelAndView("only-tweets");
		
		List<HashMap<String,Object>> tweets = getTweets(Integer.parseInt(amount), dateFrom, dateTo);
		mv.addObject("tweets",tweets);
		//mv.addObject("needed","<div id='added_tweetwall_container' class='tweetwall-container'><div id='added_tweetwall_div' class='tweetwall'></div></div>");
		return mv;
	}
	
		
	/**
	 * This method will handle the connection to the db later
	 * @throws UnknownHostException 
	 * */
	private ArrayList<HashMap<String,Object>> getTweets(int amount, String dateFrom, String dateTo) throws UnknownHostException {
		ArrayList<HashMap<String,Object>> t;		
		if (dateFrom.compareTo("0") == 0) {
		 t = tweetSaver.getLastTweets(amount, "tweets");
		}
		else {
		 t = tweetSaver.getTweetsForDate(amount, dateFrom, dateTo, "tweets");
		}
		System.out.println(t.size());
		return t;
	
	}

	public ModelAndView allTweetsTest2(Set<String> tweetsSet){
		ModelAndView mv = new ModelAndView("only-tweets");
		//List<HashMap<String,Object>> tweets = getTweets();
		List<HashMap<String,Object>> tweets = new ArrayList<HashMap<String,Object>>();
		for(String tweet: tweetsSet){
			HashMap<String, Object> hmobj = new HashMap<String, Object>();
			//hmobj.put(key, value)
			//tweets.add();
		}
		mv.addObject("tweets",tweets);
		//mv.addObject("needed","<div id='added_tweetwall_container' class='tweetwall-container'><div id='added_tweetwall_div' class='tweetwall'></div></div>");
		return mv;
	}
}
