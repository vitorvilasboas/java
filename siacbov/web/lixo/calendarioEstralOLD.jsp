<h1>Calendário Estral</h1>
<div id="container" style="height: 400px; min-width: 310px; max-width: 800px; margin: 0 auto"></div>
<script type="text/javascript">
$(function () {
    $('#container').highcharts({
        chart: {
            type: 'heatmap',
            marginTop: 40,
            marginBottom: 40
        },

        title: {
            text: 'Vendas por funcionário por dia da semana'
        },

        xAxis: {
            valueFormatString: ""
            //categories: ['Alexander', 'Marie', 'Maximilian', 'Sophia', 'Lukas', 'Maria', 'Leon']
        },

        yAxis: {
            //categories: ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday'],
            title: null
        },

        colorAxis: {
            min: 0,
            minColor: '#FFFFFF',
            maxColor: '##022729'//Highcharts.getOptions().colors[0]
        },

        legend: false,

        tooltip: {
            formatter: function () {
                return '<b>' + this.series.xAxis.categories[this.point.x] + '</b> sold <br><b>' + 
                    this.point.value + '</b> items on <br><b>' + this.series.yAxis.categories[this.point.y] + '</b>';
            }
        },

        series: [{
            name: 'Sales per employee',
            borderWidth: 1,
            data: /*[[0, 0, 10], [0, 1, 19], [0, 2, 8], [0, 3, 24], [0, 4, 67], [1, 0, 92], 
                [1, 1, 58], [1, 2, 78], [1, 3, 117], [1, 4, 48], [2, 0, 35], [2, 1, 15], 
                [2, 2, 123], [2, 3, 64], [2, 4, 52], [3, 0, 72], [3, 1, 132], [3, 2, 114], 
                [3, 3, 19], [3, 4, 16], [4, 0, 38], [4, 1, 5], [4, 2, 8], [4, 3, 117], 
                [4, 4, 115], [5, 0, 88], [5, 1, 32], [5, 2, 12], [5, 3, 6], [5, 4, 120], 
                [6, 0, 13], [6, 1, 44], [6, 2, 88], [6, 3, 98], [6, 4, 96]]*/
                
                ( function () {
                    var data = [];
                    <%  for(int i=0; i<30;i++) { %>
                           <%  for(int j=0; i<30;i++) { %> 
                            data.push([<%= i %>,<%= i %>,2]);
                    <%  }   %>        
                    return data;
                }())
            ,   
            dataLabels: {
                enabled: true,
                color: 'black',
                style: {
                    textShadow: 'none',
                    HcTextStroke: null
                }
            }
        }]
    });
});
</script> 

<!--
legend: {
    align: 'right',
    layout: 'vertical',
    margin: 0,
    verticalAlign: 'top',
    y: 25,
    symbolHeight: 320
}
-->