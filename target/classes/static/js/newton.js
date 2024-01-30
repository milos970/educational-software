


function method() 
{
    var equation = document.getElementById("equation").value;
    var iterations = document.getElementById("iterations").value;
    var tolerance = document.getElementById("tolerance").value;

    var initial = document.getElementById("initial").value;

    var current = initial;
    var parsedEquation = math.parse(equation);
    var derivative = math.derivative(parsedEquation, 'x');
    
    for (let i = 0; i < iterations; i++) 
    {
         next = current - (parsedEquation.evaluate(current) / derivative.evaluate(current));

         if (abs(next - current) < tolerance) 
         {
            return next;
         }

         current = next;
    }
}

document.addEventListener("DOMContentLoaded", function () {

    // Example equation as a string
    const equationString = "x^3 - x^2 - 1";

    // Parse the equation string
    const parsedEquation = math.parse(equationString);

    // Define a function for evaluating the equation
    const evaluateEquation = (x) => parsedEquation.evaluate({ x });

    // Generate data points for the plot
    const xValues = math.range(-10, 10, 0.1)._data;
    const yValues = xValues.map((x) => evaluateEquation(x));

    // Create a trace for the plot
    const trace = {
        x: xValues,
        y: yValues,
        type: 'scatter',
        mode: 'lines',
        name: 'Equation Plot',
    };

    // Layout configuration for the plot
    const layout = {
        title: `Plot of the equation: ${equationString}`,
        xaxis: { title: 'x-axis' },
        yaxis: { title: 'y-axis' },
    };

    const point1 = { x: 2, y: 4 };
    const point2 = { x: 4, y: 1 };

    // Create a trace for the line connecting the two points
    const lineTrace = {
        x: [point1.x, point2.x],
        y: [point1.y, point2.y],
        type: 'scatter',
        mode: 'lines',
        name: 'Line Connecting Points',
        line: { color: 'red' } // Optional: Set line color
    };

    // Add the line trace to the existing plot data
    trace.push(lineTrace);

    // Create the plot
    Plotly.newPlot('plot', trace, layout);
    

});