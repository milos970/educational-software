


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


function sendMessage(idA, idB, content)
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

    let url = "/person/sendMessage";

    xhttp.open("POST", url, true);
    xhttp.setRequestHeader("Content-Type", "application/json");



    let data = {
        idA: idA,
        idB: idB,
        content: content
    };
    xhttp.send(JSON.stringify(data));
}


function getConversation(username, idA, idB)
{


    let dt = {
        idA: 0,
        idB: 0
    };

    jQuery.ajax({
        url: "/person/conversation/0",
        data: dt,
        type: "GET",
        success: function (dejta) {



            var messages = dejta;

            const conversationDivElement = document.getElementById("conversation");

            messages.forEach(function(message)
            {
                const lineDiv = document.createElement('div');
                lineDiv.classList.add("flex-container");

                const innerDivLeft = document.createElement('div');
                innerDivLeft.classList.add("left-item");

                const innerDivRight = document.createElement('div');
                innerDivRight.classList.add("right-item");


                lineDiv.appendChild(innerDivLeft);
                lineDiv.appendChild(innerDivRight);

                conversationDivElement.insertBefore(lineDiv, conversationDivElement.firstChild);



                if (message.sender === username)
                {

                    innerDivLeft.style.display="hidden";
                    innerDivRight.innerHTML = message.content;

                } else {
                    innerDivRight.style.display="hidden";
                    innerDivLeft.innerHTML = message.content;
                }

            });







        },
        error: function (xhr, ajaxOptions, thrownError) {

        },
    });
}