import Ember from 'ember';

export default Ember.Controller.extend({
    cookie_username: getCookie("username"),
    actions: {
        check_poll_result: function()
        {
            var uname = getCookie("username");
            var un = {
                "username": uname,
            };
        
            $.ajax({
                type: 'POST',
                url: 'http://localhost:8080/PollResult',
                
                data: {
                    jsonData: JSON.stringify(un)
                },
                dataType: "json",
        
                success: function (data) {
                    console.log(data);            
                    var res = data.boolean;
                    if(res === "true")
                    {
                        console.log("Poll result Data fetched. Success");
                        var btn = '', date='';

                        var poll_content, poll_id, poll_options,poll_creation_datetime, poll_assign_usernames;
                        var poll_resp_data_id, poll_result_username, poll_result, poll_resp_datetime;
                        
                        if(data["data_status"]==="NO POLL DATA")
                        {
                            btn = "<h2>No Poll has been created by you</h2>";
                        }
                        else
                        {
                            delete data["boolean"];
                            delete data["ResultText"];
                            delete data["data_status"];

                            for(var key in data) 
                            {
                                var subdata = data[key];
                                delete subdata["user_id"];

                                poll_content = subdata["poll_content"];
                                poll_id = subdata["poll_id"];
                                poll_options = subdata["poll_options"];
                                poll_creation_datetime = subdata["datetime"];
                                poll_assign_usernames = subdata["poll_assign_usernames"];

                                date = new Date(parseInt(poll_creation_datetime, 10));         // Milliseconds to date & 10 for decimal

                                btn +='<table width="650px" class="tout">';
                                btn += '<tr><th width="300px"> Poll ID: </th><td>'+poll_id+'</td></tr>';
                                btn += '<tr><th width="300px"> Poll Creation Datetime: </th><td>'+date+'</td></tr>';
                                btn += '<tr><th width="300px"> Poll Content: </th><td><b>'+poll_content+'</b></td></tr>';
                                btn += '<tr><th width="300px"> Poll Options given: </th><td>'+poll_options+'</td></tr>';
                                btn += '<tr><th width="300px"> Poll Assigned Usernames: </th><td>'+poll_assign_usernames+'</td></tr>';

                                if(subdata["data_status"]==="NO RESPONSE DATA")
                                {                                   
                                    btn += '<tr><th width="300px"> Poll Result: </th><td><b>NOT YET ANSWERED BY ANYONE</b></td></tr>';
                                    btn += '<tr><th colspan="2"><button id="'+poll_id+'" class="button_form" data-ember-action="432">Delete poll</button></th></tr>';
                                }
                                else
                                {
                                    var count = subdata["poll_result_username"].split(';').length;

                                    poll_resp_data_id = subdata["poll_resp_data_id"].split(';');
                                    poll_result_username = subdata["poll_result_username"].split(';');
                                    poll_result = subdata["poll_result"].split(';');
                                    poll_resp_datetime = subdata["poll_resp_datetime"].split(';');

                                    btn += '<tr><th colspan="2"><button class="button_form">Delete poll</button></th></tr>';
                                    btn += '<tr><td colspan="2">';
                                    for(let i = 0; i < count; i++) 
                                    {
                                        date = new Date(parseInt(poll_resp_datetime[i], 10));         // Milliseconds to date & 10 for decimal
                                        
                                        //inner table
                                        btn +='<table width="650px" class="innertable">';
                                        btn += '<tr><th width="300px"> Poll Response ID : </th><td>'+poll_resp_data_id[i]+'</td></tr>';
                                        btn += '<tr><th width="300px"> Poll Result User Name : </th><td>'+poll_result_username[i]+'</td></tr>';
                                        btn += '<tr><th width="300px"> Poll Result: </th><td><b>'+poll_result[i]+'</b></td></tr>';
                                        btn += '<tr><th width="300px"> Poll Result Datetime: </th><td>'+date+'</td></tr>';
                                        btn += '</table><br>';    
                                    }
                                    btn += '</td></tr>';   
                                }
                                btn += '</table></form><br>';
                            }
                        }
                        document.getElementById("result_list").innerHTML=btn;
                    }
                    else
                    {
                        console.log("Unable to fetch Poll result data. Failed");
                        document.getElementById("result_list").innerHTML="<h2>No data has found</h2>";
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
        delete: function()
        {
            // $("button").click(function() {
            //     var poll_id = $(this).attr('id');
            //     console.log("Button ID clicked = " + poll_id);
            // });


            
            // const buttons = document.getElementsByTagName("button");
            // const buttonPressed = e => {
            // var id = e.target.id;  // Get ID of Clicked Element
            // alert("Are you sure you want to delete this poll?\n"+id);
            // }
            // for (let button of buttons) {
            // button.addEventListener("click", buttonPressed);
            // }



            var buttons = document.getElementsByTagName("button");
            var buttonsCount = buttons.length;
            for (var i = 0; i <= buttonsCount; i += 1) {
                buttons[i].onclick = function() {
                    // alert(this.id);
                    var pollID= this.id;
                    console.log(pollID);

                    var text = "Are you sure you want to delete this poll?\n"+pollID;
                    if (confirm(text) === true) 
                    {  
                        var pid = {
                            "poll_id": pollID,
                        };

                        $.ajax({
                            type: 'POST',
                            url: 'http://localhost:8080/PollDelete',
                            data: {
                                jsonData: JSON.stringify(pid)
                            },
                            dataType: "json",
                    
                            success: function (data) {
                                console.log(data);            
                                var res = data.boolean;
                                if(res === "true")
                                {
                                    console.log("Poll Data has been deleted successfully");
                                }
                                else{
                                    console.log("Poll Data has NOT been deleted. Failed");
                                }
                            },
            
                            error: function (jqXHR, responseText, textStatus) {
                                alert(jqXHR.responseText + " , " + jqXHR.status +" , "+ textStatus+" , "+ responseText);
                            }
                        });
                        // location.reload();
                    }


                };
            }




            // if(document.getElementById('1a9e5686-6478-4841-ad3e-ed749b5fa048').clicked === true)
            // {
            //     alert("button was clicked");
            // }


            // var buttonclicked; 
            // $("button").click(function(){ 
            // if( buttonclicked!= true ) { 
            //     buttonclicked= true; 
            //     alert("Button is clicked for first time"); 
            // }else{ 
            //     alert("Button was clicked before"); 
            // } 
            // }); 

            
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