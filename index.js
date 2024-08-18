import { createRingPuzzle } from "./src/factories/RingPuzzleFactory.js"
const N_RINGS = 4;

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}


const puzzlecontainer = document.getElementById('puzzlecontainer');
const solved_heading = document.getElementById('solved');
const next_button = document.getElementById("next_button");
let full_img;

let actual_puzzle;
let id_actual = 0;

document.addEventListener('dragstart', (e) => {
    e.preventDefault();
});

function solvedHandler() {
    actual_puzzle.remove();
    full_img.hidden = false;
    solved_heading.hidden = false;
    next_button.hidden = false;
    document.removeEventListener("solved", solvedHandler);
}

function nextPuzzle() {
    if(id_actual < 2) {
        if(full_img != null) full_img.remove();
        solved_heading.hidden = true;
        next_button.hidden = true;
        actual_puzzle = createRingPuzzle("images/painting" + id_actual, N_RINGS, puzzlecontainer, 30);
        full_img = document.createElement("img");
        full_img.src = "images/painting" + id_actual + "/full_painting.png";
        puzzlecontainer.appendChild(full_img);
        full_img.style.height = `400`;
        console.log(full_img);
        full_img.hidden = true;
        id_actual += 1;
        document.addEventListener(
            "solved",
            solvedHandler,
        );
    }
}

next_button.addEventListener("click", nextPuzzle);
nextPuzzle();



//sleep(30000).then(() => { puzzle.remove() } );

