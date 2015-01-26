package glasgow.teamproject.teamB.Main;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.RedirectViewControllerRegistration;
import org.springframework.web.servlet.view.RedirectView;


@Controller
//@RequestMapping("/main")
public class MainController {
	
	@RequestMapping("/")
	public ModelAndView index(){
		System.out.println("OMGMGOMGOMGOGMOMGOMG");
		ModelAndView mv = new ModelAndView("home");
		return mv;
	}
	
	@RequestMapping("/home")
	public ModelAndView getHome(){
		System.out.println("OMGMGOMGOMGOGMOMGOMG");
		ModelAndView mv = new ModelAndView("home");	
		return mv;
	}
	
	@RequestMapping("/example/*")
	public RedirectView example(Model model){
		RedirectView omg = new RedirectView("", true);
		String url = omg.getUrl();
		System.out.println("URL:"+url);
		return omg;
	}
}
