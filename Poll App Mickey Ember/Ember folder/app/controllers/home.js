import Ember from 'ember';

export default Ember.Controller.extend({
    cookie_username: getCookie("username"),
    actions: {
        profile: function()
        {
            console.log("move to profile ember");
            location.assign('profile');
        },
        poll_create: function()
        {
            console.log("move to poll create ember");
            location.assign('poll_create');
        },
        poll_request: function()
        {
            console.log("move to poll request ember");
            location.assign('poll_request');
        },
        poll_result: function()
        {
            console.log("move to poll result ember");
            location.assign('poll_result');
        },
        poll_response: function()
        {
            console.log("move to poll response ember");
            location.assign('poll_response');
        }
    }

});

function getCookie(cname) {
    let name = cname + "=";
    let unameval="";
    let decodedCookie = decodeURIComponent(document.cookie);
    console.log(decodedCookie);
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
