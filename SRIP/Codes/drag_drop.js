var id;
function allowDrop(ev) {
  // body...
  ev.preventDefault();
}

function dragStart(ev) {
  // body...
  id=ev.target.id
}

function drop(ev) {
  // body...
  ev.target.append(document.getElementById(id));
}
// Drop Drag function
$( function() {
 
        // There's the gallery and the workspace
        var $gallery = $( "#gallery" ),
          $workspace = $( "#workspace" );
     
        // Let the gallery items be draggable
        $( "li", $gallery ).draggable({
          cancel: "a.ui-icon", // clicking an icon won't initiate dragging
          revert: "invalid", // when not dropped, the item will revert back to its initial position
          containment: "document",
          helper: "clone",
          cursor: "move"
      });
 
        // making the workspace be droppable, accepting the gallery items
        $workspace.droppable({
          accept: "#gallery > li",
          classes: {
            "ui-droppable-active": "ui-state-highlight"
          },
          drop: function( event, ui ) {
            deleteImage( ui.draggable );
          }
        });
 
    // Let the gallery be droppable as well, accepting items from the workspace
    $gallery.droppable({
      accept: "#workspace li",
      classes: {
        "ui-droppable-active": "custom-state-active"
      },
      drop: function( event, ui ) {
        //replaceImage( ui.draggable );
      }
    });
 
    // Image deletion function
    var replace_icon = "<a href='#link' title='replace this image' class='ui-icon ui-icon-refresh'>replace gate</a>";
    function deleteImage( $item ) {
      $item.fadeOut(function() {
        var $list = $( "ul", $workspace ).length ?
          $( "ul", $workspace ) :
          $( "<ul class='gallery ui-helper-reset'/>" ).appendTo( $workspace );
 
        $item.find( "a.ui-icon-workspace" ).remove();
        $item.append( replace_icon ).appendTo( $list ).fadeIn(function() {
          $item
            .animate({ width: "48px" })
            .find( "img" )
              .animate({ height: "36px" });
        });
      });
    }
 
    // Image replace function
    var workspace_icon = "<a href='#link' title='Delete this image' class='ui-icon ui-icon-workspace'>Delete gate</a>";
    function replaceImage( $item ) {
      $item.fadeOut(function() {
        $item
          .find( "a.ui-icon-refresh" )
            .remove()
          .end()
          .css( "width", "96px")
          .append( workspace_icon )
          .find( "img" )
            .css( "height", "72px" )
          .end()
          .appendTo( $gallery )
          .fadeIn();
      });
    }
 
 
  // Resolve the icons behavior with event delegation
    $( "ul.gallery > li" ).on( "click", function( event ) {
      var $item = $( this ),
        $target = $( event.target );
 
      if ( $target.is( "a.ui-icon-workspace" ) ) {
        deleteImage( $item );
      } else if ( $target.is( "a.ui-icon-zoomin" ) ) {
        viewLargerImage( $target );
      } else if ( $target.is( "a.ui-icon-refresh" ) ) {
        replaceImage( $item );
      }
 
      return false;
    });
  } );