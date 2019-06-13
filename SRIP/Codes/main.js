
$("#check1").click(function(event) {
  /*sessionStorage.removeItem('8bitRingCounter');
  sessionStorage.removeItem('8bitCounter');*/
  location.reload();
});
/*  Go JS ..junction later on changed the library
function goIntro() {

  var diagram =new go.Diagram("myDiagramDiv");
  
  var nodeDataArray =[
    {key:"Alpha"},
    {key:"Beta"}
  ];
  var linkDataArray =[
    { from:"Alpha", to:"Beta"}
  ];
  diagram.model =new go.GraphLinksModel(nodeDataArray,linkDataArray);
}


  Go JS ..junction later on changed the library
function init() {
  // body...
  var $ =go.GraphObject.make;
  myDiagram=$(go.Diagram,"myDiagramDiv");

  myDiagram.nodeTemplate =
    $(go.Node,"Spot",
      $(go.Panel, "Auto",
          $(go.Shape,"RoundedRectangle",{ fill: "lightblue"}),
          $(go.TextBlock, { margin:10 },
              new go.Binding("text","key")
            )
        ),
      //left gate
      $(go.Shape,"Ellipse",
        {
          fill:"pink"
          //desiredSize: new go.Size(10,10),alignment: go.Spot.Left,
          portId: "l",fromSpot:go.Spot.Left, toSpot:go.Spot.Left
        }
      ),
      //right gate
      $(go.Shape,"Ellipse",
        {
          fill:"green"
          desiredSize: new go.Size(10,10),alignment: go.Spot.Right,
          portId: "r",fromSpot:go.Spot.Right , toSpot: go.Spot.Right
        }
      ),
    );


  myDiagram.model = new go.GraphLinksModel(
    [
      {key:"Alpha"},
      {key:"Beta"}
    ],
    [
      {from:"Alpha",to:"Beta", fromPort:"r",toPort:"l"}
    ]
  );

  myDiagram.model.linkFromPortIdProperty ="fromPort";
  myDiagram.model.linktoPortIdProperty ="toPort";

}*/



//to close the combo box when user clicks outside it
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
