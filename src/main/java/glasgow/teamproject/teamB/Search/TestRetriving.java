package glasgow.teamproject.teamB.Search;


import org.terrier.applications.secondary.CollectionEnrichment;
import org.terrier.indexing.Document;
import org.terrier.indexing.TwitterJSONCollection;
import org.terrier.matching.ResultSet;
import org.terrier.querying.Manager;
import org.terrier.querying.SearchRequest;
import org.terrier.realtime.memory.MemoryIndex;
import org.terrier.structures.MetaIndex;
import org.terrier.utility.ApplicationSetup;


public class TestRetriving
{
	public static void main(String[] args){
		String query = "glasgow";
		// To be configured
		ApplicationSetup.setProperty("terrier.home", "/Users/vincentfung13/Development/TP3/terrier-4.0-win");
		ApplicationSetup.setProperty("terrier.etc", "/Users/vincentfung13/Development/TP3/terrier-4.0-win/etc");
		ApplicationSetup.setProperty("stopwords.filename", "/Users/vincentfung13/Development/TP3/terrier-4.0-win/share/stopword-list.txt");
		ApplicationSetup.setProperty("indexer.meta.forward.keys", "docno,text");
		ApplicationSetup.setProperty("indexer.meta.forward.keylens", "20,200");
		String pathToCollectionSpec = "/Users/vincentfung13/Development/TP3/terrier-4.0-win/etc/collection.spec";

		try{
			MemoryIndex index = indexTweets(pathToCollectionSpec);
			MetaIndex mt = index.getMetaIndex();
			
			SearchRequest srq = search(query, index);
			// Print out the result here
			ResultSet rs = srq.getResultSet();
			System.err.println(rs.getDocids().length);
			
			int[] resultDocids = srq.getResultSet().getDocids();
			
			for (int id: resultDocids)
				System.out.println(mt.getItem("docno", resultDocids[id]));
			
			String[] fields = mt.getKeys();
			
			for (String s: fields)
				System.out.println("I am fields:" + s);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	private static MemoryIndex indexTweets(String pathToCollectionSpec) throws Exception{
		MemoryIndex index = new MemoryIndex();
		TwitterJSONCollection tweets = new TwitterJSONCollection(pathToCollectionSpec);
		while(tweets.nextDocument()){
			Document tweet = tweets.getDocument();
			index.indexDocument(tweet);
//			System.out.println("ying");
		}
		tweets.close();
		return index;
	}
	
	private static SearchRequest search(String query, MemoryIndex memIndex) {
		System.err.println("Running search for" + query);
		StringBuffer sb = new StringBuffer();
		sb.append(CollectionEnrichment.normaliseString(query));
		Manager queryingManager = new Manager(memIndex);

		SearchRequest srq = queryingManager.newSearchRequest("query", sb.toString());
		/* What matching model should I set? */
		/* Study searchRequest class and matching model. */
		srq.addMatchingModel("Matching","DirichletLM");
		srq.setOriginalQuery(sb.toString());
		srq.setControl("decorate", "on");
		queryingManager.runPreProcessing(srq);
		queryingManager.runMatching(srq);
		queryingManager.runPostProcessing(srq);
		queryingManager.runPostFilters(srq);
		System.err.println("Returned "+srq.getResultSet().getDocids().length+" tweets");
		return srq;
	}

}