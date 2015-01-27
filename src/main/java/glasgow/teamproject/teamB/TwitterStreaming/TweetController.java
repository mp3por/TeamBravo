package glasgow.teamproject.teamB.TwitterStreaming;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class TweetController {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	
	@RequestMapping("/all")
	public ModelAndView allTweets(){
//		System.out.println("getting all tweets");
		ModelAndView mv = new ModelAndView("allTweets");
		
		List<HashMap<String,String>> tweets = getTweets();
		
		mv.addObject("tweets",tweets);
		
		return mv;
	}

	/**
	 * This method will handle the connection to the db later
	 * */
	private List<HashMap<String, String>> getTweets() {
		List<HashMap<String,String>> tweets = new ArrayList<HashMap<String,String>>();
		HashMap<String, String> tweet1 = new HashMap<String,String>();
		HashMap<String, String> tweet2 = new HashMap<String,String>();
		HashMap<String, String> tweet3 = new HashMap<String,String>();
		
		tweet1.put("name", "Vili");
		tweet1.put("text", "What's up ???");
		tweet1.put("time", "14:57");
		tweet1.put("location", "Glasgow");
		
		tweet2.put("name", "Rali");
		tweet2.put("text", "OMG");
		tweet2.put("time", "14:58");
		tweet2.put("location", "Glasgow");
		
		tweet3.put("name", "Niki");
		tweet3.put("text", "Yes this is Dog?");
		tweet3.put("time", "14:59");
		tweet3.put("location", "Glasgow");
		
		tweets.add(tweet1);
		tweets.add(tweet2);
		tweets.add(tweet3);
		
		return tweets;
	}
}
