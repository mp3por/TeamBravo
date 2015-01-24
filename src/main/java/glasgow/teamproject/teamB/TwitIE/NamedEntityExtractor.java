package glasgow.teamproject.teamB.TwitIE;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;


public abstract class NamedEntityExtractor {
	
	private ArrayList<Tweet> tweets = new ArrayList<>();
	private HashSet<String> interestedNE = new HashSet<String>(); 
	private ArrayList<String> types = new ArrayList<>();
	
	public HashSet<String> defaultNE = new HashSet<String>(Arrays.asList("Location", "Organization", "Person", "Hashtag", "URL", "UserID", "Emoticon"));
		
	public abstract void addTweet (Tweet tweet);
	public abstract void addTweets (ArrayList<Tweet> tweets);
	
	public abstract void processTweets ();
	
	public void removeTweets () {
		tweets.clear();
	}
	
	public void addNE (String s) {
		interestedNE.add(s);
	}

	public abstract void init ();

	public abstract ArrayList<String> getAllTypes();

	public abstract void saveTweets ();

	
	
	
}
