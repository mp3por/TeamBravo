$(document).ready(function() {
	
	$('#timeButton1').click(function() {
		$('.block').css('background-color', "#656565");
		//Call graphHandler.js redrawGraph(tileNO, scope, graphType, timeScale)
		redrawGraph(null, "ALL","ALL","WEEK");
	});
	
	$('#timeButton2').click(function() {
		$('.block').css('background-color', "#CCCCCC"); //#7A9999
		//Call graphHandler.js redrawGraph(tileNO, scope, timeScale)
		redrawGraph(null, "ALL", "ALL", "MONTH");
	});
});

function settingsButtonClick(clicked) {
	var settings = $('#settings' + clicked.id);
	settings.show();
}


