

let isNameValid = false;
let isDateValid = false;
let isIntervalValid = false;

const button = document.getElementById("submit-button");
button.disabled = true;

function getById(id)
{
    let el = document.getElementById(id);
    if (!el)
    {
        throw new ReferenceError(id + " is not defined");
    }
    return el;
}


function isEmpty(element, elementHint)
{
    if (element.value === "")
    {
        elementHint.innerHTML = "Pole je prázdne!";
    } else {
        elementHint.innerHTML = "";
    }
}

function validateInterval(min, max, element, elementHint)
{
    isIntervalValid = true;
    if (min > element.value || element.value > max)
    {
        elementHint.innerHTML = "Hodnota mimo intervalu!";
        isIntervalValid = false;
    }else {
        elementHint.innerHTML = "";
    }
}

function validateDate(element, elementHint)
{

    isDateValid = true;

    let pickedDate = new Date(element.value);

    let today = new Date();


    if (pickedDate > today) {
        elementHint.innerHTML = "";
    } else {
        elementHint.innerHTML = "Nevalidný dátum!";
        isDateValid = false;
    }


}


function validateName(element, elementHint)
{
    isNameValid = true;
    if (isEmpty(element, elementHint))
    {
        elementHint.innerHTML = "Pole je prázdne!";
        isNameValid = false;
    }


    const xhttp = new XMLHttpRequest();
    xhttp.onload = function()
    {
        if (this.responseText === "true") {
            elementHint.innerHTML = "Názov sa už používa!"
        } else {
            elementHint.innerHTML = "";
            isNameValid = false;
        }

    }
    xhttp.open("GET", "/admin/hi");
    xhttp.send();


}


function isValid()
{
    const button = document.getElementById("submit-button");
    return isNameValid && isIntervalValid && isDateValid ? button.disabled = false : button.disabled = true;
}



