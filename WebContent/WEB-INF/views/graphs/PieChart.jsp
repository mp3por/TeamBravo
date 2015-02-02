<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Pie Chart</title>
	
	<link href="<c:url value="/resources/css/base.css" />" rel="stylesheet">
	<link href="<c:url value="/resources/css/graphs/c3CSS.css" />" rel="stylesheet">
	<script src="<c:url value="/resources/js/graphs/d3.min.js" />"></script>
	<script src="<c:url value="/resources/js/graphs/c3.min.js" />"></script>

</head>

<body>
	<h1>Hot Topics Pie</h1>
	<div id="chart">
		<script>
		 var frequency_list = ${frequencyList};
		 var chart = c3.generate({
			    data: {
			        columns: [
			            [frequency_list[0].Name, frequency_list[0].Tweets],
			            [frequency_list[1].Name, frequency_list[1].Tweets],
			            [frequency_list[2].Name, frequency_list[2].Tweets],
			        ],
			        type : 'pie',
			    },
				color: {
					pattern: ['#C01E11', '#92B710', '#134C7C']
			    }
			}); //.name and .tweets may need changed
		 
		 </script>
	</div>
</body>

</html>