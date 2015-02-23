<%@ include file="/WEB-INF/include.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<link href="<c:url value="/resources/css/jquery.qtip.css" />"
	rel="stylesheet">
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/imagesloaded.pkg.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/jquery.qtip.js" />"></script>


<script type="text/javascript">
$(function () {
    $("[rel='tooltip']").tooltip();
});

	//Create the tooltips only when document ready
	
	$(document)
			.ready(
					function() {

											$('.URLTooltip')
													.each(
															function() {
																title = $(this)
																		.attr(
																				"title");
																$(this)
																		.html(
																				title
																						+ " is an URL. Click on it to open it")
															});

											$('.HashtagTooltip')
													.each(
															function() {
																title = $(this)
																		.attr(
																				"title");
																$(this)
																		.html(
																				title
																						+ " is a hashtag. Click on it to search for this hashtag in Twitter");

															});

											$('.UserIDTooltip')
													.each(
															function() {
																title = $(this)
																		.attr(
																				"title");
																$(this)
																		.html(
																				title
																						+ " is a mentioned user on Twitter. Click to see the profile");
															});

											$('.LocationTooltip')
													.each(
															function() {
																title = $(this)
																		.attr(
																				"title");
																$(this)
																		.html(
																				title
																						+ " is a location. Click to search for this place in Google Maps");

															});
											$('.PersonTooltip')
													.each(
															function() {
																title = $(this)
																		.attr(
																				"title");
																$(this)
																		.html(
																				title
																						+ " is a Person. Click to search for this place in Wikipedia");

															});

											$('.OrganizationTooltip')
													.each(
															function() {
																title = $(this)
																		.attr(
																				"title");
																$(this)
																		.html(
																				title
																						+ " is an Organization. Click to search for this place in Wikipedia");

															});
	$('.tweetwall_tweet').each(
			function() {
				
				text = $(this).attr("text");
				console.log(text);
				
				var tooltipValues = [
					$(this).attr("person"),
					$(this).attr("location"),
					$(this).attr("organization"),
					$(this).attr("userID"),
					$(this).attr("hashtag"),
					$(this).attr("URL")
				];
				console.log(tooltipValues);
				
				
				var tooltipTexts = [
					" is a person. Click to search for this place in Wikipedia",
					" is a location. Click to search for this place in Google Maps",  
					" is an organization. Click to search for this place in Wikipedia",
					" is a mentioned user on Twitter. Click to see the profile",
					" is a hashtag. Click on it to search for this hashtag in Twitter",
					" is an URL. Click on it to open it"
					];
				var tooltipImgs = [
					"resources/img/user91.png",
					"resources/img/world90.png",
					"resources/img/factory6.png",
					"resources/img/at2.png",
					"resources/img/internet60.png",
					"resources/img/external1.png"
				];
				var tooltipLinks = [
					"http://en.wikipedia.org/w/index.php?search=",
					"https://www.google.co.uk/maps/search/",
					"http://en.wikipedia.org/w/index.php?search=",
					"http://twitter.com/",
					"https://twitter.com/hashtag/",
					""
				];
				randomText = '<a href="#" data-toggle="tooltip" title="Title Here">Hyperlink Text</a>'; 
				$(this).html(randomText + " Zis " + text);
			}
			);
    $("body").tooltip({ selector: '[data-toggle=tooltip]' } );
		
					});
	
</script>



<a href="http://www.google.com" class="yTooltip" title="Google">Hey</a>
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
			</c:if> 
			 <c:if test="${tweet.containsKey('text')}">
				<div class="tweetwall_tweet"
					text="${tweet.text}"
					person="${tweet.Person}"
					location="${tweet.Location}"
					organization="${tweet.Organization}"
					userID="${tweet.UserID}"
					hashtag="${tweet.Hashtag}"
					URL="${tweet.URL}"
					>
				</div>
			</c:if> </li>
	</c:forEach>
</ul>