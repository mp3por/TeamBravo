package glasgow.teamproject.teamB.TwitIE;
import gate.CorpusController;
import gate.Factory;
import gate.Gate;
import gate.util.GateException;
import gate.util.persistence.PersistenceManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import gate.Annotation;
import gate.AnnotationSet;
import gate.Corpus;
import gate.CorpusController;
import gate.Document;
import gate.Factory;
import gate.Gate;
import gate.creole.ExecutionException;
import gate.creole.ResourceInstantiationException;
import gate.util.GateException;
import gate.util.persistence.PersistenceManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public abstract class NamedEntityExtractor<Tweet> {
	
	private ArrayList<Tweet> tweets = new ArrayList<>();
	private HashSet<String> interestedNE = new HashSet<String>(); 
	private ArrayList<String> types = new ArrayList<>();
	
	public HashSet<String> defaultNE = new HashSet<String>(Arrays.asList("Location", "Organization", "Person", "Hashtag", "URL", "UserID", "Emoticon"));
		
	public abstract void addTweet (Tweet tweet);
	public abstract void addTweets (ArrayList<Tweet> tweets);
	
	public abstract void processTweets ();
	
	public void removeTweets () {
		tweets.clear();
	}
	
	public void addNE (String s) {
		interestedNE.add(s);
	}

	public abstract void init ();

	public abstract ArrayList<String> getAllTypes();

	public abstract void saveTweets ();

	
	
	
}
