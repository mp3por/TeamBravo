

//INITIALISE GLOBAL DATA FOR GRAPHS --------------------------------------------------------------------------->>
var dataForDimpleWeek;
var dataForDimpleMonth;

var dataForPieWeek;
var dataForPieMonth;

var dataForCloudWeek;
var dataForCloudMonth;

var dataForSearchWeek;
var dataForSearchMonth;

//Default Colours
var colour1 = new dimple.color("#AA3939"); //Red
var colour2 = new dimple.color("#AA9739"); //Yellow
var colour3 = new dimple.color("#403075"); //Blue
var colour4 = new dimple.color("#2D882D"); //Green
var colour5 = new dimple.color("#7C2969"); //Purple
colour1.opacity = 1;
colour2.opacity = 1;
colour3.opacity = 1;
colour4.opacity = 1;
colour5.opacity = 1;

//Initialisation methods -------------------------------------------------------------------------------------->>
function initDimple(timeScale){

	switch(timeScale){
	case "WEEK":
		if(dataForDimpleWeek == null){
			$.ajax({
				async: false,
				url : '/TeamBravo/graphs/dimple/WEEK',
				success : function(data) {
					dataForDimpleWeek = data;
				}
			});
		}
		break;
	case "MONTH":
		if(dataForDimpleMonth == null){
			$.ajax({
				async: false,
				url : '/TeamBravo/graphs/dimple/MONTH',
				success : function(data) {
					dataForDimpleMonth = data;
				}
			});
		}
		break;
	default:
	}
}


function initPie(timeScale){

	switch(timeScale){
	case "WEEK":
		if(dataForPieWeek == null){
			$.ajax({
				async: false,
				url : '/TeamBravo/graphs/pie/WEEK',
				success : function(data) {
					dataForPieWeek = data;
				}
			});
		}
		break;
	case "MONTH":
		if(dataForPieMonth == null){
			$.ajax({
				async: false,
				url : '/TeamBravo/graphs/pie/MONTH',
				success : function(data) {
					dataForPieMonth = data;
				}
			});
		}
		break;
	default:
	}
}

function initCloud(timeScale){
	switch(timeScale){
	case "WEEK":
		if(dataForCloudWeek == null){
			$.ajax({
				async: false,
				url : '/TeamBravo/graphs/cloud/WEEK',
				success : function(data) {
					dataForCloudWeek = data;
				}
			});
		}
		break;
	case "MONTH":
		if(dataForCloudMonth == null){
			$.ajax({
				async: false,
				url : '/TeamBravo/graphs/cloud/MONTH',
				success : function(data) {
					dataForCloudMonth = data;
				}
			});
		}
		break;
	default:
	}
}

function initSearchGraphs(searchTerm){
	
	$.ajax({
		async: false,
		url : '/TeamBravo/graphs/search/WEEK/' + searchTerm,
		success : function(data) {
			dataForSearchWeek = data;
		}
	});
	
	$.ajax({
		async: false,
		url :  '/TeamBravo/graphs/search/MONTH/' + searchTerm,
		success : function(data) {
			dataForSearchMonth = data;
		}
	});
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
			drawBarChart(tileNo, "WEEK", "MAIN", null, null, null);
		}
	});

	$.ajax({
		aync : false,
		url : '/TeamBravo/graphs/getSettings',
		success : function(data) {
			$('#settings' + tileNo).append(data);
			//Graph settings form
			var c = tileNo;
			$('#settingsForm').attr('id', 'settingsForm' + c);
			$('#settingsForm' + c).attr('data-tileno', c);
			$('#settingsFormComponent').attr('id', 'settingsForm' + c + 'Component');
			$('#settingsFormComponent' + c).attr('data-tileno', c);
			$('#graphSetBtnWeek').attr('id','graphSetBtnWeek' + c);
			$('#graphSetBtnWeek' + c).attr('data-tileno', c);
			$('#graphSetBtnMonth').attr('id','graphSetBtnMonth' + c);
			$('#graphSetBtnMonth' + c).attr('data-tileno', c);
			$('#topicChooserA').attr('id', 'topicChooserA' + c);
			$('#topicChooserB').attr('id', 'topicChooserB' + c);
			$('#topicChooserBtn').attr('id', 'topicChooserBtn' + c);
			$('#topicChooserBtn' + c).attr('data-tileno', c);
		}
	});
}


function drawBarChart(tileNo, timeScale, page, searchTerm, from , to){
	
	var src;
	var srcInit;
	var srcBtwn;
	from --; //decrease for actual array positions
	to --;
	
	if(page == "MAIN"){
		initDimple(timeScale);
		//Set data source week or month
		if(timeScale == "WEEK"){
			srcInit = dataForDimpleWeek;
		}else if(timeScale == "MONTH"){
			srcInit = dataForDimpleMonth;
		}
		
		if(from == null || to == null){
			for(var i = 0; i < 5 ; i++){
				srcBtwn[i] = srcInit[i];
			}
		}else{
			var j = 0;
			for(var i = from; i <= to; i++){
				srcBtwn[j] = srcInit[i];
				j++
			}
		}
		src = srcBtwn;
	}else if(page == "SEARCH"){
		initSearchGraphs(searchTerm);
		src = dataForSearchWeek;
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
	myLegend.paddin
	myLegend.fontSize = "12px"
	barChart.defaultColors = [
		colour1,
		colour2,
		colour3,
		colour4,
		colour5
	];

	barChart.draw();
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
			drawLineGraph(tileNo,"WEEK", null, null, null);
		}
	});
}

function drawLineGraph(tileNo,timeScale, page, searchTerm, from, to){
	
	var src
	var srcLine;
	var srcLineBtwn
	
	from --;
	to --;
	
	if(page == "MAIN"){
		
		initDimple(timeScale);
		//Set data source week or month
		if(timeScale == "WEEK"){
			srcLine = dataForDimpleWeek;
		}else if(timeScale == "MONTH"){
			srcLine = dataForDimpleMonth;
		}
		
		if(from == null || to == null){
			for(var i = 0; i < 5 ; i++){
				srcLineBtwn[i] = srcLine[i];
			}
		}else{
			var j = 0;
			for(var i = from; i <= to; i++){
				srcLineBtwn[j] = srcLine[i];
				j++
			}
		}
		
		src = srcLineBtwn;
	}else if(page == "SEARCH"){
		initSearchGraphs(searchTerm);
		src = dataForSearchMonth;
	}

		
	var LinechartId = "#chart" + tileNo;
	var svg1 = dimple.newSvg(LinechartId, "100%", "57%");
	
	var myChart = new dimple.chart(svg1, src);
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
	                		colour3,
	                		colour4,
	                		colour5
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
			drawPieChart(tileNo, "SINGLE","WEEK", null, null); //default
		}
	});
}

function drawPieChart(tileNo,timeScale, from, to){
	
	initPie(timeScale);
	var srcPie;
	var srcBtwn;
	if(timeScale == "WEEK"){
		srcPie = dataForPieWeek;
	}else if(timeScale == "MONTH"){
		srcPie = dataForPieMonth;
	}
	
	if(from == null || to == null){
		for(var i = 0; i < 5 ; i++){
			srcBtwn[i] = srcPie[i];
		}
	}else{
		var j = 0;
		for(var i = from; i <= to; i++){
			srcBtwn[j] = srcPie[i];
			j++
		}
	}
	
	var pieChartId = "#chart" + tileNo;
	var svgPie = dimple.newSvg(pieChartId, "100%", "57%"); //550, 320
	
	var myChart = new dimple.chart(svgPie, srcBtwn);
	myChart.setBounds("1%", "10%", "80%", "80%")
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
	}else if(timeScale == "MONTH"){
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

				window.open("/TeamBravo/search/terrier/" + d.Name);

			});
		}
}

//RE-DRAW GRAPHS------------------------------------------------------------------------------------------->>
function reDrawGraph(tileNo, graphType, timeScale, from, to){
	//Remove old SVG
	d3.select('#chart' + tileNo).select("svg").remove();
	
	//Draw graph
	switch(graphType) {
    case "LINEGRAPH":
    	drawLineGraph(tileNo, timeScale, "MAIN", null, from , to);
        break;
    case "BARCHART":
    	drawBarChart(tileNo, timeScale, "MAIN", null, from, to);
        break;
    case "PIECHART":
    	drawPieChart(tileNo, timeScale, from, to);
        break;
    case "WORDCLOUD":
    	drawWordCloud(tileNo, timeScale);
        break;
    default:
	} 	
}

//GRAPHS FOR SEARCH------------------------------------------------------------------------------------------>>
function getGraphsForSearch(searchTerm){
	debugger;
	drawBarChart(1, null, "SEARCH", searchTerm);
	drawLineGraph(2, null, "SEARCH", searchTerm);
}