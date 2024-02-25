function getById(id)
{
    let el = document.getElementById(id);
    if (!el)
    {
        throw new ReferenceError(id + " is not defined");
    }
    return el;
}

const usernameInput = getById("username-input");
const passwordInput = getById("password-input");
const submitButton = getById("submit-button");

submitButton.disabled = true;


function isNotEmpty()
{
    submitButton.disabled = usernameInput.value === "" || passwordInput.value === "";


}