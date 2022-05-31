	var cnt = 0;
    
    $(document).ready(function(){
    	$("#mainMenu>li").mouseover(function(){
         $(this).find(".subMenu").css({display:"block"});
     }).mouseout(function(){
         $(".subMenu").css({display:"none"});
     })
    })
    
    function menuOn(){
    		cnt ++;
    		if(cnt % 2 == 1){
    			$("nav").css({left:"0",transition:"all 0.5s"})
    		}else{
    			$("nav").css({left:"-150px",transition:"all 0.5s"})
    		}
    	}

	function chatOn(){
    		cnt ++;
    		if(cnt % 2 == 1){
    			$("#chat").css({top:"350px",transition:"all 0.5s"})
    		}else{
    			$("#chat").css({top:"25px",transition:"all 0.5s"})
    		}
    	}

	