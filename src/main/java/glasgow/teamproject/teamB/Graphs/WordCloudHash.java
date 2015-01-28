package glasgow.teamproject.teamB.Graphs;

import twitter4j.JSONArray;
import twitter4j.JSONException;
import twitter4j.JSONObject;

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
					value = (double) Math.round(value);
				}else{
					value = Math.ceil(value); //Rounds UP to nearest int
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
		
		int hashedValue;
		if(value == 1 || value == 2){
			hashedValue = 6;
		}else if(value == 3){
			hashedValue = 7;
		}else if(value == 4){
			hashedValue = 8;
		}else if(value >= 5 && value <= 8){
			hashedValue = 9;
		}else if(value >= 9 && value <= 12){
			hashedValue = 16;
		}else if(value >= 13 && value <= 15){
			hashedValue = 20;
		}else if(value >= 16 && value <= 18){
			hashedValue = 26;
		}else if(value >= 19 && value <= 21){
			hashedValue = 28;
		}else if(value >= 22 && value <= 25){
			hashedValue = 32;
		}else if(value >= 26 && value <= 30){
			hashedValue = 36;
		}else if(value >= 31 && value <= 35){
			hashedValue = 42;
		}else if(value >= 36 && value <= 42){
			hashedValue = 50;
		}else if(value >= 43 && value <= 60){
			hashedValue = 60;
		}else if(value >= 61 && value <= 70){
			hashedValue = 70;
		}else if(value >= 71 && value <= 80){
			hashedValue = 80;
		}else if(value >= 81 && value <= 90){
			hashedValue = 100;
		}else{
			hashedValue = 120;
		}
		
		
		return hashedValue;
	}
}
