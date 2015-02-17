<%@ include file="/WEB-INF/include.jsp"%>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Bar Chart</title>
	
	<link href="<c:url value="/resources/css/base.css" />" rel="stylesheet">
	<script src="<c:url value="/resources/js/graphs/d3.min.js" />"></script>
	<script src="<c:url value="/resources/js/graphs/dimple.v2.1.0.min.js" />"></script>

</head>

<body>
	<div id="bar">
		<h1>Tweets by Week</h1>
		<script>
			var svg = dimple.newSvg("#bar", 800, 500);
			var src="${pageContext.request.contextPath}/resources/tempData/tweet_example2.tsv";
		    d3.tsv(src, function (error, data) {
			
		      //data = dimple.filterData(data, "Owner", ["Aperture", "Black Mesa"])
		      var barChart = new dimple.chart(svg, data);
			  
		      barChart.addCategoryAxis("x", ["Day", "Topic"]); //Taking out Topic and leaving ["Day"] here creates a stacked bar chart
		      barChart.addMeasureAxis("y", "Tweets");
		      barChart.addSeries("Topic", dimple.plot.bar);
		      barChart.addLegend(100, 10, 510, 20, "right");
			  barChart.width = 600;
			  barChart.height = 400;
		      barChart.draw();
		    });
	    </script>
	</div>
</body>

</html>