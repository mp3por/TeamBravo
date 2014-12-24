package glasgow.teamproject.teamB.mongodb.dao;

import org.springframework.data.mongodb.core.MongoOperations;

public class TweetDAOImpl implements TweetDAO {
	
	private MongoOperations mongoOps;
    private static final String TWEET_COLLECTION = "tweets";
    
    public TweetDAOImpl(MongoOperations mongoOps) {
		this.mongoOps = mongoOps;
	}
    
	@Override
	public void addTweet(String tweet) {
		mongoOps.insert(tweet,TWEET_COLLECTION);
	}

}
