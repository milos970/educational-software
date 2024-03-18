







function clearTable()
{
    $("#table tr").remove();
}



function saveToFile()
{


    const csvContent = array.map(row => row.join(';')).join('\n');

    const blob = new Blob([csvContent], { type: 'text/csv' });

    const link = document.createElement('a');

    link.href = window.URL.createObjectURL(blob);

    let fileName = "";

    switch(methods.value)
    {
        case "1":
            fileName = "Newtnová metoda";
            break;

        case "2":
            fileName = "Regula falsi";
            break;

        case "3":
            fileName = "Bisekcia";
            break;

    }

    link.download = fileName + ".csv";

    document.body.appendChild(link);
    link.click();

    document.body.removeChild(link);
}





