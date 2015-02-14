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
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import org.springframework.stereotype.Component;

@Component
public class TwitIE implements NamedEntityExtractor {
	private static HashSet<String> interestedNE = new HashSet<String>(); 
	public final HashSet<String> defaultNE = ProjectProperties.defaultNE;

	private static Corpus corpus;
	private static CorpusController pipeline;

	public void addNE (String s) {
		interestedNE.add(s);
	}

	public synchronized HashMap<String, ArrayList<String>> processString (String s) throws InterruptedException {
		
		HashMap<String, ArrayList<String>> NEs = new HashMap<String, ArrayList<String>>();
		if (s.isEmpty()) return null;
		Document doc = null;
		try {
			doc = Factory.newDocument(s);
			corpus.add(doc);

			pipeline.setCorpus(corpus);
			pipeline.execute();
		} catch (ResourceInstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// That usually happens with cyrillic sentences or tweets containing only other (weird) characters, like one char, etc.  
			System.out.println("GATE was unable to execute this tweet:");
			System.out.println(s);
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		finally {
			corpus.remove(doc);
			corpus.clear();
			corpus.cleanup();
		}

		if (doc != null) {

			AnnotationSet annotations = doc.getAnnotations();
			for (String namedEntity: interestedNE) {
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
		return NEs;
	}


	public void init() {
		try {
			if (interestedNE.isEmpty()) {
				interestedNE.addAll(defaultNE);
			}

			// Only god knows how it works
			String currentDir = getClass().getProtectionDomain().getCodeSource().getLocation().toString();
			currentDir = currentDir.replace("file:", "").split("\\.")[0] + "TeamBravo";
			System.out.println(currentDir);

			File f = new File(currentDir);
			Gate.setGateHome(f);

			Gate.init();
			String pathToApplication =currentDir+"/"+"applicationState.xgapp";
			pipeline = (CorpusController) PersistenceManager.loadObjectFromFile(new File(pathToApplication));
			corpus = Factory.newCorpus("Tweet corpus");
		} catch (GateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}		
	}

}
