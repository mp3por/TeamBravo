<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Line Graph</title>
	
	<link href="<c:url value="/resources/css/base.css" />" rel="stylesheet">
	<script src="<c:url value="/resources/js/graphs/d3.min.js" />"></script>
	<script src="<c:url value="/resources/js/graphs/dimple.v2.1.0.min.js" />"></script>
</head>

<body>
	<div id="chartContainer">
		<h1>Hot Topics Line</h1>
		<script>    
			var svg1 = dimple.newSvg("#chartContainer", 590, 400);
			var src1 ="${pageContext.request.contextPath}/resources/tempData/tweet_example.tsv";
			d3.tsv(src1, function (data) {
			  var myChart = new dimple.chart(svg1, data);
			  myChart.setBounds(60, 30, 505, 305);
			  
			  var x = myChart.addCategoryAxis("x", "Month");
			  x.addOrderRule("Date");
			  
			  myChart.addMeasureAxis("y", "Tweets");
			  
			  var s = myChart.addSeries("Topic", dimple.plot.line);
			  s.interpolation = "cardinal";
			  
			  myChart.addLegend(60, 10, 500, 20, "right");
			  myChart.draw();
			});
		</script>
	</div>
</body>

</html>