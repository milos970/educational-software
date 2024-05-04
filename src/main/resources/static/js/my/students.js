function sendPoints(id)
{
    const xhttp = new XMLHttpRequest();

    const points = document.getElementById("points-input-" + id);


    xhttp.onload = function()
    {
        if (xhttp.status === 200)
        {
            let tablePoints = document.getElementById('points-' + id);

            let num = (Number.parseInt(points.value) + Number.parseInt(tablePoints.innerText));
            tablePoints.innerText = num;
            points.value ="";

        } else {

            points.value ="";
        }


    }

    let url = "/admin/student/" + id + "/points/" + points.value;

    xhttp.open("PATCH", url, true);
    xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");



    xhttp.send();
}


function sendAbsents(id)
{
    const xhttp = new XMLHttpRequest();

    const absents = document.getElementById("absents-input-" + id);

    xhttp.onload = function()
    {
        if (xhttp.status === 200)
        {
            let tableAbsents = document.getElementById('absents-' + id);

            let num = (Number.parseInt(absents.value) + Number.parseInt(tableAbsents.innerText));
            tableAbsents.innerText = num;
            absents.value ="";

        } else {

            absents.value ="";
        }


    }

    let url = "/admin/student/" + id + "/absents/" + absents.value;

    xhttp.open("PATCH", url, true);
    xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");



    xhttp.send();
}