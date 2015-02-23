<%@ include file="/WEB-INF/include.jsp"%>
<html>
<head>
<link href="<c:url value="/resources/css/graphs.css" />" rel="stylesheet">

<link href="<c:url value="/resources/css/tweets.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/css/styles.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/css/maps.css" />" rel="stylesheet">

<script src="<c:url value="/resources/js/jquery-1.11.2.min.js" />"></script>
<script src="<c:url value="/resources/js/graphs/d3.min.js" />"></script>

<script
	src="https://maps.googleapis.com/maps/api/js?sensor=false&region=GB"></script>
<script src="<c:url value="/resources/js/maps/markerclusterer.js" />"></script>

<link href="<c:url value="/resources/css/c3CSS.css" />" rel="stylesheet">
<script src="<c:url value="/resources/js/graphs/c3.min.js" />"></script>
<script src="<c:url value="/resources/js/graphs/dimple.v2.1.0.min.js" />"></script>
<script src="<c:url value="/resources/js/graphs/d3.layout.cloud.js" />"></script>

<title>Search Results Page</title>
</head>
<body>

	<!--------------------------- BAR ------------------------------------>
	<header>
		<div id='logo'>
			<img src="/TeamBravo/resources/img/GreyRedMackintosh2.png"
				style="width: 30%;">
			<img src = "/TeamBravo/resources/img/Terrier.png"
				style = "width: 30%;">
		</div>
		
		<div id='cssmenu'>
			<ul id='naviMenu'>
				<li class='active'><a href='http://localhost:8080/TeamBravo/main/home'>
														<span>Return to Home Page</span></a></li>
			</ul>
			<div id="search"></div>
		</div>
	</header>
	<!-- -------------------------------------------------------------- -->

	<!-- MAIN OUTLOOK TABLE  -->
	
	<!--------------------------- RENDER MAP ------------------------------------>
	<div class="row cf">
		<div class="mapHolder column">
			<h3>Map for Tweets with "${query}"</h3>
			<div id="map-container">
				<div id="map"></div>
			</div>
		</div>
	</div>
	
	<!--------------------------- RENDER TWEET WALL ------------------------------------>
	<div class="row cf">
		<div class="tweetwall column">
			<h3>Tweets with "${query}"</h3>
			<div id="tweetwall-container">
				<div id="tweetwall"></div>
			</div>
		</div>		
	</div>

	<!--------------------------- RENDER GRAPHS ------------------------------------>
	<div class="row cf">
		<div class="main column">
			<h3>This Week</h3>
			<div id="graphWeek"></div>
		</div>
	</div>
	
	<div class="row cf">
		<div class="main column">
			<h3>This Month</h3>
			<div id="graphMonth"></div>
		</div>
	</div>


	<!-- -------------------------------------------- -->

</body>

</html>
