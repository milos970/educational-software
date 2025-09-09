async function sendPoints(id) {
    const pointsInput = document.getElementById("points-input-" + id);

    try {
        const response = await fetch('/teacher/students/' + id + '/points', {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json',
                [csrfHeader]: csrfToken
            },
            body: JSON.stringify({
                points: Number(pointsInput.value)
            })
        });

        if (!response.ok) {
            throw new Error('Nepodarilo sa odoslať body');
        }

       addPoints(id);

    } catch (error) {
        console.error(error);
    }
}

function addPoints(id) {
    const pointsInput = document.getElementById("points-input-" + id);
    const el = document.getElementById("points-" + id);


    const addedPoints = Number(pointsInput.value);


    let currentPoints = Number(el.dataset.points);


    currentPoints += addedPoints;


    el.dataset.points = currentPoints;
    el.textContent = currentPoints;

    pointsInput.value = '';
}

function addAbsences(id) {
    const absencesInput = document.getElementById("absences-input-" + id);
    const el = document.getElementById("absences-" + id);


    const addedAbsences = Number(absencesInput.value);


    let currentAbsences = Number(el.dataset.points);


    currentAbsences += addedAbsences;


    el.dataset.points = currentAbsences;
    el.textContent = currentAbsences;

    absencesInput.value = '';
}



async function sendAbsences(id)
{
const absences = document.getElementById("absences-input-" + id);
    try {
        const response = await fetch('/teacher/students/' + id + '/absences', {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json',
                [csrfHeader]: csrfToken
            },
            body: JSON.stringify({
                absences: Number(absences.value)
            })
        });

        if (!response.ok) {
            throw new Error('Nepodarilo sa odoslať správu');
        }

        addAbsences(id);

    } catch (error) {
        console.error(error);
    }
}