

$('#test-button').click(function (event) {

    $.ajax({
        type: 'GET',
        url: 'https://api.openweathermap.org/data/2.5/weather?zip=55417,us&appid=5f90b0cc3c4bf2cbea8cb5afc7d4eb2b',
        success: function (data, status) {

            var description = data.weather[0].description;
            var temp = data.main.temp;
            var celsius = temp - 272.15;
            var icon = data.weather[0].icon;
            var sunriseMill = data.sys.sunrise;
            var dateSunrise = new Date(sunriseMill * 1000);
            var realSunrise = dateSunrise.toLocaleTimeString();
            var sunsetMill = data.sys.sunset;
            var dateSunset = new Date(sunsetMill * 1000);
            var realSunset = dateSunset.toLocaleTimeString();

            $('#weather').append("<p>" + description + "</p>");
            $('#weather').append("<p><img src=http://openweathermap.org/img/w/" + icon + ".png></img></p>");
            $('#weather').append("<p> Fahrenheit: " + Math.round(((celsius * (9 / 5)) + 32)) + "°</p>");
            $('#weather').append("<p> Sunrise: " + realSunrise + "</p>");
            $('#weather').append("<p> Sunset: " + realSunset + "</p>");
        },
        error: function () {
            $('#errorMessages')
                    .append($('<li>')
                            .attr({class: 'list-group-item list-group-item-danger'})
                            .text('Error calling web service.  Please try again later.'));
        }
    });

});



$('#test-button-2').click(function (event) {



    $('#weather').append("<p> This has been created </p>");
    $('#weather').append("<p> This has been appended </p>");

});


function loadWeather() {
    $.ajax({
        type: 'GET',
        url: 'https://api.openweathermap.org/data/2.5/weather?zip=55417,us&appid=5f90b0cc3c4bf2cbea8cb5afc7d4eb2b',
        success: function (data, status) {

            var description = data.weather[0].description;
            var temp = data.main.temp;
            var celsius = temp - 272.15;
            var icon = data.weather[0].icon;
            var sunriseMill = data.sys.sunrise;
            var dateSunrise = new Date(sunriseMill * 1000);
            var realSunrise = dateSunrise.toLocaleTimeString();
            var sunsetMill = data.sys.sunset;
            var dateSunset = new Date(sunsetMill * 1000);
            var realSunset = dateSunset.toLocaleTimeString();

            $('#weather').append("<p>" + description + "</p>");
            $('#weather').append("<p><img src=http://openweathermap.org/img/w/" + icon + ".png></img></p>");
            $('#weather').append("<p> Fahrenheit: " + Math.round(((celsius * (9 / 5)) + 32)) + "°</p>");
            $('#weather').append("<p> Sunrise: " + realSunrise + "</p>");
            $('#weather').append("<p> Sunset: " + realSunset + "</p>");
        },
        error: function () {
            $('#errorMessages')
                    .append($('<li>')
                            .attr({class: 'list-group-item list-group-item-danger'})
                            .text('Error calling web service.  Please try again later.'));
        }
    });
}



$(document).ready(function () {
    $('#beach-dropdown').on('change', function () {

        alert($(this).find('option:selected').val());
        alert($('#beach-dropdown').find('option:selected').val());


    })
})



function loadBreaks() {

    /*<![CDATA[*/

    var allBreaksForBeach = /*[[${allBreaksForBeach}]]*/ 'default';

    /*]]>*/


    for (var i = 0; i < allBreaksForBeach.length; i++) {

        var breakId = allBreaksForBeach[i].id;
        var breakName = allBreaksForBeach[i].name;

        var div = '<div class ="col-md-3 text-center border border-light">';
        div += '<h1>' + breakName + '</h1>';
        div += '<input type="hidden" id =' + breakId + '</>';
        div += '</div>';

        $('#breaks').append(div);


    }

}



function loadBeachComments() {

    /*<![CDATA[*/

    var beachComments = /*[[${beachComments}]]*/ 'default';

    /*]]>*/


    for (var i = 0; i < beachComments.length; i++) {

        var beachCommentId = beachComments[i].id;
        var beachCommentText = beachComments[i].commentText;
        var beachCommentUser = beachComments[i].user.username;

        var div = '<div class ="row-md-3 text-center border border-light">';
        div += '<div class ="col-md-2">';
        div += beachCommentUser;
        div += '</div>';
        div += '<div class ="col-md-9">';
        div += beachCommentText;
        div += '</div>';
        div += '</div>';

        $('#beach-user-comments').append(div);


    }

}


