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
		</div>
		<div class="container-fluid">
		<div class="row" id="top-menu">
		
		<div id='cssmenu' class="col-lg-4 col-lg-offset-2">
			<ul id='naviMenu'>
				<li>
				<a href='/TeamBravo/search/terrier/${query}'><span>Results</span></a>
				<li class='active'>
				<a href='/TeamBravo/search/graphs/${query}'><span>Graphs</span></a>
				<li class='last'><a href='#about-us'><span>About Us</span></a></li>
			</ul>
		</div>
		<div class="col-lg-4 col-lg-offset-1" id="search-padding">
			<div id="search" ></div>
			</div>
		</div> <!-- row -->
		</div>
	</header>
	<!-- -------------------------------------------------------------- -->

	<!-- MAIN OUTLOOK TABLE  -->
	
	<div id="chart1"></div>
	<div id="chart2"></div>
	
	<footer> 
	
    <div class="about-us" id="about-us">
	<legend>About us:</legend>
	<p><img src="<c:url value="/resources/img/uni_glasgow_logo.png" />" width="300px"><br/>
	<h4>"A Dasboard for Monitoring Tweets in Glasgow" is a Team Project made by level 3 students at University of Glasgow. Team members:</h4>
	<ul>
	<li>Prapaipim Junhavittaya</li>
	<li>Paulius Peciura</li>
	<li>Zijian Feng</li>
	<li>Steven McGuckin</li>
	<li>Velin Kerkov</li>
	</ul>
	<h4>Project supervisors:</h4>
	<ul>
	<li>Dr Iadh Ounis</li>
	<li>Richard McCreadie</li>
	</ul>
	<br/>
	<a href='#'><span>Back to the top</span></a></li>
	</div>
</footer>
	
	<script type="text/javascript">
		$(document).ready(function() {
			var searchTerm = "${query}";
			console.log("getting graph");
			getGraphsForSearch(searchTerm);
		});	
	</script>

	<!-- -------------------------------------------- -->