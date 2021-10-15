var myAlert1 = $("#svg_form");
var myMask = $("#mask");

var svgDocument = document;
var svgNS = "http://www.w3.org/2000/svg";
var svg_element = svgDocument.getElementById('svgContainer');

var tspan_width = new Array();
var tspan_x_coordinate = new Array();

var totalWidth = 100;
var maxHeight = 0;
/* 起点位置确定： （中点 or 偏右）*/
/* 中点情况：仅有此一条出弧 */
/* 偏右情况： 有入弧或者有向左出弧*/
function left_start_x(start) {
    var x = tspan_x_coordinate[start];
    var width = tspan_width[start];
    var flag = false;
    /* 检查是否有入弧*/
    for (var i = 0; i < size; i++) {
        if (pixel[i][start] != 0) {
            return x + width / 2 + 8;
        }
    }
    /* 检查是否有向左出弧 */
    for (var i = 0; i < start; i++) {
        if (pixel[start][i] != 0) {
            return x + width / 2 + 8;
        }
    }
    return x + width / 2;
}

/* 起点位置确定： （中点 or 偏左）*/
/* 中点情况：仅有此一条出弧 */
/* 偏左情况：有入弧或者有向左出弧*/
function right_start_x(index) {
    // console.log("index :" + index);
    // console.log(tspan_x_coordinate[index]);
    var x = tspan_x_coordinate[index];
    var width = tspan_width[index];
    var flag = false;
    /* 检查是否有入弧*/
    for (var i = 0; i < size; i++) {
        if (pixel[i][index] != 0) {
            return x + width / 2 - 8;
        }
    }
    /* 检查是否有向右出弧 */
    for (var i = index + 1; i < size; i++) {
        if (pixel[index][i] != 0) {
            return x + width / 2 - 8;
        }
    }
    return x + width / 2;
}

/* 终点位置确定： （中点 or 偏右）*/
/* 中点情况：有向左和向右的出弧或者没有出弧 */
/* 偏右情况：有向左的出弧*/
/* 偏左情况：有向右的出弧*/
function end_x(index) {
    // console.log("index: " + index);
    var x = tspan_x_coordinate[index];
    var width = tspan_width[index];
    var left = false;
    var right = false;
    /* 检查是否有向左的出弧 */
    for (var i = 0; i < index; i++) {
        if (pixel[index][i] != 0) {
            left = true;
            break;
        }
    }
    /* 检查是否有向右的出弧 */
    for (var i = index; i < size; i++) {
        if (pixel[index][i] != 0) {
            right = true;
            break;
        }
    }

    if (left && right || !left && !right) {
        return x + width / 2;
    } else {
        if (left) {
            return x + width / 2 + 8;
        }
        if (right) {
            return x + width / 2 - 8;
        }
    }
}
/* 创建路径*/
function create_path(start, end, relation) {
    var x0, x1, x2, x3;
    var y0, y1;
    var delt;
    var d_str = 'M';

    // console.log(start + "--" + end);
    /* 向右画弧 */
    if (Number(start) < Number(end)) {
        delt = end - start;
        // console.log(start + "   " + end);
        // console.log("start info ");
        // console.log($("#contain tspan")[start].getBBox())
        // console.log("end info ");
        // console.log($("#contain tspan")[end].getBBox())

        x0 = x1 = left_start_x(start);
        x2 = x3 = end_x(end);
        y0 = y3 = $("#contain tspan")[end].getBBox().y;
        y1 = y2 = y0 - (20 - 0.1 * delt) * delt;
        d_str = d_str + x0 + "," + y0 + " C" + x1 + "," + y1 + " " + x2 + ", " + y2 + " " + x3 + "," + y3;
        var path_element = document.createElementNS("http://www.w3.org/2000/svg", "path");
        path_element.setAttributeNS(null, "fill", "none");
        path_element.setAttributeNS(null, "stroke-width", "1");
        path_element.setAttributeNS(null, "stroke", "black");
        path_element.setAttributeNS(null, "d", d_str);
        path_element.setAttributeNS(null, "marker-end", "url(#arrow)")
        svg_element.appendChild(path_element);
    } else {
        delt = start - end;
        x0 = x1 = right_start_x(start);
        x2 = x3 = end_x(end);
        y0 = y3 = $("#contain tspan")[end].getBBox().y;
        y1 = y2 = y0 - (20 - 0.1 * delt) * delt;
        d_str = d_str + x0 + "," + y0 + " C" + x1 + "," + y1 + " " + x2 + ", " + y2 + " " + x3 + "," + y3;
        var path_element = document.createElementNS("http://www.w3.org/2000/svg", "path");
        path_element.setAttributeNS(null, "fill", "none");
        path_element.setAttributeNS(null, "stroke-width", "1");
        path_element.setAttributeNS(null, "stroke", "black");
        path_element.setAttributeNS(null, "d", d_str);
        path_element.setAttributeNS(null, "marker-end", "url(#arrow)")
        svg_element.appendChild(path_element);
    }
    var height = Number(y1) + 5 + 0.25 * (350 - Number(y1));
    var element = document.createElementNS("http://www.w3.org/2000/svg", "text");
    element.setAttributeNS(null, "x", (Number(x0) + Number(x3)) / 2);
    element.setAttributeNS(null, "y", height);
    element.setAttributeNS(null, "text-anchor", "middle");
    element.setAttributeNS(null, "font-size", "13");
    element.setAttributeNS(null, "fill", "red");

    if (height > Number(maxHeight)) {
        maxHeight = height;
    }
    var text_node = svgDocument.createTextNode(relation);
    element.appendChild(text_node);
    svg_element.appendChild(element);
}

/* 下载文件 */
function download_swf() {
    var content = document.getElementById("svgContainer").innerHTML;
    $.ajax({
        type: "GET",
        url: "down",
        dataType: "json",
        data: {
            svgStr: content,
            width: 800,
            height: 500
        },
        success: function(result) {},
        error: function(e) {
            console.log("ERROR: ", e);
        }
    });
}


$("#svg_create").click(function() {

    if (segcheck == true) {
        window.alert('请先切换出分词修改状态，否则无法保存！');
        return;
    }


    myMask.css('display', "block");
    myAlert1.css('display', "block");
    $("body").css('overflow', "hidden");

    var text = document.createElementNS(svgNS, "text");
    text.setAttributeNS(null, "id", "contain");
    text.setAttributeNS(null, "x", "50");
    text.setAttributeNS(null, "y", "350");
    svg_element.appendChild(text);

    var defs = document.createElementNS(svgNS, "defs");

    var marker = document.createElementNS(svgNS, "marker");
    marker.setAttributeNS(null, "id", "arrow");
    marker.setAttributeNS(null, "markerUnits", "strokeWidth");
    marker.setAttributeNS(null, "markerWidth", "12");
    marker.setAttributeNS(null, "markerHeight", "12");
    marker.setAttributeNS(null, "viewBox", "0 0 12 12");
    marker.setAttributeNS(null, "refX", "6");
    marker.setAttributeNS(null, "refY", "6");
    marker.setAttributeNS(null, "orient", "auto");

    var path = svgDocument.createElementNS(svgNS, "path");
    path.setAttributeNS(null, "d", "M2,2 L10,6 L2,10 L6,6 L2,2");
    path.setAttributeNS(null, "fill", "#000000");

    marker.appendChild(path);
    defs.appendChild(marker);
    svg_element.appendChild(defs);

    var text_element = svgDocument.getElementById('contain');

    /* 生成<tspan>*/
    for (var i = 0; i < words.length; i++) {
        var tspan_element = document.createElementNS(svgNS, "tspan");
        var text_node = svgDocument.createTextNode(words[i]);
        tspan_element.appendChild(text_node);
        if (i != 0) {
            totalWidth += 20;
            tspan_element.setAttributeNS(null, "dx", 20);
        }
        tspan_element.setAttributeNS(null, "id", 'tspan' + i);
        tspan_element.setAttributeNS(null, "border", "1");
        tspan_element.setAttributeNS(null, "font-size", "17px");
        text_element.appendChild(tspan_element);

        tspan_width[i] = $("#contain tspan")[i].getBBox().width;
        totalWidth += tspan_width[i];
        tspan_x_coordinate[i] = $("#contain tspan")[i].getBBox().x;
    }
    svg_element.setAttribute("width", totalWidth + "px");
    /* 初始化弧关系二维数组 */
    for (var i = 0; i < words.length; i++) {
        pixel[i] = new Array();
        for (var j = 0; j < words.length; j++) {
            pixel[i][j] = 0;
        }
    }
    for (var i = 0; i < arcs.length; i++) {
        pixel[arcs[i].start][arcs[i].end] = 1;
    }

    for (var i = 0; i < arcs.length; i++) {
        create_path(arcs[i].start, arcs[i].end, arcs[i].text);
    }
    svg_element.setAttribute("height", Number(maxHeight) + 100 + "px");


});

$("#svg_no").click(function() {
    myAlert1.css('display', "none");
    myMask.css('display', "none");
    svg_element.innerHTML = "";
});

$("#svg_save").click(function() {
    var content = document.getElementById("svgContainer").innerHTML;
    var params = { // 参数
        svgStr: content,
        weight: 600
    };

    var form = document.createElement('form')
    form.id = 'down'
    form.name = 'down'
    document.body.appendChild(form)
    for (var obj in params) {
        if (params.hasOwnProperty(obj)) {
            var input = document.createElement('input')
            input.tpye = 'hidden'
            input.name = obj;
            input.value = params[obj]
            form.appendChild(input)
        }
    }
    form.method = "GET" //请求方式
    form.action = 'down'
    form.submit()
    document.body.removeChild(form)

});