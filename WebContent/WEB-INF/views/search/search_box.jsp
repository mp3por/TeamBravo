<!-- BEGIN TERRIER search box -->

<script>
	var full = location.protocol+'//'+location.hostname+(location.port ? ':'+location.port: '');
</script> 
   <!--  <div id="input-group">
      <input class="form-control" name="SEARCH_ALL" id="TERRIER_SEARCH" type="text" placeholder="Search for...">
      <span class="input-group-btn">
        <button id="searchbutton" type="submit" class="btn btn-default" type="button">Go!</button>
      </span>
    </div>
   <form method="get" target="_blank" action="/TeamBravo/search/terrier/" accept-charset="UTF-8" 
      onsubmit="window.open('/TeamBravo/search/terrier/' + encodeURIComponent(document.getElementById('TERRIER_SEARCH').value),'TERRIER'); return false;">
 -->
    <form method="get" target="_blank" action="/TeamBravo/search/terrier/" accept-charset="UTF-8" 
      onsubmit="window.open('/TeamBravo/search/terrier/' + encodeURIComponent(document.getElementById('TERRIER_SEARCH').value),'TERRIER'); return false;">

    <div class="input-group">
      <input type="text" name="SEARCH_ALL" id="TERRIER_SEARCH" class="form-control" placeholder="Search for...">
      <span class="input-group-btn">
        <button class="btn btn-default" type="submit">Search</button>
      </span>
    </div><!-- /input-group -->
 </form>

<!-- END TERRIER search box -->