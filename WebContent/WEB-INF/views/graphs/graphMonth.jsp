<%@ include file="/WEB-INF/include.jsp"%>

<script>    
	var svg1 = dimple.newSvg("#graphMonth", 520, 320);

	var topData = ${tweetsForMonth};
	//console.log(topData);
	  var myChart = new dimple.chart(svg1, topData);
	  myChart.setBounds(60, 30, 400, 200);
	  
	  var x = myChart.addCategoryAxis("x", "Day");
	  x.addOrderRule("Day");
	  
	  myChart.addMeasureAxis("y", "Tweets");
	  
	  var s = myChart.addSeries("Topic", dimple.plot.line);
	  s.interpolation = "linear"; //make interpolation "cardinal" for curved lines
	  
	  myChart.addLegend(50, 10, 400, 20, "right");
	  myChart.draw();
	
</script>
