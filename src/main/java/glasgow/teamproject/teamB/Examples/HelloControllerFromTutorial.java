package glasgow.teamproject.teamB.Examples;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

//public class HelloControllerFromTutorial extends AbstractController {
//
//	@Override
//	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//		System.out.println("PARTY");
//		ModelAndView modelandview = new ModelAndView("HelloPage");
//		modelandview.addObject("welcomeMessage", "Hi User, WELCOME!");
//
//		return modelandview;
//	}
//
//}

/**
 * @ Controller this is the tag that <context:component-scan> in
 * test-context.xml is looking for. It tell the Spring framework that this class
 * is a controller.
 * 
 * @ RequestMapping We can put RequestMapping annotations not only on methods
 * but also on the hole class. This way we can specify exactly when will this
 * controller be searched for matching patterns. This is useful when there are
 * lots of controllers with lots of methods which correspond to different URL.
 * Since the DispatchServlet goes word by word in the URL and tries to match it
 * in this case URL = localhost:8080/teamB/greet/welcome - will match URL =
 * localhost:8080/teamB/party/welcome - will match not only not match but will
 * also save the server the time to go through the methods in this class.
 */
@Controller
//@RequestMapping("/greet")
public class HelloControllerFromTutorial {

	/**
	 * This will map the http://localhost:8080/teamB/welcome.html URL to this
	 * method
	 * */
	@RequestMapping("/welcome")
	public ModelAndView helloWorld() {
		/**
		 * This will prepare the model-and-view object that has the data which
		 * will be displayed and the VIEW NAME to be used to prepare the
		 * response (which will be the final HTML)
		 * */
		ModelAndView modelandview = new ModelAndView("HelloPage"); // "HelloPage" is the name of the view

		/**
		 * Here we are adding information to the object. This is done on the
		 * same basis using a dict like syntax. The information that we add will
		 * be accessible later in the view under the key we have specified.
		 * 
		 * See HelloPage.jsp to see how this is done
		 * */
		modelandview.addObject("welcomeMessage", "Hi and WELCOME!");
		return modelandview;
	}

	/**
	 * This will map the http://localhost:8080/teamB/hi URL to this method. It's
	 * just an example
	 * */
	@RequestMapping("/hi")
	public ModelAndView hiWorld() {

		ModelAndView modelandview = new ModelAndView("HelloPage");
		modelandview.addObject("welcomeMessage", "Hi User, WELCOME!");

		return modelandview;
	}

	/**
	 * This URL has a PARAMETER -> This is a GET parameter meaning it is in the
	 * URL. This means that the user can substitute the "username" value in the
	 * request with any value he wants URL -
	 * localhost:8080/test/greet/welcome/countryName/OMG - not only match but
	 * you also have username=OMG, because of the @PathVariable annotation
	 * */
	@RequestMapping("/welcome/countryName/{userName}")
	public ModelAndView welcomeUser( @PathVariable("userName") String name ) {

		ModelAndView modelandview = new ModelAndView("HelloPage"); // "HelloPage" is the name of the view
		modelandview.addObject("welcomeMessage", "Hi and WELCOME");
		modelandview.addObject("user",name);

		return modelandview;
	}
	
	/**
	 * TODO: DOESN'T WORK WILL FIX
	 * Or you can use a map to handle multiple GET parameters easy.
	 * But you must place <mvc:annotation-driven /> int test-context.xml
	 * */
	@RequestMapping("/party/{countryName}/{userName}")
	public ModelAndView welcomeUser2(@PathVariable Map<String, String> pathVar) {
		System.out.println(pathVar.get("userName"));

		ModelAndView modelandview = new ModelAndView("HelloPage"); // "HelloPage" is the name of the view
		modelandview.addObject("welcomeMessage", "Hi and WELCOME");
		modelandview.addObject("user",pathVar.get("userName"));

		return modelandview;
	}
	
	
	
}