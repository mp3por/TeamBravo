<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
  <title>Terrier Search</title>
</head>
<body>

/* Import all the packages needed */
<%@ page import="org.terrier.utility.ApplicationSetup, org.springframework.data.mongodb.core.MongoOperations,
				org.springframework.data.mongodb.core.MongoTemplate, org.terrier.matching.ResultSet, com.mongodb.DBCursor, 
				com.mongodb.MongoClient, glasgow.teamproject.teamB.Search.TweetsIndexer, glasgow.teamproject.teamB.Search.TweetsRetriver, 
				java.net.UnknownHostException"%>

<%
	/* Configure Terrier here */
	ApplicationSetup.setProperty("terrier.home", "/Users/vincentfung13/Development/TP3/terrier-4.0-win");
	ApplicationSetup.setProperty("terrier.etc", "/Users/vincentfung13/Development/TP3/terrier-4.0-win/etc");
	ApplicationSetup.setProperty("stopwords.filename", "/Users/vincentfung13/Development/TP3/terrier-4.0-win/share/stopwords.txt");
	
	/* Specify the database here */
	final String DB_NAME = "tweetsTest";
	final String TWEETS_COLLECTION = "tweets";
	final String MONGO_HOST = "localhost";
	final int MONGO_PORT = 27017;
%>

<%
	com.mongodb.MongoClient mongo;
	try {
		String query = "Taylor Swift";
		mongo = new MongoClient(MONGO_HOST, MONGO_PORT);
		MongoOperations mongoOps = new MongoTemplate(mongo, DB_NAME);
	
		TweetsIndexer indexer = new TweetsIndexer(mongoOps);
		indexer.indexTweets();
	
		TweetsRetriver retriver = new TweetsRetriver(indexer.getIndex(), query);
		retriver.runQuery();
		ResultSet result = retriver.getResult();
		
		result.sort();
		int[] resultDocnos = result.getDocids();
		
		out.println("Returned " + resultDocnos.length + " tweets shown as follow: ");
		
		DBCursor cursor = mongoOps.getCollection("tweets").find();
		cursor.next();
		int j = 0;
		for(int i = 0; i < resultDocnos.length; i++){
			while(j != resultDocnos[i]){
				cursor.next();
				j++;
			}
			out.print(cursor.curr().get("text").toString());
		}
	}
	catch (UnknownHostException e) {
		e.printStackTrace();
	}

%>
</body>
</html>