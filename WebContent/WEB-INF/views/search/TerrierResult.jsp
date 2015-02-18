<%@ include file="/WEB-INF/include.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>

<%@include file="Terrier.jsp" %>

<form method="get" target="_blank" action= "${requestScope['javax.servlet.forward.request_uri']}/rank" 
      onsubmit="window.open(${requestScope['javax.servlet.forward.request_uri']} + '/rank', '_self'); return false;">
    <input type="submit" value="Rank by retweeted count" style="vertical-align: bottom; cursor: pointer; font-size: 11px;"/>
</form>


<head>
<link href="<c:url value="/resources/css/tweets.css" />" rel="stylesheet">

</head>
<body>
	    Search returned ${count} tweets.
	    
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
					<c:if test="${tweet.containsKey('retweet_count')}">
						<p>Retweeted Count: ${tweet.retweet_count}</p>
					</c:if>										
				</li>
				
			</c:forEach>
		</ul>
</body>
</html>