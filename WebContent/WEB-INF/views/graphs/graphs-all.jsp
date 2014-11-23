<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
  <title>D3 Graphic</title>
  <link href="<c:url value="/resources/css/base.css" />" rel="stylesheet">
  <link href="<c:url value="/resources/css/graphs/style.css" />" rel="stylesheet">
  <script src="<c:url value="/resources/js/graphs/d3.min.js" />"></script>
  <script src="<c:url value="/resources/js/graphs/d3.layout.cloud.js" />"></script>
</head>
<body>
  <div class="container">
    <h2>D3 Graphic</h2>
    <div id="chart"></div>
  </div>
  <script src="<c:url value="/resources/js/graphs/script.js" />"></script>

  <script src="<c:url value="/resources/js/graphs/wordCloud.js" />"></script>
</body>
</html>