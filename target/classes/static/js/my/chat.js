
let idA = 0;
let idB = 0;
let userName = "";


let receiver = "";
function sendMessage(senderUsername,receiverUsername)
{




    if (getById("message-input").value.length < 1 || getById("message-input").value.length > 100)
    {
        getById("message-input-error-hint").innerHtml = "Nevalidný výraz!";
        return;
    }

    if (receiverUsername === 'null') {
        receiverUsername = receiver;
    } else {
        receiver = receiverUsername;
    }







    const xhttp = new XMLHttpRequest();
    const element = document.getElementById("conversation");
    const inputElement = document.getElementById("message-input");

    const content = getById("message-input").value;

    let scrollDiv = getById("conversation");
    scrollDiv.scrollTop = scrollDiv.scrollHeight;


    getById("message-input").value = "";
    xhttp.onload = function() {
        if (xhttp.status === 200) {

        } else {

            return;
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

    let url = "/person/message";

    xhttp.open("POST", url, true);
    xhttp.setRequestHeader("Content-Type", "application/json");




    let data =
    {
        content: content,
        senderUsername: senderUsername,
        receiverUsername: receiverUsername
    };
    xhttp.send(JSON.stringify(data));
}


var lastId = -1;
function getConversation(receiverUsername, id)
{

    getById("send-button").disabled = false;
    while (document.getElementById("conversation").firstChild) {
        document.getElementById("conversation").removeChild(document.getElementById("conversation").firstChild);
    }

    receiver = receiverUsername;


    getById("conversation-div").style.display = "block";


    getById("send-message-div").style.display="block";







    if (lastId === -1)
    {
        lastId = id;
        document.getElementById(id).style.backgroundColor="green";
    } else {
        document.getElementById(lastId).style.backgroundColor="";
        lastId = id;
        document.getElementById(id).style.backgroundColor="green";
    }



    $.ajax({
        url: '/person/conversation',
        method: 'GET',
        data: { receiver: receiver },
        dataType: 'json',
        async: false,
        success: function(response) {
            const messages = response;


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

                if (conversationDivElement.querySelector("div"))
                {

                    conversationDivElement.insertBefore(lineDiv, conversationDivElement.firstChild);

                } else {
                    conversationDivElement.appendChild(lineDiv);

                }


                const button = document.createElement("button");
                button.textContent = message.content;


                if (message.senderUsername !== receiver) {

                    button.classList.add("btn", "btn-success", "btn-rounded", "btn-fw");
                    innerDivLeft.style.display = "hidden";
                    innerDivRight.appendChild(button);

                } else {

                    innerDivRight.style.display = "hidden";
                    button.classList.add("btn", "btn-light", "btn-rounded", "btn-fw");
                    innerDivLeft.appendChild(button);

                }
            })
            let scrollDiv = getById("conversation");
            scrollDiv.scrollTop = scrollDiv.scrollHeight;
        },
        error: function(xhr, status, error) {
            // Handle error
            console.error(xhr.responseText);
        }
    });












}