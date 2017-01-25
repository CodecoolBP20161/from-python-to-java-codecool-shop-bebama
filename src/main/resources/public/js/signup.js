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

$(function () {
    $("#pwd2").keyup(function () {
        var password = $("#pwd").val();
        if (password == $(this).val()) {
            $("#signup-btn").removeAttr("disabled");
        }
        else {
            $("#signup-btn").attr("disabled", "disabled");
        }
    });
});

$("#nameField").keyup(function () {
    $("#nameDiv").removeClass("has-error");
    $("#nameDiv").removeClass("has-feedback");
    $("#errMsg").remove();
    $("#errSpan").remove();
});

$(function () {
    if ($("#existingName").val() != null) {
        $("#nameField").focus();
        $("#nameDiv").addClass("has-error");
        $("#nameDiv").addClass("has-feedback");
        $("#nameDiv").after('<div class="alert alert-danger alert-dismissable fade in" id="errMsg"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a> <strong>Name already exists!</strong></div>');
        $("#nameField").after('<span id="errSpan" class="glyphicon glyphicon-remove form-control-feedback"></span>')
    }
});

$("#nameField").blur();

$("#emailField").keyup(function () {
    $("#emailDiv").removeClass("has-error");
    $("#emailDiv").removeClass("has-feedback");
    $("#errMsg").remove();
    $("#errSpan").remove();
});

$(function () {
    if ($("#existingEmail").val() != null) {
        $("#emailField").focus();
        $("#emailDiv").addClass("has-error");
        $("#emailDiv").addClass("has-feedback");
        $("#emailDiv").after('<div class="alert alert-danger alert-dismissable fade in" id="errMsg"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a> <strong>Email already exists!</strong></div>');
        $("#emailField").after('<span id="errSpan" class="glyphicon glyphicon-remove form-control-feedback"></span>')
    }
});

$("#emailField").blur();

// tick checkbox with enter key
$('input:checkbox').keypress(function(e){
    if((e.keyCode ? e.keyCode : e.which) == 13){
        $(this).trigger('click');
    }
});

$("#nameField").blur();