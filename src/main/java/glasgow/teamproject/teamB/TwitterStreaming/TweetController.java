package glasgow.teamproject.teamB.TwitterStreaming;

import glasgow.teamproject.teamB.Main.SearchResultsInterface;
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
public class TweetController implements SearchResultsInterface {
	
	@Autowired
	private TweetDAO tweetSaver;
	
	private final int TWEETS_TO_RECEIVE = 20;
	protected final Log logger = LogFactory.getLog(getClass());
	
	
	@RequestMapping("/all")
	public ModelAndView allTweets() throws UnknownHostException{
		ModelAndView mv = new ModelAndView("betterAllTweets");
		List<HashMap<String,Object>> tweets = getTweets();
		mv.addObject("tweets",tweets);
		mv.addObject("needed","<div id='added_tweetwall_container' class='tweetwall-container'><div id='added_tweetwall_div' class='tweetwall'></div></div>");
		return mv;
	}
	
	
	
	@RequestMapping("/test")
	public ModelAndView allTweetsTest() throws UnknownHostException{
		ModelAndView mv = new ModelAndView("only-tweets");
		List<HashMap<String,Object>> tweets = getTweets();
		mv.addObject("tweets",tweets);
		//mv.addObject("needed","<div id='added_tweetwall_container' class='tweetwall-container'><div id='added_tweetwall_div' class='tweetwall'></div></div>");
		return mv;
	}
	
		
	/**
	 * This method will handle the connection to the db later
	 * @throws UnknownHostException 
	 * */
	private ArrayList<HashMap<String,Object>> getTweets() throws UnknownHostException {
				
		ArrayList<HashMap<String,Object>> t = tweetSaver.getLastTweets(TWEETS_TO_RECEIVE, "tweets");		
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
	

	@Override
	public String getResultsForSetOfTweets(Set<String> tweetsSet) {
		// TODO Auto-generated method stub
		String r = allTweetsTest2(tweetsSet).toString();
		return r;
	}
}
