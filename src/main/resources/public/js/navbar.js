/**
 * Created by cickib on 2017.01.25..
 */

function resetCatSearch() {
    var categories = $("#search-cat").find("a");
    for (var i = 0; i < categories.length; i++) {
        $(categories[i]).show();
    }
    $("#input-cat").val("");
    $(".noMatch").remove();
}

function resetSupSearch() {
    var suppliers = $("#search-sup").find("a");
    for (var i = 0; i < suppliers.length; i++) {
        $(suppliers[i]).show();
    }
    $("#input-sup").val("");
    $(".noMatch").remove();
}

function dropdownFunc(target) {
    resetCatSearch();
    resetSupSearch();
    var inputFocus = "#" + "input-" + target;
    var id = target + "Dropdown";
    document.getElementById(id).classList.toggle("show");
    $(inputFocus).focus();
}

window.onclick = function (event) {
    if (event.target.matches('#catBtn') || event.target.matches('#input-cat')) {
        $("#supDropdown").removeClass("show");
        $("#supDropdown").addClass("hide");
        $("#input-sup").blur();
    } else if (event.target.matches('#supBtn') || event.target.matches('#input-sup')) {
        $("#catDropdown").removeClass("show");
        $("#catDropdown").addClass("hide");
        $("#input-cat").blur();
    } else {
        $("#catDropdown").removeClass("show");
        $("#supDropdown").removeClass("show");
        $("#catDropdown").addClass("hide");
        $("#supDropdown").addClass("hide");
        $("#input-sup").blur();
        $("#input-cat").blur();
    }
}

function filter(filterby) {
    var input, filter, a, div;
    var counter = 0;
    $(".noMatch").remove();

    if (filterby === "cat") {
        input = $("#input-cat");
        div = $("#search-cat");
    } else if (filterby === "sup") {
        input = $("#input-sup");
        div = $("#search-sup");
    }
    filter = input.val().toUpperCase();
    a = div.find("a");
    for (var i = 0; i < a.length; i++) {
        if ($(a[i]).html().toUpperCase().indexOf(filter) > -1) {
            $(a[i]).show();
        } else {
            $(a[i]).hide();
            counter++;
        }
    }
    if (counter > 3) {
        div.append('<a class="disabled noMatch">No match found.</a>');
    }
}