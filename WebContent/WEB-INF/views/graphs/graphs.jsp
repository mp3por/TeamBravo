<%@ include file="/WEB-INF/include.jsp"%>
<html>
<head>
<link href="<c:url value="/resources/css/base.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/tweets.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/css/styles.css" />"
	rel="stylesheet">


<script src="<c:url value="/resources/js/jquery-1.11.2.min.js" />"></script>
<script src="<c:url value="/resources/js/graphs/d3.min.js" />"></script>
<script src="<c:url value="/resources/js/graphs/d3.layout.cloud.js" />"></script>
<script src="https://maps.googleapis.com/maps/api/js?sensor=false&region=GB"></script>
<script src="<c:url value="/resources/js/graphs/d3.min.js" />"></script>
<script src="<c:url value="/resources/js/graphs/dimple.v2.1.0.min.js" />"></script>
<link href="<c:url value="/resources/css/c3CSS.css" />" rel="stylesheet">
<script src="<c:url value="/resources/js/graphs/c3.min.js" />"></script>

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
				<li><a href='#'><span>Home</span></a></li>
				<li><a href='#'><span>Tweets</span></a></li>
				<li><a href='#'><span>Map</span></a></li>
				<li class='last active'><a href='#'><span>Graphs</span></a></li>
			</ul>
			<div id="search"></div>
		</div>
	</header>
	<!-- -------------------------------------------------------------- -->

	<!-- MAIN OUTLOOK TABLE  -->
	<div class="row cf">

		<div class="main column">
			<h3>Hot Topic Trends Over Past Week</h3>
			<div id="graphWeek"></div>
		</div>

		<div class="main column">
			<h3>Hot Topic Trends Over Past Month</h3>
			<div id="graphMonth"></div>
		</div>

	</div>

	<div class="row cf">

		<div class="main column">
			<h3>This Weeks Hot Topics</h3>
			<div id="chart"></div>
		</div>

		<div class="main column">
			<h3>Top Hash Tags</h3>
			<div id="wordCloud"></div>
		</div>

	</div>


	<!-- -------------------------------------------- -->

</body>

<script type="text/javascript">
	$(document).ready(function() {

		extract();

		function extract() {
			graphInit();
			getTopicsForWeek();
			getTopicsForMonth();
			getSearchBox();
			getPieChart();
			getWordCloud();
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

		function getTopicsForWeek() {
			$.ajax({
				url : '/TeamBravo/graphs/graphWeek',
				//async : false, //Quick fix, remove later
				success : function(data) {
					$("#graphWeek").html(data);
				}
			});
		}

		function getTopicsForMonth() {
			$.ajax({
				url : '/TeamBravo/graphs/graphMonth',
				//async : false, //Quick fix remove later
				success : function(data) {
					$("#graphMonth").html(data);
				}
			});
		}
		
		function getPieChart() {
			$.ajax({
				url : '/TeamBravo/graphs/pieChart',
				//async: false, //Quick fix remove later
				success : function(data) {
					$("#chart").html(data);
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
		
		function getWordCloud() {
			$.ajax({
				url : '/TeamBravo/graphs/wordCloud',
				//async: false, //Quick fix remove later
				success : function(data) {
					$("#wordCloud").html(data);
				}
			});
		}
	});
</script>
</html>
