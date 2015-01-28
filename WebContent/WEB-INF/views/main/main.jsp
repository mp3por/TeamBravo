<%@ include file="/WEB-INF/include.jsp"%>
<html>
<head>
<link href="<c:url value="/resources/css/base.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/tweets.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">


<script src="<c:url value="/resources/js/jquery-1.11.2.min.js" />"></script>
<script src="<c:url value="/resources/js/graphs/d3.min.js" />"></script>
<script src="<c:url value="/resources/js/graphs/d3.layout.cloud.js" />"></script>
<script src = "http://maps.googleapis.com/maps/api/js?sensor=false&region=GB"></script>

<title>Home</title>
</head>
<body>

	<!--------------------------- BAR ------------------------------------>
	<header>
		<div id='cssmenu'>
			<ul>
				<li class='active'>
					<a href='#'><span>Search</span></a>
				</li>
				<li>
					<a href='#'><span>TweetWall</span></a>
				</li>
				<li>
					<a href='#'><span>Map</span></a>
				</li>
				<li class='last'>
					<a href='#'><span>Graphs</span></a>
				</li>
			</ul>
		</div>
	</header>
	<!-- -------------------------------------------------------------- -->

	<!-- MAIN OUTLOOK TABLE  -->
	
	<div class="main left">
		<div class="bordered">
			<h3>Search</h3>
			<div id="search"></div>
		</div>
	</div>
	<div class="main right">
		<div class="bordered">
			<h3>TweetWall</h3>
			<div id="tweetwall"></div>
		</div>
	</div>
	<div class="main left">
		<div class="bordered">
			<h3>Map</h3>
			<div id="map"></div>
		</div>
	</div>
	<div class="main right">
		<div class="bordered">
			<h3>Graphs</h3>
			<div id="graphs"></div>
		</div>
	</div>

	<!-- -------------------------------------------- -->


	<footer> OMF FOOTER </footer>
</body>

<script type="text/javascript">
	$(document).ready(function() {
		console.log("ready!");

		extract();

		function extract() {
			getTweetWall();
			getMaps();
			getSearchBox();
		}
		;

		function getTweetWall() {
			$.ajax({
				url : '/TeamBravo/tweets/allJustTweets',
				success : function(data) {
					$("#tweetwall").html(data);
				}
			});
		}
		
		function getMaps() {
			$.ajax({
				url : '/TeamBravo/maps/ajaxAll',
				success : function(data) {
					$("#map").html(data);
				}
			});
		}
		
		function getSearchBox() {
			$.ajax({
				url : '/TeamBravo/main/searchBox',
				success : function(data) {
					$("#search").html(data);
				}
			});
		}
	});
</script>
</html>
