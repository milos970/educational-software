

function initializeSignUpPage()
{
    studentOrEmployee(1);
}
function studentOrEmployee(who)
{
    switch (who)
    {
        case 1:
            showFormForStudent();
            break;
        case 2:
            showFormForEmployee();
            break;
        default:

    }
}

function showFormForStudent()
{
    const elementsToHide = ["name-div", "pin-div", "surname-div"];

    elementsToHide.forEach((item) => {
        document.getElementById(item).style.display="none";
    });

    const elementsToShow = ["email-div", "password-div", "rep-password-div"];

    elementsToShow.forEach((item) => {
        document.getElementById(item).style.display="block";
    });
}


function showFormForEmployee()
{
    const elementsToShow = ["name-div", "pin-div", "surname-div", "email-div", "password-div", "rep-password-div"];

    elementsToShow.forEach((item) => {
        document.getElementById(item).style.display="block";
    });
}











function canRegister()
{
    const nameInput = document.getElementById("name-input");
    const surnameInput = document.getElementById("name-input");
    const passwordInput = document.getElementById("password-input");
    const repPasswordInput = document.getElementById("password-input");
    const emailInput = document.getElementById("email-input");
    const personalNumberInput = document.getElementById("pin-input");

    const nameInputErrorHint = document.getElementById("name-input-error-hint");
    const surnameInputErrorHint = document.getElementById("surname-input-error-hint");
    const emailpasswordInputErrorHint = document.getElementById("email-input-error-hint");
    const personalNumberInputErrorHint = document.getElementById("pin-input-error-hint");
    const passwordInputErrorHint = document.getElementById("password-input-error-hint");
    const repPasswordInputErrorHint = document.getElementById("rep-password-input-error-hint");

    if (nameInput.value === "")
    {
        nameInputErrorHint.innerHTML = "Zadajte meno!";
        return false;
    } else {
        nameInputErrorHint.innerHTML = "";
    }

    if (surnameInput.value === "")
    {
        surnameInputErrorHint.innerHTML = "Zadajte priezvisko!";
        return false;
    } else {
        surnameInputErrorHint.innerHTML = "";
    }

    if (personalNumberInput.value === "")
    {
        personalNumberInputErrorHint.innerHTML = "Zadajte osobné číslo!";
        return false;
    } else {
        personalNumberInputErrorHint.innerHTML = "";
    }

    if (emailInput.value === "")
    {
        emailpasswordInputErrorHint.innerHTML = "Zadajte školský email!";
        return false;
    } else {
        emailpasswordInputErrorHint.innerHTML = "";
    }

    const regexEmail = /^[a-zA-Z0-9._%+-]+@fri\.uniza\.sk$/;
    if (regexEmail.test(emailInput.value))
    {
        emailpasswordInputErrorHint.innerHTML = "Nevalidný školský email!";
        return false;
    } else {
        emailpasswordInputErrorHint.innerHTML = "";
    }

    if (passwordInput.value === "")
    {
        passwordInputErrorHint.innerHTML = "Zadajte heslo!";
        return false;
    }else {
        passwordInputErrorHint.innerHTML = "";
    }


    if (repPasswordInput.value === "")
    {
        repPasswordInputErrorHint.innerHTML = "Znova zadajte heslo!";
        return false;
    }else {
        repPasswordInputErrorHint.innerHTML = "";
    }

    return true;
}


//https://stackoverflow.com/questions/10610249/prevent-checkbox-from-ticking-checking-completely

$('input[type="checkbox"]').click(function(event) {
    var $checkbox = $(this);

    // Ensures this code runs AFTER the browser handles click however it wants.
    setTimeout(function() {
        $checkbox.removeAttr('checked');
    }, 0);

    event.preventDefault();
    event.stopPropagation();
});

////////////////////


function validatePassword()
{
    const regexUpperCase = /[A-Z]/g;
    const regexLowerCase = /[a-z]/g;
    const regexNumber = /[0-9]/g;
    const regexSpecialCharacter = /[?.:!@#$%^&*()_-]/g;

    const passwordInput = document.getElementById("password-input");

    const upperCaseHint = document.getElementById("upper-case-checkbox-div");
    const lowerCaseHint = document.getElementById("lower-case-checkbox-div");
    const specialCharacterHint = document.getElementById("special-char-checkbox-div");
    const oneNumberHint = document.getElementById("number-checkbox-div");
    const minCharacters = document.getElementById("min-chars-checkbox-div");
    const maxCharacters = document.getElementById("max-chars-checkbox-div");

    if (passwordInput.value.match(regexUpperCase)) {
        upperCaseHint.classList.remove("form-check-danger");
        upperCaseHint.classList.add("form-check-success");
        upperCaseHint.getElementsByTagName("input")[0].checked = true;
    } else {
        upperCaseHint.classList.remove("form-check-success");
        upperCaseHint.classList.add("form-check-danger");
        upperCaseHint.getElementsByTagName("input")[0].checked = false;
    }


    if (passwordInput.value.match(regexLowerCase)) {
        lowerCaseHint.classList.remove("form-check-danger");
        lowerCaseHint.classList.add("form-check-success");
        lowerCaseHint.getElementsByTagName("input")[0].checked = true;
    } else {
        lowerCaseHint.classList.remove("form-check-success");
        lowerCaseHint.classList.add("form-check-danger");
        lowerCaseHint.getElementsByTagName("input")[0].checked = false;
    }

    if (passwordInput.value.match(regexNumber)) {
        oneNumberHint.classList.remove("form-check-danger");
        oneNumberHint.classList.add("form-check-success");
        oneNumberHint.getElementsByTagName("input")[0].checked = true;
    } else {
        oneNumberHint.classList.remove("form-check-success");
        oneNumberHint.classList.add("form-check-danger");
        oneNumberHint.getElementsByTagName("input")[0].checked = false;
    }

    if (passwordInput.value.match(regexSpecialCharacter)) {
        specialCharacterHint.classList.remove("form-check-danger");
        specialCharacterHint.classList.add("form-check-success");
        specialCharacterHint.getElementsByTagName("input")[0].checked = true;
    } else {
        specialCharacterHint.classList.remove("form-check-success");
        specialCharacterHint.classList.add("form-check-danger");
        specialCharacterHint.getElementsByTagName("input")[0].checked = false;
    }


    if (passwordInput.value.length >= 8) {
        minCharacters.classList.remove("form-check-danger");
        minCharacters.classList.add("form-check-success");
        minCharacters.getElementsByTagName("input")[0].checked = true;
    } else {
        minCharacters.classList.remove("form-check-success");
        minCharacters.classList.add("form-check-danger");
        minCharacters.getElementsByTagName("input")[0].checked = false;
    }

    if (passwordInput.value.length <= 64) {
        maxCharacters.classList.remove("form-check-danger");
        maxCharacters.classList.add("form-check-success");
        maxCharacters.getElementsByTagName("input")[0].checked = true;
    } else {
        maxCharacters.classList.remove("form-check-success");
        maxCharacters.classList.add("form-check-danger");
        maxCharacters.getElementsByTagName("input")[0].checked = false;
    }

}


