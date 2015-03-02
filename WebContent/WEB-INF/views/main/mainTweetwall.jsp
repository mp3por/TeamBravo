<script>
function submitTweetwallSettings (deb) {
		console.log(deb);
		var s = deb.getAttribute('id');
		var parts = s.split("_");
		var index = parts[parts.length-1];
		
		var count = document.getElementById('tweetwallTweetNumber_'+index).value;
		var dateFrom = $('#datetimepicker_from_'+index).data('date');
		console.log(dateFrom);
		var dateTo = $('#datetimepicker_to_'+index).data('date');
		console.log(dateTo);
		if (!dateFrom) dateFrom = 0;
		if (!dateTo) dateTo = 0;
		$.ajax({
			url : '/TeamBravo/tweets/tweetWall/'+count+'/'+dateFrom+'/'+dateTo,
			success : function(data) {
				initWall("tile_content"+index, data, index);
			}
		});
	}
	
	function makeUserIDLarger(index, inc) {
		var s = index.getAttribute("id");
		var parts = s.split("_");
		var i = parts[parts.length-1];
		$('.tweetwall_h3_'+i).each( function () {
			 var size = parseFloat($(this).css("font-size"));

			$(this).css('font-size', size+15*inc+"px");
		});
	}
	
	function makeAvatarLarger(index, inc) {
		var s = index.getAttribute("id");
		var parts = s.split("_");
		var i = parts[parts.length-1];
		
		console.log(index);
		console.log(i);
		$('.avatar_'+i).each( function () {
			 var size = parseFloat($(this).css("width"));

			$(this).css('width', size+50*inc+"px");
		});
	}
	
	function makeTweetLarger(index, inc) {
		var s = index.getAttribute("id");
		var parts = s.split("_");
		var i = parts[parts.length-1];
		
	}
	
	function initWall(container_id, data, index) {
		//console.log(data);
		//console.log("Container id:");
		//console.log(container_id);
		console.log("index:");
		console.log(index);
		//sorry if I left it!
		$('#tile_content'+index).html(data);
		
		$('#settings'+index).html(
					'<p>Number of tweets to show:</p>'+
					'<input id="tweetwallTweetNumber" class="intSpinner" type="text" value="25" name="demo3_22">'+
					'<script>$("input[name=demo3_22]").TouchSpin({'+
						'initval:40,min:1,max:100}'+
						')'+';'+' </sc'+'ript>'+
					//end of date & time picker	
					//date & time picker:
					'Date from: <br/><div class="form-group">'+
            		'<div class="input-group date" id="added_datetimepicker_from">'+
                	'<input type="text" class="form-control" />'+
                	'<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span>'+
                	'</span></div></div>'+
        			'Date to: <br/><div class="form-group">'+
            		'<div class="input-group date" id="added_datetimepicker_to">'+
                	'<input type="text" class="form-control" />'+
                	'<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span>'+
                	'</span></div></div>'+	

					//submitTweetwallSettings should not be used in your component
					'<button type="button" onclick="submitTweetwallSettings(this);" index="added_Submit_index" '+
					'id="added_submitTweetwallSettings" class="btn btn-default added_submitTweetwallSettings">Submit settings</button>'+
					'<button type="button" onclick="makeUserIDLarger(this, -1);" index="added_smaller_index" id="added_makeUserIDSmaller" class="btn btn-default added_makeUserIDSmaller"><span class="glyphicon glyphicon-minus" aria-hidden="true"></span> Make User ID smaller</button>'+
					'<button type="button" onclick="makeUserIDLarger(this, 1);" index="added_larger_index" id="added_makeUserIDLarger" class="btn btn-default added_makeUserIDLarger"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Make User ID larger</button>'+
					'<button type="button" onclick="makeAvatarLarger(this, -1);" index="added_smaller_avatar_index" id="added_smaller_avatar_index" class="btn btn-default added_smaller_avatar_index "><span class="glyphicon glyphicon-minus" aria-hidden="true"></span> Make User Avatar smaller</button>'+
					'<button type="button"  onclick="makeAvatarLarger(this, 1);" index="added_larger_avatar_index" id="added_larger_avatar_index" class="btn btn-default added_larger_avatar_index"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Make User Avatar larger</button>'+
					'<button type="button" onclick="makeTweetLarger(this, -1);" index="added_smaller_tweet" id="added_smaller_tweet" class="btn btn-default added_smaller_tweet"><span class="glyphicon glyphicon-minus" aria-hidden="true"></span> Make Tweet smaller</button>'+
					'<button type="button"  onclick="makeTweetLarger(this, 1);" index="added_smaller_tweet" id="added_smaller_tweet" class="btn btn-default added_smaller_tweet"><span class="glyphicon glyphicon-plus"  aria-hidden="true"></span> Make Tweet larger</button>'								
		);
	       
	           
	       
				
		$('#added_submitTweetwallSettings').attr('id', 'submitTweetwallSettings_' + index);
		
		$('#added_submitTweetwallSettings').attr('index', index);
		
		$('.added_tweetwall_h3').each(function () {
			console.log("I am here at h3");
			$(this).attr('class', 'tweetwall_h3_'+index);
		});
		
		$('.added_tweetwall_h4').each(function () {
			$(this).attr('class', 'tweetwall_h4_'+index);
		});
		
		$('.added_tweetwall_avatar').each(function () {
			$(this).attr('class', 'avatar_'+index);
		});

		$('.added_tweetwall_tweet').each(function () {	
			$(this).attr('class', 'tweetwall_tweet_'+index);
		});
		
		$('#added_makeUserIDLarger').each( function () {
			$(this).attr("id", "makeUserIDLarger_"+index);
		});
		$('#added_makeUserIDSmaller').each( function () {
			$(this).attr("id", "makeUserIDSmaller_"+index);
		});
		
		$('#tweetwallTweetNumber').attr("id", "tweetwallTweetNumber_"+index);
		
		$('#added_smaller_avatar_index').each( function () {
			$(this).attr("id", "smaller_avatar_"+index);
		});

		$('#added_larger_avatar_index').each( function () {
			$(this).attr("id", "larger_avatar_index_"+index);
		});
		
		$('#added_Submit_index').attr("id", "tweetWall_submit_"+index);
		
		$('.added_tweetwall_li').each(function () {
			$(this).attr('class', 'tweetwall_li_'+index);
		});
		
		$('.addedTweetText').each (function () {
			$(this).attr('class', 'tweetText_'+index);
		});
		
		$("#added_datetimepicker_from").attr("id", "datetimepicker_from_"+index);
		$("#added_datetimepicker_to").attr("id", "datetimepicker_to_"+index);
		
     	fixDatePickers(index);
        

		
		console.log("init wall");
		

	}
	//initial tweetWall with latest tweets and 10 tweets in the wall
	function getTweetwall(container_id, index) {
		$.ajax({
			url : '/TeamBravo/tweets/tweetWall/10/0/0',
			success : function(data) {
				console.log("index: " + index);
				console.log("cont_id: " + container_id);
				initWall("tile_content"+index, data, index);
			}
		});
	}
	
	
</script>