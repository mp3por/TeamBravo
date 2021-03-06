package glasgow.teamproject.teamB.Maps;

import glasgow.teamproject.teamB.Util.ProjectProperties;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MapsController {

	@Autowired
	private TweetDAO tweetSaver;

	//	@Autowired
	//	private StreamReaderService serv;
	//	
	//	
	//	@PostConstruct
	//	public void testing(){
	//		System.out.println("OMGOMGOMGOMG");
	//		serv.addObserver(this);
	//		System.out.println("OMGOMGOMGOMG: " + serv.countObservers());
	//	}

//	public MapsController(TweetDAO ts) {
//		this.tweetSaver = ts;
//	}

	/**
	 * just a simple googleMaps controller method that sets the display to
	 * Glasgow
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
	 * Plain default map for front page
	 * */
	@RequestMapping("/defaultMap/")
	public ModelAndView getDefault() {
		ModelAndView modelandview = new ModelAndView("DefaultMap");
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
	public ModelAndView loadTweets() {
		ModelAndView mv = new ModelAndView("Tweets");

		ArrayList<Double> latitudes = new ArrayList<>();
		ArrayList<Double> longtitudes = new ArrayList<>();
		ArrayList<String> tweets = new ArrayList<>();
		Random r = new Random();
		double LowLat = 55.814552;
		double HighLat = 55.919543;
		double LowLong = -4.488351;
		double HighLong = -4.129512;

		double LatDiff = HighLat - LowLat;
		double LongDiff = HighLong - LowLong;

		for (int i = 0; i < 1000; i++) {
			double randLat = r.nextDouble();
			double randLong = r.nextDouble();
			if (randLat <= LatDiff && randLong <= LongDiff) {
				latitudes.add(LowLat + randLat);
				longtitudes.add(LowLong + randLong);
				tweets.add("OMGOMGOMGOMG");
			}

		}
		mv.addObject("latitudes", latitudes);
		mv.addObject("longtitudes", longtitudes);
		mv.addObject("tweets", tweets);
		mv.addObject("numOfTweets", latitudes.size());

		return mv;
	}

	@RequestMapping("/googleMaps/loadTweets/{collection}")
	public ModelAndView loadTweetsFromDB(@PathVariable("collection") String collection) {
		ModelAndView mv = new ModelAndView("Tweets");
		tweetSaver.getTweetsForMaps(collection);
		return mv;
	}

	@RequestMapping("/maps/getSettings")
	@ResponseBody
	public ModelAndView getSettings() {
		ModelAndView mv = new ModelAndView("settings-maps");

		return mv;
	}

	@RequestMapping("/ajaxAll")
	public ModelAndView ajaxMaps() {

		ModelAndView model = new ModelAndView("only-maps-all");

		return model;
	}

	@RequestMapping("/test")
	public ModelAndView test() {

		ModelAndView model = new ModelAndView("only-maps-all");

		ArrayList<Double> latitudes = new ArrayList<>();
		ArrayList<Double> longitudes = new ArrayList<>();
		ArrayList<String> tweets = new ArrayList<>();
		Random r = new Random();
		double LowLat = 55.814552;
		double HighLat = 55.919543;
		double LowLong = -4.488351;
		double HighLong = -4.129512;

		double LatDiff = HighLat - LowLat;
		double LongDiff = HighLong - LowLong;

		for (int i = 0; i < 1000; i++) {
			double randLat = r.nextDouble();
			double randLong = r.nextDouble();
			if (randLat <= LatDiff && randLong <= LongDiff) {
				latitudes.add(LowLat + randLat);
				longitudes.add(LowLong + randLong);
				tweets.add("\"OMGOMGOMGOMG\"");
			}

		}

		model.addObject("longitude", "-4.287393");
		model.addObject("latitude", "55.873714");
		model.addObject("latitudes", latitudes);
		model.addObject("longitudes", longitudes);
		model.addObject("text", tweets);

		return model;
	}

	@RequestMapping("/test2")
	@ResponseBody
	public Map<String, ArrayList<String>> test2() {

		ArrayList<String> latitudes = new ArrayList<>();
		ArrayList<String> longitudes = new ArrayList<>();
		ArrayList<String> tweets = new ArrayList<>();
		Random r = new Random();
		double LowLat = 55.814552;
		double HighLat = 55.919543;
		double LowLong = -4.488351;
		double HighLong = -4.129512;

		double LatDiff = HighLat - LowLat;
		double LongDiff = HighLong - LowLong;

		for (int i = 0; i < 1000; i++) {
			double randLat = r.nextDouble();
			double randLong = r.nextDouble();
			if (randLat <= LatDiff && randLong <= LongDiff) {
				latitudes.add(Double.toString(LowLat + randLat));
				longitudes.add(Double.toString(LowLong + randLong));
				tweets.add("\"OMGOMGOMGOMG\"");
			}

		}
		String needed = "<div id='added_map_container' class='map-container'><div id='added_map_div' class='map'></div></div>";
		ArrayList<String> need = new ArrayList<String>();
		need.add(needed);
		Map<String, ArrayList<String>> coordinates = new HashMap<String, ArrayList<String>>();
		coordinates.put("longitudes", longitudes);
		coordinates.put("latitudes", latitudes);
		coordinates.put("text", tweets);
		coordinates.put("needed", need);

		return coordinates;
	}

	@RequestMapping("/test3")
	@ResponseBody
	public Map<String, ArrayList<String>> test3() {

		List<String> tweetsForMaps = tweetSaver.getTweetsForMapsWithLimit(ProjectProperties.TWEET_COLLECTION, 100);
		Map<String, ArrayList<String>> data = null;
		HashSet<String> tweetsSet = new HashSet<String>(tweetsForMaps);
		data = getData(tweetsSet);
		return data;
	}

	private Map<String, ArrayList<String>> getData(Set<String> tweetsForMaps) {
		ArrayList<String> tweet_text = new ArrayList<>();
		ArrayList<String> latitudes = new ArrayList<>();
		ArrayList<String> longitudes = new ArrayList<>();
		ArrayList<String> tweet_time = new ArrayList<>();
		ArrayList<String> tweet_user = new ArrayList<>();
		Map<String, ArrayList<String>> data = new HashMap<String, ArrayList<String>>();

		String a = null;
		String time = null;
		JSONObject b = null;
		JSONArray c = null;

		String user = null;

		for (String tweet : tweetsForMaps) {
			JSONObject js = new JSONObject(tweet);
			a = (String) js.get("text");
			time = js.getString("created_at");
			b = (JSONObject) js.get("coordinates");
			c = (JSONArray) b.get("coordinates");
			b = js.getJSONObject("user");
			user = b.getString("name");
			tweet_text.add(a);
			longitudes.add(Double.toString(c.getDouble(0)));
			latitudes.add(Double.toString(c.getDouble(1)));
			tweet_time.add(time);
			tweet_user.add(user);
		}

		String needed = "<div id='added_map_container' class='map-container'><div id='added_map_div' class='map'></div></div>";
		ArrayList<String> need = new ArrayList<String>();
		need.add(needed);
		data.put("needed", need);
		data.put("longitudes", longitudes);
		data.put("latitudes", latitudes);
		data.put("text", tweet_text);
		data.put("time", tweet_time);
		data.put("user", tweet_user);

		return data;
	}

	//	@Override
	//	public void update(Observable o, Object arg) {
	//		// TODO Auto-generated method stub
	//		System.out.println("maps: " + arg);
	//	}
}
