<%@ include file="/WEB-INF/include.jsp"%>

<script>
 var frequency_list = ${tweetsForPie};
 //console.log(frequency_list);
 var chart = c3.generate({
	    data: {
	        columns: [
	            [frequency_list[0].Topic, frequency_list[0].Tweets],
	            [frequency_list[1].Topic, frequency_list[1].Tweets],
	            [frequency_list[2].Topic, frequency_list[2].Tweets],
	        ],
	        type : 'pie',
	    },
		color: {
			pattern: ['#C01E11', '#92B710', '#134C7C']
	    }
	});
 
 </script>