<%@ include file="/WEB-INF/include.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html>
<head>
<link href="<c:url value="/resources/css/tweets.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/css/jquery.qtip.css" />"
	rel="stylesheet">

<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/imagesloaded.pkg.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/jquery.qtip.js" />"></script>

<script type="text/javascript">
	function getWikiBox(title) {
		var text = "hey";
		text = getWiki(title);
		//while (!text) {}

		return text;
	};

	function getIntro(data) {
		var t;
		for (text in data.parse.text) {

			var text = data.parse.text[text].split("<p>");
			var pText = "";
			for (p in text) {
				//Remove html comment
				text[p] = text[p].split("<!--");
				if (text[p].length > 1) {
					text[p][0] = text[p][0].split(/\r\n|\r|\n/);
					text[p][0] = text[p][0][0];
					text[p][0] += "</p> ";
				}
				text[p] = text[p][0];
				//Construct a string from paragraphs
				if (text[p].indexOf("</p>") == text[p].length - 5) {
					var htmlStrip = text[p].replace(/<(?:.|\n)*?>/gm, '') //Remove HTML
					var splitNewline = htmlStrip.split(/\r\n|\r|\n/); //Split on newlines
					for (newline in splitNewline) {
						if (splitNewline[newline].substring(0, 11) != "Cite error:") {
							pText += splitNewline[newline];
							pText += "\n";
						}
					}
				}
			}
			pText = pText.substring(0, pText.length - 2); //Remove extra newline
			pText = pText.replace(/\[\d+\]/g, ""); //Remove reference tags (e.x. [1], [4], etc)
			t = pText;

		}
		return t;
	};

	function getWiki(title) {
		//Get Leading paragraphs (section 0)
		var t = "";

		$.getJSON(
				"http://en.wikipedia.org/w/api.php?action=parse&page=" + title
						+ "&prop=text&section=0&format=json&callback=?",
				function(data) {
					console.log("Success for " + title);
				}).done(function(data, callback) {
			console.log("done");
			t = getIntro(data);
			console.log("returned" + t);
		}).fail(function() {
			console.log("fail");
			t = "No additional information was found for " + title;
		}).always(function() {
			console.log("always");
			return t;
		});
	};

	//x = getWiki("Glasgow");
	//console.log(x);
	//y = getImages("Glasgow");
	//console.log(y);
</script>

<script type="text/javascript">
	//Create the tooltips only when document ready
	$(document)
			.ready(
					function() {

						$('.NETooltip').each(function() {
							console.log($(this).text());
							console.debug($(this));
							$(this).qtip({
								content : {
									text : $(this).context.innerText
								}
							})
						});

						$('.UserIDTooltip').each(function() {
							console.log($(this).text());
							console.debug($(this));
							$(this).qtip({
								content : {
									text : $(this).context.innerText
								}
							})
						});

						$('.URLTooltip').each(function() {
							console.log($(this).text());
							console.debug($(this));
							$(this).qtip({
								content : {
									text : $(this).context.innerText
								}
							})
						});

						$('.HashtagTooltip').each(function() {
							console.log($(this).text());
							console.debug($(this));
							$(this).qtip({
								content : {
									text : $(this).context.innerText
								}
							})
						});

						// Grab all elements with the class "hasTooltip"
						$('.xTooltip')
								.each(
										function() { // Notice the .each() loop, discussed below
											$(this)
													.qtip(
															{
																content : {
																	text : $(
																			this)
																			.next(
																					'div')
																// Use the "div" element next to this for the content
																},
																style : {
																	width : 250,
																	classes : 'qtip-blue qtip-shadow qtip-rounded',
																	tip : {
																		corner : true,
																	}
																}

															});
										});

						$('.NETest').each(function() {
							text = getWikiBox($(this).attr("title"));
							console.log("For each: " + $(this).attr("title"));
							console.log(text);
							$(this).html("blah" + "\n" + text);
						});

						$('.URLTooltip')
								.each(
										function() {
											title = $(this).attr("title");
											$(this)
													.html(
															title
																	+ " is an URL. Click on it to open it")
										});

						$('.HashtagTooltip')
								.each(
										function() {
											title = $(this).attr("title");
											$(this)
													.html(
															title
																	+ " is a hashtag. Click on it to search for this hashtag in Twitter");

										});

						$('.UserIDTooltip')
								.each(
										function() {
											title = $(this).attr("title");
											$(this)
													.html(
															title
																	+ " is a mentioned user on Twitter. Click to see the profile");
										});

						$('.LocationTooltip')
								.each(
										function() {
											title = $(this).attr("title");
											$(this)
													.html(
															title
																	+ " is a location. Click to search for this place in Google Maps");

										});
					});
	$('.PersonTooltip')
	.each(
			function() {
				title = $(this).attr("title");
				$(this)
						.html(
								title
										+ " is a Person. Click to search for this place in Wikipedia");

			});
	
	$('.OrganizationTooltip')
	.each(
			function() {
				title = $(this).attr("title");
				$(this)
						.html(
								title
										+ " is an Organization. Click to search for this place in Wikipedia");

			});
</script>
</head>
<body>


	<h1>Tweets Wall</h1>

	<button type="button" onClick="window.location.reload();">Reload</button>

	<ul>
		<!-- Loop over the tweets  -->

		<c:forEach var="tweet" items="${tweets}">

			<li class="tweet"><img
				src='${fn:replace(tweet.user.profile_image_url, "_normal", "")}'
				class="avatar" /> <!-- Loop over the elements of the tweet --> <c:if
					test="${tweet.containsKey('user')}">
					<p class="Username">${tweet.user.screen_name}</p>
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
	</div>
</body>
</html>