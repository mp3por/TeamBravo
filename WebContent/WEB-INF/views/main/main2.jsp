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
<script src="<c:url value="/resources/js/maps/markerclusterer.js" />"></script>

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

	var markerClusterer = null;
	var map = null;
	var imageUrl = 'http://chart.apis.google.com/chart?cht=mm&chs=24x32&'
			+ 'chco=FFFFFF,008CFF,000000&ext=.png';

	var long1 = "-4.287393";
	var lat = "55.873714";
	var myCenter = new google.maps.LatLng(lat, long1);

	function refreshMap(longitudes, latitudes, tweets) {
		if (markerClusterer) {
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

		markerClusterer = new MarkerClusterer(map, markers, {
			maxZoom : null,
			gridSize : null,
			styles : styles[null]
		});
	}

	function initialize(mapElementId, longitudes, latitudes, tweets) {

		map = new google.maps.Map(document.getElementById(mapElementId), {
			zoom : 11,
			center : myCenter,
			mapTypeId : google.maps.MapTypeId.ROADMAP
		});

		refreshMap(longitudes, latitudes);
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

		<div id="next"></div>

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
								<label class="radio" for="radios-0"> <input type="radio" name="type" id="radios-0" value="1" checked="checked"> Maps
								</label> <label class="radio" for="radios-1"> <input type="radio" name="type" id="radios-1" value="2"> Graphs
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
			addTile(0);
			addTile(1);
		}

		// extract();

		function extract() {
			//graphInit();
			//getTweetWall();
			getMaps();
			//getSearchBox();
			//getTopicsForWeek();
			//getTopicsForMonth();
			//getPieChart();
			//getWordCloud();
		}
		;
	});

	var tile_template = null;
	var row_index = 0;
	var current_num_of_tiles = 0;

	function settingsButtonClick(clicked) {
		var settings = $('#settings' + clicked.id);
		settings.show();
	}

	$('#add_more_form').on('submit', function(e) { //use on if jQuery 1.7+
		e.preventDefault(); //prevent form from submitting
		var data = $("#add_more_form").serializeArray();
		console.log(data[0].value);
		var toAdd = data[0].value;
		if (tile_template == null) {
			$.ajax({
				url : '/TeamBravo/main/tile_template',
				success : function(data) {
					tile_template = data;
					//addTile();
				}
			});
		} else {
			//addTile();
		}
	});

	function addTile(toAdd) {
		if (toAdd != null && tile_template != null) {
			current_num_of_tiles += 1;
			console.log("addTile:" + toAdd);
			console.log("curr:" + current_num_of_tiles);
			var next = $('#next');
			var c = current_num_of_tiles;
			var row = next.closest('.row');
			console.log("row_index: "+ row_index);
			if (row.length == 0) {
				console.log("no row above");
				next.append('<div id="row'+row_index+'" class="row"></div>');
				next = $('#row' + row_index);
				next.unwrap();
				next.append(tile_template);
				next.append('<div id="next"></div>');
				row_index += 1;
			} else {
				console.log("row above");
				next.append(tile_template);
				$('#template_column_id').unwrap();
				$('#row' + row_index).after('<div id="next"></div>');
			}

			fixTemplate(c);
			var tile_title = $('#tile_title' + c);
			switch (toAdd) {
			case 0:// add map
				tile_title.text("Map");
				getMaps('tile_content' + c,c);
				break;
			case 1:// add graphs
				tile_title.text("Graphs");
				break;
			}
		} else {
			alert("Something is wrong! toAdd: " + toAdd + ", tile_template: "
					+ tile_template);
		}
	}

	function getMaps(container_id,index) {
		console.log("getting maps: " + container_id);
		$.ajax({
			url : '/TeamBravo/maps/test2',
			success : function(data) {
				var longitudes = data['longitudes'];
				var latitudes = data['latitudes'];
				var tweets = data.text;
				var needed = data.needed;
				initMaps(container_id, longitudes, latitudes, tweets, needed,index);
			}
		});
	}

	function initMaps(container_id, longitudes, latitudes, tweets, needed,index) {
		debugger;
		console.log("init maps");
		$('#' + container_id).append(needed);

		$('#added_map_container').attr('id',
				'map_container' + index);
		$('#added_map_div').attr('id', 'map' + index);

		google.maps.event.addDomListener(window, 'load', initialize('map'
				+ index, longitudes, latitudes, tweets));
	}

	function fixTemplate(c) {
		console.log("fixTemplate " +c );
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
			url : '/TeamBravo/main/searchBox',
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
