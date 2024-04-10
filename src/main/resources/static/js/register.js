const input = document.getElementById("confirm-password");
const cnf = document.getElementById("chk-confirm");
const button = document.getElementById("btn-register");

input.addEventListener("input", updateValue);

//Check if the password and confirm password are the same, otherwise the register button is hidden
function updateValue(e) {

    //If the password and confirm password are the same, the register button is shown
    if(e.target.value == document.getElementById("password").value){
        cnf.style.display='none';
        button.style.display='block';
    }
    else{
        cnf.style.display='block';
        button.style.display='none';
    }
}

function chkRegister(){
    var data = {}
    data["username"] = $("#username").val();
    data["password"] = $("#password").val();
    data["email"] = $("#email").val();

    //Calls the API to register the user
    $.ajax({
        url: "/apiregister?username="+data["username"]+"&password="+data["password"] + "&email="+data["email"],
        success: function (data) {

            if(data){
                //Redirects to the login page
                window.location.href = "/login";
            }else{
                document.getElementById("error-message").style.display='block';
            }

        },
        error: function (e) {
            alert("Error!");
        }
    });
}