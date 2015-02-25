package glasgow.teamproject.teamB.Graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class WordCloudHash {

	private static List<HashMap<String,String>> hashedFrequencies;

	public static List<HashMap<String,String>> gethashedFrequencies(List<HashMap<String,String>> frequencyList){


		hashedFrequencies = new ArrayList<HashMap<String,String>>();
		double n = 0; //The sum of all values
		for (int i = 0; i < frequencyList.size(); i++) {
			HashMap<String,String> item = frequencyList.get(i);
			n += Double.parseDouble(item.get("Tweets"));
		}

		double sum = 100/n; //For percentage

		for (int i = 0; i < frequencyList.size(); i++) {
			HashMap<String,String> item = frequencyList.get(i);
	
			HashMap<String,String> hashedItem = new HashMap<String,String>();
			hashedItem.put("Name", item.get("Name"));
	
			//Rounding value to int
			Integer intTweet = Integer.parseInt(item.get("Tweets"));
			Double value = new Double((double) intTweet);
			value *= sum; //Get percentage
			if(value > 99.0){
				value = (double) Math.floor(value); //Rounds down to nearest whole
			}else{
				value = (double) Math.round(value); //Rounds to nearest whole
			}
			int intVal = value.intValue();
	
			//Hash the value
			int hashedValue = hashValue(intVal);
			hashedItem.put("Tweets", Integer.toString(hashedValue));
	
			//Place into new array
			hashedFrequencies.add(hashedItem);
		}

		return hashedFrequencies;
	}

	private static int hashValue(int value){
		return value + 40;
	}
}
