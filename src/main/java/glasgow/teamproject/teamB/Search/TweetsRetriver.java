package glasgow.teamproject.teamB.Search;

import org.terrier.applications.secondary.CollectionEnrichment;
import org.terrier.matching.ResultSet;
import org.terrier.querying.Manager;
import org.terrier.querying.SearchRequest;
import org.terrier.realtime.memory.MemoryIndex;

/**
 * A TweetsRetriver object takes an index object produced by a TweetsIndexer.
 * Then query the collection based on the given query as a String.
 * @author vincentfung13
 *
 */

public class TweetsRetriver {
	private String query;
	private Manager queryManager;
	private ResultSet result;
	
	public TweetsRetriver(MemoryIndex index, String query){
		this.query = query;
		this.queryManager = new Manager(index);
		this.result = null;
	}
	
	public ResultSet getResult(){
		return this.result;
	}
	
	public void runQuery(){
		System.err.println("Running search for " + this.query);
        StringBuffer sb = new StringBuffer();
        sb.append(CollectionEnrichment.normaliseString(this.query));
        
        SearchRequest srq = this.queryManager.newSearchRequest("query", sb.toString());
        /* What matching model should I set? */
        /* Study searchRequest class and matching model. */
        srq.addMatchingModel("Matching","DirichletLM");
        srq.setOriginalQuery(sb.toString());
        srq.setControl("decorate", "on");
        this.queryManager.runPreProcessing(srq);
        this.queryManager.runMatching(srq);
        this.queryManager.runPostProcessing(srq);
        this.queryManager.runPostFilters(srq);
        this.result = srq.getResultSet();
	}

}
