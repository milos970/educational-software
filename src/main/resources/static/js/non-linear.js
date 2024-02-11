
let data = [];

let start = 0;
let finish = 0;
let step = 0;

function simpleIterationMethod() {

    if (!validate()) {
        return;
    }

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

function setParameters(a,b,step) 
{
    start = a;
    finish = b;
    step = step;
}


function display() {
    if (isEmpty()) {
        return;
    }

    var table = document.getElementById("table");
    table.style.display="none";

    var plot = document.getElementById("plot");
    plot.style.display = "block";

    var equation = document.getElementById("equation").value;

    
    const xValues = [];


    for (let i = start; i < finish; i += step) {
        xValues.push(i);
    }



    var parsedEquation = math.parse(equation);
    const yValues = xValues.map(x => math.evaluate(parsedEquation.toString(), { x: x }));


    const trace = {
        x: xValues,
        y: yValues,
        mode: 'lines',
        name: 'Function',
    };


    const layout = {
        title: parsedEquation.toString(),
        xaxis: { title: 'x' },
        yaxis: { title: 'f(x)' }
    };


    Plotly.newPlot('plot', [trace], layout);

    
}




function increase() {
    const el = document.getElementById("tolerance");

    const value = el.value;
    if (value.split('.')[1].length === 6) {
        el.step = 0.000001
        return
    }


    if (value.split('.')[1].length === 5) {
        el.step = 0.00001
        return
    }


    if (value.split('.')[1].length === 4) {
        el.step = 0.0001
        return
    }

    if (value.split('.')[1].length === 3) {
        el.step = 0.001
        return
    }
}

function isEmpty() 
{
    var value = document.getElementById("equation").value;

    if (value.length === 0) 
    {
        element.innerHTML = "Nevalidný výraz!";
        return true;
    }

    return false;

}



function validate() 
{
    var equation = document.getElementById("equation");

    var tolerance = document.getElementById("tolerance");

    var initialValue = document.getElementById("initial");


    var error = false;
    
    
    if (tolerance.value.length === 0 || tolerance.value < 0.000001 || tolerance.value > 0.001) 
    {
        document.getElementById("tolerance-error").innerHTML = "Tolerancia musí byť v rozsahu <1*10e-6;1*10e-3>!";
        error = true;
    } else {
        document.getElementById("tolerance-error").innerText = "";
    }


    if (initialValue.value.length === 0) 
    {
        document.getElementById("initial-error").innerHTML = "Nevalidná hodnota!";
        error = true;
    } else {
        document.getElementById("initial-error").innerText = "";
    }

    if (equation.value.length === 0) 
    {
        document.getElementById("equation-error").innerHTML = "Nevalidný výraz!";
        error = true;
    } else 
    {
        try 
        {
            var expression = math.parse(equation.value);
            math.evaluate(expression.toString(), { x: 0 });
            document.getElementById("equation-error").innerText = "";
            return error
    
        } catch (error) 
        {
    
            document.getElementById("equation-error").innerText = "Nevalidný výraz!";
            return false;
        }
    }

    return error;
    

}


function tabelation() 
{
    var equation = document.getElementById("equation").value;
    var parsedEquation = math.parse(equation).toString();

    var value = 0;
    data = [];

    for (let i = 0; i < 1000; i++) 
    {
        var first = math.evaluate(parsedEquation, { x: value });

        value += 0.1;

        var second = math.evaluate(parsedEquation, { x: value })


        if ( (first > 0 && second <= 0) || (second > 0 && first <= 0)) 
        {   
            data.push(value - 0.1);
            data.push(value);
        }
        value += 0.1;

    }


    for (let i = 0; i < data.length; i+=2) 
    {
        console.log(data[i] + " " + data[i + 1]);
    }

}



function newtonMethod() 
{

    if (validate()) {
        return;
    }
    var equation = document.getElementById("equation").value;
    var tolerance = document.getElementById("tolerance").value;
    var initial = document.getElementById("initial").value;

    iterations = 100;


    var current = initial;
    var parsedEquation = math.parse(equation);
    var derivative = math.derivative(parsedEquation, 'x');

    let xvalues = [];
    let yvalues = [];

    let data = [];



    for (let i = 0; i < iterations; i++) {
        data[i] = [3];

        let fx = math.evaluate(parsedEquation.toString(), { x: current });
        let derfx = math.evaluate(derivative.toString(), { x: current });

        xvalues.push(current);
        yvalues.push(fx);

        next = current - (fx / derfx);
        data[i][0] = i + 1;
        data[i][1] = next;
        data[i][2] = Math.abs(next - current);



        if (Math.abs(next - current) <= tolerance) {

            break;
        }

        current = next;


    }





    let headers = ["k", "x", "chyba"];

    initializeTable(headers, data);



}

function initializeTable(headers, data) {

    var plot = document.getElementById("plot");
    plot.style.display="none";

    var table = document.getElementById("table");
    table.style.display = "block";

    var header = table.createTHead();
    var row = header.insertRow(0);
    for (let i = 0; i < headers.length; ++i) {
        var cell = row.insertCell(i);
        cell.innerHTML = headers[i];
    }

    let from = 0;

    if (data.length > 8) {
        from = data.length - 8;
    }


    for (let i = from; i < data.length; ++i) {
        let row = table.insertRow(-1);
        for (let j = 0; j < data[0].length; ++j) {
            let c = row.insertCell(j);


            if (j == 0) {
                c.innerText = data[i][j];
                continue;
            }

            if (j === data[0].length - 2 && i === data.length - 1) {
                c.style.backgroundColor = "green";
            }
            c.innerText = data[i][j].toFixed(6);
        }
    }




}







function regulaFalsi() {
    if (!validate()) {
        return;
    }
    var equation = document.getElementById("equation").value;
    var tolerance = document.getElementById("tolerance").value;

    var initial = document.getElementById("initial").value;



    var a = document.getElementById("a").value;
    var b = document.getElementById("b").value;
    var prev = a;

    let data = [];

    equation = "10cos(x - 1) - x^2 + 2x - 1";
    tolerance = "0.001";
    iterations = Number.MAX_SAFE_INTEGER;
    a = 2.3;
    b = 2.4;
    prev = a;

    var parsedEquation = math.parse(equation);

    for (let k = 0; k < iterations; ++k) {

        data[k] = [5];
        let fak = math.evaluate(parsedEquation.toString(), { x: a });
        let fbk = math.evaluate(parsedEquation.toString(), { x: b });


        let xk = a - (b - a) / (fbk - fak) * fak;

        let fxk = math.evaluate(parsedEquation.toString(), { x: xk });

        data[k][0] = k;
        data[k][1] = a;
        data[k][2] = b;
        data[k][3] = xk;
        data[k][4] = Math.abs(xk - prev);
        if (fxk === 0) {
            //alert(xk);
            break;
        }

        if (fak * fxk < 0) {
            b = xk;
        }

        if (fbk * fxk < 0) {
            a = xk;
        }

        if (Math.abs(xk - prev) <= tolerance) {
            //window.alert(xk);
            break;
        }

        prev = xk;
    }

    let headers = ["k", "a", "b", "x", "chyba"];

    initializeTable(headers, data);



}


function bisection() {
    if (!validate()) {
        return;
    }
    var equation = document.getElementById("equation").value;
    var tolerance = document.getElementById("tolerance").value;




    var a = document.getElementById("a").value;
    var b = document.getElementById("b").value;

    equation = "10cos(x - 1) - x^2 + 2x - 1";
    tolerance = 0.001;
    iterations = Number.MAX_SAFE_INTEGER;
    initial = 2.4;

    a = 2.3;
    b = 2.4;

    var parsedEquation = math.parse(equation);

    let xvalues = [];
    let yvalues = [];

    let data = []

    for (let k = 0; k < iterations; ++k) {



        let xk = (a + b) / 2;
        let fxk = math.evaluate(parsedEquation.toString(), { x: xk });

        let fak = math.evaluate(parsedEquation.toString(), { x: a });

        let fbk = math.evaluate(parsedEquation.toString(), { x: b });

        xvalues.push(xk);
        yvalues.push(fxk);

        data[k] = [5];

        data[k][0] = k;
        data[k][1] = a;
        data[k][2] = b;
        data[k][3] = xk;
        data[k][4] = (b - a) / 2;

        if ((b - a) / 2 <= tolerance) {

            break;
        }

        if (fxk === 0) {

            break;
        }

        if (fak * fxk < 0) {
            b = xk;
        }

        if (fbk * fxk < 0) {
            a = xk;
        }






    }

    let headers = ["k", "a", "b", "x", "(b - a)/2"];

    initializeTable(headers, data);





}


