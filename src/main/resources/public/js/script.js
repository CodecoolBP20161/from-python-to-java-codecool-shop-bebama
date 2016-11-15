/**
 * Created by cickib on 2016.11.14..
 */

/* When the user clicks on the button,
 toggle between hiding and showing the dropdown content */

function filter() {
    var input, filter, ul, li, a, i;
    input = document.getElementById("input-cat");
    filter = input.value.toUpperCase();
    div = document.getElementById("search-cat");
    a = div.getElementsByTagName("a");
    for (i = 0; i < a.length; i++) {
        if (a[i].innerHTML.toUpperCase().indexOf(filter) > -1) {
            a[i].style.display = "";
        } else {
            a[i].style.display = "none";
        }
    }
}