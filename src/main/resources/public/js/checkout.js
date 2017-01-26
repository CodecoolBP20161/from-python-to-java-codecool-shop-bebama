/**
 * Created by cickib on 2017.01.24..
 */

$("#checkoutVerified").attr("disabled", "disabled");

// autofocus on the checkout form, jumps to next input field on enter
$(document).ready(function () {
    $("#name").focus();
    $('.form-control').keydown(function (e) {
        if (e.which === 13) {
            $("#name").blur();
            var index = $('.form-control').index(this) + 1;
            $('.form-control').eq(index).focus();
        }
    });
});

// when same address checkbox is ticked, autofills shipping fields with billing data
$("#sameAddress").click(function () {
    $("#shippingAddressLine1").val($("#billingAddressLine1").val());
    $("#shippingAddressLine2").val($("#billingAddressLine2").val());
    $("#shippingCity").val($("#billingCity").val());
    $("#shippingPostalCode").val($("#billingPostalCode").val());
    $("#shippingCountry").val($("#billingCountry").val());

    $('html, body').animate({
        scrollTop: $("#checkout").offset().top
    }, 2000);
});

$("#name").blur();
