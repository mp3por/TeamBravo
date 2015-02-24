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

<title>Search Results Page</title>

</head>

<body>

	<!--------------------------- BAR ------------------------------------>
	<header>
		<div id='logo'>
			<img src="/TeamBravo/resources/img/GreyRedMackintosh2.png"
				style="width: 30%;">
			<img src="/TeamBravo/resources/img/Terrier.png"
				style="width: 30%;">
		</div>
		<div id='cssmenu'>
			<ul id='naviMenu'>
				<li class='active'><a href='http://localhost:8080/TeamBravo/main/home'>
								<span>BACK TO MAIN PAGE</span></a></li>
			</ul>
		</div>
	</header>
	<!-- -------------------------------------------------------------- -->

	<!-- MAIN OUTLOOK TABLE  -->

	<div class="container-fluid">
		Search returned ${numberOfTweets} tweets for keywords "${query}".
		<div id="row0" class="row"></div>
		
	</div>

	<!-- -------------------------------------------- -->

</body>

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
			addTile("2");
			addTile("3");
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
				tile_title.text("Locations of Tweets with Keyword '${query}'");
				getMaps('tile_content' + c, c);
				break;
			case "1":// add graphs
				tile_title.text("Graphs for Tweets with Keyword '${query}'");
				getGraphs("tile_content" + c, c);
				break;
			case "2":// add tweetwall
				tile_title.text("Tweet Wall for Tweets with Keyword '${query}'");
				getTweetwall("tile_content" + c, c);
				break;
			case "3":
				tile_title.text("General Statistics for Tweets with Keyword '$query'");
				getStastics("tile_content" + c, c);
				break;
			}
			current_num_of_tiles += 1;
		} else {
			alert("Something is wrong! toAdd: " + toAdd + ", tile_template: "
					+ tile_template);
		}
	}
	
	function getStastics(container_id, index){
		var img = document.createElement("img");
		img.src = 
			"https://dl-web.dropbox.com/get/fake_stat.png?_subject_uid=96006775&w=AABe7KABsfKQUdwmrjymXE96pxDcgIk9wbw1K_XCdMG69A";
		
		img.style.height = '600px';
	    img.style.width = '610px';
		
		var src = document.getElementById("tile3");
		src.appendChild(img);	
	}
	
	function getGraphs(container_id, index){
		var img = document.createElement("img");
		img.src = 
			"https://dl-web.dropbox.com/get/anychart1.jpg?_subject_uid=96006775&w=AABmUPooiXMworxwJmxaNjbhhCFAaQemNGnt7dlG9jClzQ";
		
		var src = document.getElementById("tile1");
		src.appendChild(img);
	}

	function getMaps(container_id, index) {
		var img = document.createElement("img");
		img.src = 
			"https://dl-web.dropbox.com/get/fake_map.png?_subject_uid=96006775&w=AADfkGiai_-39KpvfdLdpfjNETeeyFtMG06rCWThgEeLoQ";
		
		img.style.height = '450px';
	    img.style.width = '610px';
		
		var src = document.getElementById("tile0");
		src.appendChild(img);
	}
		
	function getTweetwall(container_id, index) {
		console.log("Getting tweetwall: " + container_id);
		$.ajax({
			url : '/TeamBravo/search/terrier/tweetwall/${query}',
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
	
	function initWall(container_id, data, index) {
		//debugger;
		$('#tile_content'+index).append(data);
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

	function fixTemplate(c) {
		console.log("fixTemplate " + c);
		$('#template_column_id').attr('id', 'tile' + c);
		$('#template_title').attr('id', 'tile_title' + c);
		$('#template_submit_button').attr('id', c);
		$('#template_settings_div').attr('id', 'settings' + c);
		$('#template_content').attr('id', 'tile_content' + c);
	}

</script>
</html>
