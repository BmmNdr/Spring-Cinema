//Used in Login.html
function chkLogin(){
    var params = {}
    params["username"] = $("#username").val();
    params["password"] = $("#password").val();

    //Calls the API to check if the login is correct
    $.ajax({
        url: "/api/login?username="+params["username"]+"&password="+params["password"],
        success: function (data) {

            var JSON = $.parseJSON(data);

            if(JSON["status"]){
                var token = JSON["token"];

                //Redirects to the home page with the token and username parameters (AJAX --> WebController --> Home.html)
                window.location.href = "/home?token="+token+"&username="+params["username"];
            }else{

                //Shows the error message
                document.getElementById("error-message").style.display='block';
            }

        },
        error: function (e) {
            alert("Error!");
        }
    });
}

//Used in Home.html
function filter(){
    var data = {}
    data["filter"] = $("#filter").val();

    //Gets the token and username from the URL (Get parameters)
    var queryString = window.location.search;
    var urlParams = new URLSearchParams(queryString);

    data["token"] = urlParams.get('token');
    data["username"] = urlParams.get('username');

    //Reloads the page with the new filter
    window.location.href = "home?token="+data["token"]+"&filter="+data["filter"]+"&username="+data["username"];

    return true;
}

//Used in Home.html
function removeFilm(id){
    var param = {}
    param["id"] = id;

    //Gets the token and username from the URL (Get parameters)
    var queryString = window.location.search;
    var urlParams = new URLSearchParams(queryString);

    param["token"] = urlParams.get('token');
    param["username"] = urlParams.get('username');

    //Calls the API to remove the film
    $.ajax({
        url: "/api/removeFilm?id="+param["id"]+"&token="+param["token"]+"&username="+param["username"],
        success: function () {
            //Reloads the page
            window.location.href = "home?token="+param["token"]+"&username="+param["username"];
        },
        error: function (e) {
            alert("Error!");
        }
    });
}

//Used in addFilm.html
function addFilm(){
    var param = {}
    param["title"] = $("#title").val();
    param["description"] = $("#description").val();
    param["release_date"] = $("#release_date").val();
    param["genre"] = $("#genre").val();

    //HTML add "C:\\fakepath\\" to the loaded image path
    param["imagePath"] = $("#image").val().split('C:\\fakepath\\').pop();

    var queryString = window.location.search;
    var urlParams = new URLSearchParams(queryString);

    param["token"] = urlParams.get('token');
    param["username"] = urlParams.get('username');

    //Calls the API to add the film
    $.ajax({
        url: "/api/addFilm?title="+param["title"]+"&description="+param["description"]+"&imagePath="+param["imagePath"]+"&release_date="+param["release_date"]+"&genre="+param["genre"]+"&token="+param["token"]+"&username="+param["username"],
        
        success: function () {
            window.location.href = "home?token="+param["token"]+"&username="+param["username"];
        },
        error: function (e) {
            alert("Error!");
        }
    });
}