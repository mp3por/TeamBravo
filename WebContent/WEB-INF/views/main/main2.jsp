<%@ include file="/WEB-INF/include.jsp"%>
<html>
<head>

<link href="<c:url value="/resources/css/graphs.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/tweets.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/maps.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/c3CSS.css" />" rel="stylesheet">

<!-- jQuery -->
<script src="<c:url value="/resources/js/jquery-1.11.2.min.js" />"></script>

<!-- maps -->
<script src="https://maps.googleapis.com/maps/api/js?sensor=false&region=GB"></script>
<script src="<c:url value="/resources/js/maps/markerclustererplus.js" />"></script>
<script src="<c:url value="/resources/js/maps/mapsJS.js" />"></script>

<!-- graphs -->
<script src="<c:url value="/resources/js/graphs/d3.min.js" />"></script>
<script src="<c:url value="/resources/js/graphs/c3.min.js" />"></script>
<script src="<c:url value="/resources/js/graphs/dimple.v2.1.0.min.js" />"></script>
<script src="<c:url value="/resources/js/graphs/d3.layout.cloud.js" />"></script>


<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

<!-- bootstrap -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Home</title>

</head>
<body>

	<!--------------------------- BAR ------------------------------------>
	<header>
		<div id='logo'>
			<img src="/TeamBravo/resources/img/GreyRedMackintosh2.png" style="width: 30%;">
		</div>
		<div id='cssmenu'>
			<ul id='naviMenu'>
				<li class='active'>
					<a href='#'><span>Home</span></a>
				</li>
				<li>
					<a href='#'><span>Tweets</span></a>
				</li>
				<li>
					<a href='#'><span>Map</span></a>
				</li>
				<li class='last'>
					<a href='#'><span>Graphs</span></a>
				</li>
			</ul>
			<div id="search"></div>
		</div>
	</header>
	<!-- -------------------------------------------------------------- -->

	<!-- MAIN OUTLOOK TABLE  -->

	<div class="container-fluid">

		<div id="row0" class="row"></div>

		<div class="row" id="last_row">
			<div class="col-md-12">
				<form class="form-horizontal" id="add_more_form">
					<fieldset>

						<!-- Form Name -->
						<legend>Add more tiles </legend>

						<!-- Multiple Radios (inline) -->
						<div class="form-group">
							<label class="col-md-4 control-label" for="radios">Choose Tile Type</label>
							<div class="col-md-8">

								<label class="radio" for="radios-0"> <input type="radio" name="type" id="radios-0" value="0" checked="checked"> Maps
								</label> <label class="radio" for="radios-1"> <input type="radio" name="type" id="radios-1" value="1"> Graphs
								</label> <label class="radio" for="radios-2"> <input type="radio" name="type" id="radios-2" value="2"> Tweet Wall
								</label> <label class="radio" for="radios-3"> <input type="radio" name="type" id="radios-3" value="3"> Stastistics
								</label>
							</div>
						</div>


						<!-- Button -->
						<div class="form-group">
							<label class="col-md-4 control-label" for="addMoreSubmit"></label>
							<div class="col-md-4">
								<button id="addMoreSubmit" name="addMoreSubmit" type="submit" class="btn btn-primary">Add</button>
							</div>
						</div>

					</fieldset>
				</form>
			</div>
		</div>
	</div>

	<!-- -------------------------------------------- -->

</body>
<footer> footer </footer>

<script type="text/javascript">
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

	$('#add_more_form').on('submit', function(e) { //use on if jQuery 1.7+
		console.log("submit");
		e.preventDefault(); //prevent form from submitting
		var data = $("#add_more_form").serializeArray();
		console.log(data[0].value);
		var toAdd = data[0].value;
		//debugger;
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
		//debugger;
		var settings = $('#settings' + clicked.id);
		var p = $(clicked).attr("opened");
		if (p == '0') {
			settings.show();
			$(clicked).attr("opened","1");
		}else{
			settings.hide();
			$(clicked).attr("opened","0");
		}
	}

	function addTile(toAdd) {
		if (toAdd != null && tile_template != null) {
			//debugger;

			console.log("addTile:" + toAdd);
			console.log("curr:" + current_num_of_tiles);
			var next = $('#next');
			var c = current_num_of_tiles;
			//var row = next.closest('.row');
			console.log("row_index: " + row_index);
			var row = $('#row' + row_index);
			var children = row.children();
			if (children.length >= 2) {
				row_index += 1;
				row.after('<div id="row'+row_index+'" class="row"></div>');
				row = $('#row' + row_index);
			}
			row.append(tile_template);

			fixTemplate(c);
			var tile_title = $('#tile_title' + c);
			//debugger;
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

	function getStastics(container_id, index) {
		$.ajax({
			url : '/TeamBravo/counter/test',
			success : function(data) {
				initStatistics(data, index);
			}
		});
	}

	function initStatistics(data, index) {
		$('#tile_content' + index).html(data);
	}

	function getMaps(container_id, index) {
		console.log("getting maps: " + container_id);
		$.ajax({
			url : '/TeamBravo/maps/test3',
			success : function(data) {
				var longitudes = data['longitudes'];
				var latitudes = data['latitudes'];
				var tweets = data.text;
				var settings = data.settings;
				var needed = data.needed;
				initMaps(container_id, longitudes, latitudes, tweets, needed,
						index);
			}
		});
		$.ajax({
			url : '/TeamBravo/maps/maps/getSettings',
			success : function(data) {
				//console.log(data);
				$('#settings' + index).html(data);
				$('#settings_template_form')
						.attr('id', 'settings_form' + index);
				$('#settings_button_template').attr('id',
						'settings_button' + index);
				$('#settings_button' + index).attr('tile', index);
			}
		});
	}

	function initMaps(container_id, longitudes, latitudes, tweets, needed,
			index) {
		//debugger;
		console.log("init maps");
		$('#' + container_id).append(needed);

		$('#added_map_container').attr('id', 'map_container' + index);
		$('#added_map_div').attr('id', 'map' + index);

		google.maps.event.addDomListener(window, 'load', initialize('map'
				+ index, longitudes, latitudes, tweets, index));
	}

	function initWall(container_id, data, index) {
		//debugger;
		$('#tile_content' + index).append(data);
		console.log("init wall");
		//console.log(tweets);

		/*
		console.log("container_id: " + container_id);
		console.log("index: " + index);
		
		$('#' + container_id).append(needed);
		$('#added_tweetwall_container').attr('id', 'tweetwall_container' + index);
		$('#added_tweetwall_div').attr('id', 'tweetwall' + index);
		
		 */
	}

	function getTweetwall(container_id, index) {
		console.log("Getting tweetwall: " + container_id);
		$.ajax({
			url : '/TeamBravo/tweets/test',
			success : function(data) {
				//debugger;
				//console.log("consoler:");
				//console.log(data);
				//var tweets = null;
				//var needed = null;
				//console.log (data);
				console.log("Success for tweetwall");
				initWall(container_id, data, index);
			}
		});
	}

	function fixTemplate(c) {
		console.log("fixTemplate " + c);
		$('#template_column_id').attr('id', 'tile' + c);
		$('#template_title').attr('id', 'tile_title' + c);
		$('#template_submit_button').attr('id', c);
		$('#template_settings_div').attr('id', 'settings' + c);
		$('#template_content').attr('id', 'tile_content' + c);
	}

	function graphInit() {
		$.ajax({
			url : '/TeamBravo/graphs/graphInit',
			async : false, //Quick fix, remove later
			success : function(data) {
				console.log("Graphs Initialised");
			}
		});
	}

	function getTweetWall() {
		$.ajax({
			url : '/TeamBravo/tweets/all',
			success : function(data) {
				$("#tweetwall").html(data);
			}
		});
	}

	function getSearchBox() {
		$.ajax({
			url : '/TeamBravo/search/searchBox',
			success : function(data) {
				$("#search").html(data);
			}
		});
	}

	function getTopicsForWeek() {
		$.ajax({
			url : '/TeamBravo/graphs/graphWeek',
			success : function(data) {
				$("#graphWeek").html(data);
			}
		});
	}

	function getTopicsForMonth() {
		$.ajax({
			url : '/TeamBravo/graphs/graphMonth',
			success : function(data) {
				$("#graphMonth").html(data);
			}
		});
	}

	function getPieChart() {
		$.ajax({
			url : '/TeamBravo/graphs/pieChart',
			success : function(data) {
				$("#chart").html(data);
			}
		});
	}

	function getWordCloud() {
		$.ajax({
			url : '/TeamBravo/graphs/wordCloud',
			success : function(data) {
				$("#wordCloud").html(data);
			}
		});
	}
</script>
</html>
