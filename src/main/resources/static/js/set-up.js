

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
    xhttp.open("GET", "/admin/system/check-name");
    xhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

    const params = 'value=' + inputElement.value;

    xhttp.send(params);

}

function validateCsv(csvString)
{
    var result = $.csv.toArrays(csvString);

    const element = document.getElementById("file-hint")

    if (result[0][0].trim().toLowerCase() !== "osobné číslo" || result[0][1].trim().toLowerCase() !== "meno" || result[0][2].trim().toLowerCase() !== "priezvisko" || result[0][3].trim().toLowerCase() !== "email")
    {
        element.innerHTML = "Nevalidná hlavička!";
        return;
    } else {
        element.innerHTML = "";
    }

    const regexPin = /^\d{6}$/;
    const regexEmail = /^[a-zA-Z0-9._%+-]+@stud\.uniza\.sk$/;
    for (let i = 1; i < result.length; ++i)
    {



            if (!regexPin.test(result[i][0].trim()))
            {
                const char = String.fromCharCode(65);
                element.innerHTML = "Nevalidný výraz! " + (i + 1) + ":" + char;
                return
            }

            if (result[i][1].length === 0)
            {
                const char = String.fromCharCode(66);
                element.innerHTML = "Prázdne pole! " +  (i + 1) + ":" + char;
                return;
            }


            if (result[i][1].trim().length > 50)
            {
                const char = String.fromCharCode(66);
                element.innerHTML = "Meno je dlhšie ako 50 znakov! " +  (i + 1) + ":" + char;
                return;
            }

            if (result[i][2].length === 0)
            {
                const char = String.fromCharCode(67);
                element.innerHTML = "Prázdne pole! " +  (i + 1) + ":" + char
                return;
            }

            if (result[i][2].trim().length > 50)
            {
                const char = String.fromCharCode(67);
                element.innerHTML = "Priezvisko je dlhšie ako 50 znakov!" +  (i + 1) + ":" + char;
                return;
            }



            if (!regexEmail.test(result[i][3].trim()))
            {
                const char = String.fromCharCode(68);
                element.innerHTML = "Nevalidná emailová adresa!" + (i + 1) + ":" + char;
                return;
            }



    }

    element.innerHTML = "";







}

function csvToString(file, callback) {
    var reader = new FileReader();

    // Closure to capture the file information.
    reader.onload = function(event) {
        var csvString = event.target.result;
        callback(csvString);
    };

    // Read in the file as a text
    reader.readAsText(file);
}

// Example usage
var fileInput = document.getElementById('file-input');

fileInput.addEventListener('change', function(event) {
    var file = event.target.files[0];

    csvToString(file, function(csvString) {
        validateCsv(csvString); // Here you can use the CSV string as needed
    });
});



function isValid()
{
    const button = document.getElementById("submit-button");
    return isNameValid && isIntervalValid && isDateValid ? button.disabled = false : button.disabled = true;
}



