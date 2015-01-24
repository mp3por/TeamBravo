package glasgow.teamproject.teamB.TwitIE;

import java.util.ArrayList;
import java.util.HashMap;

public interface NamedEntityExtractor {

	public void addNE(String s);
	public void init ();
	public HashMap<String, ArrayList<String>> processString(String tweetText) throws InterruptedException;

}
