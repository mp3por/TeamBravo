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
<script src="<c:url value="/resources/js/maps/markerclustererplus.js" />"></script>
<script src="<c:url value="/resources/js/maps/mapsJS.js" />"></script>

<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

<!-- bootstrap -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

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
				<li><a href='/TeamBravo/main/home'>
								<span>Home</span></a>
				</li>
				<li><a href='/TeamBravo/search/terrier/${query}'>
								<span>Results Page</span></a>
				</li>
				<li>
					<a href='/TeamBravo/search/graphs/${query}'>
								<span>Graphs for ${query}</span></a>
				</li>		
			</ul>
			<div id="search"></div>
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
					url : '/TeamBravo/search/tile_template_search',
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
			getSearchBox();
			addTile("2");
			addTile("0");
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
				url : '/TeamBravo/search/tile_template_search',
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
		
		var src = document.getElementById("tile2");
		src.appendChild(img);
	}

	function getMaps(container_id, index) {
		console.log("getting maps: " + container_id);
		$.ajax({
			url : '/TeamBravo/search/terrier/maps/{query}',
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
				//console.log(data);
				$('#settings' + index).html(data);
				$('#settings_template_form').attr('tile', index);
				$('#settings_template_form')
						.attr('id', 'settings_form' + index);
				$('#settings_button_template').attr('id',
						'settings_button' + index);
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
						} else if (p == "user"){
							$('#tooltip_user'+index).show();
						} else if (p == "time"){
							$('#tooltip_time'+index).show();
						}
					}

					//$('#settings_button'+index).click();
				});
			}
		});
	}

	function initMaps(container_id, index, needed, tweets_info) {
		//debugger;
		console.log("init maps");
		$('#' + container_id).append(needed);

		$('#added_map_container').attr('id', 'map_container' + index);
		$('#added_map_div').attr('id', 'map' + index);

		google.maps.event.addDomListener(window, 'load', initialize('map'
				+ index, index, tweets_info));
	}
		
	function getTweetwall(container_id, index) {
		$.ajax({
			url : '/TeamBravo/search/terrier/tweetwall/${query}',
			success : function(data) {
				console.log("index: " + index);
				console.log("cont_id: " + container_id);
				initWall("tile_content"+index, data, index);
			}
		});		
	}
	
	function rankedByRetweeted(container_id, index){
		$.ajax({
			url : '/TeamBravo/search/terrier/tweetwall/retweeted/${query}',
			success: function(data) {
				console.log("index: " + index);
				console.log("cont_id: " + container_id);
				initWall("tile_content" + index, data, index);
			}
		});
	}
	
	function rankedByPosted(container_id, index){
		$.ajax({
			url : '/TeamBravo/search/terrier/tweetwall/recent/${query}',
			success: function(data) {
				console.log("index: " + index);
				console.log("cont_id: " + container_id);
				initWall("tile_content" + index, data, index);
			}
		});
	}
	
	function rankedByFavourited(container_id, index){
		$.ajax({
			url : '/TeamBravo/search/terrier/tweetwall/favourite/${query}',
			success: function(data) {
				console.log("index: " + index);
				console.log("cont_id: " + container_id);
				initWall("tile_content" + index, data, index);
			}
		});
	}
	
	function initWall(container_id, data, index) {

		$('#tile_content'+index).html(data);
		
		$('#settings'+index).html('<p>Rank the results</p>'+
					'<button type="button" onclick="rankedByRetweeted(tile_content0, 0);" id="rank_retweeted" index="rank_by_retweeted" class="btn btn-default rank_retweeted">Rank by retweeted times</button>'+
					'<button type="button" onclick="rankedByFavourited(tile_content0, 0);" id="rank_favourited" index="rank_by_favourited" class="btn btn-default rank_favourited">Rank by favourited times</button>'+
					'<button type="button" onclick="rankedByPosted(tile_content0, 0);" id="rank_posted" index="rank_by_posted" class="btn btn-default rank_posted">See most recent tweets</button>'+			
					'<br/><br />');
		
		$('#rank_by_retweeted').each( function () {
			$(this).attr("index", index);
		});
		
		$('#rank_by_favourited').each( function () {
			$(this).attr("index", index);
		});
		
		$('#rank_by_posted').each( function () {
			$(this).attr("index", index);
		});
		
		console.log("init wall");
	}
	
	function getSearchBox() {
		$.ajax({
			url : '/TeamBravo/search/searchBox',
			success : function(data) {
				$("#search").html(data);
			}
		});
	}
	
	function settingsButtonClick(clicked) {
		//debugger;
		var settings = $('#settings' + clicked.id);
		var p = $(clicked).attr("opened");
		if (p == '0') {
			settings.show();
			$(clicked).attr("opened", "1");
		} else {
			settings.hide();
			$(clicked).attr("opened", "0");
		}
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
