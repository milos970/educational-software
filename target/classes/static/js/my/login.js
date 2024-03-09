function canSubmit()
{
    const nameInput = document.getElementById("name-input");
    const passwordInput = document.getElementById("password-input");
    const nameInputErrorHint = document.getElementById("name-input-error-hint");
    const passwordInputErrorHint = document.getElementById("password-input-error-hint");

    if (nameInput.value === "")
    {
        nameInputErrorHint.innerHTML = "Zadajte meno!";

        return false;
    } else {
        nameInputErrorHint.innerHTML = "";
    }


    if (passwordInput.value === "")
    {
        passwordInputErrorHint.innerHTML = "Zadajte heslo!";
        return false;
    }else {
        passwordInputErrorHint.innerHTML = "";
    }

    return true;
}