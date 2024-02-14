
const nodes = document.getElementById("nodes");
const result = document.getElementById("result");
const err = document.getElementById("error");

function isValid() 
{
    const regex = /^\[\((?:-?\d*\.?\d+),(-?\d*\.?\d+)\)(?:,\((?:-?\d*\.?\d+),(-?\d*\.?\d+)\))*\]$/;
    
    
    let element = document.getElementById("error");


    if (nodes.value.length === 0) 
    {
        element.innerText = "Prázdne pole!";
        return false;
    }


    if (regex.test(nodes.value)) 
    {
        element.innerText = "";
    } else {
        element.innerText = "Nevalidný výraz!";
        return false;
    }


    let data = parse();

    if (data.length === 1) 
    {
        element.innerText = "Málo vstupných bodov!";
        return false;
    } else {
        element.innerText = "";
    }


    if (data.length === 100) 
    {
        element.innerText = "Veľa vstupných údajov!";
        return false;
    } else {
        element.innerText = "";
    }


    const uniques = new Set();

    for (let i = 0; i < data.length; ++i) 
    {
        uniques.add(data[i][0]);
    }

    if (data.length !== uniques.size) 
    {
        element.innerText = "Duplikatný vstupný údaj!";
        return false;
    } else {
        element.innerText = "";
    }

    return true;
}


function parse() 
{
    let matches = nodes.value.match(/-?\d+(\.\d+)?/g);


    let data = [];
    let j = 0;
    for (let i = 0; i < matches.length; i += 2) 
    {
        data[j] = [2];

        data[j][0] = matches[i];

        data[j][1] = matches[i + 1];

        ++j;
    }


    return data;
}


function display(fun) 
{
    let plot = document.getElementById("plot");
    plot.style.display = "block";

    

    
    const xValues = [];


    let start = -10;
    let finish = 100;
    let step = 0.1;


    for (let i = start; i < finish; i += step) {
        xValues.push(i);
    }



    let parsedEquation = math.parse(fun);
    const yValues = xValues.map(x => math.evaluate(parsedEquation.toString(), { x: x }));


    const trace = {
        x: xValues,
        y: yValues,
        mode: 'lines',
        name: 'Function',
    };


    const layout = {
        title: "Funkcia",
        xaxis: { title: 'x' },
        yaxis: { title: 'f(x)' }
    };


    Plotly.newPlot('plot', [trace], layout);

    
}


function langrangeInterpolate() 
{

    if ( !isValid()) 
    {
        return;
    }
    
    let matches = nodes.value.match(/-?\d+(\.\d+)?/g);

    let arr = matches;
    let equation = "";
    let first = true;
    
    for (let i = 0; i < arr.length; i+=2) 
    {
        let part = "";
        let menovatel = 1;
        for (let k = 0; k < arr.length; k+=2) 
        {
            
                if (i == k) 
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

    result.value = equation;

    display(equation);


}

function leastSquares() {
    if (!isValid()) {
        return;
    }

    if (document.getElementById('radio1').checked) {
        
        linear();
    }

    if (document.getElementById('radio2').checked) {
        logaritmic();
    }

    



}



function logaritmic() 
{

    

    let data = parse();

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

    result.value = equation;

    display(equation);

    error(equation, data);

}


function linear() 
{
    

    let data = parse();

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

    

    result.value = equation;

    display(equation);
    
    error(equation,data);
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


function error(equation,data) 
{
    let e = 0;
    let parsedEquation = math.parse(equation);
    for (let i = 0; i < data.length; ++i) 
    {
        let fx = math.evaluate(parsedEquation.toString(), { x: data[i][0] });
        let yi = data[i][1];
        
        e += Math.pow(fx - yi, 2);
        
    }

    
    e = e/data.length;
   
    e = Math.sqrt(e);

    
    err.value = e;
}



function newtonInterpolate() 
{
    if ( !isValid()) 
    {
        return;
    }

    var equation = "";

    let data = parse();
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


    

    result.value = equation;

    display(equation);



    function dividedDifference(i, j) 
    {

        if (j === 0) {
            return data[i][1];
        } else {
            return (dividedDifference(i + 1, j - 1) - dividedDifference(i, j - 1)) / (data[i + j][0] - data[i][0]);
        }
    }
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






