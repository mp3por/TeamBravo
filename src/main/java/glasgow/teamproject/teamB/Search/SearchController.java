package glasgow.teamproject.teamB.Search;

import glasgow.teamproject.teamB.Search.dao.SearchDAO;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
//@RequestMapping("/search")
public class SearchController {
	
	@Autowired
	private TweetDAO tweetSaver;
	
	@Autowired
	private TweetsIndexer indexer;
		
	@RequestMapping("/terrier")
	public ModelAndView search(){
		ModelAndView modelandview = new ModelAndView("Terrier");	
		return modelandview;
	}
	
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

		TweetsRetriver retriver = new TweetsRetriver(this.indexer.getIndex(), query);
		retriver.runQuery();
		List<HashMap<String,Object>> tweets = tweetSaver.getTerrierResults(tweetSaver.getResultList(retriver.getResultSet().getDocids()));    			

		ModelAndView modelandview = new ModelAndView("TerrierResult");
		modelandview.addObject("tweets", tweets);
		modelandview.addObject("count", tweets.size());
		
		return modelandview;
	}
	
	@RequestMapping("/terrier/{query}/rank")
	public ModelAndView rankedSearch(@PathVariable("query") String query){
		TweetsRetriver retriver = new TweetsRetriver(this.indexer.getIndex(), query);
		retriver.runQuery();
		List<HashMap<String,Object>> tweets = tweetSaver.getTerrierResults(tweetSaver.getRankedResultList(retriver.getResultSet().getDocids()));
		
		ModelAndView modelandview = new ModelAndView("TerrierResult");
		
		modelandview.addObject("tweets", tweets);
		modelandview.addObject("count", tweets.size());
		return modelandview;
	}
	
    @RequestMapping("/terrier/{query}/maps")
    public ModelAndView searchMap(@PathVariable("query") String query){
    	
    	TweetsRetriver retriver = new TweetsRetriver(this.indexer.getIndex(), query);
		retriver.runQuery();

    	ModelAndView modelandview = new ModelAndView("search_map");
    	ArrayList<Tweet> results = tweetSaver.getResultList(retriver.getResultSet().getDocids());
    	ArrayList<Double> lats = tweetSaver.latitudesForMaps(results);
    	ArrayList<Double> lons = tweetSaver.longtitudesForMaps(results);
    	
    	
    	modelandview.addObject("longitude", "-4.287393");
		modelandview.addObject("latitude", "55.873714");
    	modelandview.addObject("latitudes", lats);
		modelandview.addObject("longtitudes", lons);
		modelandview.addObject("numOfTweets", lats.size());
		
		
    	return modelandview;
    }
}
