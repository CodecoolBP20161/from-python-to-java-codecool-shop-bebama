/**
 * Created by cickib on 2016.11.14..
 */

function filter(filterby) {
    var input, filter, ul, li, a;

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

var modal = document.getElementById("productsModal");

var button = document.getElementById("cart");

button.onclick = function() {
    modal.style.display = "block";
}

function productsWriter(products) {
    console.log(products);
    // for (var x = 0; x < products.length; i++){
    //     console.log(JSON.parse(products[x]));
    // }
    var table = document.getElementById("productsTable");
    var data = ["name", "quality", "price", "sum"];
    // for ( var i = 0; i < product.length; i++) {
    //     var row = table.insertRow();
    //     row.setAttribute("class", "product");
    //     for (var i = 0; i < 8; i++) {
    //         var cell = row.insertCell(i);
    //         cell.innerHTML = product[data[i]];
    //     }
    // }
}
$(button).click(function () {
    clickOnCartEventHandler();
})

function clickOnCartEventHandler() {
    $.ajax({
        type: "GET",
        url: "/showcart",
        dataType: "json",
        success: function(data) {
            productsWriter(data)
        },
    });
}