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
<script src = "https://maps.googleapis.com/maps/api/js?sensor=false&region=GB"></script>

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
	
		<div class="main column">
			<h3>Topics by Week</h3>
			<div id="graphWeek"></div>
		</div>
		
		<div class="main column">
			<h3>Topics by Month</h3>
			<div id="graphMonth"></div>
		</div>
		
	</div>
	
	<div class="row cf">
	
		<div class="main column">
			<h3>Top Hot Topics</h3>
			<div id="pieChart"></div>
		</div>
		
		<div class="main column">
			<h3>Word Cloud</h3>
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
			getTopicsForWeek();
			getTopicsForMonth();
			getSearchBox();
			getPieChart();
			getWordCloud();
		}
		;

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
					$("#pieChart").html(data);
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
				success : function(data) {
					$("#wordCloud").html(data);
				}
			});
		}
	});
</script>
</html>
