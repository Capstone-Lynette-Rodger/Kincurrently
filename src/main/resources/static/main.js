let current = new Date();
let monthArray = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
let dayArray = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
let abbMonthArray = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sept", "Oct", "Nov", "Dec"];
let abbDayArray = ["Sun", "Mon", "Tues", "Wed", "Thur", "Fri", "Sat"];

currentDate = dayArray[current.getDay()] + ", " + monthArray[current.getMonth()] + " " + current.getDate() + ', ' + current.getFullYear();
$(".todaysDate").text(currentDate);

// addDays ads the number of days specified to the date specified
let addDays = (date, days) => {
    let result = new Date(date);
    result.setDate(result.getDate() + days);
    return result;
};

// changeView searches through all the dates with a date class and hides items that do not meet search criteria
let changeEventView = () => {
    $.each($('.date.event'), (index, element) => {
        let date = new Date(element.textContent.replace(/-/g, '\/'));
        switch($('#eventView').val()) {
            case "day":
                if(current.getFullYear() !== date.getUTCFullYear() || current.getMonth() !== date.getUTCMonth() || current.getDate() !== date.getUTCDate()) {
                    element.parentNode.parentNode.setAttribute("hidden", "hidden");
                }
                break;
            case "week":
                if((current.getFullYear() !== date.getUTCFullYear() || current.getMonth() !== date.getUTCMonth() || current.getDate() !== date.getUTCDate()) && (date >= addDays(current, 6)  || date < current)) {
                    element.parentNode.parentNode.setAttribute("hidden", "hidden");
                }
                break;
            case "month":
                if(current.getFullYear() !== date.getUTCFullYear() || date.getUTCMonth() !== current.getMonth()) {
                    element.parentNode.parentNode.setAttribute("hidden", "hidden");
                }
                break;
            case "all":
                break;
        }
    });
};
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
                if(current.getUTCFullYear() < date.getUTCFullYear() || date.getUTCMonth() > current.getUTCMonth()) {
                    element.parentNode.parentNode.setAttribute("hidden", "hidden");
                }
                break;
            case "all":
                break;
        }
    });
};
changeEventView();
changeTaskView();
$('#eventView').change(() => {
    $.each($('.date.event'), (index, element) => {
        element.parentNode.parentNode.removeAttribute("hidden");
    });
    changeEventView();
});
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
//sets a minimum date of today on date inputs, sets a maximum date of today on birthdays, and sets dates that begin empty to today
let today = new Date();
today.setHours(today.getHours()-5);
today = today.toISOString().split('T')[0];
$.each($('input[type="date"]'), (index, input) => {
    if(input.name !== 'birthdate') {
        input.setAttribute('min', today);
    }  else {
        input.setAttribute('max', today);
    }
    if (!input.value) {
        input.setAttribute('value', today)
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
$('input[type="time"]').hide();
$('#allDay').click(()=> {
    $('#allDay').attr('checked', (index, attr) => {
        return attr === "checked" ? null : "checked";
    });
    if($('#allDay').attr('checked')) {
        $('input[type="time"]').hide();
    } else {
        $('input[type="time"]').show();
    }
});
