<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<img src="/TeamBravo/resources/img/Terrier.png" style="width: 30%;">
<head>
  <title>Terrier Search</title>
</head>
<body>

</body>

<form method="get" target="_blank" action="http://localhost:8080/TeamBravo/search/terrier" accept-charset="UTF-8" 
      onsubmit="window.open('http://localhost:8080/TeamBravo/search/terrier/' + encodeURIComponent(document.getElementById('TERRIER_SEARCH').value),'TERRIER'); return false;">
    <input name="SEARCH_ALL" id="TERRIER_SEARCH" type="text" style="vertical-align: bottom; font-size: 11px; width: 60%;"/> 
    <input type="submit" value="Search" style="vertical-align: bottom; cursor: pointer; font-size: 11px;"/>
    <input type="hidden" id="LANG" name="LANG" value="en" />
</form>

</html>