<%@ include file="/WEB-INF/include.jsp"%>
<html>
<head>
<link href="<c:url value="/resources/css/styles.css" />"
	rel="stylesheet">


<script src="<c:url value="/resources/js/jquery-1.11.2.min.js" />"></script>
<script src="<c:url value="/resources/js/graphs/d3.min.js" />"></script>

<script src="<c:url value="/resources/js/graphs/dimple.v2.1.0.min.js" />"></script>
<script src="<c:url value="/resources/js/graphs/graphHandler.js" />"></script>


<title>Home/SearchTopic</title>
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
	
		<div class="graphWeek column">
			<h3>Topic by Week</h3>
			<div id="chart1" style="width: 45%;"></div>
		</div>
		
		<div class="main column">
			<h3>Topic by Month</h3>
			<div id="chart2" style="width: 45%;"></div>
		</div>
		
	</div>

<script type="text/javascript">
	$(document).ready(function() {
		var searchTerm = "ygytft"
		getGraphsForSearch(searchTerm);
	});
</script>

</body>

</html>
