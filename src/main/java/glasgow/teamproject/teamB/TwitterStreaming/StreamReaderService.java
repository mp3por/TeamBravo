package glasgow.teamproject.teamB.TwitterStreaming;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;


public class StreamReaderService {
	
	/**
	 * Make it so it checks till when do we have the tweets in the db, downloads them and then starts listening...
	 * */
	//@PostConstruct
	// same as init-method in .xml but with annotations
	public void run() {
		System.out.println("\n\n\n");
		System.out.println("PARTY in StreamReaderService");
		System.out.println("\n\n\n");
	}
	
	/*public void readTwitterFeed(){
		TwitterStream stream = TwitterStreamBuilderUtil.getStream();
		
		StatusListener listener = new StatusListener() {
			
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
				System.out.println("Tweet:" + status.getText());
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
		};
		
		FilterQuery qry = new FilterQuery();
		
		double[][] locations = {{55.812753, -4.508147},{55.965241, -4.037108}};
		
		qry.locations(locations);
		
		
		stream.addListener(listener);
		stream.filter(qry);
	}*/
}
