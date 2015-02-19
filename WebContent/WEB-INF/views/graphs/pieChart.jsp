<%@ include file="/WEB-INF/include.jsp"%>
<script>
	var pieList = ${tweetsForPie};
	var chart = c3.generate({
		data : {
			columns : [ [ pieList[0].Topic, pieList[0].Tweets ],
					[ pieList[1].Topic, pieList[1].Tweets ],
					[ pieList[2].Topic, pieList[2].Tweets ], ],
			type : 'pie',
		},
		color : {
			pattern : [ '#C01E11', '#92B710', '#134C7C' ]
		}
	}); //.name and .tweets may need changed
</script>