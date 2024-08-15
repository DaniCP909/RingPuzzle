export class RingImg {
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
