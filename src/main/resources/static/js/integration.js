const fun = document.getElementById("function");
const dh = document.getElementById("dh");
const hh = document.getElementById("hh");
const n = document.getElementById("n");
//const res = document.getElementById("result");
const funError = document.getElementById("function-error");
const dhError = document.getElementById("dh-error");
const hhError = document.getElementById("hh-error");
//const nError = document.getElementById("n-error");

function validateEquation()
{
  if (fun.value == null || fun.value === "")
  {
    funError.innerHTML = "Prázdne poľe!";
    return false;
  } else
  {
    funError.innerHTML = "";
  }

  fun.value = fun.value.toLowerCase();

  if (fun.value.indexOf("x") === -1)
  {

    funError.innerHTML  = "Nevalidný výraz!";
    return false;
  } else
  {
    funError.innerHTML = "";
  }



  try
  {
    let expression = math.parse(fun.value);
    math.evaluate(expression.toString(), { x: 0 });
    funError.innerHTML = "";

  } catch (error)
  {
    funError.innerHTML  = "Nevalidný výraz!";
    return false;
  }


  return true;
}
function validateLowerBound()
{

  if (dh.value === "")
  {
    dhError.innerHTML = "Prázdne poľe!";
    return false;
  } else {
    dhError.innerHTML = "";
  }


  let num = Number.parseFloat(dh.value);

  if (-10 > num || num > 9)
  {
    hhError.innerHTML = "Hodnota mimo intervalu!";
    return false;
  }else {
    hhError.innerHTML = "";
  }

  return true;

}

function validateUpperBound()
{

  if (hh.value === "")
  {
    hhError.innerHTML = "Prázdne poľe!";
    return false;
  } else {
    hhError.innerHTML = "";
  }

  let num = Number.parseFloat(hh.value);
  let numD = Number.parseFloat(dh.value);

  if (-9 > num || num > 10)
  {
    hhError.innerHTML = "Hodnota mimo intervalu!";
    return false;
  }else {
    hhError.innerHTML = "";
  }

  if (numD >= num)
  {
    hhError.innerHTML = "Horná hranica je menšia/rovná než dolná!";
    return false;
  }else {
    hhError.innerHTML = "";
  }

  return true;
}

function trapezoid()
{
  if ( !(validateEquation() && validateLowerBound() && validateUpperBound()) )
  {
    return;
  }

    let textarea = document.getElementById("text-area");

    
      const h = (hh.value - dh.value) / n.value;
    
      const parsedEquation = math.parse(fun.value);
    
    
      let sum = 0;
      let part = 1 * dh.value;
    
      for (let i = 0; i <= n.value; ++i) 
      {

        if (i == 0)
        {
            let fx = math.evaluate(parsedEquation.toString(), { x: dh.value});
            sum += fx;
            textarea.value += (fx + "\n");
            continue;
        }

        if (i == n.value)
        {
            let fx = math.evaluate(parsedEquation.toString(), { x: hh.value});
            sum += fx;
            textarea.value += (fx + "\n");
            continue;
        }

        part += 1 * h;

        let fx = 2 * math.evaluate(parsedEquation.toString(), { x: part })
        sum = sum + fx;
          textarea.value += (fx + "\n");
        
    
      }
    
      const result = sum * h/2;
      //res.value = result.toFixed(3);
}

function simpson() {
  if ( !(validateEquation() && validateLowerBound() && validateUpperBound()) )
  {
    return;
  }
    
      const h = (hh.value - dh.value) / n.value;

      const parsedEquation = math.parse(fun.value);
    
    
      let sum = 0;
      let part = 1 * dh.value;
    
      for (let i = 0; i <= n.value; ++i) 
      {

        if (i == 0)
        {
            sum += math.evaluate(parsedEquation.toString(), { x: dh.value});
            continue;
        }

        if (i == n.value)
        {
            sum += math.evaluate(parsedEquation.toString(), { x: hh.value});
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
      //res.value = result.toFixed(3);
}

function setMethod()
{



        title.innerHTML = methods.options[methods.selectedIndex].text;



}


function displayProcess()
{

}

