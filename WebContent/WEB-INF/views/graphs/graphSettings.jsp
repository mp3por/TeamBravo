<%@ include file="/WEB-INF/include.jsp"%>

	<form id='settingsForm' onchange="settingsFormOptionSelect(this)"
		data-tileno='tileNo'>
	
		<select id='settingsFormComponent' data-tileno='tileNo'>
			<option value='BARCHART'>Bar Chart</option>
			<option value='LINEGRAPH'>Line Graph</option>
			<option value='PIECHART'>Pie Chart</option>
			<option value='WORDCLOUD'>Word Cloud</option>
		</select>
	
	
		<div id="stat_time_setting" class="btn-group btn-group-xs"
			role="group">
			<button id="graphSetBtnWeek" type="button" class="btn btn-default graphActive graphBtn" data-tileno="tileNo" onClick="settingsFormTimeSelect(this)" value="WEEK">past week</button>
			<button id="graphSetBtnMonth" type="button" class="btn btn-default graphBtn" data-tileno="tileNo" onClick="settingsFormTimeSelect(this)" value="MONTH">past month</button>
		</div>
	
	</form>
	<!--  
	<div class="spinnerContainer">
	
		<div id="topicChooserFrom">
			<h5>Show Topics:</h5>
			<input id="topicChooserA" class="topicChooser cf" type="text" value="" name="topicChooserA">
	        <script>
	            $("input[name='topicChooserA']").TouchSpin({
	                initval: 5
	            });
	        </script>
		</div>
		
		<div id="topicChooserTo">
		<h5>To:</h5>
			<input id="topicChooserB" class="topicChooser cf" type="text" value="" name="topicChooserB">
	       	<script>
	            $("input[name='topicChooserB']").TouchSpin({
	                initval: 10
	            });
	       	</script>
	    </div>
	       	
	    <button id="topicChooserBtn" type="button" class="btn btn-default topicBtn" data-tileno="tileNo" onClick="settingsFormTopicSelect(this)">Show Topics</button>
	</div>
	-->
