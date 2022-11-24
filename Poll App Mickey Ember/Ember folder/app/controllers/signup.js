import Ember from 'ember';

export default Ember.Controller.extend({
    actions: {
        signup: function () {
            var user_id = $("#user_id").val();
            var name = $("#name").val();
            var username = $("#username").val();
            var pswd = $("#pswd").val();
            var repswd = $("#repswd").val();
            var dept = $("#dept").val();
            var user_mail_id = $("#user_mail_id").val();

            if (pswd !== repswd) {
                alert("Password entered is mismatched");
                document.getElementById("repswd").focus();
                return;
            }

            var signup_data = {
                    "user_id": user_id,
                    "name": name,
                    "username": username,
                    "pswd": pswd,
                    "dept": dept,
                    "user_mail_id": user_mail_id,
            };
            console.log(signup_data);

            let unameval = getCookie("username");
            if (unameval !== "" && unameval !== "0")       // "0" for already logged in and logoutted
            {
                alert("Some other user logged in! - " + unameval);
                console.log("move to profile ember");
                location.assign('profile');
            }
            else 
            {
                $.ajax({
                    type: 'POST',
                    url: 'http://localhost:8080/signup',
                    data: {
                        jsonData: JSON.stringify(signup_data)
                    },
                    dataType: "json",

                    success: function (data) {
                        console.log(data);
                        var res = data.boolean;
                        if (res === "true") {
                            const d = new Date();
                            d.setTime(d.getTime() + (600 * 1000));        //600 s --> 10 min 
                            let expires = "expires=" + d.toUTCString();
                            document.cookie = "username=" + username + ";" + expires + ";path=/";

                            displayCookieJSON();
                            console.log("New cookie created");
                            // window.open('home', '_blank');
                            location.replace("home");
                        }
                        else {
                            alert("Error in account creation (Check console. User ID/ Username may already exists)");
                            console.log("end");
                        }
                    },

                    error: function (jqXHR, responseText, textStatus) {
                        alert(jqXHR.responseText + " , " + jqXHR.status + " , " + textStatus + " , " + responseText);
                    }
                });
            }
        }
    }
});

function displayCookieJSON() {
    var cookie = document.cookie;
    var output = {};
    cookie.split(/\s*;\s*/).forEach(function (pair) {
        pair = pair.split(/\s*=\s*/);
        output[pair[0]] = pair.splice(1).join('=');
    });
    var cookie_json = JSON.stringify(output, null, 4);
    console.log(cookie_json);
}

function getCookie(cname) {
    let name = cname + "=";
    let decodedCookie = decodeURIComponent(document.cookie);
    let ca = decodedCookie.split(';');
    for (let i = 0; i < ca.length; i++) {
        let c = ca[i];
        while (c.charAt(0) === ' ') {      //remove front leading white spaces
            c = c.substring(1);
        }
        if (c.indexOf(name) === 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}
// window.open('home', '_blank');         //Don't uncomment this line while server running