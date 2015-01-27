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
					<c:forEach items="${tweet}" var="entry">

						<%-- Key = ${entry.key}, value = ${entry.value} --%>
						<c:set var="key" value="${entry.key}" />
						<c:set var="value" value="${entry.value}" />

						<c:choose>
							<c:when test="${key == 'name'}">
								<h3>${value}</h3>
							</c:when>
							<c:when test="${key == 'time'}">
								${value}
							</c:when>
							<c:when test="${key == 'location'}">
								<b>${value}</b>
							</c:when>
							<c:when test="${key == 'text'}">
								<p>${value}</p>
							</c:when>
							<c:otherwise>...</c:otherwise>
						</c:choose>
					</c:forEach>
				</li>
			</c:forEach>
		</ul>
	</div>
</body>
</html>