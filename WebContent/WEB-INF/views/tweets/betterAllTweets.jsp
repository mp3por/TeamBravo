<%@ include file="/WEB-INF/include.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
<link href="<c:url value="/resources/css/tweets.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/jquery.qtip.css" />" rel="stylesheet">

<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"></script>
<script type="text/javascript" src="<c:url value="/resources/js/imagesloaded.pkg.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.qtip.js" />"></script>

<script type="text/javascript">
			/* THIS DOES WORK */
/*			title = "Glasgow";
			var getImagess = function (title) {
				$.getJSON("http://en.wikipedia.org/w/api.php?action=parse&page=" + title + "&prop=images&format=json&callback=?&limit=9", function (data) {
					return data;
			}
				console.log(data);
			})}*/
			
			function getImages (title, count) {
				images = getAllImages(title);
				for (i = 0; i < images.length; i++) { 
				 	console.log(images[i]);   
				}
			}
			
			function getAllImages (title) {
					//Get Leading paragraphs (section 0)
					$.getJSON("http://en.wikipedia.org/w/api.php?action=parse&page=" + title + "&prop=images&format=json&callback=?&limit=9", function (data, error) {
							array = data["parse"]["images"];
							console.log (data);
					        return data;
					});
				}			
			function getWiki (title) {
				//Get Leading paragraphs (section 0)
				$.getJSON("http://en.wikipedia.org/w/api.php?action=parse&page=" + title + "&prop=text&section=0&format=json&callback=?", function (data, error) {
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
				        console.log("Return for " + title);
				        console.log(pText);
				        document.getElementById(0).innerHTML = pText;
				        return pText;
				    }
				});
			}
			//x = getWiki("Glasgow");
			//console.log(x);
			//y = getImages("Glasgow");
			//console.log(y);
			
			</script>

<script type="text/javascript">

//Create the tooltips only when document ready
$(document).ready(function()
{
	

	$('.NETooltip').each(function (){
		console.log($(this).text());
		console.debug($(this));
		$(this).qtip({
			content: {
				text: $(this).context.innerText
			}
		})
	});
	
	$('.UserIDTooltip').each(function (){
		console.log($(this).text());
		console.debug($(this));
		$(this).qtip({
			content: {
				text: $(this).context.innerText
			}
		})
	});
	
	$('.URLTooltip').each(function (){
		console.log($(this).text());
		console.debug($(this));
		$(this).qtip({
			content: {
				text: $(this).context.innerText
			}
		})
	});
	
	$('.HashtagTooltip').each(function (){
		console.log($(this).text());
		console.debug($(this));
		$(this).qtip({
			content: {
				text: $(this).context.innerText
			}
		})
	});


	// Grab all elements with the class "hasTooltip"
	$('.xTooltip').each(function() { // Notice the .each() loop, discussed below
	    $(this).qtip({
	        content: {
	            text: $(this).next('div') // Use the "div" element next to this for the content
	        },
		    position: {
	    	    my: 'right center',  // Position my top left...
	        	at: 'right center', // at the bottom right of...
	        	target: $('.xTooltip') // my target
	        	
		    },
		    style: 'qtip-wiki'

	    });
	});
	
});
</script>
</head>
<body>


<div class="xTooltip">Hover me to see a tooltip</div>
<div class="hidden" id=0 style="display: none; display:inline-block; text-align:center;"> 
<!-- This class should hide the element, change it if needed -->
    <script>getWiki("Glasgow");</script>
</div>



	<h1>Tweets Wall [Current]</h1>

<button type="button" onClick="window.location.reload();">Reload</button>

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
					
					<c:forEach var="NE" items="${tweet.Person}">
						<p>
							<img src=<c:url value="resources/img/user91.png"/> class="icon_img"/>
							<div class="xTooltip">${NE}</div>
							
							<div class="hidden" style="display: none;"> <!-- This class should hide the element, change it if needed -->
    						<p>Stuff</p>
							</div>
							
						
					</c:forEach>
					
					<c:forEach var="NE" items="${tweet.Location}">
					<p>
							<img src=<c:url value="resources/img/world90.png"/> class="icon_img"/>
							<div class="xTooltip">${NE}</div>
							
							<div class="hidden" style="display: none;"> <!-- This class should hide the element, change it if needed -->
    						<p>Stuff</p>
							</div>
					</c:forEach>
					
					<c:forEach var="NE" items="${tweet.Organization}">
					<p>
							<img src=<c:url value="resources/img/factory6.png"/> class="icon_img"/>
							<div class="xTooltip">${NE}</div>
							
							<div class="hidden" style="display: none;"> <!-- This class should hide the element, change it if needed -->
    						<p>Stuff</p>
							</div>
					</c:forEach>
					
					
					<c:forEach var="NE" items="${tweet.UserID}">
						<p>
							<img src=<c:url value="resources/img/at2.png"/> class="icon_img"/>
							<p class="UserIDTooltip">${NE}</p><br>
					</c:forEach>
					
					<c:forEach var="NE" items="${tweet.URL}">
						<p>
							<img src=<c:url value="resources/img/external1.png"/> class="icon_img"/>
							<p class="URLTooltip">${NE}</p><br>
					</c:forEach>
					
					<c:forEach var="NE" items="${tweet.Hashtag}">
					<p>
						<img src=<c:url value="resources/img/internet60.png"/> class="icon_img"/>
						<p class="HashtagTooltip">${NE}</p><br>
					</c:forEach>
					
	
					

				</li>
			</c:forEach>
		</ul>
	</div>
</body>
</html>