import Ember from 'ember';

var global="", count=0;
export default Ember.Controller.extend({
    cookie_username: getCookie("username"),
    actions: {
        check_poll_request: function()
        {
            var uname = getCookie("username");
            var un = {
                "username": uname,
            };
        
            $.ajax({
                type: 'POST',
                url: 'http://localhost:8080/PollRequest',
                data: {
                    jsonData: JSON.stringify(un)
                },
                dataType: "json",
        
                success: function (data) {
                    console.log(data);            
                    var res = data.boolean;
                    if(res === "true")
                    {
                        console.log("Poll request Data fetched. Success");
                        var btn = '', date='';
                        var poll_content, poll_creation_datetime, poll_id, poll_options, poll_owner;
                        if(data["data_status"]==="NO DATA")
                        {
                            btn = "<h2>No Poll request pending</h2>";
                        }
                        else
                        {
                            delete data["boolean"];
                            delete data["ResultText"];
                            delete data["data_status"];
                            
                            global=data;

                            count = data["poll_content"].split(';').length;

                            for(let i = 0; i < count; i++) 
                            {
                                poll_content = data["poll_content"].split(';');
                                poll_creation_datetime = data["poll_datetime"].split(';');
                                poll_id = data["poll_id"].split(';');
                                poll_options = data["poll_options"].split(';');
                                poll_owner = data["poll_owner"].split(';');

                                date = new Date(parseInt(poll_creation_datetime[i], 10));         // Milliseconds to date & 10 for decimal

                                btn +='<form><table width="650px" class="tout">';
                                btn += '<tr><th width="300px"> Poll ID: </th><td>'+poll_id[i]+'</td></tr>';
                                btn += '<tr><th width="300px"> Poll Owner: </th><td>'+poll_owner[i]+'</td></tr>';
                                btn += '<tr><th width="300px"> Poll Creation Datetime: </th><td>'+date.toString()+'</td></tr>';
                                btn += '<tr><th width="300px"> Poll Content: </th><td><b>'+poll_content[i]+'</b></td></tr>';
                                
                                btn += '<tr><th width="300px"> Poll Options : </th><td>';
                                let c = poll_options[i].split(',');
                                for(let j = 0; j < c.length; j++) 
                                {
                                    btn += '<input type="radio" name="test'+i+'" value="'+c[j]+'"/>'+c[j]+'<br>';
                                }
                                btn += '</td></tr>';
                                btn += '<tr><td colspan="2"><center><input class="button_form" type="reset" value="Reset">&ensp;<button class="button_form" data-ember-action="432">SUBMIT</button></center></td></tr></table></form><br>';
                            }
                            document.getElementById("submit_all").style.display = "block";
                        }
                        document.getElementById("request_list").innerHTML=btn;
                    }
                    else{
                        console.log("Unable to fetch Poll request data. Failed");
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

        submit: function()
        {
            var p_id = global["poll_id"].split(';');
            var res="", text="";

            var jobj = {};
            var results = [];
            jobj.results=results;
            jobj.username=getCookie("username");
            
            for(var i=0;i<count;i++)
            {
                var str = "test"+i;
                var g = $("input[type='radio'][name="+str+"]:checked").val();
                if(g !== undefined)
                {
                    res = res + "Poll ID: "+ p_id[i]+" --> Selected option: "+g + "\n";
                    var result = {
                        "poll_id": p_id[i],
                        "poll_result": g,
                        };
                    jobj.results.push(result);
                }
            }
            console.log(jobj);
            if(res === "")          //if none of the options selected then stop proceeding
            {
                return;
            }
            text = "Are you sure you want to submit?\n"+res;
            if (confirm(text) === true) 
            {  
                $.ajax({
                    type: 'POST',
                    url: 'http://localhost:8080/PollSubmit',
                    data: {
                        jsonData: JSON.stringify(jobj)
                    },
                    dataType: "json",
            
                    success: function (data) {
                        console.log(data);            
                        var res = data.boolean;
                        if(res === "true")
                        {
                            console.log("Poll Data has been submitted successfully");
                        }
                        else{
                            console.log("Poll Data has NOT been submitted. Failed");
                        }
                    },
    
                    error: function (jqXHR, responseText, textStatus) {
                        alert(jqXHR.responseText + " , " + jqXHR.status +" , "+ textStatus+" , "+ responseText);
                    }
                });
                location.reload();
            }
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