const fun = document.getElementById("function");
const a = document.getElementById("a");
const b = document.getElementById("b");
const n = document.getElementById("n");
const errorFun = document.getElementById("error-function");
const errorDH = document.getElementById("error-dh");
const errorHH = document.getElementById("error-hh");
const errorN = document.getElementById("error-n");

function validate() 
{
    try {
        
        var expression = math.parse(fun.value);
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

   
    let h = (b.value - a.value) / n.value;

    let parsedEquation = math.parse(fun.value);
    

    let result = math.evaluate(parsedEquation.toString(), { x: 0 });
    result = result + math.evaluate(parsedEquation.toString(), { x: b - a });

    let sum = h;
    for (let i = 0; i < n.value - 1; ++i) 
    {
        
        let fx = math.evaluate(parsedEquation.toString(), { x: sum });
        result = result + 2 * fx;

        sum += h;

    }


    result = result * (h/2);
    res.value = result.toFixed(6);
}

function simpson() 
{
    if (!validate()) {
        return;
    }
    
    
    let parsedEquation = math.parse(fun);

    let h = (b - a) / n;

    

    let result = math.evaluate(parsedEquation.toString(), { x: 0 });
    result = result + math.evaluate(parsedEquation.toString(), { x: b - a });

    let sum = h;

    let parResultA = 0;
    let parResultB = 0;

    for (let i = 1; i < n ; ++i) 
    {
        let yi = math.evaluate(parsedEquation.toString(), { x: sum });

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

   res.value = result.toFixed(6);

}

