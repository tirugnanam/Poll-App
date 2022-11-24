// var Person = Ember.Object.extend({
//     a: "Class defined variable",
//     firstName: null,
//     lastName: null,
//     function_alert() {
//         alert("Hi, my name is " + this.get('firstName') + this.get('lastName'));
//       },
//     function_return(){
//         return "Return statement..." + this.get('a');
//     }
//   });

// var newa1 = Person.create();

// var person = Person.create({
//     firstName: "Tirugnanam",
//     lastName: "M",
//     a: "....PERSON....override",
//     uw: "00000"
// });

// Person.reopen({
//     a: "Reopened and value 'a' changed",
//     b: "reopen new value 'b'",
// });

// // Person.create();
// var newa2 = Person.create();
// var newa3 = Person.create();




// var Computed = Ember.Object.extend({
//     firstName: null,
//     lastName: null,
//     age: null,
//     country: null,
  
//     fullName: Ember.computed('firstName', 'lastName', function() {
//       return this.get('firstName') + ' ' + this.get('lastName');
//     }),
  
//     description: Ember.computed('fullName', 'age', 'country', function() {
//       return this.get('fullName') + '; Age: ' + this.get('age') + '; Country: ' + this.get('country');
//     })
//   });
  
//   var captainAmerica = Computed.create({
//     firstName: 'Steve',
//     lastName: 'Rogers',
//     age: 80,
//     country: 'USA'
//   });
//   captainAmerica.set('firstName','harry');

export default Ember.Controller.extend({
    // fName: "Prem",
    // lName: "Sundar",
    // // alert: person.function_alert(),
    // // return: person.function_return(),
    // newa1, 
    // person,
    // newa2,
    // newa3,
    // captain: captainAmerica.get('description'),
    // imgurl: "",
    // // imgurl: "/home/tiru-pt6045/Downloads/zoho.jpeg",

    login: function()
    {
        console.log("move to login ember");
        location.assign('login');
    },
    signup: function()
    {
        console.log("move to signup ember");
        location.assign('signup');
    },
});
