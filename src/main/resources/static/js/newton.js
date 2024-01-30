


function simpleIterationMethod() 
{

    var equation = document.getElementById("equation").value;
    var iterations = document.getElementById("iterations").value;
    var tolerance = document.getElementById("tolerance").value;

    var initial = document.getElementById("initial").value;

    var current = initial;
    var parsedEquation = math.parse(equation);
    var derivative = math.derivative(parsedEquation, 'x');

    alert(current);
    for (let i = 0; i < 8; i++) {
        var next = math.evaluate(parsedEquation.toString(), { x: current });
        alert(next);
        if (Math.abs(next - current) <= tolerance) {
            return next;
        }

        current = next;

    }
}



function newtonMethod() {

    initialisePlot();

    for (let i = 0; i < iterations; i++) {
        var fx = math.evaluate(parsedEquation.toString(), { x: current });
        var derfx = math.evaluate(derivative.toString(), { x: current });

        next = current - (fx / derfx);

        setTrace(current, fx, current, 0);


        if (Math.abs(next - current) < tolerance) {
            return next;
        }

        current = next;
        setTrace(current, fx, next, 0);

    }

}












let data = [];

function setTrace(x1, y1, x2, y2) {
    var newLine = {
        x: [x1, x2],
        y: [y1, y2],
        type: 'scatter',
    };

    data.push(newLine);
    Plotly.newPlot('plot', data);
}

function initialisePlot() {


    Plotly.newPlot('plot', data);
}

