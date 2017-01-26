/**
 * Created by cickib on 2017.01.25..
 */


$("#shippingCity").keyup(function () {
    $("#shippingCityDiv").removeClass("has-error");
    $("#shippingCityDiv").removeClass("has-feedback");
    $("#errMsg").remove();
    $("#errSpan").remove();
});

$("#checkout").click(function () {
    $.getJSON("http://localhost:8888/checkcity?city=" + $("#shippingCity").val(), function (data) {
        if (data.result === "too_far") {
            $(function () {
                $("#shippingCity").focus();
                $("#shippingCityDiv").addClass("has-error");
                $("#shippingCityDiv").addClass("has-feedback");
                $("#shippingCityDiv").after('<div class="alert alert-danger alert-dismissable fade in" id="errMsg"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a> <strong>Location too far. Please choose another destination!</strong></div>');
                $("#shippingCity").after('<span id="errSpan" class="glyphicon glyphicon-remove form-control-feedback"></span>')
            });
        } else if (data.result === "invalid") {
            $(function () {
                $("#shippingCity").focus();
                $("#shippingCityDiv").addClass("has-error");
                $("#shippingCityDiv").addClass("has-feedback");
                $("#shippingCityDiv").after('<div class="alert alert-danger alert-dismissable fade in" id="errMsg"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a> <strong>Invalid location. Please choose another destination!</strong></div>');
                $("#shippingCity").after('<span id="errSpan" class="glyphicon glyphicon-remove form-control-feedback"></span>')
            });
        } else {
            $("#shippingCity").focus();
            $("#checkoutVerified").removeAttr("disabled");
            $("#checkoutVerified").trigger('click');
            $("#checkoutVerified").on("click", function () {
                $("#checkout-form").submit();
                $("#shippingCity").blur();
            });
        }
    });
});

$('#checkout').keypress(function (e) {
    if (e.which == 13) {
        $("#checkout").click();
    }
});

$(document).click(function () {
    $(function () {
        $("#shippingCity").keyup();
    });
});

$(document).keypress(function (e) {
    if (e.which == 13) {
        $(function () {
            $("#checkout").click();
        });
    }
});

$("#shippingCity").blur();
