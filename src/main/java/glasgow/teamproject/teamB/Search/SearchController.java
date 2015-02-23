package glasgow.teamproject.teamB.Search;

import glasgow.teamproject.teamB.Search.dao.SearchDAOImpl;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAO;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	
	@RequestMapping("/terrier/{mode}/{query}")
	public ModelAndView search(@PathVariable Map<String, String> pathVar){

		String mode = pathVar.get("mode");
		String query = pathVar.get("query");
		
		
    	Set<String> resultSet = dao.getTweetsForQuery(query);

    	//TODO : vili find a way to do this AspectOriented
    	// String resultMaps = maps.getResultsForSetOfTweets(tweetsSet);
    	
    	System.err.println(dao.getResultsList().get(2).toString());
    	
    	List<HashMap<String,Object>> tweets = dao.getTweetsForTweetWall();
    	
		ModelAndView modelandview = new ModelAndView("TerrierResult");
		modelandview.addObject("tweets", tweets);
		modelandview.addObject("count", tweets.size());
		
		return modelandview;
	}
	
	
}
