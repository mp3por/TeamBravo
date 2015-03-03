<%@ include file="/WEB-INF/include.jsp"%>


<div class="col-md-6 HOLDER" id="template_column_id">
	<div id='block' class="block">
		<div class="row">
			<div class="col-md-10 col-sm-10 col-xs-10">
				<h4 class="above_box text-center" id="template_title">TITLE</h4>
			</div>

			<div class="col-md-2 col-sm-2 col-xs-2">
				<button id="template_submit_button" type="button"
					 class="btn btn-sm setting-btn"
					settings_button_id="temlate" opened="0"
					onClick="settingsButtonClick(this)">
					<span class="glyphicon glyphicon-wrench"></span>
				</button>
				<button id="template_delete_button" type="button"
					 class="btn btn-sm setting-btn" tile="temlate_tile"
					onClick="deleteButtonClick(this)">
					<span class="glyphicon glyphicon-remove"></span>
				</button>
			</div>

		</div>
		<div id="template_settings_div" class="settings">
			<span class="glyphicon glyphicon-ok"></span>
		</div>
		<div id="template_content"></div>
	</div>
</div>
