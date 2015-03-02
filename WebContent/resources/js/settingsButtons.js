
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

function settingsFormOptionSelect(dropDownList){
	var tileNo = dropDownList.getAttribute('data-tileno');
	var componentOption = $('#settingsForm' + tileNo + 'Component').val();
	var activeGraph = $('.graphActive');
	var time = $('.graphActive').val();
	
	console.log("OptionSelected: tile-" + tileNo + " option-" + componentOption + " time-" + time);
	reDrawGraph(tileNo, componentOption, time);
}

function settingsFormTimeSelect(settingsForm){

	$(settingsForm).addClass('graphActive').siblings().removeClass('graphActive');
	var time = settingsForm.value;
	console.log(time);
	var tileNo = settingsForm.getAttribute('data-tileno');
	console.log(tileNo);
	var componentOption = $('#settingsForm' + tileNo + 'Component').val();
	console.log(componentOption);
	
	console.log("TimeSelected: tile-" + tileNo + " option-" + componentOption + " time-" + time);
	reDrawGraph(tileNo, componentOption, time);
}





