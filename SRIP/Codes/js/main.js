//this file contains additional features written by me

//for variable size of the simulator according to screen size
var x=$(".simcir_outer_div").width();
x=x*0.90;

//the if else block decides which circuit to display according to user settings
if(sessionStorage.getItem("4bitMUX"))
{
	var mux={
		"width":x,
		"height":800,
		"showToolbox":true,
		"toolbox":[
		{"type":"Joint"},
		{"type":"DC"},
		{"type":"LED"},
		{"type":"PushOff"},
		{"type":"PushOn"},
		{"type":"Toggle"},
		{"type":"BUF"},
		{"type":"NOT"},
		{"type":"AND"},
		{"type":"NAND"},
		{"type":"OR"},
		{"type":"NOR"}
		],
		"devices":[
		{"type":"2bitMUX","id":"dev0","x":312,"y":104,"label":"2bitMultiplexer"},
		{"type":"2bitMUX","id":"dev1","x":176,"y":48,"label":"2bitMultiplexer"},
		{"type":"2bitMUX","id":"dev2","x":176,"y":144,"label":"2bitMultiplexer"},
		{"type":"Toggle","id":"dev3","x":80,"y":72,"label":"Toggle","state":{"on":false}},
		{"type":"Toggle","id":"dev4","x":80,"y":184,"label":"Toggle","state":{"on":false}},
		{"type":"Toggle","id":"dev5","x":64,"y":248,"label":"Toggle","state":{"on":true}},
		{"type":"Joint","id":"dev6","x":120,"y":256,"label":"Joint","state":{"direction":0}},
		{"type":"Joint","id":"dev7","x":136,"y":80,"label":"Joint","state":{"direction":0}},
		{"type":"Joint","id":"dev8","x":160,"y":256,"label":"Joint","state":{"direction":0}},
		{"type":"Toggle","id":"dev9","x":280,"y":240,"label":"Toggle","state":{"on":false}},
		{"type":"LED","id":"dev10","x":432,"y":112,"label":"LED"},
		{"type":"DC","id":"dev11","x":8,"y":16,"label":"I0"},
		{"type":"DC","id":"dev12","x":8,"y":72,"label":"I1"},
		{"type":"DC","id":"dev13","x":8,"y":128,"label":"I2"},
		{"type":"DC","id":"dev14","x":8,"y":184,"label":"I3"},
		{"type":"Toggle","id":"dev15","x":80,"y":16,"label":"Toggle","state":{"on":false}},
		{"type":"DC","id":"dev16","x":192,"y":240,"label":"S1"},
		{"type":"DC","id":"dev17","x":8,"y":248,"label":"S0"},
		{"type":"Toggle","id":"dev18","x":80,"y":128,"label":"Toggle","state":{"on":false}}
		],
		"connectors":[
		{"from":"dev0.in0","to":"dev1.out0"},
		{"from":"dev0.in1","to":"dev2.out0"},
		{"from":"dev0.in2","to":"dev9.out0"},
		{"from":"dev1.in0","to":"dev15.out0"},
		{"from":"dev1.in1","to":"dev3.out0"},
		{"from":"dev1.in2","to":"dev7.out0"},
		{"from":"dev2.in0","to":"dev18.out0"},
		{"from":"dev2.in1","to":"dev4.out0"},
		{"from":"dev2.in2","to":"dev8.out0"},
		{"from":"dev3.in0","to":"dev12.out0"},
		{"from":"dev4.in0","to":"dev14.out0"},
		{"from":"dev5.in0","to":"dev17.out0"},
		{"from":"dev6.in0","to":"dev5.out0"},
		{"from":"dev7.in0","to":"dev6.out0"},
		{"from":"dev8.in0","to":"dev5.out0"},
		{"from":"dev9.in0","to":"dev16.out0"},
		{"from":"dev10.in0","to":"dev0.out0"},
		{"from":"dev15.in0","to":"dev11.out0"},
		{"from":"dev18.in0","to":"dev13.out0"}
		]
	};
	
	if(sessionStorage.getItem("4bitMUX_module"))
	{
		$("#mux4").attr("checked", true);
		mux.toolbox.push({
			"type":"4bitMUX"
		});
	}

	if(sessionStorage.getItem("2bitMUX_module"))
	{
		$("#mux2").attr("checked", true);
		mux.toolbox.push({
			"type":"2bitMUX"
		});
	}
	//set all the settings as string inside div
	var html1=JSON.stringify(mux);
	document.querySelector(".simcir").innerHTML=html1;
	// document.querySelector(".simcir").classList.add("unclickable");

}

else if(sessionStorage.getItem("2bitMUX")){
	var mux={
		"width":x,
		"height":800,
		"showToolbox":true,
		"toolbox":[
		{"type":"Joint"},
		{"type":"DC"},
		{"type":"LED"},
		{"type":"PushOff"},
		{"type":"PushOn"},
		{"type":"Toggle"},
		{"type":"BUF"},
		{"type":"NOT"},
		{"type":"AND"},
		{"type":"NAND"},
		{"type":"OR"},
		{"type":"NOR"}
		],
		"devices":[
		{"type":"Toggle","id":"dev0","x":120,"y":272,"label":"Toggle","state":{"on":false}},
		{"type":"AND","id":"dev1","x":312,"y":176,"label":"AND"},
		{"type":"AND","id":"dev2","x":312,"y":224,"label":"AND"},
		{"type":"OR","id":"dev3","x":368,"y":200,"label":"OR"},
		{"type":"DC","id":"dev4","x":56,"y":272,"label":"S0"},
		{"type":"Toggle","id":"dev5","x":120,"y":168,"label":"Toggle","state":{"on":false}},
		{"type":"DC","id":"dev6","x":56,"y":168,"label":"I0"},
		{"type":"Toggle","id":"dev7","x":120,"y":216,"label":"Toggle","state":{"on":false}},
		{"type":"DC","id":"dev8","x":56,"y":216,"label":"I1"},
		{"type":"Joint","id":"dev9","x":256,"y":240,"label":"Joint","state":{"direction":0}},
		{"type":"Joint","id":"dev10","x":272,"y":192,"label":"Joint","state":{"direction":0}},
		{"type":"NOT","id":"dev11","x":216,"y":288,"label":"NOT"},
		{"type":"LED","id":"dev12","x":432,"y":200,"label":"LED"}
		],
		"connectors":[
		{"from":"dev0.in0","to":"dev4.out0"},
		{"from":"dev1.in0","to":"dev5.out0"},
		{"from":"dev1.in1","to":"dev10.out0"},
		{"from":"dev2.in0","to":"dev7.out0"},
		{"from":"dev2.in1","to":"dev9.out0"},
		{"from":"dev3.in0","to":"dev1.out0"},
		{"from":"dev3.in1","to":"dev2.out0"},
		{"from":"dev5.in0","to":"dev6.out0"},
		{"from":"dev7.in0","to":"dev8.out0"},
		{"from":"dev9.in0","to":"dev0.out0"},
		{"from":"dev10.in0","to":"dev11.out0"},
		{"from":"dev11.in0","to":"dev0.out0"},
		{"from":"dev12.in0","to":"dev3.out0"}
		]
	};
	if(sessionStorage.getItem("4bitMUX_module"))
	{
		$("#mux4").attr("checked", true);
		mux.toolbox.push({
			"type":"4bitMUX"
		});
	}

	if(sessionStorage.getItem("2bitMUX_module"))
	{
		$("#mux2").attr("checked", true);
		mux.toolbox.push({
			"type":"2bitMUX"
		});
	}
	var html1=JSON.stringify(mux);
	document.querySelector(".simcir").innerHTML=html1;
	// document.querySelector(".simcir").classList.add("unclickable");
}

else{
	var obj={
		"width":x,
		"height":800,
		"showToolbox":true,
		"toolbox":[
		{"type":"Joint"},
		{"type":"DC"},
		{"type":"LED"},
		{"type":"PushOff"},
		{"type":"PushOn"},
		{"type":"Toggle"},
		{"type":"BUF"},
		{"type":"NOT"},
		{"type":"AND"},
		{"type":"NAND"},
		{"type":"OR"},
		{"type":"NOR"}
		],
		"devices":[
		],
		"connectors":[
		]
	};

	if(sessionStorage.getItem("4bitMUX_module"))
	{
		$("#mux4").attr("checked", true);
		obj.toolbox.push({
			"type":"4bitMUX"
		});
	}

	if(sessionStorage.getItem("2bitMUX_module"))
	{
		$("#mux2").attr("checked", true);
		obj.toolbox.push({
			"type":"2bitMUX"
		});
	}

	var html=JSON.stringify(obj);
	document.querySelector(".simcir").innerHTML=html;
}


$("#mux2").change(function(event) {
	if(this.checked)
	{
		sessionStorage.setItem("2bitMUX_module",1);
	}
	else
	{
		sessionStorage.removeItem("2bitMUX_module");
	}
});

$("#mux4").change(function(event) {
	if(this.checked)
	{
		sessionStorage.setItem("4bitMUX_module",1);
	}
	else
	{
		sessionStorage.removeItem("4bitMUX_module");
	}
});

$("#load_mux2").click(function(event) {
	sessionStorage.setItem("2bitMUX",1);
	sessionStorage.removeItem("4bitMUX");
	location.reload();
});

$("#load_mux4").click(function(event) {
	sessionStorage.setItem("4bitMUX",1);
	sessionStorage.removeItem("2bitMUX");
	location.reload();
});

$("#clear_workspace").click(function(event) {
	sessionStorage.removeItem("4bitMUX");
	sessionStorage.removeItem("2bitMUX");
	location.reload();
});

$("#clear_settings").click(function(event) {
	sessionStorage.clear();
});