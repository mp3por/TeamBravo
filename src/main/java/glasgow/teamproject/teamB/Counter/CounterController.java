package glasgow.teamproject.teamB.Counter;

import glasgow.teamproject.teamB.Counter.Counter.DateCountPair;
import glasgow.teamproject.teamB.Counter.Counter.EntityCountPair;

import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CounterController {
	
	@RequestMapping("/stat")
	public ModelAndView getStat() {
		ModelAndView model = new ModelAndView("statistics");
		
		return model;
	}
	
	
	@RequestMapping("/test")
	public ModelAndView getCount(){
		
		Counter c = new Counter();
		Calendar today = Calendar.getInstance();
		c.dailyMapReduce(today.getTime());
		today.add(Calendar.DATE,  -1);
		c.dailyMapReduce(today.getTime());
		today.add(Calendar.DATE,  -4);
		c.dailyMapReduce(today.getTime());
		today.add(Calendar.DATE,  -2);
		c.dailyMapReduce(today.getTime());

		c.mergingMapReduce(Counter.TimePeriod.PASTWEEK);
		
		StringBuilder sb = new StringBuilder();
		sb.append("Top Entities<br>");
		List<EntityCountPair> l = c.getTopEntities(Counter.Field.PERSON, Counter.TimePeriod.PASTWEEK, 20);
		for( EntityCountPair e : l ) {
			sb.append(e.getID() + " : " +  e.getCount().intValue() + "<br>");
		}
		sb.append("<br><br>");
		
		ModelAndView model = new ModelAndView("counter");
		model.addObject("output1", sb.toString());
		
		StringBuilder sb2 = new StringBuilder();
		sb2.append("Entity Search \"rain\"<br>");
		List<DateCountPair> l2 = c.getEntitiyTrend("rain", 7);
		for( DateCountPair e : l2 ) {
			sb2.append(e.getDate() + " : " + e.getCount() + "<br>");
		}
		model.addObject("output2", sb2.toString());
		
		return model;
	}

}
