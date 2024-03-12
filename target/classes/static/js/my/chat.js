


function sendEmailVerification()
{
    const xhttp = new XMLHttpRequest();

    const element = document.getElementById("chat-div");
    const inputElement = document.getElementById("message-input");


    xhttp.onload = function()
    {
        if (xhttp.status === 200)
        {
            alert("Success");
        } else {
            alert("Unsuccessfull");
        }


    }

    let url = "/admin/"

    xhttp.open("GET", url, true);
    xhttp.setRequestHeader("Content-Type", "application/json");

    xhttp.send();
}


function sendMessage()
{
    const xhttp = new XMLHttpRequest();
    const element = document.getElementById("chat-div");
    const inputElement = document.getElementById("message-input");


    const idA = "0";
    const idB = "0";
    const sender = "gabrisova";

    xhttp.onload = function()
    {
        if (xhttp.status === 200)
        {
            alert("Success");
        } else {
            alert("Unsuccessfull");
        }


        const newDiv = document.createElement("div");
        newDiv.className = "flex-item";

       /* const innerDivA = document.createElement("div");
        innerDivA.className = "left-item";

        innerDivA.style.display = "none";*/

        const innerDivB = document.createElement("div");
        innerDivB.className = "right-item";

        innerDivB.innerHTML = inputElement.value;
        inputElement.value = "";

       // newDiv.appendChild(innerDivA);
        newDiv.appendChild(innerDivB);
        element.appendChild(newDiv);
    }

    let url = "/admin/sendMessage";

    xhttp.open("POST", url, true);
    xhttp.setRequestHeader("Content-Type", "application/json");



    let data = {
        idA: idA,
        idB: idB,
        content: "sdafsdfasdfasdfs",
        sender: sender
    };
    xhttp.send(JSON.stringify(data));
}


function getConversation(idA, idB)
{

    const xhttp = new XMLHttpRequest();

    const element = document.getElementById("chat-div");
    const inputElement = document.getElementById("message-input");


    alert(idA);
    alert(idB);


    xhttp.onload = function()
    {
        if (xhttp.status === 200)
        {
            alert("Success");
        } else {
            alert("Unsuccessfull");
        }


    }

    let url = "/person/conversation/" + idA + "/" + idB;

    xhttp.open("GET", url, true);
    xhttp.setRequestHeader("Content-Type", "application/json");

    xhttp.send();
}