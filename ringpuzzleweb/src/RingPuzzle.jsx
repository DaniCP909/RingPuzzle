import { useState, useEffect, useRef, useCallback } from "react";
import { RingImage } from "./RingImage";

export function RingPuzzle({ path, accessionNumber, scale }) {
    const SCALE = 4;
    const ringsContainerRef = useRef(null);


    const [ringsPath, setRingsPath] = useState([])
    const [isDragging, setIsDragging] = useState(false);
    const [imgPosition, setImgPosition] = useState({ centerX: 0, centerY: 0, wh: 0 });
    const [selectedRing, setSelectedRing] = useState(-1);
    const [ringsPosition, setRingsPosition] = useState(Array(SCALE).fill(0.0));
    const [startAngle, setStartAngle] = useState(0);

    // Calculate angle between two points
    const calculateAngle = useCallback((clientX, clientY, centerX, centerY) => {
        return Math.atan2(clientY - centerY, clientX - centerX) * (180 / Math.PI);
    }, []);

    // Calculate distance between two points
    const calculateDistance = useCallback((pointerX, imgCenterX, pointerY, imgCenterY) => {
        const dx = imgCenterX - pointerX;
        const dy = imgCenterY - pointerY;
        return Math.sqrt((dx * dx) + (dy * dy));
    }, []);

    // Initialize container dimensions
    useEffect(() => {
        const updateDimensions = () => {
            if (ringsContainerRef.current) {
                const rect = ringsContainerRef.current.getBoundingClientRect();
                const cX = rect.left + (rect.width / 2);
                const cY = rect.top + (rect.height / 2);
                setImgPosition({ centerX: cX, centerY: cY, wh: rect.width });
            }
        };

        const items = Array.from({ length: SCALE }, (_,i) => `rings/0-0-0/${SCALE}/0-0-0-4-${i}.png`)
        setRingsPath(items)

        updateDimensions();
        window.addEventListener('resize', updateDimensions);
        
        // Prevent default drag behavior
        const preventDrag = (e) => e.preventDefault();
        window.addEventListener('dragstart', preventDrag);

        return () => {
            window.removeEventListener('resize', updateDimensions);
            window.removeEventListener('dragstart', preventDrag);
        };
    }, []);

    // Handle pointer down
    const handlePointerDown = useCallback((e) => {
        const { clientX, clientY } = e;
        const { centerX, centerY, wh } = imgPosition;
        
        const distance = calculateDistance(clientX, centerX, clientY, centerY);
        const ringIndex = Math.floor(distance / ((wh / 2) / SCALE));
        
        if (distance <= (wh / 2) && ringIndex < SCALE) {
            setIsDragging(true);
            setSelectedRing(ringIndex);
            const angle = calculateAngle(clientX, clientY, centerX, centerY);
            setStartAngle(angle);
        }
    }, [imgPosition, calculateDistance, calculateAngle]);

    // Handle pointer up
    const handlePointerUp = useCallback(() => {
        setIsDragging(false);
        setSelectedRing(-1);
    }, []);

    // Handle pointer move
    const handlePointerMove = useCallback((e) => {
        if (!isDragging || selectedRing === -1) return;

        const { clientX, clientY } = e;
        const { centerX, centerY } = imgPosition;
        
        const currentAngle = calculateAngle(clientX, clientY, centerX, centerY);
        const angleDelta = currentAngle - startAngle;
        
        setStartAngle(currentAngle);
        setRingsPosition(prev => {
            const newPositions = [...prev];
            newPositions[selectedRing] = (newPositions[selectedRing] + angleDelta) % 360;
            return newPositions;
        });
    }, [isDragging, selectedRing, imgPosition, startAngle, calculateAngle]);

    // Setup event listeners
    useEffect(() => {
        window.addEventListener('pointerdown', handlePointerDown);
        window.addEventListener('pointerup', handlePointerUp);
        window.addEventListener('pointermove', handlePointerMove);

        return () => {
            window.removeEventListener('pointerdown', handlePointerDown);
            window.removeEventListener('pointerup', handlePointerUp);
            window.removeEventListener('pointermove', handlePointerMove);
        };
    }, [handlePointerDown, handlePointerUp, handlePointerMove]);

    return (
        <div className='rp-ringPuzzleContainer'>
            <div className='rp-ringsContainer' ref={ringsContainerRef}>
                {ringsPath.map((ringpath, index) => (
                    <RingImage 
                        key={`0-0-0-${SCALE}-${index}`} 
                        ringId={index} 
                        path={ringpath}
                        degrees={Math.round(ringsPosition[index])}
                    />
                 ))}
            </div>
        </div>
    );
}