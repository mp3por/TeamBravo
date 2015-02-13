package glasgow.teamproject.teamB.Search;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
//@RequestMapping("/search")
public class SearchController {
	
	@RequestMapping("/terrier")
	public ModelAndView Search(){
		ModelAndView modelandview = new ModelAndView("Terrier");	
		return modelandview;
	}
	
	@RequestMapping("/terrier/{query}")
	public ModelAndView Search(@PathVariable("query") String query){	
		ModelAndView modelandview = new ModelAndView("TerrierResult");
		modelandview.addObject("query", query);	
		return modelandview;
	}
}
