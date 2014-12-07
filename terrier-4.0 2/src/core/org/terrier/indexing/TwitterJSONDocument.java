/*
 * Terrier - Terabyte Retriever 
 * Webpage: http://terrier.org/
 * Contact: terrier{a.}dcs.gla.ac.uk
 * University of Glasgow - Department of Computing Science
 * http://www.gla.ac.uk/
 * 
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See
 * the License for the specific language governing rights and limitations
 * under the License.
 *
 * The Original Code is TwitterJSONDocument.java
 *
 * The Original Code is Copyright (C) 2004-2014 the University of Glasgow.
 * All Rights Reserved.
 *
 * Contributor(s):
 *   Richard McCreadie <richardm{a.}dcs.gla.ac.uk> (original contributor)
 */
package org.terrier.indexing;

import gnu.trove.TObjectIntHashMap;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.hadoop.io.Text;
import org.terrier.indexing.tokenisation.Tokeniser;
import org.terrier.structures.Index;
import org.terrier.structures.Lexicon;
import org.terrier.structures.seralization.FixedSizeTextFactory;
import org.terrier.terms.BaseTermPipelineAccessor;
import org.terrier.terms.TermPipelineAccessor;
import org.terrier.utility.ApplicationSetup;
import org.terrier.utility.ArrayUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

/**
 * This is a Terrier Document implementation of a Tweet stored in JSON format. It parses out
 * the fields of the Tweet from an input google.gson JsonObject. This document implementation
 * implements fields and meta-data.
 * 
 * Fields:
 * Each TwitterJSONDocument is considered to have four fields for searching. The tokenised tweet text, denoted TWEET,
 * this will have been processed by the tokeniser and subjected to stopword removal/stemming.
 * The raw user name of the tweeter broken on spaces, denoted NAME. The raw screen name of the tweeter broken on spaces, 
 * denoted SNAME. The location of the tweet processed by the terrier EnglishTokeniser and subjected to stopword
 * removal/stemming, denoted LOC.
 * 
 * Meta-Data:
 * During the parsing process, the properties of each TwitterJSONDocument is decorated with tweet meta-data.
 * The following are added to the document properties, if and only if they exist in JSON input. Note that unless
 * you are using the TREC Twitter API crawler or the Gardenhose/Firehose stream, then the majority of this data 
 * will be missing, as much of this data is unavailable when scraping the HTML.
 * 
 * // Tweet data
 * docno
 * id
 * created_at
 * source
 * lang
 * text
 * truncated
 * retweet_count
 * contributors
 *
 * // User data
 * user.screen_name
 * user.protected
 * user.lang
 * user.name
 * user.profile_image_url
 * user.friends_count
 * user.favourites_count
 * user.listed_count
 * user.statuses_count
 * user.followers_count
 * user.description
 * user.location
 * user.id
 * user.time_zone
 * user.utc_offset
 * 
 * // if tweet is reply
 * in_reply_to_screen_name
 * in_reply_to_user_id
 * in_reply_to_status_id
 * 
 * // if place is known (like a region, for example a city, defined my a polygon with gps coordinates for points)
 * place.place_type
 * place.country_code
 * place.id
 * place.name
 * place.full_name
 * place.url
 * place.country
 * place.bounding_box.type (always polygon?)
 * place.bounding_box.coordinates.size
 * place.bounding_box.coordinates.[n].lat
 * place.bounding_box.coordinates.[n].lng
 * 
 * // if user coordinates are known
 * coordinates.type (always point?)
 * coordinates.lat
 * coordinates.lng
 * 
 * // if geo location of user is known
 * geo.type (always point?)
 * geo.lat
 * geo.lng
 *
 * // if is retweet
 * All of the above, but add retweet. on the front 
 * 
 * @author Richard McCreadie
 * @since 4.0
 *
 */
public class TwitterJSONDocument implements Document{
	
	private static JsonParser parser = new JsonParser();
	
	private Map<String,String> properties = null;
	private String[] terms = null;
	private int index = -1;
	private Tokeniser t;
	private String[] fields = new String[]{"TWEET","NAME","SNAME","LOC"};
	private String[] fieldsToProcess = ArrayUtils.parseCommaDelimitedString(ApplicationSetup.getProperty("FieldTags.process", ""));
	private short[] termfields;
	private int maxtermlength = Integer.parseInt(ApplicationSetup.getProperty("max.term.length", "20"));
	String[] keys = ApplicationSetup.getProperty("indexer.meta.forward.keys", "docno,text,created_at").split(",");
	String[] maxlengths = ApplicationSetup.getProperty("indexer.meta.forward.keylens", "32,200,40").split(",");
	TObjectIntHashMap<String> bytelengths = new TObjectIntHashMap<String>();
	TObjectIntHashMap<String> keyslengths = new TObjectIntHashMap<String>();
	private boolean saveAll = Boolean.parseBoolean(ApplicationSetup.getProperty("JSONDocument.saveAll", "false"));
	private boolean ignoreURLs = Boolean.parseBoolean(ApplicationSetup.getProperty("JSONDocument.ignoreURLs", "true"));
	private boolean ignoreMentions = Boolean.parseBoolean(ApplicationSetup.getProperty("JSONDocument.ignoreMentions", "true"));
	
	/** TermPipeline processing */
	protected static TermPipelineAccessor tpa = null;
	
	public TwitterJSONDocument(String JSONTweet) {
		//System.err.println("TwitterJSONDocument: New tweet from string");
		JsonObject json = null;
		try {
			json = parser.parse(JSONTweet).getAsJsonObject();
		} catch (JsonSyntaxException e) {
			System.err.println("TwitterJSONDocument: Syntax Failure");
			e.printStackTrace();
		}
		doParsing(json);
	}
	
	public TwitterJSONDocument(String JSONTweet, boolean saveAll) {
		this.saveAll = saveAll;
		JsonObject json = null;
		try {
			json = parser.parse(JSONTweet).getAsJsonObject();
		} catch (JsonSyntaxException e) {
			System.err.println("TwitterJSONDocument: Syntax Failure");
			e.printStackTrace();
		}
		doParsing(json);
	}
	
	public void doParsing(JsonObject json) {
		
		
		
		//System.err.println("Parsing Tweet");
		
		if (tpa==null) load_pipeline();
		Set<String> processFields = new HashSet<String>();
		for (String field : fieldsToProcess) processFields.add(field);
		int i = 0;
		if (keys.length>0) {
			for (String key : keys) {
				keyslengths.put(key, Integer.parseInt(maxlengths[i]));
				bytelengths.put(key, FixedSizeTextFactory.getMaximumTextLength(Integer.parseInt(maxlengths[i])));
				i++;
			}
		}
		
		
		properties = new HashMap<String,String>();
		if (json.get("id")!=null) if (!json.get("id").isJsonNull()) {
			addProperty("docno", String.valueOf(json.get("id").getAsLong()));
			addProperty("id", String.valueOf(json.get("id").getAsLong()));
		} else {
			addProperty("docno", json.get("id_str").getAsString());
			addProperty("id", json.get("id_str").getAsString());
		}
		
		if (json.get("created_at")!=null) if (!json.get("created_at").isJsonNull()) addProperty("created_at", json.get("created_at").getAsString());
		if (json.get("source")!=null) if (!json.get("source").isJsonNull()) addProperty("source", json.get("source").getAsString());
	    if (json.get("lang")!=null) if (!json.get("lang").isJsonNull()) addProperty("lang", json.get("lang").getAsString());
		if (json.get("text")!=null) if (!json.get("text").isJsonNull()) addProperty("text",json.get("text").getAsString());

		// text processing block - tokenise the tweet/stop and stem
		String[] tokenisedterms = {};
		Reader r = new StringReader(properties.get("text"));
		t = Tokeniser.getTokeniser();
		List<String> pipedTerms = new ArrayList<String>();
		try {
			tokenisedterms = t.getTokens(r);
			pipedTerms.clear();
			for (int j=0; j<tokenisedterms.length; j++) {
				if ((ignoreURLs && tokenisedterms[j].contains("/")) || (ignoreMentions && tokenisedterms[j].contains("@"))) {
					//System.err.println("Ignoring "+tokenisedterms[j]);
					continue;
				}
				String term = tpa.pipelineTerm(tokenisedterms[j]);
				if (term!=null) pipedTerms.add(term);
			}
			tokenisedterms=pipedTerms.toArray(new String[pipedTerms.size()]);
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		//String[] untokenisedterms = {};
		
		if (json.get("truncated")!=null) if (!json.get("truncated").isJsonNull()) addProperty("truncated", String.valueOf(json.get("truncated").getAsBoolean()));
		if (json.get("retweet_count")!=null) if (!json.get("retweet_count").isJsonNull()) addProperty("retweet_count", json.get("retweet_count").getAsString());
		index= -1;
	    
	    
	    //retweet block
		// just call the constructor again with the retweet block
		JsonObject jretweet = null;
		if (json.get("retweeted_status")!=null) if (!json.get("retweeted_status").isJsonNull()) {
			jretweet = json.getAsJsonObject("retweeted_status");
			TwitterJSONDocument retweet = new TwitterJSONDocument(jretweet);
			for (String key : retweet.getAllProperties().keySet()) {
				addProperty("retweet."+key, retweet.getAllProperties().get(key));
			}
		}
		
	    // ReplyStatus will/may override these if the tweet is a reply
		if (json.get("in_reply_to_screen_name")!=null) if (!json.get("in_reply_to_screen_name").isJsonNull()) addProperty("in_reply_to_screen_name", json.get("in_reply_to_screen_name").getAsString());
		if (json.get("in_reply_to_user_id")!=null) if (!json.get("in_reply_to_user_id").isJsonNull()) addProperty("in_reply_to_user_id", String.valueOf(json.get("in_reply_to_user_id").getAsLong()));
		if (json.get("in_reply_to_status_id")!=null) if (!json.get("in_reply_to_status_id").isJsonNull()) addProperty("in_reply_to_status_id", String.valueOf(json.get("in_reply_to_status_id").getAsLong()));
	    
		//array
		if (json.get("contributors")!=null) {
			if (json.get("contributors").isJsonArray()) addProperty("contributors", json.getAsJsonArray("contributors").toString());
		}
	    //user block
	    JsonObject juser = json.getAsJsonObject("user");
	    if (juser!=null) { 
	    if (juser.get("screen_name")!=null) if (!juser.get("screen_name").isJsonNull()) addProperty("user.screen_name", juser.get("screen_name").getAsString());    
	    if (juser.get("protected")!=null) if (!juser.get("protected").isJsonNull()) addProperty("user.protected", String.valueOf(juser.get("protected").getAsBoolean()));
	    if (juser.get("lang")!=null) if (!juser.get("lang").isJsonNull()) addProperty("user.lang", juser.get("lang").getAsString());
	    if (juser.get("name")!=null) if (!juser.get("name").isJsonNull()) addProperty("user.name", juser.get("name").getAsString());
	    if (juser.get("profile_image_url")!=null) if (!juser.get("profile_image_url").isJsonNull()) addProperty("user.profile_image_url", juser.get("profile_image_url").getAsString());
	    if (juser.get("friends_count")!=null) if (!juser.get("friends_count").isJsonNull()) addProperty("user.friends_count", new Integer(juser.get("friends_count").getAsInt()).toString());
		if (juser.get("favourites_count")!=null) if (!juser.get("favourites_count").isJsonNull()) addProperty("user.favourites_count", new Integer(juser.get("favourites_count").getAsInt()).toString());
		if (juser.get("listed_count")!=null) if (!juser.get("listed_count").isJsonNull()) addProperty("user.listed_count", new Integer(juser.get("listed_count").getAsInt()).toString());
		if (juser.get("statuses_count")!=null) if (!juser.get("statuses_count").isJsonNull()) addProperty("user.statuses_count", new Integer(juser.get("statuses_count").getAsInt()).toString());
		if (juser.get("followers_count")!=null) if (!juser.get("followers_count").isJsonNull()) addProperty("user.followers_count", new Integer(juser.get("followers_count").getAsInt()).toString());
		if (juser.get("followers_count")!=null) if (!juser.get("followers_count").isJsonNull()) addProperty("user.followers_count", new Integer(juser.get("followers_count").getAsInt()).toString());
		if (juser.get("description")!=null) if (!juser.get("description").isJsonNull()) addProperty("user.description", juser.get("description").getAsString());
		if (juser.get("location")!=null) if (!juser.get("location").isJsonNull()) addProperty("user.location", juser.get("location").getAsString());
		if (juser.get("id")!=null) if (!juser.get("id").isJsonNull()) addProperty("user.id", juser.get("id").getAsString());
		if (juser.get("time_zone")!=null) if (!juser.get("time_zone").isJsonNull()) addProperty("user.time_zone", juser.get("time_zone").getAsString());
		if (juser.get("utc_offset")!=null) if (!juser.get("utc_offset").isJsonNull()) addProperty("user.utc_offset", juser.get("utc_offset").getAsString());
	    }
		String[] username =new String[0];
		if (processFields.contains("NAME")) {
			try {
				r = new StringReader((properties.get("user.name")+" "+properties.get("retweet.user.name")));
				username = t.getTokens(r);
				pipedTerms.clear();
				for (int j=0; j<username.length; j++) {
					String term = tpa.pipelineTerm(username[j]);
					if (term!=null) pipedTerms.add(term);
				}
				username=pipedTerms.toArray(new String[pipedTerms.size()]);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String[] screenname = new String[0];
		if (processFields.contains("SNAME")) {
			try {
				r = new StringReader((properties.get("user.screen_name")+" "+properties.get("retweet.user.screen_name")));
				screenname = t.getTokens(r);
				pipedTerms.clear();
				for (int j=0; j<screenname.length; j++) {
					String term = tpa.pipelineTerm(screenname[j]);
					if (term!=null) pipedTerms.add(term);
				}
				screenname=pipedTerms.toArray(new String[pipedTerms.size()]);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	    
	    //location block
	    // example : "place":{
	    //	"country_code":"BR",
	    //	"bounding_box":{
	    //		"type":"Polygon",
	    //		"coordinates":[[[-48.285982,-16.0524045],[-47.307264,-16.0524045],[-47.307264,-15.500216],[-48.285982,-15.500216]]]},
	    //	"place_type":"city",
	    //	"country":"Brasil",
	    //	"attributes":{},
	    //	"name":"Bras\u00edlia",
	    //	"full_name":"Bras\u00edlia, Distrito Federal",
	    //	"id":"5722ff20ba67083b",
	    //	"url":"http:\/\/api.twitter.com\/1\/geo\/id\/5722ff20ba67083b.json"}
	    
	    JsonObject jplace = null;
	    if (json.get("place")!=null) if (!json.get("place").isJsonNull()) jplace = json.getAsJsonObject("place");
	    if (jplace!=null) {
	    	if (jplace.get("place_type")!=null) if (!jplace.get("place_type").isJsonNull()) addProperty("place.place_type", jplace.get("place_type").getAsString());
	    	if (jplace.get("country_code")!=null) if (!jplace.get("country_code").isJsonNull()) addProperty("place.country_code", jplace.get("country_code").getAsString());
	    	if (jplace.get("id")!=null) if (!jplace.get("id").isJsonNull()) addProperty("place.id", jplace.get("id").getAsString());
	    	if (jplace.get("place_id")!=null) if (!jplace.get("place_id").isJsonNull()) addProperty("place.id", jplace.get("place_id").getAsString());
	    	if (jplace.get("name")!=null) if (!jplace.get("name").isJsonNull()) addProperty("place.name", jplace.get("name").getAsString());
	    	if (jplace.get("place_name")!=null) if (!jplace.get("place_name").isJsonNull()) addProperty("place.name", jplace.get("place_name").getAsString());
	    	if (jplace.get("full_name")!=null) if (!jplace.get("full_name").isJsonNull()) addProperty("place.full_name", jplace.get("full_name").getAsString());
	    	if (jplace.get("url")!=null) if (!jplace.get("url").isJsonNull()) addProperty("place.url", jplace.get("url").getAsString());
	    	if (jplace.get("country")!=null) if (!jplace.get("country").isJsonNull()) addProperty("place.country", jplace.get("country").getAsString());
	    	if (jplace.get("bounding_box")!=null) {
	    		if (!jplace.get("bounding_box").isJsonNull()) {
	    			JsonObject jboundbox = jplace.getAsJsonObject("bounding_box");
	    			if (jboundbox.get("type")!=null) if (!jboundbox.get("type").isJsonNull()) addProperty("place.bounding_box.type", jboundbox.get("type").getAsString());
	    			if (jboundbox.get("coordinates").isJsonArray()) {
	    				JsonArray coords = jboundbox.getAsJsonArray("coordinates");
	    				addProperty("place.bounding_box.coordinates.size", String.valueOf(coords.size()));
	    				for (int coord = 0; coord<coords.size(); coord++) {
	    					if (coords.get(coord).isJsonArray()) {
	    						JsonArray latlng = coords.get(coord).getAsJsonArray();
	    						if ( latlng.size() ==2 ) {
	    			    			if (latlng.get(0)!=null) if (!latlng.get(0).isJsonNull()) addProperty("place.bounding_box.coordinates."+coord+".lat", String.valueOf(latlng.get(0).getAsDouble()));
	    			    			if (latlng.get(1)!=null) if (!latlng.get(1).isJsonNull()) addProperty("place.bounding_box.coordinates."+coord+".lng", String.valueOf(latlng.get(1).getAsDouble()));
	    			    		}
	    					}
	    				}
	    			}
	    		}
	    	}
	    	// catch case for the TerrierTeam crawler which uses a slightly different format
	    	if (jplace.get("latlng")!=null) {
	    		if (!jplace.get("latlng").isJsonArray()) {
	    			JsonArray latlng = jplace.getAsJsonArray("latlng");
	    			if ( latlng.size() ==2 ) {
	    				addProperty("geo.type", "Point");
		    			if (latlng.get(0)!=null) if (!latlng.get(0).isJsonNull()) addProperty("geo.lat", String.valueOf(latlng.get(0).getAsDouble()));
		    			if (latlng.get(1)!=null) if (!latlng.get(1).isJsonNull()) addProperty("geo.lng", String.valueOf(latlng.get(1).getAsDouble()));
		    		}
	    		}
	    	}
	    }
	    
	    String[] location;
	    String placename = "";
	    String[] placenameA = new String[0];;
	    
	    if (properties.get("place.name")!=null) {
	    	r = new StringReader(properties.get("place.name"));
		    try {
		    	placenameA = t.getTokens(r);
		    	for (String s : placenameA) {
					placename = placename+" "+s;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
	    }
	    if (properties.get("retweet.place.name")!=null) {
	    	r = new StringReader(properties.get("retweet.place.name"));
		    try {
		    	placenameA = t.getTokens(r);
		    	pipedTerms.clear();
				for (int j=0; j<placenameA.length; j++) {
					String term = tpa.pipelineTerm(placenameA[j]);
					if (term!=null) pipedTerms.add(term);
				}
				placenameA=pipedTerms.toArray(new String[pipedTerms.size()]);
			} catch (IOException e) {
				e.printStackTrace();
			}

	    }
	    
		if (processFields.contains("LOC")) {
			location = placenameA;
		} else {
			location = new String[0];
		}
	    
	    // coordinates block
	    //"coordinates":{
	    //	"type":"Point",
	    //	"coordinates":[-47.89096436,-15.75141167]}
	    if (json.get("coordinates")!=null) {
	    	if (!json.get("coordinates").isJsonNull()) {
				JsonObject jcoordinates = json.getAsJsonObject("coordinates");
				if (jcoordinates.get("type")!=null) if (!jcoordinates.get("type").isJsonNull()) addProperty("coordinates.type", jcoordinates.get("type").getAsString());
				if (jcoordinates.get("coordinates").isJsonArray()) {
					JsonArray coords = jcoordinates.getAsJsonArray("coordinates");
					if ( coords.size() ==2 ) {
						if (coords.get(0)!=null) if (!coords.get(0).isJsonNull()) addProperty("coordinates.lat", String.valueOf(coords.get(0).getAsDouble()));
						if (coords.get(1)!=null) if (!coords.get(1).isJsonNull()) addProperty("coordinates.lng", String.valueOf(coords.get(1).getAsDouble()));
					}
				}
	    	}
	    }
	    
	    // geo block
	    //"geo":{
	    //	"type":"Point",
	    //	"coordinates":[-15.75141167,-47.89096436]}
	    if (json.get("geo")!=null) {
	    	if (!json.get("geo").isJsonNull()) {
	    		JsonObject jgeo = json.getAsJsonObject("geo");
	    		if (jgeo.get("type")!=null) if (!jgeo.get("type").isJsonNull()) addProperty("geo.type", jgeo.get("type").getAsString());
	    		if (jgeo.get("coordinates").isJsonArray()) {
	    			JsonArray coords = jgeo.getAsJsonArray("coordinates");
	    			if ( coords.size() ==2 ) {
	    				if (coords.get(0)!=null) if (!coords.get(0).isJsonNull()) addProperty("geo.lat", String.valueOf(coords.get(0).getAsDouble()));
	    				if (coords.get(1)!=null) if (!coords.get(1).isJsonNull()) addProperty("geo.lng", String.valueOf(coords.get(1).getAsDouble()));
	    			}
	    		}	
	    	}
	    }
	    
	    JsonObject jentities = json.getAsJsonObject("entities");
	    if (jentities!=null) {
	    	if (jentities.get("hashtags")!=null) if (!jentities.get("hashtags").isJsonNull()) if (jentities.get("hashtags").isJsonArray()) addProperty("hashtags", jentities.getAsJsonArray("hashtags").toString());
	    	if (jentities.get("urls")!=null) if (!jentities.get("urls").isJsonNull()) if (jentities.get("urls").isJsonArray()) addProperty("urls", jentities.getAsJsonArray("urls").toString());
	    	if (jentities.get("user_mentions")!=null) if (!jentities.get("user_mentions").isJsonNull()) if (jentities.get("user_mentions").isJsonArray()) addProperty("user_mentions", jentities.getAsJsonArray("user_mentions").toString());
	    }
	    
	    int totallength = tokenisedterms.length+username.length+screenname.length+location.length;
		terms = new String[totallength];
		termfields = new short[totallength];
		i=0;
		for (String t : tokenisedterms) {
			if (t==null) continue;
			if (t.length()>maxtermlength) t=t.substring(0, maxtermlength);
			terms[i] = t;
			termfields[i] = 0;
			i++;
		}
		if (processFields.contains("NAME")) {
			for (String t : username) {
				if (t.length()>maxtermlength) t=t.substring(0, maxtermlength);
				terms[i] = t;
				termfields[i] = 1;
				i++;
			}
		}
		if (processFields.contains("SNAME")) {
			for (String t : screenname) {
				if (t.length()>maxtermlength) t=t.substring(0, maxtermlength);
				terms[i] = t;
				termfields[i] = 2;
				i++;
			}
		}
		if (processFields.contains("LOC")) {
			for (String t : location) {
				if (t.length()>maxtermlength) t=t.substring(0, maxtermlength);
				terms[i] = t;
				termfields[i] = 3;
				i++;
			}
		}
		
		
		// clean up
		String[] pk = properties.keySet().toArray(new String[properties.size()]);
		for (String key : pk) {
			if (!keyslengths.contains(key)) properties.remove(key);
		}
		
		//System.err.println("Finished Parsing Tweet");
		
		
	}
	
	public TwitterJSONDocument(JsonObject json) {
		
		doParsing(json);
	}
	
	@Override
	public String getNextTerm() {
		index = index+1;
		return terms[index].replace('.', ' ').trim();
	}

	@Override
	public Set<String> getFields() {
		Set<String> f = new HashSet<String>();
		f.add(fields[termfields[index]]);
		return f;
	}

	@Override
	public boolean endOfDocument() {
		return (!(index<terms.length-1));
	}

	@Override
	public Reader getReader() {
		return new StringReader(properties.get("text"));
	}

	@Override
	public String getProperty(String name) {
		return properties.get(name.toLowerCase());
	}

	@Override
	public Map<String, String> getAllProperties() {
		return properties;
	}
	
	/**
	 * Add a specific property to the properties for this document.
	 * This method has a second function, in that it will attempt to
	 * trim the tweet if it exceeds the meta index length for the key.
	 * @param propertyName
	 * @param propertyValue
	 */
	public void addProperty(String propertyName, String propertyValue) {
		//System.err.println("Add "+propertyName+" "+propertyValue+" "+saveAll);
		//System.err.println("1="+propertyValue);
			if ((propertyValue.length() == 0 && keyslengths.containsKey(propertyName))) {
				properties.put(propertyName, propertyValue);
			}
			else if (keyslengths.containsKey(propertyName) || saveAll) {
				if (propertyValue.length()>keyslengths.get(propertyName)) {
					propertyValue = propertyValue.substring(0,keyslengths.get(propertyName));
				}
				//System.err.println("2="+propertyValue);
				while(byteLength(propertyValue) > bytelengths.get(propertyName)) {
					propertyValue = propertyValue.substring(0, propertyValue.length() -1);
				}
				//System.err.println("3="+propertyValue);	
				properties.put(propertyName, propertyValue);
			}
	}

	protected int byteLength(String t)
	{
		try{
			return Text.encode(t).array().length;
		} catch (Exception e) {
			assert false;
			return -1;
		}
	}
	
	/** load in the term pipeline */
	public static void load_pipeline()
	{
		//final String[] pipes = ApplicationSetup.getProperty(
		//		"termpipelines", "Stopwords,PorterStemmer").trim()
		//		.split("\\s*,\\s*");
			tpa = new BaseTermPipelineAccessor("Stopwords");
	}
	
	
	
	public static void main(String[] args) {
		String testterms = "BBC World Service staff cuts 2022 FIFA soccer  Haiti Aristide return Mexico drug war NIST computer security NSA  Pakistan diplomat arrest murder phone hacking British politicians Toyota Recall Egyptian protesters attack museum Kubica crash Assange Nobel peace nomination Oprah Winfrey half-sister release of The Rite Thorpe return in 2012 Olympics release of Known and Unknown White Stripes breakup  William and Kate fax save-the-date Cuomo budget cuts Taco Bell filling lawsuit Emanuel residency court rulings healthcare law unconstitutional  Amtrak train service Super Bowl, seats TSA airport screening US unemployment reduce energy consumption Detroit Auto Show global warming and weather  Keith Olbermann new job Special Olympics athletes State of the Union  and jobs Dog Whisperer Cesar Millan's techniques MSNBC Rachel Maddow Sargent Shriver tributes Moscow airport bombing Giffords' recovery protests in Jordan Egyptian curfew Beck attacks Piven Obama birth certificate Holland Iran envoy recall Kucinich olive pit lawsuit White House spokesman replaced political campaigns and social media Bottega Veneta organic farming requirements Egyptian evacuation carbon monoxide law war prisoners, Hatch Act";
		if (tpa==null) load_pipeline();
		String[] tokenisedterms = {};
		Reader r = new StringReader(testterms);
		Tokeniser t = Tokeniser.getTokeniser();
		List<String> pipedTerms = new ArrayList<String>();
		
		Set<String> stem1terms = new HashSet<String>();
		Set<String> stem2terms = new HashSet<String>();
		try {
			tokenisedterms = t.getTokens(r);
			pipedTerms.clear();
			for (int j=0; j<tokenisedterms.length; j++) {
				String term = tpa.pipelineTerm(tokenisedterms[j]);
				String term2 = tpa.pipelineTerm(term);
				if (term!=null) {
					pipedTerms.add(term);
					stem1terms.add(term);
					stem2terms.add(term2);
					if (term2==null) System.err.println("WARN: "+tokenisedterms[j]+" "+term+" "+term2);
					else if (!term.equalsIgnoreCase(term2)) System.err.println("WARN: "+tokenisedterms[j]+" "+term+" "+term2);
				}
			}
			tokenisedterms=pipedTerms.toArray(new String[pipedTerms.size()]);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Index.setIndexLoadingProfileAsRetrieval(false);
		Index index = Index.createIndex("/users/richardm/tr.richardm/Twitter/TRECMicroblog2012/", "Twitter2012TIndexNoUM");
		Lexicon<String> lex = index.getLexicon();
		int match1 = 0;
		int match2 = 0;
		for (int i =0; i<index.getCollectionStatistics().getNumberOfUniqueTerms(); i++) {
			String te = (String)lex.getIthLexiconEntry(i).getKey();
			if (stem1terms.contains(te)) match1++;
			if (stem2terms.contains(te)) match2++;
			
		}
		
		System.err.println("Matched 1 stem "+match1+" of "+stem1terms.size());
		System.err.println("Matched 2 stem "+match2+" of "+stem2terms.size());
	}
	
}
