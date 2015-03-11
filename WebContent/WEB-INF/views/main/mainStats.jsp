<script>
	function reloadStats(data) {
		
		var s = data.getAttribute("id");
		var parts = s.split("_");
		var index = parts[parts.length - 1];
		console.log(index);
		
		var linkStr = data.getAttribute("data_time");

		$('.stat_time_picker_'+index).each(function(){
			if (linkStr==$(this).attr("data_time")) {
				$(this).addClass("active");
			} else {
				$(this).removeClass("active");
			};
		});
		
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
						'stat_time_setting_' + index);
				$('.added_stat_time_picker').attr('id',
						'stat_time_picker_' + index);
				$('#stat_time_setting_label').attr('for',
						'stat_time_setting' + index);
				$('.added_stat_time_picker').each(function(){
					$(this).attr("onclick","reloadStats(this)");
					$(this).addClass("stat_time_picker_"+index);
					$(this).removeClass("added_stat_time_picker");
				});
				//$('#stat_time_setting_' + index + ' button').on("click", "input", reloadStats);
				/*click({
					param1 : index
				}, reloadStats);*/
			}
		});
		$.ajax({
			url : '/TeamBravo/counter/getStats/pastMonth',
			success : function(data) {
				$('#tile_content' + index).html(data);
				$('#added_stat_container').attr('id',
						'stat_container' + index);
				
			}
		});
	}
</script>