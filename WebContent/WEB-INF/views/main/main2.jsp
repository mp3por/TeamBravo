<%@ include file="/WEB-INF/include.jsp"%>
<html>
<head>

<link href="<c:url value="/resources/css/graphs.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/tweets.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/maps.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/c3CSS.css" />" rel="stylesheet">

<!-- jQuery -->
<script src="<c:url value="/resources/js/jquery-1.11.2.min.js" />"></script>

<!-- maps -->
<script src="https://maps.googleapis.com/maps/api/js?sensor=false&region=GB"></script>
<script src="<c:url value="/resources/js/maps/markerclusterer.js" />"></script>

<!-- graphs -->
<script src="<c:url value="/resources/js/graphs/d3.min.js" />"></script>
<script src="<c:url value="/resources/js/graphs/c3.min.js" />"></script>
<script src="<c:url value="/resources/js/graphs/dimple.v2.1.0.min.js" />"></script>
<script src="<c:url value="/resources/js/graphs/d3.layout.cloud.js" />"></script>


<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

<!-- bootstrap -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Home</title>
</head>
<body>

	<!--------------------------- BAR ------------------------------------>
	<header>
		<div id='logo'>
			<img src="/TeamBravo/resources/img/GreyRedMackintosh2.png" style="width: 30%;">
		</div>
		<div id='cssmenu'>
			<ul id='naviMenu'>
				<li class='active'>
					<a href='#'><span>Home</span></a>
				</li>
				<li>
					<a href='#'><span>Tweets</span></a>
				</li>
				<li>
					<a href='#'><span>Map</span></a>
				</li>
				<li class='last'>
					<a href='#'><span>Graphs</span></a>
				</li>
			</ul>
			<div id="search"></div>
		</div>
	</header>
	<!-- -------------------------------------------------------------- -->

	<!-- MAIN OUTLOOK TABLE  -->

	<div class="container-fluid">
		<div class="row">
			<div class="col-md-6" id="holder_map">
				<h3 class="box">Map</h3>
				<div id="map-container" class="box">
					<div id="map"></div>
				</div>
			</div>
			<div class="col-md-6 " id="holder_tweet_wall">
				<h3 class="box">Tweets</h3>
				<div id="tweetwall" class="box"></div>
			</div>
		</div>
	</div>

	<!-- -------------------------------------------- -->

</body>
<footer>
	footer
</footer>

<script type="text/javascript">
	$(document).ready(function() {
		console.log("ready!");

		extract();

		function extract() {
			//graphInit();
			//getTweetWall();
			getMaps();
			//getSearchBox();
			//getTopicsForWeek();
			//getTopicsForMonth();
			//getPieChart();
			//getWordCloud();
		}
		;

		function graphInit() {
			$.ajax({
				url : '/TeamBravo/graphs/graphInit',
				async : false, //Quick fix, remove later
				success : function(data) {
					console.log("Graphs Initialised");
				}
			});
		}

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
				url : '/TeamBravo/main/searchBox',
				success : function(data) {
					$("#search").html(data);
				}
			});
		}

		function getTopicsForWeek() {
			$.ajax({
				url : '/TeamBravo/graphs/graphWeek',
				success : function(data) {
					$("#graphWeek").html(data);
				}
			});
		}

		function getTopicsForMonth() {
			$.ajax({
				url : '/TeamBravo/graphs/graphMonth',
				success : function(data) {
					$("#graphMonth").html(data);
				}
			});
		}

		function getPieChart() {
			$.ajax({
				url : '/TeamBravo/graphs/pieChart',
				success : function(data) {
					$("#chart").html(data);
				}
			});
		}

		function getWordCloud() {
			$.ajax({
				url : '/TeamBravo/graphs/wordCloud',
				success : function(data) {
					$("#wordCloud").html(data);
				}
			});
		}
	});
</script>
</html>
