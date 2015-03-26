package glasgow.teamproject.teamB.Search;

import glasgow.teamproject.teamB.mongodb.dao.TweetDAO;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SearchController {
	
	@Autowired
	private TweetDAO tweetSaver;
	
	@Autowired
	private SearchRetriever dao;
	
	
	@RequestMapping("/searchBox")
	public ModelAndView searchBox(){
		String now = (new Date()).toString();
		String viewName = "search_box";
		ModelAndView mv = new ModelAndView(viewName);
		mv.addObject("serverTime", now);
		return mv;
	}
	
	@RequestMapping("/tile_template_search")
	public String getTeplate(){
		return "tile_template_search";
	}
	
	@RequestMapping("/terrier/{query}")
	public ModelAndView searchPage(@PathVariable("query") String query){
		
		dao.runQuery(query);
    	
		ModelAndView modelandview = new ModelAndView("TerrierResult");
		String reformattedQuery = reformatQuery(query);
		modelandview.addObject("numberOfTweetsToShow", dao.getResultsList().size());
		modelandview.addObject("numberOfTweets", dao.getResultsCount());
		modelandview.addObject("unformattedQuery", query);
		modelandview.addObject("query", reformattedQuery);		
		return modelandview;
	}
	
	@RequestMapping("/terrier/tweetwall/{query}")
	public ModelAndView tweetWall(@PathVariable("query") String query){
//		dao.runQuery("normal", query);
		ModelAndView modelandview = new ModelAndView("search_tweet_wall");
		List<HashMap<String,Object>> tweets = dao.getTweetsForTweetWall(dao.getResultsList());
		modelandview.addObject("tweets", tweets);
		return modelandview;
	}
	
	@RequestMapping("/terrier/tweetwall/{mode}/{query}")
	public ModelAndView tweetWallRanked(@PathVariable Map<String, String> pathVar) {
		
		String mode = pathVar.get("mode");
		ModelAndView modelandview = new ModelAndView("search_tweet_wall");
		System.err.println("mode is: " + mode);
		
		List<Tweet> l = dao.getResultsList();
		
		if (mode.equals("retweeted")){
			System.err.println("Ranking by retweeted times.");
			l = dao.rankedByRetweeted();
		}
		
		else if(mode.equals("recent")){
			System.err.println("Ranking by posted time.");
			l = dao.rankedByPostedTime();
		}
		
		else{
			System.err.println("Ranking by favourited times.");
			l = dao.rankByFavourited();
		}
		
		List<HashMap<String,Object>> tweets = dao.getTweetsForTweetWall(l);
		modelandview.addObject("tweets", tweets);
		return modelandview;
	}
	
	@RequestMapping("/terrier/refresh/{query}")
	public ModelAndView refresh(){
		ModelAndView modelandview = new ModelAndView("search_tweet_wall");
		List<Tweet> list = dao.getResultsList();
		List<HashMap<String,Object>> tweets = dao.getTweetsForTweetWall(list);

		System.err.println("Resetting!");
		modelandview.addObject("tweets", tweets);
		return modelandview;
	}
	
	@RequestMapping("/terrier/maps/{query}")
	@ResponseBody
	public Map<String, ArrayList<String>> maps(@PathVariable("query") String query) throws FileNotFoundException, UnsupportedEncodingException{
		return dao.getDataForMaps();
	}
	
	private String reformatQuery(String query){
		if (query.startsWith("#"))
			query = query.substring(1);
		return query;
	}
	
}
