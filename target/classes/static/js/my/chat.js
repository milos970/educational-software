
let idA = 0;
let idB = 0;
let userName = "";

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
    const element = document.getElementById("conversation");
    const inputElement = document.getElementById("message-input");

    const content = getById("message-input").value;

    let scrollDiv = getById("conversation");
    scrollDiv.scrollTop = scrollDiv.scrollHeight;


    getById("message-input").value = "";
    xhttp.onload = function() {
        if (xhttp.status === 200) {
            alert("Success");
        } else {
            alert("Unsuccessfull");
        }

        const lineDiv = document.createElement('div');
        lineDiv.classList.add("flex-container");

        const innerDivLeft = document.createElement('div');
        innerDivLeft.classList.add("left-item");

        const button = document.createElement("button");
        button.classList.add("btn", "btn-success", "btn-rounded", "btn-fw");



        const innerDivRight = document.createElement('div');
        innerDivRight.classList.add("right-item");

        innerDivRight.appendChild(button);
        lineDiv.appendChild(innerDivLeft);
        lineDiv.appendChild(innerDivRight);


        element.appendChild(lineDiv);


        innerDivLeft.style.display = "hidden";
        button.textContent = content;


    }

    let url = "/person/sendMessage";

    xhttp.open("POST", url, true);
    xhttp.setRequestHeader("Content-Type", "application/json");



    let data = {
        senderId: idA,
        recipientId: idB,
        sender: userName,
        content: content
    };
    xhttp.send(JSON.stringify(data));
}


function getConversation(usrName, ida, idb)
{

    userName = usrName;

    getById("send-message-div").style.display="block";
    const xhttp = new XMLHttpRequest();

    xhttp.onload = function()
    {
        if (xhttp.status === 200)
        {
            const messages = JSON.parse(xhttp.response);


            const conversationDivElement = document.getElementById("conversation");

            messages.forEach(function(message) {
                const lineDiv = document.createElement('div');
                lineDiv.classList.add("flex-container");

                const innerDivLeft = document.createElement('div');
                innerDivLeft.classList.add("left-item");

                const innerDivRight = document.createElement('div');
                innerDivRight.classList.add("right-item");


                lineDiv.appendChild(innerDivLeft);
                lineDiv.appendChild(innerDivRight);

                conversationDivElement.insertBefore(lineDiv, conversationDivElement.lastChild);

                const button = document.createElement("button");
                button.textContent = message.content;

                if (message.sender === usrName) {

                    button.classList.add("btn", "btn-success", "btn-rounded", "btn-fw");
                    innerDivLeft.style.display = "hidden";
                    innerDivRight.appendChild(button);
                    //innerDivRight.innerHTML = message.content;

                } else {
                    innerDivRight.style.display = "hidden";
                    button.classList.add("btn", "btn-light", "btn-rounded", "btn-fw");
                    innerDivLeft.appendChild(button);
                    //innerDivLeft.innerHTML = message.content;
                }
            })
            let scrollDiv = getById("conversation");
            scrollDiv.scrollTop = scrollDiv.scrollHeight;
        } else {
            alert("Unsuccessfull");
        }



    }





    idA = ida;
    idA = idb;



    let url = "/person/" + idA + "/conversation/" + idB;

    xhttp.open("GET", url, true);

    xhttp.send();



}