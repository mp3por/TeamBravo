<%@ include file="/WEB-INF/include.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<script type="text/javascript"
	src="<c:url value="/resources/js/jquery.bootstrap-touchspin.js" />"></script>
  <script type="text/javascript"
	src="<c:url value="/resources/js/moment.js" />"></script>

 <script type="text/javascript"
	src="<c:url value="/resources/js/bootstrap-datetimepicker.js" />"></script>

<script type="text/javascript">

	//Create the tooltips only when document ready

	$(document)
			.ready(
					function() {
						
						$('.addedTweetText')
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
																		"<a href='"+tooltipLinks[i]+toReplace+"' class='URLTooltip' title='"
																				+toReplace+tooltipTexts[i]+"'>"
																				+ "<span class='"+tooltipImgs[i]+"'></span>"
																				+ toReplace
																				+ "</a>");
													}
												}
											}
											$(this).html("<br/>"+"<p class='tweetText' id='tweetText'>"+text+"</p>");
										});
								$(".URLTooltip").each(function (){$(this).tooltip();});
					});
</script>

<br/>

       
<script type="text/javascript">
    $(function () {
        $('#datetimepicker6').datetimepicker();
        $('#datetimepicker7').datetimepicker();
        $("#datetimepicker6").on("dp.change",function (e) {
            $('#datetimepicker7').data("DateTimePicker").minDate(e.date);
        });
        $("#datetimepicker7").on("dp.change",function (e) {
            $('#datetimepicker6').data("DateTimePicker").maxDate(e.date);
        });
    });
</script>

<div id="" style="overflow-y: scroll; height:400px;">
<ul>
	<!-- Loop over the tweets  -->
	<c:forEach var="tweet" items="${tweets}">
		<div class="tweet" class="added_tweetwall_li"><img
			src='${fn:replace(tweet.user.profile_image_url, "_normal", "")}'
			id="avatar" class="added_tweetwall_avatar" /> <!-- Loop over the elements of the tweet --> 
				<h3 class="added_tweetwall_h3">${tweet.user.screen_name}</h3>
			
				<h4 class="added_tweetwall_h4"><b>${fn:substringBefore(tweet.created_at,'+')}</b></h4>
			
				<div class="addedTweetText" text="${tweet.text}"
					person="${tweet.Person}" location="${tweet.Location}"
					organization="${tweet.Organization}" userID="${tweet.UserID}"
					hashtag="${tweet.Hashtag}" URL="${tweet.URL}"></div>
			</div>
	</c:forEach>
</ul>
</div>