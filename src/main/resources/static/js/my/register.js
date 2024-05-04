

function initializeSignUpPage()
{
    studentOrEmployee(1);
}


let which = 0;
function studentOrEmployee(who)
{

    switch (who)
    {
        case 1:

            showFormForStudent();


            which = 1;
            break;
        case 2:
            showFormForEmployee();
            getById("employee-form").action = "/person/create";

            which = 2;
            break;
        default:

    }
}



function showFormForStudent()
{
    document.getElementById("student-form-div").style.display="block";
    document.getElementById("employee-form-div").style.display="none";
}


function showFormForEmployee()
{
    document.getElementById("employee-form-div").style.display="block";
    document.getElementById("student-form-div").style.display="none";

}








function canRegisterStudent()
{
    const emailInput = document.getElementById("student-email-input");
    const emailpasswordInputErrorHint = document.getElementById("student-email-input-error-hint");



    if (emailInput.value === "")
    {
        emailpasswordInputErrorHint.innerHTML = "Zadajte školský email!";
        return false;
    } else {
        emailpasswordInputErrorHint.innerHTML = "";
    }

    const regexEmail = /^[a-zA-Z0-9._%+-]+@stud\.uniza\.sk$/;
    if (!regexEmail.test(emailInput.value))
    {
        emailpasswordInputErrorHint.innerHTML = "";
    } else {
        emailpasswordInputErrorHint.innerHTML = "Nevalidný školský email!";
        return false;

    }


    return true;
}


function canRegisterEmployee()
{
    const nameInput = document.getElementById("name-input");
    const surnameInput = document.getElementById("surname-input");
    const passwordInput = document.getElementById("password-input");
    const repPasswordInput = document.getElementById("rep-password-input");
    const emailInput = document.getElementById("employee-email-input");
    const personalNumberInput = document.getElementById("pin-input");

    const nameInputErrorHint = document.getElementById("name-input-error-hint");
    const surnameInputErrorHint = document.getElementById("surname-input-error-hint");
    const emailInputErrorHint = document.getElementById("employee-email-input-error-hint");
    const personalNumberInputErrorHint = document.getElementById("pin-input-error-hint");
    const passwordInputErrorHint = document.getElementById("password-input-error-hint");
    const repPasswordInputErrorHint = document.getElementById("rep-password-input-error-hint");

    if (nameInput.value.length === 0)
    {
        nameInputErrorHint.innerHTML = "Zadajte meno!";
        return false;
    } else
    {
        nameInputErrorHint.innerHTML = "";
    }


    if (nameInput.value.length > 50)
    {
        nameInputErrorHint.innerHTML = "Viac než 50 znakov!";
        return false;
    } else
    {
        nameInputErrorHint.innerHTML = "";
    }

    if (surnameInput.value.length === 0)
    {
        surnameInputErrorHint.innerHTML = "Zadajte priezvisko!";
        return false;
    } else {
        surnameInputErrorHint.innerHTML = "";
    }

    if (surnameInput.value.length > 50)
    {
        surnameInputErrorHint.innerHTML = "Viac než 50 znakov!";
        return false;
    } else {
        surnameInputErrorHint.innerHTML = "";
    }



    if (emailInput.value.length === 0)
    {
        emailInputErrorHint.innerHTML = "Zadajte školský email!";
        return false;
    } else {
        emailInputErrorHint.innerHTML = "";
    }

    const regexEmail = /^[a-zA-Z0-9._%+-]+@fri\.uniza\.sk$/;
    if (!regexEmail.test(emailInput.value))
    {
        emailInputErrorHint.innerHTML = "Nevalidný školský email!";
        return false;
    } else {
        emailInputErrorHint.innerHTML = "";
    }

    if (personalNumberInput.value.length === 0)
    {
        personalNumberInputErrorHint.innerHTML = "Zadajte osobné číslo!";
        return false;
    } else {
        personalNumberInputErrorHint.innerHTML = "";
    }

    const regexPin = /^\d{5}$/;

    if (!regexPin.test(personalNumberInput.value))
    {
        personalNumberInputErrorHint.innerHTML = "Nevalidné osobné číslo!";
        return false;
    } else {
        personalNumberInputErrorHint.innerHTML = "";
    }

    if (passwordInput.value.length === 0)
    {
        passwordInputErrorHint.innerHTML = "Zadajte heslo!";
        return false;
    }else {
        passwordInputErrorHint.innerHTML = "";
    }


    if (repPasswordInput.value.length === 0)
    {
        repPasswordInputErrorHint.innerHTML = "Znova zadajte heslo!";
        return false;
    }else {
        repPasswordInputErrorHint.innerHTML = "";
    }


    if (repPasswordInput.value !== passwordInput.value)
    {
        repPasswordInputErrorHint.innerHTML = "Heslá sa nezhodujú!";
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

let isValid = false;

function validatePassword()
{
    const regexUpperCase = /[A-Z]/g;
    const regexLowerCase = /[a-z]/g;
    const regexNumber = /[0-9]/g;
    const regexSpecialCharacter = /[?.:!@#$%^|<>&*~()+_\-{}[\],=;/\\'"]/g;

    const passwordInput = document.getElementById("password-input");

    const upperCaseHint = document.getElementById("upper-case-checkbox-div");
    const lowerCaseHint = document.getElementById("lower-case-checkbox-div");
    const specialCharacterHint = document.getElementById("special-char-checkbox-div");
    const oneNumberHint = document.getElementById("number-checkbox-div");
    const minCharacters = document.getElementById("min-chars-checkbox-div");
    const maxCharacters = document.getElementById("max-chars-checkbox-div");




    isValid = true;

    if (passwordInput.value.match(regexUpperCase)) {
        upperCaseHint.classList.remove("form-check-danger");
        upperCaseHint.classList.add("form-check-success");
        upperCaseHint.getElementsByTagName("input")[0].checked = true;

    } else {
        upperCaseHint.classList.remove("form-check-success");
        upperCaseHint.classList.add("form-check-danger");
        upperCaseHint.getElementsByTagName("input")[0].checked = false;
        isValid = false;

    }


    if (passwordInput.value.match(regexLowerCase)) {
        lowerCaseHint.classList.remove("form-check-danger");
        lowerCaseHint.classList.add("form-check-success");
        lowerCaseHint.getElementsByTagName("input")[0].checked = true;
    } else {
        lowerCaseHint.classList.remove("form-check-success");
        lowerCaseHint.classList.add("form-check-danger");
        lowerCaseHint.getElementsByTagName("input")[0].checked = false;
        isValid = false;

    }

    if (passwordInput.value.match(regexNumber)) {
        oneNumberHint.classList.remove("form-check-danger");
        oneNumberHint.classList.add("form-check-success");
        oneNumberHint.getElementsByTagName("input")[0].checked = true;
    } else {
        oneNumberHint.classList.remove("form-check-success");
        oneNumberHint.classList.add("form-check-danger");
        oneNumberHint.getElementsByTagName("input")[0].checked = false;
        isValid = false;
    }

    if (passwordInput.value.match(regexSpecialCharacter)) {
        specialCharacterHint.classList.remove("form-check-danger");
        specialCharacterHint.classList.add("form-check-success");
        specialCharacterHint.getElementsByTagName("input")[0].checked = true;
    } else {
        specialCharacterHint.classList.remove("form-check-success");
        specialCharacterHint.classList.add("form-check-danger");
        specialCharacterHint.getElementsByTagName("input")[0].checked = false;
        isValid = false;
    }


    if (passwordInput.value.length >= 8) {
        minCharacters.classList.remove("form-check-danger");
        minCharacters.classList.add("form-check-success");
        minCharacters.getElementsByTagName("input")[0].checked = true;
    } else {
        minCharacters.classList.remove("form-check-success");
        minCharacters.classList.add("form-check-danger");
        minCharacters.getElementsByTagName("input")[0].checked = false;
        isValid = false;
    }

    if (passwordInput.value.length <= 64) {
        maxCharacters.classList.remove("form-check-danger");
        maxCharacters.classList.add("form-check-success");
        maxCharacters.getElementsByTagName("input")[0].checked = true;

    } else {
        maxCharacters.classList.remove("form-check-success");
        maxCharacters.classList.add("form-check-danger");
        maxCharacters.getElementsByTagName("input")[0].checked = false;
        isValid = false;
    }

    return isValid;

}


function submit()
{


    if (which === 1)
    {

        if (canRegisterStudent())
        {

            getById("student-form").submit();
        }
    }

    if (which === 2)
    {
        if (canRegisterEmployee() && isValid)
        {
            getById("employee-form").submit();
        }
    }
}


function submitChangedPassword()
{
    const passwordInput = document.getElementById("password-input");
    const oldPasswordInput = document.getElementById("old-password-input");
    const repPasswordInput = document.getElementById("rep-password-input");

    const passwordInputErrorHint = document.getElementById("password-input-error-hint");
    const oldPasswordInputErrorHint = document.getElementById("old-password-input-error-hint");
    const repPasswordInputErrorHint = document.getElementById("rep-password-input-error-hint");


    if (oldPasswordInput.value.length === 0)
    {
        oldPasswordInputErrorHint.innerHTML = "Zadajte staré heslo!";
        return false;
    } else {
        oldPasswordInputErrorHint.innerHTML = "";
    }

    if (passwordInput.value.length === 0)
    {
        passwordInputErrorHint.innerHTML = "Zadajte nové heslo!";
        return false;
    } else {
        passwordInputErrorHint.innerHTML = "";
    }

    if (repPasswordInput.value.length === 0)
    {
        repPasswordInputErrorHint.innerHTML = "Zadajte znova nové heslo!";
        return false;
    } else {
        repPasswordInputErrorHint.innerHTML = "";
    }

    if (repPasswordInput.value !== passwordInput.value)
    {
        repPasswordInputErrorHint.innerHTML = "Heslá sa nezhodujú!";
        return false;
    } else {
        repPasswordInputErrorHint.innerHTML = "";
    }


    if (isValid)
    {

        document.getElementById("form").submit();
    }



}


