$(document).ready(function() {
	console.log("ready!");
	getTemplate();
});

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

var tile_template = null;
var num_of_rows = 0;
var num_of_tiles_added = 0;

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
		console.log("curr:" + num_of_tiles_added);

		var c = num_of_tiles_added;
		console.log("num_of_rows: " + num_of_rows);
		var row = $('#row' + num_of_rows);
		var children = row.children();
		if (children.length >= 2) {
			num_of_rows += 1;
			row
					.after('<div id="row' + num_of_rows
							+ '" class="row BIG_ROW" row ="' + num_of_rows
							+ '"></div>');
			row = $('#row' + num_of_rows);
		}
		row.append(tile_template);

		// $('#row').insertBefore(tile_template);

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
			showBarChart(c);
			break;
		case "2":// add tweetwall
			tile_title.text("Tweet Wall");
			getTweetwall("tile_content" + c, c);
			break;
		case "3":
			tile_title.text("Stats");
			getStatistics("tile_content" + c, c);
			break;
		}
		num_of_tiles_added += 1;
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
	$('#template_delete_button').attr('tile', c);
	$('#template_delete_button').attr('id', 'delete_tile_button' + c);
	$('#template_settings_div').attr('id', 'settings' + c);
	$('#template_content').attr('id', 'tile_content' + c);
}

function deleteButtonClick(button) {
	debugger;
	console.log("INDEXX:" + num_of_rows);
	var tile_index = $(button).attr('tile');
	var row_of_deleted_item = $(button).closest('.BIG_ROW');
	console.log("deleting:" + tile_index);
	$('#tile' + tile_index).remove();
	moveTilesAfter(tile_index, row_of_deleted_item);
}

function removeRow(row){
	debugger;
	$(row).remove();
	num_of_rows--;
}

function moveTilesAfter(tile_index, row_of_deleted_item) {
	console.log("INDEXX:" + num_of_rows);
	var r_index = $(row_of_deleted_item).attr('row');
	console.log("R_INDEXX:" + r_index);
	debugger;
	if (r_index == num_of_rows) {
		if ($(row_of_deleted_item).children().length == 0) {
			removeRow(row_of_deleted_item);
//			$(row_of_deleted_item).remove();
//			num_of_rows--;
		}
	} else {
		var prev_row = row_of_deleted_item;
		var next_row;
		for (var next_row_index = parseInt(r_index) + 1; next_row_index <= num_of_rows; next_row_index++) {
			next_row = $("#row" + next_row_index);
			var next_row_children = $(next_row).children();
			var child1 = next_row_children[0];
			$(child1).remove();
			console.log(child1);
			$(prev_row).append(child1);
			prev_row=next_row;
		}
		var children = $(next_row).children();
		if(children.length == 0){
			removeRow(next_row);
//			$(next_row).remove();
//			num_of_rows --;
		}
	}
	// for(var i = tile_index,num_of_rows; i < num_of_tiles_added ; i++){
	// var tile = $('#tile'+i);
	//		
	// }
}