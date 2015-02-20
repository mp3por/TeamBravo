<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang = "en">
<head>
    <style type="text/css">
        html{height: 100%}
        body{height: 100%; margin: 0; padding: 0}
        #map-canvas{height: 100%}
    </style>
    <title>GMaps Demo</title>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script>
    <script src="https://maps.googleapis.com/maps/api/js?v=3.11&sensor=false&region=GB" type="text/javascript"></script>
    <script type="text/javascript">
    <script>
    	
    	// get local JS variable
        var LatArray = ${latitudes};
        var LongArray = ${longtitudes};
        var numOfTweets = ${numOfTweets};
        var markerArray[numOfTweets];
        for (i = 0; i < numOfTweets; i++) { 
        	var myCenter = new google.maps.LatLng(LatArray[i],LongArray[i]);	
        	var marker=new google.maps.Marker({ position:myCenter, });
        	marlerArray[i] = marker;
        }
        
        var clong = ${clongitude};
        var clat = ${clatitude};
        var zoom = ${zoom};
        
        var mapOptions = {
                center: myCenter, //new google.maps.LatLng(lat, long),
                zoom: zoom,
                mapTypeId: google.maps.MapTypeId.ROADMAP,
            };
        
        for (i = 0; i < locations.length; i++) {  
            marker = new google.maps.Marker({
              position: new google.maps.LatLng(locations[i][1], locations[i][2]),
              map: map
            });
        </script>
</head>
<body>
	<div id= "testing" >
	Number of markers: <div id="numOfMarkers"> ${numOfTweets} </div>>
	</div>
    <div id = "map-canvas" style="width:500px;height:380px;" >
    </div>
</body>
</html>