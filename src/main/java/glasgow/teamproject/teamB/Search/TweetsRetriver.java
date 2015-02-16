package glasgow.teamproject.teamB.Search;

import glasgow.teamproject.teamB.mongodb.dao.TweetDAO;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAOImpl;

import java.net.UnknownHostException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.terrier.applications.secondary.CollectionEnrichment;
import org.terrier.matching.ResultSet;
import org.terrier.querying.Manager;
import org.terrier.querying.SearchRequest;
import org.terrier.realtime.memory.MemoryIndex;

import com.mongodb.MongoClient;

/**
 * A TweetsRetriver object takes an index object produced by a TweetsIndexer.
 * Then query the collection based on the given query as a String.
 * @author vincentfung13
 *
 */

public class TweetsRetriver {
	
	public static final String DB_NAME = "tweetsTest";
	public static final String TWEETS_COLLECTION = "tweets";
	public static final String MONGO_HOST = "localhost";
	public static final int MONGO_PORT = 27017;
	
	private String query;
	private Manager queryManager;
	private ResultSet result;
	private TweetDAO tweetSaver;
	
	public TweetsRetriver(MemoryIndex index, String query){
		this.query = query;
		this.queryManager = new Manager(index);
		this.result = null;
		
		MongoClient mc;
		try {
			mc = new MongoClient(MONGO_HOST, MONGO_PORT);
			MongoOperations mongoOps = new MongoTemplate(mc, DB_NAME);
			this.tweetSaver = new TweetDAOImpl(mongoOps);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet getResultSet(){
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
	
	public ArrayList<String> getResultsList(){
		
		ArrayList<String> results = new ArrayList<String>();
		int[] resultDocids = this.result.getDocids();
		
		for(int i = 0; i < resultDocids.length; i++){
			results.add(tweetSaver.getNthEntry("tweets", resultDocids[i]).toString());
		}
		return results;
	}

}
