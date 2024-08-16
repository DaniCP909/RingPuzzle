import {RingImg} from "./RingImg.js"
const N_RINGS = 4;
const N_GROOVES  = 360;
const DEGREES_STEP = (360 / N_GROOVES);


const puzzlecontainer = document.getElementById('puzzlecontainer');

document.addEventListener('dragstart', (e) => {
    e.preventDefault();
});
let isDragging = false;
let selectedRing = 0;

let accum_degrees = []
let all_rings = []
for(var i = 0; i < N_RINGS; i++) {
    const r_img = new RingImg(i, ("images/ring_" + i + ".png"), puzzlecontainer, N_RINGS)
    all_rings.push(r_img)
    accum_degrees.push(0);

}
let clickedMouse = {
    x: 0,
    y: 0,
}
let mouseAngle = 0;
let mouseDegrees = 0;
let final_degrees = 0;

function clickDown(e) {
    clickedMouse.x = e.clientX;
    clickedMouse.y = e.clientY;
    console.log("MOUSE --> " + clickedMouse.x + ", " + clickedMouse.y)
    for(const ri of all_rings) {
        if(ri.isClicked(clickedMouse.x, clickedMouse.y)) {
            console.log(ri.ring_id);
            selectedRing = ri.ring_id;
            isDragging = true;
            mouseAngle = Math.atan2(clickedMouse.y - all_rings[0].center[1], clickedMouse.x - all_rings[0].center[0]);
            mouseDegrees = mouseAngle * (180 / Math.PI);
        }
    }
    //console.log(all_rings[0].isClicked(mouseX, mouseY));
    //console.log(mouseX + " " + mouseY + " | " + ri.center[0] + " " + ri.center[1]);
}

function dragMove(e) {
    if(isDragging) {
        const drag_img = all_rings[selectedRing];

        const angle = Math.atan2(e.clientY - drag_img.center[1], e.clientX - drag_img.center[0]);
        const degrees = angle * (180 / Math.PI);

        final_degrees = accum_degrees[selectedRing] + (degrees - mouseDegrees);
        console.log("final degrees: " + final_degrees);
        if((final_degrees.toFixed(0) % DEGREES_STEP.toFixed(0)) == 0)drag_img.ringimg.style.transform = `rotate(${final_degrees}deg)`;
    }
}

function clickUp() {
    isDragging = false;
    accum_degrees[selectedRing] = final_degrees;
}

document.addEventListener('mousedown', clickDown);
document.addEventListener('mousemove', dragMove);
document.addEventListener('mouseup', clickUp);
console.log(all_rings.length);


