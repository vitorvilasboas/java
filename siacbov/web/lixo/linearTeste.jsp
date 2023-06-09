<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>
    Linear Gradients Example - HTML5 jQuery Chart Plugin by jqChart
</title>
    <link rel="stylesheet" type="text/css" href="css/jqchart/jquery.jqChart.css" />
    <link rel="stylesheet" type="text/css" href="css/jqchart/jquery.jqRangeSlider.css" />
    <link rel="stylesheet" type="text/css" href="css/jqchart/themes/smoothness/jquery-ui-1.10.4.css" />
    <script src="js/jqchart/jquery-1.11.1.min.js" type="text/javascript"></script>
    <script src="js/jqchart/.mousewheel.js" type="text/javascript"></script>
    <script src="js/jqchart/jquery.jqChart.min.js" type="text/javascript"></script>
    <script src="js/jqchart/jquery.jqRangeSlider.min.js" type="text/javascript"></script>
    <!--[if IE]><script lang="javascript" type="text/javascript" src="../../js/excanvas.js"></script><![endif]-->
    
    <script lang="javascript" type="text/javascript">

        $(document).ready(function () {

            var fillStyle1 = {
                type: 'linearGradient',
                x0: 0,
                y0: 0,
                x1: 1,
                y1: 0,
                colorStops: [{ offset: 0, color: '#65c2e8' },
                             { offset: 0.49, color: '#55b3e1' },
                             { offset: 0.5, color: '#3ba6dc' },
                             { offset: 1, color: '#2794d4' }]
            };

            var fillStyle2 = {
                type: 'linearGradient',
                x0: 0,
                y0: 0,
                x1: 1,
                y1: 0,
                colorStops: [{ offset: 0, color: '#eea151' },
                             { offset: 0.49, color: '#ea8f44' },
                             { offset: 0.5, color: '#e67e28' },
                             { offset: 1, color: '#e06818' }]
            };

            $('#jqChart').jqChart({
                title: { text: 'Linear Gradients' },
                legend: {
                    location: 'bottom',
                    visible: false
                },
                animation: { duration: 1 },
                series: [
                    {
                        type: 'column',
                        data: [['A', 33], ['B', 57], ['C', 33], ['D', 12], ['E', 35], ['F', 7]],
                        fillStyle: fillStyle1
                    },
                    {
                        type: 'column',
                        data: [['A', 67], ['B', 25], ['C', 11], ['D', 8], ['E', 44], ['F', 14]],
                        fillStyle: fillStyle2
                    }
                ]
            });
        });
    </script>

</head>
<body>
    <div>
        <div id="jqChart" style="width: 500px; height: 300px;">
        </div>
    </div>
</body>
</html>
