import { createRingImagePiece } from "./RingImageFactory.js";


export const createRingPuzzle = (path, n_rings, father, ngrooves) => {
    const N_GROOVES = ngrooves;
    const DEGREES_STEP = (360 / N_GROOVES);
    let isDragging = false;
    let selectedRing = -1;
    let all_rings = [];
    let acumm_degrees = [];
    let clickedMouse = {
        x: 0,
        y: 0,
    }
    let mouseRads = 0;
    let mouseDegrees = 0;
    let finalDegrees = 0;

    function clickDown(e) {
        clickedMouse.x = e.clientX;
        clickedMouse.y = e.clientY;
        for(const ring of all_rings) {
            if(ring.isClicked(clickedMouse.x, clickedMouse.y)) {
                selectedRing = ring.id;
                isDragging = true;
                mouseRads = Math.atan2(clickedMouse.y - all_rings[0].center.y, clickedMouse.x - all_rings[0].center.x);
                mouseDegrees = mouseRads * (180 / Math.PI);
            }
        }
    }

    function draggingMove(e) {
        if(isDragging) {
            const img_dragging = all_rings[selectedRing];
            console.log(selectedRing);

            const dragging_rads = Math.atan2(e.clientY - img_dragging.center.y, e.clientX - img_dragging.center.x);
            const dragging_degrees = dragging_rads * (180 / Math.PI);

            finalDegrees = acumm_degrees[selectedRing] + (dragging_degrees - mouseDegrees);
            const normalizedDegrees = (finalDegrees / DEGREES_STEP).toFixed(0) * DEGREES_STEP; 
            img_dragging.element.style.transform = `rotate(${normalizedDegrees}deg)`;
        }
    }

    function clickUp() {
        isDragging = false;
        acumm_degrees[selectedRing] = finalDegrees;
        console.log(finalDegrees);
    }

    function initOffsets(nrings) {
        offsets = [];
        for(var i = 0; i < nrings; i++) {
            offsets.push(0);
        }
    }
    for(var i = 0; i < n_rings; i++) {
        const r_img = createRingImagePiece(i, (path + "/ring_" + i + ".png"), n_rings, father);
        all_rings.push(r_img);
        acumm_degrees.push(0);
    }
    document.addEventListener('mousedown', clickDown);
    document.addEventListener('mousemove', draggingMove);
    document.addEventListener('mouseup', clickUp);
    return {
        remove() {
            for(var i = 0; i < n_rings; i++) {
                all_rings[i].element.remove();
            }
        }
    }
}
