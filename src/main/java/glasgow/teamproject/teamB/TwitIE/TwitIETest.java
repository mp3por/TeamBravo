package glasgow.teamproject.teamB.TwitIE;

/**
 * 
 */


import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

/**
 * @author ppp
 *
 */
public class TwitIETest {

	TwitIE t;

	@Before
	public void setup (){
		t = new TwitIE();
		t.init();
	}

	/*@Test 
	public void testEmptyTweet ()
	{
		t.addTweet(new Tweet("", 0));
		t.processTweets();
		
		Tweet tweet = t.getTweets().get(0); 
		
		assertTrue (tweet.getNEs().isEmpty());
		
	}
*/
	@Test
	public void testNEs (){
		/* Took from pre-refactored code - lets see if it works as it's supposed to
		 * OriginalTweet was removed as it is not relevant 
	{"527587083041669121": {
	    "UserID": [
	        "celticfc",
	        "superguidetti"
	    ],
	    "OriginalTweet": "RT @celticfc: A delighted @superguidetti with the matchball after claiming his first Celtic hat-trick tonight in Paradise. (MH) http://t.co\u2026",
	    "URL": ["http://t.co\u2026"]
		}}
	{"527587068633833472": {
    	"Hashtag": ["#burrel"],
    	"OriginalTweet": "Checking out Bonhams in London for the up and coming Burrell at Bonhams exhibition in December very exciting #burrel\u2026http://t.co/l69kEQvk2O",
    	"URL": ["http://t.co/l69kEQvk2O"],
    	"Location": ["London"]
	}}
		 */
		
		t.addTweet(new Tweet("RT @celticfc: A delighted @superguidetti with the matchball after claiming his first Celtic hat-trick tonight in Paradise. (MH) http://t.co\u2026", 0));
		t.addTweet(new Tweet("Checking out Bonhams in London for the up and coming Burrell at Bonhams exhibition in December very exciting #burrel\u2026http://t.co/l69kEQvk2O", 1));
		
		t.processTweets();
		
		ArrayList<String> actualTypes = t.getAllTypes();
		ArrayList<String> expectedTypes = new ArrayList<String>(Arrays.asList("Location", "Hashtag", "UserID"));
		//assertEquals (expectedTypes, actualTypes);
		System.out.println(actualTypes);
		System.out.println(expectedTypes);
		for (String s: expectedTypes) {
			assertTrue(actualTypes.contains(s));
		}

		String actualNES = t.getTweets().get(0).getNEs().toString();
		ArrayList<String> expectedNES = new ArrayList<String> (Arrays.asList("UserID", "URL"));
		
		for (String s: expectedNES) {
			assertTrue (actualNES.contains(s));
		}
		
		actualNES = t.getTweets().get(1).getNEs().toString();
		expectedNES = new ArrayList<String> (Arrays.asList("Hashtag", "Location", "URL"));
		//System.out.println(actualNES);
		for (String s: expectedNES) {
			assertTrue (actualNES.contains(s));
		}
		
	}

}
