$(function() {
    var Accordion = function(el, multiple) {
        this.el = el || {};
        this.multiple = multiple || false;

        var links = this.el.find('.link');

        links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown)
    }

    Accordion.prototype.dropdown = function(e) {
        var $el = e.data.el;
        $this = $(this),
            $next = $this.next();

        $next.slideToggle();
        $this.parent().toggleClass('open');

        if (!e.data.multiple) {
            $el.find('.submenu').not($next).slideUp().parent().removeClass('open');
        };
    }

    var accordion = new Accordion($('#accordion'), false);
});


document.addEventListener("DOMContentLoaded", function() {
    const element = document.getElementById("href1");
    const fileInput = document.getElementById("upload-non-linear");

    element.addEventListener("click", function() {
        fileInput.click() // Programmatically trigger click event on file input
    });

});


function openFile(id)
{


    const xhttp = new XMLHttpRequest();


    xhttp.onload = function()
    {

        if (xhttp.status === 200) {
            // Decode the Base64 string
            var decodedData = atob(xhttp.response);

            // Convert the decoded string to a Uint8Array
            var uint8Array = new Uint8Array(decodedData.length);
            for (var i = 0; i < decodedData.length; i++) {
                uint8Array[i] = decodedData.charCodeAt(i);
            }

            // Create a Blob object from the Uint8Array
            var blob = new Blob([uint8Array], { type: xhttp.getResponseHeader("Content-Type") });

            // Create a temporary link element to download the file
            var link = document.createElement('a');
            link.href = window.URL.createObjectURL(blob);

            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
        }



       /* const url = window.URL.createObjectURL(xhttp.response);
        window.open(url);*/

    }

    const url = "/person/material/file/" + id;

    xhttp.open("GET", url, true);
    xhttp.send();
}

function deleteFile(id)
{


    const xhttp = new XMLHttpRequest();


    xhttp.onload = function()
    {

        const element = document.getElementById(id);
        element.remove();

    }

    const url = "/admin/file/delete/" + id;

    xhttp.open("DELETE", url, true);
    xhttp.send();
}


function uploadFile()
{


    const xhttp = new XMLHttpRequest();

    xhttp.onload = function()
    {

        if (xhttp.status === 200)
        {

                let row = document.createElement("tr");
                let body = document.getElementsByTagName("tbody")[0];



                row.setAttribute("id", xhttp.responseText);


                var nameCell = document.createElement("td");
                var nameSpan = document.createElement("span");
                nameSpan.textContent = document.getElementById("file-name-input").value;
                nameCell.appendChild(nameSpan);

                var descriptionCell = document.createElement("td");
                var descriptionSpan = document.createElement("span");
                descriptionSpan.textContent = document.getElementById("file-description").value;
                descriptionCell.appendChild(descriptionSpan);

                var showButtonCell = document.createElement("td");
                var showButton = document.createElement("button");
                showButton.setAttribute("type", "button");
                showButton.setAttribute("class", "btn btn-success btn-icon-text");
                showButton.setAttribute("onclick", "openFile('" + xhttp.responseText + "')");
                showButton.innerHTML = '<i class="mdi mdi-upload btn-icon-prepend"></i>Zobraziť';
                showButtonCell.appendChild(showButton);

                row.appendChild(nameCell);
                row.appendChild(descriptionCell);
                row.appendChild(showButtonCell);
                body.appendChild(row);

                var deleteButtonCell = document.createElement("td");
                    var deleteButton = document.createElement("button");
                    deleteButton.setAttribute("type", "button");
                    deleteButton.setAttribute("class", "btn btn-danger btn-icon-text");
                    deleteButton.setAttribute("onclick", "deleteFile('" + xhttp.responseText + "')");
                    deleteButton.innerHTML = '<i class="mdi mdi-upload btn-icon-prepend"></i>Odstrániť';
                    deleteButtonCell.appendChild(deleteButton);

                    row.appendChild(deleteButtonCell);




        }

    }

    const url = "/admin/material/upload";

    var formData = new FormData();
    formData.append("name", document.getElementById("file-name-input").value);
    formData.append("description", document.getElementById("file-description").value);
    formData.append("data", document.getElementById("file-input").files[0]);


    xhttp.open("POST", url, true);

    xhttp.send(formData);
}

