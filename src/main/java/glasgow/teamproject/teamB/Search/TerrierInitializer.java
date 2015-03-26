package glasgow.teamproject.teamB.Search;

import org.springframework.stereotype.Component;
import org.terrier.realtime.memory.MemoryIndex;
import org.terrier.utility.ApplicationSetup;

/*
 * This class configures Terrier at the start of the application.
 */

@Component
public class TerrierInitializer {

	private MemoryIndex memoryIndex;

	public TerrierInitializer() {
		/*
		 * Configure Terrier so that it stores the users' screen name and text of the tweets in the meta index
		 * This information is then used to query the database.
		 * Note that I encountered problem when parsing tweet id, which should be more reasonable to be used for query, 
		 * so I used texts to query the database instead.
		 */
		ApplicationSetup.setProperty("indexer.meta.forward.keys", "user.screen_name,text");
		ApplicationSetup.setProperty("indexer.meta.forward.keylens", "20,200");
		
		
		String currentDir = getClass().getProtectionDomain().getCodeSource().getLocation().toString();
		currentDir = currentDir.replace("file:", "").split("\\.")[0] + "TeamBravo/stopword-list.txt";
		System.out.println("Search:" + currentDir);
		ApplicationSetup.setProperty("stopwords.filename", currentDir);	
		this.memoryIndex = new MemoryIndex();
	}

	public MemoryIndex getMemoryIndex() {
		return memoryIndex;
	}
}
