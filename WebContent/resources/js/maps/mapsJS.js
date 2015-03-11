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
// var infowindow = new google.maps.InfoWindow({
// content : "what ?",
// maxWidth : 250
// });

var imageUrl = 'http://chart.apis.google.com/chart?cht=mm&chs=24x32&'
		+ 'chco=FFFFFF,008CFF,000000&ext=.png';

var long1 = "-4.287393";
var lat = "55.873714";
var myCenter = new google.maps.LatLng(lat, long1);
var startDate;
var endDate;

function refreshMap(tweets_info, index, map) {
	// debugger;
	var longitudes = tweets_info["longitudes"];
	var latitudes = tweets_info["latitudes"];
	var tweets = tweets_info["tweets"];
	var users = tweets_info["users"];
	var time = tweets_info["time"];

	// var map = maps[index];
	var marketClusterer = markerClusterers[index];
	if (typeof markerClusterer != 'undefined') {
		markerClusterer.clearMarkers();
	}

	// var tooltip_template =
	// "<div><label>Time:</label>1</div><div><label>user:</label>2</div><div><label>text:</label>3</div>"
	var tooltip_template;
	var markerImage = new google.maps.MarkerImage(imageUrl,
			new google.maps.Size(24, 32));
	var curr_markers = [];

	for (var i = 0; i < latitudes.length; i++) {
		var latLng = new google.maps.LatLng(latitudes[i], longitudes[i])
		tooltip_template = "<div id='tooltip_time" + index
				+ "'><label>Time:</label>" + time[i]
				+ "</div><div id='tooltip_user" + index
				+ "'><label>user:</label>" + users[i]
				+ "</div><div id='tooltip_text" + index
				+ "'><label>text:</label>" + tweets[i] + "</div>"
		var marker = new google.maps.Marker({
			position : latLng,
			draggable : false,
			icon : markerImage,
			map : map,
			tweet : tooltip_template
		});
		var infowindow = new google.maps.InfoWindow({
			content : "what ?",
			maxWidth : 250
		});
		google.maps.event.addListener(marker, 'click', function(e) {
			infowindow.setContent(this.tweet);
			infowindow.open(map, this);
		});
		curr_markers.push(marker);
	}
	// debugger;

	var markerClusterer = new MarkerClusterer(map, curr_markers, {
		maxZoom : null,
		gridSize : null,
		styles : styles[null]
	});
	// console.log(marketClusterer);
	markerClusterers[index] = marketClusterer;
	// console.log(markerClusterers);
}

function initialize(mapElementId, index, tweets_info) {

	var map = new google.maps.Map(document.getElementById(mapElementId), {
		zoom : 11,
		center : myCenter,
		mapTypeId : google.maps.MapTypeId.ROADMAP
	});
	maps[index] = map;
	refreshMap(tweets_info, index, map);
}

function clearClusters(e) {
	e.preventDefault();
	e.stopPropagation();
	markerClusterer.clearMarkers();
}

function getMaps(container_id, index) {
	console.log("getting maps: " + container_id);
	$.ajax({
		url : '/TeamBravo/maps/test3',
		success : function(data) {
			var longitudes = data['longitudes'];
			var latitudes = data['latitudes'];
			var tweets = data.text;
			var needed = data.needed;
			var users = data.user;
			var time = data.time;

			var tweets_info = {
				"users" : users,
				"time" : time,
				"tweets" : tweets,
				"longitudes" : longitudes,
				"latitudes" : latitudes
			}
			initMaps(container_id, index, needed, tweets_info);
		}
	});
	$.ajax({
		url : '/TeamBravo/maps/maps/getSettings',
		success : function(data) {
			// console.log(data);
			$('#settings' + index).html(data);
			$('#settings_template_form').attr('tile', index);
			$('#settings_template_form').attr('id', 'settings_form' + index);
			$('#settings_button_template')
					.attr('id', 'settings_button' + index);
			$('#settings_button' + index).attr('tile', index);
			$('#settings_form' + index).submit(function(e) {
				e.preventDefault();
				var index = $(this).attr('tile');
				var data = $(this).serializeArray();
				$('#tooltip_time' + index).hide();
				$('#tooltip_text' + index).hide();
				$('#tooltip_user' + index).hide();

				for (var i = 0; i < data.length; i++) {
					var p = data[i]["value"];
					if (p == "text") {
						$('#tooltip_text' + index).show();
					} else if (p == "user") {
						$('#tooltip_user' + index).show();
					} else if (p == "time") {
						$('#tooltip_time' + index).show();
					}
				}

				// $('#settings_button'+index).click();
			});

			$("#added_datetimepicker_from").attr("id",
					"datetimepicker_from_" + index);
			$("#added_datetimepicker_to").attr("id",
					"datetimepicker_to_" + index);

			fixDatePickers(index);

		}
	});
}

function initMaps(container_id, index, needed, tweets_info) {

	// debugger;
	console.log("init maps");
	$('#' + container_id).append(needed);

	$('#added_map_container').attr('id', 'map_container' + index);
	$('#added_map_div').attr('id', 'map' + index);

	google.maps.event.addDomListener(window, 'load', initialize('map' + index,
			index, tweets_info));
}