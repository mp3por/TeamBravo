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
	var myCenter = new google.maps.LatLng(lat,long1);
	
	function refreshMap(longitudes,latitudes,tweets) {
		console.log(longitudes);
		console.log(latitudes);
		if (markerClusterer) {
			markerClusterer.clearMarkers();
		}

		var markers = [];

		var markerImage = new google.maps.MarkerImage(imageUrl,
				new google.maps.Size(24, 32));

		
		for (var i = 0; i < latitudes.length; ++i) {
			var latLng = new google.maps.LatLng(latitudes[i],
					longitudes[i])
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

	function initialize(mapElementId,longitudes,latitudes,tweets) {
		
		map = new google.maps.Map(document.getElementById(mapElementId), {
			zoom : 11,
			center : myCenter,
			mapTypeId : google.maps.MapTypeId.ROADMAP
		});

		refreshMap(longitudes,latitudes);
	}

	function clearClusters(e) {
		e.preventDefault();
		e.stopPropagation();
		markerClusterer.clearMarkers();
	}

	//google.maps.event.addDomListener(window, 'load', initialize('map',longitudes,latitudes,tweets));
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
		<div class="row">
			<div class="col-md-6 HOLDER" id="holder_map" holder_id="1">
				<div class="block">
					<div class="row">
						<div class="col-md-12">
							<div class="above_box text-center">MAP</div>
							<div>
								<button id="1" type="button" class="btn btn-sm" settings_button_id="1" onClick="settingsButtonClick(this)">Settings!</button>
							</div>
						</div>
					</div>
					<div id="settings1" class="settings" settings_div_id="1" omg="2">settings</div>
					<div id="map-container" class="box">
						<div id="map"></div>
					</div>
				</div>
			</div>
			<div class="col-md-6" id="holder_tweet_wall" holder_id="2">
				<div class="block">
					<div class="row">
						<div class="col-md-12">
							<div class="above_box text-center">Tweets</div>
							<div>
								<button id="2" type="button" class="btn btn-sm" settings_button_id="2" onClick="settingsButtonClick(this)">Settings!</button>
							</div>
						</div>
					</div>
					<div id="settings2" class="settings" settings_div_id="2" omg="3">settings</div>
					<div id="tweetwall" class="box"></div>
				</div>
			</div>
		</div>

		<div class="row">
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
	var tile_template = null;

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
					addTile(toAdd);
				}
			});
		}
	});

	function addTile(toAdd) {
		if (toAdd != null && tile_template != null) {
			console.log("toAdd:" + toAdd);
			console.log("tile_template:" + tile_template);
			
		} else {
			alert("Something is wrong! toAdd: " + toAdd + ", tile_template: "
					+ tile_template);
		}
	}
	$(document).ready(function() {
		console.log("ready!");

		/* $('.SETTINGS_BUTTON').each(function(index,element) {
			console.log(element);
			element.click(function(e) {
				console.log("clicked");
				var sender = $(e.target);
				alert(sender.attr('settings_button_id'));
				console.log(sender)
				var index = sender.attr('settings_button_id');
				var settings = $('#settings[settings_div_id=' + index + ']');
				console.log(settings.attr('omg'));
				settings.show();
			})
		}); */

		$('#settings_button22').click(function(e) {
			console.log("clicked");
			var sender = $(e.target);
			alert(sender.attr('settings_button_id'));
			console.log(sender)
			var index = sender.attr('settings_button_id');
			var settings = $('#settings[settings_div_id=' + index + ']');
			console.log(settings.attr('omg'));
			settings.show();
		});

		extract();

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

		function getMaps() {
			$.ajax({
				url : '/TeamBravo/maps/test2',
				success : function(data) {
					console.log(data);
					var longitudes = data['longitudes'];
					var latitudes = data['latitudes'];
					var tweets = data.text;
					google.maps.event.addDomListener(window, 'load', initialize('map',longitudes,latitudes,tweets));
					//$("#map").html(data);
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
	});
</script>
</html>
