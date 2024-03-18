



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










