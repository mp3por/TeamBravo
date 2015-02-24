package glasgow.teamproject.teamB.Search;

import glasgow.teamproject.teamB.Search.dao.SearchDAOImpl;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAO;


import java.util.Date;
import java.util.HashMap;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SearchController {
	
	@Autowired
	private TweetDAO tweetSaver;
	
	@Autowired
	private TweetsIndexer indexer;
	
	@Autowired
	private SearchDAOImpl dao;
	
	@RequestMapping("/searchBox")
	public ModelAndView searchBox(){
		String now = (new Date()).toString();
		String viewName = "search_box";
		ModelAndView mv = new ModelAndView(viewName);
		mv.addObject("serverTime", now);
		return mv;
	}
	
	@RequestMapping("/terrier/{query}")
	public ModelAndView search(@PathVariable("query") String query){		
		
		dao.runQuery("normal", query);
	
//    	Set<String> resultSet = dao.getTweetsForQuery(query);
    	// TODO : vili find a way to do this AspectOriented
    	// String resultMaps = maps.getResultsForSetOfTweets(tweetsSet);
    	// String resultTweetWall = tweetWall.getResultsForSetOfTweets(tweetsSet);
    	// String resultGraphs = graphs.getResultsForSetOfTweets(tweetSet);
    	
//    	System.err.println(dao.getResultsList().get(2).toString());
    	
//    	List<HashMap<String,Object>> tweets = dao.getTweetsForTweetWall();
    	
		ModelAndView modelandview = new ModelAndView("TerrierResult");
//		modelandview.addObject("tweets", tweets);
//		
//		modelandview.addObject("count", tweets.size());
		modelandview.addObject("numberOfTweets", dao.getResultsList().size());
		modelandview.addObject("query", query);		
		return modelandview;
	}
	
	@RequestMapping("/terrier/tweetwall/{query}")
	public ModelAndView tweetWallSearch(@PathVariable("query") String query){
		
		dao.runQuery("normal", query);
		List<HashMap<String, Object>> tweets = dao.getTweetsForTweetWall();
		ModelAndView mv = new ModelAndView("search_tweetwall_test");
		
		mv.addObject("tweets", tweets);	
		return mv;	
	}	
	
}
