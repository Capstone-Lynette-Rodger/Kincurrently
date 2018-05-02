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

let addDays = (date, days) => {
    let result = new Date(date);
    result.setDate(result.getDate() + days);
    return result;
}

let changeView = () => {
    $.each($('.date'), (index, element) => {
        let date = new Date(element.textContent.replace(/-/g, '\/'));
        switch($('#taskView').val()) {
            case "day":
                if(current.getUTCFullYear() !== date.getUTCFullYear() || current.getUTCMonth() !== date.getUTCMonth() || current.getUTCDate() !== date.getUTCDate()) {
                    element.parentNode.parentNode.setAttribute("hidden", "hidden");
                }
                break;
            case "week":
                if((current.getUTCFullYear() !== date.getUTCFullYear() || current.getUTCMonth() !== date.getUTCMonth() || current.getUTCDate() !== date.getUTCDate()) && (date >= addDays(current, 6)  || date < current)) {
                    element.parentNode.parentNode.setAttribute("hidden", "hidden");
                    console.log(date);
                    console.log(addDays(current, 6));
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
