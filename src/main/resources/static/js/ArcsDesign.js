//单元格宽度是40，该数是写到代码里的，待改。
var CellWidth = 40;
var CellCont = 70;
var ArcStartPx = CellWidth / 2 - 4;
var xcoordinate = new Array();
var ycoordinate = 547;
var canvasWidth = 5000;
var canvasHeight = 550;



var Arcs = function(start, end, x1, ycoordinate, x2, text) {
    // Arcs(start, end, tdBeginX[start], ycoordinate, tdBeginX[end], (tdBeginX[start]+ tdBeginX[end])/2, Math.abs(end-start), relation,widthArr[start]);
    this.delt = Math.abs(end - start);

    this.start = start;
    this.end = end;

    this.x1 = tdBeginX[this.start]; //tdBeginX[start]
    this.y1 = ycoordinate;

    this.x = tdBeginX[this.start]; //tdBeginX[start]
    this.y = ycoordinate - (20 - 0.1 * this.delt) * this.delt; //调整25这个值可以调整弧高度，越大，弧越高。  delt:Math.abs(end-start)

    this.xx = tdBeginX[end]; //tdBeginX[end]
    this.yy = ycoordinate - (20 - 0.1 * this.delt) * this.delt;

    this.x2 = tdBeginX[end]; //tdBeginX[end]
    this.y2 = ycoordinate;

    this.ytxt = this.y + 5 + 0.25 * (ycoordinate - this.y); //三次贝塞尔曲线可以算，有公式
    this.xtxt = (this.x1 + this.x2) / 2;

    this.text = text;
    this.choose = false; //判断该弧是否是用户点击的弧
    this.graph = false;
    this.width = widthArr[this.start];
};