package glasgow.teamproject.teamB.Graphs;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.MongoClient;

import glasgow.teamproject.teamB.TwitIE.NamedEntityParser;
import glasgow.teamproject.teamB.TwitIE.TwitIE;
import glasgow.teamproject.teamB.Util.ProjectProperties;
import glasgow.teamproject.teamB.Util.StreamReaderService;
import glasgow.teamproject.teamB.Util.TwitterStreamBuilderUtil;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAO;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAOAbstract;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAOImpl;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAOImpl.EntityCountPair;

public class TestRun {
	public static final String DB_NAME = "tweetsTest";
	public static final String TWEETS_COLLECTION = "tweets";
	public static final String MONGO_HOST = "localhost";
	public static final int MONGO_PORT = 27017;

	public static void main(String[] args) {
		
		/*System.out.println("start");
		try {
			MongoClient mongo = new MongoClient(MONGO_HOST, MONGO_PORT);
			MongoOperations mongoOps = new MongoTemplate(mongo, DB_NAME);

			StreamReaderService serv = new StreamReaderService(new ProjectProperties(), new TwitterStreamBuilderUtil());
			System.out.println("111111");

			TweetDAOAbstract c = new TweetDAOImpl(mongoOps, serv);
			System.out.println("222222");
			
			List<EntityCountPair> tweets = c.getTopEntities(TweetDAOImpl.Field.ALL,
					TweetDAOImpl.TimePeriod.PASTWEEK, 10);

			for (EntityCountPair e : tweets) {
				System.out.println(e.getID() + " " + e.getCount().intValue());
			}
			
			
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}*/
	}

}
