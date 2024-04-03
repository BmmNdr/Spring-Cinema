function chkLogin(){
    var params = {}
    params["username"] = $("#username").val();
    params["password"] = $("#password").val();

    $.ajax({
        url: "/api/login?username="+params["username"]+"&password="+params["password"],
        success: function (data) {

            var JSON = $.parseJSON(data);

            if(JSON["status"]){
                var token = JSON["token"];
                window.location.href = "/home?token="+token+"&username="+params["username"];
            }else{
                document.getElementById("error-message").style.display='block';
            }

        },
        error: function (e) {
            alert("Error!");
        }
    });
}

function filter(){
    var data = {}
    data["filter"] = $("#filter").val();

    var queryString = window.location.search;
    var urlParams = new URLSearchParams(queryString);

    data["token"] = urlParams.get('token');
    data["username"] = urlParams.get('username');

    window.location.href = "home?token="+data["token"]+"&filter="+data["filter"]+"&username="+data["username"];

    return true;
}

function removeFilm(id){
    var param = {}
    param["id"] = id;

    var queryString = window.location.search;
    var urlParams = new URLSearchParams(queryString);

    param["token"] = urlParams.get('token');
    param["username"] = urlParams.get('username');

    $.ajax({
        url: "/api/removeFilm?id="+param["id"]+"&token="+param["token"]+"&username="+param["username"],
        success: function () {
            window.location.href = "home?token="+param["token"]+"&username="+param["username"];
        },
        error: function (e) {
            alert("Error!");
        }
    });
}

function addFilm(){
    var param = {}
    param["title"] = $("#title").val();
    param["description"] = $("#description").val();
    param["release_date"] = $("#release_date").val();
    param["genre"] = $("#genre").val();
    param["imagePath"] = $("#image").val().split('C:\\fakepath\\').pop();

    var queryString = window.location.search;
    var urlParams = new URLSearchParams(queryString);

    param["token"] = urlParams.get('token');
    param["username"] = urlParams.get('username');

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