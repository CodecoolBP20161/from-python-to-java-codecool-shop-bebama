/**
 * Created by cickib on 2016.12.14..
 */

$(document).ready(function () {
    $("#form_paypal").hide();
    $("#form_card").hide();
})


$('#payment input').on('change', function () {
    var target = $('input[type=radio][name=optradio]:checked').attr('id');
    if (target === "paypal") {
        $("#form_card").hide();
        $("#form_paypal").show();
    }
    else if (target === "card") {
        $("#form_paypal").hide();
        $("#form_card").show();
    }
    else {
        $("#form_paypal").hide();
        $("#form_card").hide();
    }
});

$("#card_number").keyup(function() {
    var cardNum = $(this).val().split("-").join("");
    if (cardNum.length > 0) {
        cardNum = cardNum.match(new RegExp('.{1,4}', 'g')).join("-");
    }
    $(this).val(cardNum);
});
