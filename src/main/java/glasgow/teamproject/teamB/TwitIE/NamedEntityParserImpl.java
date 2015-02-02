package glasgow.teamproject.teamB.TwitIE;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NamedEntityParserImpl implements NamedEntityParser {
	
	@Autowired
	private TwitIE twitIE;
	
	
	@Override
	public HashMap<String, ArrayList<String>> getNamedEntites(String tweet) {
		JSONObject ob = new JSONObject(tweet);
		HashMap<String, ArrayList<String>> NEs = null;
		try {
			NEs = twitIE.processString((String) ob.getString("text"));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return NEs;
	}

}
