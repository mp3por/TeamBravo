<%@ include file="/WEB-INF/include.jsp"%>

<script>
 var pieTweets = ${tweetsForPie};
 //console.log(frequency_list);
 var chart = c3.generate({
	    data: {
	        columns: [
	            [pieTweets[0].Topic, pieTweets[0].Tweets],
	            [pieTweets[1].Topic, pieTweets[1].Tweets],
	            [pieTweets[2].Topic, pieTweets[2].Tweets],
	        ],
	        type : 'pie',
	    },
	    legend: {
	    	  position: 'bottom'
	    },
		color: {
			pattern: ['#C01E11', '#92B710', '#134C7C']
	    }
	});
 
 </script>