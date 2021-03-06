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
var infowindow = new google.maps.InfoWindow({
	content : "what ?",
	maxWidth : 250
});

var imageUrl = 'http://chart.apis.google.com/chart?cht=mm&chs=24x32&'
		+ 'chco=FFFFFF,008CFF,000000&ext=.png';

var long1 = "-4.287393";
var lat = "55.873714";
var myCenter = new google.maps.LatLng(lat, long1);

function refreshMap(longitudes, latitudes, tweets, index, map) {
	// debugger;

	// var map = maps[index];
	var marketClusterer = markerClusterers[index];
	if (typeof markerClusterer != 'undefined') {
		markerClusterer.clearMarkers();
	}

	var markerImage = new google.maps.MarkerImage(imageUrl,
			new google.maps.Size(24, 32));
	var curr_markers = [];

	for (var i = 0; i < latitudes.length; i++) {
		var latLng = new google.maps.LatLng(latitudes[i], longitudes[i])
		var marker = new google.maps.Marker({
			position : latLng,
			draggable : false,
			icon : markerImage,
			map : map,
			tweet : tweets[i]
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

function initialize(mapElementId, longitudes, latitudes, tweets, index) {

	var map = new google.maps.Map(document.getElementById(mapElementId), {
		zoom : 11,
		center : myCenter,
		mapTypeId : google.maps.MapTypeId.ROADMAP
	});
	maps[index] = map;
	refreshMap(longitudes, latitudes, tweets, index, map);
}

function clearClusters(e) {
	e.preventDefault();
	e.stopPropagation();
	markerClusterer.clearMarkers();
}