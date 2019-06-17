// Get Circut data and Set Circuit data button action
 $(function() {

     var $s = simcir;

     var $simcir = $('#mySimcir');

     var getCircuitData = function() {
        return $s.controller(
        $simcir.find('.simcir-workspace') ).text();
     };
    
     var setCircuitData = function(data) {
        $s.setupSimcir($simcir, JSON.parse(data) );
     };

      //button click evt
     $('#getDataBtn').click(function() {
        $('#dataArea').val(getCircuitData() );
     });
    
     $('#setDataBtn').click(function() {
        setCircuitData($('#dataArea').val() );
     })
     
  });

// load 2-bit comaparator

function myfunction() {
  
  var data={
  "width":1100,
  "height":870,
  "showToolbox":true,
  
  "devices":[
    {"type":"NOR","id":"dev0","x":616,"y":240,"label":"NOR"},
    {"type":"NOR","id":"dev1","x":616,"y":320,"label":"NOR"},
    {"type":"Toggle","id":"dev2","x":128,"y":144,"label":"Toggle","state":{"on":true}},
    {"type":"DC","id":"dev3","x":88,"y":56,"label":"DC"},
    {"type":"DC","id":"dev4","x":208,"y":56,"label":"DC"},
    {"type":"DC","id":"dev5","x":328,"y":56,"label":"DC"},
    {"type":"DC","id":"dev6","x":448,"y":56,"label":"DC"},
    {"type":"Joint","id":"dev7","x":192,"y":256,"label":"Joint","state":{"direction":0}},
    {"type":"Joint","id":"dev8","x":408,"y":240,"label":"Joint","state":{"direction":0}},
    {"type":"Toggle","id":"dev9","x":496,"y":144,"label":"Toggle","state":{"on":false}},
    {"type":"Toggle","id":"dev10","x":360,"y":144,"label":"Toggle","state":{"on":false}},
    {"type":"Joint","id":"dev11","x":288,"y":320,"label":"Joint","state":{"direction":0}},
    {"type":"Joint","id":"dev12","x":536,"y":336,"label":"Joint","state":{"direction":0}},
    {"type":"Joint","id":"dev13","x":816,"y":336,"label":"Joint","state":{"direction":0}},
    {"type":"LED","id":"dev14","x":856,"y":264,"label":"LED"},
    {"type":"AND","id":"dev15","x":752,"y":264,"label":"AND"},
    {"type":"NOT","id":"dev16","x":376,"y":440,"label":"NOT"},
    {"type":"NOT","id":"dev17","x":504,"y":440,"label":"NOT"},
    {"type":"NAND","id":"dev18","x":712,"y":504,"label":"NAND"},
    {"type":"NAND","numInputs":"3","label":"NAND(3in)","id":"dev19","x":720,"y":720},
    {"type":"Joint","id":"dev20","x":192,"y":504,"label":"Joint","state":{"direction":0}},
    {"type":"Joint","id":"dev21","x":424,"y":520,"label":"Joint","state":{"direction":0}},
    {"type":"Joint","id":"dev22","x":312,"y":608,"label":"Joint","state":{"direction":0}},
    {"type":"Joint","id":"dev23","x":432,"y":624,"label":"Joint","state":{"direction":0}},
    {"type":"Joint","id":"dev24","x":544,"y":632,"label":"Joint","state":{"direction":0}},
    {"type":"Joint","id":"dev25","x":176,"y":712,"label":"Joint","state":{"direction":0}},
    {"type":"Joint","id":"dev26","x":336,"y":728,"label":"Joint","state":{"direction":0}},
    {"type":"Joint","id":"dev27","x":568,"y":736,"label":"Joint","state":{"direction":0}},
    {"type":"NAND","numInputs":"3","label":"NAND(3in)","id":"dev28","x":712,"y":616},
    {"type":"NAND","numInputs":"3","label":"NAND(3in)","id":"dev29","x":856,"y":616},
    {"type":"NOR","id":"dev30","x":880,"y":416,"label":"NOR"},
    {"type":"Joint","id":"dev31","x":832,"y":472,"label":"Joint","state":{"direction":0}},
    {"type":"LED","id":"dev32","x":928,"y":616,"label":"LED"},
    {"type":"LED","id":"dev33","x":936,"y":416,"label":"LED"},
    {"type":"Toggle","id":"dev34","x":240,"y":144,"label":"Toggle","state":{"on":true}}
  ],
  "connectors":[
    {"from":"dev0.in0","to":"dev8.out0"},
    {"from":"dev0.in1","to":"dev7.out0"},
    {"from":"dev1.in0","to":"dev11.out0"},
    {"from":"dev1.in1","to":"dev12.out0"},
    {"from":"dev2.in0","to":"dev3.out0"},
    {"from":"dev7.in0","to":"dev2.out0"},
    {"from":"dev8.in0","to":"dev10.out0"},
    {"from":"dev9.in0","to":"dev6.out0"},
    {"from":"dev10.in0","to":"dev5.out0"},
    {"from":"dev11.in0","to":"dev34.out0"},
    {"from":"dev12.in0","to":"dev9.out0"},
    {"from":"dev13.in0","to":"dev15.out0"},
    {"from":"dev14.in0","to":"dev15.out0"},
    {"from":"dev15.in0","to":"dev0.out0"},
    {"from":"dev15.in1","to":"dev1.out0"},
    {"from":"dev16.in0","to":"dev10.out0"},
    {"from":"dev17.in0","to":"dev9.out0"},
    {"from":"dev18.in0","to":"dev20.out0"},
    {"from":"dev18.in1","to":"dev21.out0"},
    {"from":"dev19.in0","to":"dev25.out0"},
    {"from":"dev19.in1","to":"dev26.out0"},
    {"from":"dev19.in2","to":"dev27.out0"},
    {"from":"dev20.in0","to":"dev2.out0"},
    {"from":"dev21.in0","to":"dev16.out0"},
    {"from":"dev22.in0","to":"dev34.out0"},
    {"from":"dev23.in0","to":"dev21.out0"},
    {"from":"dev24.in0","to":"dev17.out0"},
    {"from":"dev25.in0","to":"dev2.out0"},
    {"from":"dev26.in0","to":"dev22.out0"},
    {"from":"dev27.in0","to":"dev24.out0"},
    {"from":"dev28.in0","to":"dev22.out0"},
    {"from":"dev28.in1","to":"dev23.out0"},
    {"from":"dev28.in2","to":"dev24.out0"},
    {"from":"dev29.in0","to":"dev18.out0"},
    {"from":"dev29.in1","to":"dev28.out0"},
    {"from":"dev29.in2","to":"dev19.out0"},
    {"from":"dev30.in0","to":"dev13.out0"},
    {"from":"dev30.in1","to":"dev31.out0"},
    {"from":"dev31.in0","to":"dev29.out0"},
    {"from":"dev32.in0","to":"dev29.out0"},
    {"from":"dev33.in0","to":"dev30.out0"},
    {"from":"dev34.in0","to":"dev4.out0"}
  ]
};
  var html1=JSON.stringify(data);
  document.querySelector(".simcir").innerHTML=html1;

};


//save as txt and load selected file code
function saveTextAsFile()
{
    var textToSave = document.getElementById("dataArea").value;
    var textToSaveAsBlob = new Blob([textToSave], {type:"text/plain"});
    var textToSaveAsURL = window.URL.createObjectURL(textToSaveAsBlob);
    var fileNameToSaveAs = document.getElementById("inputFileNameToSaveAs").value;
 
    var downloadLink = document.createElement("a");
    downloadLink.download = fileNameToSaveAs;
    downloadLink.innerHTML = "Download File";
    downloadLink.href = textToSaveAsURL;
    downloadLink.onclick = destroyClickedElement;
    downloadLink.style.display = "none";
    document.body.appendChild(downloadLink);
 
    downloadLink.click();
}
 
function destroyClickedElement(event)
{
    document.body.removeChild(event.target);
}
 
function loadFileAsText()
{
    var fileToLoad = document.getElementById("fileToLoad").files[0];
 
    var fileReader = new FileReader();
    fileReader.onload = function(fileLoadedEvent) 
    {
        var textFromFileLoaded = fileLoadedEvent.target.result;
        document.getElementById("dataArea").value = textFromFileLoaded;
    };
    fileReader.readAsText(fileToLoad, "UTF-8");
}