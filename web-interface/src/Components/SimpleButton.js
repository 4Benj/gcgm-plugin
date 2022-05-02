import React from 'react'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'

export default function SimpleButton(props) {
  return (
    <div id={props.id} className='simple-button'>
        <FontAwesomeIcon icon={props.icon} />
    </div>
  )
}
