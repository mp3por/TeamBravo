package glasgow.teamproject.teamB.Counter;

import glasgow.teamproject.teamB.Counter.Counter.EntityCountPair;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CounterController {
	
	@RequestMapping("/test")
	public ModelAndView getCount(){
		
		Date date = new Date();
		
		Counter c = new Counter();
		c.dailyMapReduce(date);
		
		StringBuilder sb = new StringBuilder();
		List<EntityCountPair> l = c.getTopEntities(Counter.Field.HASHTAG, Counter.TimePeriod.PASTDAY, 10);
		for( EntityCountPair e : l ) {
			sb.append(e.getID() + " : " +  e.getCount().intValue() + "<br>");
		}
		
		ModelAndView model = new ModelAndView("counter");
		model.addObject("output1", sb.toString());
		
		return model;
	}

}
