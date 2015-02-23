<%@ include file="/WEB-INF/include.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<button type="button" onClick="window.location.reload();">Reload</button>

	<ul>
		<!-- Loop over the tweets  -->

		<c:forEach var="tweet" items="${tweets}">

			<li class="tweet"><img
				src='${fn:replace(tweet.user.profile_image_url, "_normal", "")}'
				class="avatar" /> <!-- Loop over the elements of the tweet --> <c:if
					test="${tweet.containsKey('user')}">
					<p class="Username" id="tweets_wall_username_id">${tweet.user.screen_name}</p>
				</c:if> <c:if test="${tweet.containsKey('created_at')}">
					<b>${fn:substringBefore(tweet.created_at,'+')}</b>
				</c:if> <c:if test="${tweet.containsKey('text')}">
					<p>${tweet.text}</p>
				</c:if> <c:forEach var="NE" items="${tweet.Person}">
					<p>
						<img src=<c:url value="resources/img/user91.png"/>
							class="icon_img" />
					<div class="xTooltip"><a href="http://en.wikipedia.org/w/index.php?search=${NE}">${NE}</a></div>

					<div class="PersonTooltip" title=${NE}
						style="visibility: hidden; display: none;"> <!-- This class should hide the element, change it if needed -->
					    						
							</div>
					<br />

				</c:forEach> <c:forEach var="NE" items="${tweet.Location}">

					<img src=<c:url value="resources/img/world90.png"/>
						class="icon_img" />
					<div class="xTooltip" title=${NE}>
						<a href="https://www.google.co.uk/maps/search/${NE}">${NE}</a>
					</div>
					<div class="LocationTooltip" title=${NE} style="visibility: hidden; display: none;"></div>
					<br />

				</c:forEach> <c:forEach var="NE" items="${tweet.Organization}">
					<p>
						<img src=<c:url value="resources/img/factory6.png"/>
							class="icon_img" />
					<div class="xTooltip"><a href="http://en.wikipedia.org/w/index.php?search=${NE}">${NE}</a></div>

					<div class="OrganizationTooltip" title=${NE}
						style="visibility: hidden; display: none;"></div>
					<br />

				</c:forEach> <c:forEach var="NE" items="${tweet.UserID}">
					<c:set var="UserIDTemplate" scope="page" value="<a href='http://en.wikipedia.org/w/index.php?search='>Something</a>"/>
					<% /*${fn:replace(tweet.text, ${NE}, ${UserIDTemplate})}*/ %>
					
					<img src=<c:url value="resources/img/at2.png"/> class="icon_img" />
					<div class="xTooltip" title=${NE}>
						<a href="http://twitter.com/${NE}">${NE}</a>
					</div>
					<div class="UserIDTooltip" title=${NE
						} style="visibility: hidden; display: none;"></div>
					<br />
					

				</c:forEach> <c:forEach var="NE" items="${tweet.URL}">
					<img src=<c:url value="resources/img/external1.png"/>
						class="icon_img" />
					<div class="xTooltip" title=${NE}>
						<a href="${NE}">${NE}</a>
					</div>
					<div class="URLTooltip" title=${NE
						} style="visibility: hidden; display: none;"></div>
					<br />

				</c:forEach> <c:forEach var="NE" items="${tweet.Hashtag}">

					<img src=<c:url value="resources/img/internet60.png"/>
						class="icon_img" />
					<div class="xTooltip" title="${NE}">
						<a href="https://twitter.com/hashtag/${fn:replace(NE,'#', '')}">
							${NE} </a>
					</div>
					<div class="HashtagTooltip" title=${NE
						} style="visibility: hidden; display: none;"></div>
					<br />

				</c:forEach></li>
		</c:forEach>
	</ul>