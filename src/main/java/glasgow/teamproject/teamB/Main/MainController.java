package glasgow.teamproject.teamB.Main;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class MainController {
	
	
//	@Autowired
//	private StreamReaderService serv;
//	
//	
//	@PostConstruct
//	private void test(){
//		System.out.println("OMGOMGOMGOMG");
//		serv.addObserver(this);
//		System.out.println("OMGOMGOMGOMG: " + serv.countObservers());
//	}
	
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
	
	@RequestMapping("/tile_template")
	public String getTeplate(){
		return "tile_template";
	}

//	@Override
//	public void update(Observable o, Object arg) {
//		// TODO Auto-generated method stub
//		System.out.println("main: " + arg);
//	}
}
