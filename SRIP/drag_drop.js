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