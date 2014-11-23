package glasgow.teamproject.teamB.Graphs;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class GraphsController {
	
	
	@RequestMapping("/getAll")
	public ModelAndView getGraphs(){
		
		ModelAndView model = new ModelAndView("graphs-all.jsp");
		
		return model;
	}
	
}