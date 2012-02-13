<html>
<body>
<script type="text/javascript">
var x=screen.availWidth-60;var y=screen.availHeight-50;
if(x<1024)
{
	x=1024;
}
if(y<660)
{
	y=660;
}
document.getElementById("applet_embed").innerHTML='<applet code="dldvirtuallabs.simulationApplet" archive="../DLDVirtualLabs.jar"'+' HEIGHT='+y+' WIDTH='+x+'>' +'<param name="file_name" value="line.txt"><param name="content1" value="Make 8 X 1 multiplexer with the help of 2 X 1 multiplexer"><param name="file_list" value="Multiplexer,8_1_multiplexer"></applet>' ;
</script>
<div id="applet_embed"></div> 
</body>
</html>
