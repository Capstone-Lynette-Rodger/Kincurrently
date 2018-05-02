let current = new Date();
let monthArray = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
let dayArray = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];

currentDate = dayArray[current.getUTCDay()] + ", " + monthArray[current.getUTCMonth()] + " " + current.getUTCDate() + ', ' + current.getUTCFullYear();
$(".todaysDate").text(currentDate);

// $.each($(".changeDate"), (index, element) => {
//     let date = new Date(element.textContent);
//     element.innerHTML = dayArray[date.getUTCDay()] + ", " + monthArray[date.getUTCMonth()] + " " + date.getUTCDate();
//     if(current.getUTCFullYear() !== date.getUTCFullYear()) {
//         element.innerHTML += ', ' + date.getUTCFullYear();
//     }
// });

let changeView = () => {
    $.each($('.date'), (index, element) => {
        let date = new Date(element.textContent);
        console.log(element.textContent);
        console.log(date);
        switch($('#taskView').val()) {
            case "day":
                if(current.getUTCFullYear() !== date.getUTCFullYear() || current.getUTCMonth() !== date.getUTCMonth() || current.getUTCDate() !== date.getUTCDate()) {
                    let task = element.parentNode.parentNode;
                    task.parentNode.setAttribute("hidden");
                }
                break;
            case "week":
                console.log(current+7);
                if(date > current + 7  || date < current) {
                    let task = element.parentNode.parentNode;
                    task.parentNode.removeChild(task);
                }
                break;
            case "month":
                if(current.getUTCFullYear() !== date.getUTCFullYear() || date.getUTCMonth() !== current.getUTCMonth()) {
                    let task = element.parentNode.parentNode;
                    task.parentNode.removeChild(task);
                }
                break;
        }
    });
};
changeView();
$('#taskView').change(changeView());

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
