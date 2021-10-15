// $(".radios").each(function () {
//     //循环使用is进行判断
//     if ($(this).is(":checked")) {
//         alert($(this).val());
//     }
// });

$(document).ready(function() {
    $(".radios").click(function () {
        if ($(this).val()==="1") {
            $(".inp").attr('placeholder','关键字查询：输入要查询的关键字、标签、词性等');
        }
        else{
            $(".inp").attr('placeholder','序号查询：输入要查询的句子序号');
        }
    });
});



