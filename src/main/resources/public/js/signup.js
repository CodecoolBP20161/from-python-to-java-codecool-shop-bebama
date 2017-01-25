/**
 * Created by cickib on 2017.01.25..
 */

// autofocus on the signup form, jumps to next input field on enter
$(document).ready(function () {
    $("#nameField").focus();
    $('.form-control').keydown(function (e) {
        if (e.which === 13) {
            $("#nameField").blur();
            var index = $('.form-control').index(this) + 1;
            $('.form-control').eq(index).focus();
        }
    });
});

// tick checkbox with enter key
$('input:checkbox').keypress(function(e){
    if((e.keyCode ? e.keyCode : e.which) == 13){
        $(this).trigger('click');
    }
});

$("#nameField").blur();