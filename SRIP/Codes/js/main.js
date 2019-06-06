var input1_id=0;
var input2_id=0;
var id=0;

function input1()
{
	var input_div='<div class="input input1"></div>';
	var input=$(input_div).css({
		position:"absolute",
		width:"8px",
		height: "8px",
		left:"-2px",
		top:"22%",
		"background-color":"grey",
		"border-radius":"50%",
		"z-index":"5"
	});
	return input;
}

function input2()
{
	var input_div='<div class="input input2"></div>';
	var input=$(input_div).css({
		position:"absolute",
		width:"8px",
		height: "8px",
		left:"-2px",
		top:"65%",
		"background-color":"grey",
		"border-radius":"50%",
		"z-index":"5"
	});
	return input;
}

function output()
{
	var output_div='<div class="output"></div>';
	var output=$(output_div).css({
		position:"absolute",
		width:"8px",
		height: "8px",
		right:"-2px",
		top:"45%",
		"background-color":"#47cf73",
		"border-radius":"50%",
		"z-index":"5"
	});
	return output;
}

var diagram={"devices":[],
			 "connectors":[]};


var tools=$(".drag");
tools.draggable({
	helper:"clone"
});

var canvas=$("#drop_zone").droppable({
	drop: function(event, ui){
		var node={
			_id: id,
			position: ui.helper.position()
		};
		node.position.left-=$('#tools').width();
		id=id+1;
		if(ui.helper.hasClass("tool_1")){
			node.type="Tool_1";
		}
		else if(ui.helper.hasClass("tool_2")){
			node.type="Tool_2";
		}
		else if(ui.helper.hasClass("tool_3")){
			node.type="Tool_3";
		}
		else if(ui.helper.hasClass("tool_4")){
			node.type="Tool_4";
		}
		else if(ui.helper.hasClass("tool_5")){
			node.type="Tool_5";
		}
		else if(ui.helper.hasClass("tool_6")){
			node.type="Tool_6";
		}
		else if(ui.helper.hasClass("tool_7")){
			node.type="Tool_7";
		}
		else if(ui.helper.hasClass("tool_8")){
			node.type="Tool_8";
		}
		else{
			id=id-1;
			return;
		}
		diagram.devices.push(node);
		renderDiagram(diagram);
	}
});

function interact()
{
	$(".output").mousedown(function(event) {
		var cur_gate = $(this).closest('.gate');
		var connector=$('#connector_canvas');
		var cur_con;

		if(!$(cur_gate).data('output_lines'))
			$(cur_gate).data('output_lines', []);

		if(!$(cur_gate).data('line',))
		{
			cur_con = $(document.createElementNS('http://www.w3.org/2000/svg','line'));
			cur_gate.data('line', cur_con);
		}
		else cur_con = cur_gate.data('line');

		connector.append(cur_con);
		var start= cur_gate.position();
		cur_con.attr('x1', start.left + cur_gate.width()-5).attr('y1', start.top+(0.50 * cur_gate.height())).attr('x2',start.left+cur_gate.width()).attr('y2',start.top+(0.48 * cur_gate.height()));
	});

	$(".output").draggable({
		containment: canvas,
		drag: function(event,ui){
			var _end=$(event.target).parent().position();
			var end= $(event.target).position();
			if(_end&&end)
				$(event.target).parent().data('line').attr('x2',end.left+_end.left+5).attr('y2',end.top+_end.top+2);
		},

		stop: function(event,ui){
			if(!ui.helper.closest('.gate').data('line'))
				return;
			ui.helper.css({
				top:"45%",
				right:"-2px",
				left:"auto"
			});
			ui.helper.closest('.gate').data('line').remove();
			ui.helper.closest('.gate').data('line',null);
			console.log("stopped");
		}
	});

	$(".gate").droppable({
		accept: '.output',
		drop: function(event,ui){
			var gate=ui.draggable.closest('.gate'); //the gate whose output is being dragged
			$(this).data('connected-gate',gate);
			ui.draggable.css({
				top:"45%",
				right:"-2px",
				left:"auto"
			});
			gate.data('output_lines').push(gate.data('line'));

			var x_abs=parseInt(gate.data('line').attr('x2'));
		    var y_abs=parseInt(gate.data('line').attr('y2'));
		    // console.log(x_abs,y_abs);
		    // var xx=parseInt($(this).attr('left')) - x_abs;
		    // var yy=parseInt($(this).attr('top')) - y_abs;
		    // console.log(xx,yy);
		    var this_x=parseInt($(this).css('left'));
		    var this_y=parseInt($(this).css('top'));
		    // console.log(this_x,this_y);
		    // console.log($(this).height());
		    if((x_abs - this_x)< $(this).width()/2 && (y_abs - this_y)< $(this).height()/2)
		    	console.log("left upper");
		}	
	});
}

function renderDiagram(diagram){
	canvas.empty();
	var s='<svg id="connector_canvas"></svg>';
	canvas.append(s);
	for(var d in diagram.devices)
	{
		var node=diagram.devices[d];
		html='<div><img src="images/and_gate.png" class="img-thumbnail"></div>';
		var dom=$(html).css({
			position:"absolute",
			top: node.position.top,
			left: node.position.left,
			"z-index":2,
			"max-width":"7%"
		}).draggable({
			containment:"parent",

			stop: function(event,ui){
				var id=ui.helper.attr("id");
				diagram.devices[id].position.top=ui.position.top;
				diagram.devices[id].position.left=ui.position.left;
			}
		}).attr("id", node._id).addClass('gate');
		dom.append(input1()).append(input2()).append(output());
		canvas.append(dom);
	}
	interact();
}
