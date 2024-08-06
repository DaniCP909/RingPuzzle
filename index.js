const N_RINGS = 4;
const N_GROOVES  = 15;
const DEGREES_STEP = (360 / N_GROOVES);

class RingImg {
    constructor(ring_id, path, father, n_rings) {
        this.ring_id = ring_id;
        this.path = path;
        this.n_rings = n_rings;
        const img = document.createElement('img');
        img.src = path;
        img.alt = "ring" + ring_id;

        img.style.width = `100%`;
        img.style.height = `100%`;
        img.style.userSelect = `none`; //hay más pero parecen deprecated
        img.style.gridArea = `1 / 1`;
        father.appendChild(img)
        const rect = img.getBoundingClientRect();
        this.ringimg = img;
        this.center = [
            rect.left + rect.width / 2,
            rect.top + rect.height / 2,
        ]
        this.w_h = rect.width;
    }

    static dist(x1, x2, y1, y2) {
        const dx = x2 - x1;
        const dy = y2 - y1;
        return Math.sqrt((dx*dx) + (dy * dy));
    }
    isClicked(x, y) {
        const distance = RingImg.dist(x, this.center[0], y, this.center[1]);
        const radius = this.w_h / 2;
        const down_limit = this.ring_id * ((this.w_h / 2) / this.n_rings);
        const up_limit = (this.ring_id + 1) * ((this.w_h / 2) / this.n_rings); 
        return ((distance < radius) && distance > down_limit) && (distance < up_limit);
    }
}

const puzzlecontainer = document.getElementById('puzzlecontainer');

document.addEventListener('dragstart', (e) => {
    e.preventDefault();
});
let isDragging = false;
let selectedRing = 0;

let all_rings = []
for(var i = 0; i < N_RINGS; i++) {
    const r_img = new RingImg(i, ("images/ring_" + i + ".png"), puzzlecontainer, N_RINGS)
    all_rings.push(r_img)
}
let clickedMouse = {
    x: 0,
    y: 0,
}
let mouseAngle = 0;
let mouseDegrees = 0;

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


        const final_degrees = degrees - mouseDegrees;
        console.log("mouse degrees: " + mouseDegrees + " | degrees: " + degrees);
        if((final_degrees.toFixed(0) % DEGREES_STEP.toFixed(0)) == 0)drag_img.ringimg.style.transform = `rotate(${final_degrees}deg)`;
    }
}

function clickUp() {
    isDragging = false;
    mouseAngle = 0;
    mouseDegrees = 0;
}

document.addEventListener('mousedown', clickDown);
document.addEventListener('mousemove', dragMove);
document.addEventListener('mouseup', clickUp);
console.log(all_rings.length);


