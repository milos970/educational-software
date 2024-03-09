

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


function selectedMethod(value)
{
    const dropDownButton = document.getElementById("dropdownMenuButton1");

    switch (value)
    {
        case 1:
            dropDownButton.innerHTML = "Newtnová metóda";
            break;
        case 2:
            dropDownButton.innerHTML = "Regula falsi";
            break;
        case 3:
            dropDownButton.innerHTML = "Bisekcia";
            break;
    }



}

function selectedTolerance(value)
{
    const dropDownButton = document.getElementById("dropdownMenuOutlineButton1");

    switch (value)
    {
        case 1:
            dropDownButton.innerHTML = "0.001";
            break;
        case 2:
            dropDownButton.innerHTML = "0.0001";
            break;
        case 3:
            dropDownButton.innerHTML = "0.00001";
            break;
        case 4:
            dropDownButton.innerHTML = "0.000001";
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

    const table = document.getElementById("table");

    let header = table.createTHead();
    let row = header.insertRow(0);
    for (let i = 0; i < data[0].length; ++i) {
        let cell = row.insertCell(i);
        cell.innerHTML = data[0][i];
    }

    let from = 1;

    if (data.length > 6) {
        from = data.length - 6;
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


function newtonMethod()
{
    const iterations = 1000;

    const initialValueInput = document.getElementById("initial-value-input");


    let current = initialValueInput.value;
    let parsedEquation = math.parse(fun);
    let derivative = math.derivative(parsedEquation, 'x');

    let data = [];

    data[0] = [3];
    data[0][0] = "k";
    data[0][1] = "x";
    data[0][2] = "chyba";

    let fx = math.evaluate(parsedEquation.toString(), { x: current });
    let derfx = math.evaluate(derivative.toString(), { x: current });
    let next = current - (fx / derfx);

    const tolerance = 0.001;
    data[1] = [3];
    data[1][0] = "0";
    data[1][1] = current;
    data[1][2] = Math.abs(next - current).toFixed(6);
    current = next;
    for (let i = 2; i < iterations; ++i)
    {

        data[i] = [3];
        fx = math.evaluate(parsedEquation.toString(), { x: current });
        derfx = math.evaluate(derivative.toString(), { x: current });

        if (derfx === 0)
        {
            //progressError.value = "Delenie hodnotou 0 vo výpočte!"
            return;
        }


        next = current - (fx / derfx);
        data[i][0] = i - 1;
        data[i][1] = next.toFixed(6);
        let error = Math.abs(next - current);
        data[i][2] = error.toFixed(6);



        if (error <= tolerance)
        {

            break;
        }

        current = next;
    }


   // initializeTable(data);
    graph(data);

}


function display()
{
    //graph(-100,100, 1);
    //graph(Number.parseInt(dh.value),Number.parseInt(hh.value), Number.parseInt(stp));

}



function graph(data)
{
    let xValues = [];
    let yValues = [];

    for (let i = -100; i <= 100; ++i)
    {
        xValues.push(i);
        let expression = math.parse(fun);
        yValues.push(math.evaluate(expression.toString(), { x: i }));


    }
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

    var options = {
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero: true
                },
                gridLines: {
                    color: "rgba(204, 204, 204,0.1)"
                }
            }],
            xAxes: [{
                gridLines: {
                    color: "rgba(204, 204, 204,0.1)"
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
            options: options
        });
    }

}



























