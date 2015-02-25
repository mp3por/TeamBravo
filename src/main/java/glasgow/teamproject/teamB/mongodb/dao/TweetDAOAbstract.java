package glasgow.teamproject.teamB.mongodb.dao;

import java.util.Observer;

import glasgow.teamproject.teamB.Util.StreamReaderService;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class TweetDAOAbstract implements Observer, TweetDAO  {

	@Autowired
	private StreamReaderService serv;

	@PostConstruct
	private void setUp() {
		serv.addObserver(this);
	}
	
}
