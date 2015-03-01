<%@ include file="/WEB-INF/include.jsp"%>


<div class="col-md-6 HOLDER" id="template_column_id">
	<div id= 'block' class="block">
		<div class="row">
			<div class="col-md-12">
				<div class="above_box text-center" id="template_title">TITLE</div>
				<div>
					<button id="template_submit_button" type="button" class="btn btn-sm" settings_button_id="temlate" opened="0" onClick="settingsButtonClick(this)">Settings!</button>

				</div>
			</div>
		</div>
		<div id="template_settings_div" class="settings" >
			
			<form id='settingsForm'>
				<select id='settingsFormComponent'>
					<option  value='MAP'>Map</option>
					<option  value='BARCHART'>Bar Chart</option>
					<option  value='LINEGRAPH'>Line Graph</option>
					<option  value='PIECHART'>Pie Chart</option>
					<option  value='WORDCLOUD'>Word Cloud</option>
				</select>
				
				<select id='settingsFormTimeScale'>
					<option value='WEEK'>Week</option>
					<option value='MONTH'>Month</option>
				</select> 
				
				<button id='settingsFormButton' data-tileno='tileNo' type='button' onClick='settingsFormButtonClick(this)'>Show</button>
			</form>
			
		</div>
		<div id="template_content">
		</div>
	</div>
</div>
<!-- <div id="next">
</div> -->