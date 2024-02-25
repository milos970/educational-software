function getById(id)
{
    let el = document.getElementById(id);
    if (!el)
    {
        throw new ReferenceError(id + " is not defined");
    }
    return el;
}

const emailInput = getById("email-input");
const passwordInput = getById("password-input");
const repPasswordInput = getById("rep-password-input");
const submitButton = getById("submit-button");

submitButton.disabled = true;


function isNotEmpty()
{
    submitButton.disabled = usernameInput.value === "" || passwordInput.value === "";
}

