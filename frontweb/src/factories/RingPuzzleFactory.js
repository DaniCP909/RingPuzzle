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
    let normalizedFinalDegrees = 0;


    function normalizeDegrees(degrees) {
        return (degrees / DEGREES_STEP).toFixed(0) * DEGREES_STEP;
    }

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

            const dragging_rads = Math.atan2(e.clientY - img_dragging.center.y, e.clientX - img_dragging.center.x);
            const dragging_degrees = dragging_rads * (180 / Math.PI);

            finalDegrees = acumm_degrees[selectedRing] + (dragging_degrees - mouseDegrees);
            normalizedFinalDegrees = normalizeDegrees(finalDegrees);
            img_dragging.element.style.transform = `rotate(${normalizedFinalDegrees}deg)`;
        }
    }

    function clickUp() {
        isDragging = false;
        acumm_degrees[selectedRing] = normalizedFinalDegrees;
        if(isSolved()) {
            const event = new Event("solved");
            document.dispatchEvent(event);
        }
    }

    function initOffsets(nrings) {
        const offsets = [];
        for(var k = 0; k < nrings; k++) {
            const offset = Math.floor(Math.random() * 360);
            let final_offset = 0;
            if(offset > 180) 
                final_offset = offset % 180;
            else 
                final_offset = - (offset)
            offsets.push(final_offset);
        }
        return offsets;
    }
    function applyOffset(idRing, degrees) {
        acumm_degrees[idRing] = degrees;
        const img2offset = all_rings[idRing].element;
        img2offset.style.transform = `rotate(${degrees}deg)`;
    }

    function isSolved() {
        let solved = true;
        for (var i = 0; i < n_rings; i++) {
            if(((acumm_degrees[i]) % 360).toFixed(0) != 0) solved = false;
        }
        return solved;
    }
    const rings_offsets = initOffsets(n_rings);
    for(var i = 0; i < n_rings; i++) {
        const r_img = createRingImagePiece(i, (path + "/ring_" + i + ".png"), n_rings, father);
        console.log(path + "/ring_" + i + ".png");
        all_rings.push(r_img);
        acumm_degrees.push(0);
        applyOffset(i, rings_offsets[i]);
    }
    document.addEventListener('mousedown', clickDown);
    document.addEventListener('mousemove', draggingMove);
    document.addEventListener('mouseup', clickUp);
    return {
        remove() {
            for(var i = 0; i < n_rings; i++) {
                all_rings[i].element.remove();
            }
        },
    }
}
