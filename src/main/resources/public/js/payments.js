/**
 * Created by cickib on 2016.12.14..
 */

$(document).ready(function () {
    $("#panel_paypal").hide();
    $("#panel_card").hide();
    $("#form_card").find(":input, textarea").val("");
    $("#form_paypal").find(":input, textarea").val("");

})


$('#payment input').on('change', function () {
    var target = $('input[type=radio][name=optradio]:checked').attr('id');
    if (target === "paypal") {
        $("#panel_card").hide();
        $("#form_card").find(":input, textarea").val("");
        $("#panel_paypal").show();
    }
    else if (target === "card") {
        $("#panel_paypal").hide();
        $("#form_paypal").find(":input, textarea").val("");
        $("#panel_card").show();
    }
    else {
        $("#panel_paypal").hide();
        $("#panel_card").hide();
        $("#form_card").find(":input, textarea").val("");
        $("#form_paypal").find(":input, textarea").val("");
    }
});

$("#card_number").keyup(function() {
    var cardNum = $(this).val().split("-").join("");
    if (cardNum.length > 0) {
        cardNum = cardNum.match(new RegExp('.{1,4}', 'g')).join("-");
    }
    $(this).val(cardNum);
});

$("#card_expiry_date").keyup(function() {
    var exp = $(this).val().split("/").join("");
    if (exp.length > 0) {
        exp = exp.match(new RegExp('.{1,2}', 'g')).join("/");
    }
    $(this).val(exp);
});


if($("#ok-code").length !=0){
    $("#payment-code-panel").remove();
    setTimeout(function() {
        window.location.href = "http://0.0.0.0:8888/";
    }, 4000);
}