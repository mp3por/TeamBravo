package glasgow.teamproject.teamB.Search;

import org.springframework.stereotype.Component;
import org.terrier.utility.ApplicationSetup;

@Component
public class TerrierInitializer {
	
	private SearchMemoryIndex memoryIndex;
	
	public TerrierInitializer() {
		ApplicationSetup.setProperty("stopwords.filename", System.getProperty("user.dir") + "/stopword-list.txt");
		this.memoryIndex = new SearchMemoryIndex();
	}
	
	public SearchMemoryIndex getMemoryIndex() {
		return memoryIndex;
	}
}
