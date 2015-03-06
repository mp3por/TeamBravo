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
import glasgow.teamproject.teamB.Util.ProjectProperties;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import javax.annotation.PostConstruct;

import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.terrier.utility.ApplicationSetup;

@Component
public class TwitIE {
	private static HashSet<String> interestedNE = new HashSet<String>();
	public final HashSet<String> defaultNE = ProjectProperties.defaultNE;
	private boolean accept = false;

	private static Corpus corpus;
	private static CorpusController pipeline;

	public void addNE(String s) {
		interestedNE.add(s);
	}

//	public TwitIE() {
//		// TODO Auto-generated constructor stub
//		init();
//	}

	@PostConstruct
	public void init() {
		System.out.println("twitie init");
		try {
			if (interestedNE.isEmpty()) {
				interestedNE.addAll(defaultNE);
			}
			System.out.println("added NE");

			// Only god knows how it works
			String currentDir = getClass().getProtectionDomain().getCodeSource().getLocation().toString();

			//String currentDir = "C:\\Users\\velin\\GoogleDrive\\DOCUMENTS\\1111111\\3_Year\\Team Project\\";
			currentDir = currentDir.replace("file:", "").split("\\.")[0] + "TeamBravo";
			System.out.println(currentDir);
			
			/****** TO BE MOVED BACK TO SEARCH CONTEXT ******/
			String list = getClass().getProtectionDomain().getCodeSource().getLocation().toString();
			list = list.replace("file:", "").split("\\.")[0] + "TeamBravo/stopword-list.txt";
			System.out.println("Search:" + list);
			ApplicationSetup.setProperty("stopwords.filename", list);
			// Comment the following two lines if you want to disable stemming (guessing words)
			ApplicationSetup.setProperty("termpipelines","Stopwords");
			System.err.println("Stemming has been disabled");
			/****** TO BE MOVED BACK TO SEARCH CONTEXT ******/

			File f = new File(currentDir);
			Gate.setGateHome(f);
			System.out.println("before gate.init");
			Gate.init();
			System.out.println("after gate.init");
			String pathToApplication = currentDir + "/" + "applicationState.xgapp";
			pipeline = (CorpusController) PersistenceManager.loadObjectFromFile(new File(pathToApplication));
			corpus = Factory.newCorpus("Tweet corpus");
			accept = true;
		} catch (GateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		//System.out.println("twirie init END");
	}

	public HashMap<String, ArrayList<String>> getNamedEntites(String tweet) {
		accept = false;
		//System.out.println("getNamedEntities: " + tweet);
		JSONObject ob = null;
		HashMap<String, ArrayList<String>> NEs = null;
		try {
			//System.out.println("before JSON");
			ob = new JSONObject(tweet);
			//System.out.println("after JSON");
			//System.out.println("JSON: " + ob);

			//System.out.println("trying");
			NEs = this.processString((String) ob.getString("text"));
			//System.out.println("tried");

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.err.println("Something wrong processing the string");
		}

		//System.out.println("END getNamedEntitties");
		accept = true;
		return NEs;
	}

	public synchronized HashMap<String, ArrayList<String>> processString(String s) throws InterruptedException {
		//System.out.println("process String");

		HashMap<String, ArrayList<String>> NEs = new HashMap<String, ArrayList<String>>();
		if (s.isEmpty())
			return null;
		Document doc = null;
		try {
			doc = Factory.newDocument(s);
			corpus.add(doc);

			pipeline.setCorpus(corpus);
			pipeline.execute();
			
			if (doc != null) {

				AnnotationSet annotations = doc.getAnnotations();
				for (String namedEntity : interestedNE) {
					NEs.put(namedEntity, new ArrayList<String>());
				}
				Iterator<Annotation> itr = annotations.iterator();
				while (itr.hasNext()) {
					Annotation a = itr.next();
					if (!interestedNE.contains(a.getType())) {
						continue;
					}
					ArrayList<String> NEsArray = NEs.get(a.getType());
					NEsArray.add(s.substring(a.getStartNode().getOffset().intValue(), a.getEndNode().getOffset().intValue()));
					NEs.put(a.getType(), NEsArray);
				}
			}
			
		} catch (ResourceInstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// That usually happens with cyrillic sentences or tweets containing only other (weird) characters, like one char, etc.  
			System.out.println("GATE was unable to execute this tweet:");
			System.out.println(s);
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} finally {
			corpus.clear();
			Factory.deleteResource(doc);
		}
		return NEs;
	}

	public boolean accept() {
		// TODO Auto-generated method stub
		return accept;
	}

}
