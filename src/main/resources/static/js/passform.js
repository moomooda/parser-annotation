var myAlert = $("#pass_form");
var myMask = $("#mask");

//重新渲染表单函数
function renderForm() {
    layui.use('form', function() {
        var form = layui.form; //高版本建议把括号去掉，有的低版本，需要加()
        form.render();
    });
}
// 下拉框手动赋值
function set_select_checked(selectId, checkValue) {
    var select = document.getElementById(selectId);

    for (var i = 0; i < select.options.length; i++) {
        if (select.options[i].value == checkValue) {
            select.options[i].selected = true;
            break;
        }
    }
}
/* 判断是否标注完成 */
function isDone() {
    var is_done;
    $.ajax({
        type: "GET",
        async: false,
        url: "isDone",
        data: {
            batchId: Number(document.getCookie("current_batch")),
            userId: Number(localStorage.getItem("userId"))
        },
        success: function(result) {
            result = eval("(" + result + ")");
            if (result.status === "success") {
                is_done = "0";
            } else {
                is_done = "1";
            }
        },
        error: function(e) {
            console.log("ERROR: ", e);
            is_done = "1";
        }
    });
    return is_done;
}
$("#pass").click(function() {
    myMask.css('display', "block");
    myAlert.css('display', "block");
    $("body").css('overflow', "hidden");
});
$("#pass_yes").click(function() {
    var status = $("input[name='reason']:checked").val();
    if (typeof(status) == undefined) {

    }
    var result = localStorage.getItem("result");
    var result = eval("(" + result + ")");

    $.ajax({
        type: "POST",
        url: "updateStatus",
        dataType: "json",
        data: {
            corpusId: result.data.corpusId,
            corpusStatus: status
        },
        success: function(result) {
            // 判断是否标注完成
            var is_done = isDone(result.data.corpusId);
            if (is_done == "1") {
                window.alert("标注完成!");
                return;
            } else {
                // 获取下一句
                wordsSize = []; // 词大小数组
                widthArr = []; // td宽度数组
                arcs = [];
                $("#canvasdiv").empty();
                $("#content").empty();
                $('#canvasdiv').html(canvas_str);
                c = document.getElementById("cc");
                hb = c.getContext("2d");
                hb.clearRect(0, 0, canvasWidth, canvasHeight); //与cover大小一致
                getWordsAndTags("", false)
            }
        },
        error: function(e) {
            console.log("ERROR: ", e);
        }
    });
})
$(".close").click(function() {
    myAlert.css('display', "none");
    myMask.css('display', "none");
    $("body").css('overflow', "auto");
    
});

$("#pass_no").click(function() {
    myAlert.css('display', "none");
    myMask.css('display', "none");
});