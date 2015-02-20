package glasgow.teamproject.teamB.Main;

import glasgow.teamproject.teamB.mongodb.dao.TweetDAO;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAOImpl;

import java.net.UnknownHostException;
import java.util.Date;

import org.json.JSONArray;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mongodb.MongoClient;


@Controller
public class MainController {
	
	@RequestMapping("/hello")
	public ModelAndView Hello(){
		String now = (new Date()).toString();
		String viewName = "home";
		ModelAndView mv = new ModelAndView(viewName);
		mv.addObject("serverTime", now);
		return mv;
	}
	
	@RequestMapping("/home")
	public ModelAndView Main(){
		String now = (new Date()).toString();
		String viewName = "main2";
		ModelAndView mv = new ModelAndView(viewName);
		mv.addObject("serverTime", now);
		return mv;
	}
	
	@RequestMapping("/specificTopic")
	public ModelAndView specificTopic(){
		ModelAndView mv = new ModelAndView("specificTopic");
		return mv;
	}
}
