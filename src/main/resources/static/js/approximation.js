
const nodes = document.getElementById("nodes-input");
const result = document.getElementById("result");
const err = document.getElementById("error");
const methods = document.getElementById("methods");
const functions = document.getElementById("least-squares-type");


function display()
{
    graph(-100,100, 1);
    //graph(Number.parseInt(dh.value),Number.parseInt(hh.value), Number.parseInt(stp));
}

function graph(dh,hh,step)
{
    var plot = document.getElementById("plot");
    plot.style.display = "block";




    const xValues = [];


    for (let i = dh; i < hh; i += 0.1)
    {
        xValues.push(i);
    }



    var parsedEquation = result.value;
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
        Plotly.toImage(gd, {format: 'png', height: 600, width: 500})
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

function calculate()
{
    switch(methods.value)
    {
        case "1":
            langrangeInterpolate();
            break;
        case "2":
            newtonInterpolate();
            break;
        case "3":
            leastSquares();
            break;
    }
}

function setMethod()
{

    if (methods.value === "3")
    {
        document.getElementById('select-div').style.display="block";
        title.innerHTML = methods.options[methods.selectedIndex].text;
    } else {
        document.getElementById('select-div').style.display="none";
        title.innerHTML = methods.options[methods.selectedIndex].text;
    }
}



function isValid() 
{
    const regex = /^\[\((?:-?\d*\.?\d+),(-?\d*\.?\d+)\)(?:,\((?:-?\d*\.?\d+),(-?\d*\.?\d+)\))*\]$/;
    
    let element = document.getElementById("nodes-error");


    if (nodes.value.length === 0) 
    {
        element.innerHTML = "Prázdne pole!";
        return false;
    }


    if (regex.test(nodes.value)) 
    {
        element.innerHTML = "";
    } else {
        element.innerHTML = "Nevalidný výraz!";
        return false;
    }


    let data = parse();

    if (data.length === 1) 
    {
        element.innerHTML = "Málo vstupných bodov!";
        return false;
    } else {
        element.innerHTML = "";
    }


    if (data.length === 100) 
    {
        element.innerHTML = "Veľa vstupných údajov!";
        return false;
    } else {
        element.innerHTML = "";
    }


    const uniques = new Set();

    for (let i = 0; i < data.length; ++i) 
    {
        uniques.add(data[i][0]);
    }

    if (data.length !== uniques.size) 
    {
        element.innerHTML = "Duplikatný vstupný údaj!";
        return false;
    } else {
        element.innerHTML = "";
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

function parseCsvToArray()
{

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

    display();


}

function leastSquares() {


    if (functions.value === "1") {

        linear();
    }

    if (functions.value === "2") {
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

    display();

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

    display();
    
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

    
    //err.value = e.toFixed(6);;
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

    display();



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






