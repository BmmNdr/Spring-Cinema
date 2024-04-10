function removeFilm(id) {
    var param = {}
    param["id"] = id;

    //Calls the API to remove the film
    $.ajax({
        url: "/apiremoveFilm?id=" + param["id"],
        success: function () {
            loadTable();
        },
        error: function (e) {
            alert("Error!");
        }
    });
}


function loadTable() {
    var data = {}
    data["filter"] = $("#filter").val();

    //Calls the API to get the films
    $.ajax({
        url: "/apifilms?filter=" + data["filter"],

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
                title.innerHTML = "<a href='film/" + response[i].id + "'>" + response[i].title + "</a>";
                releaseYear.innerHTML = response[i].release_year;
                genre.innerHTML = response[i].genre;

                if (document.getElementById("isAdmin").value === "true") {
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

    //Calls the loadTable function when the user types in the filter input
    $("#filter").on("keyup", function () {
        loadTable();
    });
});