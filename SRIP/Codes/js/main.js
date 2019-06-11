var x=$(".simcir_outer_div").width();
x=x*0.90;
if(sessionStorage.getItem('8bitCounter'))
{
	var counter={
		"width":x,
		"height":500,
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
		{"type":"NOR"},
		{"type":"XOR"},
		{"type":"XNOR"},
		{"type":"OSC"},
		{"type":"BusIn"},
		{"type":"BusOut"},
		{"type":"RS-FF"},
		{"type":"JK-FF"},
		{"type":"T-FF"},
		{"type":"D-FF"},
		{"type":"DSO","numInputs":8}
		],
		"devices":[
		{"type":"T-FF","id":"dev0","x":184,"y":16,"label":"T-FF"},
		{"type":"T-FF","id":"dev1","x":184,"y":64,"label":"T-FF"},
		{"type":"T-FF","id":"dev2","x":184,"y":112,"label":"T-FF"},
		{"type":"T-FF","id":"dev3","x":184,"y":160,"label":"T-FF"},
		{"type":"T-FF","id":"dev4","x":184,"y":208,"label":"T-FF"},
		{"type":"T-FF","id":"dev5","x":184,"y":256,"label":"T-FF"},
		{"type":"T-FF","id":"dev6","x":184,"y":304,"label":"T-FF"},
		{"type":"T-FF","id":"dev7","x":184,"y":352,"label":"T-FF"},
		{"type":"Out","id":"dev8","x":264,"y":64,"label":"D1"},
		{"type":"Out","id":"dev9","x":264,"y":112,"label":"D2"},
		{"type":"Out","id":"dev10","x":264,"y":160,"label":"D3"},
		{"type":"Out","id":"dev11","x":264,"y":208,"label":"D4"},
		{"type":"Out","id":"dev12","x":264,"y":256,"label":"D5"},
		{"type":"Out","id":"dev13","x":264,"y":304,"label":"D6"},
		{"type":"Out","id":"dev14","x":264,"y":352,"label":"D7"},
		{"type":"In","id":"dev15","x":120,"y":16,"label":"T"},
		{"type":"In","id":"dev16","x":120,"y":112,"label":"CLK"},
		{"type":"DSO","numInputs":8,"id":"dev17","x":424,"y":88,"label":"DSO","state":{"playing":true,"rangeIndex":0}},
		{"type":"Out","id":"dev18","x":264,"y":16,"label":"D0"},
		{"type":"OSC","id":"dev19","x":24,"y":144,"label":"OSC"},
		{"type":"DC","id":"dev20","x":24,"y":32,"label":"DC"}
		],
		"connectors":[
		{"from":"dev0.in0","to":"dev15.out0"},
		{"from":"dev0.in1","to":"dev16.out0"},
		{"from":"dev1.in0","to":"dev15.out0"},
		{"from":"dev1.in1","to":"dev0.out0"},
		{"from":"dev2.in0","to":"dev15.out0"},
		{"from":"dev2.in1","to":"dev1.out0"},
		{"from":"dev3.in0","to":"dev15.out0"},
		{"from":"dev3.in1","to":"dev2.out0"},
		{"from":"dev4.in0","to":"dev15.out0"},
		{"from":"dev4.in1","to":"dev3.out0"},
		{"from":"dev5.in0","to":"dev15.out0"},
		{"from":"dev5.in1","to":"dev4.out0"},
		{"from":"dev6.in0","to":"dev15.out0"},
		{"from":"dev6.in1","to":"dev5.out0"},
		{"from":"dev7.in0","to":"dev15.out0"},
		{"from":"dev7.in1","to":"dev6.out0"},
		{"from":"dev8.in0","to":"dev1.out0"},
		{"from":"dev9.in0","to":"dev2.out0"},
		{"from":"dev10.in0","to":"dev3.out0"},
		{"from":"dev11.in0","to":"dev4.out0"},
		{"from":"dev12.in0","to":"dev5.out0"},
		{"from":"dev13.in0","to":"dev6.out0"},
		{"from":"dev14.in0","to":"dev7.out0"},
		{"from":"dev15.in0","to":"dev20.out0"},
		{"from":"dev16.in0","to":"dev19.out0"},
		{"from":"dev17.in0","to":"dev18.out0"},
		{"from":"dev17.in1","to":"dev8.out0"},
		{"from":"dev17.in2","to":"dev9.out0"},
		{"from":"dev17.in3","to":"dev10.out0"},
		{"from":"dev17.in4","to":"dev11.out0"},
		{"from":"dev17.in5","to":"dev12.out0"},
		{"from":"dev17.in6","to":"dev13.out0"},
		{"from":"dev17.in7","to":"dev14.out0"},
		{"from":"dev18.in0","to":"dev0.out0"}
		]
	};
	if(sessionStorage.getItem('OSCfreq'))
	{
		$("#freq").val(sessionStorage.getItem('OSCfreq'));
		counter.toolbox[14]["freq"]=parseInt(sessionStorage.getItem('OSCfreq'));
		counter.devices[19]["freq"]=parseInt(sessionStorage.getItem('OSCfreq'));
	}
	
	if(sessionStorage.getItem('8bitCounter_module'))
	{
		$("#counter").attr("checked", true);
		counter.toolbox.push({
			"type":"8bitCounter"
		});
	}
	var html1=JSON.stringify(counter);
	document.querySelector(".simcir").innerHTML=html1;


}else
{
	var obj={
		"width":x,
		"height":500,
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
		{"type":"NOR"},
		{"type":"XOR"},
		{"type":"XNOR"},
		{"type":"OSC"},
		{"type":"BusIn"},
		{"type":"BusOut"},
		{"type":"RS-FF"},
		{"type":"JK-FF"},
		{"type":"T-FF"},
		{"type":"D-FF"},
		{"type":"DSO", "numInputs":8}
		],
		"devices":[
		],
		"connectors":[
		]
	};

	var html=JSON.stringify(obj);
	document.querySelector(".simcir").innerHTML=html;
	if(sessionStorage.getItem('OSCfreq'))
	{
		$("#freq").val(sessionStorage.getItem('OSCfreq'));
		obj.toolbox[14]["freq"]=parseInt(sessionStorage.getItem('OSCfreq'));
	}
	if(sessionStorage.getItem('8bitCounter_module'))
	{
		$("#counter").attr("checked", true);
		obj.toolbox.push({
			"type":"8bitCounter_module"
		});
	}
}


$("#counter").change(function(event) {
	if(this.checked)
	{
		sessionStorage.setItem('8bitCounter_module',1);
	}
	else
	{
		sessionStorage.removeItem('8bitCounter_module');
	}
});

$("#ringcounter").change(function(event) {
	if(this.checked)
	{
		sessionStorage.setItem('8bitRingCounter');
	}
	else
	{
		sessionStorage.removeItem('8bitRingCounter');
	}
});

$("#load_counter").click(function(event) {
	
	// location.reload(true);
});

$("#freq").change(function(event) {
	sessionStorage.setItem('OSCfreq', parseInt(this.value));
});

