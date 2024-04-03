const input = document.getElementById("confirm-password");
const cnf = document.getElementById("chk-confirm");
const button = document.getElementById("btn-register");

input.addEventListener("input", updateValue);

function updateValue(e) {
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

    $.ajax({
        url: "/api/register?username="+data["username"]+"&password="+data["password"] + "&email="+data["email"],
        success: function (data) {

            if(data){
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