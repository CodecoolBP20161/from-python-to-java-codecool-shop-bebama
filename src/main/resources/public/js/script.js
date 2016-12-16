/**
 * Created by cickib on 2016.11.14..
 */

$(".dropdown-toggle").click(function () {
    $("#input-cat").val("");
    $("#input-sup").val("");
});


function filter(filterby) {
    var input, filter, a, div;

    if (filterby === "cat") {
        input = document.getElementById("input-cat");
        filter = input.value.toUpperCase();
        div = document.getElementById("search-cat");
        a = div.getElementsByTagName("a");

    }
    else if (filterby === "sup") {
        input = document.getElementById("input-sup");
        filter = input.value.toUpperCase();
        div = document.getElementById("search-sup");
        a = div.getElementsByTagName("a");

    }
    for (var i = 0; i < a.length; i++) {
        if (a[i].innerHTML.toUpperCase().indexOf(filter) > -1) {
            a[i].style.display = "";
        } else {
            a[i].style.display = "none";
        }
    }
}

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

$(function () {
    $("#emailDiv").removeClass("has-error");
    $("#emailDiv").removeClass("has-feedback");
    $("#errMsgEmail").remove();
    if ($("#existingEmail").val() != null) {
        $("#emailDiv").addClass("has-error");
        $("#email-div").addClass("has-feedback");
        $("#existingEmail").append('<div class="alert alert-danger alert-dismissable fade in" id="errMsgEmail"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a> <strong>Email already exists!</strong></div>');
        $("#emailField").after('<span class="glyphicon glyphicon-remove form-control-feedback"></span>')

    }});

$(function () {
    $("#nameDiv").removeClass("has-error");
    $("#nameDiv").removeClass("has-feedback");
    $("#errMsg").remove();
        if ($("#existingName").val() != null) {
            $("#nameDiv").addClass("has-error");
            $("#nameDiv").addClass("has-feedback");
            $("#existingName").append('<div class="alert alert-danger alert-dismissable fade in" id="errMsg"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a> <strong>Name already exists!</strong></div>');
            $("#nameField").after('<span class="glyphicon glyphicon-remove form-control-feedback"></span>')
        }
    });

$('#loginModal').on('hidden.bs.modal', function () {
    $("#login-name").val("");
    $("#login-pwd").val("");
});

$('#loginModal').keypress(function (e) {
    if (e.which == 13) {
        $('#login-form').submit();
        return false;
    }
});


if($("#failedLogin").val()!= null){

    $('#failed-login').appendTo("body").modal('show');

}

$(document).ready(function(){
    $('[data-toggle="tooltip"]').tooltip();
});