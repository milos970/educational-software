let receiver = "";
let lastId = -1;

const csrfToken = document.querySelector('meta[name="_csrf"]').content;
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

function createMessageElement(content, isSender) {
    const lineDiv = document.createElement('div');
    lineDiv.classList.add("flex-container");

    const leftDiv = document.createElement('div');
    leftDiv.classList.add("left-item");

    const rightDiv = document.createElement('div');
    rightDiv.classList.add("right-item");

    const button = document.createElement("button");
    button.textContent = content;

    if (isSender) {
        button.classList.add("btn", "btn-success", "btn-rounded", "btn-fw");
        rightDiv.appendChild(button);
        leftDiv.style.display = "none";
    } else {
        button.classList.add("btn", "btn-light", "btn-rounded", "btn-fw");
        leftDiv.appendChild(button);
        rightDiv.style.display = "none";
    }

    lineDiv.appendChild(leftDiv);
    lineDiv.appendChild(rightDiv);

    return lineDiv;
}


async function sendMessage(senderUsername, receiverUsername) {
    const inputElement = document.getElementById("message-input");
    const errorHint = document.getElementById("message-input-error-hint");
    const content = inputElement.value.trim();

    if (content.length < 1 || content.length > 100) {
        errorHint.innerHTML = "Nevalidný výraz!";
        return;
    } else {
        errorHint.innerHTML = "";
    }

    const receiver = receiverUsername && receiverUsername !== 'null'
        ? receiverUsername
        : null;

    inputElement.value = "";

    try {
        const response = await fetch('/chat/messages', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                [csrfHeader]: csrfToken   // teraz je v rovnakom objekte
            },
            body: JSON.stringify({
                content,
                senderUsername,
                receiverUsername: receiver
            })
        });

        if (!response.ok) {
            throw new Error('Nepodarilo sa odoslať správu');
        }

        const conversationDiv = document.getElementById("conversation");
        const messageElement = createMessageElement(content, true);
        conversationDiv.appendChild(messageElement);
        conversationDiv.scrollTop = conversationDiv.scrollHeight;

    } catch (error) {
        console.error(error);
    }
}



async function getConversation(receiverUsername, id) {
    const conversationDiv = document.getElementById("conversation");

    receiver = receiverUsername;

    const currentUser = document.querySelector('meta[name="current-user"]').content;


    document.getElementById("send-button").disabled = false;
    document.getElementById("conversation-div").style.display = "block";
    document.getElementById("send-message-div").style.display = "block";

    if (lastId !== -1) {
        document.getElementById(lastId).style.backgroundColor = "";
    }
    document.getElementById(id).style.backgroundColor = "green";
    lastId = id;

    conversationDiv.innerHTML = "";

    try {
        const response = await fetch(`/chat/conversations?receiver=${encodeURIComponent(receiver)}`);
        if (!response.ok) {
        throw new Error('Nepodarilo sa načítať konverzáciu');
        }

        const messages = await response.json();

        messages.forEach(msg => {
            const isSender = msg.senderUsername === currentUser;
            const messageElement = createMessageElement(msg.content, isSender);
            conversationDiv.appendChild(messageElement);
        });

        conversationDiv.scrollTop = conversationDiv.scrollHeight;

    } catch (error) {
        console.error(error);
    }
}
