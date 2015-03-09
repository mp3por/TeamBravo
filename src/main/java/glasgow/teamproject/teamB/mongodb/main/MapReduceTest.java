package glasgow.teamproject.teamB.mongodb.main;

import glasgow.teamproject.teamB.TwitIE.NamedEntityParser;
import glasgow.teamproject.teamB.TwitIE.TwitIE;
import glasgow.teamproject.teamB.Util.ProjectProperties;
import glasgow.teamproject.teamB.Util.StreamReaderService;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAOAbstract;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAOImpl.TimePeriod;

import java.util.Date;
import java.util.Scanner;

import com.mongodb.MongoClient;

public class MapReduceTest {

	public static final String DB_NAME = "tweetsTest";
	public static final String TWEETS_COLLECTION = "tweets";
	public static final String MONGO_HOST = "localhost";
	public static final int MONGO_PORT = 27017;
	
	static TweetDAOAbstract tweetSaver;
	static NamedEntityParser NE;
	static StreamReaderService serv;
	static MongoClient mongo;
	static TwitIE t;

	public static void main(String[] args) {
		System.out.println("start");
		//startStreamerDBAndNE();
		
		Scanner i = new Scanner(System.in);
		boolean work = true;
		while(work){
			String j = i.next();
			int  p = Integer.parseInt(j);
			switch (p) {
			case 2:
				System.out.println("daily party");
				doDaily(TWEETS_COLLECTION);
				break;
			case 3:
				System.out.println("daily tweets");
				doDaily(ProjectProperties.TWEET_COLLECTION);
				break;
			case 4:
				System.out.println("merge");
				doMerging();
				break;

			default:
				break;
			}
		}
		
	}
	
	private static void doDaily(String name){
		System.out.println("DAILY -> " + name);
		tweetSaver.dailyMapReduce(new Date(),name);
	}

	private static void doMerging() {
		System.out.println("MERGING");
		tweetSaver.mergingMapReduce(TimePeriod.PASTWEEK);
//		tweetSaver.mergingMapReduce(TimePeriod.PASTMONTH);
	}

//	private static void startStreamerDBAndNE() {
//		try {
//			mongo = new MongoClient(MONGO_HOST, MONGO_PORT);
//			MongoOperations mongoOps = new MongoTemplate(mongo, DB_NAME);
//
//			serv = new StreamReaderService(new ProjectProperties(), new TwitterStreamBuilderUtil());
//			System.out.println("111111");
//
//			tweetSaver = new TweetDAOImpl(mongoOps, serv);
//			System.out.println("222222");
//			t = new TwitIE();
//			System.out.println("333333");
//			NE = new NamedEntityParser(serv, tweetSaver, t);
//			System.out.println("444444");
//			//serv.addObserver(tweetSaver);
//			serv.addObserver(NE);
//			System.out.println("555555: " + serv.countObservers());
//			
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		}
//	}
}
