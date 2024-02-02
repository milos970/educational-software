
let data = [];

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



function newtonMethod() 
{
    initialisePlot();
    var equation = document.getElementById("equation").value;
    var iterations = document.getElementById("iterations").value;
    var tolerance = document.getElementById("tolerance").value;

    var initial = document.getElementById("initial").value;

    
    
    equation = "10cos(x - 1) - x^2 + 2x - 1";
    tolerance = "0.000001";
    iterations = 100;
    initial = 2.4;

    var current = initial;
    var parsedEquation = math.parse(equation);
    var derivative = math.derivative(parsedEquation, 'x');

    let xvalues = [];
    let yvalues = [];

    
    
    for (let i = 0; i < iterations; i++) 
    {
        let fx = math.evaluate(parsedEquation.toString(), { x: current });
        let derfx = math.evaluate(derivative.toString(), { x: current });
        
        xvalues.push(current);
        yvalues.push(fx);

        next = current - (fx / derfx);
        

        if (Math.abs(next - current) <= tolerance) 
        {
            initialisePlot(xvalues, yvalues);
            return next;
        }

        current = next;
        

    }




    

}


function regulaFalsi() 
{
    var equation = document.getElementById("equation").value;
    var iterations = document.getElementById("iterations").value;
    var tolerance = document.getElementById("tolerance").value;

    var initial = document.getElementById("initial").value;

    var parsedEquation = math.parse(equation);

    var a = document.getElementById("a").value;
    var b = document.getElementById("b").value;
    var prev = a;

    for (let k = 0; k < iterations; ++k) 
    {
        let fak = math.evaluate(parsedEquation.toString(), { x: a });
        let fbk = math.evaluate(parsedEquation.toString(), { x: b });
        
        let xk = a - (b - a) / (fbk - fak) * fak;
        let fxk = math.evaluate(parsedEquation.toString(), { x: xk });
        
        if (fxk === 0) 
        {
            alert(xk);
            return xk;
        }

        if (fak * fxk < 0) 
        {
            b = xk;
        } else 
        {
            a = xk;
        }


        if (Math.abs(xk - prev) <= tolerance) {
            window.alert(xk);
            return xk;
        }

        prev = xk;
    }

}


function bisection() 
{
    var equation = document.getElementById("equation").value;
    var iterations = document.getElementById("iterations").value;
    var tolerance = document.getElementById("tolerance").value;

    var initial = document.getElementById("initial").value;

    

    var a = document.getElementById("a").value;
    var b = document.getElementById("b").value;

    equation = "10cos(x - 1) - x^2 + 2x - 1";
    tolerance = "0.001";
    iterations = 100;
    initial = 2.4;

    a=2.3;
    b=2.4;

    var parsedEquation = math.parse(equation);

    let xvalues = [];
    let yvalues = [];
    alert(45);

    for (let k = 0; k < iterations; ++k) 
    {
        let xk = (a + b) / 2;
        let fxk = math.evaluate(parsedEquation.toString(), { x: xk });

        let fak = math.evaluate(parsedEquation.toString(), { x: a });

        let fbk = math.evaluate(parsedEquation.toString(), { x: b });

        xvalues.push(xk);
        yvalues.push(fxk);
        
        if (fxk === 0) 
        {
            alert(xk);
            initialisePlot(xvalues, yvalues);
            return xk;
        }

        if (fak * fxk < 0) 
        {
            b = xk;
        } 

        if (fbk * fxk < 0) 
        {
            a = xk;
        } 


        if ( (b - a) / 2 <= tolerance) {
            window.alert(xk);
            initialisePlot(xvalues, yvalues);
            return xk;
        }

       

        
    }
}





function initialisePlot(xValues, yValues) 
{
    const data = 
    [
        { type: 'scatter', mode: 'markers', x: xValues, y: yValues,  name: 'Equation' },
        { type: 'scatter', mode: 'markers', x: xValues, y: yValues, name: 'Roots' },
    ];

    const layout = {
        title: 'Newton-Raphson method',
        xaxis: { title: 'x' },
        yaxis: { title: 'f(x)' },
    };

    Plotly.newPlot('plot', data, layout);
}

