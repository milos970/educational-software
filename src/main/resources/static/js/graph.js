const dh = document.getElementById("dh");
const hh = document.getElementById("hh");
const equation = document.getElementById("equation");
const plot = document.getElementById("plot");

const equationError = document.getElementById("equation-error");
const dhError = document.getElementById("dh-error");
const hhError = document.getElementById("hh-error");
const resultError = document.getElementById("result-error");

const button = document.getElementById("button");


let validFunction = false;
let validLowerBound = false;
let validUpperBound = false;

const step = 1;


function validateFunction()
{
    validFunction = true;


    if (equation.value == null || equation.value === "")
    {
        equationError.innerHTML = "Prázdne poľe!";
        validFunction = false;
        return;
    } else {
        equationError.innerHTML = "";
    }

    equation.value = equation.value.toLowerCase();

    if (equation.value.indexOf("x") === -1)
    {
        equationError.innerHTML  = "Nevalidný výraz!";
        validFunction = false;
    } else {
        try
        {
            let expression = math.parse(equation.value);
            math.evaluate(expression.toString(), { x: 0 });
            equationError.innerHTML = "";

        } catch (error)
        {
            equationError.innerHTML  = "Nevalidný výraz!";
            validFunction = false;
        }
    }



}

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



function graph(dh,hh,step)
{

    plot.style.display = "block";


    const xValues = [];


    for (let i = dh; i < hh; i += step)
    {

        xValues.push(i);
    }


    const parsedEquation = math.parse(equation.value);

    const yValues =[xValues.length];

    let found = false;

    let prev = math.evaluate(parsedEquation.toString(), { x: xValues[0] });
    for (let i = 1; i < xValues.length; ++i)
    {
        let x = xValues[i];
        yValues[i] = math.evaluate(parsedEquation.toString(), { x: x });

        if (prev * yValues[i] < 0 || prev === 0 || yValues[i] ===0)
        {
            found = true;
        }

        prev = yValues[i];

    }

    if (!found)
    {
        resultError.innerHTML = "Koreň na intervale <" + dh.value + ";" + hh.value + "> neleží!";
    } else {
        resultError.innerHTML = "";
    }

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


function display()
{
    if ( validFunction&& validLowerBound && validUpperBound)
    {

        graph(Number.parseInt(dh.value),Number.parseInt(hh.value), Number.parseInt(step));
    }

}

