// Fetching HTML Elements in Variables by ID.
var x = document.getElementById("form");
var createform = document.createElement('form'); // Create New Element Form
createform.setAttribute("action", ""); // Setting Action Attribute on Form
createform.setAttribute("method", "post"); // Setting Method Attribute on Form
x.appendChild(createform);

var linebreak = document.createElement('br');
createform.appendChild(linebreak);

var namelabel = document.createElement('label'); // Create Label for Name Field
namelabel.innerHTML = "Name : "; // Set Field Labels
createform.appendChild(namelabel);

var inputelement = document.createElement('input'); // Create Input Field for Name
inputelement.setAttribute("type", "text");
inputelement.setAttribute("name", "dname");
createform.appendChild(inputelement);

var emaillabel = document.createElement('label'); // Create Label for E-mail Field
emaillabel.innerHTML = "Email : ";
createform.appendChild(emaillabel);

var emailelement = document.createElement('input'); // Create Input Field for E-mail
emailelement.setAttribute("type", "text");
emailelement.setAttribute("name", "demail");
createform.appendChild(emailelement);

var telephonelabel = document.createElement('label'); // Create Label for Telephone Field
telephonelabel.innerHTML = "Telephone : "; // Set Field Labels
createform.appendChild(telephonelabel);

var inputelement = document.createElement('input'); // Create Input Field for Telephone
inputelement.setAttribute("type", "text");
inputelement.setAttribute("name", "dname");
createform.appendChild(inputelement);

var addresslabel = document.createElement('label'); // Create Label for Address Field
addresslabel.innerHTML = "Address : "; // Set Field Labels
createform.appendChild(addresslabel);

var inputelement = document.createElement('input'); // Create Input Field for Address
inputelement.setAttribute("type", "text");
inputelement.setAttribute("name", "dname");
createform.appendChild(inputelement);

var ziplabel = document.createElement('label'); // Create Label for ZIP Field
ziplabel.innerHTML = "Zip : "; // Set Field Labels
createform.appendChild(ziplabel);

var inputelement = document.createElement('input'); // Create Input Field for ZIP
inputelement.setAttribute("type", "text");
inputelement.setAttribute("name", "dname");
createform.appendChild(inputelement);

var linebreak = document.createElement('br');
createform.appendChild(linebreak);

var messagelabel = document.createElement('label'); // Append Textarea
messagelabel.innerHTML = "Message : ";
createform.appendChild(messagelabel);

var texareaelement = document.createElement('textarea');
texareaelement.setAttribute("name", "dmessage");
createform.appendChild(texareaelement);

var messagebreak = document.createElement('br');
createform.appendChild(messagebreak);

var submitelement = document.createElement('input'); // Append Submit Button
submitelement.setAttribute("type", "submit");
submitelement.setAttribute("name", "dsubmit");
submitelement.setAttribute("value", "Submit");
createform.appendChild(submitelement);