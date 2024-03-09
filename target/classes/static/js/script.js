

function getById(id)
{
    let element = document.getElementById(id);
    if (!element)
    {
        throw new ReferenceError(id + " is not defined!");
    }
    return element;
}

function isNotEmpty(element, errorElement)
{
    if (element.value === "")
    {
        errorElement.innerHTML = "Prázdne pole!";
        return false;
    } else {
        errorElement.innerHTML = "";
        return true;
    }
}

function isIntervalValid(min, max, element, elementHint)
{
    if (min > element.value || element.value > max)
    {
        elementHint.innerHTML = "Hodnota mimo intervalu!";
        return false;

    }else {
        elementHint.innerHTML = "";
        return true;
    }
}

function parseStringCoordintatesToArrays()
{
    const matches = coordinatesStringElement.value.match(/-?\d+(\.\d+)?/g);


    const coordinates = [];
    let j = 0;
    for (let i = 0; i < matches.length; i += 2)
    {
        coordinates[j] = [2];
        coordinates[j][0] = matches[i];
        coordinates[j][1] = matches[i + 1];
        ++j;
    }

    validateCoordinates(coordinates);
}

function parseCsvCoordinatesToArrays()
{
    function csvToString(file, callback) {
        let reader = new FileReader();

        // Closure to capture the file information.
        reader.onload = function(event) {
            var csvString = event.target.result;
            callback(csvString);
        };

        // Read in the file as a text
        reader.readAsText(file);
    }

// Example usage
    const fileInput = getById("coordinates-csv-input");

    let coordinates = [];

    fileInput.addEventListener('change', function(event) {
        let file = event.target.files[0];

        csvToString(file, function(csvString) {
            coordinates = $.csv.toArrays(csvString);
            validateCoordinates(coordinates);

        });
    });





}
function validateCoordinates(coordinatesArrays)
{

    const uniques = new Set();

    const coordinatesElement = getById("coordinates-csv-error-hint");
    console.log(coordinatesArrays[0][0].length);
    for (let i = 0; i < coordinatesArrays.length; ++i)
    {

        const valueX = coordinatesArrays[i][0];
        const valueY = coordinatesArrays[i][1];



        if (isNaN(valueX) === true || isNaN(valueY) === true || valueX.length === 0 || valueY.length === 0)
        {
            const char = String.fromCharCode(65);
            coordinatesElement.innerHTML = "Nevalidná hodnota na súradnici: " + char + (i+1);
            return;
        }

    }

    for (let i = 0; i < coordinatesArrays.length; ++i)
    {
        uniques.add(coordinatesArrays[i][0]);
    }

    if (coordinatesArrays.length !== uniques.size)
    {
        coordinatesElement.innerHTML = "Duplikát X!";
        return false;
    } else {
        coordinatesElement.innerHTML = "";
    }


    return true;

}




function areStringCoordinatesValid()
{
    const regex = /^\[\((?:-?\d*\.?\d+),(-?\d*\.?\d+)\)(?:,\((?:-?\d*\.?\d+),(-?\d*\.?\d+)\))*]$/;

    let element = document.getElementById("nodes-error");


    if (coordinatesStringElement.value.length === 0)
    {
        element.innerHTML = "Prázdne pole!";
        return false;
    }


    if (regex.test(coordinatesStringElement.value))
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

















const coordinatesCsvElement = getById("coordinates-csv-input");
const coordinatesStringElement = getById("coordinates-string-input");

const resultElement = getById("result-input");



const calculateButton = getById("calculate-button");
const displayGraphButton = getById("display-graph-button");

calculateButton.disabled = true;
displayGraphButton.disabled = true;

let areCoordinatesValid = false;