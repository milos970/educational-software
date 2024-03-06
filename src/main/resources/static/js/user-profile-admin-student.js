

function addPoints(id)
{

    const inputElement = document.getElementById('points-input');


    const xhttp = new XMLHttpRequest();
    xhttp.onload = function()
    {
        const inputElement = document.getElementById('points');
        inputElement.innerHTML = xhttp.responseText;
    }


    const url = "/admin/student/" + id + "/update-points";


    xhttp.open("PATCH", url, true);
    xhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

    const params = 'value=' + inputElement.value;

    xhttp.send(params);


}


function addAbsenciu(id)
{
    const inputElement = document.getElementById('absencie-input-input');
    const xhttp = new XMLHttpRequest();
    xhttp.onload = function()
    {

        const spanElement = document.getElementById('absencie');
        spanElement.innerHTML = xhttp.responseText;
    }

    const url = "/admin/student/" + id + "/update-absencie";

    xhttp.open("PATCH", url, true);
    xhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

    const params = 'value=' + inputElement.value;

    xhttp.send(params);


}