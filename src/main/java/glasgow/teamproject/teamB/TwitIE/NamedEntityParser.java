package glasgow.teamproject.teamB.TwitIE;

import java.util.ArrayList;
import java.util.HashMap;

public interface NamedEntityParser {
	
	public HashMap<String, ArrayList<String>>  getNamedEntites(String tweet);

}
