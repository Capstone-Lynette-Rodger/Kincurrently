let current = new Date();
let monthArray = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
let dayArray = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];

currentDate = dayArray[current.getUTCDay()] + ", " + monthArray[current.getUTCMonth()] + " " + current.getUTCDate() + ', ' + current.getUTCFullYear();
$(".todaysDate").text(currentDate);

$.each($(".changeDate"), (index, element) => {
    console.log(element.textContent);
    if (element.textContent == "") {
        let parent = element.parentNode;
    parent.parentNode.removeChild(parent);
    } else {

    let date = new Date(element.textContent);
    element.innerHTML = dayArray[date.getUTCDay()] + ", " + monthArray[date.getUTCMonth()] + " " + date.getUTCDate();
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

let today = new Date().toISOString().split('T')[0];
$.each($('input[type="date"]'), (index, input) => {
    if(input.name !== 'birthdate') {
        input.setAttribute('min', today-1);
    }
});
