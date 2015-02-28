<form class="form-horizontal" id="settings_template_form">
	<fieldset>
		<!-- Form Name -->
		<legend>Settings</legend>
		<div class="row">
			<label class="col-md-4 control-label" for="checkbox">Choose what to be displayed </label>
			<div class="col-md-4">
				<div class="checkbox">
					<label> <input type="checkbox"> Text
					</label>
				</div>
			</div>
			<div class="col-md-4">
				<button id="settings_button_template" tile="templete_tile" name="setSettings" type="submit" class="btn btn-primary" onClick="setButtonClick(this)">Set</button>
			</div>
		</div>

	</fieldset>
</form>