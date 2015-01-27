package glasgow.teamproject.teamB.Main;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
//@RequestMapping("/main")
public class MainController {
	
	@RequestMapping("/hello")
	public ModelAndView getHome(){
		String now = (new Date()).toString();
		String viewName = "home";
		ModelAndView mv = new ModelAndView(viewName);
		mv.addObject("serverTime", now);
		return mv;
	}
}
