let current = new Date();
let monthArray = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
let dayArray = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
let abbMonthArray = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sept", "Oct", "Nov", "Dec"];
let abbDayArray = ["Sun", "Mon", "Tues", "Wed", "Thur", "Fri", "Sat"];

currentDate = dayArray[current.getDay()] + ", " + monthArray[current.getMonth()] + " " + current.getDate() + ', ' + current.getFullYear();
$(".todaysDate").text(currentDate);

let addDays = (date, days) => {
    let result = new Date(date);
    result.setDate(result.getDate() + days);
    return result;
};

let changeView = () => {
    $.each($('.date'), (index, element) => {
        let date = new Date(element.textContent.replace(/-/g, '\/'));
        switch($('#taskView').val()) {
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
                if(current.getUTCFullYear() !== date.getUTCFullYear() || date.getUTCMonth() !== current.getUTCMonth()) {
                    element.parentNode.parentNode.setAttribute("hidden", "hidden");
                }
                break;
            case "all":
                break;
        }
    });
};
changeView();
$('#taskView').change(() => {
    $.each($('.date'), (index, element) => {
        element.parentNode.parentNode.removeAttribute("hidden");
    });
    changeView();
});


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
$.each($(".changeDate"), (index, element) => {
    if (element.textContent == "") {
        let parent = element.parentNode;
        parent.parentNode.removeChild(parent);
    } else {
        let date = new Date(element.textContent);
        element.innerHTML = abbDayArray[date.getUTCDay()] + ", " + abbMonthArray[date.getUTCMonth()] + " " + date.getUTCDate();
        if (current.getUTCFullYear() !== date.getUTCFullYear()) {
            element.innerHTML += ', ' + date.getUTCFullYear();
        }
    }
});

$('#joinIfExisting').click(()=> {
    $('#joinIfExisting').attr('checked', function(index, attr){
        return attr == "checked" ? null : "checked";
    });
    if($('#joinIfExisting').attr('checked')) {
        $("#showForNewFamily").hide();
    } else {
        $("#showForNewFamily").show();
    }
});

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

$("#showDeleteForm").click(() => {
   $("#deleteModal").modal('show');
});
