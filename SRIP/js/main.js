var input1_id=0;
var input2_id=0;
$(init);
function init(){

	var id=0;

	function input1()
	{
		var input_div='<div class="input"></div>';
		var input=$(input_div).css({
			position:"absolute",
			width:"8px",
			height: "8px",
			left:"-2px",
			top:"22%",
			"background-color":"#47cf73",
			"border-radius":"50%",
			"z-index":"5"
		});
		return input;
	}

	function input2()
	{
		var input_div='<div class="input"></div>';
		var input=$(input_div).css({
			position:"absolute",
			width:"8px",
			height: "8px",
			left:"-2px",
			top:"65%",
			"background-color":"#47cf73",
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

	var diagram=[];

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
			diagram.push(node);
			renderDiagram(diagram);
		}
	});

	function renderDiagram(diagram){
		canvas.empty();
		for(var d in diagram)
		{
			var node=diagram[d];
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
					diagram[id].position.top=ui.position.top;
					diagram[id].position.left=ui.position.left;
				}
			}).attr("id", node._id);
			dom.append(input1()).append(input2()).append(output());
			canvas.append(dom);
		}
	}
}