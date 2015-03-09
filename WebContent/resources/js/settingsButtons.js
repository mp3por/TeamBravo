
function settingsFormOptionSelect(dropDownList){
	var tileNo = dropDownList.getAttribute('data-tileno');
	var componentOption = $('#settingsForm' + tileNo + 'Component').val();
	var activeGraph = $('.graphActive');
	var time = $('.graphActive').val();
	reDrawGraph(tileNo, componentOption, time, null, null);
}

function settingsFormTimeSelect(settingsForm){

	$(settingsForm).addClass('graphActive').siblings().removeClass('graphActive'); //for button highlighting
	var time = settingsForm.value;
	var tileNo = settingsForm.getAttribute('data-tileno');
	var componentOption = $('#settingsForm' + tileNo + 'Component').val();
	reDrawGraph(tileNo, componentOption, time, null, null);
}

function settingsFormTopicSelect(topicForm){
	var tileNo = topicForm.getAttribute('data-tileno');
	console.log(tileNo);
	var from = $('#topicChooserA' + tileNo).val();
	console.log("From: " + from);
	var to = $('#topicChooserB' + tileNo).val();
	console.log("To: " + to);
	var chartType = $('#settingsForm' + tileNo + 'Component').val();
	redrawGraph(tileNo, componentOption, "WEEK", from, to);
}





