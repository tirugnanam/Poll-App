import Ember from 'ember';

var username = "", pswd = "";
export default Ember.Controller.extend({
    actions: {
        login: function () {
            username = $("#username").val();
            pswd = $("#pswd").val();

            if (username === "") {
                alert("Please enter your username");
                document.getElementById("username").focus();
                return;
            }
            else if (pswd === "") {
                alert("Please enter your password");
                document.getElementById("pswd").focus();
                return;
            }

            var ld = {
                "username": username,
                "password": pswd,
            };
            console.log(ld);

            let unameval = getCookie("username");
            if (unameval === username) {
                alert("Same user Already logged in! - " + unameval);
                console.log("move to profile ember");
                location.assign('profile');
            }
            else if (unameval !== "" && unameval !== "0")       // "0" for already logged in and logoutted
            {
                alert("Some other user logged in! - " + unameval);
                console.log("move to profile ember");
                location.assign('profile');
            }
            else {
                $.ajax({
                    type: 'POST',
                    url: 'http://localhost:8080/login',
                    data: {
                        jsonData: JSON.stringify(ld)
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
                            alert("INVALID login");
                            document.getElementById("username").focus();
                        }
                    },

                    error: function (jqXHR, responseText, textStatus) {
                        alert("Check mickey connectivity in backgroud\n" + jqXHR.responseText + " , " + jqXHR.status + " , " + textStatus + " , " + responseText);
                    }
                });
            }
        },
    },
});

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
