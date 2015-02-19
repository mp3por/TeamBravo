<%@ include file="/WEB-INF/include.jsp"%>
<script src="<c:url value="/resources/js/graphs/dimple.v2.1.0.min.js" />"></script>
<script>
	//Graph Week
	var svg = dimple.newSvg("#graphWeek", 520, 320);
	var src = ${tweetsForWeek};
	var colour1 = new dimple.color("#134C7C");
	var colour2 = new dimple.color("#C01E11");
	var colour3 = new dimple.color("#92B710");
	
	colour1.opacity = 1;
	colour2.opacity = 1;
	colour3.opacity = 1;
	
	var barChart = new dimple.chart(svg, src);
	var x = barChart.addCategoryAxis("x", [ "Day", "Topic" ]);
	x.addOrderRule("Day");
	var myAxis = barChart.addMeasureAxis("y", "Tweets");
	barChart.addSeries("Topic", dimple.plot.bar);
	var myLegend = barChart.addLegend(25, 10, 400, 20, "right");
	myLegend.fontSize = "14px"
	barChart.defaultColors = [
		colour1,
		colour2,
		colour3
	];
	barChart.width = 450;
	barChart.height = 220;
	barChart.draw();
	myAxis.gridlineShapes.selectAll().attr("stroke", "#FFFFFF");
</script>
