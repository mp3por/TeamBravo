package glasgow.teamproject.teamB.TwitIE;

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
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;


public class TwitIE extends NamedEntityExtractor<Tweet> {
	private ArrayList<Tweet> tweets = new ArrayList<>();
	private HashSet<String> interestedNE = new HashSet<String>(); 
	private ArrayList<String> types = new ArrayList<>();

	private Corpus corpus;
	private CorpusController pipeline;

	@Override
	public void addTweet(Tweet tweet) {
		tweets.add(tweet);

	}

	@Override
	public void addTweets(ArrayList<Tweet> tweets) {
		tweets.addAll(tweets);

	}

	
	public HashMap<String, ArrayList<String>> processString (String s) {
		if (pipeline == null) init(); 
		
		if (interestedNE.isEmpty()) {
			interestedNE.addAll(defaultNE);
		}

		HashMap<String, ArrayList<String>> NEs = new HashMap<String, ArrayList<String>>();
		if (s.isEmpty()) return null;
		Document doc = null;
		try {
			doc = Factory.newDocument(s);
			corpus.add(doc);

			pipeline.setCorpus(corpus);
			pipeline.execute();
			corpus.remove(doc);
		} catch (ResourceInstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (doc != null) {

			AnnotationSet annotations = doc.getAnnotations();

			Iterator<Annotation> itr = annotations.iterator();
			while (itr.hasNext()) {
				Annotation a = itr.next();
				if (!interestedNE.contains(a.getType())) {
					continue;
				}
				ArrayList<String> NEsArray = NEs.getOrDefault(a.getType(), new ArrayList<String>());
				NEsArray.add(s.substring(a.getStartNode().getOffset().intValue(), a.getEndNode().getOffset().intValue()));
				NEs.put(a.getType(), NEsArray);
			}
			types.addAll(annotations.getAllTypes());

		}
		return NEs;
	}
	
	private void processTweet (Tweet t) {
		String s = null;
		s = getMessage(t);
		if (s.isEmpty()) return;
		Document doc = null;
		try {
			doc = Factory.newDocument(s);
			corpus.add(doc);

			pipeline.setCorpus(corpus);
			pipeline.execute();
			corpus.remove(doc);
		} catch (ResourceInstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (doc != null) {

			AnnotationSet annotations = doc.getAnnotations();

			Iterator<Annotation> itr = annotations.iterator();
			while (itr.hasNext()) {
				Annotation a = itr.next();
				if (!interestedNE.contains(a.getType())) {
					continue;
				}
				t.setNE(a.getType(), s.substring(a.getStartNode().getOffset().intValue(), a.getEndNode().getOffset().intValue()));
			}
			types.addAll(annotations.getAllTypes());

		}
	}
	
	@Override
	public void processTweets() {
		if (pipeline == null) init(); 
		
		if (interestedNE.isEmpty()) {
			interestedNE.addAll(defaultNE);
		}
		
		for (int i = 0; i < tweets.size(); i++) {
			processTweet(tweets.get(i));
		}
	}

	private String getMessage (Tweet t) {
		return t.getMessage();
	}


	@Override
	public void init() {
		try {
			//File f = new File("/home/ppp/TP3/Main/TeamBravo");
			// This is meant to be your main directory, where your git is initialized. For some reasons, it crashed today, so I had to hardcode the file folder
			File f = new File(".");
			System.out.println(f.getCanonicalPath());
			Gate.setGateHome(f);			
			Gate.init();
			pipeline = (CorpusController) PersistenceManager.loadObjectFromFile(new File("applicationState.xgapp"));
			corpus = Factory.newCorpus("Tweet corpus");
		} catch (GateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}		
	}

	public ArrayList<Tweet> getTweets() {
		return tweets;
	}
	
	@Override
	public void saveTweets() {
		for (Tweet t: tweets) {
			System.out.println(t.getMessage());
			System.out.println(t.getNEs());
		}

	}

	@Override
	public ArrayList<String> getAllTypes() {
		return types;
	}

}
