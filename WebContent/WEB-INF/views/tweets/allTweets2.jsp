<%@ include file="/WEB-INF/include.jsp"%>
<html>
<head>
<link href="<c:url value="/resources/css/tweets.css" />" rel="stylesheet">
</head>
<body>
	<h1>Tweets Wall</h1>

	<div>
		<ul>
			<!-- Loop over the tweets  -->
			<c:forEach var="tweet" items="${tweets}">

				<li>
					<img src="http://lorempixum.com/100/100/nature/1">
					<!-- Loop over the elements of the tweet -->

					<c:if test="${tweet.containsKey('name')}">
						<h3>${tweet.name}</h3>
					</c:if>
					<c:if test="${tweet.containsKey('time')}">	
						${tweet.time}
					</c:if>
					<c:if test="${tweet.containsKey('location')}">
						<b> ${tweet.location} </b>
					</c:if>
					<c:if test="${tweet.containsKey('text')}">
						<p>${tweet.text}</p>
					</c:if>

				</li>
			</c:forEach>
		</ul>
	</div>
</body>
</html>