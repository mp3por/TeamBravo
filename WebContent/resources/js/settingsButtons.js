
function settingsButtonClick(clicked) {
	var settings = $('#settings' + clicked.id);
	settings.show();
}

function settingsFormButtonClick(formButtonClicked){
	
	var tileNo = formButtonClicked.getAttribute('data-tileno');
	var componentOption = $('#settingsForm' + tileNo + 'Component').val();
	var timeScaleOption = $('#settingsForm' + tileNo + 'TimeScale').val();
	reDrawGraph(tileNo, componentOption, timeScaleOption);
}





