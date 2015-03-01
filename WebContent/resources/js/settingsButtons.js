
function settingsButtonClick(clicked) {
	
	var settings = $('#settings' + clicked.id);
	var p = $(clicked).attr("opened");
	if (p == '0') {
		settings.show();
		$(clicked).attr("opened", "1");
	} else {
		settings.hide();
		$(clicked).attr("opened", "0");
	}
}

function settingsFormButtonClick(formButtonClicked){
	
	var tileNo = formButtonClicked.getAttribute('data-tileno');
	var componentOption = $('#settingsForm' + tileNo + 'Component').val();
	var timeScaleOption = $('#settingsForm' + tileNo + 'TimeScale').val();
	
	
		reDrawGraph(tileNo, componentOption, timeScaleOption);

	
	
}





