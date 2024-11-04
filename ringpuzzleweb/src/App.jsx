import { useState, useEffect } from 'react'
import './App.css'
import { RingImage } from './RingImage'
import { RingPuzzle } from './RingPuzzle'

function App() {
  const [enabled, setEnabled] = useState(false)
  const [isDragging, setIsDragging] = useState(false)

  return (
    <main className='rp-main'>
      <h1 className='rp-main-greeting'>RingPuzzle</h1>
        <RingPuzzle path={ "rings" } accessionNumber={ "0_0_0"} scale={ "4"}></RingPuzzle>
    </main>
  )
}

export default App
