<div class="col-md-12 col-sm-12 col-xs-12 tweetwall-setting">

	<div class="row">
		<div class="col-md-4 col-sm-4 col-xs-4">
			<label class="tweetwall-label" for="tweetwallTweetNumber">Number
				of tweets</label> <input id="tweetwallTweetNumber" class="intSpinner"
				type="text" value="25" name="demo3_22">
			<script>
				$("input[name=demo3_22]").TouchSpin({
					initval : 40,
					min : 1,
					max : 100
				});
			</script>
		</div>

		<div class="col-md-4 col-sm-4 col-xs-4">
			<label class="tweetwall-label" for="added_datetimepicker_from">Date
				from:</label>
			<div class="input-group date" id="added_datetimepicker_from">
				<input type="text" class="form-control" /> <span
					class="input-group-addon"><span
					class="glyphicon glyphicon-calendar"></span> </span>
			</div>
		</div>

		<div class="col-md-4 col-sm-4 col-xs-4">
			<label class="tweetwall-label" for="added_datetimepicker_from">Date
				to:</label>
			<div class="input-group date" id="added_datetimepicker_to">
				<input type="text" class="form-control" /> <span
					class="input-group-addon"><span
					class="glyphicon glyphicon-calendar"></span> </span>
			</div>
		</div>

	</div>

	<div class="row tweetwall-submit">

		<button type="button" index="added_Submit_index"
			id="added_submitTweetwallSettings"
			class="btn btn-default btn-sm added_submitTweetwallSettings">Submit
			Setting</button>

	</div>

	<div class="row">

		<div class="col-md-4 col-sm-4 col-xs-4">
			<label class="tweetwall-label">User ID size</label>
			<button type="button" onclick="makeUserIDLarger(this, -1);"
				index="added_smaller_index" id="added_makeUserIDSmaller"
				class="btn btn-default btn-sm added_makeUserIDSmaller">
				<span class="glyphicon glyphicon-minus" aria-hidden="true"></span>
			</button>
			<button type="button" onclick="makeUserIDLarger(this, 1);"
				index="added_larger_index" id="added_makeUserIDLarger"
				class="btn btn-default btn-sm added_makeUserIDLarger">
				<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
			</button>
		</div>

		<div class="col-md-4 col-sm-4 col-xs-4">
			<label class="tweetwall-label">Avatar size</label>
			<button type="button" onclick="makeAvatarLarger(this, -1);"
				index="added_smaller_avatar_index" id="added_smaller_avatar_index"
				class="btn btn-default btn-sm added_smaller_avatar_index ">
				<span class="glyphicon glyphicon-minus" aria-hidden="true"></span>
			</button>
			<button type="button" onclick="makeAvatarLarger(this, 1);"
				index="added_larger_avatar_index" id="added_larger_avatar_index"
				class="btn btn-default btn-sm added_larger_avatar_index">
				<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
			</button>
		</div>

		<div class="col-md-4 col-sm-4 col-xs-4">
			<label class="tweetwall-label">Tweet size</label>
			<button type="button" onclick="makeTweetLarger(this, -1);"
				index="added_smaller_tweet" id="added_smaller_tweet"
				class="btn btn-default btn-sm added_smaller_tweet">
				<span class="glyphicon glyphicon-minus" aria-hidden="true"></span>
			</button>
			<button type="button" onclick="makeTweetLarger(this, 1);"
				index="added_larger_tweet" id="added_larger_tweet"
				class="btn btn-default btn-sm added_larger_tweet">
				<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
			</button>
		</div>

	</div>

</div>