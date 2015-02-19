<<<<<<< HEAD
<%@ include file="/WEB-INF/include.jsp"%>

  <script>
  	var frequency_list = ${frequencyList};
  	
  	var color = d3.scale.linear()
    .domain([0,1,2,3,4,5,6,10,15,20,100])
    .range(["#C54040", "#C496B0","#8A2E60","#CCA352","#FFCC66","#95B15D","#7A9E35","#97ADB9","#7D99A7"]);
	
	d3.layout.cloud().size([420, 340]) //Change back to 400,400 if they get cut off
	    .words(frequency_list)
	    .rotate(0)
		.text(function(d) { return d.Name; })
	    .fontSize(function(d) { return d.Tweets; })
	    .on("end", draw)
	    .start();
	
	function draw(words) {
	d3.select("#wordCloud").append("svg")
	        .attr("width", 520)
	        .attr("height", 320)
	        .attr("class", "wordcloud")
	        .append("g")
	        // without the transform, words would get cut off to the left and top, they would
	        // appear outside of the SVG area
	        .attr("transform", "translate(121,175)") //(from left, from top) size(445,340) transform(110,175) worked
	        .selectAll("text")
	        .data(words)
	        .enter().append("text")
	        .style("font-size", function(d) { return d.Tweets + "px"; })
	        .style("fill", function(d, i) { return color(i); })
	        .attr("transform", function(d) {
	            return "translate(" + [d.x, d.y] + ")rotate(" + d.rotate + ")";
	        })
	        .text(function(d) { return d.Name; })
			.on("click", function (d, i){
				window.open("/TeamBravo/topic/specific/", "_blank");
			});
	}
  </script>

=======
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Word Cloud</title>
	
	<link href="<c:url value="/resources/css/base.css" />" rel="stylesheet">
	<script src="<c:url value="/resources/js/graphs/d3.min.js" />"></script>
	<script src="<c:url value="/resources/js/graphs/d3.layout.cloud.js" />"></script>
	<%@ page isELIgnored="false" %>

</head>

<body>
	<div id="wordCloud">
		<h1>Clickable Word Cloud</h1>
		<script>
		var frequency_list = ${frequencyList};
		//var frequency_list = [{"text":"Ibrox","size":50,"URL":"http://www.rangers.co.uk/"},{"text":"Hydro","size":110,"URL":"http://www.thessehydro.com/default.aspx"},{"text":"Parkhead","size":80,"URL":"http://www.celticfc.net/mainindex"},{"text":"Finnieston","size":30,"URL":"http://www.thefinniestonbar.com/"},{"text":"Celtic","size":60,"URL":"http://www.celticfc.net/mainindex"},{"text":"Subway","size":110,"URL":"http://www.spt.co.uk/subway/"},{"text":"EMA","size":40,"URL":"http://tv.mtvema.com/"},{"text":"Still Game","size":70,"URL":"http://www.bbc.co.uk/programmes/b006qgl8"}];
			
			var color = d3.scale.linear()
			        .domain([0,1,2,3,4,5,6,10,15,20,100])
			        .range(["#ddd", "#ccc", "#bbb", "#aaa", "#999", "#888", "#777", "#666", "#555", "#444", "#333", "#222"]);
			
			d3.layout.cloud().size([400, 400]) //Change back to 400,400 if they get cut off
			        .words(frequency_list)
			        .rotate(0)
					.text(function(d) { return d.Name; })
			        .fontSize(function(d) { return d.Tweets; })
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
			            .style("font-size", function(d) { return d.Tweets + "px"; })
			            .style("fill", function(d, i) { return color(i); })
			            .attr("transform", function(d) {
			                return "translate(" + [d.x, d.y] + ")rotate(" + d.rotate + ")";
			            })
			            .text(function(d) { return d.Name; })
						.on("click", function (d, i){
							window.open("/TeamBravo/topic/specific/", "_blank");
						});
			}
		</script>
	</div>
</body>

</html>
>>>>>>> Steven
