<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>
    Labels Example - HTML5 jQuery Linear Gauge Plugin by jqChart
</title>
    <link rel="stylesheet" type="text/css" href="../css/jquery.jqGauges.css" />
    <script src="../js/jqchart/jquery-1.11.1.min.js" type="text/javascript"></script>
    <script src="../js/jqchart/jquery.jqGauges.min.js" type="text/javascript"></script>
    <!--[if IE]><script lang="javascript" type="text/javascript" src="../../js/excanvas.js"></script><![endif]-->
    
    <script lang="javascript" type="text/javascript">
        $(document).ready(function () {

            var rangeGradient = {
                type: 'linearGradient',
                x0: 0.5,
                y0: 0,
                x1: 0.5,
                y1: 1,
                colorStops: [{ offset: 0, color: '#9DE100' },
                             { offset: 1, color: '#669200'}]
            };

            var needleGradient = {
                type: 'linearGradient',
                x0: 0,
                y0: 0.5,
                x1: 1,
                y1: 0.5,
                colorStops: [{ offset: 0, color: '#4F6169' },
                             { offset: 1, color: '#252E32'}]
            };

            $('#jqLinearGauge').jqLinearGauge({
                orientation: 'horizontal',
                background: '#F7F7F7',
                border: {
                    padding: 10,
                    lineWidth: 4,
                    strokeStyle: '#76786A'
                },
                tooltips: {
                    disabled: false,
                    highlighting: true
                },
                animation: {
                    duration: 1
                },
                scales: [
                         {
                             minimum: 0,
                             maximum: 100,
                             labels: {
                                 offset: 0.16,
                                 fillStyle: '#B2183E',
                                 stringFormat: '%dK',
                                 font: '12px sans-serif'
                             },
                             majorTickMarks: {
                                 length: 10,
                                 offset: 0.28,
                                 lineWidth: 2
                             },
                             minorTickMarks: {
                                 length: 6,
                                 visible: true,
                                 interval: 2,
                                 offset: 0.34,
                                 lineWidth: 2
                             },
                             ranges: [
                                         {
                                             startValue: 0,
                                             endValue: 100,
                                             innerOffset: 0.46,
                                             outerStartOffset: 0.70,
                                             outerEndOffset: 0.90,
                                             fillStyle: rangeGradient
                                         }
                                     ],
                             needles: [

                                         {
                                             value: 70,
                                             fillStyle: needleGradient,
                                             innerOffset: 0.50,
                                             outerOffset: 0.95
                                         }
                                      ]
                         }
                        ]
            });
        });
    </script>

</head>
<body>
    <div>
        <div id="jqLinearGauge" style="width: 400px; height: 100px;">
        </div>
    </div>
</body>
</html>