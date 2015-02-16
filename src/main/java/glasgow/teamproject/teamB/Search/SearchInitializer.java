package glasgow.teamproject.teamB.Search;

import java.util.ArrayList;
import org.terrier.utility.ApplicationSetup;
/**
 * This class provides a main method to configure Terrier and initialize the
 * search.
 * 
 * @author vincentfung13
 *
 */

public class SearchInitializer {

	public static void main(String[] args) {
		String query = "Labour";
		// To be configured
		ApplicationSetup.setProperty("stopwords.filename", System.getProperty("user.dir") + "/stopword-list.txt");
		
		
			
			TweetsIndexer indexer = new TweetsIndexer();			
			
			indexer.indexTweets();
			
			TweetsRetriver retriver = new TweetsRetriver(indexer.getIndex(), query);
			retriver.runQuery();			
//			ArrayList<String> results = retriver.getResultsList();
//			for (int i = 0; i < results.size(); i++){
//				System.out.println(results.get(i));
//			}			
		
	}
}
