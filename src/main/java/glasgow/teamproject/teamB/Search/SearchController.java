package glasgow.teamproject.teamB.Search;

import glasgow.teamproject.teamB.mongodb.dao.TweetDAO;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.terrier.indexing.TwitterJSONDocument;
import org.terrier.realtime.memory.MemoryIndex;


@Controller
//@RequestMapping("/search")
public class SearchController {
	
	@Autowired
	private TweetDAO tweetSaver;
	
	@Autowired
	private TweetsIndexer indexer;
		
	@RequestMapping("/terrier")
	public ModelAndView Search(){
		ModelAndView modelandview = new ModelAndView("Terrier");	
		return modelandview;
	}
	
	@RequestMapping("/terrier/{query}")
	public ModelAndView Search(@PathVariable("query") String query){
		
//		indexerindexTweets();
		TweetsRetriver retriver = new TweetsRetriver(this.indexer.getIndex(), query);
		retriver.runQuery();
		List<HashMap<String,Object>> tweets = tweetSaver.getTerrierResults(retriver.getResultSet().getDocids());
		
		ModelAndView modelandview = new ModelAndView("TerrierResult");
		modelandview.addObject("tweets", tweets);	
		return modelandview;
	}
}
