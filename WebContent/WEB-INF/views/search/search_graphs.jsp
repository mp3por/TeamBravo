<%@ include file="/WEB-INF/include.jsp"%>
<html>
<head>

<link href="<c:url value="/resources/css/graphs.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">

<!-- jQuery -->
<script src="<c:url value="/resources/js/jquery-1.11.2.min.js" />"></script>

<!-- graphs -->
<script src="<c:url value="/resources/js/graphs/d3.min.js" />"></script>
<script src="<c:url value="/resources/js/graphs/c3.min.js" />"></script>
<script src="<c:url value="/resources/js/graphs/dimple.v2.1.0.min.js" />"></script>
<script src="<c:url value="/resources/js/graphs/d3.layout.cloud.js" />"></script>
<script src="<c:url value="/resources/js/graphs/graphHandler.js" />"></script>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Graphs for Term ${query}</title>

</head>

<body>

	<!--------------------------- BAR ------------------------------------>
	<header>
		<div id='logo'>
			<img src="/TeamBravo/resources/img/GreyRedMackintosh2.png"
				style="width: 30%;">
			<img src="/TeamBravo/resources/img/Terrier.png"
				style="width: 30%;">
		</div>
		<div id='cssmenu'>
			<ul id='naviMenu'>
				<li><a href='/TeamBravo/main/home'>
								<span>Home</span></a>
				</li>
				<li><a href='/TeamBravo/search/terrier/${query}'>
								<span>Results Page</span></a>
				</li>
				<li>
					<a href='/TeamBravo/search/graphs/${query}'>
								<span>Graphs for ${query}</span></a>
				</li>		
			</ul>
			<div id="search"></div>
		</div>
		
	</header>
	<!-- -------------------------------------------------------------- -->

	<!-- MAIN OUTLOOK TABLE  -->
	
	<div id="chart1"></div>
	<div id="chart2"></div>
	
	<script type="text/javascript">
		$(document).ready(function() {
			 var searchTerm = "${query}";
		 	getGraphsForSearch(searchTerm);
		 	
		 	$.ajax({
				url : '/TeamBravo/search/searchBox',
				success : function(data) {
					$("#search").html(data);
				}
			});
			});	
	</script>

	<!-- -------------------------------------------- -->