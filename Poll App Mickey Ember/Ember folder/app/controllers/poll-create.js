import Ember from 'ember';

var opt_count=2, user_count=2, uname, usernames_arr=[];    //global scope variables
export default Ember.Controller.extend({
    cookie_username: getCookie("username"),
    get_all_usernames: all_usernames(),
    actions: {
        poll_create_submit: function()
        {
            var temp="", temp_val="";
            
            var pollcontent = $("#pollcontent").val();
            if(pollcontent==="")
            {
                alert("Fill the missing details - poll content");
                document.getElementById("pollcontent").focus();
                return;
            }

            // polloptions
            var polloptions = "";
            for(let i=1; i<=opt_count;i++)
            {
                temp = "#opt"+i;
                temp_val = $(temp).val();
                if(temp_val==="")
                {
                    alert("Fill the missing details - poll options "+i);
                    document.getElementById(temp.substring(1)).focus();     //substring to remove first character '#' from temp
                    return;
                }
                polloptions = polloptions + "," + temp_val;
            }
            polloptions=polloptions.substring(1);           //substring to remove first character ',' from polloptions
            // console.log(polloptions);

            //pollassigned_users
            var pollassigned_users = "";
            for(let i=1; i<=user_count;i++)
            {
                temp = "#uname"+i;
                temp_val = $(temp).val();
                if(temp_val === null)
                {
                    alert("Fill the missing details - poll assigned users "+i);
                }
                else if(pollassigned_users.includes(temp_val))
                {
                    alert("Usernames selected is repeating - "+temp_val);
                }
                else
                {
                    pollassigned_users = pollassigned_users + "," + temp_val;
                    continue;      //to avoid unnecessary focus() below this else part
                }
                document.getElementById(temp.substring(1)).focus();     //substring to remove first character '#' from temp
                return;
            }
            pollassigned_users=pollassigned_users.substring(1);           //substring to remove first character ',' from pollassigned_users
            // console.log(pollassigned_users);

            var pd = {
                "username": uname,
                "pollcontent": pollcontent,
                "polloptions": polloptions,
                "pollassigned_users": pollassigned_users,
            };
            console.log(pd);

            // $.ajax({
            //     type: 'POST',
            //     url: 'http://localhost:8080/pollCreate',
            //     data: {
            //         jsonData: JSON.stringify(pd)
            //     },
            //     dataType: "json",

            //     success: function (data) {
            //         console.log(data);
            //         console.log(data.res);
                    
            //         var res = data.boolean;
            //         if(res === "true")
            //         {
            //             console.log("New poll created");
            //             alert("New poll created");
            //             location.reload();
            //         }
            //         else
            //         {
            //             console.log("poll NOT created");
            //             alert("Poll not created");
            //         }
            //     },

            //     error: function (jqXHR, responseText, textStatus) {
            //         alert(jqXHR.responseText + " , " + jqXHR.status +" , "+ textStatus+" , "+ responseText);
            //     }
            // }); 
        },
        set_opt_count: function()
        {
            opt_count = $("#opt_count").val();
            var rows="";
            if(opt_count<2 || opt_count>5)
            {
                document.getElementById("opt_count").focus();
                return;
            }
            for(let i = 1; i <= opt_count; i++)
            {
                rows += '<tr><td>Enter Option '+i+' value : </td><td><input name="opt'+i+'" id="opt'+i+'" placeholder="Enter option '+i+'" required=true></td></tr>';
            }
            document.getElementById("opt").innerHTML=rows;
        },

        set_user_count: function()
        {
            user_count = $("#user_count").val();
            if(user_count<1 || user_count>usernames_arr.length)
            {
                document.getElementById("user_count").focus();
                return;
            }
            select_username(user_count);
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
       location.replace("login");
    }
    else if(unameval === "")
    {
        alert("No Cookie/Cookie Timeout");
        location.replace("login");
    }
    return unameval;
}

function all_usernames()
{
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/getAllUsernames',
       
        dataType: "json",

        success: function (data) {
            console.log(data);
            var res = data.boolean;
            if(res === "true")
            {
                // console.log(data["all_usernames"]);
                usernames_arr = data["all_usernames"];      //set json response data into global variable "usernames_arr"
                uname = getCookie("username");              //set current username value into global variable "uname"

                //remove self username from "usernames_arr"
                const index = usernames_arr.indexOf(uname);
                if (index > -1) {                           // only splice array when item is found
                    usernames_arr.splice(index, 1);         // 2nd parameter means remove one item only
                }

                document.getElementById("max_user_count").innerHTML="No. of users needed<br>(Min 1, Max "+usernames_arr.length+")";                
                //Function() -> default two field for getting username from user
                select_username(2);
            }
            else
            {
                console.log("something problem");
            }
        },

        error: function (jqXHR, responseText, textStatus) {
            alert(jqXHR.responseText + " , " + jqXHR.status +" , "+ textStatus+" , "+ responseText);
        }
    });
} 

function select_username(count)
{
    var rows="";
    for(let i = 1; i <= count; i++)
    {
        rows += '<tr><td>Select Username '+i+' : </td><td>';

        // rows += '<select name="uname'+i+'" id="uname'+i+'"><option value="none" selected disabled hidden>Select an option</option>';
        // for (var j = 0; j < usernames_arr.length; j++) 
        // {
        //     rows += '<option value="'+usernames_arr[j]+'">'+usernames_arr[j]+'</option>';
        // }
        // rows += '</select>';

        //      datalist newly added
        rows+='<input list="browsers" id="uname'+i+'" name="myBrowser" /><datalist id="browsers">';
        for (var j = 0; j < usernames_arr.length; j++) 
        {
            rows += '<option value="'+usernames_arr[j]+'"></option>';
        }
        rows += '</td></tr>';
    }
    document.getElementById("user").innerHTML=rows;
}
