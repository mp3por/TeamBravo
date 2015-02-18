<%@ include file="/WEB-INF/include.jsp"%>
<script src="<c:url value="/resources/js/graphs/d3.layout.cloud.js" />"></script>
  <script>
  	var frequency_list = ${wordCloudList};
  	
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

