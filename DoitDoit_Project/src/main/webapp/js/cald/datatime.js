$(document).ready(function(){
	$.datetimepicker.setLocale('ko');
	$("[id*=datetimepicker1]").datetimepicker({
		onGenerate:function(ct){
		    $(this).find('.xdsoft_date.xdsoft_weekend')
		      .addClass('xdsoft_disabled');
		  },
		allowTimes:[
			'08:00','08:30',
			'09:00','09:30',
			'10:00','10:30',
			'11:00','11:30',
			'12:00','12:30',
			'13:00','13:30',
			'14:00','14:30',
			'15:00','15:30',
			'16:00','16:30',
			'17:00','17:30',
			'18:00'
		],
		minDate:'-1970/01/01',
		maxDate:'+1971/01/01',
	// 	minTime:0,
	});
	
	$("[id*=datetimepicker2]").datetimepicker({
		onGenerate:function(ct){
		    $(this).find('.xdsoft_date.xdsoft_weekend')
		      .addClass('xdsoft_disabled');
		  },
		allowTimes:[
			'08:00','08:30',
			'09:00','09:30',
			'10:00','10:30',
			'11:00','11:30',
			'12:00','12:30',
			'13:00','13:30',
			'14:00','14:30',
			'15:00','15:30',
			'16:00','16:30',
			'17:00','17:30',
			'18:00'
		],
		minDate:'-1970/01/01',
		maxDate:'+1971/01/01',
	});

})