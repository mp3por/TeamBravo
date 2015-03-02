package glasgow.teamproject.teamB.Util;

import java.io.IOException;
import java.util.Observable;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import twitter4j.FilterQuery;
import twitter4j.RawStreamListener;
import twitter4j.TwitterStream;

@Component
@Scope("singleton")
public class StreamReaderService extends Observable {

	@Autowired
	private TwitterStreamBuilderUtil twitterStreamBuilder;

	@Autowired
	private ProjectProperties projectProperties;
	
	private StreamReaderService serv;

	private TwitterStream stream;
	
	
	/**
	 * Just for testings
	 * */
//	public StreamReaderService(ProjectProperties pr , TwitterStreamBuilderUtil ts) {
//		this.twitterStreamBuilder = ts;
//		this.projectProperties = pr;
//		try {
//			run();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	@PostConstruct
	public void run() throws IOException {
		serv = this;
		System.out.println("\n\n\n");
		System.out.println("RUNNIIINNGGGGG");
		
		//readTwitterFeed();

		System.out.println("\n\n\n");
	}

	/**
	 * Just for testing
	 * */
	public static void main(String[] args) throws IOException {
		//readTwitterFeed();
	}

	public void readTwitterFeed() throws IOException {
		stream = this.twitterStreamBuilder.getStream();
		
		
		RawStreamListener raw = new RawStreamListener() {

			@Override
			public void onException(Exception ex) {
				System.out.println("error");
			}

			@Override
			public void onMessage(String rawString) {
				System.out.println("twitterStreamer: " + rawString);
				if (rawString.isEmpty()) return;
				serv.setChanged();
				serv.notifyObservers(rawString);
			}

//			private String manipulateTweetBeforeSaving(String rawString) {
//				HashMap<String, ArrayList<String>> NEs = namedEntityParser.getNamedEntites(rawString);
//				if (!NEs.isEmpty()) {
//					StringBuilder sb = new StringBuilder(rawString);
//					sb.setLength(Math.max(sb.length() - 1, 0));
//					for (String s : NEs.keySet()) {
//						sb.append(",");
//						sb.append("\"" + s + "\":\"" + NEs.get(s) + "\"");
//					}
//					sb.append("}");
//					rawString = sb.toString();
//				}
//				return rawString;
//			}
		};

		/*
		 * Unneeded. Kept just for reference
		 * */
		/*		StatusListener listener = new StatusListener() {

			@Override
			public void onException(Exception ex) {
				// TODO Auto-generated method stub
				System.out.println("Exception occured:" + ex.getMessage());
				ex.printStackTrace();
			}

			@Override
			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
				// TODO Auto-generated method stub
				System.out.println("Track limitation notice for " + numberOfLimitedStatuses);

			}

			@Override
			public void onStatus(Status status) {
				// TODO Auto-generated method stub
				String message = "Tweet:" + status.getText() + "\tLoc:" + status.getGeoLocation();
				//System.out.println(message);

			}

			@Override
			public void onStallWarning(StallWarning warning) {
				// TODO Auto-generated method stub
				System.out.println("Stall worning");

			}

			@Override
			public void onScrubGeo(long userId, long upToStatusId) {
				// TODO Auto-generated method stub
				System.out.println("Scrub geo with " + userId + ":" + upToStatusId);
			}

			@Override
			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
				// TODO Auto-generated method stub
				System.out.println("Status deletion notice");
			}
		};*/

		double[][] locations = new double[][] { { -4.508147d, 55.812753d }, { -4.037108d, 55.965241d } };

		stream.addListener(raw);

		FilterQuery fq = new FilterQuery();
		fq.locations(locations);

		stream.filter(fq);
	}
}
