<%@ include file="/WEB-INF/include.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>

<%@include file="Terrier.jsp" %>

<head>
<link href="<c:url value="/resources/css/tweets.css" />" rel="stylesheet">

</head>
<body>
	<h1>Terrier Search Results</h1>

		<ul>
			<!-- Loop over the tweets  -->

			<c:forEach var="tweet" items="${tweets}">
				
				<li class="tweet">
					<img src='${fn:replace(tweet.user.profile_image_url, "_normal", "")}' class="avatar"/>
					<!-- Loop over the elements of the tweet -->
					<c:if test="${tweet.containsKey('user')}">
						<h3>${tweet.user.screen_name}</h3>
					</c:if>
					<c:if test="${tweet.containsKey('created_at')}">	
						<b>${tweet.created_at}</b>
					</c:if>
					<c:if test="${tweet.containsKey('text')}">
						<p>${tweet.text}</p>
					</c:if>					
					
					<c:if test="${not empty tweet.Person}">
					<c:forEach var="NE" items="${tweet.Person}">
						<p>
							<img src=<c:url value="resources/img/user91.png"/> class="icon_img"/>
							<p title='${NE} is a person' class="masterTooltip">${NE}</p><br>
						
					</c:forEach>
					</c:if>
					
					<c:forEach var="NE" items="${tweet.Location}">
					<p>
						<img src=<c:url value="resources/img/world90.png"/> class="icon_img"/>
							<p title='${NE} is a location' class="masterTooltip">${NE}</p><br>
					</c:forEach>
					
					<c:forEach var="NE" items="${tweet.Emoticon}">
					<p>
						<img src=<c:url value="resources/img/smiling36.png"/> class="icon_img"/>
							<p title='${NE} is an emoticon' class="masterTooltip">${NE}</p><br>
					</c:forEach>
					
					<c:forEach var="NE" items="${tweet.UserID}">
						<p>
							<img src=<c:url value="resources/img/at2.png"/> class="icon_img"/>
							<p title='${NE} is a mentioned user' class="masterTooltip">${NE}</p><br>
					</c:forEach>
					
					<c:forEach var="NE" items="${tweet.URL}">
						<p>
							<img src=<c:url value="resources/img/external1.png"/> class="icon_img"/>
							<p title='${NE} is the URL' class="masterTooltip">${NE}</p><br>
					</c:forEach>
					
					<c:forEach var="NE" items="${tweet.Hashtag}">
					<p>
						<img src=<c:url value="resources/img/internet60.png"/> class="icon_img"/>
						<p title='${NE} is a hashtag' class="masterTooltip">${NE}</p><br>
					</c:forEach>
					
					<c:forEach var="NE" items="${tweet.Organization}">
					<p>
							<img src=<c:url value="resources/img/factory6.png"/> class="icon_img"/>
							<p title='${NE} is an organization' class="masterTooltip">${NE}</p><br>
					</c:forEach>
					

				</li>
			</c:forEach>
		</ul>
	</div>
</body>
</html>