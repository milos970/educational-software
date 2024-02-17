
    function showReg()
    {
        var upload = document.getElementById('upload');
        upload.style.display = 'none';

        var dead = document.getElementById('dead');
        dead.style.display = 'none';

        var reg = document.getElementById('reg');
        reg.style.display = 'block';

    }

   function showUpload()
        {
            var reg = document.getElementById('reg');
            reg.style.display = 'none';

            var upload = document.getElementById('upload');
            upload.style.display = 'block';

            var dead = document.getElementById('dead');
                    dead.style.display = 'none';
        }


        function showDeadline()
        {
            var reg = document.getElementById('upload');
            reg.style.display = 'none';

            var upload = document.getElementById('dead');
            upload.style.display = 'block';
        }











        const username = document.getElementById("username");
        const password = document.getElementById("password");
        const repPassword = document.getElementById("repPassword");

        const button = document.getElementById("button");
        

        const usernameHint = document.getElementById("username-hint");
        const passwordHint = document.getElementById("password-hint");
        const repPasswordHint = document.getElementById("rep-password-hint");

        const upperCaseHint = document.getElementById("upperCase");
        const lowerCaseHint = document.getElementById("lowerCase");
        const oneNumberHint = document.getElementById("oneNumber");
        const specialCharacterHint = document.getElementById("specialCharacter");
        const rangeHint = document.getElementById("range");


        const usernameLogin = document.getElementById("form1Example13");
        const passwordLogin = document.getElementById("form1Example23");

        let validPassword = true;
        let validUsername = true;
        let validRepPassword = true;

        const tim = document.getElementById("par");

        window.addEventListener("load", myInit, true);

        function myInit() {
            disableSubmitButton();

        }



        function usernameValidation() {
            validUsername = true;

            if (username.value === "") { //nac posielat cez ajax prazdne heslo ze
                validUsername = false;
                canSubmit();
                return;
            }


            console.log(5454);



            jQuery.ajax({
                url: "/check-username",
                data: 'username=' + $("#username").val(),
                type: "GET",
                success: function (data) {
                    if (data === true) {
                        usernameHint.innerHTML = "Používateľské meno existuje!";
                        validUsername = false;
                        canSubmit();
                    } else {
                        if ( validUsername || (username.value === "")) {
                            usernameHint.innerHTML = "";
                            canSubmit();
                        }

                    }

                },
                error: function (xhr, ajaxOptions, thrownError) {

                },
            });


        }

        function passwordValidation() {
            const regexUpperCase = /[A-Z]/g;
            const regexLowerCase = /[a-z]/g;
            const regexNumber = /[0-9]/g;
            const regexSpecialCharacter = /[?.:!@#$%^&*()_-]/g;
            validPassword = true;



            if (password.value.match(regexUpperCase)) {
                upperCaseHint.style.color = "green";
            } else {
                upperCaseHint.style.color = "white";
                validPassword = false;
            }

            if (password.value.match(regexLowerCase)) {
                lowerCaseHint.style.color = "green";
            } else {
                lowerCaseHint.style.color = "white";
                validPassword = false;
            }

            if (password.value.match(regexNumber)) {
                oneNumberHint.style.color = "green";
            } else {
                oneNumberHint.style.color = "white";
                validPassword = false;
            }

            if (password.value.match(regexSpecialCharacter)) {
                specialCharacterHint.style.color = "green";
            } else {
                specialCharacterHint.style.color = "white";
                validPassword = false;
            }


            if (password.value.length >= 12 ) {
                rangeHint.style.color = "green";
            } else {
                rangeHint.style.color = "white";
                validPassword = false;
            }


            this.repPasswordValidation();

            canSubmit();

        }




        function repPasswordValidation() {

            validRepPassword = true;

            if ( (password.value === repPassword.value) || (repPassword.value === "" && password.value() !== "")) {
                repPasswordHint.innerHTML = "&#8203";
            } else {
                repPasswordHint.innerHTML = "Heslá sa nezhodujú!";
                validRepPassword = false;
            }

            canSubmit();
        }

        function canSubmit() {


            button.disabled = !(validUsername &&  validPassword && validRepPassword);
        }

        function disableSubmitButton() {
            button.disabled = true;
        }

        function isFieldEmpty()
        {
            if (usernameLogin.value === "" || passwordLogin.value === "") {
                button.disabled = true;
            } else {
                button.disabled = false;
            }
        }


