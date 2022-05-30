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