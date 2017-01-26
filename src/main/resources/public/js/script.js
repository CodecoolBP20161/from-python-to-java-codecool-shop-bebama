/**
 * Created by cickib on 2016.11.14..
 */

$("#submit_delivery").attr("disabled", "disabled");

$('#loginModal').on('hidden.bs.modal', function () {
    $("#login-name").val("");
    $("#login-pwd").val("");
    $("#login-name").blur();
});

$('#loginModal').on('shown.bs.modal', function () {
    $("#login-name").focus();
});


$('#loginModal').keypress(function (e) {
    if (e.which == 13) {
        $('#login-form').submit();
        return false;
    }
});

if ($("#failedLogin").val() != null) {
    $('#failed-login').appendTo("body").modal('show');
}

$(document).ready(function () {
    $('[data-toggle="tooltip"]').tooltip();
});

$('#deliveryForm input').on('change', function () {
    $("#submit_delivery").removeAttr("disabled");
});