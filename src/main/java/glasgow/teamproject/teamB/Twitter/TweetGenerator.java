package glasgow.teamproject.teamB.Twitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import twitter4j.GeoLocation;
import twitter4j.JSONException;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class TweetGenerator {
	/**
	 * Usage: java twitter4j.examples.geo.SearchPlaces [ip address] or [latitude] [longitude]
	 *
	 * @param args message
	 * @throws TwitterException 
	 * @throws InterruptedException 
	 * @throws JSONException 
	 * @throws IOException 
	 */
	static Integer JSONID = 0;
	public static void main(String[] args) throws TwitterException, InterruptedException, JSONException, IOException {

		/*
		 * Query settings
		 */
		
		double lon = -4.2324586;
		double lat = 55.8554602;
		double res = 10.0;
		GeoLocation geo = new GeoLocation(lat, lon);
		Query.Unit resUnit = Query.MILES;

				
		long count = 0;
		
		Calendar calendar = Calendar.getInstance();
		Date d = calendar.getTime();
		Date minDate = d;
		
		// queries 7 last days
		int daysToQuery = -7; 
		calendar.add(Calendar.DAY_OF_YEAR, daysToQuery);
		Date sevenDaysAgo = calendar.getTime();

		/*
		 * Initialize twitter APIs
		 *
		 */
		ArrayList<TwitterCreator> twitters = new ArrayList<>();
		twitters.add(new TwitterCreator("*************************", "**************************************************", "**************************************************", "*********************************************"));
		twitters.add(new TwitterCreator("*************************", "**************************************************", "**************************************************", "*********************************************"));
		twitters.add(new TwitterCreator("*************************", "**************************************************", "**************************************************", "*********************************************"));
		twitters.add(new TwitterCreator("*************************", "**************************************************", "**************************************************", "*********************************************"));
		twitters.add(new TwitterCreator("*************************", "**************************************************", "**************************************************", "*********************************************"));
		twitters.add(new TwitterCreator("*************************", "**************************************************", "**************************************************", "*********************************************"));
		//etc...

		System.out.println();
		Query query = new Query();
		query.setGeoCode(geo, res, resUnit);
		long lastID = Long.MAX_VALUE;
		ArrayList<Status> tweets = new ArrayList<Status>();
		query.setCount(100);
		while (minDate.compareTo(sevenDaysAgo) >= 0) {
			try {
				long longestTime = Long.MIN_VALUE;
				for (int i = 0; i < twitters.size(); i++) {
					System.out.println("Running " + i + "th Twitter app acc");
					TwitterCreator twitterCreator = twitters.get(i);
					Twitter twitter = twitterCreator.getTwitter();

					while (twitterCreator.getRemainingQueries() > 5) {

						QueryResult result = twitter.search(query);
						tweets.addAll(result.getTweets());
						System.out.println("Gathered " + tweets.size() + " tweets");

						for (Status t: tweets) {
							if(t.getId() < lastID) lastID = t.getId();
							if (t.getCreatedAt().compareTo(minDate) < 0) minDate = t.getCreatedAt();
						}
						query.setMaxId(lastID-1);
					}
					longestTime = twitterCreator.getResetTimer() > longestTime ? twitterCreator.getResetTimer() : longestTime; 

				}

				/*
				 * 25k is arbitrary limit when to write into file. Given 6 APIs, you'd get (175 queries * 6 APIs * 100 tweets =) 105000 tweets 
				 * If you have a large number of APIs, lets say, 30, there's a risk of not having enough memory.  
				 */
				if (tweets.size() > 25000) {
					count += tweets.size();
					TweetWriter.flushTweets (tweets, JSONID++);
					tweets.clear();
				}

				/*
				 * Notes:
				 * Technically, it's not required to synchronise sleeping time as first API should release the limits earlier than the last (given 6 APIs, it takes about 10 minutes).
				 * However, making synchronous querying is a good idea if lots of data is to be collected.      
				 * The current code works and this is a good bit about it - it will work with any number of APIs. 
				 * If you have a large number of APIs, I would suggest to check if any of APIs released the limit and repeat.  
				 */
				// seven seconds just in case there's a delay in release
				long timeToSleep = longestTime+7;

				Calendar wakeTime = Calendar.getInstance(); 
				wakeTime.add(Calendar.SECOND, (int)timeToSleep);


				System.out.println("Sleeping for " + timeToSleep);
				System.out.println("Waking up at " + wakeTime);

				Thread.sleep(timeToSleep*1000);
				System.out.println("Looking at such date now:" + minDate.toString());
			}

			catch (TwitterException te) {
				System.out.println("Couldn't connect: " + te);
			}; 

		}

		TweetWriter.flushTweets(tweets, JSONID++);
		count += tweets.size();
		System.out.println("\n\n\n\nFinished!");
		System.out.println("Got tweets: " + count);
		System.out.println("Start time: \t" + d.toString() + "\n" + 
				"End time:   \t"+ Calendar.getInstance().getTime().toString());

	}


}

