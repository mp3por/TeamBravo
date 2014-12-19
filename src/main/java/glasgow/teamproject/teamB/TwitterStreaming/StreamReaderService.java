package glasgow.teamproject.teamB.TwitterStreaming;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;

public class StreamReaderService {

	/**
	 * Make it so it checks till when do we have the tweets in the db, downloads
	 * them and then starts listening...
	 * */
	// @PostConstruct
	// same as init-method in .xml but with annotations
	public void run() throws IOException {
		System.out.println("\n\n\n");

		readTwitterFeed();

		System.out.println("\n\n\n");
	}

	public static void main(String[] args) throws IOException {
		readTwitterFeed();
	}

	public static void readTwitterFeed() throws IOException {
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
				System.out.println("Track limitation notice for "
						+ numberOfLimitedStatuses);

			}

			@Override
			public void onStatus(Status status) {
				// TODO Auto-generated method stub
				String message = "Tweet:" + status.getText() + "\tLoc:"
						+ status.getGeoLocation();
				System.out.println(message);
			}

			@Override
			public void onStallWarning(StallWarning warning) {
				// TODO Auto-generated method stub
				System.out.println("Stall worning");

			}

			@Override
			public void onScrubGeo(long userId, long upToStatusId) {
				// TODO Auto-generated method stub
				System.out.println("Scrub geo with " + userId + ":"
						+ upToStatusId);
			}

			@Override
			public void onDeletionNotice(
					StatusDeletionNotice statusDeletionNotice) {
				// TODO Auto-generated method stub
				System.out.println("Status deletion notice");
			}
		};

		FilterQuery qry = new FilterQuery();

		double[][] locations = new double[][] { { 55.812753d, -4.508147d },
				{ 55.965241d, -4.037108d } };

		qry.locations(locations);

		stream.addListener(listener);
		String[] keywordsArray = { "Glasgow" };

		FilterQuery fq = new FilterQuery(0, null, keywordsArray, locations);

		stream.filter(fq);
	}
}
