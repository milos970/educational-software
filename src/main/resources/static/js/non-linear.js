
const equation = document.getElementById("equation");
let tolerance = document.getElementById("tolerance");
const initialValue = document.getElementById("initial");
const result = document.getElementById("result");

const toleranceError = document.getElementById("tolerance-error");
const equationError = document.getElementById("equation-error");
const initialError = document.getElementById("initial-error");
const progressError = document.getElementById("progress-error");

const dh = document.getElementById("dh");
const hh = document.getElementById("hh");

const table = document.getElementById("table");


let start = 0;
let finish = 0;
let step = 0;


function newtonMethod() 
{
    if ( !isValidNewton()) {
        return;
    }

    let modified = equation.value;
        modified = modified.slice(0, -1);
        modified = modified.replace("=","");
        modified = modified.trim();

    let iterations = 1000;


    let current = initial.value;
    let parsedEquation = math.parse(modified);
    let derivative = math.derivative(parsedEquation, 'x');

    let data = [];

    for (let i = 0; i < iterations; ++i) 
    {
        data[i] = [3];

        let fx = math.evaluate(parsedEquation.toString(), { x: current });
        let derfx = math.evaluate(derivative.toString(), { x: current });

        if (derfx === 0) 
        {
            progressError.value = "Delenie hodnotou 0 vo výpočte!"
            return;
        }


        let next = current - (fx / derfx);
        data[i][0] = i + 1;
        data[i][1] = next;
        let error = Math.abs(next - current);
        data[i][2] = error;



        if (error <= tolerance.value) 
        {
            result.value = next.toFixed(6);
            break;
        }

        current = next;
    }

    let headers = ["k", "x", "chyba"];
    initializeTable(headers, data);
}



function regulaFalsi() {
    if (!validateRegulaFalsi()) {
        return;
    }
    var equation = document.getElementById("equation").value;
    var tolerance = document.getElementById("tolerance").value;




    let a = parseFloat(document.getElementById("dh").value);
    let b = parseFloat(document.getElementById("hh").value);
    var prev = a;

    let data = [];


    iterations = 100;
    

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
            res.value = xk;
            break;
        }

        if (fak * fxk < 0) {
            b = xk;
        }

        if (fbk * fxk < 0) {
            a = xk;
        }

        if (Math.abs(xk - prev) <= tolerance) {
            res.value = xk;
            break;
        }

        prev = xk;
    }

    res.value = xk;

    let headers = ["k", "a", "b", "x", "chyba"];

    initializeTable(headers, data);



}


function bisection() {
    if (!validateBisection()) {
        return;
    }

    var equation = document.getElementById("equation").value;
    var tolerance = document.getElementById("tolerance").value;

    let modified = equation;
    modified = modified.slice(0, -1);
    modified = modified.replace("=","");
    modified = modified.trim();

    

    let iterations = 100;

    var parsedEquation = math.parse(modified);

    let xvalues = [];
    let yvalues = [];

    let data = []

    let a = parseFloat(document.getElementById("dh").value);
    let b = parseFloat(document.getElementById("hh").value);
   
    let xk = 0;
    for (let k = 0; k < iterations; ++k) 
    {

        
        xk = (a + b)/2;
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

        if ((b - a) / 2 <= tolerance.value) {
            
            res.value = xk;
            break;
        }

        if (fxk === 0) {
            
            res.value = xk;
            break;
        }

        if (fak * fxk < 0) {
            b = xk;
        }

        if (fbk * fxk < 0) {
            a = xk;
        }




       

    }

    res.value = xk;

    let headers = ["k", "a", "b", "x", "(b - a)/2"];

    initializeTable(headers, data);





}


function simpleIterationMethod() {

    if (!validate()) {
        return;
    }

    var equation = document.getElementById("equation").value;
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
        title: "parsedEquation.toString()",
        xaxis: { title: 'x' },
        yaxis: { title: 'f(x)' }
    };


    Plotly.newPlot('plot', [trace], layout);

    
}




function iterate(from, num) 
    {
        let sum = parseFloat(tolerance.value).toFixed(1 * num);
        
        for (let i = 0; i < 10; ++i) 
        {
            sum =  (1 * sum + 1 * from);
        }
        
        tolerance.value = parseFloat(sum).toFixed(num - 1);
        

    }


function increase() 
{

    


    


/* 


    if (value.split('.')[1].length === 6) {
        
        iterate(0.000001,6);
        return
    }

    
    if (value.split('.')[1].length === 5) {
        iterate(0.00001,5);
        return
    }


    if (value.split('.')[1].length === 4) {
        iterate(0.0001,4);

        return
    }
    
    if (value.split('.')[1].length === 3) {
        
        iterate(0.001,3);
    } */
}


function validateRegulaFalsi() 
{
    let valid = true;

    const a = document.getElementById("dh");
    const b = document.getElementById("hh");

    
    if (tolerance.value.length === 0) 
    {
        document.getElementById("tolerance-error").innerHTML = "Prázdne pole!";
        document.getElementById("tolerance-error").style.color = "red";
        valid = false;
    } else 
    {
        if (tolerance.value < 0.000001 || tolerance.value > 0.001) 
        {
            document.getElementById("tolerance-error").innerHTML = "Hodnota mimo intervalu!";
            document.getElementById("tolerance-error").style.color = "red";
            valid = false;
        } else {
            document.getElementById("tolerance-error").innerText = "";
        }
    }

   

    if (a.value === "") 
    {
        document.getElementById("dh-error").innerHTML = "Prázdne pole!";
        document.getElementById("dh-error").style.color = "red";
        valid = false;
    } else {
        if (a.value >= b.value) 
    {
        document.getElementById("dh-error").innerHTML = "Dolná hranica je väčšia než horná!";
        document.getElementById("dh-error").style.color = "red";
        valid = false;
    }else {
        document.getElementById("dh-error").innerText = "";
    }
    }


    if (b.value === "") 
    {
        document.getElementById("hh-error").innerHTML = "Prázdne pole!";
        document.getElementById("hh-error").style.color = "red";
        valid = false;
    } else {
        document.getElementById("hh-error").innerText = "";
    }



    


    if (equation.value.length === 0) 
    {
        document.getElementById("equation-error").innerHTML = "Prázdne pole!";
        document.getElementById("equation-error").style.color = "red";
        valid = false;
    } else 
    {
        if (equation.value.charAt(equation.value.length - 1) !== "0") 
        {
            document.getElementById("equation-error").innerText = "Nevalidný výraz!";
            return false;
        } else {
            document.getElementById("equation-error").innerText = "";
        }

        if (!equation.value.includes("=")) 
        {
          
            document.getElementById("equation-error").innerText = "Nevalidný výraz!";
            return false;
        } else {
            document.getElementById("equation-error").innerText = "";
        }


        let modified = equation.value;
        modified = modified.slice(0, -1);
        modified = modified.replace("=","");
        modified = modified.trim();


        try 
        {
            var expression = math.parse(modified);
            math.evaluate(expression.toString(), { x: 0 });
            document.getElementById("equation-error").innerText = "";
    
        } catch (error) 
        {
            
            document.getElementById("equation-error").innerText = "Nevalidný výraz!";
            return false;
        }
    }


    return valid;

}



function validateBisection() 
{
    let valid = true;

    const a = document.getElementById("dh");
    const b = document.getElementById("hh");

    
    if (tolerance.value.length === 0) 
    {
        document.getElementById("tolerance-error").innerHTML = "Prázdne pole!";
        document.getElementById("tolerance-error").style.color = "red";
        valid = false;
    } else 
    {
        if (tolerance.value < 0.000001 || tolerance.value > 0.001) 
        {
            document.getElementById("tolerance-error").innerHTML = "Hodnota mimo intervalu!";
            document.getElementById("tolerance-error").style.color = "red";
            valid = false;
        } else {
            document.getElementById("tolerance-error").innerText = "";
        }
    }

   

    if (a.value === "") 
    {
        document.getElementById("dh-error").innerHTML = "Prázdne pole!";
        document.getElementById("dh-error").style.color = "red";
        valid = false;
    } else {
        if (a.value >= b.value) 
    {
        document.getElementById("dh-error").innerHTML = "Dolná hranica je väčšia než horná!";
        document.getElementById("dh-error").style.color = "red";
        valid = false;
    }else {
        document.getElementById("dh-error").innerText = "";
    }
    }


    if (b.value === "") 
    {
        document.getElementById("hh-error").innerHTML = "Prázdne pole!";
        document.getElementById("hh-error").style.color = "red";
        valid = false;
    } else {
        document.getElementById("hh-error").innerText = "";
    }



    


    if (equation.value.length === 0) 
    {
        document.getElementById("equation-error").innerHTML = "Prázdne pole!";
        document.getElementById("equation-error").style.color = "red";
        valid = false;
    } else 
    {
        if (equation.value.charAt(equation.value.length - 1) !== "0") 
        {
            document.getElementById("equation-error").innerText = "Nevalidný výraz!";
            return false;
        } else {
            document.getElementById("equation-error").innerText = "";
        }

        if (!equation.value.includes("=")) 
        {
          
            document.getElementById("equation-error").innerText = "Nevalidný výraz!";
            return false;
        } else {
            document.getElementById("equation-error").innerText = "";
        }


        let modified = equation.value;
        modified = modified.slice(0, -1);
        modified = modified.replace("=","");
        modified = modified.trim();


        try 
        {
            var expression = math.parse(modified);
            math.evaluate(expression.toString(), { x: 0 });
            document.getElementById("equation-error").innerText = "";
    
        } catch (error) 
        {
            
            document.getElementById("equation-error").innerText = "Nevalidný výraz!";
            return false;
        }
    }


    return valid;

}



function isValidNewton() 
{
    let valid = true;
    
    if (tolerance.value.length === 0) 
    {
        document.getElementById("tolerance-error").innerHTML = "Prázdne pole!";
        document.getElementById("tolerance-error").style.color = "red";
        valid = false;
    } else 
    {
        if (tolerance.value < 0.000001 || tolerance.value > 0.001) 
        {
            document.getElementById("tolerance-error").innerHTML = "Hodnota mimo intervalu!";
            document.getElementById("tolerance-error").style.color = "red";
            valid = false;
        } else {
            document.getElementById("tolerance-error").innerText = "";
        }
    }


    
    


    if (initialValue.value.length === 0) 
    {
        document.getElementById("initial-error").innerHTML = "Prázdne pole!";
        document.getElementById("initial-error").style.color = "red";
        valid = false;
    } else {
        document.getElementById("initial-error").innerText = "";
    }

    if (equation.value.length === 0) 
    {
        document.getElementById("equation-error").innerHTML = "Prázdne pole!";
        document.getElementById("equation-error").style.color = "red";
        valid = false;
    } else 
    {
        if (equation.value.charAt(equation.value.length - 1) !== "0") 
        {
            document.getElementById("equation-error").innerText = "Nevalidný výraz!";
            return false;
        } else {
            document.getElementById("equation-error").innerText = "";
        }

        if (!equation.value.includes("=")) 
        {
          
            document.getElementById("equation-error").innerText = "Nevalidný výraz!";
            return false;
        } else {
            document.getElementById("equation-error").innerText = "";
        }


        let modified = equation.value;
        modified = modified.slice(0, -1);
        modified = modified.replace("=","");
        modified = modified.trim();


        try 
        {
            var expression = math.parse(modified);
            math.evaluate(expression.toString(), { x: 0 });
            document.getElementById("equation-error").innerText = "";
    
        } catch (error) 
        {
            document.getElementById("equation-error").innerText = "Nevalidný výraz!";
            return false;
        }
    }


    return valid;



}




function initializeTable(headers, data) 
{

    clearTable();

    let header = table.createTHead();
    let row = header.insertRow(0);
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

            if (Number.isInteger(1 * data[i][j])) 
            {
                c.innerText = (1 * data[i][j]);
            } else 
            {
                c.innerText = (1 * data[i][j]).toFixed(6);
            }

            
        }
    }




}


function clearTable() 
{
  $("#table tr").remove(); 
}












