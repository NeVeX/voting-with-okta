$( document ).ready(function() {

    $("#test").text("Hello there!");

    $.ajax({
        type: "GET",
        url: "test",
        success: function(data) {
            $("#test").text(data.message);
        },
        error: function(error) {
            $("#test").text("An error occurred!");
        }
    });


});