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
                //Redirects to the home page with the token and username parameters (AJAX --> WebController --> Home.html)
                window.location.href = "/home";
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

//Used in addFilm.html
function addFilm(){
    var param = {}
    param["title"] = $("#title").val();
    param["description"] = $("#description").val();
    param["release_date"] = $("#release_date").val();
    param["genre"] = $("#genre").val();

    //HTML add "C:\\fakepath\\" to the loaded image path
    param["imagePath"] = $("#image").val().split('C:\\fakepath\\').pop();

    //Calls the API to add the film
    $.ajax({
        url: "/api/addFilm?title="+param["title"]+"&description="+param["description"]+"&imagePath="+param["imagePath"]+"&release_date="+param["release_date"]+"&genre="+param["genre"],
        
        success: function () {

            sendPicture();

            window.location.href = "home?";
        },
        error: function (e) {
            alert("Error!");
        }
    });
}

function sendPicture(){
    var formData = new FormData();
    formData.append("file", $("#image")[0].files[0]);

    $.ajax({
        url: "/api/uploadPoster",
        type: 'POST',
        data: formData,
        cache: false,
        contentType: false,
        processData: false,
        success: function () {
            console.log("Success");
        },
        error: function (e) {
            alert("Error!");
        }
    });
}