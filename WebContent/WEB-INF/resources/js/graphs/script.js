//Arbitrary data for the bar chart to display
var barData = [90185,122500,154976,84963,101432,78453,105000];
//Date values for the horizontal axis - Not used yet
//var dataDates = [{"date":"2014-11-10"},{"date":"2014-11-16"}];
var bars = 7;

// Read in json file
// All other code in this script must be enclosed in this function body
/*d3.json("out.json7", function(error, json) {
	
	//This will work if the json is formatted correctly
	//eg [{"message":"@steven hello world!"},{"message":"hellllooo everybody"}]
	//if (error) return console.warn(error);
	//for(var j = 0; j < json.length; j++){
	//	console.log(json[j].message);
	//}
  
});*/

//Margins for alignment of axis
var margin = {top: 40, right: 40, bottom: 40, left: 70}

var height = 400 - margin.top - margin.bottom,
	width = 600 - margin.left - margin.right,
	barWidth = 50,
	barrOffSet = 5;

var tempColour;

var colours = d3.scale.linear()
	.domain([0, barData.length*.33, barData.length*.66, barData.length])
	.range(['#B58929','#C61C6F','#268BD2','#85992C'])

var yScale = d3.scale.linear()
	.domain([0, d3.max(barData)])
	.range([0, height]);

var xScale = d3.scale.ordinal()
	.domain(d3.range(0, barData.length))
	.rangeBands([0, width]);

//Scale for horizontal axis - Not used yet
/*var x = d3.time.scale()
	.domain([dataDates[0].date, dataDates[1].date])
    .range([0, width]);*/

//Main bar chart function
var myChart = d3.select('#chart').append('svg')
	.attr('width', width + margin.left + margin.right)
	.attr('height', height + margin.top + margin.bottom)
	.attr('transform', 'translate(' + margin.left +',  '+margin.top +')')
	.style('background', '#536870')
	.selectAll('rect').data(barData)
	.enter().append('rect')
		.style('fill', function(d,i){
			return colours(i);
		})
		.attr('width', xScale.rangeBand())
		.attr('x',function(d,i){
			return xScale(i);
		})
		.attr('height', 0)
		.attr('y', height)
	.on('mouseover', function(d){
		tempColour = this.style.fill;
		d3.select(this)
			.style('opacity', .5)
			.style('fill', '#FFFFFF')
	})
	.on('mouseout', function(d){
			d3.select(this)
				.style('opacity', 1)
				.style('fill', tempColour)
	})

//Animation of chart elements
myChart.transition()
	.attr('height', function(d){
		return yScale(d);
	})
	.attr('y', function(d){
		return height - yScale(d);
	})
	.delay(function(d,i){
		return i * 20;
	})
	.duration(1000)
	.ease('elastic')

var vGuideScale = d3.scale.linear()
		.domain([0,d3.max(barData)])
		.range([height,0])
	
var vAxis = d3.svg.axis()
	.scale(vGuideScale)
	.orient('left')
	.ticks(20)

var vGuide = d3.select('svg').append('g')
	vAxis(vGuide)
	vGuide.attr('transform','translate(' + margin.left -40  + ',' + margin.top + ')')
	vGuide.selectAll('path')
		.style({fill: 'none', stroke: "#000"})
	vGuide.selectAll('line')
		.style({stroke: "#000"})

//Horizontal axis - Not used yet
/*var hAxis = d3.svg.axis()
	.scale(x)
	.orient('bottom')
    .ticks(d3.time.week, 0)
    .tickFormat(d3.time.format('%a'))
    .tickSize(0)
	
	var hGuide = d3.select('svg').append('g')
	hAxis(hGuide)
	hGuide.attr('transform', 'translate(0,' + (height - 20) + ')')
	hGuide.selectAll('path')
	.style({fill: 'none', stroke: "#000"})
	hGuide.selectAll('line')
	.style({stroke: "#000"})*/
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		