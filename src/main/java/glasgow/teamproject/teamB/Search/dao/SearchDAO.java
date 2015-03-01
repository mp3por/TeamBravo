package glasgow.teamproject.teamB.Search.dao;

import glasgow.teamproject.teamB.Search.Tweet;

import java.util.ArrayList;

public interface SearchDAO {

	public void runQuery(String mode, String query);
	
	public ArrayList<Tweet> getResultsList();
	
	
}
