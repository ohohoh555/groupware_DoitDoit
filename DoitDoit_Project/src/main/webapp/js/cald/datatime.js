$(document).ready(function(){
	$.datetimepicker.setLocale('ko');
	$("[id*=datetimepicker1]").datetimepicker({
		onGenerate:function(ct){
		    $(this).find('.xdsoft_date.xdsoft_weekend')
		      .addClass('xdsoft_disabled');
		  },
		allowTimes:[
			'09:00','09:15','09:30','09:45',
			'10:00','10:15','10:30','10:45',
			'11:00','11:15','11:30','11:45',
			'12:00','12:15','12:30','12:45',
			'13:00','13:15','13:30','13:45',
			'14:00','14:15','14:30','14:45',
			'15:00','15:15','15:30','15:45',
			'16:00','16:15','16:30','16:45',
			'17:00','17:15','17:30','17:45',
			'18:00'
		],
		minDate:'-1970/01/01',
		maxDate:'+1971/01/01',
	});
	
	$("[id*=datetimepicker2]").datetimepicker({
		onGenerate:function(ct){
		    $(this).find('.xdsoft_date.xdsoft_weekend')
		      .addClass('xdsoft_disabled');
		  },
		allowTimes:[
			'09:00','09:15','09:30','09:45',
			'10:00','10:15','10:30','10:45',
			'11:00','11:15','11:30','11:45',
			'12:00','12:15','12:30','12:45',
			'13:00','13:15','13:30','13:45',
			'14:00','14:15','14:30','14:45',
			'15:00','15:15','15:30','15:45',
			'16:00','16:15','16:30','16:45',
			'17:00','17:15','17:30','17:45',
			'18:00'
		],
		minDate:'-1970/01/01',
		maxDate:'+1971/01/01',
	});

})
