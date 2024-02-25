function getById(id)
{
    let el = document.getElementById(id);
    if (!el)
    {
        throw new ReferenceError(id + " is not defined");
    }
    return el;
}

const pinInput = getById("pin-input");
const emailInput = getById("email-input");
const nameInput = getById("name-input");
const surnameInput = getById("surname-input");

const passwordInput = getById("password-input");
const repPasswordInput = getById("rep-password-input");
const submitButton = getById("submit-button");


const pinInputHint = getById("pin-hint");
const emailInputHint = getById("email-hint");
const nameInputHint = getById("name-hint");
const surnameInputHint = getById("surname-hint");

const passwordInputHint = getById("password-hint");
const repPasswordInputHint = getById("rep-password-hint");


const upperCaseHint = document.getElementById("upperCase");
const lowerCaseHint = document.getElementById("lowerCase");
const oneNumberHint = document.getElementById("oneNumber");
const specialCharacterHint = document.getElementById("specialCharacter");
const rangeHint = document.getElementById("range");

submitButton.disabled = true;

let isEmailValid = false;
let isPasswordValid = false;
let isRepPasswordValid = false;
let isPINValid = false;
let isNameValid = false;
let isSurnameValid = false;

function isNotEmpty()
{
    submitButton.disabled = usernameInput.value === "" || passwordInput.value === "";
}

function isEmpty(element, errorElement)
{
    if (element.value === "")
    {
        errorElement.innerHTML = "Prázdne pole!";
    } else {
        errorElement.innerHTML = "";
    }
}


function validateEmail()
{
    const regex = /^(([^<>()[\].,;:\s@"]+(\.[^<>()[\].,;:\s@"]+)*)|(".+"))@(([^<>()[\].,;:\s@"]+\.)+[^<>()[\].,;:\s@"]{2,})$/i;

    isEmailValid = false;

    if (emailInput.value.trim() === "")
    {
        emailInputHint.innerHTML = "Prázdne pole!";
        return;
    }

    if (!emailInput.value.trim().toLowerCase().match(regex))
    {
        emailInputHint.innerHTML = "Nevalidný email!";
    }

    isEmailValid = true;
    return true;
}

function validateSchoolEmail()
{

    if (!validateEmail())
    {
        return
    }
    isEmailValid = false;

    if (emailInput.value.trim().toLowerCase().split("@")[1] !== "fri.uniza.sk")
    {
        emailInputHint.innerHTML = "Nevalidný email!";
        return;
    } else
    {
        emailInputHint.innerHTML = "";
    }

    isEmailValid = true;
}

function validatePIN()
{
    const regex = /^\d{6}$/;

    isPINValid = false;

    if (!pinInput.value.trim().toLowerCase().match(regex))
    {
        pinInputHint.innerHTML = "Nevalidné osobné číslo!";
        return;
    } else {
        pinInputHint.innerHTML = "";
    }

    isPINValid = true;

}

function validatePassword()
{
    const regexUpperCase = /[A-Z]/g;
    const regexLowerCase = /[a-z]/g;
    const regexNumber = /[0-9]/g;
    const regexSpecialCharacter = /[?.:!@#$%^&*()_-]/g;
    isPasswordValid = false;



    if (passwordInput.value.match(regexUpperCase)) {
        upperCaseHint.style.color = "green";
    } else {
        upperCaseHint.style.color = "white";
        isPasswordValid = false;
    }

    if (passwordInput.value.match(regexLowerCase)) {
        lowerCaseHint.style.color = "green";
    } else {
        lowerCaseHint.style.color = "white";
        isPasswordValid = false;
    }

    if (passwordInput.value.match(regexNumber)) {
        oneNumberHint.style.color = "green";
    } else {
        oneNumberHint.style.color = "white";
        isPasswordValid = false;
    }

    if (passwordInput.value.match(regexSpecialCharacter)) {
        specialCharacterHint.style.color = "green";
    } else {
        specialCharacterHint.style.color = "white";
        isPasswordValid = false;
    }


    if (passwordInput.value.length >= 12 ) {
        rangeHint.style.color = "green";
    } else {
        rangeHint.style.color = "white";
        isPasswordValid = false;
    }

    isPasswordValid = true;


}

function validateRepPassword()
{

    isRepPasswordValid = false;

    if ( (passwordInput.value === repPasswordInput.value) || (repPasswordInput.value === "" && passwordInput.value() !== "")) {
        repPasswordInputHint.innerHTML = "&#8203";
    } else {
        repPasswordInputHint.innerHTML = "Heslá sa nezhodujú!";
        return;
    }

    isRepPasswordValid = true;
}





