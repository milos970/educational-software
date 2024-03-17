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


function openPdf(id)
{


    const xhttp = new XMLHttpRequest();


    xhttp.onload = function()
    {


        var url = window.URL.createObjectURL(xhttp.response);
        // Open the PDF in a new tab
        window.open(url);

    }

    let url = "/admin/file/pdf/" + id;

    xhttp.open("GET", url, true);
    xhttp.send();
}

function deletePdf(id)
{


    const xhttp = new XMLHttpRequest();


    xhttp.onload = function()
    {

        const element = document.getElementById(id);
        element.remove();

    }

    let url = "/admin/file/pdf/delete/" + id;

    xhttp.open("DELETE", url, true);
    xhttp.send();
}

