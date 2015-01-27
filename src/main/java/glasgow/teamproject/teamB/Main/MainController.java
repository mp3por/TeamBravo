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
	
	@RequestMapping("/hello")
	public String getHome(){
		System.out.println("OMGMGOMGOMGOGMOMGOMG");	
		return "home";
	}
}
