let workCountdown;
let breakCountdown;
const timerDisplay = document.querySelector('.display-time-left');
const buttons = document.querySelectorAll('[data-time]');
document.getElementById("message").innerHTML = "enter a time and submit to start the clock";
var repetitionTracker;

function timer(workSeconds, breakSeconds, repetitions) {
    clearInterval(workCountdown);
    clearInterval(breakCountdown);

    if (workSeconds == 0) {
        document.getElementById('background').style.backgroundColor = 'white';
        document.getElementById("message").innerHTML = "timer has stopped!";
        document.getElementById("currentIteration").innerHTML = "0";
        displayTimeRemaining(0);
        return;
    }

    const workNow = Date.now();
    const workThen = workNow + workSeconds * 1000;
    displayTimeRemaining(workSeconds);
    document.getElementById("message").innerHTML = "start studying now!";

    document.getElementById('background').style.backgroundColor = 'ivory';
    workCountdown = setInterval(() => {

        const workSecondsRemaining = Math.round((workThen - Date.now()) / 1000);

        if (workSecondsRemaining != 0) {
            displayTimeRemaining(workSecondsRemaining);
        } else if (workSecondsRemaining == 0 && !breakSeconds) {
            displayTimeRemaining(workSecondsRemaining);
            clearInterval(workCountdown);
            document.getElementById('background').style.backgroundColor = 'honeydew';
            document.getElementById("message").innerHTML = "studying is now over!";
            return;
        } else if (workSecondsRemaining == 0) {
            displayTimeRemaining(workSecondsRemaining);
            clearInterval(workCountdown);
            // starts the break timer once the working timer reaches 0
            const breakNow = Date.now();
            const breakThen = breakNow + breakSeconds * 1000;
            breakCountdown = setInterval(() => {
                document.getElementById('background').style.backgroundColor = 'lavender';
                const breakSecondsRemaining = Math.round((breakThen - Date.now()) / 1000) + 1;
                if (breakSecondsRemaining != 0) {
                    document.getElementById("message").innerHTML = "take a break!";
                    displayTimeRemaining(breakSecondsRemaining);
                } else if (breakSecondsRemaining == 0) {
                    displayTimeRemaining(breakSecondsRemaining);
                    clearInterval(breakCountdown);
                    if (repetitionTracker < repetitions) {
                        repetitionTracker++;
                        document.getElementById("currentIteration").innerHTML = repetitionTracker;
                        timer(workSeconds, breakSeconds, repetitions);
                    } else {
                        repetitionTracker = 0;
                        document.getElementById('background').style.backgroundColor = 'honeydew';
                        document.getElementById("message").innerHTML = "studying is now over!";
                        document.getElementById("currentIteration").innerHTML = "0";
                        displayTimeRemaining(0);
                    }
                    return;
                } else if (breakSecondsRemaining < 0) {
                    clearInterval(workCountdown);
                    clearInterval(breakCountdown);
                    return;
                }
            }, 1000);
        } else if (workSecondsRemaining < 0) {
            clearInterval(workCountdown);
            clearInterval(breakCountdown);
            return;
        }
    }, 1000);
}

function displayTimeRemaining(seconds) {
    const minutes = Math.floor(seconds / 60);
    const remainderSeconds = seconds % 60;
    const display = `${minutes < 10 ? '0' : ''}${minutes}:${remainderSeconds < 10 ? '0' : ''}${remainderSeconds}`;
    timerDisplay.textContent = display;
    document.title = display;

//    console.log({minutes, remainderSeconds});
}

document.customInput.addEventListener("submit", function(e) {
    e.preventDefault();

    const workMinutes = this.workMinutes.value;
    const breakMinutes = this.breakMinutes.value;
    const repetitions = this.repetitions.value;

    const studySession = (workMinutes * 60) / 60;
    const studyBreak = (breakMinutes * 60) / 60;
    document.getElementById("studySession").innerHTML = `${studySession} minutes`;
    document.getElementById("studyBreak").innerHTML = `${studyBreak} minutes`;
    document.getElementById("repetitions").innerHTML = `${repetitions}`;
    document.getElementById("currentIteration").innerHTML = "1";
    repetitionTracker = 1;
    timer(workMinutes * 60, breakMinutes * 60, repetitions);


    this.reset();
})

// this function is for the buttons
function startTimer() {
    const seconds = parseInt(this.dataset.time);

    timer(seconds);
}

buttons.forEach(button => button.addEventListener('click', startTimer));