package glasgow.teamproject.teamB.Twitter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import twitter4j.JSONArray;
import twitter4j.JSONException;
import twitter4j.JSONObject;


public class JSonExtractor {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	private static ArrayList<Tweet> readJSONFile (Integer id){
	String currentJSONString = "";
	FileReader f;
	try {
		f = new FileReader("out.json"+id.toString());
		BufferedReader br = new BufferedReader(f);
		currentJSONString = br.readLine();
		br.close();

	} catch (IOException e1) {
		// TODO Auto-generated catch block
		System.out.println("Read " + (id-1) + " files");
		return null;
	}
	finally {
		
	}
	JSONArray currentJSONArray = new JSONArray();
			

	try {
			currentJSONArray = new JSONArray(currentJSONString);
	
	} catch (JSONException e) {
		// TODO Auto-generated catch block
	}
	ArrayList<Tweet> tweetsList = new ArrayList<>();
	for (int i = 0; i < currentJSONArray.length(); i++) {
		try {
			tweetsList.add(new Tweet(new JSONObject(currentJSONArray.getString(i))));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	return tweetsList;
}
	public static void main(String[] args) throws FileNotFoundException {
		ArrayList<Tweet> tweets = new ArrayList<>();
		int id = 0;
		ArrayList<Tweet> TweetsArray = new ArrayList<>();
		while ((TweetsArray = readJSONFile(id)) != null) {
			System.out.println("Read" + id);
			tweets.addAll(TweetsArray);
			id++;
		}
		//Tweets are now ordered in chronological order
		Collections.reverse(tweets);

		System.out.println("Total " + tweets.size() + " tweets");
		System.out.println("Tweets are between these dates: " + tweets.get(0).getDate() + " and " + tweets.get(tweets.size()-1).getDate());
		System.out.println("First tweet: " + tweets.get(0).getMessage());
		System.out.println("Last tweet: " + tweets.get(tweets.size()-1).getMessage());
		
		
		//System.out.println("hello");

	}
	}


