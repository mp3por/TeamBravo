<!DOCTYPE html>
<html lang = "en">
<head>
    <style type="text/css">
        html{height: 100%}
        body{height: 100%; margin: 0; padding: 0}
        #map-canvas{height: 100%}
    </style>
    <title>GMaps Demo</title>
    <script src = "http://maps.googleapis.com/maps/api/js?sensor=false&region=GB"></script>
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
            var map = new google.maps.Map(document.getElementById("map-canvas"), mapOptions);
        	
        	//Add the marker
        	var marker=new google.maps.Marker({ position:myCenter, });
        	marker.setMap(map); 
        	
        }

		// add the map to the DOM
        google.maps.event.addDomListener(window, 'load', initialize);
        </script>
</head>
<body>
	<div id= "testing" >
	The location is centered at 
	
	${latitude},
	${longitude}
	</div>
    <div id = "map-canvas" style="width:500px;height:380px;" >
    </div>
</body>
</html>