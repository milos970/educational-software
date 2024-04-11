
function getById(id)
{
    const element = document.getElementById(id);
    if (!element)
    {
        throw new ReferenceError(id + " is not defined");
    }
    return element;
}

const equationInputElement = getById("equation-input");
const functionInputElement = getById("function-input");
const initialValueInputElement = getById("initial-value-input");
const resultValueInputElement = getById("result-input");
const subintervalsValueInputElement = getById("subintervals-input");

const lowerValueInputElement = getById("lower-bound-input");
const upperValueInputElement = getById("upper-bound-input");


const equationErrorHintElement = getById("equation-hint-error");
const functionErrorHintElement = getById("function-hint-error");
const toleranceValueErrorHintElement = getById("tolerance-value-hint-error");
const resultValueErrorHintElement = getById("result-hint-error");


const ITERATIONS = 1000_000;
let tolerance = 0;






function saveToFile()
{



    let allRows =[]

    if (selectedCategory === 1)
    {
        if (resultValueInputElement.value.length === 0)
        {
            return;
        }

        allRows = inputs.concat(results);
        switch(selectedMethod)
        {
            case 1:
                fileName = "Newtonová metóda";

                break;
            case 2:
                fileName = "Regula falsi";

                break;
            case 3:
                fileName = "Bisekcia";

                break;
            default:
        }
    }

    if (selectedCategory === 2)
    {
        if (resultValueInputElement.value.length === 0)
        {
            return;
        }
        allRows = inputs;
        switch(selectedMethod)
        {
            case 1:
                fileName = "Lichobežníková metóda";
                break;
            case 2:
                fileName = "Simpsonová metóda";
                break;
            default:
        }
    }

    if (selectedCategory === 3)
    {

        if (!isSelected())
        {
            return;
        }
        allRows = inputs.concat(results);
        switch(selectedMethod)
        {
            case 1:
                fileName = "Lagrangeov polynóm";
                break;
            case 2:
                fileName = "Newtonov polynom";
                break;
            case 3:
                fileName = "Metóda najmenších štvorcov";

                if (selectedFunction === 1)
                {
                    fileName = fileName + "-" + "Lineárna funkcia";
                }

                if (selectedFunction === 2)
                {
                    fileName = fileName + "-" + "Logaritmická funkcia";
                }
                break;
            default:
        }
    }








    const csvContent = allRows.map(row => row.join(';')).join('\n');

    const blob = new Blob(["\uFEFF" + csvContent], { type: 'text/csv; charset=utf-8' });

    const link = document.createElement('a');

    link.href = window.URL.createObjectURL(blob);



    link.download = fileName + ".csv";

    document.body.appendChild(link);
    link.click();

    document.body.removeChild(link);
}


let fun = "";






let selectedCategory = 0;
let selectedMethod = 0;
function selectNumericCategory(category)
{

    document.getElementById("methods-calculation-div").style.display = "block";
    document.getElementById("graph-calculation-div").style.display = "block";
    selectedCategory = category;
    setUpElements(category, 1);
    resetRightCard();
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
    document.getElementById("lower-bound-div").value ="";
    document.getElementById("upper-bound-div").value ="";

    document.getElementById("initial-value-div").style.display="none";
    document.getElementById("equation-div").style.display="none";
    document.getElementById("tolerance-div").style.display="none";
    document.getElementById("initial-value-div").value ="";
    document.getElementById("equation-div").value ="";
    document.getElementById("tolerance-div").value ="";
    document.getElementById("non-linear-dropdown-div").style.display="none";
    document.getElementById("result-error-div").style.display="none";
}

function hideAllIntegrationElements()
{
    document.getElementById("lower-bound-div").style.display="none";
    document.getElementById("upper-bound-div").style.display="none";
    document.getElementById("subintervals-div").style.display="none";
    document.getElementById("function-div").style.display="none";

    document.getElementById("integration-dropdown-div").style.display="none";
    document.getElementById("result-error-div").style.display="none";
}

function hideAllApproximationElements()
{
    document.getElementById("nodes-string-div").style.display="none";
    document.getElementById("nodes-csv-div").style.display="none";
    document.getElementById("functions-dropdown-div").style.display="none";

    document.getElementById("approximation-dropdown-div").style.display="none";
    document.getElementById("result-error-div").style.display="none";
}





function selectNonLinearMethod(value)
{
    hideAllIntegrationElements();
    hideAllApproximationElements();

    const dropDownButton = document.getElementById("dropdownMenuButton1");



    document.getElementById("result-input").value = "";

    const nonLinearDropdownDiv = document.getElementById("non-linear-dropdown-div");
    nonLinearDropdownDiv.style.display="block";

    const lowerBoundDivElement = document.getElementById("lower-bound-div");
    const upperBoundDivElement = document.getElementById("upper-bound-div");

    const initialValueDivElement = document.getElementById("initial-value-div");
    const equationDiv = document.getElementById("equation-div");
    equationDiv.style.display="block";

    const toleranceDiv = document.getElementById("tolerance-div");

    $("#table tr").remove();


    switch (value)
    {
        case 1:
            dropDownButton.innerHTML = "Newtonová metóda";

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

    document.getElementById("result-input").value = "";
    const functionsDropdownDiv = document.getElementById("functions-dropdown-div");

    $("#table tr").remove();
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
            document.getElementById("dropdownMenuButton3").innerHTML = "Newtonov polynóm";
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
    $("#table tr").remove();
    const subintervalsDiv = document.getElementById("subintervals-div");

    document.getElementById("initial-value-input").value = "";


    document.getElementById("result-input").value = "";

    switch (value)
    {
        case 1:
            document.getElementById("dropdownMenuButton2").innerHTML = "Lichobežníková metóda";

            subintervalsDiv.style.display = "block";
            selectedMethod = 1;
            break;
        case 2:
            document.getElementById("dropdownMenuButton2").innerHTML = "Simpsonová metóda";


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



let round = -1;
function selectedTolerance(value)
{
    const dropDownButton = getById("dropdownMenuOutlineButton1");

    switch (value)
    {
        case 1:
            dropDownButton.innerHTML = "0.001";
            tolerance = 0.001;
            round = 3;
            break;
        case 2:
            dropDownButton.innerHTML = "0.0001";
            tolerance = 0.0001;
            round = 4;
            break;
        case 3:
            dropDownButton.innerHTML = "0.00001";
            tolerance = 0.00001;
            round = 5;
            break;
        case 4:
            dropDownButton.innerHTML = "0.000001";
            tolerance = 0.000001;
            round = 6;
            break;
        case 5:
            dropDownButton.innerHTML = "0.0000001";
            tolerance = 0.0000001;
            round = 7;
            break;
        case 6:
            dropDownButton.innerHTML = "0.00000001";
            tolerance = 0.00000001;
            round = 8;
            break;
        default:
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

    const tableDiv = document.getElementById("graph-calculation-div");
    tableDiv.style.display="block";

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

let inputs = [];
let results = [];
function validateInitialValue()
{
    const initialValueInput = getById("initial-value-input");
    const initialValueErrorHint = getById("initial-value-hint-error");

    if (initialValueInput.value.length !== 0)
    {
        initialValueErrorHint.innerHTML = "";
    } else
    {
        initialValueErrorHint.innerHTML = "Prázdne pole!";
        return false;
    }

    if (initialValueInput.value >= -100 && initialValueInput.value <= 100)
    {
        initialValueErrorHint.innerHTML = "";
        return true;
    } else
    {
        initialValueErrorHint.innerHTML = "Hodnota mimo intervalu!";
        return false;
    }
}


function validateToleranceValue()
{

    const toleranceValueErrorHint = getById("tolerance-value-hint-error");

    if (tolerance === 0.001 || tolerance === 0.0001 || tolerance === 0.00001 || tolerance === 0.000001 || tolerance === 0.0000001 || tolerance === 0.00000001)
    {
        toleranceValueErrorHint.innerHTML = "";
        return true;
    } else
    {
        toleranceValueErrorHint.innerHTML = "Nezvolená hodnota!";
        return false;
    }

}


function validateBounds()
{
    const lowerBoundInput = getById("lower-bound-input");
    const upperBoundInput = getById("upper-bound-input");
    const lowerBoundInputErrorHint = getById("lower-bound-value-hint-error");
    const upperBoundInputErrorHint = getById("upper-bound-value-hint-error");


    if (lowerBoundInput.value.length === 0)
    {
        lowerBoundInputErrorHint.innerHTML = "Prázdne pole!";

        return false;
    } else {

        lowerBoundInputErrorHint.innerHTML = "";
    }

    if (lowerBoundInput.value < -10 || lowerBoundInput.value > 9)
    {

        lowerBoundInputErrorHint.innerHTML = "Hodnota mimo intervalu!";
        return false;
    } else {
        lowerBoundInputErrorHint.innerHTML = "";
    }




    if (upperBoundInput.value.length === 0)
    {
        upperBoundInputErrorHint.innerHTML = "Prázdne pole!";
        return false;
    } else {
        upperBoundInputErrorHint.innerHTML = "";
    }




    if (upperBoundInput.value < -9 || upperBoundInput.value > 10)
    {
        upperBoundInputErrorHint.innerHTML = "Hodnota mimo intervalu!";
        return false;
    } else {
        upperBoundInputErrorHint.innerHTML = "";
    }


    const upperValue = parseFloat(upperBoundInput.value);
    const lowerValue = parseFloat(lowerBoundInput.value);

    if (upperValue <= lowerValue)
    {
        upperBoundInputErrorHint.innerHTML = "Horná hranica je menšia než dolná!";
        return false;
    } else {
        upperBoundInputErrorHint.innerHTML = "";
    }


    return true;

}


function validateFunction()
{
    const functionInput = getById("function-input");
    const functionErrorHint = getById("function-hint-error");


    if (functionInput.value.length === 0)
    {
        functionErrorHint.innerHTML = "Prázdne pole!";
        return false;
    } else {
        functionErrorHint.innerHTML = "";
    }



    try
    {
        let expression = math.parse(functionInput.value);
        math.evaluate(expression.toString(), { x: 0 });
    } catch (error)
    {
        functionErrorHint.innerHTML = "Nevalidný výraz!";
        return false;
    }



    fun = functionInput.value;


    return true;
}


function validateEquation()
{
    const equationInput = getById("equation-input");
    let modifiedEquation = equationInput.value;
    const equationErrorHint = getById("equation-hint-error");


    if (modifiedEquation === null || modifiedEquation === "")
    {
        equationErrorHint.innerHTML = "Zadajte rovnicu v požadovanom tvare!";
        return false;
    }

    modifiedEquation = modifiedEquation.trim();
    modifiedEquation = modifiedEquation.toLowerCase();


    if (modifiedEquation.indexOf("x") === -1)
    {
        equationErrorHint.innerHTML = "Nevalidný výraz!";
        return false;
    }



    if (modifiedEquation.charAt(modifiedEquation.length - 1) !== "0")
    {
        equationErrorHint.innerHTML = "Nevalidný výraz!";
        return false;
    }

    if (!modifiedEquation.includes("="))
    {
        equationErrorHint.innerHTML = "Nevalidný výraz!";
        return false;
    }


    modifiedEquation = modifiedEquation.slice(0, -1);
    modifiedEquation = modifiedEquation.replace("=","");


    try
    {
        let expression = math.parse(modifiedEquation);
        math.evaluate(expression.toString(), { x: 0 });
    } catch (error)
    {
        equationErrorHint.innerHTML = "Nevalidný výraz!";
        return false;
    }

    equationErrorHint.innerHTML = "";

    fun = modifiedEquation;


    return true;
}



function newtonMethod()
{
    if (!validateEquation() || !validateToleranceValue() || !validateInitialValue())
    {
        return false;
    }


    let current = parseFloat(initialValueInputElement.value);


    const data = [];



    data[0] = [3];
    data[0][0] = "k";
    data[0][1] = "x";
    data[0][2] = "Chyba";

    const parsedEquation = math.parse(fun);
    const derivative = math.derivative(parsedEquation, 'x');

    for (let i = 0; i < ITERATIONS; ++i)
    {
        data[i + 1] = [3];
        data[i + 1][0] = i;
        data[i + 1][1] = current.toFixed(round);

        let fx = 0;
        try
        {
            fx = math.evaluate(parsedEquation.toString(), { x: current });
        }
        catch (error) {
            equationErrorHintElement.innerHTML = "Nedefinovaná hodnota!";
        }


        let derFx = 0;


        try
        {
            derFx = math.evaluate(derivative.toString(), { x: current });
        }
        catch (error) {
            equationErrorHintElement.innerHTML = "Nedefinovaná hodnota!";
        }

        if (derFx === 0)
        {

            return;
        }

        let next = current - (fx / derFx);
        let error = Math.abs(next - current);
        data[i + 1][2] = error.toFixed(round);



        if (error <= tolerance)
        {
            break;
        }

        current = next;
    }

    $("#table tr").remove();




    const resultInput = getById("result-input");
    resultInput.value = "Vypočítaný koreň rovnice: " + current.toFixed(round);


    inputs[0] = [2];


    inputs[0][0] = "Rovnica";
    inputs[0][1] = document.getElementById("equation-input").value;

    inputs[1] = [2];
    inputs[1][0] = "Tolerancia";
    inputs[1][1] = tolerance;


    inputs[2] = [2];
    inputs[2][0] = "Počiatočná aproximácia";
    inputs[2][1] = document.getElementById("initial-value-input").value;


    inputs[3] = [2];
    inputs[3][0] = "Vypočítaný koreň rovnice"
    inputs[3][1] = current.toFixed(round);


    inputs[4] = [1];
    inputs[4][0] = "";

    results = data;





    initializeTable(data);

}





function bisectionMethod()
{

    if (!validateEquation() || !validateToleranceValue() || !validateBounds())
    {
        return false;
    }


    resultValueErrorHintElement.innerHTML = "";


    const data = [];
    data[0] = [5];

    data[0][0] = "k";
    data[0][1] = "dolná hranica";
    data[0][2] = "horná hranica";
    data[0][3] = "x";
    data[0][4] = "chyba";


    let a = parseFloat(lowerValueInputElement.value);
    let b = parseFloat(upperValueInputElement.value);

    let parsedEquation = math.parse(fun);

    let xk = 0;
    for (let k = 1; k < ITERATIONS; ++k)
    {
        xk = (a + b)/2;
        const fxk = math.evaluate(parsedEquation.toString(), { x: xk });

        const fak = math.evaluate(parsedEquation.toString(), { x: a });

        const fbk = math.evaluate(parsedEquation.toString(), { x: b });

        if (fak * fbk > 0)
        {
            resultValueErrorHintElement.innerHTML = "Funkcia nieje spojitá na danom intervale!";
            return;
        }

        data[k] = [5];

        data[k][0] = k - 1;
        data[k][1] = a.toFixed(round);
        data[k][2] = b.toFixed(round);
        data[k][3] = xk.toFixed(round);
        data[k][4] = ((b - a) / 2).toFixed(round);

        if ((b - a) / 2 <= tolerance)
        {
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


    resultValueInputElement.value = "Vypočítaný koreň rovnice: " + xk.toFixed(round);

    $("#table tr").remove();
    initializeTable(data);


    inputs =[];

    inputs[0] = [2];


    inputs[0][0] = "Rovnica";
    inputs[0][1] = equationInputElement.value;


    inputs[1] = [2];
    inputs[1][0] = "Tolerancia";
    inputs[1][1] = tolerance;


    inputs[2] = [1];
    inputs[2][0] = "Dolná hranica";
    inputs[2][1] = lowerValueInputElement.value;


    inputs[3] = [2];
    inputs[3][0] = "Horná hranica";
    inputs[3][1] = upperValueInputElement.value;


    inputs[4] = [2];
    inputs[4][0] = "Vypočítaný koreň rovnice"
    inputs[4][1] = xk.toFixed(round);


    inputs[5] = [1];
    inputs[5][0] = "";

    results = data;

}


function regulaFalsiMethod()
{
    if (!validateEquation() || !validateToleranceValue() || !validateBounds())
    {
        return false;
    }


    let a = parseFloat(lowerValueInputElement.value);
    let b = parseFloat(upperValueInputElement.value);
    let prev = a;



    let parsedEquation = math.parse(fun);

    const data = [];

    data[0] = [5];
    data[0][0] = "k";
    data[0][1] = "dolná hranica";
    data[0][2] = "horná hranica";
    data[0][3] = "x";
    data[0][4] = "chyba";

    let xk = 0;
    for (let k = 1; k < ITERATIONS; ++k) {


        const fak = math.evaluate(parsedEquation.toString(), { x: a });
        const fbk = math.evaluate(parsedEquation.toString(), { x: b });

        if (fak * fbk > 0)
        {

            equationErrorHintElement.innerHTML = "Funkcia nieje spojitá na danom intervale!";
            return;
        }


         xk = a - (b - a) / (fbk - fak) * fak;

        const fxk = math.evaluate(parsedEquation.toString(), { x: xk });

        data[k] = [5];
        data[k][0] = k - 1;
        data[k][1] = a.toFixed(round);
        data[k][2] = b.toFixed(round);
        data[k][3] = xk.toFixed(round);
        data[k][4] = (Math.abs(xk - prev)).toFixed(round);

        if (fxk === 0)
        {
            break;
        }

        if (fak * fxk < 0)
        {
            b = xk;
        }

        if (fbk * fxk < 0)
        {
            a = xk;
        }

        if (Math.abs(xk - prev) <= tolerance)
        {
            break;
        }

        prev = xk;
    }

    resultValueInputElement.value = "Vypočítaný koreň rovnice: " + xk.toFixed(round);

    $("#table tr").remove();
    initializeTable(data);


     inputs =[];

    inputs[0] = [2];


    inputs[0][0] = "Rovnica";
    inputs[0][1] = equationInputElement.value;


    inputs[1] = [2];
    inputs[1][0] = "Tolerancia";
    inputs[1][1] = tolerance;


    inputs[2] = [2];
    inputs[2][0] = "Dolná hranica";
    inputs[2][1] = lowerValueInputElement.value;


    inputs[3] = [2];
    inputs[3][0] = "Horná hranica";
    inputs[3][1] = upperValueInputElement.value;


    inputs[4] = [2];
    inputs[4][0] = "Vypočítaný koreň rovnice"
    inputs[4][1] = xk.toFixed(round);


    inputs[5] = [1];
    inputs[5][0] = "";

    results = data;



}




function hideGraphTable() {
    document.getElementById("lineChart").style.display="none";
    document.getElementById("table-div").style.display="none";
}









////////Non-linear methods//////////////////////////////////////////////////////////////////////////////////////////////




let lineChart = null;
function graph()
{
    if (selectedCategory === 1)
    {
        if (!validateEquation())
        {
            return;
        }
    }


    if (selectedCategory === 2)
    {
        if (!validateFunction() || !validateBounds())
        {
            return;
        }
    }

    if (selectedCategory === 3)
    {
        if (!isSelected() || resultValueInputElement.value.length === 0)
        {
            return;
        }
    }




    let expression = math.parse(fun);
    let data = [];



    getById("graph-calculation-div").style.display="block";


    let xValues = [];
    let yValues = [];

    let yValues1 = [];

    if (selectedCategory === 1)
    {
        for (let i = -10; i <= 10; ++i)
        {
            xValues.push(i);
            yValues.push(math.evaluate(expression.toString(), { x: i }));
        }
    }

    if (selectedCategory === 2)
    {
        const lower = parseFloat(lowerValueInputElement.value);
        const upper = parseFloat(upperValueInputElement.value);
        for (let i = lower; i <= upper; ++i)
        {
            xValues.push(i);
            yValues.push(math.evaluate(expression.toString(), { x: i }));
        }
    }




    if (selectedCategory === 3)
    {
        points.sort(function(a, b) {
            return a[0] - b[0];
        });




        for (let i = 0; i < points.length; ++i)
        {
            xValues.push(points[i][0]);

            yValues.push(points[i][1]);
        }



        for (let i = 0; i < xValues.length; ++i)
        {
            yValues1.push(math.evaluate(expression.toString(), { x: xValues[i] }));
        }

    }



    $("#table tr").remove();

    const graphDiv = document.getElementById("graph-div");
    graphDiv.style.display="block";





     data = {
        labels: xValues,
        datasets: [
        {
            label: 'line 1',
            data: yValues,
            backgroundColor: 'rgba(255, 99, 132, 0.2)',
            borderColor: 'rgba(255, 99, 132, 1)',
            borderWidth: 2,
            fill: true,
            type: "line",

            pointBackgroundColor: function(context)
            {
                // Highlight points where x equals 0
                return context.dataIndex === 10 ? 'blue' : 'blue';
            },
            // Define point border color
            pointBorderColor: function(context) {
                // Highlight points where x equals 0
                return context.dataIndex === 10 ? 'blue' : 'blue';
            },

            pointRadius: function(context) {
                // Increase the radius of highlighted points
                return context.dataIndex === 10 ? 2 : 2; // Adjust the value as needed
            },


        },
            {
                label: 'Points Only',  // Label for the dataset
                data: yValues1,          // Y values for the points
                backgroundColor: 'red',  // Background color of the points
                borderColor: 'red',      // Border color of the points
                borderWidth: 3,           // Border width of the points

                type: 'scatter',          // Type of the dataset (scatter for points only)
            }


        ],
        lineAtIndex: 2
    };

    if (selectedCategory === 1 || selectedCategory === 3)
    {
        data.datasets[0].fill = false;
    } else {
        data.datasets[0].fill = true;
    }


    if (selectedCategory === 3 && selectedMethod === 3)
    {
        data.datasets[1].hidden = false;
        data.datasets[1].type = "line";
        data.datasets[1].fill = false;
        data.datasets[0].type = "scatter";
    } else {
        data.datasets[1].hidden = true;
    }

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
                    zeroLineWidth: 3,
                    zeroLineColor: "#2C292E",

                }
            }],
            xAxes: [{
                ticks: {
                    beginAtZero: true,
                    fontColor: "#000000",

                },
                gridLines: {
                    
                    zeroLineColor: "#2C292E",
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




    const pl = {
        afterDraw: function(chart) {
            var ctx = chart.ctx;
            var xAxis = chart.scales['x-axis-0'];
            var yAxis = chart.scales['y-axis-0'];
            ctx.save();
            ctx.beginPath();
            ctx.strokeStyle = '#000000'; // Red color for the vertical line
            ctx.lineWidth = 3;
            ctx.moveTo(xAxis.getPixelForValue(0), yAxis.top);
            ctx.lineTo(xAxis.getPixelForValue(0), yAxis.bottom);
            ctx.stroke();
            ctx.restore();
        }
    }






    if ($("#lineChart").length) {
        let lineChartCanvas = $("#lineChart").get(0).getContext("2d");
         lineChart = new Chart(lineChartCanvas, {
            data: data,
            options: options,
            plugins:
                [backgroundColorPlugin, pl]

        });

    }






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


let selectedApproximationInput = 0;
function isSelected()
{
    if ( (getById("nodes-string-input").value.length === 0) && (getById("file-input").files.length === 0) )
    {
        getById("coordinates-string-error-hint").innerHTML = "Zvoľte túto možnosť!";
        getById("coordinates-csv-error-hint").innerHTML = "Alebo túto možnosť!";
        return false;
    }

    if ( (getById("nodes-string-input").value.length != 0) && (getById("file-input").files.length != 0) ) {
        getById("coordinates-string-error-hint").innerHTML = "Vyberte len jednu možnosť!";

        return false;
    }


    if ( (getById("nodes-string-input").value.length !== 0) )
    {
        selectedApproximationInput = 1;
    } else {
        selectedApproximationInput = 2;
    }

    return true;
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

function resetRightCard()
{
    $("#table tr").remove(); //zmaže tabulku

    if (lineChart != null)
    {
        lineChart.destroy(); //zmaže graf
    }

    getById("graph-calculation-div").style.display = "none";
}

let points = [];
function lagrangeInterpolating()
{

    if (!isSelected())
    {
        return;
    }

    let arr = [];

    if (selectedApproximationInput === 1 && validateStringCoordinates())
    {
        arr = document.getElementById("nodes-string-input").value.match(/-?\d+(\.\d+)?/g);
    }

    if (selectedApproximationInput === 2 && validateCsvCoordinates())
    {
        let j = 0;
        for (let i = 0; i < array.length; i+=2)
        {
            arr[i] = array[j][0];
            arr[i + 1] = array[j][1];
            ++j;
        }

    }






   let j = 0;
   for (let i = 0; i < arr.length; i+=2)
   {
       points[j] = [2];
       points[j][0] = parseFloat(arr[i]);
       points[j][1] = parseFloat(arr[i + 1]);

       ++j;
   }



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

    resultValueInputElement.value = "f(x) = " + fun;

    inputs = [];

    inputs[0] = [2];
    inputs[0][0] =  "f(x)";
    inputs[0][1] =  resultValueInputElement.value;

    inputs[1] = [1];
    inputs[1][0] = "Uzly"


    results = points;



}





function newtonInterpolating()
{
    if (!isSelected())
    {
        return;
    }



    if (selectedApproximationInput === 1 && validateStringCoordinates())
    {
        data = parseStringCoordintatesToArrays();
    }

    if (selectedApproximationInput === 2 && validateCsvCoordinates())
    {
        data = parseCsvToArrays();
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

    document.getElementById("result-input").value = "f(x) = " + fun;
    points = data;


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

    if (!isSelected())
    {
        return;
    }



    if (selectedApproximationInput === 1 && validateStringCoordinates())
    {
        points = parseStringCoordintatesToArrays();
    }

    if (selectedApproximationInput === 2 && validateCsvCoordinates())
    {
        points = parseCsvToArrays();
    }

    if (selectedFunction === 1) {

        linear();
    }

    if (selectedFunction === 2) {
        logaritmic();
    }


}





let array = [];
function parseCsvToArrays()
{

    var csvString = "";
    function csvToString(file, callback) {
        let reader = new FileReader();

        // Closure to capture the file information.
        reader.onload = function(event) {
             csvString = event.target.result;

            callback(csvString);
        };

        // Read in the file as a text
        reader.readAsText(file);
    }


    const file = getById("file-input").files[0];

    getFileName();

    csvToString(file, function(csvString) {
        array = $.csv.toArrays(csvString);


    });

    return array;

}


function getFileName()
{
    const elementInputFileName = getById("text-input");
    const elementInputFile = getById("file-input");

    elementInputFileName.value = elementInputFile.files[0].name;
}

function parseStringCoordintatesToArrays()
{
    const stringCoordinatesInputElement = getById("nodes-string-input");

    const array = stringCoordinatesInputElement.value.match(/-?\d+(\.\d+)?/g);


    const coordinates = [];
    let j = 0;
    for (let i = 0; i < array.length; i += 2)
    {
        coordinates[j] = [2];
        coordinates[j][0] = array[i];
        coordinates[j][1] = array[i + 1];
        ++j;
    }

    return coordinates;
}


function validateStringCoordinates()
{
    const regex = /^\((?:-?\d*\.?\d+),(-?\d*\.?\d+)\)(?:,\((?:-?\d*\.?\d+),(-?\d*\.?\d+)\)){3,}$/;


    const coordinatesStringElement = getById("nodes-string-input");
    const coordinatesStringErrorHint = getById("coordinates-string-error-hint");

    if (coordinatesStringElement.value.length === 0)
    {
        coordinatesStringErrorHint.innerHTML = "Prázdne pole!";
        return false;
    }


    if (regex.test(coordinatesStringElement.value))
    {
        coordinatesStringErrorHint.innerHTML = "";
    } else {
        coordinatesStringErrorHint.innerHTML = "Nevalidný výraz!";
        return false;
    }

    return validateCoordinates(parseStringCoordintatesToArrays(), 1);
}

function validateCsvCoordinates()
{
    parseCsvToArrays();

    return validateCoordinates(array, 2);
}




function validateCoordinates(coordinatesArrays, type)
{

    const coordinatesCsvErrorHint = getById("coordinates-csv-error-hint");
    const coordinatesStringErrorHint = getById("coordinates-string-error-hint");




    if (type === 1)
    {
        if (coordinatesArrays.length > 10)
        {
            coordinatesStringErrorHint.innerHTML = "Uzlov je viac než 10!";
            return false;
        } else {
            coordinatesStringErrorHint.innerHTML = "";
        }

    }


    if (type === 2)
    {

        if (coordinatesArrays.length > 1000)
        {
            coordinatesCsvErrorHint.innerHTML = "Uzlov je viac než 1000!";
            return false;
        } else {
            coordinatesCsvErrorHint.innerHTML = "";
        }

    }



    let uniques = new Set();



    for (let i = 0; i < coordinatesArrays.length; ++i)
    {
        let valueX = coordinatesArrays[i][0];
        let valueY = coordinatesArrays[i][1];

        if (valueX.length !== 0)
        {
            if (valueX.includes(','))
            {
                valueX =  valueX.replace(',', '.');
            }

            if (isNaN(valueX) === true)
            {
                uniques.clear();
                uniques = null;

                if (type === 1)
                {
                    coordinatesStringErrorHint.innerHTML = "Nevalidná hodnota vo výraze!";
                    return false;
                }

                const char = String.fromCharCode(65);
                coordinatesCsvErrorHint.innerHTML = "Nevalidná hodnota na súradnici: " + char + (i+1);

                return false;
            }
        }

        if (valueY.length !== 0)
        {
            if (valueY.includes(','))
            {
                valueY =  valueY.replace(',', '.');
            }

            if (isNaN(valueY) === true)
            {
                uniques.clear();
                uniques = null;

                if (type === 1)
                {
                    coordinatesStringErrorHint.innerHTML = "Nevalidná hodnota vo výraze!";
                    return false;
                }

                const char = String.fromCharCode(66);
                coordinatesCsvErrorHint.innerHTML = "Nevalidná hodnota na súradnici: " + char + (i+1);

                return false;
            }
        }



        uniques.add(valueX);

    }


    if (coordinatesArrays.length !== uniques.size)
    {
        if (type === 1) {
            coordinatesStringErrorHint.innerHTML = "Duplikát X!";
        }

        if (type === 2) {
            coordinatesCsvErrorHint.innerHTML = "Duplikát X!";
        }

        return false;
    }
    else
    {
        coordinatesStringErrorHint.innerHTML = "";
        coordinatesCsvErrorHint.innerHTML = "";
    }

    array = coordinatesArrays;


    return true;

}



function logaritmic()
{

    let arr = null;
    let data =[];

    if (getById("nodes-string-input").value.length !== 0)
    {
        arr = document.getElementById("nodes-string-input").value.match(/-?\d+(\.\d+)?/g);

        let j = 0;
        for (let i = 0; i < arr.length; i += 2)
        {
            data[j] = [2];
            data[j][0] = parseFloat(arr[i]);
            data[j][1] = parseFloat(arr[i + 1]);
            ++j;
        }
    }

    if (getById("file-input").files.length !== 0)
    {
        for (let i = 0; i < array.length; ++i)
        {
            data[i] = [2];
            data[i][0] = parseFloat(array[i][0]);
            data[i][1] = parseFloat(array[i][1].replace(",","."));

        }
    }



points = data;



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

    document.getElementById("result-input").value = "f(x) = " + fun.replace("log","ln");;

graph();
    totalError(data);

}


function linear()
{


    let arr = null;
    let data =[];

    if (getById("nodes-string-input").value.length !== 0)
    {
        arr = document.getElementById("nodes-string-input").value.match(/-?\d+(\.\d+)?/g);

        let j = 0;
        for (let i = 0; i < arr.length; i += 2)
        {
            data[j] = [2];
            data[j][0] = parseFloat(arr[i]);
            data[j][1] = parseFloat(arr[i + 1]);
            ++j;
        }
    }

    if (getById("file-input").files.length !== 0)
    {
        for (let i = 0; i < array.length; ++i)
        {
            data[i] = [2];
            data[i][0] = parseFloat(array[i][0]);
            data[i][1] = parseFloat(array[i][1].replace(",","."));

        }
    }

    points = data;


    let coeficients = [];


    coeficients.push(data.length);

    let sum_x = 0;
    for(let i = 0; i < data.length; ++i)
    {
        sum_x += (1 *data[i][0]);
    }

    console.log(sum_x);

    coeficients.push(sum_x);


    let sum_y = 0;
    for(let i = 0; i < data.length; ++i)
    {
        sum_y += (1 *data[i][1]);
    }

    console.log(sum_y);

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

    document.getElementById("result-input").value = "f(x) = " + fun;

    graph();
    totalError(data);

}


function cramer(option, coeficients) {

    let A = coeficients[0] * coeficients[3] -  coeficients[1] * coeficients[1];



    let A1 = coeficients[2] * coeficients[3] -  coeficients[4] * coeficients[1];


    let A2 = coeficients[0] * coeficients[4] -  coeficients[2] * coeficients[1];

    switch (option)
    {
        case 1:
            return "" + (A1 / A).toFixed(8) + " +" + (A2 / A).toFixed(8) + "x";

        case 2:
            return "" +(A1 / A).toFixed(8) + " +" + (A2 / A).toFixed(8) + "log(x)";

    }



}


function totalError(data)
{
    let e = 0;
    const n = data.length;

    const parsedEquation = math.parse(fun);

    for (let i = 0; i < n; ++i)
    {
        const x = data[i][0];
        const y = data[i][1];

        const fx =  math.evaluate(parsedEquation.toString(), { x: x});

        e += Math.pow(fx - y,2);
        console.log("e " + e);

    }

    e /=n;

    e = Math.sqrt(e);

    getById("result-error-input").value = e;
}


function spline()
{
    if ( !isValid())
    {
        return;
    }

    let dk = [];

    for (let i = 0; i < data.length - 1; ++i)
    {
        dk[i] = (data[i + 1][1] - data[i][1]) / (data[i + 1][0] - data[i][0]);
    }



    let Sx = [data.length - 1];

    for (let i = 0; i < data.length - 1; ++i)
    {
        Sx[i] = "";
        Sx[i] += data[i][1];
        if (dk[i] > 0)
        {
            Sx[i] += "+";
        }
        Sx[i] += dk[i];

        if (data[i][0] > 0)
        {
            Sx[i] += "(x-" + data[i][0]+ ")";
        } else
        {
            Sx[i] += "(x+" + ((-1) * data[i][0]) + ")";
        }
    }

    for(var i = 0; i < Sx.length; i++)
    {
        console.log(Sx[i]);
    }


}



/////////////////////////////////////////Aproximation & interpolation///////////////////////////////////////////////////////////////






/////////////////////////////Integration//////////////////////////////////////////////////////////////////////////////



function validateSubintervals()
{
    const subIntervalsInputElement = getById("subintervals-input");
    const subIntervalsInputErrorHint = getById("subintervals-value-hint-error");


    if (subIntervalsInputElement.value.length === 0)
    {
        subIntervalsInputErrorHint.innerHTML = "Prázdne pole!"
        return false;
    }else
    {
        subIntervalsInputErrorHint.innerHTML = "";
    }


    function isPositiveInteger(n)
    {
        return n >>> 0 === parseFloat(n);
    }


    if (!isPositiveInteger(subIntervalsInputElement.value))
    {
        subIntervalsInputErrorHint.innerHTML = "Nevalidná hodnota!"
        return false;
    }else {
        subIntervalsInputErrorHint.innerHTML = "";
    }

    if (subIntervalsInputElement.value  <= 0 || subIntervalsInputElement.value > 100)
    {
        subIntervalsInputErrorHint.innerHTML = "Hodnota mimo intervalu!"
        return false;
    } else {
        subIntervalsInputErrorHint.innerHTML = "";
    }

    return true;


}
function trapezoid()
{
    if (!validateFunction() || !validateBounds() || !validateSubintervals() )
    {
        return false;
    }


    resultValueErrorHintElement.innerHTML = "";

    let a = parseFloat(lowerValueInputElement.value);
    let b = parseFloat(upperValueInputElement.value);


    const n = parseFloat(subintervalsValueInputElement.value);

    const h = (b - a) / n;

    const parsedEquation = math.parse(fun);


    let sum = 0;
    let part = 1 * a;

    for (let i = 0; i <= n; ++i)
    {

        if (i == 0)
        {
            let fx = math.evaluate(parsedEquation.toString(), { x: a});
            sum += fx;
            continue;
        }

        if (i == n)
        {
            let fx = math.evaluate(parsedEquation.toString(), { x: b});
            sum += fx;
            continue;
        }

        part += 1 * h;

        let fx = 2 * math.evaluate(parsedEquation.toString(), { x: part })
        sum = sum + fx;


    }


    const result = sum * h/2;

    resultValueInputElement.value = "Hodnota aproximačného integrálu: " + result.toFixed(6);


     inputs =[];

    inputs[0] = [2];


    inputs[0][0] = "Funkcia:";
    inputs[0][1] = fun;

    inputs[1] = [2];
    inputs[1][0] = "Dolná hranica:";
    inputs[1][1] = lowerValueInputElement.value;


    inputs[2] = [2];
    inputs[2][0] = "Horná hranica:";
    inputs[2][1] = upperValueInputElement.value;


    inputs[3] = [2];
    inputs[3][0] = "Počet dielikov:";
    inputs[3][1] = n;


    inputs[4] = [2];
    inputs[4][0] = resultValueInputElement.value;


}

function simpson()
{

    if (!validateFunction() || !validateSubintervals() || !validateBounds())
    {
        return false;
    }


    resultValueErrorHintElement.innerHTML = "";



    let a = parseFloat(lowerValueInputElement.value);
    let b = parseFloat(upperValueInputElement.value);


    const n = parseFloat(subintervalsValueInputElement.value);

    const h = (b - a) / n;

    const parsedEquation = math.parse(fun);


    let sum = 0;
    let part = 1 * a;

    for (let i = 0; i <= n; ++i)
    {

        if (i == 0)
        {
            sum += math.evaluate(parsedEquation.toString(), { x: a});
            continue;
        }

        if (i == n)
        {
            sum += math.evaluate(parsedEquation.toString(), { x: b});
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

    resultValueInputElement.value = "Hodnota aproximačného integrálu: " + result.toFixed(6);

     inputs =[];

    inputs[0] = [2];


    inputs[0][0] = "Funkcia:";
    inputs[0][1] = fun;

    inputs[1] = [2];
    inputs[1][0] = "Dolná hranica:";
    inputs[1][1] = lowerValueInputElement.value;


    inputs[2] = [2];
    inputs[2][0] = "Horná hranica:";
    inputs[2][1] = upperValueInputElement.value;


    inputs[3] = [2];
    inputs[3][0] = "Počet dielikov:";
    inputs[3][1] = n;


    inputs[4] = [2];
    inputs[4][0] = resultValueInputElement.value;




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

////////////////////////////////////////////////////////////////////////////////////////////

function checkUsername(userName)
{
    const errorHint = getById("username-hint-error");

    if (userName.length === 0)
    {
        getById("submit-button").disabled = true;
        errorHint.innerHTML = "";
        return;
    }

    const xhttp = new XMLHttpRequest();


    xhttp.onload = function()
    {
        if (xhttp.status === 404)
        {
            getById("submit-button").disabled = true;
            errorHint.innerHTML ="Nenájdený!";
        }


        if (xhttp.status === 200)
        {
            getById("submit-button").disabled = false;
            errorHint.innerHTML ="Nájdený!";
        }
    }




    let url = "/admin/employee/" + userName + "/check-username";

    xhttp.open("GET", url, true);


    xhttp.send();
}

function clic()
{
    const fileInputElement = getById("file-input");
    fileInputElement.click();
}



function validateStudentsCsv()
{
    const studentsCsvHintError = getById("students-csv-hint-error");

    for (let i = 1; i < array.length; ++i)
    {

        let surname = array[i][0];

        if (surname === null || surname.length === 0)
        {
            const char = String.fromCharCode(65);
            const dig = i + 1;
            studentsCsvHintError.innerHTML = "Prázdna bunka!: "  + (char + dig);
            return false;
        } else {
            studentsCsvHintError.innerHTML = "";
        }

        surname = surname.trim();


        if (surname.length > 50)
        {

            const char = String.fromCharCode(65);
            const dig = i + 1;
            studentsCsvHintError.innerHTML = "Text je dlhší než 50 znakov!: "  + (char + dig);
            return false;
        }else {
            studentsCsvHintError.innerHTML = "";
        }


        let name = array[i][1];

        if (name === null || name.length === 0)
        {
            const char = String.fromCharCode(66);
            const dig = i + 1;
            studentsCsvHintError.innerHTML = "Prázdna bunka!: " + (char + dig);
            return false;
        }else
        {
            studentsCsvHintError.innerHTML = "";
        }

        name = name.trim();

        if (name.length > 50)
        {
            const char = String.fromCharCode(66);
            const dig = i + 1;
            studentsCsvHintError.innerHTML = "Text je dlhší než 50 znakov!: "  + (char + dig);
            return false;
        }else {
            studentsCsvHintError.innerHTML = "";
        }


        const pin = array[i][2].trim();
        const pinRegex = /^\d{6}$/;

        if (pin === null || pin.length === 0)
        {
            const char = String.fromCharCode(67);
            const dig = i + 1;
            studentsCsvHintError.innerHTML = "Prázdna bunka!: "  + (char + dig);
            return false;
        }else {
            studentsCsvHintError.innerHTML = "";
        }



        if (!pinRegex.test(pin))
        {
            const char = String.fromCharCode(67);
            const dig = i + 1;
            studentsCsvHintError.innerHTML = "Nevalidná hodnota!: "  + (char + dig);
            return false;

        }else {
            studentsCsvHintError.innerHTML = "";
        }



        const email = array[i][3].trim();
        const emailRegex = /^[a-zA-Z0-9._%+-]+@stud\.uniza\.sk$/;

        if (email === null || pin.length === 0)
        {
            const char = String.fromCharCode(68);
            const dig = i + 1;
            studentsCsvHintError.innerHTML = "Prázdna bunka!: "  + (char + dig);
            return false;
        }else
        {
            studentsCsvHintError.innerHTML = "";
        }



        if (!emailRegex.test(email))
        {
            const char = String.fromCharCode(68);
            const dig = i + 1;
            studentsCsvHintError.innerHTML = "Nevalidná hodnota!: "  + (char + dig);
            return false;
        }else {
            studentsCsvHintError.innerHTML = "";
        }


    }

    const emails = new Set();
    const pins = new Set();

    for (let i = 1; i < array.length; ++i)
    {
        emails.add(array[i][3]);
        pins.add(array[i][2]);
    }

    if (emails.size !== array.length - 1)
    {
        const char = String.fromCharCode(68);
        studentsCsvHintError.innerHTML = "Duplicitná hodnota v stlpci: "  + char;
        return false;
    }


    if (pins.size !== array.length - 1)
    {
        const char = String.fromCharCode(67);
        studentsCsvHintError.innerHTML = "Duplicitná hodnota v stlpci: "  + char;
        return false;
    }



    return true;


}


function updateSystemAbsents()
{
    alert(45);
    const errorHint = getById("absents-hint-error");
    const absentsInputElement = getById("absents-input");
    const absentsButton = getById("absents-button");

    if (absentsInputElement.value.length === 0)
    {
        errorHint.innerHTML = "Nezadaná hodnota!";
        return;
    }


    const absents = Number(absentsInputElement.value)

    if (absents === NaN)
    {
        errorHint.innerHTML = "Hodnota nieje číslo!";
        return;
    }



    if (!Number.isInteger(absents))
    {
        errorHint.innerHTML = "Hodnota nieje celé číslo!";
        return;
    }


    if (absents <= 0 || absents > 13)
    {
        errorHint.innerHTML = "Hodnota mimo intervalu!";
        return;
    }

    errorHint.innerHTML = "";


    const xhttp = new XMLHttpRequest();


    xhttp.onload = function()
    {

        if (xhttp.status === 200)
        {
            getById("absents-count").innerText = absents;
            absentsInputElement.value = "";
        }
    }

    let data = {
        absents: absentsInputElement.value
    };




    let url = "/admin/system/update/absents";

    xhttp.open("PATCH", url, true);
    xhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");

    xhttp.send(JSON.stringify(data));
}

function updateTeacher()
{

    const usernameInputElement = getById("username-input");

    const xhttp = new XMLHttpRequest();


    xhttp.onload = function()
    {
        if (xhttp.status === 404 || xhttp.status === 400)
        {
            errorHint.innerHTML ="Nezmenený!";
        }



        if (xhttp.status === 200)
        {
            usernameInputElement.value = "";
        }
    }


    let data = {
        username: usernameInputElement.value
    };




    let url = "/admin/system/update/teacher";

    xhttp.open("PATCH", url, true);
    xhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");

    xhttp.send(JSON.stringify(data));
}

function updateSystemDate()
{
    const errorHint = getById("date-hint-error");
    const dateInputElement = getById("date-input");
    const dateButton = getById("date-button");

    if (dateInputElement.value.length === 0)
    {
        errorHint.innerHTML = "Prázdne pole!";
        return;
    }



    if (!validateDate(dateInputElement.value))
    {
       return;
    }



    const xhttp = new XMLHttpRequest();


    xhttp.onload = function()
    {

        if (xhttp.status === 200)
        {
            getById("date-value").innerText =dateInputElement.value;
            dateInputElement.value = "";
        }
    }


    let data = {
        date: dateInputElement.value
    };




    let url = "/admin/system/update/date";

    xhttp.open("PATCH", url, true);
    xhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");

    xhttp.send(JSON.stringify(data));
}



function validateDate(dateString)
{
    const dateErrorHint = getById("date-hint-error");

    const regex = /^\d{2}\.\d{2}\.\d{4} \d{2}:\d{2}$/;

    if (!regex.test(dateString)) {
        dateErrorHint.innerHTML = "Dátum v nesprávnom formáte!";
        return false;
    } else {
        dateErrorHint.innerHTML = "";
    }


    // Parse the date string into a Date object
    var parts = dateString.split(' ');
    var dateParts = parts[0].split('.');
    var timeParts = parts[1].split(':');
    var year = parseInt(dateParts[2], 10);
    var month = parseInt(dateParts[1], 10) - 1; // Months are zero-based
    var day = parseInt(dateParts[0], 10);
    var hours = parseInt(timeParts[0], 10);
    var minutes = parseInt(timeParts[1], 10);


    const date = new Date(year, month, day, hours, minutes);


    const success = date.getFullYear() === year && date.getMonth() === month && date.getDate() === day && date.getHours() === hours && date.getMinutes() === minutes && !isNaN(date.getTime())

    if (!success)
    {
        dateErrorHint.innerHTML = "Nevalidný dátum";
        return false;
    } else {
        dateErrorHint.innerHTML = "";
    }


    const currentDateTime = new Date();





    if (date <= currentDateTime)
    {

        dateErrorHint.innerHTML = "Dátum predchádza dnešný!";
        return false;
    } else {
        dateErrorHint.innerHTML = "";

    }

    return true;
}





/////////////////////////////////////////////////////page-MATERIALS///////////////////////////////////


function isEmpty(element, hintElement)
{
    if (element.value.length === 0)
    {
        hintElement.innerHTML = "Pole je prázdne!";
        return true;
    } else {
        hintElement.innerHTML = "";
    }

    return false;
}


function stringSizeInInterval(element, hintElement, min, max)
{
    if (element.value.length >= min && element.value.length <= max)
    {
        hintElement.innerHTML = "";
    } else {
        hintElement.innerHTML = "Dĺžka mimo intervalu!";
    }
}


function numberSizeInInterval(element, hintElement)
{
    if (element >= min && element <= max)
    {
        hintElement.innerHTML = "";
    } else {
        hintElement.innerHTML = "Dĺžka mimo intervalu!";
    }
}


function checkFileType(element, hintElement)
{
    if (!element.files[0])
    {
        hintElement.innerHTML = "Vložte súbor!";
    }
    const MAX_FILE_SIZE = parseInt(element.getAttribute('size'), 10);

    const ALLOWED_TYPES = [
        'application/pdf',        // PDF
        'application/msword',     // DOC
        'application/vnd.openxmlformats-officedocument.wordprocessingml.document', // DOCX
        'image/png',              // PNG
        'image/jpeg',             // JPEG
        'image/jpg',              // JPG
        'text/csv',               // CSV
        'application/vnd.ms-excel', // CSV alternative (Excel)
        'application/vnd.ms-powerpoint', // PPTX
        'application/vnd.openxmlformats-officedocument.presentationml.presentation' // PPTX
    ];

    const file = element.files[0];

    if (file.size <= MAX_FILE_SIZE)
    {
        hintElement.innerHTML = "";
    } else {
        hintElement.innerHTML = "Veľkosť súboru presahuje limit!";
        return false;
    }


    if (ALLOWED_TYPES.includes(file.type))
    {
        hintElement.innerHTML = "";
    } else {
        hintElement.innerHTML = "Nepodporovaný typ súboru!";
        return false;
    }


return true;
}








//////////////sendStudentsCsv//////////////////////////////////////////////

function uploadStudentsCsv()
{
    if (getById("file-input").files.length === 0)
    {
        getById("students-csv-hint-error").innerHTML = "Poľe je prázdne!";
        return;
    }

    if (!validateStudentsCsv()) {
        alert(5);
        return;
    }

    getById("students-csv-hint-error").innerHTML = "";


    const xhttp = new XMLHttpRequest();

    xhttp.onload = function()
    {
        if (xhttp.status === 200)
        {
           alert("OK");

        } else {
            alert("KO");
        }


    }

    let url = "/admin/upload/file/csv";

    xhttp.open("POST", url, true);


    const formData = new FormData();
    const fileInput = document.getElementById('file-input');

    formData.append('file', fileInput.files[0]);



    // Sending the JSON object as the request body
    xhttp.send(formData);
}


function setFileName()
{
    const inputFileNameElement = getById("text-input");
    const inputFileElement = getById("file-input");

    inputFileNameElement.value = inputFileElement.files[0].name;
}







$(function() {
    var Accordion = function(el, multiple) {
        this.el = el || {};
        this.multiple = multiple || false;

        var links = this.el.find('.link');

        links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown)
    }

    Accordion.prototype.dropdown = function(e) {
        var $el = e.data.el;
        $this = $(this),
            $next = $this.next();

        $next.slideToggle();
        $this.parent().toggleClass('open');

        if (!e.data.multiple) {
            $el.find('.submenu').not($next).slideUp().parent().removeClass('open');
        };
    }

    var accordion = new Accordion($('#accordion'), false);
});





function openFile(name)
{
    const xhttp = new XMLHttpRequest();


    xhttp.onload = function()
    {

        if (xhttp.status === 200) {

            let decodedData = atob(xhttp.response);
            let uint8Array = new Uint8Array(decodedData.length);

            for (var i = 0; i < decodedData.length; i++) {
                uint8Array[i] = decodedData.charCodeAt(i);
            }

            let blob = new Blob([uint8Array], { type: xhttp.getResponseHeader("Content-Type") });
            let link = document.createElement('a');

            link.href = window.URL.createObjectURL(blob);

            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
        }


    }

    const url = "/person/material/file/" + name;

    xhttp.open("GET", url, true);
    xhttp.send();
}

function checkFile(name)
{
    let exists = false;
    const xhttp = new XMLHttpRequest();


    xhttp.onload = function()
    {
        const inputNameErrorHint = getById("name-input-error-hint");

        if (xhttp.status === 200)
        {
            inputNameErrorHint.innerHTML = "Súbor s týmto názvom už existuje!";
            exists = true;

        } else {
            inputNameErrorHint.innerHTML = "";
            exists = falses;
        }

    }

    const url = "/person/material/file/check-name/" + name;

    xhttp.open("GET", url, false);
    xhttp.send();

    return exists;
}

function deleteFile(name)
{
    const xhttp = new XMLHttpRequest();

    xhttp.onload = function()
    {

        const element = document.getElementById(name);
        element.remove();

    }

    const url = "/admin/file/delete/" + name;

    xhttp.open("DELETE", url, true);
    xhttp.send();
}


function uploadFile()
{


    const xhttp = new XMLHttpRequest();

    xhttp.onload = function()
    {

        if (xhttp.status === 200)
        {

            let row = document.createElement("tr");
            let body = document.getElementsByTagName("tbody")[0];



            row.setAttribute("id", getById("name-input").value);


            var nameCell = document.createElement("td");
            var nameSpan = document.createElement("span");
            nameSpan.textContent = document.getElementById("name-input").value;
            nameCell.appendChild(nameSpan);

            var descriptionCell = document.createElement("td");
            var descriptionSpan = document.createElement("span");
            descriptionSpan.textContent = document.getElementById("file-description").value;
            descriptionCell.appendChild(descriptionSpan);

            var showButtonCell = document.createElement("td");
            var showButton = document.createElement("button");
            showButton.setAttribute("type", "button");
            showButton.setAttribute("class", "btn btn-success btn-icon-text");
            showButton.setAttribute("onclick", "openFile('" + xhttp.responseText + "')");
            showButton.innerHTML = '<i class="mdi mdi-upload btn-icon-prepend"></i>Zobraziť';
            showButtonCell.appendChild(showButton);

            row.appendChild(nameCell);
            row.appendChild(descriptionCell);
            row.appendChild(showButtonCell);
            body.appendChild(row);

            let deleteButtonCell = document.createElement("td");
            var deleteButton = document.createElement("button");
            deleteButton.setAttribute("type", "button");
            deleteButton.setAttribute("class", "btn btn-danger btn-icon-text");
            deleteButton.setAttribute("onclick", "deleteFile('" + xhttp.responseText + "')");
            deleteButton.innerHTML = '<i class="mdi mdi-upload btn-icon-prepend"></i>Odstrániť';
            deleteButtonCell.appendChild(deleteButton);

            row.appendChild(deleteButtonCell);




        }

    }

    const url = "/admin/material/upload";

    let formData = new FormData();
    formData.append("name", document.getElementById("name-input").value);
    formData.append("description", document.getElementById("file-description").value);
    formData.append("data", document.getElementById("file-input").files[0]);


    xhttp.open("POST", url, true);

    xhttp.send(formData);
}


function canSubmit()
{
    const nameInputElement = getById("name-input");
    const nameInputErrorHint = getById("name-input-error-hint");

    const fileInputElement = getById("file-input");
    const fileInputElementErrorHint = getById("file-input-error-hint");


    if (isEmpty(nameInputElement, nameInputErrorHint))
    {
        return ;
    }

    if (checkFile(nameInputElement.value))
    {
        alert("EXISTUJE");
        return;
    } else {
        alert("NEEXISTUJE");
    }


    if (checkFileType(fileInputElement, fileInputElementErrorHint))
    {

        uploadFile();
    }
}


function clickOnInput() {
    const element = document.getElementById("file-input");

    element.click();
}




