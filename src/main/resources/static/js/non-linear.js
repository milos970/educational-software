
const equation = document.getElementById("equation");
const tolerance = document.getElementById("tolerance");
const initialValue = document.getElementById("initial");
const result = document.getElementById("result");

const toleranceError = document.getElementById("tolerance-error");
const equationError = document.getElementById("equation-error");
const initialError = document.getElementById("initial-error");
const progressError = document.getElementById("progress-error");
const dhError = document.getElementById("dh-error");
const hhError = document.getElementById("hh-error");

const dh = document.getElementById("dh");
const hh = document.getElementById("hh");
const stp = document.getElementById("step");

const table = document.getElementById("table");

let validFunction = false;
let validLowerBound = false;
let validUpperBound = false;

let start = 0;
let finish = 0;
let step = 0;

let prev = 0.001;

function checkValues()
{
    let num = Number.parseFloat(tolerance.value);

    if (prev === 0.001 && num < 0)
    {
        tolerance.value = 0.0001;
        prev = 0.0001;
        return;
    }

    if (prev === 0.001 && num > 0)
    {
        tolerance.value = 0.001;
        prev = 0.001;
        return;
    }


    if (prev === 0.0001 && num < 0)
    {
        tolerance.value = 0.00001;
        prev = 0.00001;
        return;
    }

    if (prev === 0.0001 && num > 0)
    {
        tolerance.value = 0.001;
        prev = 0.001;
        return;
    }


    if (prev === 0.00001 && num < 0)
    {
        tolerance.value = 0.000001;
        prev = 0.000001;
        return;
    }


    if (prev === 0.00001 && num > 0)
    {
        tolerance.value = 0.0001;
        prev = 0.0001;
        return;
    }


    if (prev === 0.000001 && num > 0)
    {
        tolerance.value = 0.00001;
        prev = 0.00001;
        return;
    }

    if (prev === 0.000001 && num < 0)
    {
        tolerance.value = 0.000001;
        prev = 0.000001;
        return;
    }



    if ( !(num === 0.001 || num === 0.0001 || num === 0.00001 || num === 0.000001 || num === 0.0000001 || num === 0.00000001))
    {
        tolerance.value = 0.001;
    }
}


function round()
{
    return tolerance.value.split(".")[1].length;
}


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

    data[0] = [3];
    data[0][0] = "k";
    data[0][1] = "k";
    data[0][2] = "chyba";
    for (let i = 1; i < iterations; ++i)
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
        data[i][1] = next.toFixed(round());
        let error = Math.abs(next - current);
        data[i][2] = error.toFixed(round());



        if (error <= tolerance.value) 
        {
            result.value = next.toFixed(round());
            break;
        }

        current = next;
    }

    saveToFile(data);
    initializeTable(data);
}



function regulaFalsi() {
    if (!validateRegulaFalsi()) {
        return;
    }


    let a = parseFloat(dh.value);
    let b = parseFloat(hh.value);
    let prev = a;

     let iterations = 1000;

    const parsedEquation = math.parse(equation.value);

    const data = [];

    data[0] = [5];
    data[0][0] = "k";
    data[0][1] = "a";
    data[0][2] = "b";
    data[0][3] = "x";
    data[0][4] = "chyba";


    for (let k = 1; k < iterations; ++k) {
alert(45);
        data[k] = [5];
        const fak = math.evaluate(parsedEquation.toString(), { x: a });
        const fbk = math.evaluate(parsedEquation.toString(), { x: b });


        const xk = a - (b - a) / (fbk - fak) * fak;

        const fxk = math.evaluate(parsedEquation.toString(), { x: xk });

        data[k][0] = k - 1;
        data[k][1] = a;
        data[k][2] = b;
        data[k][3] = xk;
        data[k][4] = Math.abs(xk - prev);
        if (fxk === 0) {
            result.value = xk;
            break;
        }

        if (fak * fxk < 0) {
            b = xk;
        }

        if (fbk * fxk < 0) {
            a = xk;
        }

        if (Math.abs(xk - prev) <= tolerance.value)
        {
            result.value = xk;
            break;
        }

        prev = xk;
    }

    result.value = xk;



    initializeTable(data);



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



    const iterations = 100;

    const parsedEquation = math.parse(modified);


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


        data[k] = [5];

        data[k][0] = k;
        data[k][1] = a;
        data[k][2] = b;
        data[k][3] = xk;
        data[k][4] = (b - a) / 2;

        if ((b - a) / 2 <= tolerance.value) {

            result.value = xk;
            break;
        }

        if (fxk === 0) {

            result.value = xk;
            break;
        }

        if (fak * fxk < 0) {
            b = xk;
        }

        if (fbk * fxk < 0) {
            a = xk;
        }




       

    }

    result.value = xk;

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

function display() 
{

    graph(Number.parseInt(dh.value),Number.parseInt(hh.value), Number.parseInt(stp));
}


function graph(dh,hh,step) 
{
    var plot = document.getElementById("plot");
    plot.style.display = "block";

    var equation = document.getElementById("equation").value;

    
    const xValues = [];


    for (let i = dh; i < hh; i += 1) 
    {
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
        if (tolerance.value < 0.00000001 || tolerance.value > 0.001)
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




function initializeTable(data)
{

    clearTable();

    let header = table.createTHead();
    let row = header.insertRow(0);
    for (let i = 0; i < data[0].length; ++i) {
        let cell = row.insertCell(i);
        cell.innerHTML = data[0][i];
    }

    let from = 1;

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
                c.innerText = data[i][j];
            } else 
            {
                c.innerText = data[i][j];
            }

            
        }
    }




}


function clearTable() 
{
  $("#table tr").remove(); 
}




////////////////////////////////////////////////////////SAVE TO FILE//////////////////////////////////////////////////////////////////////////////////////////////

function saveToFile(array)
{
    function download2DArrayAsCSV(array) {
        // Convert the 2D array to a CSV string
        const csvContent = array.map(row => row.join(';')).join('\n');

        // Create a Blob object representing the data as a file
        const blob = new Blob([csvContent], { type: 'text/csv' });

        // Create a link element
        const link = document.createElement('a');

        // Set the href attribute of the link to the Blob object
        link.href = window.URL.createObjectURL(blob);

        // Set the download attribute to specify the filename
        link.download = "Výsledok.csv";

        // Append the link to the document body
        document.body.appendChild(link);

        // Trigger a click event on the link to prompt download
        link.click();

        // Clean up by removing the link from the DOM
        document.body.removeChild(link);
    }

    download2DArrayAsCSV(array);
}



////////////////////////////////////////////////////////SAVE TO FILE//////////////////////////////////////////////////////////////////////////////////////////////



////////////////////////////////////////////////////////VALIDATE BOUNDS//////////////////////////////////////////////////////////////////////////////////////////////

function validateLowerBound()
{
    validLowerBound = true;

    if (dh.value === "")
    {
        dhError.innerHTML = "Prázdne poľe!";
        validLowerBound = false;
        return;
    } else {
        dhError.innerHTML = "";
    }

    let num = Number.parseInt(dh.value);
    let numD = Number.parseInt(hh.value);

    if (-1000 > num || num > 999)
    {
        dhError.innerHTML = "Hodnota mimo intervalu!";
        validLowerBound = false;
        return;
    }else {
        dhError.innerHTML = "";
    }

    if (hh.value !== "" && num >= numH)
    {
        dhError.innerHTML = "Dolná hranica je väčšia/rovná než dolná!";
        validLowerBound = false;
    }else {
        validateUpperBound();
        dhError.innerHTML = "";
    }

}

function validateUpperBound()
{
    validUpperBound = true;
    if (hh.value === "")
    {
        hhError.innerHTML = "Prázdne poľe!";
        validUpperBound = false;
        return;
    } else {
        hhError.innerHTML = "";
    }

    let num = Number.parseInt(hh.value);
    let numD = Number.parseInt(dh.value);

    if (-999 > num || num > 1000)
    {
        validUpperBound = false;
        hhError.innerHTML = "Hodnota mimo intervalu!";
        return;
    }else {
        hhError.innerHTML = "";
    }

    if (numD >= num)
    {
        validUpperBound = false;
        hhError.innerHTML = "Horná hranica je menšia/rovná než dolná!";
    }else {
        hhError.innerHTML = "";
    }


}
///////////////////////////////////////////////////////VALIDATE BOUNDS//////////////////////////////////////////////////////////////////////////////////////////////









