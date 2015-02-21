<%@ include file="/WEB-INF/include.jsp"%>

<html>

<head>

<meta charset="utf-8">
<title></title>

<link href="<c:url value="/resources/css/stat.css" />" rel="stylesheet">

<!-- jQuery -->
<script src="<c:url value="/resources/js/jquery-1.11.2.min.js" />"></script>

<!-- bootstrap -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

</head>

<body>

	<div id="stat-container" class="row">
		<div class="col-md-12 col-sm-12 col-xs-12">

			<div class="row">
				<div class="col-md-6 col-sm-6 col-xs-6">
					<h4>
						<small>TOTAL TWEETS</small><br> 3625
					</h4>
				</div>
				<div class="col-md-6 col-sm-6 col-xs-6">
					<h4>
						<small>TOTAL RETWEETED TWEETS</small><br> 1500
					</h4>
				</div>
			</div>

			<hr class="horizontal-line ">

			<div class="row">
				<div class="col-md-6 col-sm-6 col-xs-6">
					<img src=<c:url value="resources/img/user91.png"/>
						class="user_icon" />
					<h4>
						<small>MOST ACTIVE USER</small><br> TEMP_USERNAME
					</h4>
				</div>

				<div class="col-md-6 col-sm-6 col-xs-6">
					<img src=<c:url value="resources/img/user91.png"/>
						class="user_icon" />
					<h4>
						<small>MOST MENTIONED USER</small><br> TEMP_USERNAME
					</h4>
				</div>
			</div>

			<hr class="horizontal-line ">

			<div class="row">
				<div class="col-md-12 col-sm-12 col-xs-12">
					<h4 class="mini stat_general_topic">
						<small>MOST POPULAR</small><br>
					</h4>
				</div>
			</div>

			<div class="row">
				<div class="col-md-4 col-sm-4 col-xs-4">
					<h4 class="subtopic">
						<small>HASHTAG </small><br> TEMP_HASHTAG
					</h4>
				</div>

				<div class="col-md-4 col-sm-4 col-xs-4">
					<h4 class="subtopic">
						<small>LOCATION</small><br> TEMP_LOCATION
					</h4>
				</div>

				<div class="col-md-4 col-sm-4 col-xs-4">
					<h4 class="subtopic">
						<small>PERSON</small><br> TEMP_PERSON
					</h4>
				</div>
			</div>

			<hr class="horizontal-line ">

			<div class="row">
				<div class="col-md-12 col-sm-12 col-xs-12">
					<img src=<c:url value="resources/img/smiling36.png"/>
						class="stat_icon" />
					<h4 class="mini">
						<small>MOST FAVOURITED TWEET</small>
					</h4>
					<blockquote class="mini">
						<p class="mini">02:38 listening to Never Shout Never's and
							drinking hot chocolate</p>
						<footer>temp_username</footer>
					</blockquote>
				</div>
			</div>

			<hr class="horizontal-line ">

			<div class="row">
				<div class="col-md-12 col-sm-12 col-xs-12">
					<img src=<c:url value="resources/img/smiling36.png"/>
						class="stat_icon" />
					<h4 class="mini">
						<small>MOST FAVOURITED TWEET</small>
					</h4>
					<blockquote class="mini">
						<p class="mini">Let's get rich and build a house on a moutain
							making every body looks like ant. You and I :)</p>
						<footer>temp_username</footer>
					</blockquote>
				</div>
			</div>

		</div>
	</div>

</body>

</html>