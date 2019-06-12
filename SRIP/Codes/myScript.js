function myFunction() {
     document.getElementById("myDropdown").classList.toggle("show");
   }

    // Close the dropdown if the user clicks outside of it
  window.onclick = function(event) {
   if (!event.target.matches('.dropbtn')) {
    var dropdowns = document.getElementsByClassName("dropdown-content");
    var i;
    for (i = 0; i < dropdowns.length; i++) {
      var openDropdown = dropdowns[i];
      if (openDropdown.classList.contains('show')) {
        openDropdown.classList.remove('show');
      }
    }
  }
}
//and
var A = true;
var B = true;
if(A && B) {
    console.log("1");
} else {
    console.log("0");
}

//or
var A = true;
var B = false;
if(A || B) {
    console.log("1");
} else {
    console.log("0");
}

//not
var A = true;
var B = false;
if(A || B) {
    console.log("1");
} else {
    console.log("0");
}

//nand
var A = true;
var B = true;
if(!(A && B)) {
    console.log("1");
} else {
    console.log("Bye");
}

//nor
var A = false;
var B = false;
if(!(A || B)) {
    console.log("1");
} else {
    console.log("Bye");
}

//xor
var A = true;
var B = false;

if(A ^ B) {
    console.log("1");
} else {
    console.log("Bye");
}

//xnor
var A = true;
var B = true;

if(!(A ^ B)) {
    console.log("Hello");
} else {
    console.log("Bye");
}


