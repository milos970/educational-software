const fun = document.getElementById("function");
const a = document.getElementById("dh");
const b = document.getElementById("hh");
const n = document.getElementById("n");
const res = document.getElementById("result");
const errorFun = document.getElementById("error-function");
const errorDH = document.getElementById("error-dh");
const errorHH = document.getElementById("error-hh");
const errorN = document.getElementById("error-n");

function isValid() {
  let valid = true;

  if (a.value === "") {
    errorDH.innerHTML = "Prázdne pole!";
    valid = false;
  } else {
    if (a.value > b.value) {
        errorDH.innerHTML = "Dolná hranica je väčšia než horná!";
      valid = false;
    } else {
        errorDH.innerHTML = "";
    }
  }

  if (b.value === "") {
    errorHH.innerHTML = "Prázdne pole!";
    valid = false;
  } else {
    errorHH.innerHTML = "";
}

  if (n.value === "") {
    errorN.innerHTML = "Prázdne pole!";
    valid = false;
  } else {
    if (!Number.isInteger((1 * n.value)) || n.value <= 0) {
      errorN.innerHTML = "Nevalidná hodnota!";
      valid = false;
    } else {
      if (1 <= n.value && n.value <= 10) {
        errorN.innerHTML = "";
      } else {
        errorN.innerHTML = "Hodnota mimo povoleného intervalu!";
        valid = false;
      }
    }
  }

  if (fun.value.length === 0) {
    errorFun.innerHTML = "Prázdne pole!";
    return false;
  } else {
    try {
      var expression = math.parse(fun.value);
      math.evaluate(expression.toString(), { x: 0 });
      errorFun.innerHTML = "";
      return true;
    } catch (error) {
      errorFun.innerHTML = "Nevalidný výraz!";
      return false;
    }
  }
}

function trapezoid() {
    if (!isValid()) {
        return;
      }
    
      let h = (b.value - a.value) / n.value;
    
      let parsedEquation = math.parse(fun.value);
    
    
      let sum = 0;
      let part = 1 * a.value;
    
      for (let i = 0; i <= n.value; ++i) 
      {

        if (i == 0) 
        {
            sum += math.evaluate(parsedEquation.toString(), { x: a.value});
            continue;
        }

        if (i == n.value) 
        {
            sum += math.evaluate(parsedEquation.toString(), { x: b.value});
            continue;
        }

        part += 1 * h;
        
        sum = sum + (2 * math.evaluate(parsedEquation.toString(), { x: part }));
        
    
      }
    
      let result = sum * h/2;
      res.value = result.toFixed(6);
}

function simpson() {
    if (!isValid()) {
        return;
      }
    
      let h = (b.value - a.value) / n.value;
    
      let parsedEquation = math.parse(fun.value);
    
    
      let sum = 0;
      let part = 1 * a.value;
    
      for (let i = 0; i <= n.value; ++i) 
      {

        if (i == 0) 
        {
            sum += math.evaluate(parsedEquation.toString(), { x: a.value});
            continue;
        }

        if (i == n.value) 
        {
            sum += math.evaluate(parsedEquation.toString(), { x: b.value});
            continue;
        }

        part += 1 * h;

        if (i % 2 != 0) 
        {
            sum = sum + (4 * math.evaluate(parsedEquation.toString(), { x: part }));
        }

        if (i % 2 == 0) 
        {
            sum = sum + (2 * math.evaluate(parsedEquation.toString(), { x: part }));
        }
    
        
        
      }
    
      let result = sum * h/3;
      res.value = result.toFixed(6);
}
