import { useState, useEffect, useRef } from "react";
import { RingImage } from "./RingImage";
export function RingPuzzle ({ path }) {

    const nGrooves = 4;

    const ringsContainerRef = useRef(null)

    const [data, setData] = useState(null)
    const [enabled, setEnabled] = useState(false)
    const [rings, setRings] = useState([])
    const [selectedRing, setSelectedRing] = useState(-1)

    // !!! CENTER(x,y) IMG(w,h) --> not lef top
    const [imgPosition, setImgPosition] = useState({ x: 0, y: 0, wh: 0})

    useEffect(() => {
        fetch("http://localhost:8005/random_ringpuzzle")
        .then(response => response.json())
        .then(json => setData(json))
        .catch(error => console.error(error))
        .finally(setEnabled(!enabled))
    }, []);

    useEffect(() => {
        if(data && enabled) {
            const scale_aux = data.scalesSolved
            const accNumber_aux = data.ringPuzzle.accessionNumber
            const items = Array.from({ length: scale_aux }, (_,i) => `${path}/${accNumber_aux}/${scale_aux}/${accNumber_aux}-${scale_aux}-${i}.png`)
            setRings(items)

            const rect = ringsContainerRef.current.getBoundingClientRect()
            const centerX = rect.left + rect.width / 2
            const centerY = rect.top + rect.height / 2
            const wH = rect.width

            setImgPosition({ x:centerX , y:centerY , wh: wH})
        }
    }, [enabled, data])


    useEffect(() => {
        const  handleDown = (e) => {
            if(data && imgPosition && enabled) {
                const {clientX, clientY} = e
                const distance = dist(clientX, imgPosition.x, clientY, imgPosition.y)
                const wh_aux = imgPosition.wh
                const scale_handle = data.scalesSolved
                if((wh_aux / 2) < distance) {
                    setSelectedRing(-1)
                }
                else {
                    setSelectedRing(Math.floor(distance / ((wh_aux / 2)/ scale_handle)))
                }
            
            }
            setSelectedRing((prev) => {
                console.log("Selected ring:", prev); // Logs the updated value immediately
                return prev;
            });
        }
        window.addEventListener('pointerdown', handleDown)
        window.addEventListener('pointermove', handleMove)
        window.addEventListener('pointerup', handleUp)

        return () => {
            window.removeEventListener('pointerdown', handleDown)
            window.removeEventListener('pointermove', handleMove)
            window.removeEventListener('pointerup', handleUp)
        }
    }, [imgPosition, data, enabled])

    

    function dist(pointerX, imgCenterX, pointerY, imgCenterY) {
        const dx = imgCenterX - pointerX;
        const dy = imgCenterY - pointerY;
        return Math.sqrt((dx*dx) + (dy * dy));
    }
  
    const  handleMove = (e) => {
        /*
        if(isDragging) {
            const img_dragging = all_rings[selectedRing];
  
            const dragging_rads = Math.atan2(e.clientY - img_dragging.center.y, e.clientX - img_dragging.center.x);
            const dragging_degrees = dragging_rads * (180 / Math.PI);
  
            finalDegrees = acumm_degrees[selectedRing] + (dragging_degrees - mouseDegrees);
            normalizedFinalDegrees = normalizeDegrees(finalDegrees);
            img_dragging.element.style.transform = `rotate(${normalizedFinalDegrees}deg)`;
        }
            */
    }
  
    const handleUp = (e) => {
        /*
        isDragging = false;
        acumm_degrees[selectedRing] = normalizedFinalDegrees;
        if(isSolved()) {
            const event = new Event("solved");
            document.dispatchEvent(event);
        }
            */
    }

    return(
        <div 
            className='rp-ringPuzzleContainer' 
        >
            <div 
                id='rp-ringsContainer'
                ref={ringsContainerRef}
            >
                 {rings.map((ringpath, index) => (
                    <RingImage 
                        key={`${data.accessionNumber}-${data.scalesSolved}-${index}`} 
                        ringId={index} 
                        path={ringpath}
                    />
                 ))}
            </div>
            <h2 className="rp-solved">SOLUCIONADO</h2>
            <button className="rp-nextButton">Siguiente</button>
        </div>
    )
}


/*
import { useState, useEffect, useRef } from "react";
import { RingImage } from "./RingImage";
export function RingPuzzle ({ path, accessionNumber, scale }) {

    const SCALE = 4

    const ringsContainerRef = useRef(null)

    const [isDragging, setIsDragging] = useState(false)
    const [pointer, setPointer] = useState({x: 0, y: 0})
    const [imgPosition, setImgPosition] = useState({centerX: 0, centerY: 0, wh: 0})
    const [selectedRing, setSelectedRing] = useState(-1)
    const [loaded, setLoaded] = useState(false)

    useEffect(() => {
        if(ringsContainerRef.current) {
            const rect = ringsContainerRef.current.getBoundingClientRect();

            const cX = rect.left + (rect.width / 2)
            const cY = rect.top + (rect.height / 2)
            const wH = rect.width
            //console.log("Left: ", rect.left," | Top: ", rect.top," | W: ", rect.width," | H: ", rect.height," || cX: ", cX," || cY: ", cY)
            setImgPosition({ centerX:cX, centerY:cY, wh:wH})
        }
        setLoaded(!loaded)
    }, [])

    useEffect(() => {
        const handleDown = (e) => {
            const {clientX, clientY} = e
            setIsDragging(true)
            setPointer({x: clientX, y: clientY})

            const distance = dist(clientX, imgPosition.centerX, clientY, imgPosition.centerY)
            const wh_aux = imgPosition.wh
            //console.log(imgPosition)
            //console.log(distance)
            //console.log(clientX, " - ", clientY)
            if(distance > (wh_aux / 2)) {
                setSelectedRing(-1)
            }
            else {
                setSelectedRing(Math.floor(distance / ((wh_aux / 2)/ SCALE)))
            }
        }
        const handleUp = (e) => {
            setIsDragging(false)
        }
        if(loaded) {
            window.addEventListener('pointerdown', handleDown)
            window.addEventListener('pointerup', handleUp)
        }

        return () => {
            window.removeEventListener('pointerdown', handleDown)
            window.removeEventListener('pointerup', handleUp)
        }
    }, [loaded, imgPosition])

    useEffect(() => {
        const handleMove = (e) => {
            if(isDragging) {
                console.log(selectedRing)
            }
        }
        if(isDragging && (selectedRing != -1)) {
            window.addEventListener('pointermove', handleMove)
        }
        return () => {
            window.removeEventListener('pointermove', handleMove)
        }
    }, [isDragging, selectedRing])


    function dist(pointerX, imgCenterX, pointerY, imgCenterY) {
        const dx = imgCenterX - pointerX;
        const dy = imgCenterY - pointerY;
        return Math.sqrt((dx*dx) + (dy * dy));
    }


    return(
        <div 
            className='rp-ringPuzzleContainer' 
        >
            <div 
                className='rp-ringsContainer'
                ref={ringsContainerRef}
            >
                 
            </div>
        </div>
    )
}
*/