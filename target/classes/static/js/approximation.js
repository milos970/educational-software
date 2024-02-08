

function validate() 
{
    const regex =/^\[(\((\d+),\d+\))(?:,(?!\2)\((\d+),\d+\))*\]$/gm;
    
    var x = document.getElementById("equation").value;

    var element = document.getElementById("error");


    if (regex.test(x)) 
    {
        element.innerText = "";
    } else {
        element.innerText = "Nesprávny formát!";
        return;
    }


    let data = parse();


    const uniques = new Set();

    for (let i = 0; i < data.length; ++i) 
    {
        uniques.add(data[i][0]);
    }

    if (data.length !== uniques.size) 
    {
        element.innerText = "Nájdený duplikát!";
        return;
    } else {
        element.innerText = "";
    }

    return data;
}


function parse() 
{
    var eq = document.getElementById("equation").value;
    let matches = eq.match(/-?\d+(\.\d+)?/g);


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


function leastSquares() 
{
    let data = validate();

    linear(data);
    logaritmic(data);
}


function logaritmic(data) 
{
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

    var equation = cramer(2,coeficients);

    var res = document.getElementById("result");

    res.innerHTML = equation;

    error(equation, data);


}


function linear(data) 
{
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


    var equation = cramer(1,coeficients);
    var res = document.getElementById("result2");

    res.innerHTML = equation;
    error(equation, data);
}


function cramer(option, coeficients) {
   
    let A = coeficients[0] * coeficients[3] -  coeficients[1] * coeficients[1];

   

    let A1 = coeficients[2] * coeficients[3] -  coeficients[4] * coeficients[1];

    
    let A2 = coeficients[0] * coeficients[4] -  coeficients[2] * coeficients[1];

    switch (option) 
    {
        case 1:
            return "f(x) = " + (A1 / A).toFixed(3) + " +" + (A2 / A).toFixed(3) + "x";
        
        case 2:
            return "f(x) = " +(A1 / A).toFixed(3) + " +" + (A2 / A).toFixed(3) + "log(x)";
            
    }

    

}


function error(equation,data) 
{
    let e = 0;
    var parsedEquation = math.parse(equation);
    console.log(parsedEquation.toString());
    for (let i = 0; i < data.length; ++i) 
    {
        var fx = math.evaluate(parsedEquation.toString(), { x: data[i][0] });
        var yi = data[i][1];
        
        e += Math.pow(fx - yi, 2);
        
    }

    
    e = e/data.length;
   
    e = Math.sqrt(e);

    console.log("E = " + e);
}





    
    

    

function newtonInterpolate() 
{
    let data = validate();

    var equation = "";
    for (let i = 0; i < data.length; ++i) 
    {
        let cislo = dividedDifference(0,i);

        if (i == 0) 
        {
            if (cislo < 0) 
            {
                equation += "- " + ((-1) * cislo);
            } else {
                equation += cislo;
            }
            continue;
        }

        if (cislo > 0) 
        {
            equation += "+";
        }
        equation += cislo;
        
        for (let j = 0; j < i; ++j) 
        {
            if (data[j][0] < 0) 
            {
                equation += "(x + " + ((-1) * data[j][0]) + ")";
            } else {
                equation += "(x - " + data[j][0] + ")";
            }
        }
    }


    var res = document.getElementById("result");

    res.innerHTML = equation;



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
    let data = validate();

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


function langrangeInterpolate() 
{
    var eq = document.getElementById("equation").value;
    let matches = eq.match(/-?\d+(\.\d+)?/g);

    matches.forEach((element) => console.log(element));
    arr = matches;
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
                    part += "(x - " + arr[k] + ")";
                } else 
                {
                    part += "(x + " + ((-1) * (arr[k])) + ")";
                }
                

                menovatel *= (arr[i] - arr[k]);
                alert(menovatel);
        }

        menovatel = 1/ menovatel;
        

        

        if (first) 
        {
            part = (arr[i + 1] * menovatel) + part;
            equation += part;
            first = false;
        } else 
        {
            if (arr[i + 1] * menovatel > 0) 
            {
                part = "+" + (arr[i + 1] * menovatel) + part;
            } else 
            {
                part = (arr[i + 1] * menovatel) + part;
            }

            equation += part;
        }


    }

    var res = document.getElementById("result");

    res.innerHTML = equation;


}



