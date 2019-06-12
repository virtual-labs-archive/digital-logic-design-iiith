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
    console.log("0");
}

//nor
var A = false;
var B = false;
if(!(A || B)) {
    console.log("1");
} else {
    console.log("0");
}

//xor
var A = true;
var B = false;

if(A ^ B) {
    console.log("1");
} else {
    console.log("0");
}

//xnor
var A = true;
var B = true;

if(!(A ^ B)) {
    console.log("1");
} else {
    console.log("0");
}


//temporary code logic
const logicGates = {
  nand(a, b) {
   return !(a && b);
  },
  not(a) {
   return this.nand(a, a);
  },
  and(a, b) {
   return this.not(this.nand(a, b));
  },
  or(a, b) {
   return this.nand(this.not(a), this.not(b));
  },
  nor(a, b) {
   return this.not(this.or(a, b));
  },
  xor(a, b) {
   return this.and(this.nand(a, b), this.or(a, b));
  },
  xnor(a, b) {
   return this.not(this.xor(a, b));
  }
};

const results = Object.keys(logicGates)
 .map(gate => [
    { a: false, b: false },
    { a: false, b: true },
    { a: true, b: false },
    { a: true, b: true },
   ].map(test => {
     const result = (({ a, b }) => logicGates[gate](a, b))(test);
     const desc = JSON.stringify(test)
      .replace(/:|"|\{|\}/g, ' ')
      .replace(/\s+/g, ' ')
      .replace(/true/g, 'true ');
  
     return `${gate}:${desc}= ${result}\n`;
}).
join('')
  ).join('\n');

console.log(results);

