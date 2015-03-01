package glasgow.teamproject.teamB.mongodb.main;

import glasgow.teamproject.teamB.TwitIE.NamedEntityParser;
import glasgow.teamproject.teamB.TwitIE.TwitIE;
import glasgow.teamproject.teamB.Util.ProjectProperties;
import glasgow.teamproject.teamB.Util.StreamReaderService;
import glasgow.teamproject.teamB.Util.TwitterStreamBuilderUtil;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAOAbstract;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAOImpl;

import java.net.UnknownHostException;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.MongoClient;

public class ObserverObservableTest {

	public static final String DB_NAME = "tweetsTest";
	public static final String TWEETS_COLLECTION = "tweets";
	public static final String MONGO_HOST = "localhost";
	public static final int MONGO_PORT = 27017;

	public static void main(String[] args) {
		System.out.println("start");
		try {
			MongoClient mongo = new MongoClient(MONGO_HOST, MONGO_PORT);
			MongoOperations mongoOps = new MongoTemplate(mongo, DB_NAME);

			StreamReaderService serv = new StreamReaderService(new ProjectProperties(), new TwitterStreamBuilderUtil());
			System.out.println("111111");

			TweetDAOAbstract tweetSaver = new TweetDAOImpl(mongoOps, serv);
			System.out.println("222222");
			TwitIE t = new TwitIE();
			System.out.println("333333");
			NamedEntityParser NE = new NamedEntityParser(serv, tweetSaver, t);
			System.out.println("444444");
			//serv.addObserver(tweetSaver);
			serv.addObserver(NE);
			System.out.println("555555: " + serv.countObservers());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
