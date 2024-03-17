

let fun = "";

function validateEquation()
{
    const equationInput = document.getElementById("equation-input");
    const equationError = document.getElementById("equation-hint-error");

    let modifiedEquation = equationInput.value;


    if (modifiedEquation === null || modifiedEquation === "")
    {
        equationError.innerHTML = "Prázdne poľe!";
        return false;
    } else
    {
        equationError.innerHTML = "";
    }

    modifiedEquation = modifiedEquation.trim();
    modifiedEquation = modifiedEquation.toLowerCase();


    if (modifiedEquation.indexOf("x") === -1)
    {
        equationError.innerHTML  = "Nevalidný výraz!";
        return false;
    } else
    {
        equationError.innerHTML = "";
    }



    if (modifiedEquation.charAt(modifiedEquation.length - 1) !== "0")
    {
        equationError.innerHTML = "Nevalidný výraz!";
        return false;
    } else {
        equationError.innerHTML = "";
    }

    if (!modifiedEquation.includes("="))
    {

        equationError.innerHTML = "Nevalidný výraz!";
        return false;
    } else
    {
        equationError.innerHTML = "";
    }


    modifiedEquation = modifiedEquation.slice(0, -1);
    modifiedEquation = modifiedEquation.replace("=","");
    modifiedEquation = modifiedEquation.trim();


    try
    {
        let expression = math.parse(modifiedEquation);
        math.evaluate(expression.toString(), { x: 0 });
        equationError.innerHTML = "";

    } catch (error)
    {
        equationError.innerHTML  = "Nevalidný výraz!";

        return false;
    }

    fun = modifiedEquation;

    return true;
}




let selectedCategory = 0;
let selectedMethod = 0;
function selectNumericCategory(category)
{
    selectedCategory = category;
    setUpElements(category, 1);
}

function setUpElements(category, method)
{

    switch(category)
    {
        case 1:
            selectNonLinearMethod(method);
            break;

        case 2:
            selectIntegrationMethod(method);
            break;

        case 3:

            selectApproximationAndInterpolationMethod(method);
            break;

    }
}

function hideAllNonLinearElements()
{
    document.getElementById("lower-bound-div").style.display="none";
    document.getElementById("upper-bound-div").style.display="none";
    document.getElementById("initial-value-div").style.display="none";
    document.getElementById("equation-div").style.display="none";
    document.getElementById("tolerance-div").style.display="none";
    document.getElementById("non-linear-dropdown-div").style.display="none";
}

function hideAllIntegrationElements()
{
    document.getElementById("lower-bound-div").style.display="none";
    document.getElementById("upper-bound-div").style.display="none";
    document.getElementById("subintervals-div").style.display="none";
    document.getElementById("function-div").style.display="none";

    document.getElementById("integration-dropdown-div").style.display="none";
}

function hideAllApproximationElements()
{
    document.getElementById("nodes-string-div").style.display="none";
    document.getElementById("nodes-csv-div").style.display="none";
    document.getElementById("functions-dropdown-div").style.display="none";

    document.getElementById("approximation-dropdown-div").style.display="none";
}





function selectNonLinearMethod(value)
{
    hideAllIntegrationElements();
    hideAllApproximationElements();

    const dropDownButton = document.getElementById("dropdownMenuButton1");



    const nonLinearDropdownDiv = document.getElementById("non-linear-dropdown-div");
    nonLinearDropdownDiv.style.display="block";

    const lowerBoundDivElement = document.getElementById("lower-bound-div");
    const upperBoundDivElement = document.getElementById("upper-bound-div");

    const initialValueDivElement = document.getElementById("initial-value-div");
    const equationDiv = document.getElementById("equation-div");
    equationDiv.style.display="block";

    const toleranceDiv = document.getElementById("tolerance-div");




    switch (value)
    {
        case 1:
            dropDownButton.innerHTML = "Newtnová metóda";

            lowerBoundDivElement.style.display = "none";
            upperBoundDivElement.style.display = "none";

            initialValueDivElement.style.display = "block";
            toleranceDiv.style.display="block";
            selectedMethod = 1;
            break;
        case 2:
            dropDownButton.innerHTML = "Regula falsi";

            initialValueDivElement.style.display = "none";

            lowerBoundDivElement.style.display = "block";
            upperBoundDivElement.style.display = "block";
            selectedMethod = 2;
            break;
        case 3:
            dropDownButton.innerHTML = "Bisekcia";


            initialValueDivElement.style.display = "none";

            lowerBoundDivElement.style.display = "block";
            upperBoundDivElement.style.display = "block";

            selectedMethod = 3;
            break;
    }



}





function selectApproximationAndInterpolationMethod(value)
{
    hideAllNonLinearElements();
    hideAllIntegrationElements();

    const approximationDropdownDiv = document.getElementById("approximation-dropdown-div");
    approximationDropdownDiv.style.display="block";

    const nodesStringDiv = document.getElementById("nodes-string-div");
    const nodesCsvDiv = document.getElementById("nodes-csv-div");

    const functionsDropdownDiv = document.getElementById("functions-dropdown-div");

    switch (value)
    {
        case 1:
            document.getElementById("dropdownMenuButton3").innerHTML = "Lagrangeov polynóm";
            nodesStringDiv.style.display = "block";
            nodesCsvDiv.style.display = "block";

            functionsDropdownDiv.style.display="none";

            selectedMethod = 1;
            break;
        case 2:
            document.getElementById("dropdownMenuButton3").innerHTML = "Newtnov polynóm";
            nodesStringDiv.style.display = "block";
            nodesCsvDiv.style.display = "block";

            functionsDropdownDiv.style.display="none";

            selectedMethod = 2;
            break;
        case 3:
            document.getElementById("dropdownMenuButton3").innerHTML = "Metóda najmenších štvorcov";
            nodesStringDiv.style.display = "block";
            nodesCsvDiv.style.display = "block";

            functionsDropdownDiv.style.display="block";


            selectedMethod = 3;
            break;
    }

}





function selectIntegrationMethod(value)
{
    hideAllNonLinearElements();
    hideAllApproximationElements();

    const integrationDropdownDiv = document.getElementById("integration-dropdown-div");
    integrationDropdownDiv.style.display="block";

    const lowerBoundDivElement = document.getElementById("lower-bound-div");
    const upperBoundDivElement = document.getElementById("upper-bound-div");

    const functionDiv = document.getElementById("function-div");
    functionDiv.style.display="block";

    lowerBoundDivElement.style.display = "block";
    upperBoundDivElement.style.display = "block";

    const subintervalsDiv = document.getElementById("subintervals-div");




    switch (value)
    {
        case 1:
            document.getElementById("dropdownMenuButton2").innerHTML = "Lichobežníková metóda";

            subintervalsDiv.style.display = "block";
            selectedMethod = 1;
            break;
        case 2:
            document.getElementById("dropdownMenuButton2").innerHTML.innerHTML = "Simpsnová metóda";


            subintervalsDiv.style.display = "block";
            selectedMethod = 2;
            break;
    }

}

















function calculate()
{
    switch(selectedCategory) {
        case 1:
            calculateNonLinearMethod();
            break;
        case 2:
            calculateIntegrationMethod();
            break;
        case 3:
            calculateApproximationMethod();
            break;
    }
}

function calculateNonLinearMethod()
{

    switch(selectedMethod) {
        case 1:
            newtonMethod();
            break;
        case 2:
            regulaFalsiMethod();
            break;
        case 3:
            bisectionMethod();
            break;
    }
}


let toleranceValue = 0.001;
let round = 3;

function selectedTolerance(value)
{
    const dropDownButton = document.getElementById("dropdownMenuOutlineButton1");

    switch (value)
    {
        case 1:
            dropDownButton.innerHTML = "0.001";
            toleranceValue = 0.001;
            round = 3;
            break;
        case 2:
            dropDownButton.innerHTML = "0.0001";
            toleranceValue = 0.0001;
            round = 4;
            break;
        case 3:
            dropDownButton.innerHTML = "0.00001";
            toleranceValue = 0.00001;
            round = 5;
            break;
        case 4:
            dropDownButton.innerHTML = "0.000001";
            toleranceValue = 0.000001;
            round = 6;
            break;
    }



}

function validateInitialValue()
{
    const initialValueInput = document.getElementById("initial-value-input");

    const initalValueHintError = document.getElementById("initial-value-hint-error");

    if (initialValueInput.value < -100 || initialValueInput.value > 100)
    {
        initalValueHintError.innerHTML = "Hodnota mimo intervalu!";
    } else {
        initalValueHintError.innerHTML = "";
    }
}




function initializeTable(data)
{

    const graphDiv = document.getElementById("graph-div");
    graphDiv.style.display="none";

    const tableDiv = document.getElementById("table-div");
    tableDiv.classList.remove("hidden-div");

    const tableElement = document.getElementById("table");

    let header = tableElement.createTHead();
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
        let row = tableElement.insertRow(-1);
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


////////Non-linear methods//////////////////////////////////////////////////////////////////////////////////////////////


function newtonMethod()
{

    const initialValueInput = document.getElementById("initial-value-input");
    const resultElementErrorHint = document.getElementById("result-hint-error");

    resultElementErrorHint.innerHTML = "";

    const iterations = 1000;
    let current = parseFloat(initialValueInput.value);
    const tolerance =  toleranceValue;




    let data = [];

    data[0] = [3];
    data[0][0] = "k";
    data[0][1] = "x";
    data[0][2] = "Chyba";

    let parsedEquation = math.parse(fun);
    let derivative = math.derivative(parsedEquation, 'x');

    for (let i = 0; i < iterations; ++i)
    {

        data[i + 1] = [3];
        data[i + 1][0] = i;
        data[i + 1][1] = current.toFixed(round);

        let fx = math.evaluate(parsedEquation.toString(), { x: current });
        let derfx = 0;


        try
        {
             derfx = math.evaluate(derivative.toString(), { x: current });
        } catch (error) {
            resultElementErrorHint.innerHTML = "Nemožno derivovať!"
        }

        if (derfx === 0)
        {
            resultElementErrorHint.innerHTML = "Delenie číslom 0 vo výpočte!"
            return;
        }

        let next = current - (fx / derfx);
        let error = Math.abs(next - current);
        data[i + 1][2] = error.toFixed(round);



        if (error <= tolerance)
        {
            break;
        }

        current = next;
    }

    $("#table tr").remove();
    initializeTable(data);

}





function bisectionMethod()
{

    const iterations = 1000;

    const tolerance =  toleranceValue;

    const dh = document.getElementById("lower-bound-input");
    const hh = document.getElementById("upper-bound-input");


    const data = []
    data[0] = [5];

    data[0][0] = "k";
    data[0][1] = "dolná hranica";
    data[0][2] = "horná hranica";
    data[0][3] = "x";
    data[0][4] = "chyba";


    let a = parseFloat(dh.value);
    let b = parseFloat(hh.value);

    let parsedEquation = math.parse(fun);

    let xk = 0;
    for (let k = 1; k < iterations; ++k)
    {


        xk = (a + b)/2;
        const fxk = math.evaluate(parsedEquation.toString(), { x: xk });

        const fak = math.evaluate(parsedEquation.toString(), { x: a });

        const fbk = math.evaluate(parsedEquation.toString(), { x: b });


        data[k] = [5];

        data[k][0] = k - 1;
        data[k][1] = a.toFixed(round);
        data[k][2] = b.toFixed(round);
        data[k][3] = xk.toFixed(round);
        data[k][4] = ((b - a) / 2).toFixed(round);

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
            a = xk;
        }






    }


    $("#table tr").remove();
    initializeTable(data);





}


function regulaFalsiMethod()
{

    const tolerance =  toleranceValue;
    const dh = document.getElementById("lower-bound-input");
    const hh = document.getElementById("upper-bound-input");

    let a = parseFloat(dh.value);
    let b = parseFloat(hh.value);
    let prev = a;

    const iterations = 1000;

    let parsedEquation = math.parse(fun);

    const data = [];

    data[0] = [5];
    data[0][0] = "k";
    data[0][1] = "dolná hranica";
    data[0][2] = "horná hranica";
    data[0][3] = "x";
    data[0][4] = "chyba";


    for (let k = 1; k < iterations; ++k) {

        data[k] = [5];
        const fak = math.evaluate(parsedEquation.toString(), { x: a });
        const fbk = math.evaluate(parsedEquation.toString(), { x: b });


        const xk = a - (b - a) / (fbk - fak) * fak;

        const fxk = math.evaluate(parsedEquation.toString(), { x: xk });

        data[k][0] = k - 1;
        data[k][1] = a.toFixed(round);
        data[k][2] = b.toFixed(round);
        data[k][3] = xk.toFixed(round);
        data[k][4] = (Math.abs(xk - prev)).toFixed(round);
        if (fxk === 0) {

            break;
        }

        if (fak * fxk < 0) {
            b = xk;
        }

        if (fbk * fxk < 0) {
            a = xk;
        }

        if (Math.abs(xk - prev) <= tolerance)
        {

            break;
        }

        prev = xk;
    }


    $("#table tr").remove();
    initializeTable(data);



}


function hideGraphTable() {
    document.getElementById("lineChart").style.display="none";
    document.getElementById("table-div").style.display="none";
}









////////Non-linear methods//////////////////////////////////////////////////////////////////////////////////////////////





function graph()
{
    let xValues = [];
    let yValues = [];

    for (let i = -10; i <= 10; ++i)
    {
        xValues.push(i);
        let expression = math.parse(fun);
        yValues.push(math.evaluate(expression.toString(), { x: i }));


    }

    $("#table tr").remove();

    const graphDiv = document.getElementById("graph-div");
    graphDiv.style.display="block";



    var data = {
        labels: xValues,
        datasets: [{
            label: '# of Votes',
            data: yValues,
            backgroundColor: [
                'rgba(255, 99, 132, 0.2)',
                'rgba(54, 162, 235, 0.2)',
                'rgba(255, 206, 86, 0.2)',
                'rgba(75, 192, 192, 0.2)',
                'rgba(153, 102, 255, 0.2)',
                'rgba(255, 159, 64, 0.2)'
            ],
            borderColor: [
                'rgba(255,99,132,1)',
                'rgba(54, 162, 235, 1)',
                'rgba(255, 206, 86, 1)',
                'rgba(75, 192, 192, 1)',
                'rgba(153, 102, 255, 1)',
                'rgba(255, 159, 64, 1)'
            ],
            borderWidth: 1,
            fill: false
        }]
    };

    const backgroundColorPlugin = {
        id: 'customCanvasBackgroundColor',
        beforeDraw: (chart) => {
            const ctx = chart.ctx;
            ctx.fillStyle = 'rgb(255,255,255)';
            ctx.fillRect(0, 0, chart.width, chart.height);
        }
    };

    var options = {
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero: true,
                    fontColor: "#000000",
                },
                gridLines: {
                    color: "rgba(204, 204, 204,0.1)",
                    zeroLineColor: '#000000'
                }
            }],
            xAxes: [{
                ticks: {
                    beginAtZero: true,
                    fontColor: "#000000",
                },
                gridLines: {
                    color: "rgba(204, 204, 204,0.1)",
                    zeroLineColor: '#ffffff'
                }
            }]
        },
        legend: {
            display: false
        },
        elements: {
            point: {
                radius: 0
            }
        }
    };








    if ($("#lineChart").length) {
        var lineChartCanvas = $("#lineChart").get(0).getContext("2d");
        var lineChart = new Chart(lineChartCanvas, {
            type: 'line',
            data: data,
            options: options,
            plugins: [backgroundColorPlugin]
        });
    }



}



function clickOnInput() {
    const element = document.getElementById("file-input");

    element.click();
}


/////////////////////////////////////////Aproximation & interpolation///////////////////////////////////////////////////////////////


let selectedFunction = 0;
function selectFunction(whichOne)
{
    if (whichOne === 1) {
        document.getElementById("dropdownMenuOutlineButton3").innerHTML = "Lineárna funkcia";
    } else {
        document.getElementById("dropdownMenuOutlineButton3").innerHTML = "Logaritmická funkcia";
    }

    selectedFunction = whichOne;
}


function calculateApproximationMethod()
{

    switch(selectedMethod) {
        case 1:
            lagrangeInterpolating();
            break;
        case 2:
            newtonInterpolating();
            break;
        case 3:
            leastSquares();
            break;
    }
}

function lagrangeInterpolating()
{

    let arr = document.getElementById("nodes-string-input").value.match(/-?\d+(\.\d+)?/g);
    let equation = "";
    let first = true;

    for (let i = 0; i < arr.length; i+=2)
    {
        let part = "";
        let menovatel = 1;
        for (let k = 0; k < arr.length; k+=2)
        {

            if (i === k)
            {
                continue;
            }


            if (arr[k] > 0)
            {
                part += "(x - " + (Number.isInteger(1 * arr[k]) ? (1 * arr[k]): ((1 * arr[k]).toFixed(3))) + ")";
            } else
            {
                part += "(x + " + (Number.isInteger(-1 * arr[k]) ? (-1 * arr[k]): ((-1 * arr[k]).toFixed(3))) + ")";
            }


            menovatel *= (arr[i] - arr[k]);

        }

        menovatel = 1/ menovatel;



        if (first)
        {
            part = (Number.isInteger(arr[i + 1] * menovatel) ? (arr[i + 1] * menovatel): ((arr[i + 1] * menovatel).toFixed(3))) + part;
            equation += part;
            first = false;
        } else
        {
            if (arr[i + 1] * menovatel > 0)
            {
                part = "+" + (Number.isInteger(arr[i + 1] * menovatel) ? (arr[i + 1] * menovatel): ((arr[i + 1] * menovatel).toFixed(3))) + part;
            } else
            {
                part = (Number.isInteger(arr[i + 1] * menovatel) ? (arr[i + 1] * menovatel): ((arr[i + 1] * menovatel).toFixed(3))) + part;
            }

            equation += part;
        }


    }


    fun = equation;

    document.getElementById("result-input").value = fun;

    graph();



}





function newtonInterpolating()
{

    let arr = document.getElementById("nodes-string-input").value.match(/-?\d+(\.\d+)?/g);
    let data =[];

    let j = 0;
    for (let i = 0; i < arr.length; i+=2)
    {
        data[j] = [2];
        data[j][0] = arr[i];
        data[j][1] = arr[i + 1];
        console.log(data[j][0] + " " + data[j][1]);
        ++j;

    }

    let equation = "";


    for (let i = 0; i < data.length; ++i)
    {
        let cislo = dividedDifference(0,i);

        if (i == 0)
        {
            if (cislo < 0)
            {
                equation += "- " + (Number.isInteger(1 * cislo) ? ((-1) * cislo): ((-1) * cislo.toFixed(3)));
            } else {
                equation += (Number.isInteger(1 * cislo) ? ( 1 * cislo): (1 * cislo).toFixed(3));

            }
            continue;
        }

        if (cislo > 0)
        {
            equation += "+";
        }
        equation += (Number.isInteger(1 * cislo) ? (1 * cislo): (1 * cislo).toFixed(3));

        for (let j = 0; j < i; ++j)
        {
            if (data[j][0] < 0)
            {
                equation += "(x + " + (Number.isInteger((-1) * data[j][0]) ? ((-1) * data[j][0]): ((-1) * data[j][0]).toFixed(3))+ ")";
            } else {
                equation += "(x - " + (Number.isInteger(1 * data[j][0]) ? (1 * data[j][0]): (1 * data[j][0]).toFixed(3)) + ")";
            }
        }
    }



    fun = equation;

    document.getElementById("result-input").value = fun;

    graph();


    function dividedDifference(i, j)
    {

        if (j === 0) {
            return data[i][1];
        } else {
            return (dividedDifference(i + 1, j - 1) - dividedDifference(i, j - 1)) / (data[i + j][0] - data[i][0]);
        }
    }
}


function leastSquares() {


    if (selectedFunction === 1) {

        linear();
    }

    if (selectedFunction === 2) {
        logaritmic();
    }


}



function logaritmic()
{



    let arr = document.getElementById("nodes-string-input").value.match(/-?\d+(\.\d+)?/g);
    let data =[];

    let j = 0;
    for (let i = 0; i < arr.length; i+=2)
    {
        data[j] = [2];
        data[j][0] = arr[i];
        data[j][1] = arr[i + 1];

        ++j;

    }

    let coeficients = [];


    coeficients.push(data.length);


    let sum_x = 0;
    for(let i = 0; i < data.length; ++i)
    {

        sum_x += Math.log(parseFloat(data[i][0]));
    }

    coeficients.push(sum_x);



    let sum_y = 0;
    for(let i = 0; i < data.length; ++i)
    {
        sum_y += parseFloat(data[i][1]);
    }

    coeficients.push(sum_y);

    let sum_x_2 = 0;
    for(let i = 0; i < data.length; ++i)
    {
        sum_x_2 += (Math.log(parseFloat(data[i][0])) * Math.log(parseFloat(data[i][0])));
    }

    coeficients.push(sum_x_2);

    let sum_x_y = 0;
    for(let i = 0; i < data.length; ++i)
    {
        sum_x_y += (Math.log(parseFloat(data[i][0])) * data[i][1]);
    }

    coeficients.push(sum_x_y);

    let equation = cramer(2,coeficients);

    fun = equation;

    document.getElementById("result-input").value = fun;

    display();



}


function linear()
{

    let arr = document.getElementById("nodes-string-input").value.match(/-?\d+(\.\d+)?/g);
    let data =[];

    let j = 0;
    for (let i = 0; i < arr.length; i+=2)
    {
        data[j] = [2];
        data[j][0] = arr[i];
        data[j][1] = arr[i + 1];

        ++j;

    }

    let coeficients = [];


    coeficients.push(data.length);

    let sum_x = 0;
    for(let i = 0; i < data.length; ++i)
    {
        sum_x += (1 *data[i][0]);
    }

    coeficients.push(sum_x);


    let sum_y = 0;
    for(let i = 0; i < data.length; ++i)
    {
        sum_y += (1 *data[i][1]);
    }

    coeficients.push(sum_y);

    let sum_x_2 = 0;
    for(let i = 0; i < data.length; ++i)
    {
        sum_x_2 += (data[i][0] * data[i][0]);

    }

    coeficients.push(sum_x_2);

    let sum_x_y = 0;
    for(let i = 0; i < data.length; ++i)
    {
        sum_x_y += (data[i][0] * data[i][1]);
    }

    coeficients.push(sum_x_y);


    let equation = cramer(1,coeficients);



    fun = equation;

    document.getElementById("result-input").value = fun;
    display();


}


function cramer(option, coeficients) {

    let A = coeficients[0] * coeficients[3] -  coeficients[1] * coeficients[1];



    let A1 = coeficients[2] * coeficients[3] -  coeficients[4] * coeficients[1];


    let A2 = coeficients[0] * coeficients[4] -  coeficients[2] * coeficients[1];

    switch (option)
    {
        case 1:
            return "" + (A1 / A).toFixed(3) + " +" + (A2 / A).toFixed(3) + "x";

        case 2:
            return "" +(A1 / A).toFixed(3) + " +" + (A2 / A).toFixed(3) + "log(x)";

    }



}









/////////////////////////////Integration//////////////////////////////////////////////////////////////////////////////

function trapezoid()
{

    const hh = document.getElementById("upper-bound-input");
    const dh = document.getElementById("lower-bound-input");

    const n = document.getElementById("subintervals-input");

    const functi = document.getElementById("function-input");

    const h = (hh.value - dh.value) / n.value;

    const parsedEquation = math.parse(functi.value);


    let sum = 0;
    let part = 1 * dh.value;

    for (let i = 0; i <= n.value; ++i)
    {

        if (i == 0)
        {
            let fx = math.evaluate(parsedEquation.toString(), { x: dh.value});
            sum += fx;
            continue;
        }

        if (i == n.value)
        {
            let fx = math.evaluate(parsedEquation.toString(), { x: hh.value});
            sum += fx;
            continue;
        }

        part += 1 * h;

        let fx = 2 * math.evaluate(parsedEquation.toString(), { x: part })
        sum = sum + fx;


    }


    const result = sum * h/2;

    document.getElementById("result-input").value = result.toFixed(6);


}

function simpson() {

    const hh = document.getElementById("upper-bound-input");
    const dh = document.getElementById("lower-bound-input");

    const n = document.getElementById("subintervals-input");

    const h = (hh.value - dh.value) / n.value;
    const functi = document.getElementById("function-input");
    const parsedEquation = math.parse(functi.value);


    let sum = 0;
    let part = 1 * dh.value;

    for (let i = 0; i <= n.value; ++i)
    {

        if (i == 0)
        {
            sum += math.evaluate(parsedEquation.toString(), { x: dh.value});
            continue;
        }

        if (i == n.value)
        {
            sum += math.evaluate(parsedEquation.toString(), { x: hh.value});
            continue;
        }

        part += 1 * h;

        if (i % 2 !== 0)
        {
            sum = sum + (4 * math.evaluate(parsedEquation.toString(), { x: part }));
        }

        if (i % 2 === 0)
        {
            sum = sum + (2 * math.evaluate(parsedEquation.toString(), { x: part }));
        }



    }


    const result = sum * h/3;

    document.getElementById("result-input").value = result.toFixed(6);



}


function calculateIntegrationMethod()
{

    switch(selectedMethod) {
        case 1:
            trapezoid();
            break;
        case 2:
            simpson();
            break;
    }
}













