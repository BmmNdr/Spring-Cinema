function removeFilm(id) {
    var param = {}
    param["id"] = id;

    //Gets the token and username from the URL (Get parameters)
    var queryString = window.location.search;
    var urlParams = new URLSearchParams(queryString);

    param["token"] = urlParams.get('token');
    param["username"] = urlParams.get('username');

    //Calls the API to remove the film
    $.ajax({
        url: "/api/removeFilm?id=" + param["id"] + "&token=" + param["token"] + "&username=" + param["username"],
        success: function () {
            //Reloads the page
            window.location.href = "home?token=" + param["token"] + "&username=" + param["username"];
        },
        error: function (e) {
            alert("Error!");
        }
    });
}


function loadTable() {
    var data = {}
    data["filter"] = $("#filter").val();

    //Gets the token and username from the URL (Get parameters)
    var queryString = window.location.search;
    var urlParams = new URLSearchParams(queryString);

    data["token"] = urlParams.get('token');
    data["username"] = urlParams.get('username');

    //Calls the API to get the films
    $.ajax({
        url: "/api/films?token=" + data["token"] + "&username=" + data["username"] + "&filter=" + data["filter"],

        success: function (response) {
            var tbody = document.getElementById("table-content");
            tbody.innerHTML = "";
            for (var i = 0; i < response.length; i++) {
                var row = tbody.insertRow(i);
                var poster = row.insertCell(0);
                var title = row.insertCell(1);
                var releaseYear = row.insertCell(2);
                var genre = row.insertCell(3);


                poster.innerHTML = "<img src='images/" + response[i].imagePath + "' class='poster'>";
                title.innerHTML = response[i].title;
                releaseYear.innerHTML = response[i].release_year;
                genre.innerHTML = response[i].genre;

                if (document.getElementById("isAdmin") == "true") {
                    var remove = row.insertCell(4);
                    remove.innerHTML = "<button onclick='removeFilm(" + response[i].id + ")' class='remove'>Remove</button>";
                }
            }
        },
        error: function (e) {
            alert("Error!");
        }
    });
}

$(document).ready(function () {
    loadTable();
    $("#filter").on("keyup", function () {
        loadTable();
    });
});