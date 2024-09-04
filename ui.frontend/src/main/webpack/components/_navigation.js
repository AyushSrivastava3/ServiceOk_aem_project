// Example of how a component should be initialized via JavaScript
// This script logs the value of the component's text property model message to the //console

(function() { 
    "use strict";
    var $;
    $ = require('jquery');
    $(".list__header").append('<a href="#" class="mobile_menu" role="menu" label="menu"></a>');
    $(".mobile_menu").on("click",function(event){
        event.preventDefault();
        $(this).toggleClass("close");
        $(".cmp-navigation").toggle();
        $(".cmp-search").toggle();
        $(".cmp-navigation__group").each(function(index,element){
            if(index==0){
                $(this).append($(".list.list__header .links__nav--items"));
            }
        });
        
    });

    $(".cmp-navigation>ul>li").each(function(){
        if($(this).find(".cmp-navigation__group").length>0){
            if($(this).find(".cmp-navigation__group>li").length<=4){
                $(this).find(".cmp-navigation__group").addClass("one-column");
            }
            $(this).find(">a").append('<span href="#" class="drop-arrow"></span>');
            $('<a href="#" class="mobile_arrow"></a>').insertBefore( $(this).find(".cmp-navigation__group") );
        }
    });

    
    $(".mobile_arrow").on("click",function(){
        $(this).parent().toggleClass("open");
        $(this).parent().find("ul").toggleClass("selected");        
    });

    $( ".cmp-navigation__item-link" ).on('focus',function() {
        if($(window).width()>=1024){            
            if($(this).closest("ul").parent().hasClass("cmp-navigation") && $(this).parent().find(".cmp-navigation__group").length>0){
                $(".cmp-navigation__group").not(":eq(0)").css("display","none");
                $(this).parent().find(".cmp-navigation__group").css("display","flex");
            }
            if($(this).closest("ul").parent().hasClass("cmp-navigation") && $(this).parent().find(".cmp-navigation__group").length==0){
                $(".cmp-navigation__group").not(":eq(0)").css("display","none");
            }
        }
    });
     
    $('.cmp-navigation__item').hover(function () {
        if ($('.cmp-navigation__group',this).length > 0) {
            if($(window).width()>=1024){
                $('.cmp-navigation__group',this).stop().show();
            }
        }
        },function () {
            if ($('.cmp-navigation__group',this).length > 0) {
                if($(window).width()>=1024){
                    $('.cmp-navigation__group',this).stop().hide();
                }
            }
        }
    );
   
   function makeNavResponsive(){
        var windowWidth = $(window).width();
        var actualAppWidth = 936;
        var balanceWidth = (windowWidth-actualAppWidth)/2;
        if(windowWidth<1024 && !$("body").hasClass("small-device")){

            $(".mobile_menu").removeClass("close");
            $(".cmp-experiencefragment--header .image, .cmp-navigation").removeAttr("style");
            $(".cmp-experiencefragment--header .list.list__header,.cmp-search, .cmp-navigation").removeAttr("style");
            $(".cmp-navigation__group").each(function(index,element){
                if(index!=0){
                    $(this).removeAttr("style");
                    $(this).removeAttr("style");
                }
            }); 
            $("body").addClass("small-device").removeClass("large-device");
        }else if(windowWidth>=1024 && !$("body").hasClass("large-device")){       
            ////console.log("GOING TO Large DEVICE");     
            $(".cmp-experiencefragment--header .image, .cmp-navigation").css("padding-left",balanceWidth);
            $(".cmp-experiencefragment--header .list.list__header").show();
            $(".cmp-experiencefragment--header .cmp-search").css("right",balanceWidth);
            $(".cmp-experiencefragment--header .list.list__header, .cmp-navigation").css("padding-right",(balanceWidth+200));

            $(".cmp-navigation__group").each(function(index,element){
                if(index!=0){
                    $(this).css("padding-left",balanceWidth);
                    $(this).css("padding-right",balanceWidth);   
                }
            });
            $("body").addClass("large-device").removeClass("small-device");
        }
    }
    $(window).on('resize',function(){
        makeNavResponsive();
    });
    makeNavResponsive();

    //Search click events
    let ITEMS_PER_PAGE = 10;
    $("form#search-result-form").submit(function(e){
        e.preventDefault();              
    });

    $(".cmp-search__input").on("keyup",function(event) {
        event.preventDefault(); 
        let searchTag = '';
        searchTag = $(this).closest("form").find(".cmp-search__input").val();
        if (event.keyCode === 13) {
            $(".cmp-search__results").hide();
            if(searchTag !='')
                searchForString(searchTag);  
        }
        if($(this).closest("form").hasClass("cmp-search__form") && searchTag.length>3){
            //console.log("I am testing here");
            getSearchResults(searchTag,true);
        }

    });
    

    $(".cmp-search__icon1,.search-tag").on("keyup",function(event) {
        //console.log("TEST CLICN");
        event.preventDefault();
        if (event.keyCode === 13) {
            $(this).click();
        }
    });

    $(".cmp-search__icon1").on("click",function(event) {        
        let searchTag = $(this).closest("form").find(".cmp-search__input").val();
        event.preventDefault();
         if(searchTag !='') searchForString(searchTag); 
    });

    function searchForString(searchTag)
    {
        //  log('REDIRECT==>',$(".cmp-search__form").attr("data-redirect"));
        let redirectPage = $(".cmp-search__form").attr("data-redirect");
        let newSearchURL = redirectPage + "?searchString=" + searchTag;
        if(window.location.href.includes(redirectPage))
        {
            const nextState = { additionalInformation: 'Updated the URL with JS without reloading' };
            window.history.pushState(nextState, '', newSearchURL);
            getSearchResults(searchTag,false);
        } 
        else    
        {
            window.location.href= redirectPage + "?searchString=" + searchTag;  
        } 
    }


    function prepareSearchResults(resultsArray){
        let resultsString="";
        let targetEle = $(".search-result__innercontent ul");
        $.each(resultsArray, function(key,item) {            
            let itemDec = (item.description === 'undefined' || item.description=='') ? window.location.origin+item.link : item.description;
            //console.log("itemDec",itemDec);
            resultsString+='<li><a href='+ item.link +'>'+item.title+'</a><p>'+itemDec+'</p></li>';
        });
        $(targetEle).html(resultsString);
        let totalItems = resultsArray.length;
        let numberOfpages = Math.ceil(totalItems/ITEMS_PER_PAGE);
        $(".search-result__pagination ul").html('');
        if(numberOfpages>1)
            makePagination(numberOfpages);
    }



    function getSearchResults(searchString,fromHeader){
        let searcURL = $(".cmp-search__form").attr("action")+"?fulltext="+searchString+"&resultsSize=150";
        $.ajax({
            type: 'GET',
            url: searcURL,
            success:function(responseData){
                //console.log("searchString",searchString);
                //console.log("responseData==>",responseData);
                if(responseData.results.length>0){
                    let getSrotingData = sortResults(responseData.results,searchString);
                    //console.log('getSrotingData',getSrotingData);
                    if(fromHeader){
                        autoPopulateResults(getSrotingData);
                    }else{
                        prepareSearchResults(getSrotingData);
                        $(".cmp-search__input").val(searchString);
                        $(".cmp-search__results").hide();
                    }
                }



            }
        });
    }


    const sortResults = (array, searchString) => {
        searchString = searchString.toLowerCase();
        return array.sort((a, b) => 
              b.title.toLowerCase().startsWith(searchString) - a.title.toLowerCase().startsWith(searchString) ||
              a.title.localeCompare(b.title)
          );
    }

    function autoPopulateResults(responseData){       
        if(responseData.length>0){
            let autoResultsGrid = '';
            $.each(responseData, function(key,item) {
                autoResultsGrid+='<a class="cmp-search__item" data-cmp-hook-search="item" role="option" aria-selected="false" href="'+ item.link +'">'+
                '<span class="cmp-search__item-title" data-cmp-hook-search="itemTitle">'+ item.title +'</span></a>';
            });
            $(".cmp-search__results").html(autoResultsGrid);
            $(".cmp-search__results").show();
        }
    }

    function makePagination(numberOfPages){
        var items = $(".search-result__innercontent ul li");
        preparePagination(numberOfPages);      
        items.slice(ITEMS_PER_PAGE).hide();
    }

    function preparePagination(numberOfpages){
        var paginationGrid = $(".search-result__pagination ul");
        paginationGrid.empty();
        let windowWidth = $(window).width();
        let prevNextBtns = (windowWidth<768)?{prev:'<',next:'>'}:{prev:'Previous',next:'Next'}
        paginationGrid.append('<li><a id="prev" class="disabled">' + prevNextBtns.prev + '</a></li>');
        for (let pageItems = 0; pageItems < numberOfpages; pageItems++) {
            if(pageItems == 0)
                paginationGrid.append('<li class="active" data-number='+(pageItems+1)+'>'+(pageItems+1)+'</li>');
            else
                paginationGrid.append('<li data-number='+(pageItems+1)+'>'+(pageItems+1)+'</li>');
        }
        paginationGrid.append('<li><a id="next">' + prevNextBtns.next + '</a></li>');
    }
    

    $(document).on('click','.search-result__pagination li',function(e){
        if($(this).find("a").attr("id") == "next")
            goToNextPage(e);
        else if($(this).find("a").attr("id") == "prev")
            goToPrevPage(e);
        else
            goToPage(parseInt($(this).text()));
    })

    function goToNextPage(e)
    {
        e.preventDefault();
        if(!$("#next").hasClass("disabled"))
        {
            $("#prev").removeClass("disabled");
            let currentPage = $(".search-result__pagination ul li.active").data('number');
            goToPage(parseInt(currentPage)+1);
        }        
    }

    function goToPrevPage(e){
        e.preventDefault();
        if(!$("#prev").hasClass("disabled")){
            $("#next").removeClass("disabled");
            let currentPage = $(".search-result__pagination ul li.active").data('number');
            goToPage(parseInt(currentPage)-1);
        }      
    }

    function goToPage(pageNumber)
    {
        let itemsCount = $(".search-result__innercontent ul li");
        var showFrom = ITEMS_PER_PAGE * (pageNumber - 1);
        var showTo = showFrom + ITEMS_PER_PAGE;
        $(".search-result__pagination ul li").removeClass("active");
        $(".search-result__pagination ul").find("li[data-number='" + pageNumber + "']").addClass('active');
         itemsCount.hide().slice(showFrom, showTo).show();
         let totalPageCount = Math.ceil($(".search-result__innercontent ul li").length/ITEMS_PER_PAGE);
         $(window).scrollTop(0);

        if(pageNumber == totalPageCount)
            $("#next").addClass("disabled");
        else
            $("#next").removeClass("disabled");

        if(pageNumber == 1)
            $("#prev").addClass("disabled");
        else
            $("#prev").removeClass("disabled");
    }


    $( document ).ready(function() {
        if(window.location.href.includes("?searchString="))
        {
            const params = new URLSearchParams(window.location.search);
            let searchingStr = params.get('searchString'); 
            $(".cmp-search__input").val(searchingStr);
            getSearchResults(searchingStr,false);
        }
    });
    $(document).on("click", function(e) {
        if ($(e.target).is(".cmp-search__input") === false) {
          $(".cmp-search__results").hide();
        }
      });
        // Makedropdown focus functionality
    $( ".search-tag, .cmp-search__input,.cmp-search__icon1" ).on('focus',function() {
        //console.log("TEST ICON FOucs");
        if($(".cmp-search__results a").length>0){
            $(".cmp-search__results").show();
        }
    });
}());
