
function validate() 
{
    
    var fun = document.getElementById("function").value;
    var element = document.getElementById("error");
    try {
        
        var expression = math.parse(fun);
        math.evaluate(expression.toString(), { x: 0 });
    
        element.innerText = "";
        
    } catch (error) {
        
        element.innerText = "Nevalidný výraz!";
        return false;
    }

    return true;
}


function trapezoid() 
{
    if (!validate()) {
        return;
    }

    var fun = document.getElementById("function").value;
    var a = document.getElementById("a").value;
    var b = document.getElementById("b").value;

    var n = document.getElementById("n").value;

    var parsedEquation = math.parse(fun);

    
    var res = document.getElementById("result");
    var h = (b - a) / n;

    
    

    var result = math.evaluate(parsedEquation.toString(), { x: 0 });
    var result = result + math.evaluate(parsedEquation.toString(), { x: b - a });

    var sum = h;
    for (var i = 0; i < n - 1; ++i) 
    {
        
        var fx = math.evaluate(parsedEquation.toString(), { x: sum });
        result = result + 2 * fx;

        sum += h;

    }


    result = result * (h/2);

    res.innerHTML = result.toFixed(6);

    
}

function simpson() 
{
    if (!validate()) {
        return;
    }
    
    var fun = document.getElementById("function").value;
    var a = document.getElementById("a").value;
    var b = document.getElementById("b").value;

    var n = document.getElementById("n").value;

    var parsedEquation = math.parse(fun);

    var res = document.getElementById("result");

    

    var h = (b - a) / n;

    

    var result = math.evaluate(parsedEquation.toString(), { x: 0 });
    var result = result + math.evaluate(parsedEquation.toString(), { x: b - a });

    var sum = h;

    var parResultA = 0;
    var parResultB = 0;

    for (var i = 1; i < n ; ++i) 
    {
        var yi = math.evaluate(parsedEquation.toString(), { x: sum });

        if (i % 2 !== 0) 
        {
            parResultA += yi;
        } else 
        {
            parResultB += yi;
        }
        

        sum += h;

    }

    parResultA *= 4;
    parResultB *= 2;

    result += (parResultA + parResultB);

    result *= (h/3);

   res.innerHTML = result.toFixed(6);

}

