<%@ include file="/WEB-INF/include.jsp"%>

		<script>
		
	//Graph Week
			var svg = dimple.newSvg("#graphWeek", 520, 320);
			var src= ${tweetsForWeek};
			//console.log(src);

		      var barChart = new dimple.chart(svg, src);
		      var x = barChart.addCategoryAxis("x", ["Day", "Topic"]);
			  x.addOrderRule("Day");
		      //barChart.addCategoryAxis("x", ["Day", "Topic"]); //Taking out Topic and leaving ["Day"] here creates a stacked bar chart
		      barChart.addMeasureAxis("y", "Tweets");
		      barChart.addSeries("Topic", dimple.plot.bar);
		      barChart.addLegend(50, 10, 400, 20, "right");
			  barChart.width = 450;
			  barChart.height = 220;
		      barChart.draw();
	
	    </script>
