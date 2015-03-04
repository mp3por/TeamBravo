<script>
	function reloadStats(param) {
		console.log("Hey");
		var index = param.data.param1
		$(this).addClass('active').siblings().removeClass('active');
		var time = $(this).text();
		var linkStr;
		if (time == 'past day')
			linkStr = 'pastDay';
		else if (time == 'past month')
			linkStr = 'pastMonth';
		else if (time == 'past week')
			linkStr = 'pastWeek';
		else
			linkStr = 'allTime';

		$.ajax({
			url : '/TeamBravo/counter/getStats/' + linkStr,
			success : function(data) {
				
				$('#tile_content' + index).html(data);
				$('#added_stat_container').attr('id',
						'stat_container' + index);
			}
		});
	}

	function getStatistics(container_id, index) {
		$.ajax({
			url : '/TeamBravo/counter/stats/getSettings',
			success : function(data) {
				$('#settings' + index).html(data);
				$('.added_stat_time_setting').attr('id',
						'stat_time_setting' + index);
				$('#stat_time_setting_label').attr('for',
						'stat_time_setting' + index);
				$('.added_stat_time_picker').each(function(){
					$(this).click({param1:index}, reloadStats);
				});
				$('#stat_time_setting' + index + ' button').on("click", "input", reloadStats);
				/*click({
					param1 : index
				}, reloadStats);*/
			}
		});
		$.ajax({
			url : '/TeamBravo/counter/getStats/allTime',
			success : function(data) {
				$('#tile_content' + index).html(data);
				$('#added_stat_container').attr('id',
						'stat_container' + index);
			}
		});
	}
</script>