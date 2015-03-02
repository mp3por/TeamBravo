<%@ include file="/WEB-INF/include.jsp"%>


<div class="col-md-6 HOLDER" id="template_column_id">
	<div id='block' class="block">
		<div class="row">
			<div class="col-md-12">

				<div class="above_box text-center">
					<div id="template_title">TITLE</div>
					<div>
						<button id="template_submit_button" type="button" class="btn btn-sm" settings_button_id="temlate" opened="0" onClick="settingsButtonClick(this)">
							<span class="glyphicon glyphicon-wrench"></span>
						</button>
					</div>
					<div>
						<button id="template_delete_button" type="button" class="btn btn-sm" tile="temlate_tile" onClick="deleteButtonClick(this)">
							<span class="glyphicon glyphicon-remove"></span>
						</button>
					</div>

				</div>

			</div>
		</div>
		<div id="template_settings_div" class="settings">
			<span class="glyphicon glyphicon-ok"></span>
		</div>
		<div id="template_content"></div>
	</div>
</div>
