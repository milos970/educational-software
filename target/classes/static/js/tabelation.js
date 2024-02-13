const table = document.getElementById("table");
const equation = document.getElementById("equation");
const equationError = document.getElementById("equation-error");

function validate(equation) 
{
  let lastChar = equation.charAt(equation.length - 1);
  equationError.style.color = "red";

  if (equation.length === 0) 
  {
    equationError.innerHTML = "Nevalidný výraz!";
    return false;
  }
  
  if ("0" !== lastChar) 
  {
    equationError.innerHTML = "Nevalidný výraz!";
    return false;
  }

  if (equation.indexOf("=") === - 1) 
  {
    equationError.innerHTML = "Nevalidný výraz!";
    return false;
  }

  equation = equation.slice(0, -1).replace("=", "").trim(); // separate to function

    try {
      let expression = math.parse(equation);
      math.evaluate(expression.toString(), { x: 0 });
      equationError.innerText = "";
      
      return true;
    } catch (error) {
      equationError.innerText = "Nevalidný výraz!";
      return false;
    }
}

function tabelation() {


  let equationString = equation.value;

  if (validate(equationString)) 
  {
    return;
  }

  
  

  const parsedEquation = math.parse(equationString).toString();

  let value = 0;
  const step = 0.1;
  const data = [];
  const iterations = 1000;

  for (let i = 0; i < iterations; ++i) {
    let first = math.evaluate(parsedEquation, { x: value });

    value += step;

    let second = math.evaluate(parsedEquation, { x: value });

    if (first * second < 0 && (data.length <= 16)) {
      data.push(value - step);
      data.push(value);
    }
    value += step;
  }

  const headers = ["Dolná hranica", "Horná hranica"];

  initializeTable(headers, data);
}

function cl() 
{
  $("#table tr").remove(); 
}

function initializeTable(headers, data) {
 

  
  cl();
  

  let header = table.createTHead();
  let row = header.insertRow(0);
  for (let i = 0; i < headers.length; ++i) {
    let cell = row.insertCell(i);
    cell.innerHTML = headers[i];
  }

  

  for (let i = 0; i < data.length; i += 2) {
    let row = table.insertRow(-1);
    row.insertCell(0).innerText = data[i].toFixed(6);
    row.insertCell(1).innerText = data[i + 1].toFixed(6);
  }

  
}
