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
	
	@RequestMapping("/test")
	public ModelAndView getCount(){
		
		Calendar today = Calendar.getInstance();
		today.add(Calendar.DATE,  -2);
		Calendar end = Calendar.getInstance();
		end.add(Calendar.DATE, -1);
		Counter c = new Counter();
		c.dailyMapReduce(today.getTime());
//		c.dailyMapReduce(end.getTime());
//		end.add(Calendar.DATE, -1);
//		c.dailyMapReduce(end.getTime());
		c.mergingMapReduce(Counter.TimePeriod.PASTWEEK);
		
		StringBuilder sb = new StringBuilder();
		sb.append("Top Entities<br>");
		List<EntityCountPair> l = c.getTopEntities(Counter.Field.ALL, Counter.TimePeriod.PASTWEEK, 20);
		for( EntityCountPair e : l ) {
			sb.append(e.getID() + " : " +  e.getCount().intValue() + "<br>");
		}
		sb.append("<br><br>");
		
		ModelAndView model = new ModelAndView("counter");
		model.addObject("output1", sb.toString());
		
		StringBuilder sb2 = new StringBuilder();
		sb2.append("Entity Search \"breakfast\"<br>");
		List<DateCountPair> l2 = c.getEntitiyTrend("breakfast", 7);
		for( DateCountPair e : l2 ) {
			sb2.append(e.getDate() + " : " + e.getCount() + "<br>");
		}
		model.addObject("output2", sb2.toString());
		
		return model;
	}

}
