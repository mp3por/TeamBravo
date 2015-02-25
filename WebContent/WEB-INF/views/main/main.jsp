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
<script src="<c:url value="/resources/js/graphs/graphHandler.js" />"></script>

<title>Home</title>
</head>
<body>

	<!--------------------------- BAR ------------------------------------>
	<header>
		<div id='logo'>
			<img src="/TeamBravo/resources/img/GreyRedMackintosh2.png"
				style="width: 30%;">
		</div>
		<div id='cssmenu'>
			<ul id='naviMenu'>
				<li class='active'><a href='#'><span>Home</span></a></li>
				<li><a href='#'><span>Tweets</span></a></li>
				<li><a href='#'><span>Map</span></a></li>
				<li class='last'><a href='#'><span>Graphs</span></a></li>
			</ul>
			<div id="search"></div>
		</div>
	</header>
	<!-- -------------------------------------------------------------- -->

	<!-- MAIN OUTLOOK TABLE  -->
	<div class="row cf">

		<div class="mapHolder column">
			<h3>Map</h3>
			<div id="map-container">
				<div id="map"></div>
			</div>
		</div>

		<div class="main column">
			<h3>Tweets</h3>
			<div id="tweetwall"></div>
		</div>

	</div>

	<div class="row cf">

		<div class="main column">
			<h3>This Week</h3>
			<div id="graphWeek"></div>
		</div>

		<div class="main column">
			<h3>This Month</h3>
			<div id="graphMonth"></div>
		</div>

	</div>

	<div class="row cf">

		<div class="main column">
			<h3>Hot Topics By Percentage</h3>
			<div id="chart"></div>
		</div>

		<div class="main column">
			<h3>Top Mentions</h3>
			<div id="wordCloud"></div>
		</div>

	</div>


	<!-- -------------------------------------------- -->

</body>

<script type="text/javascript">

	$(document).ready(function() {
		console.log("ready!");

		extract();

		function extract() {
			//getTweetWall();
			//getMaps();
			//getSearchBox();
		}
		;

		function getTweetWall() {
			$.ajax({
				url : '/TeamBravo/tweets/all',
				success : function(data) {
					$("#tweetwall").html(data);
				}
			});
		}

		function getMaps() {
			$.ajax({
				url : '/TeamBravo/maps/test',
				success : function(data) {
					$("#map").html(data);
				}
			});
		}

		function getSearchBox() {
			$.ajax({
				url : '/TeamBravo/search/searchBox',
				success : function(data) {
					$("#search").html(data);
				}
			});
		}
	});
</script>
</html>
