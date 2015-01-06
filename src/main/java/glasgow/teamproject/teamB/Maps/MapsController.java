package glasgow.teamproject.teamB.Maps;

import glasgow.teamproject.teamB.mongodb.dao.TweetDAO;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAOImpl;

import java.util.ArrayList;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class MapsController {
	/**
	 * just a simple googleMaps controller method that sets the display to Glasgow
	 * */
	@RequestMapping("/googleMaps/")
	public ModelAndView General() {
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
	public ModelAndView Point(@PathVariable("lat") String lat, @PathVariable("long") String lon) {
		ModelAndView modelandview = new ModelAndView("Google"); // "HelloPage" is the name of the view
		//modelandview.addObject("longitude", pathVar.get("long"));
		//modelandview.addObject("latitude",pathVar.get("lat"));
		modelandview.addObject("longitude", lon);
		modelandview.addObject("latitude", lat);
		modelandview.addObject("zoom", 15);
		return modelandview;
	}
	
	@RequestMapping("/googleMaps/loadTweets")
	public ModelAndView loadTweets(){
		ModelAndView mv = new ModelAndView("Tweets");
		
		ArrayList<Double> latitudes = new ArrayList<>();
		ArrayList<Double> longtitudes = new ArrayList<>();
		ArrayList<String> tweets = new ArrayList<>();
		Random r = new Random();
		double LowLat = 55.814552;
		double HighLat = 55.919543;
		double LowLong= -4.488351;
		double HighLong = -4.129512;
		
		double LatDiff = HighLat -  LowLat ;
		double LongDiff = HighLong - LowLong;
		
		for(int i = 0 ; i < 1000 ; i++ ){
			double randLat = r.nextDouble();
			double randLong = r.nextDouble();	
			if(randLat <= LatDiff && randLong <= LongDiff ){
				latitudes.add(LowLat + randLat);
				longtitudes.add(LowLong + randLong);
				tweets.add("OMGOMGOMGOMG");
			}
			
		}
		mv.addObject("latitudes", latitudes);
		mv.addObject("longtitudes", longtitudes);
		mv.addObject("tweets",tweets);
		mv.addObject("numOfTweets", latitudes.size());
		
		return mv;
	}
	@RequestMapping("/googleMaps/loadTweets/{collection}")
	public ModelAndView loadTweetsFromDB(@PathVariable("collection") String collection){
		ModelAndView mv = new ModelAndView("Tweets");
		TweetDAO tweetDAO = new TweetDAOImpl(mongoOps);
		return mv;
	}
}
