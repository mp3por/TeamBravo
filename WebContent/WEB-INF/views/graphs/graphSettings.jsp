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
