function sendData(id, type) {
    const xhttp = new XMLHttpRequest();
    const input = document.getElementById(`${type}-input-${id}`);

    xhttp.onload = function() {
        if (xhttp.status === 200) {
            const tableCell = document.getElementById(`${type}-${id}`);
            const num = Number.parseInt(input.value) + Number.parseInt(tableCell.innerText);
            tableCell.innerText = num;
        }
        input.value = "";
    }

    const url = `/admin/student/${id}/${type}/${input.value}`;

    xhttp.open("PATCH", url, true);
    xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhttp.send();
}
