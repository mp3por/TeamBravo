<%@ include file="/WEB-INF/include.jsp"%>
<%@ include file="/WEB-INF/views/main/mainTweetwall.jsp"%>

<html>
<head>


<link href="<c:url value="/resources/css/graphs.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/tweets.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">

<link href="<c:url value="/resources/css/stat.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/maps.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/c3CSS.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/bootstrap-datetimepicker.css" />" rel="stylesheet">


<!-- jQuery -->
<script src="<c:url value="/resources/js/jquery-1.11.2.min.js" />"></script>

<!-- tiles -->
<script src="<c:url value="/resources/js/main/tilesFunctionality.js" />"></script>

<!-- maps -->
<script src="https://maps.googleapis.com/maps/api/js?sensor=false&region=GB"></script>
<script src="<c:url value="/resources/js/maps/markerclustererplus.js" />"></script>
<script src="<c:url value="/resources/js/maps/mapsJS.js" />"></script>

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

		<div id="row0" class="row"></div>

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

								<label class="radio" for="radios-0"> <input type="radio" name="type" id="radios-0" value="0" checked="checked"> Maps
								</label> <label class="radio" for="radios-1"> <input type="radio" name="type" id="radios-1" value="1"> Graphs
								</label> <label class="radio" for="radios-2"> <input type="radio" name="type" id="radios-2" value="2"> Tweet Wall
								</label> <label class="radio" for="radios-3"> <input type="radio" name="type" id="radios-3" value="3"> Stastistics
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
	$('#add_more_form').on('submit', function(e) { // use on if jQuery 1.7+
		console.log("submit");
		e.preventDefault(); // prevent form from submitting
		var data = $("#add_more_form").serializeArray();
		console.log(data[0].value);
		var toAdd = data[0].value;
		// debugger;
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

	function reloadStats(param) {

		var index = param.data.param1
		$(this).addClass('active').siblings().removeClass('active');
		var time = $(this).text();
		var linkStr;
		if (time == 'past day')
			linkStr = 'pastDay';
		else if (time == 'past month')
			linkStr = 'pastMonth';
		else if (time == 'past week')
			linkStr = 'pastWeek';
		else
			linkStr = 'allTime';

		$
				.ajax({
					url : '/TeamBravo/counter/getStats/' + linkStr,
					success : function(data) {
						$('#tile_content' + index).html(data);
						$('#added_stat_container').attr('id',
								'stat_container' + index);
					}
				});
	}
	function settingsButtonClick(clicked) {
		var settings = $('#settings' + clicked.id);
		settings.show();
	}

	function getStastics(container_id, index) {
		$.ajax({
			url : '/TeamBravo/counter/test',
			success : function(data) {
				initStatistics(data, index);
			}
		});
	}

	function initStatistics(data, index) {
		$('#tile_content' + index).html(data);
	}

	function getStastics(container_id, index) {
		$
				.ajax({
					url : '/TeamBravo/counter/stats/getSettings',
					success : function(data) {
						$('#settings' + index).html(data);
						$('#stat_time_setting').attr('id',
								'stat_time_setting' + index);
						$('#stat_time_setting_label').attr('for',
								'stat_time_setting' + index);
						$('#stat_time_setting' + index + ' button').click({
							param1 : index
						}, reloadStats);
					}
				});
		$
				.ajax({
					url : '/TeamBravo/counter/getStats/allTime',
					success : function(data) {
						$('#tile_content' + index).html(data);
						$('#added_stat_container').attr('id',
								'stat_container' + index);
						//initStatistics(data, index);
					}
				});
	}

	//      function initStatistics(data, index) {
	//              $('#tile_content' + index).html(data);
	//              $('#added_stat_container').attr('id', 'stat_container' + index);
	//              $('#settings' + index).html
	//      }

	function graphInit() {
		$.ajax({
			url : '/TeamBravo/graphs/graphInit',
			async : false, //Quick fix, remove later
			success : function(data) {
				console.log("Graphs Initialised");
			}
		});
	}

	function getSearchBox() {
		$.ajax({
			url : '/TeamBravo/search/searchBox',
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