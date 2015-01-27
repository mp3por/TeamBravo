package glasgow.teamproject.teamB.Graphs;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import glasgow.teamproject.teamB.mongodb.dao.TweetDAO;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAOImpl;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mongodb.MongoClient;


@Controller
public class GraphsController {
	
	/*
	 * Database info
	 */
	private final String DB_NAME = "tweetsTest";
	private final String TWEETS_COLLECTION = "tweets"; //table
	private final String MONGO_HOST = "localhost";
	private final int MONGO_PORT = 27017;
	private TweetDAO tweetdao;
	private MongoOperations mongoOps;
	
	//Sets up client and mongo operations objects
	private void setUpDBInfo(){
		try{
			MongoClient mongo = new MongoClient(MONGO_HOST, MONGO_PORT);
			mongoOps = new MongoTemplate(mongo, DB_NAME);
			tweetdao = new TweetDAOImpl(mongoOps);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	private void setUpGraphObjects(){
		//call tweetDAOImpl methods to get back objects for each graph
		//Format the objects how you want them
		//Store them in an appropriate data structure and return them to be passed to the view
	}
	
	private List<String> getHotTopics(){
		ArrayList<String> hotTopics = new ArrayList<String>();
		//Query topicsQuery = new Query();
		List<String> allResults = mongoOps.find(new Query(), String.class, "Topics_Week");
		
		return hotTopics;
	}
	
	@RequestMapping("/getAll")
	public ModelAndView getGraphs(){
		setUpDBInfo();
		setUpGraphObjects();
		ModelAndView model = new ModelAndView("graphs-all");
		return model;
	}
	
	@RequestMapping("/lineGraph")
	public ModelAndView getLineGraph(){
		setUpDBInfo();
		
		setUpGraphObjects();
		ModelAndView model = new ModelAndView("LineGraph");
		//model.addObject to add stuff to the model to pass to the view
		
		return model;
	}
	
	@RequestMapping("/pieChart")
	public ModelAndView getPieChart(){
		setUpDBInfo();
		setUpGraphObjects();
		ModelAndView model = new ModelAndView("PieChart");
		//model.addObject to add stuff to the model to pass to the view
		
		return model;
	}
	
	@RequestMapping("/wordCloud")
	public ModelAndView getWordCloud(){
		setUpDBInfo();
		setUpGraphObjects();
		ModelAndView model = new ModelAndView("WordCloud");
		//model.addObject to add stuff to the model to pass to the view
		
		return model;
	}
	
	@RequestMapping("/barChart")
	public ModelAndView getBarChart(){
		setUpDBInfo();
		setUpGraphObjects();
		ModelAndView model = new ModelAndView("BarChart");
		//model.addObject to add stuff to the model to pass to the view
		
		return model;
	}
	
}