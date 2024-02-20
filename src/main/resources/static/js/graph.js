const dh = document.getElementById("dh");
const hh = document.getElementById("hh");
const stp = document.getElementById("step");
const equation = document.getElementById("equation");
const plot = document.getElementById("plot");
const result = document.getElementById("error-result");
function display()
{
    graph(Number.parseInt(dh.value),Number.parseInt(hh.value), Number.parseInt(stp));
}


function graph(dh,hh,step)
{

    plot.style.display = "block";


    const xValues = [];


    for (let i = dh; i < hh; i += 1)
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

        if (prev * yValues[i] < 0)
        {
            found = true;
        }

        prev = yValues[i];

    }

    if (!found)
    {
        result.innerHTML = "Koreň na intervale <" + dh.value + ";" + hh.value + "> neleží!";
    } else {
        result.innerHTML = "";
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