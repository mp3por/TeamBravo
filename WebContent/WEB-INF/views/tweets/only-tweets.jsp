<%@ include file="/WEB-INF/include.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<link href="<c:url value="/resources/css/jquery.qtip.css" />"
	rel="stylesheet">
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/imagesloaded.pkg.min.js" />"></script>



<script type="text/javascript">
	$(function() {
		$("[rel='tooltip']").tooltip();
	});

	//Create the tooltips only when document ready

	$(document)
			.ready(
					function() {
						
						$('.tweetwall_tweet')
								.each(
										function() {

											text = $(this).attr("text");
											

											var tooltipValues = [
													$(this).attr("person"),
													$(this).attr("location"),
													$(this)
															.attr(
																	"organization"),
													$(this).attr("userID"),
													$(this).attr("hashtag"),
													$(this).attr("URL") ];
											

											var tooltipTexts = [
													" is a person. Click to search for this place in Wikipedia",
													" is a location. Click to search for this place in Google Maps",
													" is an organization. Click to search for this place in Wikipedia",
													" is a mentioned user on Twitter. Click to see the profile",
													" is a hashtag. Click on it to search for this hashtag in Twitter",
													" is an URL. Click on it to open it" ];
											var tooltipImgs = [
													"glyphicon glyphicon-user",
													"glyphicon glyphicon-map-marker",
													"glyphicon glyphicon-briefcase",
													"glyphicon glyphicon-share-alt",
													"glyphicon glyphicon-tag",
													"glyphicon glyphicon-link" ];
											var tooltipLinks = [
													"http://en.wikipedia.org/w/index.php?search=",
													"https://www.google.co.uk/maps/search/",
													"http://en.wikipedia.org/w/index.php?search=",
													"http://twitter.com/",
													"https://twitter.com/hashtag/",
													"" ];
											for (i = 0; i < tooltipValues.length; i++) {
												tooltipValues[i] = tooltipValues[i]
														.replace("[", "");
												tooltipValues[i] = tooltipValues[i]
														.replace("]", "");
												tooltipValues[i] = tooltipValues[i]
														.split(",");
												for (s in tooltipValues[i]) {
													if (tooltipValues[i][s]
															.trim()) {
														var toReplace = tooltipValues[i][s]
																.trim();
														if (i == 3) {
															toReplace = "@"
																	+ toReplace;
														}
														
														text = text
																.replace(
																		toReplace,
																		"<a href='"+tooltipLinks[i]+toReplace+"' data-toggle='tooltip' title='"+
							toReplace+tooltipTexts[i]+"'>"
																				+ "<span class='"+tooltipImgs[i]+"'></span>"
																				+ toReplace
																				+ "</a>");
														//toReplace+tooltipTexts[i]+"'><img src=<c:url value='"+tooltipImgs[i]+"'/> class='icon_img' />"+toReplace+"</a>");
													}
												}
											}
											$(this).html("<br/>"+"<p class=tweetText>"+text+"</p>");
										});
						$("body").tooltip({
							selector : '[data-toggle=tooltip]'
						});
					});
</script>

<br/>
<div id="" style="overflow-y: scroll; height:400px;">
<ul>
	<!-- Loop over the tweets  -->

	<c:forEach var="tweet" items="${tweets}">
		<li class="tweet"><img
			src='${fn:replace(tweet.user.profile_image_url, "_normal", "")}'
			class="avatar" /> <!-- Loop over the elements of the tweet --> 
				<h3>${tweet.user.screen_name}</h3>
			
				<h4><b>${fn:substringBefore(tweet.created_at,'+')}</b></h4>
			
				<div class="tweetwall_tweet" text="${tweet.text}"
					person="${tweet.Person}" location="${tweet.Location}"
					organization="${tweet.Organization}" userID="${tweet.UserID}"
					hashtag="${tweet.Hashtag}" URL="${tweet.URL}"></div>
			</li>
	</c:forEach>
</ul>
</div>