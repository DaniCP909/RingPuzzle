export function RingImage ( { ringId, path, nRings, degrees }) {

    return (
        <img
            className="rp-ringImg" 
            alt={ `ring_img${ringId}` }
            src={ path } 
            style={{ 
                transform: `rotate(${degrees}deg)`
            }}
        />
    )
}