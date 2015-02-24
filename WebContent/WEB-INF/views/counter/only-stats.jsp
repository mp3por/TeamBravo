<%@ include file="/WEB-INF/include.jsp"%>


<div id="stat-container" class="row">
		<div class="col-md-12 col-sm-12 col-xs-12">

			<div class="row">
				<div class="col-md-6 col-sm-6 col-xs-6">
					<h5>
						<small>TOTAL TWEETS</small><br> ${total_tweets}
					</h5>
				</div>
				<div class="col-md-6 col-sm-6 col-xs-6">
					<h5>
						<small>TOTAL RETWEETS</small><br> ${total_retweets}
					</h5>
				</div>
			</div>

			<hr class="horizontal-line ">

			<div class="row">
				<div class="col-md-6 col-sm-6 col-xs-6">
					<img src=<c:url value="resources/img/user91.png"/>
						class="user_icon" />
					<h5>
						<small>MOST ACTIVE USER</small><br> ${most_active_user}
					</h5>
				</div>

				<div class="col-md-6 col-sm-6 col-xs-6">
					<img src=<c:url value="resources/img/user91.png"/>
						class="user_icon" />
					<h5>
						<small>MOST MENTIONED USER</small><br> ${most_mentioned_user}
					</h5>
				</div>
			</div>

			<hr class="horizontal-line ">

			<div class="row">
				<div class="col-md-12 col-sm-12 col-xs-12">
					<h5 class="mini stat_general_topic">
						<small>MOST POPULAR</small><br>
					</h5>
				</div>
			</div>

			<div class="row">
				<div class="col-md-4 col-sm-4 col-xs-4">
					<h5 class="subtopic">
						<small>HASHTAG </small><br> ${most_pop_hashtag}
					</h5>
				</div>

				<div class="col-md-4 col-sm-4 col-xs-4">
					<h5 class="subtopic">
						<small>LOCATION</small><br> ${most_pop_location}
					</h5>
				</div>

				<div class="col-md-4 col-sm-4 col-xs-4">
					<h5 class="subtopic">
						<small>PERSON</small><br> ${most_pop_person}
					</h5>
				</div>
			</div>

			<hr class="horizontal-line ">

			<div class="row">
				<div class="col-md-12 col-sm-12 col-xs-12">
					<img src=<c:url value="resources/img/smiling36.png"/>
						class="stat_icon" />
					<h5 class="mini">
						<small>MOST RETWEETED TWEET</small>
					</h5>
					<blockquote class="mini">
						<p class="mini">${most_retweeted_tweet}</p>
						<footer>${most_retweeted_tweet_user}</footer>
					</blockquote>
				</div>
			</div>

			<hr class="horizontal-line ">

			<div class="row">
				<div class="col-md-12 col-sm-12 col-xs-12">
					<img src=<c:url value="resources/img/smiling36.png"/>
						class="stat_icon" />
					<h5 class="mini">
						<small>MOST FAVOURITED TWEET</small>
					</h5>
					<blockquote class="mini">
						<p class="mini">${most_fav_tweet}</p>
						<footer>${most_fav_tweet_user}</footer>
					</blockquote>
				</div>
			</div>

		</div>
	</div>