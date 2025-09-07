

document.addEventListener("DOMContentLoaded", function() {
    whichFormulatorToShow(1);
    disableSubmitButton();
});

function disableSubmitButton()
{
    const submitButton = document.getElementById('submit-button');
    submitButton.disabled = true;
}

function enableSubmitButton()
{
    const submitButton = document.getElementById('submit-button');
    submitButton.disabled = false;
}


let which = 0;
function whichFormulatorToShow(value) {
    const employeeForm = document.getElementById("employee-form");

    if (value === 1) {
        showFormForStudent();
        studentRadio.checked = true;
    } else if (value === 2) {
        showFormForEmployee();
        employeeRadio.checked = true;
        if (employeeForm) {
        employeeForm.action = "/person/create";
        }
    }

    which = value;
}


const studentRadio = document.getElementById("option1-radio");

studentRadio.addEventListener("change", function() {
    if (this.checked) {
        showFormForStudent();
    }
});

const employeeRadio = document.getElementById("option2-radio");

employeeRadio.addEventListener("change", function() {
    if (this.checked) {
        showFormForEmployee();
    }
});




const nameInput = document.getElementById("name-input");
const nameInputError = document.getElementById("name-input-error-hint");
nameInput.addEventListener("change", function () {
    if (!nameInput.value.trim()) {
        nameInputError.textContent = "Zadajte meno";
        nameInput.classList.add("form-input-invalid");
        nameInput.classList.remove("form-input-valid");
    } else {
        nameInputError.textContent = "";
        nameInput.classList.add("form-input-valid");
        nameInput.classList.remove("form-input-invalid");
    }
});


const surnameInput = document.getElementById("surname-input");
const surnameInputError = document.getElementById("surname-input-error-hint");
surnameInput.addEventListener("change", function () {
    if (!surnameInput.value.trim()) {
        surnameInputError.textContent = "Zadajte priezvisko";
        surnameInput.classList.add("form-input-invalid");
        surnameInput.classList.remove("form-input-valid");
    } else {
        surnameInputError.textContent = "";
        surnameInput.classList.add("form-input-valid");
        surnameInput.classList.remove("form-input-invalid");
    }
});

const emailInput = document.getElementById("employee-email-input");
const emailInputError = document.getElementById("employee-email-input-error-hint");
emailInput.addEventListener("change", function () {
    const regex = /^[a-zA-Z0-9._%+-]+@fri\.uniza\.sk$/;

    if (!emailInput.value.trim()) {
        emailInputError.textContent = "Zadajte email";
        emailInput.classList.add("form-input-invalid");
        emailInput.classList.remove("form-input-valid");

    } else if (!regex.test(emailInput.value)) {
        emailInputError.textContent = "Nevalidný email";
        emailInput.classList.add("form-input-invalid");
        emailInput.classList.remove("form-input-valid");

    } else {
        emailInputError.textContent = "";
        emailInput.classList.add("form-input-valid");
        emailInput.classList.remove("form-input-invalid");
    }
});


const pinInput = document.getElementById("pin-input");
const pinInputError = document.getElementById("pin-input-error-hint");
pinInput.addEventListener("change", function () {


    if (!pinInput.value.trim()) {
        pinInputError.textContent = "Zadajte osobné číslo";
        pinInput.classList.add("form-input-invalid");
        pinInput.classList.remove("form-input-valid");

    } else if (!regex.test(pinInput.value)) {
        pinInputError.textContent = "Nevalidné osobné číslo";
        pinInput.classList.add("form-input-invalid");
        pinInput.classList.remove("form-input-valid");

    } else {
        pinInputError.textContent = "";
        pinInput.classList.add("form-input-valid");
        pinInput.classList.remove("form-input-invalid");
    }
});

const passwordInput = document.getElementById("password-input");
const passwordInputError = document.getElementById("password-input-error-hint");
passwordInput.addEventListener("input", checkPassword());


function checkPassword() {
    const rules = [
            { test: v => /[A-Z]/.test(v), elementId: "upper-case-checkbox-div" },
            { test: v => /[a-z]/.test(v), elementId: "lower-case-checkbox-div" },
            { test: v => /[0-9]/.test(v), elementId: "number-checkbox-div" },
            { test: v => /[?.:!@#$%^|<>&*~()+_\-{}\[\],=;/\\'"]/.test(v), elementId: "special-char-checkbox-div" },
            { test: v => v.length >= 8, elementId: "min-chars-checkbox-div" },
            { test: v => v.length > 0 && v.length <= 64, elementId: "max-chars-checkbox-div" }
        ];

        let isValid = true;

        rules.forEach(rule => {
            const element = document.getElementById(rule.elementId);
            const check = element.querySelector("input[type=checkbox]");
            const passed = rule.test(passwordInput.value);

            element.classList.toggle("form-check-success", passed);
            element.classList.toggle("form-check-danger", !passed);

            check.checked = passed;

            if (!passed) isValid = false;
        });


        if (!isValid) {
            passwordInput.classList.add("form-input-invalid");
            passwordInput.classList.remove("form-input-valid");
        } else {
            passwordInput.classList.add("form-input-valid");
            passwordInput.classList.remove("form-input-invalid");
            checkPasswordMatch();
        }
}

function checkPasswordMatch() {
    if (passwordInput.value !== repPasswordInput.value) {
        repPasswordInputError.textContent = "Heslá sa nezhodujú";
        repPasswordInput.classList.add("form-input-invalid");
        repPasswordInput.classList.remove("form-input-valid");
    } else {
        repPasswordInputError.textContent = "";
        repPasswordInput.classList.add("form-input-valid");
        repPasswordInput.classList.remove("form-input-invalid");
    }
}


const repPasswordInput = document.getElementById("rep-password-input");
const repPasswordInputError = document.getElementById("rep-password-input-error-hint");
repPasswordInput.addEventListener("change", checkPasswordMatch());




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


function canRegisterStudent() {
    const emailInput = document.getElementById("student-email-input");
    const emailErrorHint = document.getElementById("student-email-input-error-hint");

    if (!emailInput.value.trim()) {
        emailErrorHint.textContent = "Zadajte školský email!";
        return false;
    }

    const regexEmail = /^[a-zA-Z0-9._%+-]+@stud\.uniza\.sk$/;
    if (!regexEmail.test(emailInput.value)) {
        emailErrorHint.textContent = "Nevalidný školský email!";
        return false;
    }

    emailErrorHint.textContent = "";
    return true;
}


function canRegisterEmployee() {
    const fields = [
        { id: "name-input", errorId: "name-input-error-hint", required: true, maxLength: 50, name: "meno" },
        { id: "surname-input", errorId: "surname-input-error-hint", required: true, maxLength: 50, name: "priezvisko" },
        { id: "employee-email-input", errorId: "employee-email-input-error-hint", required: true, regex: /^[a-zA-Z0-9._%+-]+@fri\.uniza\.sk$/, regexMsg: "Nevalidný školský email!", name: "email" },
        { id: "pin-input", errorId: "pin-input-error-hint", required: true, regex: /^\d{5}$/, regexMsg: "Nevalidné osobné číslo!", name: "osobné číslo" },
        { id: "password-input", errorId: "password-input-error-hint", required: true, name: "heslo" },
        { id: "rep-password-input", errorId: "rep-password-input-error-hint", required: true, name: "heslo znova" }
    ];

    for (const field of fields) {
        const input = document.getElementById(field.id);
        const error = document.getElementById(field.errorId);

        if (field.required && !input.value.trim()) {
            error.textContent = `Zadajte ${field.name}!`;
            return false;
        }

        if (field.maxLength && input.value.length > field.maxLength) {
            error.textContent = `Viac než ${field.maxLength} znakov!`;
            return false;
        }

        if (field.regex && !field.regex.test(input.value)) {
            error.textContent = field.regexMsg;
            return false;
        }

        error.textContent = "";
    }


    const password = document.getElementById("password-input").value;
    const repPassword = document.getElementById("rep-password-input").value;
    const repPasswordError = document.getElementById("rep-password-input-error-hint");

    if (password !== repPassword) {
        repPasswordError.textContent = "Heslá sa nezhodujú!";
        return false;
    } else {
        repPasswordError.textContent = "";
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


function submit() {
    const forms = {
        1: { validator: canRegisterStudent, formId: "student-form" },
        2: { validator: () => canRegisterEmployee() && isValid, formId: "employee-form" }
    };

    const current = forms[which];
    if (current && current.validator()) {
        document.getElementById(current.formId).submit();
    }
}


function submitChangedPassword() {
    const fields = [
        { id: "old-password-input", errorId: "old-password-input-error-hint", message: "Zadajte staré heslo!" },
        { id: "password-input", errorId: "password-input-error-hint", message: "Zadajte nové heslo!" },
        { id: "rep-password-input", errorId: "rep-password-input-error-hint", message: "Zadajte znova nové heslo!" }
    ];

    let valid = true;

    fields.forEach(field => {
        const input = document.getElementById(field.id);
        const error = document.getElementById(field.errorId);

        if (!input.value.trim()) {
            error.textContent = field.message;
            valid = false;
        } else {
            error.textContent = "";
        }
    });

    const password = document.getElementById("password-input").value;
    const repPassword = document.getElementById("rep-password-input").value;
    const repPasswordError = document.getElementById("rep-password-input-error-hint");

    if (password && repPassword && (password !== repPassword) ) {
        repPasswordError.textContent = "Heslá sa nezhodujú!";
        valid = false;
    }

    if (valid && isValid) {
        document.getElementById("form").submit();
    }
}



