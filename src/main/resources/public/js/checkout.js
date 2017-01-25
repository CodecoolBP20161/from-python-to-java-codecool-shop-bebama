/**
 * Created by cickib on 2017.01.24..
 */

$("#sameAddress").click(function () {
    $("#shippingAddressLine1").val($("#billingAddressLine1").val());
    $("#shippingAddressLine2").val($("#billingAddressLine2").val());
    $("#shippingCity").val($("#billingCity").val());
    $("#shippingPostalCode").val($("#billingPostalCode").val());
    $("#shippingCountry").val($("#billingCountry").val());

    $('html, body').animate({
        scrollTop: $("#checkout").offset().top
    }, 2000);
})