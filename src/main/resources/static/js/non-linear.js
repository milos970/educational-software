
let data = [];

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


function display() {
    const functionString = "10cos(x - 1) - x^2 + 2x - 1"; 


const xValues = [];


for (let i = -10; i < 10; i += 0.1) 
{
    xValues.push(i);
}



var parsedEquation = math.parse(functionString);
const yValues = xValues.map(x => math.evaluate(parsedEquation.toString(), { x: x }));


const trace = {
    x: xValues,
    y: yValues,
    mode: 'lines',
    name: 'Function',
};


const layout = {
    title: 'Plot of the Function',
    xaxis: { title: 'x' },
    yaxis: { title: 'y' }
};

// Plot the function using Plotly
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

function validate() 
{
    
    var equation = document.getElementById("equation").value;
    var element = document.getElementById("error");
    try {
        
        var expression = math.parse(equation);
        math.evaluate(expression.toString(), { x: 0 });
    
        element.innerText = "";
        
    } catch (error) {
        
        element.innerText = "Nevalidný výraz!";
        return false;
    }

    return true;
}



function newtonMethod() {

    if (!validate()) {
        return;
    }
    var equation = document.getElementById("equation").value;
    var tolerance = document.getElementById("tolerance").value;
    var initial = document.getElementById("initial").value;



    equation = "10cos(x - 1) - x^2 + 2x - 1";
    tolerance = "0.000001";
    iterations = 100;
    initial = 0;

    var current = initial;
    var parsedEquation = math.parse(equation);
    var derivative = math.derivative(parsedEquation, 'x');

    let xvalues = [];
    let yvalues = [];

    let data = [];
    //initialisePlot();

    let colors = ["red", "blue", "green"];


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

       
        //addLine(current,fx,next,0,colors[i],(i+1));
        

        if (Math.abs(next - current) <= tolerance) {
            
            break;
        }

        current = next;


    }

    
    

    
    let headers =["k", "xk", "|xk - xk-1|"];

    initializeTable(headers,data);
    


   
  
}

function initializeTable(headers,data) {
    
    let table = document.getElementById("table");

    var header = table.createTHead();
    var row = header.insertRow(0);
    for (let i = 0; i < headers.length; ++i) {
        var cell = row.insertCell(i);
        cell.innerHTML = headers[i];
    }

    let from = 0;

    if (data.length > 8) 
    {
        from = data.length - 8;
    }


    for (let i = from; i < data.length; ++i) 
    {
        let row = table.insertRow(-1);
        for (let j = 0; j < data[0].length; ++j) 
        {
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



let myChart;

function initialisePlot() {
    

    var ctx = document.getElementById('myChart').getContext('2d');

    // Chart configuration
    var options = {
        scales: {
            x: {
                type: 'linear',
                position: 'bottom'
            },
            y: {
                type: 'linear',
                position: 'left'
            }
        }
    };

    // Initial data with no points
    var initialData = {
        datasets: []
    };

    // Create the chart with initial data
     myChart = new Chart(ctx, {
        type: 'scatter', // Use scatter plot type
        data: initialData,
        options: options
    });

    
    

    
}

function addLine(x1, y1, x2, y2, lineColor, lineName) {
    // Create a new dataset for the line
    var newDataset = {
        data: [{ x: x1, y: y1 }, { x: x2, y: y2 }],
        borderColor: lineColor,
        backgroundColor: 'transparent',
        type: 'line',
        label: lineName
    };

    // Add the new dataset to the chart data
    myChart.data.datasets.push(newDataset);

    // Update the chart
    myChart.update();
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
    b=2.4;
    prev = a;

    var parsedEquation = math.parse(equation);

    for (let k = 0; k < iterations; ++k) 
    {

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

        if (Math.abs(xk - prev)  <= tolerance) {
            //window.alert(xk);
            break;
        }

        prev = xk;
    }

    let headers =["k", "ak", "bk", "xk", "|xk - xk-1|"];

    initializeTable(headers,data);



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

    let headers =["k", "ak", "bk", "xk", "(bk - ak)/2"];

    initializeTable(headers,data);


   


}


