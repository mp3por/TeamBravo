<%@ include file="/WEB-INF/include.jsp"%>
<head>

	<script>
		// get local JS variable
		var long1 = ${longitude};
		var lat = ${latitude};
		var zoom = ${zoom};
		
		// initialize the map
		function initialize(){
			var myCenter = new google.maps.LatLng(lat,long1);	
			var mapOptions = {
				center: myCenter, //new google.maps.LatLng(lat, long),
				zoom: zoom,
				mapTypeId: google.maps.MapTypeId.ROADMAP,
			};
			var map = new google.maps.Map(document.getElementById("map"), mapOptions);
		   	//Add the marker
		   	var marker=new google.maps.Marker({ position:myCenter, });
		   	marker.setMap(map); 
		}
				
		// add the map to the DOM
		google.maps.event.addDomListener(window, 'load', initialize);
	</script>
</head>


