

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
document.getElementsByName("completed_by")[0].setAttribute('min', today);
// let editorArray = document.getElementsByClassName("nicEditor");
// for (let i = 0; i < editorArray.length; ++i) {
//     nicEditors.editors.push(
//         new nicEditor().panelInstance(
//             editorArray[i]
//         )
//     );
// }