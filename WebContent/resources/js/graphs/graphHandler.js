
$(function() {
	

	
	initialiseData();
	function initialiseData() {
		//graphInit();
	};
	
	function graphInit() {
		$.ajax({
			url : '/TeamBravo/graphs/graphInit',
			async : false, //Quick fix, remove later
			success : function(data) {
				console.log("Graphs Initialised");
			}
		});
	}
	
	function loadInitialBarChart() {
		$.ajax({
			url : '/TeamBravo/graphs/bar/WEEK',
			success : function(data) {
				$("#graphWeek").html(data);
			}
		});
	}

	function loadInitialLineChart() {
		$.ajax({
			url : '/TeamBravo/graphs/graphMonth',
			success : function(data) {
				$("#graphMonth").html(data);
			}
		});
	}
	
	function loadInitialPieChart() {
		$.ajax({
			url : '/TeamBravo/graphs/graphPie',
			success : function(data) {
				$("#chart").html(data);
			}
		});
	}
	
	function loadInitialWordCloud() {
		$.ajax({
			url : '/TeamBravo/graphs/wordCloud',
			success : function(data) {
				$("#wordCloud").html(data);
			}
		});
	}
	//-------------------------------------------------------------------------//
	
	/*
	//HANDLE BUTTON CLICKS TO CHANGE GRAPHS------------------------------------//
	$('.last').click(function() {
		$.ajax({
			url : '/TeamBravo/graphs/graphWeek',
			success : function(data) {
				$("#graphWeek").html(data);
			}
		});
	});

	var isClicking = false;
	$('.active').click(function() {
		if(!isClicking){
			isClicking = true;
			$.ajax({
				url : '/TeamBravo/graphs/graphMonth',
				success : function(data) {
					$("#graphMonth").html(data);
					isClicking = false;
				}
			});
		}
	});*/

	
	//Time Buttons------------------------------------------------------------//
	$('#timeButton1').click(function() {
		$('.block').css('background-color', "#656565");
		d3.select('.chart').select("svg").remove();	
		showBarChart("WEEK",1);
	});
	
	$('#timeButton2').click(function() {
		$('.block').css('background-color', "#CCCCCC"); //#7A9999
		d3.select('.chart').select("svg").remove();
			showLineGraph("WEEK",1);
	});
	
	/*$('.btn btn-sm').click(function() {
		$('.block').css('background-color', "#CCCCCC");
		//d3.select('.chart').select("svg").remove();	
		//showBarChart("WEEK",1);
	});*/
	

});



//Testing New Setup

//INITIALISE GLOBAL DATA FOR GRAPHS -----------------------------------------
var dataForDimpleWeek;
var dataForDimpleMonth;
var dataForPieWeek;
var dataForPieMonth;
var dataForCloudWeek;
var dataForCloudMonth;

//Initialisation methods -------------------------------------------->>
function initDimple(timeScale){
	
	if(timeScale == "WEEK"){
		//Only init data if it hasn't already been set
		if(dataForDimpleWeek == null){
			$.ajax({
				async: false,
				url : '/TeamBravo/graphs/dimple/WEEK',
				dataType: "json",
				success : function(data) {
					dataForDimpleWeek = data;
				}
			});
		}
	}else{
		//Only init data if it hasn't already been set
		if(dataForDimpleMonth == null){
			$.ajax({
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
				url : '/TeamBravo/graphs/cloud/MONTH',
				success : function(data) {
					dataForCloudWeek = data;
				}
			});
		}
	}else{
		//Only init data if it hasn't already been set
		if(dataForCloudMonth == null){
			$.ajax({
				url : '/TeamBravo/graphs/cloud/MONTH',
				success : function(data) {
					dataForCloudMonth = data;
				}
			});
		}
	}
}

//Show chart methods --------------------------------------------------------------->>

function showBarChart(timeScale, tileNo){
	//initDimple(timeScale);
	
	var src = [{"Day":"Mon","Topic":"IndyRef","Tweets":40000},{"Day":"Mon","Topic":"SNP","Tweets":50000},{"Day":"Mon","Topic":"Nicola Sturgeon","Tweets":50000},{"Day":"Tues","Topic":"IndyRef","Tweets":20000},{"Day":"Tues","Topic":"SNP","Tweets":10000},{"Day":"Tues","Topic":"Nicola Sturgeon","Tweets":50000},{"Day":"Wed","Topic":"IndyRef","Tweets":90000},{"Day":"Wed","Topic":"SNP","Tweets":30000},{"Day":"Wed","Topic":"Nicola Sturgeon","Tweets":90000},{"Day":"Thur","Topic":"IndyRef","Tweets":70000},{"Day":"Thur","Topic":"SNP","Tweets":80000},{"Day":"Thur","Topic":"Nicola Sturgeon","Tweets":60000},{"Day":"Fri","Topic":"IndyRef","Tweets":70000},{"Day":"Fri","Topic":"SNP","Tweets":30000},{"Day":"Fri","Topic":"Nicola Sturgeon","Tweets":80000},{"Day":"Sat","Topic":"IndyRef","Tweets":10000},{"Day":"Sat","Topic":"SNP","Tweets":60000},{"Day":"Sat","Topic":"Nicola Sturgeon","Tweets":20000},{"Day":"Sun","Topic":"IndyRef","Tweets":40000},{"Day":"Sun","Topic":"SNP","Tweets":30000},{"Day":"Sun","Topic":"Nicola Sturgeon","Tweets":70000},];
	/*if(timeScale == "WEEK"){
		src = dataForDimpleWeek;
	}else{
		src = dataForDimpleMonth;
	}*/
	
	$.ajax({
		aync : false,
		url : '/TeamBravo/graphs/' + tileNo + '/dimpleView',
		success : function(data) {
			$("#tile_content" + tileNo).html(data);
			drawGraph();
		}
	});
	
	function drawGraph(){
		
		/*if(tileNo == "all"){
			
		}else{
			
		}*/ //implement for all graphs
		
		
		var chartId = "#chart" + tileNo;
		var svgBar = dimple.newSvg(chartId, 550, 320);
		
		var colour1 = new dimple.color("#134C7C");
		var colour2 = new dimple.color("#C01E11");
		var colour3 = new dimple.color("#92B710");
		colour1.opacity = 1;
		colour2.opacity = 1;
		colour3.opacity = 1;
		
		var barChart = new dimple.chart(svgBar, src);
		var x = barChart.addCategoryAxis("x", [ "Day", "Topic" ]);
		x.addOrderRule("Day");
		var myAxis = barChart.addMeasureAxis("y", "Tweets");
		barChart.addSeries("Topic", dimple.plot.bar);
		var myLegend = barChart.addLegend(25, 10, 400, 20, "right");
		myLegend.fontSize = "14px"
		barChart.defaultColors = [
			colour1,
			colour2,
			colour3
		];
		barChart.width = 450;
		barChart.height = 220;
		barChart.draw();
		myAxis.gridlineShapes.selectAll().attr("stroke", "#FFFFFF");
	}
	
}

function showLineGraph(timeScale, tileNo){
	
	//initDimple(timeScale);
	
	var srcLine = [{"Day":"Mon","Topic":"IndyRef","Tweets":40000},{"Day":"Mon","Topic":"SNP","Tweets":50000},{"Day":"Mon","Topic":"Nicola Sturgeon","Tweets":50000},{"Day":"Tues","Topic":"IndyRef","Tweets":20000},{"Day":"Tues","Topic":"SNP","Tweets":10000},{"Day":"Tues","Topic":"Nicola Sturgeon","Tweets":50000},{"Day":"Wed","Topic":"IndyRef","Tweets":90000},{"Day":"Wed","Topic":"SNP","Tweets":30000},{"Day":"Wed","Topic":"Nicola Sturgeon","Tweets":90000},{"Day":"Thur","Topic":"IndyRef","Tweets":70000},{"Day":"Thur","Topic":"SNP","Tweets":80000},{"Day":"Thur","Topic":"Nicola Sturgeon","Tweets":60000},{"Day":"Fri","Topic":"IndyRef","Tweets":70000},{"Day":"Fri","Topic":"SNP","Tweets":30000},{"Day":"Fri","Topic":"Nicola Sturgeon","Tweets":80000},{"Day":"Sat","Topic":"IndyRef","Tweets":10000},{"Day":"Sat","Topic":"SNP","Tweets":60000},{"Day":"Sat","Topic":"Nicola Sturgeon","Tweets":20000},{"Day":"Sun","Topic":"IndyRef","Tweets":40000},{"Day":"Sun","Topic":"SNP","Tweets":30000},{"Day":"Sun","Topic":"Nicola Sturgeon","Tweets":70000},];;
	/*if(timeScale == "WEEK"){
		srcLine = dataForDimpleWeek;
	}else{
		srcLine = dataForDimpleMonth;
	}*/
	
	$.ajax({
		aync : false,
		url : '/TeamBravo/graphs/' + tileNo + '/dimpleView',
		success : function(data) {
			$("#tile_content" + tileNo).html(data);
			drawLineGraph(tileNo);
		}
	});
	
	function drawLineGraph(tileNo){
		
		var LinechartId = "#chart" + tileNo;
		var svg1 = dimple.newSvg(LinechartId, 550, 320);
		
		var myChart = new dimple.chart(svg1, srcLine);
		myChart.setBounds(60, 30, 450, 190);
		  
		var x = myChart.addCategoryAxis("x", "Day");
		x.addOrderRule("Day");
		  
		myChart.addMeasureAxis("y", "Tweets");
		  
		var s = myChart.addSeries("Topic", dimple.plot.line);
		s.interpolation = "linear"; //make interpolation "cardinal" for curved lines
		  
		var legend = myChart.addLegend(25, 10, 400, 20, "right");
		legend.fontSize = "14px"
		myChart.assignColor(srcLine[0].Topic, "#134C7C",null,1);
		myChart.assignColor(srcLine[1].Topic, "#C01E11",null,1);
		myChart.assignColor(srcLine[2].Topic, "#92B710",null,1);
		myChart.draw();
	}

}




function showPieChart(timeScale, tileNo){
	//debugger;
	/*initPie(timeScale);
	
	var srcPie;
	if(timeScale == "WEEK"){
		srcPie = dataForPieWeek;
	}else{
		srcPie = dataForPieMonth;
	}*/
	
	//To be fixed. Maybe just use Dimple!
	
	$.ajax({
		aync : false,
		url : '/TeamBravo/graphs/' + tileNo + '/dimpleView',
		success : function(data) {
			$("#tile_content" + tileNo).html(data);
			var script = document.createElement( 'script' );
			script.type = 'text/javascript';
			var $script = $(script).html("var pieTweets = srcPie;var chart = c3.generate({data : {columns : [ ['SNP', 20000],['IndyRef', 30000],['Nicola Sturgeon', 90000], ],type : 'pie',},legend : {position : 'bottom'},color : {pattern : [ '#C01E11', '#92B710', '#134C7C' ]}});");

			var chartId = "#chart" + tileNo;
			var $target = $data.find(chartId);
			$target.append($script);
		}
	});
	
	//drawPieChart();
	
	/*function drawPieChart(){
		var pieTweets = ${TweetsForPie};
		var chart = c3.generate({
			data : {
				columns : [ 
				            ["SNP", 20000],["IndyRef", 30000],["Nicola Sturgeon", 90000],
						],
				type : 'pie',
			},
			legend : {
				position : 'bottom'
			},
			color : {
				pattern : [ '#C01E11', '#92B710', '#134C7C' ]
			}
		});
		
		//var $script = $(script).html("var pieTweets = srcPie;var chart = c3.generate({data : {columns : [ [pieTweets[0].Topic, pieTweets[0].Tweets],[pieTweets[1].Topic, pieTweets[1].Tweets],[pieTweets[2].Topic, pieTweets[2].Tweets], ],type : 'pie',},legend : {position : 'bottom'},color : {pattern : [ '#C01E11', '#92B710', '#134C7C' ]}});");

	}*/
}

function showWordCloud(timeScale, tileNo){
	initCloud(timeScale);
	
}


