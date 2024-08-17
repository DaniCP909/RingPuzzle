export const createRingImagePiece = (ring_id, path, n_rings, father) => {
    const img = document.createElement('img');
    img.src = path;
    img.alt = "Ring image piece " + ring_id + " of the painting puzzle";
    img.style.width = `100%`;
    img.style.height = `100%`;
    img.style.userSelect = `none`;
    img.style.gridArea = `1 / 1`;
    father.appendChild(img);
    const rect = img.getBoundingClientRect();
    function dist(x1, x2, y1, y2) {
        const dx = x2 - x1;
        const dy = y2 - y1;
        return Math.sqrt((dx*dx) + (dy * dy));
    }
    return {
        id: ring_id,
        element: img,
        center: {
            x: rect.left + rect.width / 2,
            y: rect.top + rect.height / 2,
        },
        w_h: rect.width,
        isClicked(x, y) {
            const distance = dist(x, this.center.x, y, this.center.y);
            const radius = this.w_h / 2;
            const down_limit = ring_id * ((this.w_h / 2) / n_rings);
            const up_limit = (ring_id + 1) * ((this.w_h / 2) / n_rings);
            return ((distance < up_limit) && (distance > down_limit));
        }
    }
}