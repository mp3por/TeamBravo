package glasgow.teamproject.teamB.Examples;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StudentAdmissionController {

	/**
	 * This is how you specify what kind of request method should the method
	 * handle.
	 * 
	 * As you all know GET is for taking info from the server and POST is for
	 * giving info to the server. That is why getAdmissionFrom needs to handle
	 * only the GET method and it will return the HTML with the form inside.
	 * */
	@RequestMapping(value = "/admissionForm.html", method = RequestMethod.GET)
	public ModelAndView getAdmissionForm() {

		ModelAndView model = new ModelAndView("AdmissionForm"); // load view with name AdmissionForm

		return model;
	}

	/**
	 * This is method will be triggered on submit of the form because of the
	 * "action" tag in the form definition. When you submit a form you try to
	 * push information to the server and that is done through POST parameters.
	 * That is why this method has to fire only to POST requests. It then
	 * collects all of the POST data in the reqPar dictionary for later use.
	 * 
	 * @RequestParam is used because now the parameters are not in the PATH (the
	 *               URL) but in the request body.
	 * */
	@RequestMapping(value = "/submitAdmissionForm.html", method = RequestMethod.POST)
	public ModelAndView submitAdmissionForm(@RequestParam Map<String, String> reqPar) {

		// the names of the parameters are defined by the "name" value in the <input> of the form in the HTML
		String name = reqPar.get("studentName"); // <input type="text" name="studentName" />
		String hobby = reqPar.get("hobby"); // <input type="text" name="studentHobby" />

		ModelAndView model = new ModelAndView("AdmissionSuccess");
		model.addObject("msg", "Name: " + name + ", Hobby: " + hobby);

		return model;
	}
	
	/**
	 * This the method using normal parameter notation. There is even a way of specifying default par values.
	 * */

	//	@RequestMapping("/submitAdmissionForm.html")
	//	public ModelAndView submitAdmissionForm(@RequestParam(value="studentName", defaultValue="WHOAREYOU") String name, @RequestParam("studentHobby") String hobby) {
	//
	//		ModelAndView model = new ModelAndView("AdmissionSuccess");
	//		model.addObject("msg","Details submitted by you:: Name: "+name+ ", Hobby: " + hobby);
	//
	//		return model;
	//	}
}