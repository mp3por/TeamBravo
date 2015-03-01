
//INITIALISE GLOBAL DATA FOR GRAPHS --------------------------------------------------------------------------->>
var dataForDimpleWeek;
var dataForDimpleMonth;
var dataForPieWeek;
var dataForPieMonth;
var dataForCloudWeek;
var dataForCloudMonth;

//Default Colours
var colour1 = new dimple.color("#134C7C");
var colour2 = new dimple.color("#C01E11");
var colour3 = new dimple.color("#92B710");
var colour4 = new dimple.color("#FFE758");
var colour5 = new dimple.color("#A002F1");
colour1.opacity = 1;
colour2.opacity = 1;
colour3.opacity = 1;
colour4.opacity = 1;
colour5.opacity = 1;

//Initialisation methods -------------------------------------------------------------------------------------->>
function initDimple(timeScale){
	if(timeScale == "WEEK"){
		//Only init data if it hasn't already been set
		if(dataForDimpleWeek == null){
			$.ajax({
				async: false,
				url : '/TeamBravo/graphs/dimple/WEEK',
				success : function(data) {
					dataForDimpleWeek = data;
				}
			});
		}
	}else{
		//Only init data if it hasn't already been set
		if(dataForDimpleMonth == null){
			$.ajax({
				async: false,
				url : '/TeamBravo/graphs/dimple/MONTH',
				success : function(data) {
					dataForDimpleMonth = data;
				}
			});
		}
	}
}


function initPie(timeScale){
	
	if(timeScale == "WEEK"){
		//Only init data if it hasn't already been set
		if(dataForPieWeek == null){
			$.ajax({
				async: false,
				url : '/TeamBravo/graphs/pie/WEEK',
				success : function(data) {
					dataForPieWeek = data;
				}
			});
		}
	}else{
		//Only init data if it hasn't already been set
		if(dataForPieMonth == null){
			$.ajax({
				async: false,
				url : '/TeamBravo/graphs/pie/MONTH',
				success : function(data) {
					dataForPieMonth = data;
				}
			});
		}
	}
}

function initCloud(timeScale){
	
	if(timeScale == "WEEK"){
		//Only init data if it hasn't already been set
		if(dataForCloudWeek == null){
			$.ajax({
				async: false,
				url : '/TeamBravo/graphs/cloud/WEEK',
				success : function(data) {
					dataForCloudWeek = data;
				}
			});
		}
	}else{
		//Only init data if it hasn't already been set
		if(dataForCloudMonth == null){
			$.ajax({
				async: false,
				url : '/TeamBravo/graphs/cloud/MONTH',
				success : function(data) {
					dataForCloudMonth = data;
				}
			});
		}
	}
}

//Show chart methods--------------------------------------------------------------------------------------------->>

//BARCHART------------------------------------------------------------------------------------------->>
function showBarChart(tileNo){ //Change this to showChart(type) to just load default chart at first
	$.ajax({
		aync : false,
		url : '/TeamBravo/graphs/graphView/' + tileNo,
		success : function(data) {
			$("#tile_content" + tileNo).html(data);
			//reSet component's class
			var chartClass = $('#chart' + tileNo).attr('class');
			$('#chart' + tileNo).attr('class','bar' + chartClass);
			drawBarChart(tileNo, "WEEK");
		}
	});
}

function drawBarChart(tileNo, timeScale){
	
	initDimple(timeScale);
	//Set data source week or month
	debugger;
	var src;
	if(timeScale == "WEEK"){
		src = dataForDimpleWeek;
	}else{
		src = dataForDimpleMonth;
	}
	
	//Get id and append SVG
	var chartId = "#chart" + tileNo;
	var svgBar = dimple.newSvg(chartId, "100%", "57%");
	
	var barChart = new dimple.chart(svgBar, src);
	barChart.setBounds("10%", "20%", "80%", "60%");
	
	var x = barChart.addCategoryAxis("x", [ "Day", "Topic" ]);
	x.addOrderRule("Day");
	
	barChart.addMeasureAxis("y", "Tweets");
	barChart.addSeries("Topic", dimple.plot.bar);
	
	var myLegend = barChart.addLegend("10%", "5%", "100%", "10%", "left");
	myLegend.fontSize = "14px"
	barChart.defaultColors = [
		colour1,
		colour2,
		colour3
	];

	barChart.draw();
	myAxis.gridlineShapes.selectAll().attr("stroke", "#FFFFFF");
}

//LINEGRAPH------------------------------------------------------------------------------------------->>
function showLineGraph(tileNo){
	$.ajax({
		aync : false,
		url : '/TeamBravo/graphs/graphView/' + tileNo,
		success : function(data) {
			$("#tile_content" + tileNo).html(data);
			//reSet component's class
			var chartClass = $('#chart' + tileNo).attr('class');
			$('#chart' + tileNo).attr('class','line' + chartClass);
			drawLineGraph(tileNo,"WEEK");
		}
	});
}

function drawLineGraph(tileNo,timeScale){
	
	initDimple(timeScale);
	var srcLine;
	if(timeScale == "WEEK"){
		srcLine = dataForDimpleWeek;
	}else{
		srcLine = dataForDimpleMonth;
	}
	
		
	var LinechartId = "#chart" + tileNo;
	var svg1 = dimple.newSvg(LinechartId, "100%", "57%");
	
	var myChart = new dimple.chart(svg1, srcLine);
	myChart.setBounds("10%", "20%", "80%", "60%");
	  
	var x = myChart.addCategoryAxis("x", "Day");
	x.addOrderRule("Day");
	  
	myChart.addMeasureAxis("y", "Tweets");
	  
	var s = myChart.addSeries("Topic", dimple.plot.line);
	s.interpolation = "linear"; //make interpolation "cardinal" for curved lines
	  
	var legend = myChart.addLegend("10%", "5%", "100%", "10%", "left");
	legend.fontSize = "14px"
	myChart.defaultColors = [
	                  		colour1,
	                  		colour2,
	                  		colour3
	                  	];
		
	myChart.draw();

}

//PIECHART------------------------------------------------------------------------------------------->>
function showPieChart(tileNo){
	
	$.ajax({
		aync : false,
		url : '/TeamBravo/graphs/graphView/' + tileNo,
		success : function(data) {
			$("#tile_content" + tileNo).html(data);
			//reSet component's class
			var chartClass = $('#chart' + tileNo).attr('class');
			$('#chart' + tileNo).attr('class','pie' + chartClass);
			drawPieChart(tileNo, "SINGLE","WEEK"); //default
		}
	});
}

function drawPieChart(tileNo,timeScale){
	
	initPie(timeScale);
	var srcPie;
	if(timeScale == "WEEK"){
		srcPie = dataForPieWeek;
	}else{
		srcPie = dataForPieMonth;
	}
	
	var pieChartId = "#chart" + tileNo;
	var svgPie = dimple.newSvg(pieChartId, 550, 320);
	
	var myChart = new dimple.chart(svgPie, srcPie);
	myChart.setBounds(1, 30, 500, 250)
	myChart.addMeasureAxis("p", "Tweets");
	myChart.addSeries("Topic", dimple.plot.pie);
	var legend = myChart.addLegend(400, 20, 100, 300, "left");
	legend.fontSize = "16px";
	myChart.defaultColors = [
		                  		colour1,
		                  		colour2,
		                  		colour3,
		                  		colour4,
		                  		colour5
		                  	];
    myChart.draw()
}

//WORDCLOUD------------------------------------------------------------------------------------------->>
function showWordCloud(tileNo){
	$.ajax({
		aync : false,
		url : '/TeamBravo/graphs/graphView/' + tileNo,
		success : function(data) {
			$("#tile_content" + tileNo).html(data);
			//reSet component's class
			var chartClass = $('#chart' + tileNo).attr('class');
			$('#chart' + tileNo).attr('class','cloud' + chartClass);
			drawWordCloud(tileNo,"WEEK");
		}
	});
}

function drawWordCloud(tileNo,timeScale){
	
	initCloud(timeScale);
	var srcCloud;
	if(timeScale == "WEEK"){
		srcCloud = dataForCloudWeek;
	}else{
		srcCloud = dataForCloudMonth;
	}
  	
  	var color = d3.scale.linear()
    .domain([0,1,2,3,4,5,6,10,15,20,100])
    .range(["#C54040", "#C496B0","#8A2E60","#CCA352","#FFCC66","#95B15D","#7A9E35","#97ADB9","#7D99A7"]);
	
	d3.layout.cloud().size([420, 340]) //Change back to 400,400 if they get cut off
	    .words(srcCloud)
	    .rotate(0)
		.text(function(d) { return d.Name; })
	    .fontSize(function(d) { return d.Tweets; })
	    .on("end", draw)
	    .start();
	
	function draw(words) {
			var cloudId = "#chart" + tileNo;
			d3.select(cloudId).append("svg")
	        .attr("width", 520)
	        .attr("height", 320)
	        .attr("class", "wordcloud")
	        .append("g")
	        // without the transform, words would get cut off to the left and top, they would
	        // appear outside of the SVG area
	        .attr("transform", "translate(200,175)") //(from left, from top) size(445,340) transform(110,175) worked
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
}

//RE-DRAW GRAPHS------------------------------------------------------------------------------------------->>
function reDrawGraph(tileNo, graphType, timeScale){
	
	switch(graphType) {
    case "LINEGRAPH":
    	d3.select('#chart' + tileNo).select("svg").remove();
    	drawLineGraph(tileNo, timeScale);
        break;
    case "BARCHART":
    	d3.select('#chart'+ tileNo).select("svg").remove();
    	drawBarChart(tileNo, timeScale);
        break;
    case "PIECHART":
    	d3.select('#chart' + tileNo).select("svg").remove();
    	drawPieChart(tileNo, timeScale);
        break;
    case "WORDCLOUD":
    	d3.select('#chart' + tileNo).select("svg").remove();
    	drawWordCloud(tileNo, timeScale);
        break;
    default:
        //Add other components
	} 	
}