<%@ include file="/WEB-INF/include.jsp"%>

<script>    
	var svg1 = dimple.newSvg("#graphMonth", 520, 320);

	var topData = ${tweetsForMonth};
	//console.log(topData);
	  var myChart = new dimple.chart(svg1, topData);
	  myChart.setBounds(60, 30, 450, 190);
	  
	  var x = myChart.addCategoryAxis("x", "Day");
	  x.addOrderRule("Day");
	  
	  myChart.addMeasureAxis("y", "Tweets");
	  
	  var s = myChart.addSeries("Topic", dimple.plot.line);
	  s.interpolation = "linear"; //make interpolation "cardinal" for curved lines
	  
	  var legend = myChart.addLegend(25, 10, 400, 20, "right");
	  legend.fontSize = "14px"
	  myChart.assignColor(topData[0].Topic, "#134C7C",null,1);
	  myChart.assignColor(topData[1].Topic, "#C01E11",null,1);
	  myChart.assignColor(topData[2].Topic, "#92B710",null,1);
	  myChart.draw();
	
</script>
