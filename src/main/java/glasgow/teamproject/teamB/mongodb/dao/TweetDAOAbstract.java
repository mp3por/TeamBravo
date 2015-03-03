package glasgow.teamproject.teamB.mongodb.dao;

import java.util.Observer;

import glasgow.teamproject.teamB.Util.StreamReaderService;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class TweetDAOAbstract implements Observer, TweetDAO  {

	@Autowired
	private StreamReaderService serv;
	
	public TweetDAOAbstract(StreamReaderService s) {
		this.serv = s;
	}

	@PostConstruct
	private void setUp() {
		//serv.addObserver(this);
	}
	
}
