package glasgow.teamproject.teamB.TwitIE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javax.annotation.PostConstruct;

import glasgow.teamproject.teamB.Util.ProjectProperties;
import glasgow.teamproject.teamB.Util.StreamReaderService;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAO;
import glasgow.teamproject.teamB.mongodb.dao.TweetDAOAbstract;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NamedEntityParser implements Observer {

	@Autowired
	private StreamReaderService serv;

	@Autowired
	private TweetDAOAbstract DB;

	@Autowired
	private TwitIE twitie;

//	public NamedEntityParser(StreamReaderService s, TweetDAOAbstract db, TwitIE t) {
//		this.serv = s;
//		this.DB = db;
//		this.twitie = t;
//	}

	@PostConstruct
	private void setUp() {
		serv.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("NamedEntityParse accept:" + twitie.accept());
		if (twitie.accept()) {
//			System.out.println("NamedEntity1: " + arg);
			String rawString = (String) arg;
			HashMap<String, ArrayList<String>> NEs = twitie.getNamedEntites(rawString);
			System.out.println("NamedEntityParse NEs:" + NEs.isEmpty());
			if (NEs != null && !NEs.isEmpty()) {
				StringBuilder sb = new StringBuilder(rawString);
				sb.setLength(Math.max(sb.length() - 1, 0));
				for (String s : NEs.keySet()) {
					sb.append(",");
					sb.append("\"" + s + "\":\"" + NEs.get(s) + "\"");
				}
				sb.append("}");
				rawString = sb.toString();
				System.out.println("NamedEntityParser before save");
				DB.addTweet(rawString, ProjectProperties.TWEET_COLLECTION);
			}
			//DB.addNamedEntitiesById(tweet.getString("id_str"), ProjectProperties.TWEET_COLLECTION, namedEntites);
		} else {
			System.out.println("MISSED a TWEET");
		}
	}

}
