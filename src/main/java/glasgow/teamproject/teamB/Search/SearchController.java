package glasgow.teamproject.teamB.Search;

import glasgow.teamproject.teamB.mongodb.dao.TweetDAO;

import java.util.ArrayList;
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
	
	@RequestMapping("/terrier")
	public ModelAndView Search(){
		ModelAndView modelandview = new ModelAndView("Terrier");	
		return modelandview;
	}
	
	@RequestMapping("/terrier/{query}")
	public ModelAndView Search(@PathVariable("query") String query){
		
		TweetsIndexer indexer = new TweetsIndexer();
		indexer.indexTweets();
		
		TweetsRetriver retriver = new TweetsRetriver(indexer.getIndex(), query);
		retriver.runQuery();
		List<HashMap<String,Object>> tweets = tweetSaver.getTerrierResults(retriver.getResult().getDocids());
		
		ModelAndView modelandview = new ModelAndView("TerrierResult");
		modelandview.addObject("tweets", tweets);	
		return modelandview;
	}
}
