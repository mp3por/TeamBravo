//Testing ajax button click

$(function() {
	
	//INITIAL LOADING OF DEFAULT GRAPHS -----------------------------------------//
	loadDefaultGraphs();
	function loadDefaultGraphs() {
		graphInit();
		loadInitialBarChart();
		loadInitialLineChart();
		loadInitialPieChart();
		loadInitialWordCloud();
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
			url : '/TeamBravo/graphs/graphWeek',
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
	});
	//-------------------------------------------------------------------------//
	
	/*
	//Testing path variable
	function test() {
		$.ajax({
			url : '/TeamBravo/graphs/bar/WEEK',
			success : function(data) {
				console.log("testing path variable");
			}
		});
	}*/

});