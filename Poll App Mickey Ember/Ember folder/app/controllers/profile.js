import Ember from 'ember';

export default Ember.Controller.extend({
    cookie_username: getCookie("username"),
    cookie_user_backend_details: getUserDetails(getCookie("username")),
    actions: {
        logout: function () 
        {
            let text = "Are you sure you want to logout?";
            if (confirm(text) === true) 
            {
                document.cookie = "username=0;path=/";
                location.replace("login");
            } 
        },
        home: function()
        {
            console.log("move to home ember");
            location.assign('home');

        },
    }
});
function getCookie(cname) {
    let name = cname + "=";
    let unameval="";
    let decodedCookie = decodeURIComponent(document.cookie);
    // console.log(decodedCookie);
    let ca = decodedCookie.split(';');
    for(let i = 0; i < ca.length; i++) 
    {
      let c = ca[i];
      while (c.charAt(0) === ' ') 
      {
        c = c.substring(1);
      }
      if (c.indexOf(name) === 0) 
      {
        unameval = c.substring(name.length, c.length);
      }
    }
    if(unameval === "0")
    {
        alert("No active cookie. Please Login");
       location.replace("login");
    }
    else if(unameval === "")
    {
        alert("No Cookie/Cookie Timeout");
        location.replace("login");
    }
    return unameval;
}

function getUserDetails(uname)
{
    var un = {
        "username": uname,
    };

    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/profile',
        data: {
            jsonData: JSON.stringify(un)
        },
        dataType: "json",

        success: function (data) {
            console.log(data);            
            var res = data.boolean;
            if(res === "true")
            {
                console.log("Data fetched. Success");
                delete data["boolean"];         //delete unwanted data
                delete data["ResultText"];         //delete unwanted data
                var rows = '';
                // for(var key in data) 
                // {
                //     rows += '<tr><td>' + key + '</td><td>' + data[key] + '</td></tr>';
                // }
                rows += '<tr><td>User ID</td><td>' + data["user_id"] + '</td></tr>';
                rows += '<tr><td>Name</td><td>' + data["name"] + '</td></tr>';
                rows += '<tr><td>Username</td><td>' + data["username"] + '</td></tr>';
                rows += '<tr><td>User Mail ID</td><td>' + data["user_mail_id"] + '</td></tr>';
                rows += '<tr><td>Department</td><td>' + data["department"] + '</td></tr>';
                
                document.getElementById("profile_table").innerHTML=rows;
            }
            else{
                console.log("Unable to fetch data. Failed");
            }
        },

        error: function (jqXHR, responseText, textStatus) {
            alert(jqXHR.responseText + " , " + jqXHR.status +" , "+ textStatus+" , "+ responseText);
        }
    });
}