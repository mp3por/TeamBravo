package glasgow.teamproject.teamB.TwitterStreaming;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller 
//@RequestMapping("/tweet")
public class TweetController {
	
	
	@RequestMapping("/all")
	public ModelAndView getTweets(){
		
		ModelAndView mv = new ModelAndView("allTweets");
		
		//TODO:get tweets from db
		
		return mv;
	}
}
