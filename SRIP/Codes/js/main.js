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
		if(ui.helper.hasClass("drag")){
			node.type=ui.helper.prevObject.attr("id");
			console.log(node.type);
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
		var output_position= $(this).position();
		var x1=start.left+output_position.left+($(this).width()/2);
		y1=start.top+output_position.top+($(this).height()/2);

		cur_con.attr('x1',x1).attr('y1', y1).attr('x2',x1+1).attr('y2',y1);
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
			var gate_id=gate.attr('id');
			
			ui.draggable.css({
				top:"45%",
				right:"-2px",
				left:"auto"
			});
			gate.data('output_lines').push(gate.data('line'));

			var x_abs=parseInt(gate.data('line').attr('x2'));
		    var y_abs=parseInt(gate.data('line').attr('y2'));
		    var this_x=parseInt($(this).css('left'));
		    var this_y=parseInt($(this).css('top'));
		    if((x_abs - this_x)< $(this).width()/2 && (y_abs - this_y)< $(this).height()/2)
		    {
		    	if($(this).data('inp1'))
		    		$(this).data('inp1').remove();
		    	$(this).data('inp1',gate.data('line'));
		    	css_selector='#'+gate_id+" .input1";
		    	x2=$(this).position().left + $(css_selector).position().left+3;
		    	y2=$(this).position().top + $(css_selector).position().top+3;
		    	gate.data('line').attr('x2', x2).attr('y2', y2);
		    	
		    	
		    }
		    else if((x_abs - this_x)< $(this).width()/2 && (y_abs - this_y)> $(this).height()/2)
		    {
		    	if($(this).data('inp2'))
		    		$(this).data('inp2').remove();
		    	$(this).data('inp2',gate.data('line'));
		    	css_selector='#'+gate_id+" .input2";
		    	x2=$(this).position().left + $(css_selector).position().left+3;
		    	y2=$(this).position().top + $(css_selector).position().top+3;
		    	gate.data('line').attr('x2', x2).attr('y2', y2);
		    }
		    else gate.data('line').remove();
		    gate.data('line', null);
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
		var image="images/"+node.type+".svg";
		console.log(image);
		html='<div><img src="'+image+'" class="img-thumbnail"></div>';
		var dom=$(html).css({
			position:"absolute",
			top: node.position.top,
			left: node.position.left,
			"z-index":2,
			"max-width":"7%"
		}).draggable({
			containment:"parent",

			drag: function(event,ui){
				var lines= $(this).data('output_lines');
				var inp_1= $(this).data('inp1');
				var inp_2= $(this).data('inp2');

				if(lines){
					lines.forEach(function(line,id){
						$(line).attr('x1', $(this).position().left + $(this).width()).attr('y1', $(this).position().top + ($(this).height())/2);
					}.bind(this));
				}

				if(inp_1){
					console.log($(this).position());
					$(inp_1).attr('x2', $(this).position().left + 2).attr('y2', $(this).position().top+ $('.input1').position().top+5);
				}

				if(inp_2)
				{
					$(inp_2).attr('x2', $(this).position().left + 2).attr('y2', $(this).position().top+ $('.input2').position().top+5);
				}
			},

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
