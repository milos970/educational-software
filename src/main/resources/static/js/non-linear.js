
const equation = document.getElementById("equation");
const tolerance = document.getElementById("tolerance");
const initialValue = document.getElementById("initial");

const toleranceError = document.getElementById("tolerance-error");
const equationError = document.getElementById("equation-error");
const initialError = document.getElementById("initial-error");
const progressError = document.getElementById("progress-error");
const dhError = document.getElementById("dh-error");
const hhError = document.getElementById("hh-error");
const methods = document.getElementById("methods");


const calculateButton =  document.getElementById("calculate-button");
const displayButton =  document.getElementById("display-button");
const saveButton =  document.getElementById("save-button");

calculateButton.disabled = true;
displayButton.disabled = true;
saveButton.disabled = true;

const title = document.getElementById("title");

const dh = document.getElementById("dh");
const hh = document.getElementById("hh");
const stp = document.getElementById("step");

const table = document.getElementById("table");

let isValidEquation = false;
let isValidApproximation = false;
let isValidUpperBound = false;
let isValidLowerBound = false;

let start = 0;
let finish = 0;
let step = 0;

let prev = 0.001;

let array = [];






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
    saveButton.disabled = true;
    modified = equation.value;
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
    data[0][1] = "x";
    data[0][2] = "chyba";

    let fx = math.evaluate(parsedEquation.toString(), { x: current });
    let derfx = math.evaluate(derivative.toString(), { x: current });
    let next = current - (fx / derfx);

    data[1] = [3];
    data[1][0] = "0";
    data[1][1] = current;
    data[1][2] = Math.abs(next - current).toFixed(round());
    current = next;
    for (let i = 2; i < iterations; ++i)
    {

        data[i] = [3];
        fx = math.evaluate(parsedEquation.toString(), { x: current });
         derfx = math.evaluate(derivative.toString(), { x: current });

        if (derfx === 0)
        {
            progressError.value = "Delenie hodnotou 0 vo výpočte!"
            return;
        }


        next = current - (fx / derfx);
        data[i][0] = i - 1;
        data[i][1] = next.toFixed(round());
        let error = Math.abs(next - current);
        data[i][2] = error.toFixed(round());



        if (error <= tolerance.value)
        {

            break;
        }

        current = next;
    }

    array = data;
    initializeTable(data);
    saveButton.disabled = false;
}



function regulaFalsiMethod()
{
    if ( !(validateEquation() && validateLowerBound() && validateUpperBound()) )
    {
        return;
    }

    const modifiedEquation = modifyEquation();

    let a = parseFloat(dh.value);
    let b = parseFloat(hh.value);
    let prev = a;

    const iterations = 1000;

    const parsedEquation = math.parse(modifiedEquation);

    const data = [];

    data[0] = [5];
    data[0][0] = "k";
    data[0][1] = "a";
    data[0][2] = "b";
    data[0][3] = "x";
    data[0][4] = "chyba";


    for (let k = 1; k < iterations; ++k) {

        data[k] = [5];
        const fak = math.evaluate(parsedEquation.toString(), { x: a });
        const fbk = math.evaluate(parsedEquation.toString(), { x: b });


        const xk = a - (b - a) / (fbk - fak) * fak;

        const fxk = math.evaluate(parsedEquation.toString(), { x: xk });

        data[k][0] = k - 1;
        data[k][1] = a.toFixed(round());
        data[k][2] = b.toFixed(round());
        data[k][3] = xk.toFixed(round());
        data[k][4] = (Math.abs(xk - prev)).toFixed(round());
        if (fxk === 0) {

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

            break;
        }

        prev = xk;
    }



    initializeTable(data);



}


function bisectionMethod()
{


    const modifiedEquation = modifyEquation();
    const iterations = 1000;
    const parsedEquation = math.parse(modifiedEquation);


    const data = []
    data[0] = [5];

    data[0][0] = "k";
    data[0][1] = "a";
    data[0][2] = "b";
    data[0][3] = "x";
    data[0][4] = "chyba";


    let a = parseFloat(dh.value);
    let b = parseFloat(hh.value);

    let xk = 0;
    for (let k = 1; k < iterations; ++k)
    {


        xk = (a + b)/2;
        const fxk = math.evaluate(parsedEquation.toString(), { x: xk });

        const fak = math.evaluate(parsedEquation.toString(), { x: a });

        const fbk = math.evaluate(parsedEquation.toString(), { x: b });


        data[k] = [5];

        data[k][0] = k - 1;
        data[k][1] = a.toFixed(round());
        data[k][2] = b.toFixed(round());
        data[k][3] = xk.toFixed(round());
        data[k][4] = ((b - a) / 2).toFixed(round());

        if ((b - a) / 2 <= tolerance.value) {


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



    initializeTable(data);





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
    graph(-100,100, 1);
    //graph(Number.parseInt(dh.value),Number.parseInt(hh.value), Number.parseInt(stp));
}



function graph(dh,hh,step)
{
    var plot = document.getElementById("plot");
    plot.style.display = "block";

    var equation = modifyEquation();


    const xValues = [];


    for (let i = dh; i < hh; i += 0.1)
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



    Plotly.newPlot('plot', [trace], layout).then(function(gd) {
        Plotly.toImage(gd, {format: 'png', height: 1011, width: 600})
            .then(function(url) {
                var cardHeading = document.querySelector('.card-heading');
                cardHeading.style.background = 'url(' + url + ') top left/cover no-repeat';

                var downloadLink = document.createElement('a');
                downloadLink.href = url;
                downloadLink.download = 'multiplot.png'; // Specify the file name
                downloadLink.style.display = 'none';
                document.body.appendChild(downloadLink);

                // Simulate a click on the anchor element to trigger the download
                downloadLink.click();

                // Remove the anchor element from the document
                document.body.removeChild(downloadLink);
            });
    });


    plot.style.display="none";


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

function saveToFile()
{


    const csvContent = array.map(row => row.join(';')).join('\n');

    const blob = new Blob([csvContent], { type: 'text/csv' });

    const link = document.createElement('a');

    link.href = window.URL.createObjectURL(blob);

    let fileName = "";

    switch(methods.value)
    {
        case "1":
            fileName = "Newtnová metoda";
            break;

        case "2":
            fileName = "Regula falsi";
            break;

        case "3":
            fileName = "Bisekcia";
            break;

    }

    link.download = fileName + ".csv";

    document.body.appendChild(link);
    link.click();

    document.body.removeChild(link);
}



////////////////////////////////////////////////////////SAVE TO FILE//////////////////////////////////////////////////////////////////////////////////////////////



////////////////////////////////////////////////////////VALIDATE//////////////////////////////////////////////////////////////////////////////////////////////


function isFirstApproximationValid()
{
    isValidApproximation = true;
    if (initialValue.value === "")
    {
        initialError.innerHTML = "Prázdne poľe!";
        isValidApproximation = false;
        setCalculateButton();
        return false;

    }else
    {
        initialError.innerHTML = "";
    }

    setCalculateButton();
    return true;
}
function modifyEquation()
{
    let eq = equation.value;
    eq = eq.slice(0, -1);
    eq = eq.replace("=","");
    eq = eq.trim();

    return eq;
}
function validateEquation()
{

    isValidEquation = true;

    if (equation.value == null || equation.value === "")
    {
        equationError.innerHTML = "Prázdne poľe!";
        isValidEquation = false;
        setCalculateButton();
        setGraphButton();
        return false;
    } else
    {
        equationError.innerHTML = "";
    }

    equation.value = equation.value.toLowerCase();

    if (equation.value.indexOf("x") === -1)
    {
        equationError.innerHTML  = "Nevalidný výraz!";
        isValidEquation = false;
        setCalculateButton();
        setGraphButton();
        return false;
    } else
    {
        equationError.innerHTML = "";
    }



    if (equation.value.charAt(equation.value.length - 1) !== "0")
    {
        equationError.innerHTML = "Nevalidný výraz!";
        isValidEquation = false;
        setCalculateButton();
        setGraphButton();
        return false;
    } else {
        equationError.innerHTML = "";
    }

    if (!equation.value.includes("="))
    {

        equationError.innerHTML = "Nevalidný výraz!";
        isValidEquation = false;
        setCalculateButton();
        setGraphButton();
        return false;
    } else
    {
        equationError.innerHTML = "";
    }



    try
    {
        let expression = math.parse(modifyEquation());
        math.evaluate(expression.toString(), { x: 0 });
        equationError.innerHTML = "";

    } catch (error)
    {
        equationError.innerHTML  = "Nevalidný výraz!";
        isValidEquation = false;
        setCalculateButton();
        setGraphButton();
        return false;
    }

    setCalculateButton();
    setGraphButton();
    return true;
}
function validateLowerBound()
{

    isValidLowerBound = false;

    if (dh.value === "")
    {
        dhError.innerHTML = "Prázdne poľe!";
        setCalculateButton();
        setGraphButton();
        return false;
    } else {
        dhError.innerHTML = "";
    }

    let num = Number.parseFloat(hh.value);
    let numD = Number.parseFloat(dh.value);


    if (-1000 > num || num > 999)
    {
        hhError.innerHTML = "Hodnota mimo intervalu!";
        setCalculateButton();
        setGraphButton();
        return false;
    }else {
        hhError.innerHTML = "";
    }

    if (numD >= num)
    {
        hhError.innerHTML = "Horná hranica je menšia/rovná než dolná!";

        setCalculateButton();
        setGraphButton();
        return false;
    }else {
        hhError.innerHTML = "";
    }



    isValidLowerBound = true;
    setCalculateButton();
    setGraphButton();
    return true;

}

function validateUpperBound()
{
    isValidUpperBound = false;

    if (hh.value === "")
    {
        hhError.innerHTML = "Prázdne poľe!";
        setCalculateButton();
        setGraphButton();
        return false;
    } else {
        hhError.innerHTML = "";
    }

    let num = Number.parseFloat(hh.value);
    let numD = Number.parseFloat(dh.value);

    if (-999 > num || num > 1000)
    {
        hhError.innerHTML = "Hodnota mimo intervalu!";
        setCalculateButton();
        setGraphButton();
        return false;
    }else {
        hhError.innerHTML = "";
    }

    if (numD >= num)
    {
        hhError.innerHTML = "Horná hranica je menšia/rovná než dolná!";
        setCalculateButton();
        setGraphButton();
        return false;
    }else {
        hhError.innerHTML = "";
    }

    isValidUpperBound = true;
    isValidLowerBound = true;
    setCalculateButton();
    setGraphButton();
    return true;
}
///////////////////////////////////////////////////////VALIDATE//////////////////////////////////////////////////////////////////////////////////////////////



function setGraphButton()
{


    if (methods.value === "1")
    {
        displayButton.disabled = !(isValidEquation);
    }

    if (methods.value === "2" || methods.value === "3")
    {
        displayButton.disabled = !(isValidEquation && isValidUpperBound && isValidLowerBound);
    }
}

function setCalculateButton()
{
    switch(methods.value)
    {
        case "1":
            calculateButton.disabled = !(isValidApproximation && isValidEquation);
            break;
        case "2":
            calculateButton.disabled = !(isValidUpperBound && isValidLowerBound && isValidEquation);
            break;
        case "3":
            calculateButton.disabled = !(isValidUpperBound && isValidLowerBound && isValidEquation);
            break;
    }
}

function calculate()
{
    switch(methods.value)
    {
        case "1":
            newtonMethod();
            break;
        case "2":
            regulaFalsiMethod();
            break;
        case "3":
            bisectionMethod();
            break;
    }
}


function setMethod()
{
    if (methods.value === "1")
    {
        document.getElementById('initial-div').style.display="block";
        document.getElementById('dh-div').style.display="none";
        document.getElementById('hh-div').style.display="none";

        title.innerHTML = methods.options[methods.selectedIndex].text;


    }

    if (methods.value === "2")
    {
        document.getElementById('initial-div').style.display="none";
        document.getElementById('dh-div').style.display="block";
        document.getElementById('hh-div').style.display="block";

        title.innerHTML = methods.options[methods.selectedIndex].text;
    }

    if (methods.value === "3")
    {
        document.getElementById('initial-div').style.display="none";
        document.getElementById('dh-div').style.display="block";
        document.getElementById('hh-div').style.display="block";

        title.innerHTML = methods.options[methods.selectedIndex].text;
    }

}





