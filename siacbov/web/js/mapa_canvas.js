window.onload = Iniciar();

var canvas;//o elemento canvas sobre o qual desenharemos
var ctx;//o "contexto" da canvas que será utilizado (2D ou 3D)
var dx = 5;//a tava de variação (velocidade) horizontal do objeto
var dy = 5;//a tava de variação (velocidade) vertical do objeto
var p;
//var x = 100;//coordenada horizontal do objeto (com valor inicial)
//var y = 100;//coordenada vertical do objeto (com valor inicial)
var largura = 600; //largura da área retangular
var altura = 500; //altura da área retangular

function Iniciar() {
    canvas = document.getElementById("canvas");
    ctx = canvas.getContext("2d");       
    ctx.beginPath();
    //context.lineWidth = ; // define a largura da linha
    ctx.strokeStyle = "#f0f0f0"; //define a cor da linha
    ctx.fillStyle = "#bb404d";
    ctx.rect(0, 0, 600, 500);
    ctx.stroke();//forma não preenchida
    DesenharGrade();
    var t = 1;
    while(t <= 5){
        p = parseInt(document.getElementById("posicao"+t).value);
        Desenhar(p);
        t++;
    }
    //return setInterval(Atualizar, 10);
}

function Desenhar(pos) {
    var x = 0;
    var y = 0;
    if(p==0){
        x = 10;
        y = 10;
    } else {//cada metro da posição equivale a 20 pixel, usa-se esta fórmula para mapear
        if(p%30==0){
            x = (30 * 20) - 10; //0u largura - 10 = 590
            y = (parseInt(p/30)*20)-10;
        } else {
            x = ((p%30)*20)-10;
            y = (parseInt(p/30)*20)+10;
        }
    }
    ctx.beginPath();
    ctx.arc(x, y, 5, 0, Math.PI*2, true);
    ctx.fill();//forma preenchida   
}

function DesenharGrade() {
    var xLinha = 20;
    var yLinha = 20;
    var l = 600;
    var h = 500;
    while(xLinha < l){
        ctx.beginPath();
        ctx.moveTo(xLinha,0);
        ctx.lineTo(xLinha,h);
        ctx.stroke();
        xLinha = xLinha + 20;
    }
    while(yLinha < h){
        ctx.beginPath();
        ctx.moveTo(0,yLinha);
        ctx.lineTo(l,yLinha);
        ctx.stroke();
        yLinha = yLinha + 20;
    }
}
/*
window.setInterval(carrega, 1000);
function carrega()
{
$('#area_mapa').load("mapa.jsp");
}
*/
function LimparTela() {
    ctx.fillStyle = "white";
    ctx.strokeStyle = "black";
    ctx.beginPath();
    ctx.rect(0, 0, WIDTH, HEIGHT);
    ctx.closePath();
    ctx.fill();
    ctx.stroke();
}

function Atualizar() {
    LimparTela();
    DesenharGrade();
    Desenhar();
}

function KeyDown(evt){
    switch (evt.keyCode) {
        case 38:  /*seta para cima */
            if (y - dy > 0){
                y -= dy;
            }
            break;
        case 40:  /*set para baixo*/
            if (y + dy < HEIGHT){
                y += dy;
            }
            break;
        case 37:  /*set para esquerda*/
            if (x - dx > 0){
                x -= dx;
            }
            break;
        case 39:  /*seta para direita*/
            if (x + dx < WIDTH){
                x += dx;
            }
            break;
    }
}


//window.addEventListener('keydown', KeyDown, true);
