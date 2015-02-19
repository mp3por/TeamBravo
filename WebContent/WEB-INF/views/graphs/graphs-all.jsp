<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>D3 Graphs</title>

	<link href="<c:url value="/resources/css/base.css" />" rel="stylesheet">
	<link href="<c:url value="/resources/css/graphs/c3CSS.css" />" rel="stylesheet">
	<script src="<c:url value="/resources/js/graphs/d3.min.js" />"></script>
	<script src="<c:url value="/resources/js/graphs/dimple.v2.1.0.min.js" />"></script>
	<script src="<c:url value="/resources/js/graphs/c3.min.js" />"></script>
	<script src="<c:url value="/resources/js/graphs/d3.layout.cloud.js" />"></script>
	
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
	
	<h1>Hot Topics Pie</h1>
	<div id="chart">
		<script>
			var chart = c3.generate({
			    data: {
			        columns: [
			            ["SNP", 0.2, 0.2, 0.2, 0.2, 0.2, 0.4, 0.3, 0.2, 0.2, 0.1, 0.2, 0.2, 0.1, 0.1, 0.2, 0.4, 0.4, 0.3, 0.3, 0.3, 0.2, 0.4, 0.2, 0.5, 0.2, 0.2, 0.4, 0.2, 0.2, 0.2, 0.2, 0.4, 0.1, 0.2, 0.2, 0.2, 0.2, 0.1, 0.2, 0.2, 0.3, 0.3, 0.2, 0.6, 0.4, 0.3, 0.2, 0.2, 0.2, 0.2],
			            ["IndyRef", 1.4, 1.5, 1.5, 1.3, 1.5, 1.3, 1.6, 1.0, 1.3, 1.4, 1.0, 1.5, 1.0, 1.4, 1.3, 1.4, 1.5, 1.0, 1.5, 1.1, 1.8, 1.3, 1.5, 1.2, 1.3, 1.4, 1.4, 1.7, 1.5, 1.0, 1.1, 1.0, 1.2, 1.6, 1.5, 1.6, 1.5, 1.3, 1.3, 1.3, 1.2, 1.4, 1.2, 1.0, 1.3, 1.2, 1.3, 1.3, 1.1, 1.3],
			            ["Nicola Sturgeon", 2.5, 1.9, 2.1, 1.8, 2.2, 2.1, 1.7, 1.8, 1.8, 2.5, 2.0, 1.9, 2.1, 2.0, 2.4, 2.3, 1.8, 2.2, 2.3, 1.5, 2.3, 2.0, 2.0, 1.8, 2.1, 1.8, 1.8, 1.8, 2.1, 1.6, 1.9, 2.0, 2.2, 1.5, 1.4, 2.3, 2.4, 1.8, 1.8, 2.1, 2.4, 2.3, 1.9, 2.3, 2.5, 2.3, 1.9, 2.0, 2.3, 1.8],
			        ],
			        type : 'pie',
			    }
			});
		</script>
	</div>
	
	<div id="wordCloud">
		<h1>Clickable Word Cloud</h1>
		<script>
			var frequency_list = [{"text":"Ibrox","size":50,"URL":"http://www.rangers.co.uk/"},{"text":"Hydro","size":110,"URL":"http://www.thessehydro.com/default.aspx"},{"text":"Parkhead","size":80,"URL":"http://www.celticfc.net/mainindex"},{"text":"Finnieston","size":30,"URL":"http://www.thefinniestonbar.com/"},{"text":"Celtic","size":60,"URL":"http://www.celticfc.net/mainindex"},{"text":"Subway","size":110,"URL":"http://www.spt.co.uk/subway/"},{"text":"EMA","size":40,"URL":"http://tv.mtvema.com/"},{"text":"Still Game","size":70,"URL":"http://www.bbc.co.uk/programmes/b006qgl8"}];
			
			var color = d3.scale.linear()
			        .domain([0,1,2,3,4,5,6,10,15,20,100])
			        .range(["#ddd", "#ccc", "#bbb", "#aaa", "#999", "#888", "#777", "#666", "#555", "#444", "#333", "#222"]);
			
			d3.layout.cloud().size([400, 400]) //Change back to 400,400 if they get cut off
			        .words(frequency_list)
			        .rotate(0)
					.text(function(d) { return d.text; })
			        .fontSize(function(d) { return d.size; })
			        .on("end", draw)
			        .start();
			
			function draw(words) {
			    d3.select("body").append("svg")
			            .attr("width", 600)
			            .attr("height", 400)
			            .attr("class", "wordcloud")
			            .append("g")
			            // without the transform, words would get cut off to the left and top, they would
			            // appear outside of the SVG area
			            .attr("transform", "translate(150,200)")
			            .selectAll("text")
			            .data(words)
			            .enter().append("text")
			            .style("font-size", function(d) { return d.size + "px"; })
			            .style("fill", function(d, i) { return color(i); })
			            .attr("transform", function(d) {
			                return "translate(" + [d.x, d.y] + ")rotate(" + d.rotate + ")";
			            })
			            .text(function(d) { return d.text; })
						.on("click", function (d, i){
							window.open(d.URL, "_blank");
						});
			}
		</script>
	</div>
	
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