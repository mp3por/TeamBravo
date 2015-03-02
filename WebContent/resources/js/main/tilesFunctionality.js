$(document).ready(function() {
	console.log("ready!");

	getTemplate();

	function getTemplate() {
		if (tile_template == null) {
			$.ajax({
				url : '/TeamBravo/main/tile_template',
				success : function(data) {
					tile_template = data;
					initPage();
				}
			});
		} else {
			initPage();
		}
	}

	function initPage() {
		addTile("0");
		addTile("1");
		addTile("0");
		addTile("2");
		getSearchBox();
	}
});

var tile_template = null;
var row_index = 0;
var current_num_of_tiles = 0;

$('#add_more_form').on('submit', function(e) { // use on if jQuery 1.7+
	console.log("submit");
	e.preventDefault(); // prevent form from submitting
	var data = $("#add_more_form").serializeArray();
	console.log(data[0].value);
	var toAdd = data[0].value;
	// debugger;
	if (tile_template == null) {
		$.ajax({
			url : '/TeamBravo/main/tile_template',
			success : function(data) {
				tile_template = data;
				addTile(toAdd);
			}
		});
	} else {
		addTile(toAdd);
	}
});

function settingsButtonClick(clicked) {
	// debugger;
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

function setButtonClick(button) {
	debugger;
	var index = $(button).attr('tile');
	var form = $('#settings_form' + index);
	var form_values = form.serializeArray();
	console.log(form_values);
}

function addTile(toAdd) {
	if (toAdd != null && tile_template != null) {
		// debugger;

		console.log("addTile:" + toAdd);
		console.log("curr:" + current_num_of_tiles);
		var next = $('#next');
		var c = current_num_of_tiles;
		// var row = next.closest('.row');
		console.log("row_index: " + row_index);
		var row = $('#row' + row_index);
		var children = row.children();
		if (children.length >= 2) {
			row_index += 1;
			row.after('<div id="row' + row_index + '" class="row"></div>');
			row = $('#row' + row_index);
		}
		row.append(tile_template);

		fixTemplate(c);
		var tile_title = $('#tile_title' + c);
		// debugger;
		switch (toAdd) {
		case "0":// add map
			tile_title.text("Map");
			getMaps('tile_content' + c, c);
			break;
		case "1":// add graphs
			tile_title.text("Graphs");
			break;
		case "2":// add tweetwall
			tile_title.text("Tweet Wall");
			getTweetwall("tile_content" + c, c);
			break;
		case "3":
			tile_title.text("Stats");
			getStastics("tile_content" + c, c);
			break;
		}
		current_num_of_tiles += 1;
	} else {
		alert("Something is wrong! toAdd: " + toAdd + ", tile_template: "
				+ tile_template);
	}
}

function fixTemplate(c) {
	console.log("fixTemplate " + c);
	$('#template_column_id').attr('id', 'tile' + c);
	$('#template_title').attr('id', 'tile_title' + c);
	$('#template_submit_button').attr('id', c);
	$('#template_delete_button').attr('tile',c);
	$('#template_delete_button').attr('id','delete_tile_button'+c);
	$('#template_settings_div').attr('id', 'settings' + c);
	$('#template_content').attr('id', 'tile_content' + c);
}

function deleteButtonClick(button) {
	var index = $(button).attr('tile');
	console.log("deleting:" + index);
	$('#tile'+index).remove();
}