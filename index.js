import { createRingPuzzle } from "./src/factories/RingPuzzleFactory.js"
const N_RINGS = 4;

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}


const puzzlecontainer = document.getElementById('puzzlecontainer');

document.addEventListener('dragstart', (e) => {
    e.preventDefault();
});

const puzzle = createRingPuzzle("images", N_RINGS, puzzlecontainer, 60);

//sleep(30000).then(() => { puzzle.remove() } );

