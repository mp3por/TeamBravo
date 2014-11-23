package glasgow.teamproject.teamB.Maps;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class MapsController {
	/**
	 * just a simple googleMaps controller method that sets the display to Glasgow
	 * */
	@RequestMapping("/googleMaps/")
	public ModelAndView googleMapsGeneral() {
		ModelAndView modelandview = new ModelAndView("Google"); // "HelloPage" is the name of the view
		//modelandview.addObject("longitude", pathVar.get("long"));
		//modelandview.addObject("latitude",pathVar.get("lat"));
		modelandview.addObject("longitude", "-4.287393");
		modelandview.addObject("latitude", "55.873714");
		modelandview.addObject("zoom", 10);
		
		return modelandview;
	}
	
	/**
	 * A controller for Google Maps with optional latitude and longitude
	 * */
	@RequestMapping("/googleMaps/{lat}/{long}/")
	public ModelAndView googleMapsPoint(@PathVariable("lat") String lat, @PathVariable("long") String lon) {
		ModelAndView modelandview = new ModelAndView("Google"); // "HelloPage" is the name of the view
		//modelandview.addObject("longitude", pathVar.get("long"));
		//modelandview.addObject("latitude",pathVar.get("lat"));
		modelandview.addObject("longitude", lon);
		modelandview.addObject("latitude", lat);
		modelandview.addObject("zoom", 15);
		return modelandview;
	}
}
