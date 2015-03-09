package glasgow.teamproject.teamB.Search;

import org.springframework.stereotype.Component;
import org.terrier.realtime.memory.MemoryIndex;
import org.terrier.utility.ApplicationSetup;

@Component
public class SearchMemoryIndex extends MemoryIndex {

	public SearchMemoryIndex(){
		super();
		String currentDir = getClass().getProtectionDomain().getCodeSource().getLocation().toString();
		currentDir = currentDir.replace("file:", "").split("\\.")[0] + "TeamBravo/stopword-list.txt";
		System.out.println("Search:" + currentDir);
		ApplicationSetup.setProperty("stopwords.filename", currentDir);	
	}
}
