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