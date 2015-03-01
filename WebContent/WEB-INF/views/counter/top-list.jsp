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

<script type="text/javascript">
	
</script>

</head>

<body>

	<div id="toplist-container" class="row">
		<div class="col-md-12 col-sm-12 col-xs-12">

			<c:forEach var="topItem" items="${item}">

				<div class="row">
					<div class="col-md-12 col-sm-12 col-xs-12">
						<h5 class="mini stat_general_topic">
							${item._id}
						</h5>
					</div>
				</div>

			</c:forEach>
			
		</div>
	</div>