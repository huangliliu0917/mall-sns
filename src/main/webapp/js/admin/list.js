var dataList={};
dataList.searchModel={};

dataList.load=function(){
    dataList.circleModel=$("#dataList tr").eq(1).clone();
    dataList.circleModel.show();
    dataList.searchModel.pageNo=0;
    dataList.searchModel.ascOrdesc=0;//0:倒序，1：顺序
    dataList.searchModel.pageSize=10;
    dataList.searchModel.sortField="";//排序字段
    dataList.searchAll();
};

//================================个性的=========================================
//搜索
dataList.search=function(){
    dataList.searchModel.circleName=$("#circleName").val();
    dataList.searchModel.suggested=$("#suggested").val();
    dataList.searchAjax(dataList.searchModel);
};

dataList.searchAjax=function(searchModel){
    var ld=layer.load(5, {shade: false});
    $.ajax({
        type:'POST',
        url: '/top/circle/getCircleList',
        dataType: 'json',
        contentType:"application/json",
        data: JSON.stringify(searchModel),
        success:function(result){
            layer.close(ld);
            dataList.total=result.total;
            dataList.totalPage=result.totalPage;


            dataList.buildCircleList(result.data);

            dataList.setPageNoAndSize(dataList.searchModel.pageNo,
                dataList.searchModel.pageSize,dataList.total,dataList.totalPage);


        },
        error:function(e){
            layer.close(ld);
            layer.msg("获取圈子信息出错！");
        }
    });
};

dataList.buildCircleList=function(list){
    var tbody=$("#dataList tbody");
    $(tbody).children("tr").remove();
    for(var i=0;i<list.length;i++){
        var model=dataList.circleModel.clone();
        var val=list[i];
        var html=$(model).children("td").eq(0).html();
        html=html.replace(/chk1/g, "chk" + i);
        $(model).children("td").eq(0).html(html);
        $(model).children("td").eq(1).text(val.circleId);
        $(model).children("td").eq(2).text(val.circleName);
        $(model).children("td").eq(3).children("img").attr("src",val.pictureUrl);
        $(model).children("td").eq(4).text(val.categoryName);
        $(model).children("td").eq(5).text(val.leaderName);
        $(model).children("td").eq(6).text(val.createDate);
        $(model).children("td").eq(7).text(val.suggested?'热门':'普通');
        $(model).children("td").eq(8).text(val.userAmount);
        $(model).children("td").eq(9).text(val.articleAmount);
        $(model).children("td").eq(10).children("a").eq(0).attr("href","/top/circle/editCircle?id="+val.circleId);
        $(tbody).append(model);
    }
};

//================================个性的END=========================================

dataList.setPageNoAndSize=function(pageNo,pageSize,total,totalPage){
    $("#DataTables_Table_0_info").text("显示 "+(pageNo*pageSize+1)+" 到 "+(pageNo+1)*pageSize+" 项，共 "+total+" 项");

    var showPageNo=dataList.searchModel.pageNo-2;
    if(showPageNo<1){
        showPageNo=1;
    }
    var lis=$(".pagination li");
    lis.show();
    lis.attr("class","paginate_button");
    $(lis).each(function(index,val){
        if(index>1&&index<=9){
            $(val).children("a").text(showPageNo);
            if(dataList.searchModel.pageNo==showPageNo-1){
                $(val).attr("class","paginate_button active");
            }
            if(showPageNo>totalPage){
                $(val).hide();
            }
            showPageNo++;
        }
    });

};
//默认搜索
dataList.searchAll=function(){
    $("#circleName").val("");
    $("#suggested").val(-1);
    dataList.goTo(0);
};

dataList.searchCondition=function(){
    dataList.goTo(0);
};

dataList.goTo=function(pageNo){
    dataList.searchModel.pageNo=pageNo;
    dataList.search();
};

dataList.goToUp=function(){
    dataList.searchModel.pageNo--;
    if(dataList.searchModel.pageNo<0){
        dataList.searchModel.pageNo=0;
        return;
    }
    dataList.search();
};

dataList.goToDown=function(){
    if(dataList.searchModel.pageNo+1<dataList.totalPage){
        dataList.searchModel.pageNo++;
    }else {
        return;
    }
    dataList.search();
};

dataList.goToFirst=function(){
    dataList.searchModel.pageNo=0;
    dataList.search();
};

dataList.goToLast=function(){
    dataList.searchModel.pageNo=dataList.totalPage==0?0:dataList.totalPage-1;
    dataList.search();
};

dataList.sortConfig=function(obj,sortField){

    if($(obj).attr("class")=="sorting_desc"){
        dataList.searchModel.ascOrdesc=1;
        $(obj).attr("class","sorting_asc");
    }else {
        dataList.searchModel.ascOrdesc=0;
        $(obj).attr("class","sorting_desc");
    }

    dataList.searchModel.pageNo=0;
    dataList.searchModel.sortField=sortField;
    dataList.search();
};

dataList.checkBoxClick=function(obj){
    //获取是否单选还是多选
    var parameter=dataList.getUrlParam("extend");
    if(parameter=="radio"){//单选
        if($(obj).prop("checked")){
            $("input[type='checkbox'][name='chkGood']","#dataList").removeAttr('checked');
            $(obj).prop('checked',true);
        }
    }else {//多选
        var name=$(obj).attr("name");
        if(name=="chkAll"){
            var isCheck=$(obj).prop("checked");
            $("input[type='checkbox'][name='chkGood']","#dataList").prop("checked", isCheck);
        }
    }

};

dataList.getUrlParam=function(name){
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
};