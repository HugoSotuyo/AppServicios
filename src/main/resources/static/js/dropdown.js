$(document).ready(function () {
    $('.nav-link.dropdown-toggle').on('click', function (e) {
        e.preventDefault();
        var dropdownMenu = $(this).next('.dropdown-menu');
        if (dropdownMenu.is(':visible')) {
            dropdownMenu.hide();
        } else {
            dropdownMenu.show();
        }
    });
});