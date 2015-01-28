package glasgow.teamproject.teamB.TwitterStreaming;

import glasgow.teamproject.teamB.mongodb.dao.TweetDAO;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAOImpl;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mongodb.DBObject;
import com.mongodb.MongoClient;


@Controller
public class TweetController {
	private final String DB_NAME = "tweetsTest";
	private final String MONGO_HOST = "localhost";
	private final int MONGO_PORT = 27017;
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	
	@RequestMapping("/all")
	public ModelAndView allTweets() throws UnknownHostException{
		ModelAndView mv = new ModelAndView("betterAllTweets");
		
		List<DBObject> tweets = getTweets();
		
		System.out.println(tweets.size());
		
		for (DBObject dbobj: tweets) {
			//System.out.println(dbobj.get("created_at"));
			System.out.println(dbobj.keySet());
		}
		
		mv.addObject("tweets",tweets);
		
		return mv;
	}
	
	/*@RequestMapping("allJustTweets")
	public ModelAndView justTweets() throws UnknownHostException{
		ModelAndView mv = new ModelAndView("allJustTweets");
		
		List<HashMap<String,String>> tweets = getTweets();
		
		mv.addObject("tweets",tweets);
		
		return mv;
	}
*/
	/**
	 * This method will handle the connection to the db later
	 * @throws UnknownHostException 
	 * */
	private ArrayList<DBObject> getTweets() throws UnknownHostException {
		MongoClient mongo = new MongoClient(MONGO_HOST, MONGO_PORT);
		MongoOperations mongoOps = new MongoTemplate(mongo, DB_NAME);
		TweetDAO tweetSaver = new TweetDAOImpl(mongoOps);

		System.out.println(mongoOps.collectionExists("tweets"));
		
		ArrayList<DBObject> t = tweetSaver.getLastTweets(10, "tweets");
		
		return t;
		/*1. Tweet: hashmap<String, ArrayList<String>>
		2. 
		3. 
		*/
		
		
		//System.out.println(t.get(0).keySet());
		//System.out.println(t);*/
		
		/*List<HashMap<String,String>> tweets = new ArrayList<HashMap<String,String>>();
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
		
		return tweets;*/
	}
}
