package glasgow.teamproject.teamB.Graphs;

import glasgow.teamproject.teamB.mongodb.dao.TweetDAO;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAOImpl;

import java.net.UnknownHostException;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.MongoClient;

public class TestRun {
	
	private static final String DB_NAME = "tweetsTest";
	private static final String MONGO_HOST = "localhost";
	private static final int MONGO_PORT = 27017;
	private static TweetDAO tweetdao;
	private static MongoOperations mongoOps;
	
	//Sets up client and mongo operations objects
	private static void setUpDBInfo(){
		try{
			MongoClient mongo = new MongoClient(MONGO_HOST, MONGO_PORT);
			mongoOps = new MongoTemplate(mongo, DB_NAME);
			tweetdao = new TweetDAOImpl(mongoOps);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	
	public static void main(String[] args){
		
		/*setUpDBInfo();
		mongoOps.createCollection("DCNT_");
		mongoOps.createCollection("WCNT");
		mongoOps.createCollection("MCNT");*/

	}
	
	
	
	
	
	
	
	
	
	
	
}
