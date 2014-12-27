package glasgow.teamproject.teamB.mongodb.dao;

public interface TweetDAO {
	/**
	 * Adds a tweet to the DB
	 * @param tweet must be a JSON string
	 * */
	public void addTweet(String tweet);
	
}
