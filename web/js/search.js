

var QReceiver ={
	name:"ques-card-",
    quesId:1,
    quesSet:null,
    target:"",
    setTarget:function (selector) {
        this.target = selector;
    },
    getQuesId:function () {
        return this.quesId++;
    },
    getQuestions:function (result) {
        this.quesSet = result;
    },
    insertQuestions:function () {
	    //在主结果页面或试卷页面插入问题卡片
        // 并为每个卡片分配一个id
        var dom="";
        var len = this.quesSet.length;
        var id ="ques-card-"+this.getQuesId();


        function printOptions(options) {
            var chars = ["A","B","C","D","E","F","G"]
            var result="";
            for (var j=0;j<options.length;j++){
                result += chars[j]+"."+options[j]+"<br>"
            };
            return result;
        }
        for(var i=0;i<len;i++){


            dom = "\
            <div class=\"panel panel-info\">\
                <div class=\"panel-heading\" >\
                    <div class=\"row\">\
                        <div class=\"question-title col-xs-11\" >"+this.quesSet[i].content+"</div>\
                        <div class=\"glyphicon glyphicon-plus col-xs-1\" title=\"添加到收藏\"></div>\
                    </div>\
                    <div class=\"btn btn-block btn-default text-center\" data-toggle=\"collapse\" href=\"#" + id + "\"><span class=\"glyphicon glyphicon-circle-arrow-down\"></span></div>\
                </div>\
                <div id=\"" + id + "\" class=\"panel-collapse collapse\">\
                    <div class=\"row text-center\">\
                        <div class=\"col-xs-4\"><span class=\"glyphicon glyphicon-user\"></span>：<a class=\"ques-author\">XXX</a></div>\
                        <div class=\"col-xs-4\"><span class=\"glyphicon glyphicon-time\"></span>：<span class=\"ques-time\">2017-5-12</span></div>\
                        <div class=\"col-xs-4\"><span class=\"glyphicon glyphicon-bookmark\"></span>：<span class=\"ques-subject\">JAVA</span></div>\
                    </div>\
                    "+ printOptions(this.quesSet[i].items)+"\
                </div>\
            </div>";

            $(this.target).append(dom);
            id ="ques-card-"+this.getQuesId();
        }



    }
}

var PReceiver ={
    name:"paper",
    paperId:1,
    paperSet:null,
    target:"#main-result",
    setTarget:function (target) {
        this.target=target;
    },
    getPaperId:function () {
        return this.paperId++;
    },
    getPapers:function (result) {
        this.paperSet = result;
    },
    insertPapers:function () {
        //要在主结果面板插入试卷卡片
        // 并绑定 查看详细 按钮的事件 点击生成新试卷区域
        //先将搜索面板清空
        $(this.target).empty();
        var card="";

        for(var i =0;i<this.paperSet.length;i++){
            card = "\
                <div  class=\"panel panel-default\">\
                    <div class=\"panel-body\">\
                        <div class=\"row\">\
                            <div class=\"paper-name col-xs-11\">"+this.paperSet[i].name+"</div>\
                            <div class=\"col-xs-1 glyphicon glyphicon-plus\"></div>\
                        </div>\
                    </div>\
                    <div class=\"panel-footer\">\
                        <div class=\"row\">\
                            <div class=\"col-xs-2 \"><div class=\"btn btn-default  do-test\"  >立即考试</div></div>\
                            <div class=\"col-xs-2\"><div class=\"btn btn-default  look-detail\" data-paper-id=\""+this.paperSet[i].id+"\">查看试题</div></div>\
                        </div>\
                    </div>\
                </div>";
            $(this.target).append(card);
        }

        $(".look-detail").click(function () {
            //存放标签页的id
            var searchType;
            if($("#paper").is(":checked")){
                searchType = "paper";
            } else{
                searchType="question";
            }
            var paperId = $(this).attr("data-paper-id");
            var name = "paper"+PReceiver.getPaperId();
            //标签html
            var tab ="\
            <li >\
                <a href=\"#"+name+"\" data-toggle=\"tab\" >\
                "+$(this).parents(".panel").find(".paper-name").text()+"\
                <span class=\"glyphicon glyphicon-remove \"></span>\
                </a> \
            </li> ";
            //标签内容html
            var tabPane = "\
            <div id=\""+name+"\" class=\"tab-pane fade active\" >\
            </div>";
            //将其加入标签区域
            $("#tabs").append(tab);
            //加入标签内容区域
            $("#my-tab-content").append(tabPane);
            //申请试卷对应的试题
            $.ajax({
                type:"POST",
                url:"/SearchServlet.do",
                timeout:5000,
                data:{
                    type:"question",
                    paperId:paperId,
                },
                dataType:"json",
                success:function(result) {
                    QReceiver.getQuestions(result);
                    QReceiver.setTarget("#" + name);
                    QReceiver.insertQuestions();
                }
                    // }else if(searchType==="paper"){
                    //     PReceiver.getPapers(result);
                    //     PReceiver.insertPapers();
                    // }

            });

            $("#tabs a[href='#"+name+"']").trigger("click");
            $("#tabs a[href='#"+name+"'] .glyphicon-remove").click(function () {
                $("#"+name).remove();
                $(this).parents("li").remove();
                //切换到下一个标签
                $("#tabs li:last a").trigger("click");
            })
        })//.look-detail的绑定结束
    }
}

$(function(){

    //ajax
	$("#go-search").click(function(){
		var searchType;
		if($("#paper").is(":checked")){
			searchType = "paper";
        } else{
		    searchType="question";
		}


		$.ajax({
			type:"POST",
			url:"/SearchServlet.do",
            timeout:5000,
			data:{
					type:searchType,
					keyword:$("#searchByWord").val(),
					sort:$("sortWay").val()
				 },
			dataType:"json",
			success:function(result){
			    if(searchType==="paper") {
                    PReceiver.getPapers(result);
                    PReceiver.setTarget("#main-result");
                    PReceiver.insertPapers();
                    // console.log(result);
                }
                // }else if(searchType==="paper"){
                //     PReceiver.getPapers(result);
                //     PReceiver.insertPapers();
                // }
                else{
                   console.log("没有选择搜索目标类型")
                }

            }

		});
	});


})
