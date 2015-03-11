<%@ include file="/WEB-INF/include.jsp"%>
<%@ include file="/WEB-INF/views/main/mainTweetwall.jsp"%>
<%@ include file="/WEB-INF/views/main/mainStats.jsp"%>

<html>
<head>
<link href="<c:url value="/resources/css/graphs.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/css/tweets.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/css/styles.css" />"
	rel="stylesheet">

<link href="<c:url value="/resources/css/stats.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/maps.css" />" rel="stylesheet">

<link href="<c:url value="/resources/css/c3CSS.css" />" rel="stylesheet">
<link
	href="<c:url value="/resources/css/bootstrap-datetimepicker.css" />"
	rel="stylesheet">



<!-- jQuery -->
<script src="<c:url value="/resources/js/jquery-1.11.2.min.js" />"></script>


<!-- tiles -->
<script src="<c:url value="/resources/js/main/tilesFunctionality.js" />"></script>


<!-- maps -->
<script
	src="https://maps.googleapis.com/maps/api/js?sensor=false&region=GB"></script>
<script
	src="<c:url value="/resources/js/maps/markerclustererplus.js" />"></script>
<script src="<c:url value="/resources/js/maps/mapsJS.js" />"></script>

<!-- Optional theme -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

<!-- bootstrap -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">


<!-- graphs -->
<script src="<c:url value="/resources/js/graphs/d3.min.js" />"></script>
<script
	src="<c:url value="/resources/js/graphs/dimple.v2.1.0.min.js" />"></script>
<script src="<c:url value="/resources/js/graphs/d3.layout.cloud.js" />"></script>
<script src="<c:url value="/resources/js/graphs/graphHandler.js" />"></script>
<script src="<c:url value="/resources/js/settingsButtons.js" />"></script>

<script type="text/javascript"
	src="<c:url value="/resources/js/jquery.bootstrap-touchspin.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/moment.js" />"></script>

<script type="text/javascript"
	src="<c:url value="/resources/js/bootstrap-datetimepicker.js" />"></script>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Home</title>

</head>
<body>
	<!--------------------------- BAR ------------------------------------>
	<header>
		<div id='logo'>
			<img src="/TeamBravo/resources/img/GreyRedMackintosh2.png"
				style="width: 30%;">
		</div>
		<div class="container-fluid">
		<div class="row" id="top-menu">
		
		<div id='cssmenu' class="col-lg-4 col-lg-offset-2">
			<ul id='naviMenu'>
				<li class='active'>
				<a href='#'><span>Home</span></a></li>
				<li class='last'><a href='#about-us'><span>About Us</span></a></li>
			</ul>
		</div>
		<div class="col-lg-4 col-lg-offset-1" id="search-padding">
			<div id="search" ></div>
			</div>
		</div> <!-- row -->
		</div>
	</header>
	<!-- -------------------------------------------------------------- -->

	<!-- MAIN OUTLOOK TABLE  -->

	<div class="container-fluid">

		<div id="row0" class="row BIG_ROW" row="0"></div>

		<div class="row" id="last_row">
			<div class="col-md-12">
				<form class="form-horizontal" id="add_more_form">
					<fieldset>

						<!-- Form Name -->
						<legend>Add more tiles </legend>

						<!-- Multiple Radios (inline) -->
						<div class="form-group">
							<label class="col-md-4 control-label" for="radios">Choose
								Tile Type</label>
							<div class="col-md-8">

								<label class="radio" for="radios-0"> <input type="radio"
									name="type" id="radios-0" value="0" checked="checked">
									Maps
								</label> <label class="radio" for="radios-1"> <input
									type="radio" name="type" id="radios-1" value="1">
									Graphs
								</label> <label class="radio" for="radios-2"> <input
									type="radio" name="type" id="radios-2" value="2"> Tweet
									Wall
								</label> <label class="radio" for="radios-3"> <input
									type="radio" name="type" id="radios-3" value="3">
									Stastistics
								</label>
							</div>
						</div>


						<!-- Button -->
						<div class="form-group">
							<label class="col-md-4 control-label" for="addMoreSubmit"></label>
							<div class="col-md-4">
								<button id="addMoreSubmit" name="addMoreSubmit" type="submit"
									class="btn btn-primary">Add</button>
							</div>
						</div>

					</fieldset>
				</form>
			</div>
		</div>
	</div>

	<!-- -------------------------------------------- -->

</body>
<footer> 
<div class="about-us" id="about-us">
<legend>About us:</legend>
<p><img src="<c:url value="/resources/img/uni_glasgow_logo.png" />" width="300px"><br/>
<h4>"A Dashboard for Monitoring Tweets in Glasgow" is a Team Project made by level 3 students at University of Glasgow. Team members:</h4>
<ul>
<li>Prapaipim Junhavittaya</li>
<li>Paulius Peciura</li>
<li>Zijian Feng</li>
<li>Steven McGuckin</li>
<li>Velin Kerkov</li>
</ul>
<h4>Project supervisors:</h4>
<ul>
<li>Dr Iadh Ounis</li>
<li>Richard McCreadie</li>
</ul>
<br/>
<a href='#'><span>Back to the top</span></a></li>
</div>
</footer>

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

	function fixDatePickers(index) {

		$(function() {
			$('#datetimepicker_from_' + index).datetimepicker({
				sideBySide : true,
				format : 'ddd MMM DD HH:mm:ss YYYY'
			});
			$('#datetimepicker_to_' + index).datetimepicker({
				sideBySide : true,
				format : 'ddd MMM DD HH:mm:ss YYYY'
			});
			$("#datetimepicker_from_" + index).on(
					"dp.change",
					function(e) {
						$('#datetimepicker_to_' + index).data("DateTimePicker")
								.minDate(e.date);
					});
			$("#datetimepicker_to_" + index).on(
					"dp.change",
					function(e) {
						$('#datetimepicker_from_' + index).data(
								"DateTimePicker").maxDate(e.date);
					});
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

	function getSearchBox() {
		$.ajax({
			url : '/TeamBravo/search/searchBox',
			success : function(data) {
				$("#search").html(data);
			}
		});
	}
</script>
</html>
