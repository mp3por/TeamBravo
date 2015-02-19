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