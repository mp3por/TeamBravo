package glasgow.teamproject.teamB.Twitter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import twitter4j.GeoLocation;
import twitter4j.HashtagEntity;
import twitter4j.JSONArray;
import twitter4j.JSONException;
import twitter4j.JSONObject;
import twitter4j.Status;


public class TweetWriter {
	public static void flushTweets(ArrayList<Status> tweets, Integer JSONID) throws IOException, JSONException {
		JSONArray jsarr = new JSONArray();
		Date date = null;
		Date firstDate = tweets.get(0).getCreatedAt();
		Date lastDate = tweets.get(tweets.size()-1).getCreatedAt();
		for (int i = 0; i < tweets.size(); i++) {
			Status t = (Status) tweets.get(i);

			GeoLocation loc = t.getGeoLocation();

			String user = t.getUser().getScreenName();
			String msg = t.getText();
			date = t.getCreatedAt();
			long id = t.getId();
			JSONObject hashtags = new JSONObject();
			int j = 0;
			for (HashtagEntity hashtag : t.getHashtagEntities()) {
				hashtags.put(String.valueOf(j++), hashtag.getText());
			}
			JSONObject jsin = new JSONObject();

			if (loc != null)
			{
				jsin.put("id", id);
				jsin.put("date", date.toString());
				jsin.put("author", user);
				jsin.put("message", msg);
				jsin.put("hashtags", hashtags);
				jsin.put("lat", String.valueOf(loc.getLatitude()));
				jsin.put("lon", String.valueOf(loc.getLongitude()));
			}
			else
			{
				jsin.put("id", id);
				jsin.put("date", date.toString());
				jsin.put("author", user);
				jsin.put("message", msg);
				jsin.put("hashtags", hashtags);
				jsin.put("lat", "N/A");
				jsin.put("lon", "N/A");
			}
			jsarr.put(i, jsin);


		}
		FileWriter fw = new FileWriter("out.json"+JSONID.toString());
		fw.write(jsarr.toString());
		fw.close();
		System.out.println("Written to out.json"+JSONID.toString()+" file.");
		System.out.println("Amount of tweets: " + tweets.size());
		System.out.println("Got tweets between " + firstDate.toString() + " and " + lastDate.toString());
		

	}
}
