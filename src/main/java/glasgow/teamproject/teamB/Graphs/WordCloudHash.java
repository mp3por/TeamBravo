package glasgow.teamproject.teamB.Graphs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WordCloudHash {
	
	private static JSONArray hashedFrequencies;
	
	public static JSONArray gethashedFrequencies(JSONArray frequencyList){
		
		
		hashedFrequencies = new JSONArray();
		double n = 0; //The sum of all values
		for (int i = 0; i < frequencyList.length(); i++) {
			  try {
				JSONObject item = frequencyList.getJSONObject(i);
				n += (double) item.getInt("Tweets");
			} catch (JSONException e) {
				System.err.println("Exception: WordCloudHash.getFrequencies()");
			}
		}
		
		double sum = 100/n; //For percentage
		
		for (int i = 0; i < frequencyList.length(); i++) {
			try {
				JSONObject item = frequencyList.getJSONObject(i);
				
				JSONObject hashedItem = new JSONObject();
				hashedItem.put("Name", item.get("Name"));
				
				//Rounding value to int
				Integer intTweet = (Integer) item.get("Tweets");
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
				hashedItem.put("Tweets", hashedValue);
				
				//Place into new array
				hashedFrequencies.put(hashedItem);
				
			} catch (JSONException e) {
				System.err.println("Exception: WordCloudHash.getFrequencies()");
			}
		}
		
		return hashedFrequencies;	
	}
	
	private static int hashValue(int value){
		return value + 40;
	}
}
