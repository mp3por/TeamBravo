<%@ include file="/WEB-INF/include.jsp"%>


<div id="added_stat_container">
	<div class="col-md-12 col-sm-12 col-xs-12 stat-container">

		<div class="row">
			<div class="col-md-6 col-sm-6 col-xs-6">
				<h5 class = "stat">
					<small>TOTAL TWEETS</small><br> ${total_tweets}
				</h5>
			</div>
			<div class="col-md-6 col-sm-6 col-xs-6">
				<h5 class = "stat">
					<small>TOTAL RETWEETS</small><br> ${total_retweets}
				</h5>
			</div>
		</div>

		<hr class="horizontal-line ">

		<div class="row">
			<div class="col-md-6 col-sm-6 col-xs-6">
				<h5 class = "stat">
					<small>MOST ACTIVE USER</small><br> ${most_active_user}
				</h5>
			</div>

			<div class="col-md-6 col-sm-6 col-xs-6">
				<h5 class = "stat">
					<small>MOST MENTIONED USER</small><br> ${most_mentioned_user}
				</h5>
			</div>
		</div>

		<hr class="horizontal-line ">

		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12">
				<h5 class="stat">
					<small>MOST POPULAR</small><br>
				</h5>
			</div>
		</div>

		<div class="row">
			<div class="col-md-4 col-sm-4 col-xs-4">
				<h5 class="stat">
					<small>HASHTAG </small><br> ${most_pop_hashtag}
				</h5>
			</div>

			<div class="col-md-4 col-sm-4 col-xs-4">
				<h5 class="stat">
					<small>LOCATION</small><br> ${most_pop_location}
				</h5>
			</div>

			<div class="col-md-4 col-sm-4 col-xs-4">
				<h5 class="stat">
					<small>PERSON</small><br> ${most_pop_person}
				</h5>
			</div>
		</div>

		<hr class="horizontal-line ">

		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12">
				<img src=<c:url value="resources/img/retweet_icon.png"/>
					class="stat_icon" />
				<h5 class="stat">
					<small>MOST RETWEETED TWEET</small>
				</h5>
				<blockquote class="stat mini">
					<p class="stat">${most_retweeted_tweet}</p>
					<footer class="stat">${most_retweeted_tweet_user}</footer>
				</blockquote>
			</div>
		</div>

		<hr class="horizontal-line ">

		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12">
				<img src=<c:url value="resources/img/fav_icon.png"/>
					class="stat_icon" />
				<h5 class="stat">
					<small>MOST FAVOURITED TWEET</small>
				</h5>
				<blockquote class="stat">
					<p class="stat">${most_fav_tweet}</p>
					<footer class="stat">${most_fav_tweet_user}</footer>
				</blockquote>
			</div>
		</div>

	</div>
</div>