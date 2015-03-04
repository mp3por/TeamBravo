<script>
	function submitTweetwallSettings(param) {
		var index = param.data.param1;
		var count = document.getElementById('tweetwallTweetNumber_' + index).value;
		var dateFrom = $('#datetimepicker_from_' + index).data('date');
		console.log(dateFrom);
		var dateTo = $('#datetimepicker_to_' + index).data('date');
		console.log(dateTo);
		if (!dateFrom)
			dateFrom = 0;
		if (!dateTo)
			dateTo = 0;
		$.ajax({
			url : '/TeamBravo/tweets/tweetWall/' + count + '/' + dateFrom + '/'
					+ dateTo,
			success : function(data) {
				initWall("tile_content" + index, data, index);
			}
		});
	}

	function makeUserIDLarger(index, inc) {
		var s = index.getAttribute("id");
		var parts = s.split("_");
		var i = parts[parts.length - 1];
		$('.tweetwall_h3_' + i).each(function() {
			var size = parseFloat($(this).css("font-size"));

			$(this).css('font-size', size + size * 0.25 * inc + "px");
		});
	}

	function makeTweetTimeLarger(index, inc) {
		var s = index.getAttribute("id");
		var parts = s.split("_");
		var i = parts[parts.length - 1];
		$('.tweetwall_h4_' + i).each(function() {
			var size = parseFloat($(this).css("font-size"));

			$(this).css('font-size', size + size * 0.25 * inc + "px");
		});
	}

	function makeAvatarLarger(index, inc) {
		var s = index.getAttribute("id");
		var parts = s.split("_");
		var i = parts[parts.length - 1];

		console.log(index);
		console.log(i);
		$('.avatar_' + i).each(function() {
			var size = parseFloat($(this).css("width"));
			var height = parseFloat($(this).css("height"));
			$(this).css('width', size + size * 0.25 * inc + "px");
			$(this).css('height', height + height * 0.25 * inc + "px");

		});
	}

	function makeTweetLarger(index, inc) {
		console.log(index);
		var s = index.getAttribute("id");
		var parts = s.split("_");
		var i = parts[parts.length - 1];
		console.log("make it larger");
		console.log(i);
		$('.tweetText_' + i).each(function() {
			var size = parseFloat($(this).css("font-size"));
			console.log(size);
			$(this).css('font-size', size + size * 0.25 * inc + "px");
		});
	}

	function initWall(container_id, data, index) {
		//console.log(data);
		//console.log("Container id:");
		//console.log(container_id);
		console.log("index:");
		console.log(index);

		$('#tile_content' + index).html(data);

		$('.added_tweetwall_h3').each(function() {
			console.log("I am here at h4");
			$(this).attr('class', 'tweetwall_h3_' + index);
		});
		$('.added_tweetwall_h4').each(function() {
			$(this).attr('class', 'tweetwall_h4_' + index);
		});

		$('.added_tweetwall_avatar').each(function() {
			$(this).attr('class', 'avatar_' + index);
		});

		$('.added_tweetwall_tweet').each(function() {
			$(this).attr('class', 'tweetwall_tweet_' + index);
		});

		$('.added_tweetwall_li').each(function() {
			$(this).attr('class', 'tweetwall_li_' + index);
		});

		$('.addedTweetText').each(function() {
			$(this).attr('class', 'tweetText_' + index);
		});

		console.log("init wall");
	}

	//initial tweetWall with latest tweets and 10 tweets in the wall
	function getTweetwall(container_id, index) {
		$.ajax({
			url : '/TeamBravo/tweets/tweetWall/10/0/0',
			success : function(data) {
				console.log("Tweetwall content index: " + index);
				console.log("cont_id: " + container_id);
				initWall("tile_content" + index, data, index);
			}
		});

		$.ajax({
			url : '/TeamBravo/tweets/tweetWall/getSettings',
			success : function(data) {
				console.log("Tweetwall setting index: " + index);

				$(document).ready(
						function() {

							$('#settings' + index).html(data);

							$('#added_submitTweetwallSettings').attr('id',
									'submitTweetwallSettings_' + index);
							$('#submitTweetwallSettings_' + index).attr(
									'index', index);

							$('#added_makeUserIDLarger').each(
									function() {
										$(this).attr("id",
												"makeUserIDLarger_" + index);
									});
							$('#added_makeUserIDSmaller').each(
									function() {
										$(this).attr("id",
												"makeUserIDSmaller_" + index);
									});

							$('#tweetwallTweetNumber').attr("id",
									"tweetwallTweetNumber_" + index);

							$('#added_smaller_avatar_index').each(function() {
								$(this).attr("id", "smaller_avatar_" + index);
							});
							$('#added_larger_avatar_index')
									.each(
											function() {
												$(this).attr(
														"id",
														"larger_avatar_index_"
																+ index);
											});

							$('#added_smaller_tweet').attr("id",
									"smaller_tweet_" + index);
							$('#added_larger_tweet').attr("id",
									"larger_tweet__" + index);

							$('#added_Submit_index').attr("id",
									"tweetWall_submit_" + index);

							$("#added_datetimepicker_from").attr("id",
									"datetimepicker_from_" + index);
							$("#added_datetimepicker_to").attr("id",
									"datetimepicker_to_" + index);

							$('#submitTweetwallSettings_' + index).click({
								param1 : index
							}, submitTweetwallSettings);

							fixDatePickers(index);

						});
			}
		});
	}
</script>