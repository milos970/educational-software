
let idA = 0;
let idB = 0;
let userName = "";


let receiver = "";
function sendMessage()
{
    alert(45);
    if (getById("message-input").value.length < 1 || getById("message-input").value.length > 100)
    {
        getById("message-input-error-hint").innerHtml = "Nevalidný výraz!";
        return;
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
            alert("Success");
        } else {
            alert("Unsuccessfull");
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



    alert(5);
    let data =
    {
        content: content,
        receiverUsername: receiver
    };
    xhttp.send(JSON.stringify(data));
}


function getConversation(receiverUsername)
{

    while (document.getElementById("conversation").firstChild) {
        document.getElementById("conversation").removeChild(document.getElementById("conversation").firstChild);
    }

    receiver = receiverUsername;


    getById("conversation-div").style.display = "block";


    getById("send-message-div").style.display="block";




    $.ajax({
        url: '/person/conversation',
        method: 'GET',
        data: { receiver: receiver },
        dataType: 'json',
        success: function(response) {
            alert(response);
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

                conversationDivElement.insertBefore(lineDiv, conversationDivElement.lastChild);

                const button = document.createElement("button");
                button.textContent = message.content;

                if (message.senderUsername !== receiver) {

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
        },
        error: function(xhr, status, error) {
            // Handle error
            console.error(xhr.responseText);
        }
    });












}