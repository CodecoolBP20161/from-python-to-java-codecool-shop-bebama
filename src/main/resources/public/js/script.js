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
    $("#errMsg").remove();
    if ($("#existingEmail").val() != null) {
        $("#emailDiv").addClass("has-error");
        $("#existingEmail").append('<p style="color: red" id="errMsg">Email already exists.</p>');
    }
});

$('#loginModal').on('hidden.bs.modal', function () {
    $("#login-name").val("");
    $("#login-pwd").val("");
});