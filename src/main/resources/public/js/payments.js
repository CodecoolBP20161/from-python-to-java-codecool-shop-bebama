/**
 * Created by cickib on 2016.12.14..
 */

$(document).ready(function () {
    $("#panel_paypal").hide();
    $("#panel_card").hide();
})


$('#payment input').on('change', function () {
    var target = $('input[type=radio][name=optradio]:checked').attr('id');
    if (target === "paypal") {
        $("#panel_card").hide();
        $("#panel_paypal").show();
    }
    else if (target === "card") {
        $("#panel_paypal").hide();
        $("#panel_card").show();
    }
    else {
        $("#panel_paypal").hide();
        $("#panel_card").hide();
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

$(document).ready(function(){
    $('[data-toggle="tooltip"]').tooltip();
});