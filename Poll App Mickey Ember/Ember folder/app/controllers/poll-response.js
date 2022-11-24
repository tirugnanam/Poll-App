import Ember from 'ember';

export default Ember.Controller.extend({
    cookie_username: getCookie("username"),
    actions: {
        check_poll_response: function()
        {
            var uname = getCookie("username");
            var un = {
                "username": uname,
            };
        
            $.ajax({
                type: 'POST',
                url: 'http://localhost:8080/PollResponse',
                
                data: {
                    jsonData: JSON.stringify(un)
                },
                dataType: "json",
        
                success: function (data) {
                    console.log(data);            
                    var res = data.boolean;
                    if(res === "true")
                    {
                        console.log("Poll response Data fetched. Success");
                        var btn = '', date1='', date2='';
                        if(data["data_status"]==="NO DATA")
                        {
                            btn = "<h2>No Poll has been responded by you</h2>";
                        }
                        else
                        {
                            delete data["boolean"];
                            delete data["ResultText"];
                            
                            var count = data["poll_id"].split(';').length;

                            let poll_resp_data_id = data["poll_resp_data_id"].split(';');
                            let poll_id = data["poll_id"].split(';');
                            let poll_response = data["poll_response"].split(';');
                            let pol_resp_datetime = data["datetime"].split(';');
                            date1 = new Date(parseInt(pol_resp_datetime, 10));         // Milliseconds to date & 10 for decimal
                            
                            let poll_content = data["poll_content"].split(';');
                            let poll_owner = data["poll_owner"].split(';');
                            let poll_options = data["poll_options"].split(';');
                            let poll_creation_datetime = data["poll_creation_datetime"].split(';');
                            date2 = new Date(parseInt(poll_creation_datetime, 10));         // Milliseconds to date & 10 for decimal

                            for(let i = 0; i < count; i++) 
                            {
                                btn +='<table width="650px" class="tout">';
                                btn += '<tr><th width="300px"> Poll Response ID : </th><td>'+poll_resp_data_id[i]+'</td></tr>';
                                btn += '<tr><th width="300px"> Poll ID: </th><td>'+poll_id[i]+'</td></tr>';
                                btn += '<tr><th width="300px"> Poll Creator username: </th><td>'+poll_owner[i]+'</td></tr>';
                                btn += '<tr><th width="300px"> Poll Creation Datetime: </th><td>'+date2+'</td></tr>';
                                btn += '<tr><th width="300px"> Poll Content: </th><td><b>'+poll_content[i]+'</b></td></tr>';
                                btn += '<tr><th width="300px"> Poll Options given: </th><td>'+poll_options[i]+'</td></tr>';
                                btn += '<tr><th width="300px"> Your Poll Response: </th><td><b>'+poll_response[i]+'</b></td></tr>';
                                btn += '<tr><th width="300px"> Poll Responded Datetime: </th><td>'+date1+'</td></tr>';
                                btn += '</table><br>';
                            }
                        }
                        document.getElementById("request_list").innerHTML=btn;
                    }
                    else{
                        console.log("Unable to fetch Poll response data. Failed");
                        document.getElementById("request_list").innerHTML="<h2>No data has found</h2>";
                    }
                },

                error: function (jqXHR, responseText, textStatus) {
                    alert(jqXHR.responseText + " , " + jqXHR.status +" , "+ textStatus+" , "+ responseText);
                }
            });
            
        },
        home: function()
        {
            console.log("move to home ember");
            location.assign('home');

        },
        profile: function()
        {
            console.log("move to profile ember");
            location.assign('profile');
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
        window.location.replace("login");
    }
    else if(unameval === "")
    {
        alert("No Cookie/Cookie Timeout");
        window.location.replace("login");
    }
    // console.log(unameval);
    return unameval;
}