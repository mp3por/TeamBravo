package glasgow.teamproject.teamB.Search;

//import org.terrier.utility.ApplicationSetup;

//@Component
public class TerrierInitializer {
	
	private SearchMemoryIndex memoryIndex;
	
	public TerrierInitializer() {
//		String currentDir = getClass().getProtectionDomain().getCodeSource().getLocation().toString();
//		currentDir = currentDir.replace("file:", "").split("\\.")[0] + "TeamBravo/stopword-list.txt";
//		System.out.println("Search:" + currentDir);
//		ApplicationSetup.setProperty("stopwords.filename", currentDir);		
		this.memoryIndex = new SearchMemoryIndex();
	}
	
	public SearchMemoryIndex getMemoryIndex() {
		return memoryIndex;
	}
}
