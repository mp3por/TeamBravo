<%@ include file="/WEB-INF/include.jsp"%>
<html>
<head>

<link href="<c:url value="/resources/css/graphs.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/css/tweets.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/css/styles.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/css/maps.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/c3CSS.css" />" rel="stylesheet">

<!-- jQuery -->
<script src="<c:url value="/resources/js/jquery-1.11.2.min.js" />"></script>

<!-- maps -->
<script
	src="https://maps.googleapis.com/maps/api/js?sensor=false&region=GB"></script>
<script src="<c:url value="/resources/js/maps/markerclusterer.js" />"></script>

<!-- graphs -->
<script src="<c:url value="/resources/js/graphs/d3.min.js" />"></script>
<script src="<c:url value="/resources/js/graphs/c3.min.js" />"></script>
<script
	src="<c:url value="/resources/js/graphs/dimple.v2.1.0.min.js" />"></script>
<script src="<c:url value="/resources/js/graphs/d3.layout.cloud.js" />"></script>


<!-- Optional theme -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

<!-- bootstrap -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Home</title>

<script type="text/javascript" id="mapsJavaScript">
	var styles = [ [ {
		url : '<c:url value="/resources/img/maps/people35.png"/>',
		height : 35,
		width : 35,
		anchor : [ 16, 0 ],
		textColor : '#ff00ff',
		textSize : 10
	}, {
		url : '<c:url value="/resources/img/maps/people45.png"/>',
		height : 45,
		width : 45,
		anchor : [ 24, 0 ],
		textColor : '#ff0000',
		textSize : 11
	}, {
		url : '<c:url value="/resources/img/maps/people55.png"/>',
		height : 55,
		width : 55,
		anchor : [ 32, 0 ],
		textColor : '#ffffff',
		textSize : 12
	} ], [ {
		url : '<c:url value="/resources/img/maps/conv35.png"/>',
		height : 27,
		width : 30,
		anchor : [ 3, 0 ],
		textColor : '#ff00ff',
		textSize : 10
	}, {
		url : '<c:url value="/resources/img/maps/conv40.png"/>',
		height : 36,
		width : 40,
		anchor : [ 6, 0 ],
		textColor : '#ff0000',
		textSize : 11
	}, {
		url : '<c:url value="/resources/img/maps/conv50.png"/>',
		width : 50,
		height : 45,
		anchor : [ 8, 0 ],
		textSize : 12
	} ], [ {
		url : '<c:url value="/resources/img/maps/heart35.png"/>',
		height : 26,
		width : 30,
		anchor : [ 4, 0 ],
		textColor : '#ff00ff',
		textSize : 10
	}, {
		url : '<c:url value="/resources/img/maps/heart40.png"/>',
		height : 35,
		width : 40,
		anchor : [ 8, 0 ],
		textColor : '#ff0000',
		textSize : 11
	}, {
		url : '<c:url value="/resources/img/maps/heart50.png"/>',
		width : 50,
		height : 44,
		anchor : [ 12, 0 ],
		textSize : 12
	} ] ];

	var markerClusterers = [];
	var maps = [];
	var imageUrl = 'http://chart.apis.google.com/chart?cht=mm&chs=24x32&'
			+ 'chco=FFFFFF,008CFF,000000&ext=.png';

	var long1 = "-4.287393";
	var lat = "55.873714";
	var myCenter = new google.maps.LatLng(lat, long1);

	function refreshMap(longitudes, latitudes, tweets, index) {
		//debugger;
		var marketClusterer = markerClusterers[index];
		if (typeof markerClusterer != 'undefined') {
			markerClusterer.clearMarkers();
		}

		var markers = [];

		var markerImage = new google.maps.MarkerImage(imageUrl,
				new google.maps.Size(24, 32));

		for (var i = 0; i < latitudes.length; ++i) {
			var latLng = new google.maps.LatLng(latitudes[i], longitudes[i])
			var marker = new google.maps.Marker({
				position : latLng,
				draggable : true,
				icon : markerImage
			});
			markers.push(marker);
		}
		//debugger;
		var map = maps[index];
		var markerClusterer = new MarkerClusterer(map, markers, {
			maxZoom : null,
			gridSize : null,
			styles : styles[null]
		});
		//console.log(marketClusterer);
		markerClusterers[index] = marketClusterer;
		//console.log(markerClusterers);
	}

	function initialize(mapElementId, longitudes, latitudes, tweets, index) {

		var map = new google.maps.Map(document.getElementById(mapElementId), {
			zoom : 11,
			center : myCenter,
			mapTypeId : google.maps.MapTypeId.ROADMAP
		});
		maps[index] = map;
		refreshMap(longitudes, latitudes, tweets, index);
	}

	function clearClusters(e) {
		e.preventDefault();
		e.stopPropagation();
		markerClusterer.clearMarkers();
	}
</script>

</head>
<body>

	<!--------------------------- BAR ------------------------------------>
	<header>
		<div id='logo'>
			<img src="/TeamBravo/resources/img/GreyRedMackintosh2.png"
				style="width: 30%;">
		</div>
		<div id='cssmenu'>
			<ul id='naviMenu'>
				<li class='active'><a href='#'><span>Home</span></a></li>
				<li><a href='#'><span>Tweets</span></a></li>
				<li><a href='#'><span>Map</span></a></li>
				<li class='last'><a href='#'><span>Graphs</span></a></li>
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
							<label class="col-md-4 control-label" for="radios">Choose
								Tile Type</label>
							<div class="col-md-8">

								<label class="radio" for="radios-0"> <input type="radio"
									name="type" id="radios-0" value="0" checked="checked">
									Maps
								</label> <label class="radio" for="radios-1"> <input
									type="radio" name="type" id="radios-1" value="1">
									Graphs
								</label> <label class="radio" for="radios-2"> <input
									type="radio" name="type" id="radios-2" value="2"> Tweet
									Wall
								</label> <label class="radio" for="radios-3"> <input
									type="radio" name="type" id="radios-3" value="3">
									Stastistics
								</label>
							</div>
						</div>


						<!-- Button -->
						<div class="form-group">
							<label class="col-md-4 control-label" for="addMoreSubmit"></label>
							<div class="col-md-4">
								<button id="addMoreSubmit" name="addMoreSubmit" type="submit"
									class="btn btn-primary">Add</button>
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
		var settings = $('#settings' + clicked.id);
		settings.show();
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
	
	function reloadStats(param) {
		
		var index = param.data.param1
		$(this).addClass('active').siblings().removeClass('active');
		var time = $(this).text();
		var linkStr;
		if( time == 'past day') linkStr = 'pastDay';
		else if ( time == 'past month') linkStr = 'pastMonth';
		else if ( time == 'past week') linkStr = 'pastWeek';
		else linkStr = 'allTime';
			
		$.ajax({
			url : '/TeamBravo/counter/getStats/' + time,
			success : function(data) {
				$('#tile_content' + index).html(data);
				$('#added_stat_container').attr('id', 'stat_container' + index);
				}
		});
	}

	function getStastics(container_id, index) {
		$.ajax({
			url : '/TeamBravo/counter/stats/getSettings',
			success : function(data) {
				$('#settings' + index).html(data);
				$('#stat_time_setting').attr('id', 'stat_time_setting' + index);
				$('#stat_time_setting_label').attr('for', 'stat_time_setting' + index);
				$('#stat_time_setting' + index + ' button').click( {param1: index}, reloadStats );
			}
		});
		$.ajax({
			url : '/TeamBravo/counter/getStats/allTime',
			success : function(data) {
				$('#tile_content' + index).html(data);
				$('#added_stat_container').attr('id', 'stat_container' + index);
				//initStatistics(data, index);
			}
		});
	}

// 	function initStatistics(data, index) {
// 		$('#tile_content' + index).html(data);
// 		$('#added_stat_container').attr('id', 'stat_container' + index);
// 		$('#settings' + index).html
// 	}

	function getMaps(container_id, index) {
		console.log("getting maps: " + container_id);
		$.ajax({
			url : '/TeamBravo/maps/test2',
			success : function(data) {
				var longitudes = data['longitudes'];
				var latitudes = data['latitudes'];
				var tweets = data.text;
				var needed = data.needed;
				initMaps(container_id, longitudes, latitudes, tweets, needed,
						index);
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
