	//List contains arbitrary words and frequencies for display purposes only
    var frequency_list = [{"text":"Ibrox","size":50},{"text":"Hydro","size":15},{"text":"Parkhead","size":10},{"Green":"electricity","size":15},{"text":"Finnieston","size":10},{"text":"relation","size":5},{"text":"Subway","size":10},{"text":"EMA","size":5},{"text":"Still Game","size":5},{"text":"Indyref","size":85},{"text":"Better Together","size":5},{"text":"YES","size":5},{"text":"Scottish Labour","size":15},{"text":"SNP","size":45},{"text":"Food Banks","size":30},{"text":"Govan","size":5},{"text":"Maryhill","size":5},{"text":"Clyde","size":10},{"text":"Glasgow","size":100},{"text":"Crime","size":5},{"text":"Still Game Live","size":80},{"text":"Tommy Sheridan","size":5},{"text":"Celtic","size":10},{"text":"Rangers","size":10},{"text":"Nicola Sturgeon","size":70},{"text":"Alex Salmond","size":30}];


    var color = d3.scale.linear()
            .domain([0,1,2,3,4,5,6,10,15,20,100])
            .range(["#ddd", "#ccc", "#bbb", "#aaa", "#999", "#888", "#777", "#666", "#555", "#444", "#333", "#222"]);

    d3.layout.cloud().size([800, 300])
            .words(frequency_list)
            .rotate(0)
            .fontSize(function(d) { return d.size; })
            .on("end", draw)
            .start();

    function draw(words) {
        d3.select("body").append("svg")
                .attr("width", 850)
                .attr("height", 350)
                .attr("class", "wordcloud")
                .append("g")
                // without the transform, words words would get cutoff to the left and top, they would
                // appear outside of the SVG area
                .attr("transform", "translate(320,200)")
                .selectAll("text")
                .data(words)
                .enter().append("text")
                .style("font-size", function(d) { return d.size + "px"; })
                .style("fill", function(d, i) { return color(i); })
                .attr("transform", function(d) {
                    return "translate(" + [d.x, d.y] + ")rotate(" + d.rotate + ")";
                })
                .text(function(d) { return d.text; });
    }