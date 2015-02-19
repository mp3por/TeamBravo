package glasgow.teamproject.teamB.Examples;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PersonController {

	@RequestMapping(value = "/person", method = RequestMethod.GET)
	public ModelAndView person() {
		/**
		 * "person" - view name "command" - because Spring expects an object
		 * with the name command when you are using <form:form> tags in JSP new
		 * Person() - the model object
		 */
		return new ModelAndView("person", "command", new Person());
	}

	@RequestMapping(value = "/addPerson", method = RequestMethod.POST)
	public String addPerson(@ModelAttribute("SpringWeb") Person person, ModelMap model) {

		model.addAttribute("name", person.getName());
		model.addAttribute("age", person.getAge());
		model.addAttribute("id", person.getId());
		return "result"; // name of the view
	}
}
