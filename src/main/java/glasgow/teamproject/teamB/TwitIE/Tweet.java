package glasgow.teamproject.teamB.TwitIE;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

//import org.json.JSONObject;


public class Tweet {
	
	private String message;
	private String author;
	private Date date;
	private ArrayList<String> hashtags;
	private boolean hasLocation;
	private String lon, lat;
	private long tweetID;
	
	private HashMap<String, ArrayList<String>> namedEntities = new HashMap<String, ArrayList<String>>();
	
	public Tweet (String msg, Integer id){
		this.message = msg;
		this.tweetID = id;
	}
	
	@SuppressWarnings("deprecation")
	/*public Tweet (JSONObject jsonObj) {
		message = jsonObj.getString("message");
		author = jsonObj.getString("author");
		lon = jsonObj.getString("lon");
		tweetID = jsonObj.getLong("id");
		
		 String LARGE_TWITTER_DATE_FORMAT = "EEE MMM dd HH:mm:ss Z yyyy";
		 String twiDate = jsonObj.getString("date");
		 try {
			date = new SimpleDateFormat(LARGE_TWITTER_DATE_FORMAT, Locale.ENGLISH).parse(twiDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		lat = jsonObj.getString("lat");
		if (lon.compareTo("N/A") == 0) 
			hasLocation = false;
		else hasLocation = true;
		
		//String hsstr = jsonObj.getString("hashtags");
		//JSONObject hashtagsJS = new JSONObject(hsstr);
		//hashtags = new ArrayList<>();
		//Integer i = hashtagsJS.length(); 
			//while (i-- > 0)
				//hashtags.add(hashtagsJS.getString(i.toString()));
		
		
		
	}*/
	
	public String getMessage() {
		return message;
	}
	public String getAuthor() {
		return author;
	}
	public Date getDate() {
		return date;
	}
	public ArrayList<String> getHashtags() {
		return hashtags;
	}
	public boolean isHasLocation() {
		return hasLocation;
	}
	public String getLon() {
		return lon;
	}
	public String getLat() {
		return lat;
	}
	public long getTweetID() {
		return tweetID;
	}
	
	public String toString() {
		return date.toString()+" @"+author+" "+message+"\n Location: "+(this.isHasLocation() ? lon+" "+lat : "N/A") +"\n Hashtags: "+(hashtags.size() == 0 ? "N/A" : hashtags.toString());
	}
	
	public void setNE(String key, String value) {
		if (!namedEntities.containsKey(key)) {
			namedEntities.put(key, new ArrayList<String>());
		}
		namedEntities.get(key).add(value);
	}
	

	
	public HashMap<String, ArrayList<String>> getNEs() {
		return namedEntities;
	}
	
}
