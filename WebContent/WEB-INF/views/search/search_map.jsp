

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
	
	
	var long1 = ${longitude};
	var lat = ${latitude};
	var myCenter = new google.maps.LatLng(lat,long1);
	var latitudes = ${latitudes};
	var longitudes = ${longitudes};
	var text = ${text};
	
	function refreshMap() {
		if (markerClusterer) {
			markerClusterer.clearMarkers();
		}

		var markers = [];

		var markerImage = new google.maps.MarkerImage(imageUrl,
				new google.maps.Size(24, 32));

		
		for (var i = 0; i < latitudes.length; ++i) {
			//console.log("lat:" +latitudes[i]+ ",long:"+longitudes[i]);
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

	function initialize() {
		
		map = new google.maps.Map(document.getElementById('map'), {
			zoom : 11,
			center : myCenter,
			mapTypeId : google.maps.MapTypeId.ROADMAP
		});

		refreshMap();
	}

	function clearClusters(e) {
		e.preventDefault();
		e.stopPropagation();
		markerClusterer.clearMarkers();
	}

	google.maps.event.addDomListener(window, 'load', initialize);
</script>

<div>
	<div id="map-container">
		<div id="map"></div>
	</div>
</div>