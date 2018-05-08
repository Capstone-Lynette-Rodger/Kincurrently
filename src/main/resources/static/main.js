let current = new Date();
let monthArray = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
let dayArray = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
let abbMonthArray = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sept", "Oct", "Nov", "Dec"];
let abbDayArray = ["Sun", "Mon", "Tues", "Wed", "Thur", "Fri", "Sat"];

currentDate = dayArray[current.getDay()] + ", " + monthArray[current.getMonth()] + " " + current.getDate() + ', ' + current.getFullYear();
$(".todaysDate").text(currentDate);

//sets a minimum date of today on date inputs, sets a maximum date of today on birthdays, and sets dates that begin empty to today
let today = new Date();
today.setHours(today.getHours()-5);
today = today.toISOString().split('T')[0];

// addDays ads the number of days specified to the date specified
let addDays = (date, days) => {
    let result = new Date(date);
    result.setDate(result.getDate() + days);
    return result;
};

$.each($('input[type="date"]'), (index, input) => {
    if (!input.value) {
        input.setAttribute('value', today)
    }
    if(input.name === 'searchStartDate' || input.name === 'searchEndDate') {
        if(input.name === 'searchEndDate') {
            input.setAttribute('value', addDays(today, 6).toISOString().split('T')[0]);
        }
    } else if(input.name !== 'birthdate') {
        input.setAttribute('min', today);
    } else {
        input.setAttribute('max', today);
    }
});


let changeEventView = () => {
    let startDate = new Date($('#searchStartDate').val());
    let endDate = new Date($('#searchEndDate').val());
    startDate.setHours(startDate.getHours()+5);
    endDate.setHours(endDate.getHours()+5);
    $.each($('.date.event'), (index, element) => {
        let date = new Date(element.textContent.replace(/-/g, '\/'));
        if(startDate > date || endDate < date) {
            element.parentNode.parentNode.setAttribute("hidden", "hidden");
        }
    });
};
changeEventView();
$('#searchStartDate, #searchEndDate').change(() => {
    $.each($('.date.event'), (index, element) => {
        element.parentNode.parentNode.removeAttribute("hidden");
    });
    changeEventView();
});

let changeTaskView = () => {
    $.each($('.date.task'), (index, element) => {
        let date = new Date(element.textContent.replace(/-/g, '\/'));
        switch($('#taskView').val()) {
            case "day":
                if(current < date) {
                    element.parentNode.parentNode.setAttribute("hidden", "hidden");
                }
                break;
            case "week":
                if(addDays(current, 6)  < date) {
                    element.parentNode.parentNode.setAttribute("hidden", "hidden");
                }
                break;
            case "month":
                if(current.getUTCFullYear() < date.getUTCFullYear() || date.getUTCMonth() > addDays(current.getUTCMonth(), 30)) {
                    element.parentNode.parentNode.setAttribute("hidden", "hidden");
                }
                break;
            case "all":
                break;
        }
    });
};
changeTaskView();
$('#taskView').change(() => {
    $.each($('.date.task'), (index, element) => {
        element.parentNode.parentNode.removeAttribute("hidden");
    });
    changeTaskView();
});
//changes the display of the time to am and pm
$.each($(".changeTime"), (index, element) => {
    if(element.textContent == "") {
        let parent = element.parentNode;
        element.parentNode.parentNode.removeChild(parent);
    } else {
        let time = new Date("2018-01-01 " + element.textContent);
        let hours = time.getHours();
        let minutes = time.getMinutes();
        let ampm = hours >= 12 ? 'pm' : 'am';
        hours = hours % 12;
        hours = hours ? hours : 12;
        minutes = minutes < 10 ? '0' + minutes : minutes;
        element.textContent = hours + ':' + minutes + ' ' + ampm;
    }
});

//changes the display of the date to abbreviated date, month, day, and year if different from current
$.each($(".changeDate"), (index, element) => {
    if (element.textContent == "") {
        let parent = element.parentNode;
        parent.parentNode.removeChild(parent);
    } else {
        let date = new Date(element.textContent);
        if(element.classList.contains("due") && (current.getFullYear() >= date.getUTCFullYear() && date.getUTCMonth() <= current.getMonth() && date.getUTCDate() < current.getDate())) {
            element.style.color = "#ca0d0d";
        }
        element.innerHTML = abbDayArray[date.getUTCDay()] + ", " + abbMonthArray[date.getUTCMonth()] + " " + date.getUTCDate();
        if (current.getUTCFullYear() !== date.getUTCFullYear()) {
            element.innerHTML += ', ' + date.getUTCFullYear();
        }
    }
});

$.each($(".fullDate"), (index, element) => {
    if (element.textContent == "") {
        let parent = element.parentNode;
        parent.parentNode.removeChild(parent);
    } else {
        let date = new Date(element.textContent);
        if(element.classList.contains("due") && (current.getFullYear() >= date.getUTCFullYear() && date.getUTCMonth() <= current.getMonth() && date.getUTCDate() < current.getDate())) {
            element.style.color = "#ca0d0d";
        }
        element.innerHTML = dayArray[date.getUTCDay()] + ", " + monthArray[date.getUTCMonth()] + " " + date.getUTCDate() + ', ' + date.getUTCFullYear();
    }
});

$.each($(".birthDate"), (index, element) => {
    let date = new Date(element.textContent);
    element.innerHTML = abbMonthArray[date.getUTCMonth()] + " " + date.getUTCDate() + ', ' + date.getUTCFullYear();
});

//changes display of form based on if they are joining an existing family
$('#joinIfExisting').click(()=> {
    $('#joinIfExisting').attr('checked', (index, attr) => {
        return attr === "checked" ? null : "checked";
    });
    if($('#joinIfExisting').attr('checked')) {
        $("#showForNewFamily").hide();
    } else {
        $("#showForNewFamily").show();
    }
});

//opens up delete modal
$("#showDeleteForm").click(() => {
   $("#deleteModal").modal('show');
});

//changes navbar links to active if url matches anchor tag path
$(document).ready(() => {
    $('li.active').removeClass('active');
    $('a[href="' + location.pathname + '"]').closest('li').addClass('active');
});

//hides time inputs for events if allday is checked
$('input[type="time"]').parent().hide();
$('#allDay').click(()=> {
    $('#allDay').attr('checked', (index, attr) => {
        return attr === "checked" ? null : "checked";
    });
    if($('#allDay').attr('checked')) {
        $('input[type="time"]').parent().hide();
    } else {
        $('input[type="time"]').parent().show();
    }
});
