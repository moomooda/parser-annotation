//切记：每次旋转或者平移了原点之后都要保持回到原位，否则后面会很麻烦==！
function drawcurve() {
    hb.lineWidth = 0.5; // 控制线的宽度
    hb.clearRect(0, 0, canvasWidth, canvasHeight); // 与cover大小一致

    console.log("the size of arcs in drawcurve : " + arcs.length);
    for (var i = 0; i < arcs.length; i++) {
        // console.log(i + "  画线！");
        hb.beginPath();

        if (Number(arcs[i].start) < Number(arcs[i].end)) {
            //	console.log("右箭头  " +  "  start :" + arcs[i].start + "   end:" + arcs[i].end);           //	console.log("x1: " + arcs[i].x1 +";x2: " + arcs[i].x2 + ";width: " + arcs[i].width);
            var begin = arcs[i].x1 + widthArr[arcs[i].start] / 4 * 3;
            var end = arcs[i].x2 + widthArr[arcs[i].end] / 4 * 2;
            hb.moveTo(begin, arcs[i].y1);
            hb.bezierCurveTo(begin, arcs[i].y, end, arcs[i].yy, end, arcs[i].y2);
            // console.log("开始点： （" + (begin) + "," + (arcs[i].y1) + ")")
            // console.log("控制点一： （" + (begin) + "," + arcs[i].y + ")")
            // console.log("控制点二： （" + (end) + "," + arcs[i].yy + ")")
            // console.log("结束点： （" + (end) + "," + arcs[i].y2 + ")")

            if (arcs[i].choose == true) {
                hb.strokeStyle = "rgb(255,0,0)"; // 红色
                hb.fillStyle = "rgb(255,0 , 0)";
            } else {
                hb.fillStyle = "black";
                hb.strokeStyle = "black"; // 蓝色
            }
            hb.stroke();
            hb.save();
            hb.closePath();
            hb.beginPath();
            // 画箭头
            hb.translate(end, arcs[i].y2);

            hb.rotate(1.5); // left->right

            hb.lineTo(-5, -5);
            hb.lineTo(5, 0);
            hb.lineTo(-5, 5);
            hb.lineTo(0, 0);
            hb.fill();
            hb.restore();
            var vancas = document.getElementById('cc').getContext("2d"); // 原点归为
            hb = vancas;
            arcs[i].xtxt = (begin + end) / 2; // 更改弧线中心，及tag所在位置
            hb.translate(arcs[i].xtxt - 10, arcs[i].ytxt - 7);
            hb.fillStyle = "#F9F9F9";
            hb.fillRect(0, 0, 20, 10);
            // if(arcs[i].choose == true){
            hb.fillStyle = "rgb(255,0 , 0)";
            /*
             * }else{ hb.fillStyle="black"; }
             */
            hb.translate(10, 7);
            hb.textAlign = 'center';
            hb.font = "14.5px serif";
            hb.fillText(arcs[i].text, 0, 0);
            hb.restore();
            hb.translate((-1) * arcs[i].xtxt, (-1) * arcs[i].ytxt);
            hb.closePath();
        } else {
            //				console.log("左箭头  " +  "  start :" + arcs[i].start + "   end:" + arcs[i].end);
            //	console.log("x1: " + arcs[i].x1 +";x2: " + arcs[i].x2 + ";width: " + arcs[i].width);

            var begin = arcs[i].x1 + arcs[i].width / 4;
            var end = arcs[i].x2 + widthArr[arcs[i].end] / 4 * 2;
            hb.moveTo(begin, arcs[i].y1);
            hb.bezierCurveTo(begin, arcs[i].y, end, arcs[i].yy, end, arcs[i].y2);

            //	console.log("开始点： （" + (begin) + "," +(arcs[i].y1) + ")")
            //	console.log("控制点一： （" + (begin) + "," +arcs[i].y + ")")
            //	console.log("控制点二： （" + (end) + "," +arcs[i].yy + ")")
            //	console.log("结束点： （" + (end) + "," +arcs[i].y2 + ")")

            if (arcs[i].choose == true) {
                hb.strokeStyle = "rgb(255,0,0)"; // 红色
                hb.fillStyle = "rgb(255,0 , 0)";
            } else {
                hb.fillStyle = "black";
                hb.strokeStyle = "black"; // 黑色
            }
            hb.stroke();
            hb.save();
            hb.closePath();
            hb.beginPath();
            // 画箭头
            hb.translate(end, arcs[i].y2);

            hb.rotate(1.6); // arrow is from right <- left
            hb.lineTo(-5, -5);
            hb.lineTo(5, 0);
            hb.lineTo(-5, 5);
            hb.lineTo(0, 0);
            hb.fill();
            hb.restore();
            var vancas = document.getElementById('cc').getContext("2d"); // 原点归为
            hb = vancas;
            arcs[i].xtxt = (begin + end) / 2; // 更改弧线中心，及tag所在位置
            hb.translate(arcs[i].xtxt - 10, arcs[i].ytxt - 7);
            hb.fillStyle = "#F9F9F9"; // E1E1DF
            hb.fillRect(0, 0, 20, 10);
            hb.fillStyle = "rgb(255,0 , 0)";

            hb.translate(10, 7);
            hb.textAlign = 'center';
            hb.font = "14.5px serif";
            hb.fillText(arcs[i].text, 0, 0);
            hb.restore();
            hb.translate((-1) * arcs[i].xtxt, (-1) * arcs[i].ytxt);
            hb.closePath();
        }
    }

}