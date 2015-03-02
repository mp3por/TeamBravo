<!-- BEGIN TERRIER search box -->

<script>
	var full = location.protocol+'//'+location.hostname+(location.port ? ':'+location.port: '');
</script> 
 
<form method="get" target="_blank" action="${full}" accept-charset="UTF-8" 
      onsubmit="window.open('${full}/TeamBravo/search/terrier/' + encodeURIComponent(document.getElementById('TERRIER_SEARCH').value),'TERRIER'); return false;">
    <input name="SEARCH_ALL" id="TERRIER_SEARCH" type="text" style="vertical-align: bottom; font-size: 11px; width: 60%;"/> 
    <input id="searchbutton" type="submit" value="Search" style="vertical-align: bottom; cursor: pointer; font-size: 11px;"/>
    <input type="hidden" id="LANG" name="LANG" value="en" />
</form>

<!-- END TERRIER search box -->