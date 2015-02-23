package glasgow.teamproject.teamB.TwitterStreaming;

import glasgow.teamproject.teamB.Search.dao.SearchDAOImpl;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAO;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
		
	protected final Log logger = LogFactory.getLog(getClass());
	
	
	@RequestMapping("/all")
	public ModelAndView allTweets() throws UnknownHostException{
		ModelAndView mv = new ModelAndView("betterAllTweets");
		List<HashMap<String,Object>> tweets = getTweets();
		mv.addObject("tweets",tweets);
		return mv;
	}
	/**
	 * This method will handle the connection to the db later
	 * @throws UnknownHostException 
	 * */
	private ArrayList<HashMap<String,Object>> getTweets() throws UnknownHostException {
				
		ArrayList<HashMap<String,Object>> t = tweetSaver.getLastTweets(6, "tweets");		
		return t;
	
	}
}
