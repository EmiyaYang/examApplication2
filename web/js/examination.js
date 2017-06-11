(function ($) {
    $.getUrlParam = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r!= null)
            return decodeURI(r[2]);
        return null;
    }
})(jQuery)

function getParam(){
    var keyword= $.getUrlParam("keyword");
    var type = $.getUrlParam("type");
    if (keyword){
        $("#searchByWord").val(keyword);
    }
    if(type){
        if(type==="paper"){
            $("#searchByType label:first-child").button("toggle");
        }else if(type==="question"){
            $("#searchByType label:nth-child(2)").button("toggle");
        }
    }
    if(keyword || type){
        $("#go-search").trigger("click");
    }
}

//是否有试卷的缓存并读取
function initStoragePage() {
    if (window.sessionStorage.getItem("testTime")) {
        cur_time = parseInt(window.sessionStorage.getItem("testTime"));
    }
    if (window.sessionStorage.getItem("questions")) {
        var questions = JSON.parse(window.sessionStorage.getItem("questions"));
        $("#ques-list").empty();
        $("#quest-board").empty();
        QReceiver.getQuestions(questions);
        QReceiver.insertQuestions();
        start_test();
        getSummary();
        init_quest_view();
        //隐藏试卷概要
        $("#quest-preview").hide();
    }else{//假如没有题目的本地缓存的话加载试卷概要
        var paperId =  $.getUrlParam("paperId");
        $.ajax({
            type: "POST",
            url: "/ExaminationServlet.do",
            timeout: 5000,
            data: {
                paperId: paperId,
                forDescription:true
            },
            dataType: "json",
            success: function (result) {
                $("#paper-name").text(result.name);
                $("#paper-brief").text(result.brief);
                $("#paper-click").text(result.click);
                $("#paper-create-time").text(result.createTime)
            }

        });
    }
    if (window.sessionStorage.getItem("userAnswer")) {
        var userAnswer = JSON.parse(window.sessionStorage.getItem("userAnswer"));
        var i = 0;
        console.log(window.sessionStorage.getItem("userAnswer"));
        $(".quest-footer").each(function () {
            for (var j = 0; j < userAnswer[i].answer.length; j++) {
                $(this).find("[value='" + userAnswer[i].answer[j] + "']").addClass("active");
            }
            i++;
        })
    }
}
function setQuestionsStorage(result) {
    var jsonStr = JSON.stringify(result);
    window.sessionStorage.setItem("questions", jsonStr);
}
//
var QReceiver = {
    name: "ques-card-",
    quesId: 0,
    quesSet: null,
    target: "",
    setTarget: function (selector) {
        this.target = selector;
    },
    getQuesId: function () {
        return this.quesId++;
    },
    getQuestions: function (result) {
        this.quesSet = result;
    },
    insertQuestions: function () {
        //在主结果页面或试卷页面插入问题卡片
        // 并为每个卡片分配一个id
        var questCard = "";
        var questLink = "";
        var len = this.quesSet.length;
        //问题section标签id
        var id = "ques-card-" + this.getQuesId();
        var quesType = "";
        var quesClass = "";

        function printOptionsText(options) {
            var chars = ["A", "B", "C", "D", "E", "F", "G"];
            var template = "";
            var result = "";
            for (var j = 0; j < options.length; j += 1) {
                template = "\
                <tr>\
                    <td>" + chars[j] + ". " + options[j] + " &nbsp;&nbsp;&nbsp;&nbsp;</td>\
                </tr>";
                result += template;
            }
            return result;
        }

        function printOptions(options) {
            var chars = ["A", "B", "C", "D", "E", "F", "G"];
            var result = "";
            var template = "";
            for (var j = 0; j < options.length; j++) {
                template = "<button class=\"btn btn-default\" value=\"" + j + "\">" + chars[j] + "</button>";
                result += template;
            }
            return result;
        }

        for (var i = 0; i < len; i++) {
            quesType = (this.quesSet[i].type == 1) ? "单项" : "不定项";
            quesClass = (this.quesSet[i].type == 1) ? "opt-single" : "opt-multiple";
            questCard = "\
            <section class=\"quest\" id=\"" + id + "\">\
            <div class=\"quest-header\">\
                <h3>第 " + this.quesId + " 题 <small>" + quesType + "选择题</small></h3>\
            </div>\
            <hr/>\
            <div class=\"quest-content\">\
                <h4>" + this.quesSet[i].content + "</h4>\
                <table>\
                    " + printOptionsText(this.quesSet[i].items) + "\
                </table>\
            </div>\
            <hr/>\
            <div class=\"quest-footer " + quesClass + "\" data-quest-id=\"" + this.quesSet[i].id + "\">\
               " + printOptions(this.quesSet[i].items) + "\
            </div>\
            </section>";

            questLink = "<li class=\"list-group-item\"><a href=\"#" + id + "\">" + this.quesId + ". " + quesType + "选择题 <i class=\"fa fa-fw fa-chevron-right\"></i></a></li>";

            $("#quest-board").append(questCard);
            $("#ques-list").append(questLink);
            id = "ques-card-" + this.getQuesId();
        }

        quest_footer_nodes = $('.quest-footer');
    }
}
function getSummary() {
    $('#quest-overview').find('ul li a').each(function () {
        const qid = $(this).attr('href');
        var brief = $(qid + ' .quest-content').find('h4').text();
        if (brief.length > 9) {
            brief = brief.substr(0, 9) + ' ...';
        }

        $(this).append('<br/><span>' + brief + '</span>');
    });
}

function getUserAnswer(ans) {
    $(".quest-footer").each(function () {
        var singleAns = {"qid": 0, "answer": []};
        singleAns.qid = parseInt($(this).attr("data-quest-id"));
        singleAns.answer = [];
        $(this).find("button").filter(".active").each(function () {
            singleAns.answer.push(parseInt($(this).attr("value")));

        })
        ans.push(singleAns);
    });
}

function saveUserAnswer() {
    if ($(".quest-footer").length > 0) {
        var ansJson = [];
        getUserAnswer(ansJson);
        var jsonStr = JSON.stringify(ansJson);
        window.sessionStorage.setItem("userAnswer", jsonStr);
    }
}

function correctionResultToView(result) {
    function printCorrectChoice(correctChoice) {
        var str = "";
        var chioce = ["A", "B", "C", "D", "E", "F", "G"];
        for (var k = 0; k < correctChoice.length; k++) {
            str += chioce[correctChoice[k]];
        }
        return str;
    }

    var correctNum = 0;
    for (var i = 0; i < result.length; i++) {
        if (result[i].correct == true) {
            correctNum++;
        }
    }
    //在模态框中插入答题正确数
    $("#quest-accuracy").text("您答对了 " + correctNum + " / " + result.length + " 道题");
    var j = 0;
    $(".quest").each(function () {
        if (result[j].correct == true) {
            //插入正确标签
            $(this).find(".quest-header > h3").append("<span class=\"label right-top label-success\"> 正确答案: " + printCorrectChoice(result[j].correct_choice) + "</span>")
        } else {
            $(this).find(".quest-header > h3").append("<span class=\"label right-top label-danger\"> 正确答案: " + printCorrectChoice(result[j].correct_choice) + "</span>")
        }
        j++;
    });
    $(".loader").hide();
}

$(function () {

    initStoragePage();

    $("#quest-start").click(function () {

        $("#ques-list").empty();
        $("#quest-board").empty();

        var paperId = 1;

        //标签html
        $.ajax({
            type: "POST",
            url: "/ExaminationServlet.do",
            timeout: 5000,
            data: {
                paperId: paperId,
            },
            dataType: "json",
            success: function (result) {
                questions = result;
                QReceiver.getQuestions(result);
                QReceiver.insertQuestions();
                setQuestionsStorage(result)
                getSummary();
                init_quest_view();
                $("#quest-preview").hide()
            }
            // }else if(searchType==="paper"){
            //     PReceiver.getPapers(result);
            //     PReceiver.insertPapers();
            // }

        });
    });

    $("#quest-submit").click(function () {
        var ans = [];
        getUserAnswer(ans);
        var ansStr = JSON.stringify(ans);



        $.ajax({
            type: "POST",
            url: "/SubmissionServlet.do",
            timeout: 5000,
            data: {
                userId: 1,
                paperId: 1,
                secondUsed: 1200 - cur_time,
                submission: ansStr
            },
            dataType: "json",
            success: function (result) {

                setTimeout(function () {
                    correctionResultToView(result);
                    terminate_test();
                    window.sessionStorage.removeItem("questions");
                    window.sessionStorage.removeItem("testTime");
                    window.sessionStorage.removeItem("userAnswer");
                }, 1000);

            }
            // }else if(searchType==="paper"){
            //     PReceiver.getPapers(result);
            //     PReceiver.insertPapers();
            // }

        });
    })
})
